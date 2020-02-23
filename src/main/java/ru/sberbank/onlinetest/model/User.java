package ru.sberbank.onlinetest.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, isGetterVisibility = NONE, setterVisibility = NONE)
public class User extends AbstractBaseEntity implements UserDetails {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 6, max = 100)
    private String username;

    @Email
    @NotBlank
    @Size(min = 5, max = 50)
    private String email;

    @NotBlank
    @Size(min = 6, max = 50)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank
    @Size(min = 6, max = 255)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean enabled;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Role> roles;

    public User(Integer id, @NotBlank @Size(min = 6, max = 100) String username,
                @Email @NotBlank @Size(min = 5, max = 50) String email,
                @NotBlank @Size(min = 6, max = 50) String password,
                @NotBlank @Size(min = 6, max = 255) String name, Role role, Role... roles) {
        super(id);
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.enabled = true;
        this.roles = EnumSet.of(role, roles);
    }

    public User() {
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
