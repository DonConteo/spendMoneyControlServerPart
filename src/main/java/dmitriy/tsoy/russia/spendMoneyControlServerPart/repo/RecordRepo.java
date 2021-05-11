package dmitriy.tsoy.russia.spendMoneyControlServerPart.repo;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RecordRepo extends JpaRepository<Record, Long> {

    @Transactional
    @Query(value="select * from records where user_id =:id", nativeQuery = true)
    List<Record> getRecordsForUser(@Param("id")long id);

    @Modifying
    @Transactional
    @Query(value = "update records set category = :category, amount = :amount, comment = :comment where id = :id", nativeQuery = true)
    void updateRecord(@Param("id") long id,
                    @Param("category") String category,
                    @Param("amount") double amount,
                    @Param("comment") String comment);

    @Transactional
    @Query(value="select sum(amount) from records where user_id =:id and extract(month from date) =:month and extract(year from date) =:year", nativeQuery = true)
    double getSpendsForPeriod(@Param("id") long id, @Param("month") int month, @Param("year") int year);
}