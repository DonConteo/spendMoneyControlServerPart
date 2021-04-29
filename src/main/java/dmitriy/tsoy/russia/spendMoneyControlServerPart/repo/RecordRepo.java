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
                    @Param("category") String username,
                    @Param("amount") double age,
                    @Param("comment") String sex);

    @Transactional
    @Query(value="select sum(amount) from records where user_id =:id and date_trunc('month', date) = date_trunc('month', now())", nativeQuery = true)
    double getSpendsForUserThisMonth(@Param("id") long id);

    @Transactional
    @Query(value="select sum(amount) from records where user_id =:id and date_trunc('month', date) = date_trunc('month', current_date - interval '1' month)", nativeQuery = true)
    double getSpendsForUserLastMonth(@Param("id") long id);

    @Transactional
    @Query(value="select sum(amount) from records where user_id =:id", nativeQuery = true)
    double getSpendsForUserAllTime(@Param("id") long id);
}
