package kg.itsphere.eco_market.Eco.Market.service.impl;

import org.springframework.stereotype.Service;

@Service
public class PasswordValidationService {

    public boolean validatePassword(String password) {
        // Define your validation rules
        boolean hasMinimumLength = password.length() >= 6;
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasSpecialCharacter = !password.matches("[A-Za-z0-9 ]*");

        // Combine the validation rules
        return hasMinimumLength && hasUppercase && hasLowercase && hasSpecialCharacter;
    }
}