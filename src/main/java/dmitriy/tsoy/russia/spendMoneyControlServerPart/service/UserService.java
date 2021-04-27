package dmitriy.tsoy.russia.spendMoneyControlServerPart.service;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.User;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public void saveUser(String username, int age, char sex) {
        User user = new User();
        user.setUsername(username);
        user.setAge(age);
        user.setSex(sex);
        userRepo.save(user);
    }

    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }
}
