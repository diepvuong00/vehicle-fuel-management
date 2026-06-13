package deoca.hhv.transport.driver.repository;

import deoca.hhv.transport.driver.entity.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, String>, JpaSpecificationExecutor<Driver> {

    boolean existsByNationalId(String nationalId);

    Page<Driver> findByDeletedFalse(Pageable pageable);

    Page<Driver> findByDeletedFalseAndFullNameContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );

    Optional<Driver> findByIdAndDeletedFalse(String id);
//    boolean existsByPhone(String phone);

    List<Driver>
    findByDeletedFalse();
}
