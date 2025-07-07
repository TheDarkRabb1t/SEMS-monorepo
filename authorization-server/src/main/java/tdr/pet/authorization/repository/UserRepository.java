package tdr.pet.authorization.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tdr.pet.authorization.model.entity.CustomUser;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<CustomUser, UUID> {
    Optional<CustomUser> findByUsername(String username);
}
