package models;

import java.util.Map;

class AnemicDomain {

    static class Invoice {
        int id;
        int clientId;
        Map<Item, Quantity> line;
        Map<Item, Price> prices;
        Money tax;

        @Override public boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof Invoice invoice)) return false;

            return id == invoice.id;
        }

        @Override public int hashCode() {
            return id;
        }
    }

    // Domain Service
    static class InvoiceService {
        InvoiceRepository invoiceRepository;
        ItemRepository itemRepository;

        void addItem(int invoiceId, int itemId, Quantity quantity) {
            Invoice invoice = invoiceRepository.findBy(invoiceId);
            Item item = itemRepository.findBy(itemId);

            invoice.line.put(item, quantity);
            recalculateTax(invoice);

            invoiceRepository.save(invoice);
        }

        void applyDiscount(int invoiceId, Item item, Money discount) {
            // check business invariants
            Invoice invoice = invoiceRepository.findBy(invoiceId);

            invoice.prices.computeIfPresent(item, (k, price) -> price.minus(discount));
            recalculateTax(invoice);

            invoiceRepository.save(invoice);
        }

        private void recalculateTax(Invoice invoice) {
            // don't do it in production, use i.e., BigDecimal for money
            Money sum = invoice.prices.values().stream().reduce(Price::plus).get().value();

            invoice.tax = new Money((int) (sum.amount * 0.25), sum.currency);
        }
    }

    interface InvoiceRepository {
        Invoice findBy(int id);

        void save(Invoice invoice);
    }

    static class Item {
        int id;
        String name;

        @Override public boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof Item item)) return false;

            return id == item.id;
        }

        @Override public int hashCode() {
            return id;
        }
    }

    record Price(Money value) {
        public Price minus(Money that) {
            return new Price(this.value.minus(that));
        }

        public Price plus(Price that) {
            return new Price(this.value.plus(that.value));
        }
    }

    static class Money {
        final int amount;
        final Currency currency;

        Money(int amount, Currency currency) {
            assert amount > 0;
            this.amount = amount;
            this.currency = currency;
        }

        Money plus(Money that) {
            assert this.currency == that.currency;
            return new Money(this.amount + that.amount, currency);
        }

        Money minus(Money that) {
            assert this.currency == that.currency;
            return new Money(this.amount - that.amount, currency);
        }

        @Override public boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof Money money)) return false;

            if (amount != money.amount) return false;
            return currency == money.currency;
        }

        @Override public int hashCode() {
            int result = amount;
            result = 31 * result + (currency != null ? currency.hashCode() : 0);
            return result;
        }
    }

    static class Quantity {
        final int amount;
        final Unit unit;

        Quantity(int amount, Unit unit) {
            assert amount > 0;
            this.amount = amount;
            this.unit = unit;
        }

        Quantity increment() {
            return new Quantity(amount + 1, unit);
        }

        @Override public boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof Quantity quantity)) return false;

            if (amount != quantity.amount) return false;
            return unit == quantity.unit;
        }

        @Override public int hashCode() {
            int result = amount;
            result = 31 * result + (unit != null ? unit.hashCode() : 0);
            return result;
        }
    }

    enum Unit {
        KG,
        LITER,
        PIECE
    }

    enum Currency {
        EUR,
        GBP,
        USD
    }

    interface ItemRepository {
        Item findBy(int id);
    }

    static class ApplicationService {

        private InvoiceService invoiceService;
        private InvoiceRepository invoiceRepository;

        void addItemToInvoice(int invoiceId, int itemId, Quantity quantity) {

            invoiceService.addItem(invoiceId, itemId ,quantity);
        }

        void sendInvoiceToClient(int clientId, int invoiceId) {
            Invoice invoice = invoiceRepository.findBy(invoiceId);

            byte[] pdf = makePDF(invoice);

            sendToClient(clientId, pdf);
        }

        private void sendToClient(int clientId, byte[] attachment) {}

        private byte[] makePDF(Invoice invoice) {return new byte[0];}
    }
}
