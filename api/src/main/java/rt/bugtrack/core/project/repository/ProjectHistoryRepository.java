package rt.bugtrack.core.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rt.bugtrack.core.project.entity.ProjectHistory;

@Repository
public interface ProjectHistoryRepository extends JpaRepository<ProjectHistory, Integer> {
    public List<ProjectHistory> findByProjectIdOrderByChangeTimeDesc(Integer projectId);
}
