package project.blacklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.blacklist.model.SuspectPaymentDetail;

@Repository
public interface SuspectPaymentDetailRepository extends JpaRepository<SuspectPaymentDetail, Long> {
}
