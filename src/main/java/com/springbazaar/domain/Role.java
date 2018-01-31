package com.springbazaar.domain;

import com.springbazaar.domain.util.type.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Set;

@Entity
@Table(name = "SB_ROLES")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "user")
// TODO sort out with @EqualsAndHashCode(callSuper = false)
public class Role implements Serializable, GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "NAME", unique = true, nullable = false)
    private RoleType roleType;

    private boolean isDefaultRole;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "SB_ROLES_PERMISSIONS",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private Set<Permission> permissions;

    public Role(RoleType roleType) {
        this.roleType = roleType;
    }

    @Override
    public String getAuthority() {
        return permissions.toString();
    }
}
