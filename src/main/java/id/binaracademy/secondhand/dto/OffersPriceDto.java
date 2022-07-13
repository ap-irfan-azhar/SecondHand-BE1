package id.binaracademy.secondhand.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OffersPriceDto implements Serializable {
    private final Long productId;
    private final Long buyers_Id;
    private final int buyers_price;
    private final boolean status;
}
