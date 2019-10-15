package alteca.fr.demojwt.Repository;

import alteca.fr.demojwt.domain.Role;
import alteca.fr.demojwt.domain.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
