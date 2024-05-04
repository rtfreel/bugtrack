package rt.bugtrack.core.bug.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rt.bugtrack.auth.entity.User;
import rt.bugtrack.core.bug.entity.Bug;
import rt.bugtrack.core.bug.util.Status;

@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {
    public List<Bug> findAll();
    public List<Bug> findByReporterAndStatusInAndTitleContainingIgnoreCase(User details, List<Status> statuses, String query);
    public List<Bug> findByProjectIdAndStatusInAndTitleContainingIgnoreCase(Integer id, List<Status> statuses, String query);
    public List<Bug> findByProjectIdAndReporterAndStatusInAndTitleContainingIgnoreCase(Integer id, User details, List<Status> statuses, String query);

    public Optional<Bug> findById(Integer id);
}
