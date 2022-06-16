package id.binaracademy.secondhand.service.impl;

import id.binaracademy.secondhand.dto.ProductDto;
import id.binaracademy.secondhand.entity.Product;
import id.binaracademy.secondhand.repository.ProductRepository;
import id.binaracademy.secondhand.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public Product saveProduct(ProductDto product) {
       Product ProductToSave = new Product();
       ProductToSave.setName(product.getName());
       ProductToSave.setPrice(product.getPrice());
       ProductToSave.setCategoriesId(product.getCategoriesId());
       ProductToSave.setDescription(product.getDescription());
       ProductToSave.setStatus(product.getStatus());

        return productRepository.save(ProductToSave);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("Product with id %s not found", id)
            );
        }
        return product.get();
    }

    @Override
    public Product findProductByName(String name) {
        Optional<Product> product = productRepository.findByName(name);
        if (product.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("Product with name %s not found", name)
            );
        }
        return product.get();
    }

    @Override
    public Product updateProduct(Long id, ProductDto product) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("Product with id %s not found", id)
            );
        }
        Product productToUpdate = existingProduct.get();
        productToUpdate.setName(product.getName());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setCategoriesId(product.getCategoriesId());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setStatus(product.getStatus());

        return productRepository.save(productToUpdate);
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("Product with id %s not found", id)
            );
        }
        productRepository.delete(product.get());

    }
}

