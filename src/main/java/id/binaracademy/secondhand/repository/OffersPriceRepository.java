package id.binaracademy.secondhand.repository;

import id.binaracademy.secondhand.entity.OffersPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OffersPriceRepository extends JpaRepository<OffersPrice, Long> {
    @Query("SELECT o FROM OffersPrice o WHERE o.productId = :id")
    List<OffersPrice> findAllByProductId(Long id);

    @Query("SELECT o FROM OffersPrice o WHERE o.buyersId = :id")
    List<OffersPrice> findAllByBuyersId(Long id);

    @Query("SELECT o FROM OffersPrice o WHERE o.sellersId = :id")
    List<OffersPrice> findAllBySellersId(Long id);

    @Query("SELECT o FROM OffersPrice o WHERE o.productId = :buyerId AND o.sellersId = :sellerId")
    List<OffersPrice> findAllByBuyersIdAndSellersId(Long buyerId, Long sellerId);
}