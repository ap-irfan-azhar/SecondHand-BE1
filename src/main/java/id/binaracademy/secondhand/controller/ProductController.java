package id.binaracademy.secondhand.controller;

import id.binaracademy.secondhand.dto.ProductDto;
import id.binaracademy.secondhand.entity.Product;
import id.binaracademy.secondhand.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/")
    public List<Product> findAllProduct(){
        return productService.findAllProducts();
    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable Long id){
        return productService.findProductById(id);
    }

    @GetMapping("/")
    public Product findProductByName(@RequestParam String name){
        return productService.findProductByName(name);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody ProductDto product){
        return productService.updateProduct(id, product);
    }
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

    @PostMapping("/")
    public Product registerProduct(@RequestBody ProductDto product){
        return productService.saveProduct(product);
    }
}
