package id.binaracademy.secondhand.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OffersPriceDto1 implements Serializable {
    private final Long productId;
    private final Long buyersId;
    private final Long sellersId;
    private final int buyersPrice;
    private final boolean statusOffers;
}
