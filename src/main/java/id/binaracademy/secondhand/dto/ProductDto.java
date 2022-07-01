package id.binaracademy.secondhand.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class ProductDto implements Serializable {
    private final String name;
    private final Integer price;
    private final Long categoriesId;
    private final String description;
    private final String status;
    MultipartFile file;
}
