package dmitriy.tsoy.russia.spendMoneyControlServerPart.ServiceTest;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.User;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.Record;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.service.RecordService;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

@SpringBootTest
class UserServiceTest {

    @Autowired
    static
    UserService userService;
    @Autowired
    static
    RecordService recordService;

    @BeforeAll
    public static void Setup() {
        User user1 = new User("Vasya", 23, "male");
        User user2 = new User("Petya", 30, "male");
        User user3 = new User("Marina", 15, "female");
        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        Record record1 = new Record("табак", 120.0, "LD красный", new Date(), user1);
        Record record2 = new Record("продукты питания", 549.69, "Еда", new Date(), user1);
        Record record3 = new Record("продукты питания", 1150.23, "Здоровая еда", new Date(), user2);
        Record record4 = new Record("спорт", 5500.0, "Тренажерка", new Date(), user2);
        Record record5 = new Record("украшения", 4890.0, "Духи", new Date(), user3);
        Record record6 = new Record("спорт", 8000.0, "Фитнес", new Date(), user3);
        recordService.addRecord(user1.getId(), record1);
        recordService.addRecord(user1.getId(), record2);
        recordService.addRecord(user2.getId(), record3);
        recordService.addRecord(user2.getId(), record4);
        recordService.addRecord(user3.getId(), record5);
        recordService.addRecord(user3.getId(), record6);
    }

    @Test
    @DisplayName("get person by id")
    void getUserByIdTest() {
        assertEquals(userService.getUserById(1).get().getUsername(), "Vasya");
    }
}
