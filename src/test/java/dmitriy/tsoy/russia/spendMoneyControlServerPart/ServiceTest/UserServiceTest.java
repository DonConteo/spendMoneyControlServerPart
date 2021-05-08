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
        assertEquals(userService.getUserById(2).get().getUsername(), "Test User");
    }

    @Test
    @DisplayName("update user")
    void updateUserTest() {
        userService.updateUser(2, "Petya", 30, "male");
        assertEquals(userService.getUserById(2).get().getUsername(), "Petya");
        userService.updateUser(2, "Test User", 25, "male");
    }

    @Test
    @DisplayName("get user dto")
    void getUserDtoTest() {
        UserDto userDto = userService.getUserDto(2);
        assertEquals(userDto.getUsername(), "Test User");
        assertEquals(userDto.getRecords().get(0).getCategory(), "спорт");
        assertEquals(userDto.getRecords().get(2).getComment(), "хлеб");
        assertEquals(userDto.getRecords().get(1).getAmount(), 1700.0);
    }
}
