package todo.advance.blindsight.entity.card;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import todo.advance.blindsight.entity.subCard.SubCard;
import todo.advance.blindsight.util.generate.code.CodeGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Card {
    @Id
    @Column(name = "card_id", length = 50, updatable = false)
    @Size(max = 50, message = "Id required 50 chraracters below") // validate size
    private String cardId = CodeGenerator.generateRandomCodeWithoutSpecialCharacters(10);

    @Column(name = "card_name", length = 50)
    @Size(max = 50, message = "name required 50 chraracters below") // validate size
    private String cardName;

    @Column(name = "card_image", length = 255)
    private String cardImage;

    @CreatedDate
    private ZonedDateTime createdDate = Instant.now().atZone(ZoneId.systemDefault());

    @LastModifiedDate
    private ZonedDateTime lastModifiedDate = Instant.now().atZone(ZoneId.systemDefault());

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<SubCard> subCards;
}
