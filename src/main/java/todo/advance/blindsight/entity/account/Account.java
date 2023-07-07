package todo.advance.blindsight.entity.account;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import todo.advance.blindsight.entity.role.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Account implements UserDetails {
    @Id
    @Column(name="account_id", length = 50, nullable = false, updatable = false)
    @Size(max = 50, message = "Id required 50 chraracters") // validate size
    private String accountId = "ACC-" + UUID.randomUUID().toString();;

    @Column(name="email", length = 128, nullable = false, unique = true)
    @NotBlank(message="Email is required") // required input
    @Email(regexp="[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}") // validate email must have @
    private String email;

    @Column(name="password", length = 255, nullable = false)
    @NotBlank(message="Password is required") // required input
    @Size(min = 6, message = "Password required at least 6 characters and most 18 characters") // validate size
    private String password;

    @Size(min = 6, message = "OTP required 6 characters") // validate size
    private String codeOTP;

    @Transient
    private String retypePassword;

    @CreatedDate
    private ZonedDateTime createdDate = Instant.now().atZone(ZoneId.systemDefault());

    @LastModifiedDate
    private ZonedDateTime lastModifiedDate = Instant.now().atZone(ZoneId.systemDefault());

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private Set<Role> roles = new HashSet<>();
//=====================================================================================================================================
    // Override method from UserDetails
    @Transient
    private boolean enabled=true;
    @Transient
    private boolean locked;
    @Transient
    private boolean expired;
    @Transient
    private boolean passwordExpired;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
            .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.passwordExpired;
    }

    @Override
    public boolean isEnabled() {
       return this.enabled;
    }
}
