package id.binaracademy.secondhand.repository;

import id.binaracademy.secondhand.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}