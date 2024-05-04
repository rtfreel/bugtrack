package rt.bugtrack.core.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rt.bugtrack.auth.entity.User;
import rt.bugtrack.core.project.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    public List<Project> findByTitleContainingIgnoreCase(String title);
    public List<Project> findByOwnerAndTitleContainingIgnoreCase(User details, String query);

    public Optional<Project> findById(Integer id);
}
