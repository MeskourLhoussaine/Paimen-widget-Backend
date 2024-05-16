package ma.m2t.chaabipay.repositories;

import ma.m2t.chaabipay.entites.Role;
import ma.m2t.chaabipay.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}