package id.collect.desk.paymentservice.repo;

import id.collect.desk.paymentservice.entity.Collector;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectorRepository extends CrudRepository<Collector, Long> {
}
