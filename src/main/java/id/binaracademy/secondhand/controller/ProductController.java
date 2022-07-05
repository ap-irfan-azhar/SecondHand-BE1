package id.binaracademy.secondhand.controller;

import id.binaracademy.secondhand.dto.ProductDto;
import id.binaracademy.secondhand.entity.Product;
import id.binaracademy.secondhand.repository.ProductRepository;
import id.binaracademy.secondhand.service.impl.ProductServiceImpl;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public List<Product> findAllProduct(){
        return productService.findAllProducts();
    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable Long id){
        return productService.findProductById(id);
    }

    @GetMapping("/search/")
    public List<Product> findProductByName(@RequestParam String name){
        return productService.findByNameLike(name);
    }
    @GetMapping("/findByNameLike/")
    public List<Product> findByNameLike(@RequestParam String name){
        return productService.findByNameLike(name);
    }

    @GetMapping("/findByNameLike2/")
    public ResponseEntity<List<Product>> getProductByName(@RequestParam String name){
        return ResponseEntity.ok(productRepository.findByNameLike(name));
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody ProductDto product){
        return productService.updateProduct(id, product);
    }
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }


    @CrossOrigin
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product registerProduct(@ModelAttribute ProductDto product) throws Exception{
        return productService.saveProduct(product);

    }



}
