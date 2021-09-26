package project.blacklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.blacklist.model.AppUser;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> getAppUserByEmail(String email);
    Optional<AppUser> getAppUserByUsername(String username);
    Optional<AppUser> getAppUserByPhoneNumber(String phoneNumber);
}
