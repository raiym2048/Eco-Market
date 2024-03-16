package kg.itsphere.eco_market.Eco.Market.web.mapper.impl;

import kg.itsphere.eco_market.Eco.Market.domain.entity.user.User;
import kg.itsphere.eco_market.Eco.Market.web.dto.user.UserRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.user.UserResponse;
import kg.itsphere.eco_market.Eco.Market.web.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserResponse toDto(User user) {
        return null;
    }

    @Override
    public List<UserResponse> toDtoS(List<User> userList) {
        return null;
    }

    @Override
    public User toDtoUser(User user, UserRequest userRequest) {
        return null;
    }
}
