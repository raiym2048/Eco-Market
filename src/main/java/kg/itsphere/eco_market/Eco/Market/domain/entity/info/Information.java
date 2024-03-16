package kg.itsphere.eco_market.Eco.Market.domain.entity.info;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "info_tb")
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String infoText;
    private String phoneNumber;
    private String whatsapp;
    private String instagram;
}
