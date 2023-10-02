package Blind.Sight.Commnunity.domain.entity.many;

import Blind.Sight.Commnunity.domain.entity.Category;
import Blind.Sight.Commnunity.domain.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product_Categories")
public class ProductCategory {
    @Id
    @Column(name = "product_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productCategoryId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;
}
