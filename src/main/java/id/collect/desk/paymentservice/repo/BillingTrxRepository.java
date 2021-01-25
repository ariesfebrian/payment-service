package id.collect.desk.paymentservice.repo;

import id.collect.desk.paymentservice.entity.BillingTrx;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingTrxRepository extends CrudRepository<BillingTrx, Long> {
}
