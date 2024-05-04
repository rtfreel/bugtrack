package rt.bugtrack.core.bug.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rt.bugtrack.core.bug.entity.Step;

@Repository
public interface StepRepository extends JpaRepository<Step, Integer> {
    Optional<Step> findById(Integer id);
    List<Step> findByBugId(Integer id);
}
