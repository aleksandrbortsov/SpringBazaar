package com.springbazaar.domain;

import com.springbazaar.domain.util.type.RoleType;
import lombok.*;
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
@ToString
// TODO sort out with @EqualsAndHashCode(callSuper = false)
public class Role implements Serializable, GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "NAME", unique = true, nullable = false)
    private RoleType roleType;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> user;

//TODO add table and entity (example https://github.com/cuba-platform/cuba/blob/00ceaf9a022f4f997f29c4a2951c0ca9f7900fc0/modules/global/src/com/haulmont/cuba/security/entity/Permission.java)
//    private List<Permission> permissions;

    public Role(RoleType roleType) {
        this.roleType = roleType;
    }

    @Override
    public String getAuthority() {
        return roleType.name();
    }
}
