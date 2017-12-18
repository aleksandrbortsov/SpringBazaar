package com.springbazaar.service.security;

import com.springbazaar.domain.Role;
import com.springbazaar.domain.User;
import com.springbazaar.domain.type.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class SecurityUserDetails extends User implements UserDetails {

    public SecurityUserDetails(User user) {
        if (user == null) return;
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
//        this.setCreatedDate(user.getCreatedDate());
//        this.setLastModifiedDate(user.getLastModifiedDate());
        this.setRoles(user.getRoles());
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : super.getRoles()) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(RoleType.findByRole(role));
            authorities.add(authority);
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


}
