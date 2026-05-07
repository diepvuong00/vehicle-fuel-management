package deoca.hhv.transport.driver.repository;

import deoca.hhv.transport.driver.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, String> {

    boolean existsByNationalId(String nationalId);

//    boolean existsByPhone(String phone);
}
