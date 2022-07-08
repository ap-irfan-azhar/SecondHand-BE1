package id.binaracademy.secondhand.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import id.binaracademy.secondhand.dto.ProductDto;
import id.binaracademy.secondhand.entity.Product;
import id.binaracademy.secondhand.repository.ProductRepository;
import id.binaracademy.secondhand.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dzxhgmtvs",
            "api_key", "377982552887464",
            "api_secret", "lN_JpQY_gABvIjSPsbVtiBQov3k",
            "secure", true));


    @Override
    public Product saveProduct(ProductDto product) throws Exception {

       Product productToSave = new Product();
       productToSave.setName(product.getName());
       productToSave.setPrice(product.getPrice());
       productToSave.setCategoriesId(product.getCategoriesId());
       productToSave.setDescription(product.getDescription());
       productToSave.setStatus(product.getStatus());
        String imageUrl;
        try {
            Map uploadResult = cloudinary.uploader().upload(product.getFile().getBytes(), ObjectUtils.emptyMap());
            imageUrl = (String) uploadResult.get("url");
            productToSave.setPhotoUrl(imageUrl);
        } catch (Exception e) {
            throw new Exception("couldn't save photos");
        }
        productToSave.setPhotoUrl(imageUrl);

        return productRepository.save(productToSave);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("Product with id %s not found", id)
            );
        }
        return product.get();
    }

    @Override
    public Product findProductByName(String name) {
        Optional<Product> product = productRepository.findByName(name);
        if (!product.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("Product with name %s not found", name)
            );
        }
        return product.get();
    }

    @Override
    public Product updateProduct(Long id, ProductDto product) throws Exception {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product productToUpdate = existingProduct.get();
            productToUpdate.setName(product.getName());
            productToUpdate.setPrice(product.getPrice());
            productToUpdate.setCategoriesId(product.getCategoriesId());
            productToUpdate.setDescription(product.getDescription());
            productToUpdate.setStatus(product.getStatus());
            String imageUrl;
            try {
                Map uploadResult = cloudinary.uploader().upload(product.getFile().getBytes(), ObjectUtils.emptyMap());
                imageUrl = (String) uploadResult.get("url");
                productToUpdate.setPhotoUrl(imageUrl);
            } catch (Exception e) {
                throw new Exception("couldn't save photos");
            }
            productToUpdate.setPhotoUrl(imageUrl);
            return productRepository.save(productToUpdate);

        }
        throw new Exception("Product could not be found");
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("Product with id %s not found", id)
            );
        }
        productRepository.delete(product.get());

    }

    @Override
    public List<Product> findByNameLike(String name) {
        List<Product> product = productRepository.findByNameLike(name);
        if (product.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("Product with name %s not found", name)
            );
        }
        return product;
    }

}

