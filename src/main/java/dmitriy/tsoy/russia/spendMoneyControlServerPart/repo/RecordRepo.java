package dmitriy.tsoy.russia.spendMoneyControlServerPart.repo;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepo extends JpaRepository<Record, Long> {
}
