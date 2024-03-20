package domainModel.application;

import domainModel.domain.Bid;
import domainModel.domain.AuctionRepository;
import domainModel.domain.Money;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

class BidOnAuctionService {

    private final AuctionRepository auctions;

    BidOnAuctionService(AuctionRepository auctions) {
        this.auctions = auctions;
    }

    public void bid(UUID auctionId, UUID memberId, BigDecimal amount, LocalDateTime dateOfBid) {
        var auction = auctions.findBy(auctionId);
        var bidAmount = new Money(amount);
        var offer = new Bid(memberId, bidAmount, dateOfBid);

        auction.placeBidFor(offer, dateOfBid);

        auctions.save(auction);
    }
}
