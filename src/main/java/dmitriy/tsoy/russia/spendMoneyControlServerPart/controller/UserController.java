package dmitriy.tsoy.russia.spendMoneyControlServerPart.controller;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.User;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("save/{username}/{age}/{sex}")
    public ResponseEntity<Boolean> saveUser(@PathVariable String username,
                                         @PathVariable int age,
                                         @PathVariable char sex) {
        userService.saveUser(username, age, sex);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
