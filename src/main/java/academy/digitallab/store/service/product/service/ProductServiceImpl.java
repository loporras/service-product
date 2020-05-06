package academy.digitallab.store.service.product.service;

import academy.digitallab.store.service.product.entity.Category;
import academy.digitallab.store.service.product.entity.Product;
import academy.digitallab.store.service.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    //@Autowired
    private final ProductRepository productRepository;


    @Override
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse( null);
    }

    @Override
    public Product saveProduct(Product product) {
        product.setStatus("CREATED");
        product.setCreateAt(new Date());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product productDB = getProductById( product.getId());
        if(productDB.equals(null)){
            return null;
        }
        productDB.setStatus(product.getStatus());
        productDB.setDescription(product.getDescription());
        productDB.setName(product.getName());
        productDB.setStock(product.getStock());
        productDB.setPrice(product.getPrice());
        productDB.setCategory(product.getCategory());
        productDB.setCreateAt(product.getCreateAt());
        return productRepository.save(productDB);
    }

    @Override
    public Product deleteProduct(Long id) {
        Product productDB = getProductById( id);
        if(productDB.equals(null)) {
            return null;
        }
        productDB.setStatus("DELETED");
        return productRepository.save(productDB);

    }

    @Override
    public List<Product> findProductByCategory(Category category) {
       return  productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        Product productDB = getProductById( id);
        if(productDB.equals(null)) {
            return null;
        }
        productDB.setStock(quantity);
        return productRepository.save(productDB);
    }
}
