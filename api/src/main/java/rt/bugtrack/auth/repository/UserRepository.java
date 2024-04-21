package rt.bugtrack.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rt.bugtrack.auth.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> findAll();
    public Optional<User> findByUsername(String username);
}
