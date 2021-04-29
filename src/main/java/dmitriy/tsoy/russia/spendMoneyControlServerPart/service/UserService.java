package dmitriy.tsoy.russia.spendMoneyControlServerPart.service;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.UpdateUserDto;
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
        UpdateUserDto uud = new UpdateUserDto();
        if(!name.equals("")) {
            uud.setUsername(name);
        } else {
            uud.setUsername(user.get().getUsername());
        }
        if(age != 0) {
            uud.setAge(age);
        } else {
            uud.setAge(user.get().getAge());
        }
        if(!sex.equals("undefined")) {
            uud.setSex(sex);
        } else {
            uud.setSex(user.get().getSex());
        }
        userRepo.updateUser(id, uud.getUsername(), uud.getAge(), uud.getSex());
    }

    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }
}
