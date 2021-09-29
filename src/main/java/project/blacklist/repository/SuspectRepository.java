package project.blacklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.blacklist.model.Suspect;

@Repository
public interface SuspectRepository extends JpaRepository<Suspect, Long> {
}
