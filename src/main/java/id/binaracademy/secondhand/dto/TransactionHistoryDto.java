package id.binaracademy.secondhand.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class TransactionHistoryDto implements Serializable {
    private final Long buyerId;
    private final Long sellerId;
    private final Long productId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final Date dateSold;
    private final int price;
}
