package academy.digitallab.store.service.product.service;

import academy.digitallab.store.service.product.entity.Category;
import academy.digitallab.store.service.product.entity.Product;

import java.util.List;

public interface ProductService {

    public List<Product> listAllProducts();
    public Product getProductById(Long id);
    public Product saveProduct(Product product);
    public Product updateProduct(Product product);
    public Product deleteProduct(Long id);
    public List<Product> findProductByCategory(Category category);
    public Product updateStock(Long id, Double quantity);

}
