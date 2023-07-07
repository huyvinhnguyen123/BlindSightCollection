package todo.advance.blindsight.entity.todo;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
import todo.advance.blindsight.entity.subCard.SubCard;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Todo {
    @ManyToOne
	@JoinColumn(name = "sub_card_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SubCard subCard; // remember must the same this name with mappedBy

    @Id
    @Column(name = "todo_id", length = 50, updatable = false)
	// @GeneratedValue(strategy = GenerationType.SEQUENCE)
    // @Size(max = 1, message = "Id required 50 chraracters below") // validate size
    private String todoId;

    @Column(name = "todo_description", length = 250)
    private String todoDescription;

    @Column(name = "is_done", nullable = false)
    private boolean isDone;

    @CreatedDate
    private ZonedDateTime createdDate = Instant.now().atZone(ZoneId.systemDefault());

    @LastModifiedDate
    private ZonedDateTime lastModifiedDate = Instant.now().atZone(ZoneId.systemDefault());
}
