package project.blacklist.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.blacklist.dto.AppUser;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> getAppUserById(Long id);
    Optional<AppUser> getAppUserByEmailAndUsernameAndPhoneNumber(String email, String username, Long phoneNumber);
    Optional<AppUser> getAppUserByEmail(String email);
    Optional<AppUser> getAppUserByUsername(String username);
    Optional<AppUser> getAppUserByPhoneNumber(Long phoneNumber);
}
