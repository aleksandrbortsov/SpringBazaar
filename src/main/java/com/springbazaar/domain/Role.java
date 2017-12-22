package com.springbazaar.domain;

import com.springbazaar.domain.util.type.RoleType;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Set;

@Entity
@Table(name = "SB_ROLES")
public class Role implements Serializable, GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "NAME", unique = true, nullable = false)
    private RoleType roleType;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> user;

//TODO add table and entity (example https://github.com/cuba-platform/cuba/blob/00ceaf9a022f4f997f29c4a2951c0ca9f7900fc0/modules/global/src/com/haulmont/cuba/security/entity/Permission.java)
//    private List<Permission> permissions;

    public Role() {
    }

    public Role(RoleType roleType) {
        this.roleType = roleType;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }

    //TODO equals , hashCode, toString methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (id != null ? !id.equals(role.id) : role.id != null) return false;
        if (roleType != role.roleType) return false;
        return false; //createdWhen != null ? createdDate.equals(role.createdDate) : role.createdDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (roleType != null ? roleType.hashCode() : 0);
//        result = 31 * result + (createdWhen != null ? createdWhen.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Role{" +
                "ID=" + id +
                ", Role type =" + roleType +
                '}';
    }

    @Override
    public String getAuthority() {
        return roleType.name();
    }
}
