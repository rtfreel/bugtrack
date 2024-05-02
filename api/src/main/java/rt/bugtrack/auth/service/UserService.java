package rt.bugtrack.auth.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rt.bugtrack.auth.dto.UserDTO;
import rt.bugtrack.auth.entity.User;
import rt.bugtrack.auth.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public Optional<User> getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User createUser(User user) {
        if (repository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        return repository.save(user);
    }

    public UserDTO getUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
