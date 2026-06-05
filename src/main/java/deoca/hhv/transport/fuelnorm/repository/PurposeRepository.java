package deoca.hhv.transport.fuelnorm.repository;

import deoca.hhv.transport.fuelnorm.entity.Purpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurposeRepository extends JpaRepository<Purpose, String> {

    boolean existsByCode(String code);

    boolean existsByName(String name);
}
