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

    private Long productId;
    private Long buyers_Id;
    private int buyers_price;
    private boolean status;






}