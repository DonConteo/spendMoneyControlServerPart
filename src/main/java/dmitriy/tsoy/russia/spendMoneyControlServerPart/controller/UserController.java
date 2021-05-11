package dmitriy.tsoy.russia.spendMoneyControlServerPart.controller;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.dto.UserDto;
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

    /**
     * This method allows you to get all users from database
     * <br>URI is /users
     * <br>method GET
     *
     * @return {@code List<User>}
     */
    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Find user by id
     * <br>URI is /users/{id}
     * <br>method GET
     *
     * @param id (long) id of user to search
     * @return User
     */
    @GetMapping("{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable(value="id") long id) {
        Optional<User> user = userService.getUserById(id);
        return user.isPresent()
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Method for saving user in the database
     * <br>URI is /users
     * <br>method POST
     *
     * @param user User info
     */
    @PostMapping()
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return userService.getUserById(user.getId()).isPresent()
                ? new ResponseEntity<>("User saved successfully", HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Method for update user by id
     * If you want leave any parameter without changes, do not write it
     * <br>URI is /users/{id}?username={"new username"}&age={new age}&sex={"new sex"}
     * <br>method PUT
     *
     * @param id (long) id of user to update
     * @param username (String) new username
     * @param age (int) new age
     * @param sex (String) new sex
     */
    @PutMapping("{id}")
    public ResponseEntity<String> updateUser(@PathVariable(value="id") long id,
                                             @RequestParam(value="username", required = false, defaultValue = "") String username,
                                             @RequestParam(value="age", required = false, defaultValue = "0") int age,
                                             @RequestParam(value="sex", required = false, defaultValue = "") String sex) {
        if (userService.getUserById(id).isPresent()) {
            userService.updateUser(id, username, age, sex);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Delete user by id
     * <br>URI is /users/id
     * <br>method DELETE
     *
     * @param id (long) id of user to delete
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(value="id") long id) {
        if (userService.getUserById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.deleteUser(id);
        Optional<User> user = userService.getUserById(id);
        return user.isEmpty()
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Method for get info about user like id, username, age, sex, records and spends for current month
     * <br>URI is /users/{id}/info
     * <br>method GET
     *
     * @param id (long) id of user to search
     * @return UserDto
     */
    @GetMapping("{id}/info")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable(value="id") long id) {
        Optional<User> user = userService.getUserById(id);
        return user.isPresent()
                ? new ResponseEntity<>(userService.getUserDto(id), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
