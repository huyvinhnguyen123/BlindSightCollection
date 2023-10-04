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
@Table(name = "Images")
public class Image {
    @Id
    @Column(name = "image_id", updatable = false)
    private String imageId;
    @Column(nullable = false)
    private String imagePathId;
    @Column(nullable = false)
    private String imageName;
    @Column(nullable = false)
    private String imagePath;

    public Image() {
        this.imageId = RandomId.generateCounterIncrement("Image-");
    }

    public Image(String pathId, String name, String path) {
        this.imageId = RandomId.generateCounterIncrement("Image-");
        this.imagePathId = pathId;
        this.imageName = name;
        this.imagePath = path;
    }
}
