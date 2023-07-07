package todo.advance.blindsight.entity.profile;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import todo.advance.blindsight.entity.account.Account;
import todo.advance.blindsight.entity.subCard.SubCard;
import todo.advance.blindsight.util.generate.date.DateGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Profile {
    @OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties("profile")
	private Account account;

    @Id
	@Column(name = "profile_id", length = 50, updatable = false)
    @Size(max = 50, message = "Id required 50 chraracters below") // validate size
	private String profileId = "PRO-" + UUID.randomUUID().toString();

	@Column(name = "username", length = 50, updatable = false)
    @Size(max = 50, message = "name required 50 chraracters below") // validate size
	private String username;

    @Column(name = "avatar", length = 255, updatable = false)
    @Size(max = 255, message = "name required 255 chraracters below") // validate size
	private String avatar;

    @Column(name = "bio", length = 200, updatable = false)
    @Size(max = 200, message = "bio required 200 chraracters below") // validate size
    private String bio;

    @Column(name = "date_join", nullable = false)
	private String dateJoin = DateGenerator.getCurrentDateWithoutHMS();

    @CreatedDate
    private ZonedDateTime createdDate = Instant.now().atZone(ZoneId.systemDefault());

    @LastModifiedDate
    private ZonedDateTime lastModifiedDate = Instant.now().atZone(ZoneId.systemDefault());

    @ManyToMany(mappedBy = "profiles")
    private List<SubCard> subcards;
}
