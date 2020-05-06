package academy.digitallab.store.service.product.controller;

import academy.digitallab.store.service.product.entity.Category;
import academy.digitallab.store.service.product.entity.Product;
import academy.digitallab.store.service.product.messages.ErrorMessages;
import academy.digitallab.store.service.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    private Object HashMap;

    @GetMapping
    public ResponseEntity <List<Product>> listProduct(@RequestParam(name="categoryId" , required = false) Long categoryId){

        List<Product> products = new ArrayList<>();
        if(null == categoryId){
            products = productService.listAllProducts();
            if(products.isEmpty()){
                return  ResponseEntity.noContent().build();
            }
        }
        else{
            products = productService.findProductByCategory(Category.builder().id(categoryId).build() );
            if(products.isEmpty()){
                return  ResponseEntity.notFound().build();
            }
        }

        return  ResponseEntity.ok(products);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity <Product> productById(@PathVariable("id")  Long id){

        Product product = productService.getProductById(id);
        if(product == null){
           return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(product);
        }
    }

    @PostMapping
    public ResponseEntity<Product> createdProduct(@Valid @RequestBody Product product, BindingResult result){

        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessages(result));
        }
        Product productDB = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDB);

    }

    public String formatMessages(BindingResult result){

        List<Map<String, String>> errors= result.getFieldErrors().stream()
                .map( err -> {
                            Map <String, String> e = new HashMap<>();
                            e.put(err.getField(), err.getDefaultMessage());
                            return e;
                        }
                ).collect(Collectors.toList());

        ErrorMessages errorMessages = ErrorMessages.builder()
                .code("01")
                .messages(errors).build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString ="";
        try{
            jsonString = mapper.writeValueAsString(errorMessages);
        }
        catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonString;
    }

    @PutMapping(value= "/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable("id") Long id){

        product.setId(id);
        Product productDB = productService.updateProduct(product);
        if(productDB == null ){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDB);
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<Product> updateStock( @PathVariable("id") Long id, @RequestParam(name="quantity", required = true) Double quantity){

        Product productDB = productService.updateStock(id, quantity);

        if(productDB == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDB);
    }

    @DeleteMapping( value= "/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id){

        Product product = productService.deleteProduct(id);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

}
