package kg.itsphere.eco_market.Eco.Market.domain.entity.user;

import jakarta.persistence.*;
import kg.itsphere.eco_market.Eco.Market.domain.entity.enums.Role;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Busket;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Order;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private Role role;
//    @OneToOne
//    private Busket busket;
//    @OneToMany
//    private List<Order> order;
}
