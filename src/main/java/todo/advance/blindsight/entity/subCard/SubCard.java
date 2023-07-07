package todo.advance.blindsight.entity.subCard;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import todo.advance.blindsight.entity.card.Card;
import todo.advance.blindsight.entity.profile.Profile;
import todo.advance.blindsight.entity.todo.Todo;
import todo.advance.blindsight.util.generate.code.CodeGenerator;
import todo.advance.blindsight.util.generate.date.DateGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class SubCard {
    @ManyToOne
	@JoinColumn(name = "card_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
    // @JsonIgnoreProperties("subcards")
    @JsonIgnore
    private Card card;

    @ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
	name="profile_sub_card",
	joinColumns=@JoinColumn(name="sub_card_id"),
	inverseJoinColumns=@JoinColumn(name="profile_id")
	)
	@OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
	private List<Profile> profiles;

    @Id
    @Column(name = "sub_card_id", length = 50, updatable = false)
    @Size(max = 50, message = "Id required 50 chraracters below") // validate size
    private String subCardId = CodeGenerator.generateRandomCodeWithoutSpecialCharacters(20);

    @Column(name = "sub_card_name", length = 100)
    @Size(max = 100, message = "name required 100 chraracters below") // validate size
    private String subCardName;

    @Column(name = "sub_card_description", length = 500)
    @Size(max = 500, message = "name required 500 chraracters below") // validate size
    private String subCardDescription;

    @Column(name = "sub_card_time_upload", nullable = false)
    private String subCardTimeUpload = DateGenerator.getCurrentDateWithoutHMS();

    @Column(name = "sub_card_due_date")
    private String subCardDueDate;

    @CreatedDate
    private ZonedDateTime createdDate = Instant.now().atZone(ZoneId.systemDefault());

    @LastModifiedDate
    private ZonedDateTime lastModifiedDate = Instant.now().atZone(ZoneId.systemDefault());

    @OneToMany(mappedBy = "subCard", cascade = CascadeType.ALL)
    private List<Todo> todos;
}
