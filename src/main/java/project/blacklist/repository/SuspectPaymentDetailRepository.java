package project.blacklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.blacklist.model.SuspectPaymentDetail;

public interface SuspectPaymentDetailRepository extends JpaRepository<SuspectPaymentDetail, Long> {
}
