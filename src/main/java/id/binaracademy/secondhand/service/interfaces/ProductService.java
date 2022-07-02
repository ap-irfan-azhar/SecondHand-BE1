package id.binaracademy.secondhand.service.interfaces;

import id.binaracademy.secondhand.dto.ProductDto;
import id.binaracademy.secondhand.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Product saveProduct(ProductDto product) throws Exception;
    List<Product> findAllProducts();
    Product findProductById(Long id);
    Product findProductByName(String name);
    Product updateProduct(Long id, ProductDto product);
    void deleteProduct(Long id);
    List<Product> findByNameLike(String name);

    Product saveProductWithPhotos(MultipartFile file, ProductDto product) throws Exception;
}
