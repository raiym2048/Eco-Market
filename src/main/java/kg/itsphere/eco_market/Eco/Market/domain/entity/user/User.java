package kg.itsphere.eco_market.Eco.Market.domain.entity.user;

import jakarta.persistence.*;
import kg.itsphere.eco_market.Eco.Market.domain.entity.enums.Role;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Basket;

import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Order;
import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User implements  UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String orientation;
    private String comment;
    private String verifyCode;
    private Boolean verified = false;
    private String uuid;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne
    private Basket basket;
    @OneToMany
    private List<Order> orders;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null)
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_DEFAULT"));
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }
    @Override
    public String getUsername() {
        return email;
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
