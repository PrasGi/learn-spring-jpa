package core.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import core.entity.Category;
import core.entity.Product;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testCreate() {
        Category category = categoryRepository.findById(1L).orElse(null);
        Assertions.assertNotNull(category);

        {
            Product product = new Product();
            product.setName("laptop");
            product.setPrice(10000000L);
            product.setCategory(category);

            productRepository.save(product);

            Assertions.assertNotNull(product.getId());
        }

        {
            Product product = new Product();
            product.setName("mouse");
            product.setPrice(100000L);
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
}
