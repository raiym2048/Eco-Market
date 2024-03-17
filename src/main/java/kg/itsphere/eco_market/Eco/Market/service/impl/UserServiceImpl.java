package kg.itsphere.eco_market.Eco.Market.service.impl;

import kg.itsphere.eco_market.Eco.Market.domain.entity.enums.Role;
import kg.itsphere.eco_market.Eco.Market.domain.entity.user.User;
import kg.itsphere.eco_market.Eco.Market.domain.exception.BadCredentialsException;
import kg.itsphere.eco_market.Eco.Market.domain.exception.NotFoundException;
import kg.itsphere.eco_market.Eco.Market.repository.UserRepository;
import kg.itsphere.eco_market.Eco.Market.service.UserService;
import kg.itsphere.eco_market.Eco.Market.web.dto.user.UserRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.user.UserResponse;
import kg.itsphere.eco_market.Eco.Market.web.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public List<UserResponse> getAll() {
        return userMapper.toDtoS(userRepository.findAll());
    }

    @Override
    public UserResponse findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        checker(user, email);
        return userMapper.toDto(user.get());
    }

    @Override
    public void controlUserRole(String userEmail, Map<String, Object> fields) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        checker(user, userEmail);
        fields.forEach((key, value) -> {
            if(key.equals("role")) {
                user.get().setRole(Role.valueOf(value.toString()));
            }
        });
        userRepository.save(user.get());
    }

    @Override
    public void register(UserRequest userRequest) {
        if(userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new BadCredentialsException("User with email \"" + userRequest.getEmail() + "\" is already exist");
        }
        User user = new User();
        userRepository.save(userMapper.toDtoUser(user, userRequest));
    }

    private void checker(Optional<User> user, String email) {
        if(user.isEmpty()) {
            throw new NotFoundException("User with email \"" + email + "\" not found", HttpStatus.NOT_FOUND);
        }
    }
}
