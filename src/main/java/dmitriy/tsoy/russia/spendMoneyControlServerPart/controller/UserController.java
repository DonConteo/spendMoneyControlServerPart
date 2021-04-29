package dmitriy.tsoy.russia.spendMoneyControlServerPart.controller;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.User;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable(value="id") long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping()
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>("User successfully created", HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateUser(@PathVariable(value="id") long id,
                                             @RequestParam(value="username", required = false, defaultValue = "") String username,
                                             @RequestParam(value="age", required = false, defaultValue = "0") int age,
                                             @RequestParam(value="sex", required = false, defaultValue = "undefined") String sex) {
        userService.updateUser(id, username, age, sex);
        return new ResponseEntity<>("User successfully updated", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(value="id") long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
    }
}
