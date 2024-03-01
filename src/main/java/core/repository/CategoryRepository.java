package core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import core.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findById(int id);
}
