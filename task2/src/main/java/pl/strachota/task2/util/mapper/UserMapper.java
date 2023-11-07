package pl.strachota.task2.util.mapper;

import org.springframework.stereotype.Component;
import pl.strachota.task2.dto.user.CreateUserDTO;
import pl.strachota.task2.model.User;

@Component
public class UserMapper {
    public CreateUserDTO mapToDTO(User user) {
        return CreateUserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    public User mapToEntity(CreateUserDTO createUserDTO) {
        return User.builder()
                .firstName(createUserDTO.getFirstName())
                .lastName(createUserDTO.getLastName())
                .email(createUserDTO.getEmail())
                .build();
    }
}