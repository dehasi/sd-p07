package domainModel.domain;

import java.util.UUID;

public interface AuctionRepository {

    void save(Auction item);

    Auction findBy(UUID Id);
}
