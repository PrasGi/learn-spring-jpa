package core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import core.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Long countByCategory_name(String name);

    boolean existsByName(String name);
    Integer deleteByName(String name);
    List<Product> findAllByCategory_name(String name);
    List<Product> findAllByCategory_name(String name, Sort sort);
    Page<Product> findAllByCategory_name(String name, Pageable pageable);
}
