package Blind.Sight.Commnunity.domain.entity;

import Blind.Sight.Commnunity.util.random.RandomId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Categories")
public class Category {
    @Id
    @Column(name = "category_id", updatable = false)
    private String categoryId;
    @Column(unique = true, nullable = false)
    private String categoryName;

    public Category() {
        this.categoryId = RandomId.generateCounterIncrement("Category-");
        this.categoryName = "Uncategorized";
    }

    public Category(String categoryName) {
        this.categoryId = RandomId.generateCounterIncrement("Category-");
        this.categoryName = categoryName;
    }
}
