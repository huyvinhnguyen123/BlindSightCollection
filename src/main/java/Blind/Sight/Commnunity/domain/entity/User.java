package Blind.Sight.Commnunity.domain.entity;

import Blind.Sight.Commnunity.util.common.DeleteFlag;
import Blind.Sight.Commnunity.util.common.LockFlag;
import com.google.common.collect.Lists;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User implements UserDetails {
    @Id
    @Column(name = "user_id", updatable = false)
    private String userId = UUID.randomUUID().toString();
    @Column(unique = true, nullable = false)
    private String userName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column
    private LocalDate birthDate;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String role;

    // For prepare deleting user
    @Column(nullable = false, columnDefinition = "TINYINT")
    private int logFlag;
    @Column(nullable = false, columnDefinition = "TINYINT")
    private int deleteFlag;
    @Column(name = "old_login_id")
    private String oldLoginId;

    public User(String userId) {
        this.userId = userId;
    }

    public User(String userName, String email, String role) {
        this.userName = userName;
        this.email = email;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (StringUtils.hasText(role)) {
            return Lists.newArrayList(new SimpleGrantedAuthority(role));
        } else {
            return Lists.newArrayList();
        }
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return logFlag == LockFlag.NON_LOCK.getCode();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return deleteFlag == DeleteFlag.NON_DELETE.getCode();
    }
}
