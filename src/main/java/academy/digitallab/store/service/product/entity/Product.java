package academy.digitallab.store.service.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Entity
@Table(name="tbl_products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable = false)
    private Long id;
    @NotEmpty (message = "El Nombre no puede estar vacio")
    private String name;
    private String description;
    @Positive(message = "El stock tiene que ser mayor a 0")
    private Double stock;
    private Double price;
    private String status;
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @NotNull(message = "La Categoría no puede ser null")
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="category_id", nullable = false)

    //@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    //@JoinColumn(name= "category_id")

    //@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Category category;



}
