package id.binaracademy.secondhand.service.interfaces;

import id.binaracademy.secondhand.dto.OffersPriceDto;
import id.binaracademy.secondhand.dto.OffersPriceDto1;
import id.binaracademy.secondhand.entity.OffersPrice;


import java.util.List;

public interface OffersPriceService {
    OffersPrice saveOffer (OffersPriceDto1 offersPrice);
    List<OffersPrice> findAllOffers();
    List<OffersPrice> findAllOffersByProductId(Long id);

    List<OffersPrice> findAllOffersByBuyerId(Long id);

    List<OffersPrice> findAllOffersBySellerId(Long id);

    List<OffersPrice> findAllOffersByBuyerAndSellerId(Long buyerId, Long sellerId);

    OffersPrice saveOffer1(OffersPriceDto offersPrice);
}
