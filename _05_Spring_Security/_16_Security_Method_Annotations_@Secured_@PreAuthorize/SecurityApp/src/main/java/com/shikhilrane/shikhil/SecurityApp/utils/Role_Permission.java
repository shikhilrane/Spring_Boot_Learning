package com.shikhilrane.shikhil.SecurityApp.utils;

import com.shikhilrane.shikhil.SecurityApp.entities.enums.Permission;
import com.shikhilrane.shikhil.SecurityApp.entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Map;

import static com.shikhilrane.shikhil.SecurityApp.entities.enums.Permission.*;
import static com.shikhilrane.shikhil.SecurityApp.entities.enums.Role.*;

public class Role_Permission {
    private static final Map<Role, List<Permission>> map = Map.of(
            USER, List.of(USER_VIEW, POST_VIEW),
            CREATOR, List.of(USER_VIEW, POST_VIEW, POST_CREATE, USER_UPDATE, POST_UPDATE),
            ADMIN, List.of(USER_VIEW, POST_VIEW, POST_CREATE, USER_UPDATE, POST_UPDATE, USER_DELETE, USER_CREATE, POST_DELETE)
    );

    // Return User related to particular Role
    public static List<SimpleGrantedAuthority> getAuthoritiesForRole(Role role){
        return map.get(role).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .toList();
    }
}
