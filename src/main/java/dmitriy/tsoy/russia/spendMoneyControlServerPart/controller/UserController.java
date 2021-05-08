package dmitriy.tsoy.russia.spendMoneyControlServerPart.controller;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.dto.UserDto;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.Record;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.User;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable(value="id") long id) {
        Optional<User> user = userService.getUserById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return userService.getUserById(user.getId()) != null
                ? new ResponseEntity<>("User saved successfully", HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateUser(@PathVariable(value="id") long id,
                                             @RequestParam(value="username", required = false, defaultValue = "") String username,
                                             @RequestParam(value="age", required = false, defaultValue = "0") int age,
                                             @RequestParam(value="sex", required = false, defaultValue = "") String sex) {
        if (userService.getUserById(id) != null) {
            userService.updateUser(id, username, age, sex);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(value="id") long id) {
        if (userService.getUserById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            userService.deleteUser(id);
            Optional<User> user = userService.getUserById(id);
            return user == null
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("{id}/info")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable(value="id") long id) {
        Optional<User> user = userService.getUserById(id);
        return user != null
                ? new ResponseEntity<>(userService.getUserDto(id), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
