package taodigital.interview.jpa.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taodigital.interview.jpa.demo.entity.AppUser;
import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findByUsernameAndPassword(String username, String password);
}
