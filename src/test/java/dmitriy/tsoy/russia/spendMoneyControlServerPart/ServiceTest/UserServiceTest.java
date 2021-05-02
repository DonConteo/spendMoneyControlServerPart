package dmitriy.tsoy.russia.spendMoneyControlServerPart.ServiceTest;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.dto.UserDto;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.User;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.service.RecordService;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    RecordService recordService;

    @Test
    @DisplayName("get person by id")
    void getUserByIdTest() {
        assertEquals(userService.getUserById(1).get().getUsername(), "DonConteo");
    }

//    @Test
//    @DisplayName("save user")
//    void saveUserTest() {
//        User user = new User();
//        user.setUsername("Vasya");
//        user.setAge(25);
//        user.setSex("male");
//        userService.saveUser(user);
//        assertEquals(userService.getUserById(user.getId()).get().getUsername(), "Vasya");
//    }

    @Test
    @DisplayName("update user")
    void updateUserTest() {
        userService.updateUser(8, "Petya", 30, "male");
        assertEquals(userService.getUserById(8).get().getUsername(), "Petya");
    }

    @Test
    @DisplayName("delete user")
    void deleteUserTest() {
        userService.deleteUser(8);
        assertEquals(userService.getUserById(8), Optional.empty());
    }

    @Test
    @DisplayName("get user dto")
    void getUserDtoTest() {
        UserDto userDto = userService.getUserDto(1);
        assertEquals(userDto.getUsername(), "DonConteo");
        assertEquals(userDto.getRecords().get(0).getCategory(), "табак");
        assertEquals(userDto.getRecords().get(0).getAmount(), 120.0);
    }
}
