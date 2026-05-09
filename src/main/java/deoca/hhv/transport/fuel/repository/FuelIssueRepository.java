package deoca.hhv.transport.fuel.repository;

import deoca.hhv.transport.fuel.entity.FuelIssue;
import deoca.hhv.transport.fuel.enums.FuelIssueStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelIssueRepository extends JpaRepository<FuelIssue, String> {

//    Page<FuelIssue> findByStatus(
//            FuelIssueStatus status,
//            Pageable pageable
//    );
}
