package Blind.Sight.Commnunity.domain.entity.many;

import Blind.Sight.Commnunity.domain.entity.Category;
import Blind.Sight.Commnunity.domain.entity.Image;
import Blind.Sight.Commnunity.domain.entity.User;
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
@Table(name = "User_Images")
public class UserImage {
    @Id
    @Column(name = "user_image_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryImageId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "image_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Image image;
}