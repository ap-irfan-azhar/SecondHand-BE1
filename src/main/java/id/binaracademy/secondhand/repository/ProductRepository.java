package id.binaracademy.secondhand.repository;

import id.binaracademy.secondhand.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    List<Product> findByNameLike(String name);

    @Query("Select c from Product c where c.name like %:name%")
    List<Product>findNames(String name);
}