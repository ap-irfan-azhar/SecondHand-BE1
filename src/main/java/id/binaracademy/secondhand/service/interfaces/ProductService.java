package id.binaracademy.secondhand.service.interfaces;

import id.binaracademy.secondhand.dto.ProductDto;
import id.binaracademy.secondhand.entity.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(ProductDto product);
    List<Product> findAllProducts();
    Product findProductById(Long id);
    Product findProductByName(String name);
    Product updateProduct(Long id, ProductDto product);
    void deleteProduct(Long id);
    List<Product> findByNameLike(String name);

}
