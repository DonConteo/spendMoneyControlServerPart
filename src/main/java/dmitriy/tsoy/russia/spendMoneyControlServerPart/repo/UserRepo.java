package dmitriy.tsoy.russia.spendMoneyControlServerPart.repo;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query(value = "update users set username = :username, age = :age, sex = :sex where id = :id", nativeQuery = true)
    void updateUser(@Param("id") long id,
                    @Param("username") String username,
                    @Param("age") int age,
                    @Param("sex") String sex);
}