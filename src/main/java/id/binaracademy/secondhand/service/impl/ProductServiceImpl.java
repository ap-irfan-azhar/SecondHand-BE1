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
        Product product1;
        System.out.println("[!] existingProduct:"+existingProduct.isPresent());
        if (existingProduct.isPresent()) {
            product1 = existingProduct.get();
            product1.setName(product.getName());
            product1.setPrice(product.getPrice());
            product1.setCategoriesId(product.getCategoriesId());
            product1.setDescription(product.getDescription());
            product1.setStatus(product.getStatus());
            String imageUrl;
            try {
                Map uploadResult = cloudinary.uploader().upload(product.getFile().getBytes(), ObjectUtils.emptyMap());
                imageUrl = (String) uploadResult.get("url");
                product1.setPhotoUrl(imageUrl);
            } catch (Exception e) {
                throw new Exception("couldn't save photos");
            }
            product1.setPhotoUrl(imageUrl);
            return productRepository.save(product1);

        }else
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

