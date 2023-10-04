package Blind.Sight.Commnunity.domain.entity;

import Blind.Sight.Commnunity.util.common.DeleteFlag;
import Blind.Sight.Commnunity.util.common.LockFlag;
import com.google.common.collect.Lists;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "Users")
public class User implements UserDetails {
    @Id
    @Column(name = "user_id", updatable = false)
    private String userId;
    @Column(unique = true, nullable = false)
    private String userName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
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

    public User() {
        this.userId = UUID.randomUUID().toString();
    }

    public User(String userName, String email, String role) {
        this.userId = UUID.randomUUID().toString();
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
