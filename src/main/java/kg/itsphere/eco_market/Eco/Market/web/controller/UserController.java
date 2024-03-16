package kg.itsphere.eco_market.Eco.Market.web.controller;

import kg.itsphere.eco_market.Eco.Market.service.UserService;
import kg.itsphere.eco_market.Eco.Market.web.dto.user.UserRequest;
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

    // it's for admin to see every user data
    @GetMapping("/getAll")
    public List<UserResponse> getAll() {
        return service.getAll();
    }

    // it's for admin to see one person data
    @GetMapping("/findByEmail/{email}")
    public UserResponse findByEmail(@PathVariable String email) {
        return service.findByEmail(email);
    }

    // it's for admin to change the role of users. Admin can give to others role "admin"
    @PatchMapping("/updateRole/{userEmail}")
    public void controlUserRole(@PathVariable String userEmail, @RequestParam Map<String, Object> fields) {
        service.controlUserRole(userEmail, fields);
    }

    // it's just for testing
    @PostMapping("/register")
    public void register(@RequestBody UserRequest userRequest) {
        service.register(userRequest);
    }
}
