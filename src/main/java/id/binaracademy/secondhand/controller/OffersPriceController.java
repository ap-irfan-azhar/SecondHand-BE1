package id.binaracademy.secondhand.controller;

import id.binaracademy.secondhand.dto.OffersPriceDto;
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

    public OffersPrice getOffer(@PathVariable Long id) {
        return offersPriceService.getOffer(id);
    }

    @GetMapping("/list")
    public List<OffersPrice> getAllOffers() {
        return offersPriceService.findAllOffers();
    }

    @GetMapping("/list/product/{id}")
    public List<OffersPrice> getAllOffersByProductId(@RequestParam Long id) {
        return offersPriceService.findAllOffersByProductId(id);
    }

    @GetMapping("/list/seller/{id}")
    public List<OffersPrice> getAllOffersBySellerId(@RequestParam Long id) {
        return offersPriceService.findAllOffersBySellerId(id);
    }

    @GetMapping("/list/buyer/{id}")
    public List<OffersPrice> getAllOffersByBuyerId(@RequestParam Long id) {
        return offersPriceService.findAllOffersByBuyerId(id);
    }

    @GetMapping("/list/IdBuyerAndSeller")
    public List<OffersPrice> getAllOffersByBuyerAndSellerId(@RequestBody Long buyerId, @RequestBody Long sellerId) {
        return offersPriceService.findAllOffersByBuyerAndSellerId(buyerId, sellerId);
    }

    @PostMapping("/tawar1")
    public OffersPrice postOffer1(@RequestBody OffersPriceDto offersPrice) {

        return offersPriceService.saveOffer1(offersPrice);
    }
}
