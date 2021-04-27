package dmitriy.tsoy.russia.spendMoneyControlServerPart.repo;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
}