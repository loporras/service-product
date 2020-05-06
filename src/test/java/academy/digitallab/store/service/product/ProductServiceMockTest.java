package academy.digitallab.store.service.product;


import academy.digitallab.store.service.product.entity.Category;
import academy.digitallab.store.service.product.entity.Product;
import academy.digitallab.store.service.product.repository.ProductRepository;
import academy.digitallab.store.service.product.service.ProductService;
import academy.digitallab.store.service.product.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ProductServiceMockTest {



    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void setup(){

        MockitoAnnotations.initMocks(this);
        productService = new ProductServiceImpl(productRepository);
        Product computer = Product.builder()
                .id(1L)
                .name("Computer")
                .price(Double.parseDouble("12.5"))
                .stock(Double.parseDouble("5"))
                .category(Category.builder().name("Tecnologia").build())
                .build();

       Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(computer));
       Mockito.when(productRepository.save(computer)).thenReturn(computer);


    }
    @Test
    public void whenValidGetId(){
        Product product = productService.getProductById(1L);
        Assertions.assertThat(product.getName()).isEqualTo("Computer01");
    }
    @Test
    public void whenValidUpdateStock(){

        Product product = productService.updateStock(1L, Double.parseDouble("8"));
        System.out.print(product);
        System.out.print(product.getStock());
        Assertions.assertThat(product.getStock()).isEqualTo(10);
    }
}
