package rt.bugtrack.core.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import rt.bugtrack.auth.entity.User;
import rt.bugtrack.core.project.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    public List<Project> findAll();
    public List<Project> findByTitleContainingIgnoreCase(String title);
    public List<Project> findByOwner(User user);
    public List<Project> findByOwnerAndTitleContainingIgnoreCase(User details, String query);

    public Optional<Project> findById(Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE Project p SET p.title = :title, p.description = :description WHERE p.id = :id")
    public void updateProject(
        @Param("id") Integer id,
        @Param("title") String title,
        @Param("description") String description);
}
