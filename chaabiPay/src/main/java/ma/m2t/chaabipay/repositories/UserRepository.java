package ma.m2t.chaabipay.repositories;

import ma.m2t.chaabipay.entites.User;
import ma.m2t.chaabipay.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_ADMIN'")
    List<User> findAdmin();

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_MODERATOR'")
    List<User> findMod();

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_USER'")
    List<User> findUser();

    @Query("SELECT r.name FROM User u JOIN u.roles r WHERE u.username=?1")
    ERole findRole(String username);

    @Query("SELECT u.roles FROM User u WHERE u.username = ?1")
    List<ERole> findRolesByUsername(String username);

    void deleteUsersById(Long id);


    boolean existsByEmail(String email); // Ajoutez cette méthode


}