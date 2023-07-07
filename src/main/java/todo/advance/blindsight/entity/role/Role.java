package todo.advance.blindsight.entity.role;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Role {
    @ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
    // it appears to be related to serialization in Spring Boot using the Jackson library. 
    // it seems that there is an issue with serializing a collection (possibly a List or Set) in your code.
    @JsonIgnoreProperties("roles") // string 'roles' is a value from Set<Role> roles
	private Account account;

    @Id
    @Column(name = "role_id", length = 50, updatable = false)
    @Size(max = 50, message = "Id required 50 chraracters below") // validate size
    private String roleId = "ROLE-" + UUID.randomUUID().toString();

    @Column(name = "role_name", nullable = false, length = 10)
    @NotBlank(message="role's name is required") // required input
    @Size(max = 10, message = "name required 10 chraracters below") // validate size
    private String roleName;

    @Column(name = "role_code", nullable = false, length = 10)
    private int roleCode;

    @CreatedDate
    private ZonedDateTime createdDate = Instant.now().atZone(ZoneId.systemDefault());

    @LastModifiedDate
    private ZonedDateTime lastModifiedDate = Instant.now().atZone(ZoneId.systemDefault());
}