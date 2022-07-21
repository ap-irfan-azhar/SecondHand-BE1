package id.binaracademy.secondhand.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offers_price")
public class OffersPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offers_id", nullable = false)
    private Long offersId;

    @Column(name = "product_id", nullable = false)
    private Long productId;
    @Column(name = "buyers_id", nullable = false)
    private Long buyersId;
    @Column(name = "sellers_id", nullable = false)
    private Long sellersId;
    @Column(name = "buyers_price", nullable = false)
    private int buyersPrice;
    @Column(name = "status_offers", nullable = false)
    private boolean statusOffers;

}