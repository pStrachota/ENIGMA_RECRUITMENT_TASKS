package pl.strachota.task2.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.strachota.task2.dto.user.CreateUserDTO;
import pl.strachota.task2.dto.user.UpdateUserDTO;
import pl.strachota.task2.model.User;
import pl.strachota.task2.repository.UserRepository;
import pl.strachota.task2.service.interfaces.UserService;
import pl.strachota.task2.util.mapper.UserMapper;

import java.util.List;

import static pl.strachota.task2.util.properties.AppConstants.PAGE_SIZE;


@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<User> getAllUsers(Specification<User> spec, int pageNo, String sortBy,
                                  String sortDir) {
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, sortDir.equalsIgnoreCase(
                Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending());
        Page<User> pagedResult = userRepository.findAll(spec, pageable);
        return pagedResult.getContent();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User createUser(CreateUserDTO createUserDTO) {
        User user = userMapper.mapToEntity(createUserDTO);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id))
            throw new RuntimeException("User not found");
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(Long id, UpdateUserDTO updatedUser) {
        User user = this.getUserById(id);
        user.setFirstName(updatedUser.getFirstName() != null ? updatedUser.getFirstName() : user.getFirstName());
        user.setLastName(updatedUser.getLastName() != null ? updatedUser.getLastName() : user.getLastName());
        user.setEmail(updatedUser.getEmail() != null ? updatedUser.getEmail() : user.getEmail());
        return userRepository.save(user);
    }

}