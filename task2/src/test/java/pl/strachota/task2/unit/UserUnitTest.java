package pl.strachota.task2.unit;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.strachota.task2.dto.user.CreateUserDTO;
import pl.strachota.task2.dto.user.UpdateUserDTO;
import pl.strachota.task2.exception.UserNotFoundException;
import pl.strachota.task2.model.User;
import pl.strachota.task2.repository.UserRepository;
import pl.strachota.task2.service.impl.UserServiceImpl;
import pl.strachota.task2.util.mapper.UserMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUnitTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> users = List.of(new User(), new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.getAllUsers(null, 0, "id", "asc");

        // Assert
        assertThat(result).hasSize(3);
        verify(userRepository).findAll();
    }

    @Test
    void shouldGetUserById() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(userId);

        // Assert
        assertThat(result).isNotNull();
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowUserNotFoundException_WhenGettingNonexistentUser() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        ThrowableAssert.ThrowingCallable callable = () -> userService.getUserById(userId);
        assertThatThrownBy(callable).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldCreateUserWhenParametersAreValid() {
        // Arrange
        CreateUserDTO createUserDTO = new CreateUserDTO();
        User user = new User();
        when(userMapper.mapToEntity(createUserDTO)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User result = userService.createUser(createUserDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowUserNotFoundException_WhenUpdatingNonexistentUser() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        ThrowableAssert.ThrowingCallable callable = () -> userService.updateUser(userId, new UpdateUserDTO());
        assertThatThrownBy(callable).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldUpdateUserWhenParametersAreValid() {
        // Arrange
        Long userId = 1L;
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User result = userService.updateUser(userId, updateUserDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldDeleteUser() {
        // Arrange
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository).deleteById(userId);
    }

    @Test
    void shouldThrowUserNotFoundException_WhenDeletingNonexistentUser() {
        // Arrange
        Long userId = 1L;

        // Act and Assert
        ThrowableAssert.ThrowingCallable callable = () -> userService.deleteUser(userId);
        assertThatThrownBy(callable).isInstanceOf(UserNotFoundException.class);
    }

}
