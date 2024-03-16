package kg.itsphere.eco_market.Eco.Market.web.controller;

import kg.itsphere.eco_market.Eco.Market.service.UserService;
import kg.itsphere.eco_market.Eco.Market.web.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/getAll")
    public List<UserResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/findByEmail/{email}")
    public UserResponse findByEmail(@PathVariable String email) {
        return service.findByEmail(email);
    }

    @PatchMapping("/updateRole/{userEmail}")
    public void controlUserRole(@PathVariable String userEmail, @RequestParam Map<String, Object> fields) {
        service.controlUserRole(userEmail, fields);
    }
}
