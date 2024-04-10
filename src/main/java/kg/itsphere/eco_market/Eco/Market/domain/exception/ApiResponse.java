package kg.itsphere.eco_market.Eco.Market.domain.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private String message;

    public ApiResponse(String message) {
        this.message = message;
    }
}
