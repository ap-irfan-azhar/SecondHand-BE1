package id.binaracademy.secondhand.controller;

import id.binaracademy.secondhand.dto.ProductDto;
import id.binaracademy.secondhand.entity.Product;
import id.binaracademy.secondhand.repository.ProductRepository;
import id.binaracademy.secondhand.service.impl.ProductServiceImpl;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
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
    public Product findProductByName(@RequestParam String name){
        return productService.findProductByName(name);
    }
    @GetMapping("/findByNameLike/")
    public List<Product> findByNameLike(@RequestParam String name){
        return productService.findByNameLike(name);
    }

    @GetMapping("/findByNameLike2/")
    public ResponseEntity<List<Product>> getProductByName(@RequestParam String name){
        return ResponseEntity.ok(productRepository.findByNameLike(name));
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @ModelAttribute ProductDto product) throws Exception {
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

    @CrossOrigin
    @RequestMapping(value = "/update1", method = RequestMethod.PUT, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product updateProduct1(@RequestParam Long id, @ModelAttribute ProductDto product) throws Exception {
        return productService.updateProduct(id, product);
    }

}
