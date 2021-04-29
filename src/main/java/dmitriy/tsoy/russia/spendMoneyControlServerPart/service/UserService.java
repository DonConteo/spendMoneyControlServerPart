package dmitriy.tsoy.russia.spendMoneyControlServerPart.service;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.User;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> getUserById(long id) {
        return userRepo.findById(id);
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void updateUser(long id, String name, int age, String sex) {
        Optional<User> user = userRepo.findById(id);
        if(!name.equals("")) {
            user.get().setUsername(name);
        }
        if(age != 0) {
            user.get().setAge(age);
        }
        if(!sex.equals("undefined")) {
            user.get().setSex(sex);
        }
        userRepo.updateUser(id, user.get().getUsername(), user.get().getAge(), user.get().getSex());
    }

    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }
}
