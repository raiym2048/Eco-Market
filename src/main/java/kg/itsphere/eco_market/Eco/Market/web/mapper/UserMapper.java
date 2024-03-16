package kg.itsphere.eco_market.Eco.Market.web.mapper;

import kg.itsphere.eco_market.Eco.Market.domain.entity.user.User;
import kg.itsphere.eco_market.Eco.Market.web.dto.user.UserRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.user.UserResponse;

import java.util.List;

public interface UserMapper {
    UserResponse toDto(User user);
    List<UserResponse> toDtoS(List<User> userList);
    User toDtoUser(User user, UserRequest userRequest);
}
