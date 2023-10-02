package Blind.Sight.Commnunity.domain.entity.many;

import Blind.Sight.Commnunity.domain.entity.Image;
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
@Table(name = "Product_Images")
public class ProductImage {
    @Id
    @Column(name = "product_image_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productImageId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "image_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Image image;
}
