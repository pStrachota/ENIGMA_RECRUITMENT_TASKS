package pl.strachota.task2.service.interfaces;

import org.springframework.data.jpa.domain.Specification;
import pl.strachota.task2.dto.user.CreateUserDTO;
import pl.strachota.task2.dto.user.UpdateUserDTO;
import pl.strachota.task2.model.User;

import java.util.List;


public interface UserService {

    List<User> getAllUsers(Specification<User> spec, int pageNo, String sortBy,
                           String sortDir);

    User getUserById(Long id);

    User createUser(CreateUserDTO createUserDTO);

    void deleteUser(Long id);

    User updateUser(Long id, UpdateUserDTO updatedUser);

}