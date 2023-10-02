package Blind.Sight.Commnunity.domain.entity;

import Blind.Sight.Commnunity.util.random.RandomId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @Column(name = "product_id", updatable = false)
    private String productId;
    @Column(unique = true, nullable = false)
    private String sku;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private String description;

    // product appearance
    @Column
    private Integer length;
    @Column
    private Integer width;
    @Column
    private Integer height;
    @Column
    private Integer weight;

    // for prepare deleting product
    @Column
    private int deleteFlag = 0;
    @Column
    private String oldSku;

    public Product() {
        this.productId = RandomId.generateCounterIncrement("Product-");
        this.sku = RandomId.randomSimpleCode("SKU-",10);
    }

    public Product(String name, Double price,  String description) {
        this.productId = RandomId.generateCounterIncrement("Product-");
        this.sku = RandomId.randomSimpleCode("SKU-",10);
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
