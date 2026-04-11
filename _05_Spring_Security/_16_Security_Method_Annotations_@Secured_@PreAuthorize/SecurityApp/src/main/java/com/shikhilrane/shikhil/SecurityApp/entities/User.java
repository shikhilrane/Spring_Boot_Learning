package com.shikhilrane.shikhil.SecurityApp.entities;

import com.shikhilrane.shikhil.SecurityApp.entities.enums.Permission;
import com.shikhilrane.shikhil.SecurityApp.entities.enums.Role;
import com.shikhilrane.shikhil.SecurityApp.utils.Role_Permission;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    private String password;

//    private Role role;  // One User can have one role

    @ElementCollection(fetch = FetchType.EAGER)   // Roles will fetch directly when we fetch users because we want to fetch its roles as soon as we fetch user
    @Enumerated(EnumType.STRING)                  // Actual value of enum string will be given
    private List<Role> roles;                     // One user can have multiple roles

    // Commented : After Creating Role_Permission util class (Because not going to save in DB and directly mapping from Role_Permission class)
//    @ElementCollection(fetch = FetchType.EAGER)   // Roles will fetch directly when we fetch users because we want to fetch its roles as soon as we fetch user
//    @Enumerated(EnumType.STRING)                  // Actual value of enum string will be given
//    private List<Permission> permissions;         // One user can have multiple permissions

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Commented : After Creating Role_Permission util class
//        Set<SimpleGrantedAuthority> authorities = roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
//                .collect(Collectors.toSet());
//
//        permissions.forEach(
//                permission -> authorities.add(new SimpleGrantedAuthority(permission.name()))    // Iterate to add permissions to each Role
//        );
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(                      // User ke har role par loop chal raha hai
                role -> {
                    List<SimpleGrantedAuthority> permissions = Role_Permission.getAuthoritiesForRole(role); // Us role se related permissions (authorities) nikali ja rahi hain
                    authorities.addAll(permissions);    // Saari permissions authorities set me add kar di
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name())); // Role ko bhi authority bana kar add kiya (Spring Security ke liye)
                }
        );
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
