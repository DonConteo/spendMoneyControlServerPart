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

    /**
     * Get all users from database
     *
     * @return {@code List<User>}
     */
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    /**
     * Find user by id
     *
     * @param id (long) user id
     * @return User
     */
    public Optional<User> getUserById(long id) {
        return userRepo.findById(id);
    }

    /**
     * Save user in the database
     *
     * @param user User
     */
    public void saveUser(User user) {
        userRepo.save(user);
    }

    /**
     * Update existing user by his id. If value of any parameter will be default, the value for this parameter
     * will be taken from database (value leaves without changes)
     *
     * @param id (long) user id
     * @param username (String) default value = ""
     * @param age (int) default value = 0
     * @param sex (String) default value = ""
     */
    public void updateUser(long id, String username, int age, String sex) {
        Optional<User> user = userRepo.findById(id);
        User u = User.newBuilder().
                username(username.equals("") ? user.get().getUsername() : username).
                age(age == 0 ? user.get().getAge() : age).
                sex(sex.equals("") ? user.get().getSex() : sex).build();
        userRepo.updateUser(id, u.getUsername(), u.getAge(), u.getSex());
    }

    /**
     * Delete user by id
     *
     * @param id (long) user id
     */
    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }

    /**
     * Get user Dto by his id. Includes id, username, age, sex, records and spends for current month
     *
     * @param id (long) user id
     * @return UserDto
     */
    public UserDto getUserDto(long id) {
        Optional<User> user = userRepo.findById(id);
        return UserDto.newBuilder().
                id(id).
                username(user.get().getUsername()).
                age(user.get().getAge()).
                sex(user.get().getSex()).
                spends(recordService.getSpendsForUser(id, 0)).
                records(recordService.getRecordDto(id)).build();
    }
}
