package kg.itsphere.eco_market.Eco.Market.domain.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ExceptionResponse {

    private HttpStatus httpStatus;

    private String message;

    public ExceptionResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}