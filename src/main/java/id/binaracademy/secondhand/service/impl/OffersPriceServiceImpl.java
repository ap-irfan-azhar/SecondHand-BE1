package id.binaracademy.secondhand.service.impl;

import id.binaracademy.secondhand.dto.OffersPriceDto;
import id.binaracademy.secondhand.dto.OffersPriceDto1;
import id.binaracademy.secondhand.entity.OffersPrice;
import id.binaracademy.secondhand.repository.OffersPriceRepository;
import id.binaracademy.secondhand.service.interfaces.OffersPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OffersPriceServiceImpl implements OffersPriceService {
    @Autowired
    private OffersPriceRepository offersPriceRepository;


    @Override
    public OffersPrice saveOffer(OffersPriceDto1 offersPrice) {
        OffersPrice offersPrice1 = new OffersPrice();
        offersPrice1.setProductId(offersPrice.getProductId());
        offersPrice1.setBuyersPrice(offersPrice.getBuyersPrice());
        offersPrice1.setSellersId(offersPrice.getSellersId());
        offersPrice1.setBuyersId(offersPrice.getBuyersId());
        offersPrice1.setBuyersPrice(offersPrice.getBuyersPrice());
        offersPrice1.setStatusOffers(offersPrice.isStatusOffers());
        return offersPriceRepository.save(offersPrice1);
    }

    @Override
    public List<OffersPrice> findAllOffers() {
        return offersPriceRepository.findAll();
    }

    @Override
    public List<OffersPrice> findAllOffersByProductId(Long id) {
        Optional<OffersPrice> offersPrice = offersPriceRepository.findById(id);
        if (offersPrice.isPresent()) {
            return offersPriceRepository.findAllByProductId(id);
        } else {
            throw new IllegalArgumentException(
                    String.format("OffersPrice with id %s not found", id)
            );
        }

    }
}
