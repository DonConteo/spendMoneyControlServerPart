package dmitriy.tsoy.russia.spendMoneyControlServerPart.service;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.dto.UserDto;
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

    @Autowired
    RecordService recordService;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> getUserById(long id) {
        return userRepo.findById(id);
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void updateUser(long id, String username, int age, String sex) {
        Optional<User> user = userRepo.findById(id);
        if(username.equals("")) {
            username = user.get().getUsername();
        }
        if(age == 0) {
            age = user.get().getAge();
        }
        if(sex.equals("")) {
            sex = user.get().getSex();
        }
        User u = User.newBuilder().
                username(username).
                age(age).
                sex(sex).build();
        userRepo.updateUser(id, u.getUsername(), u.getAge(), u.getSex());
    }

    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }

    public UserDto getUserDto(long id) {
        Optional<User> user = userRepo.findById(id);
        UserDto userDto = new UserDto(id,
                                        user.get().getUsername(),
                                        user.get().getAge(),
                                        user.get().getSex(),
                                        recordService.getSpendsForUser(id, 0),
                                        recordService.getRecordDto(id));
        return userDto;
    }
}
