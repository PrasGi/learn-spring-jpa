package core.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

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
}
