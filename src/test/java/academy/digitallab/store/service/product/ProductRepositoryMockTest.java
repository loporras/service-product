package academy.digitallab.store.service.product;

import academy.digitallab.store.service.product.entity.Category;
import academy.digitallab.store.service.product.entity.Product;
import academy.digitallab.store.service.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@DataJpaTest
public class ProductRepositoryMockTest {

    @Autowired
    private ProductRepository productRepository;



    @Test
    public void findByCategory_thenListProduct(){

        Product product01 = Product.builder()

                .name("Computer")
                .description("")
                .category(Category.builder()
                        .name("Tecnología")
                        .build())
                .stock(Double.parseDouble("20"))
                .price(Double.parseDouble("190.43"))
                .status("Created")
                .createAt(new Date())
                .build();

        productRepository.save(product01);
        List<Product> products = productRepository.findByCategory(product01.getCategory());
        int i = products.size();
        Assertions.assertThat(i).isEqualTo(2);



    }

}
