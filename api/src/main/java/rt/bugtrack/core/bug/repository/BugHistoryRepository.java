package rt.bugtrack.core.bug.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rt.bugtrack.core.bug.entity.BugHistory;

@Repository
public interface BugHistoryRepository extends JpaRepository<BugHistory, Integer> {
    public List<BugHistory> findByBugIdOrderByChangeTimeDesc(Integer bugId);

    public List<BugHistory> findByBugId(Integer id);
}
