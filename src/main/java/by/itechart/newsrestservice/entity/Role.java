package by.itechart.newsrestservice.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public enum Role {
    ROLE_USER, ROLE_ADMIN, ROLE_EDITOR;

    public Collection<? extends GrantedAuthority> getAuthority() {
        return List.of(new SimpleGrantedAuthority(name()));
    }
}
