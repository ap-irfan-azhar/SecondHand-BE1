package id.binaracademy.secondhand.controller;

import id.binaracademy.secondhand.dto.OffersPriceDto1;
import id.binaracademy.secondhand.entity.OffersPrice;
import id.binaracademy.secondhand.service.interfaces.OffersPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/offer")
public class OffersPriceController {
    @Autowired
    private OffersPriceService offersPriceService;

    @PostMapping("/tawar")
    public OffersPrice postOffer(@RequestBody OffersPriceDto1 offersPrice) {
        return offersPriceService.saveOffer(offersPrice);
    }

    @GetMapping("/list")
    public List<OffersPrice> getAllOffers() {
        return offersPriceService.findAllOffers();
    }

    @GetMapping("/list/{id}")
    public List<OffersPrice> getAllOffersByProductId(Long id) {
        return offersPriceService.findAllOffersByProductId(id);
    }
}
