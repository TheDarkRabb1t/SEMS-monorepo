package tdr.pet.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdr.pet.authorization.model.entity.CustomUser;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {
    Optional<CustomUser> findByUsername(String username);
}
