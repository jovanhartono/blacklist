package project.blacklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.blacklist.model.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> getRoleByRoleID(Long roleID);
    Optional<Role> getRoleByType(String type);
}
