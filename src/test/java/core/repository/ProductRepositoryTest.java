package core.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.support.TransactionOperations;

import core.entity.Category;
import core.entity.Product;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionOperations transactional;

    @Test
    void testCreate() {
        Category category = categoryRepository.findById(1L).orElse(null);
        Assertions.assertNotNull(category);

        {
            Product product = new Product();
            product.setName("ipad");
            product.setPrice(20000000L);
            product.setCategory(category);

            productRepository.save(product);

            Assertions.assertNotNull(product.getId());
        }

        {
            Product product = new Product();
            product.setName("macbook");
            product.setPrice(300000L);
            product.setCategory(category);

            productRepository.save(product);

            Assertions.assertNotNull(product.getId());
        }
    }

    @Test
    void testGet() {
        List<Product> products = productRepository.findAllByCategory_name("gadget");
        Assertions.assertNotNull(products);
        Assertions.assertEquals(products.get(0).getCategory().getName(), "gadget");
    }

    @Test
    void findProductSort() {
        Sort sort = Sort.by(Sort.Order.desc("id"));
        List<Product> products = productRepository.findAllByCategory_name("gadget", sort);
        Assertions.assertNotNull(products);
        Assertions.assertEquals("macbook", products.get(0).getName());
        Assertions.assertEquals("ipad", products.get(1).getName());
    }

    @Test
    void findProductPageable() {
        // Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Order.desc("id")));
        Pageable pageable = PageRequest.of(0, 1, Sort.by("id").descending());
        Page<Product> products = productRepository.findAllByCategory_name("gadget", pageable);
        assertEquals(1, products.getContent().size());
        assertEquals("macbook", products.getContent().get(0).getName());

        pageable = PageRequest.of(1, 1, Sort.by(Sort.Order.desc("id")));
        products = productRepository.findAllByCategory_name("gadget", pageable);
        assertEquals(1, products.getContent().size());
        assertEquals("ipad", products.getContent().get(0).getName());
        assertEquals(1, products.getNumber()); // number of page
        assertEquals(4, products.getTotalElements()); // total element query result
        assertEquals(4, products.getTotalPages()); // total page result
    }

    @Test
    void testCount() {
        Long count = productRepository.countByCategory_name("gadget");
        Assertions.assertEquals(4, count);
    }

    @Test
    void testExists() {
        boolean exists = productRepository.existsByName("ipad");
        Assertions.assertTrue(exists);

        boolean notExists = productRepository.existsByName("janggar");
        Assertions.assertFalse(notExists);
    }

    @Test
    void testDelete() {
        Category category = categoryRepository.findById(1L).orElse(null);
        assertNotNull(category);

        Product product = new Product();
        product.setName("testDelete");
        product.setPrice(35000L);
        product.setCategory(category);
        productRepository.save(product);

        int delete = productRepository.deleteByName("testDelete");
        assertEquals(1, delete);

        delete = productRepository.deleteByName("testDelete");
        assertEquals(0, delete);
    }
}
