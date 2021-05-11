package dmitriy.tsoy.russia.spendMoneyControlServerPart.controller;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.service.RecordService;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.Record;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("records")
public class RecordController {

    @Autowired
    RecordService recordService;
    @Autowired
    UserService userService;

    /**
     * This method allows you to get all records from database
     * <br>URI is /records
     * <br>method GET
     *
     * @return {@code List<Record>}
     */
    @GetMapping()
    public ResponseEntity<List<Record>> getAllRecords() {
        List<Record> records = recordService.getAllRecords();
        return records.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(records, HttpStatus.OK);
    }

    /**
     * This method allows you to get all records for user by his id
     * <br>URI is /records/{id}
     * <br>method GET
     *
     * @param id (long) user id
     * @return {@code List<Record>}
     */
    @GetMapping("{id}")
    public ResponseEntity getRecordsForUser(@PathVariable(value="id") long id) {
        if (userService.getUserById(id).isPresent()) {
            List<Record> records = recordService.getRecordsForUser(id);
            return records.isEmpty()
                    ? new ResponseEntity<>("User has no records", HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(records, HttpStatus.OK);
        }
        return new ResponseEntity<>("User doesn't exists", HttpStatus.NOT_FOUND);
    }

    /**
     * Adding a record for user
     * <br>URI is /records/{id}
     * <br>method POST
     *
     * @param id (long) user id
     * @param record record info
     */
    @PostMapping("{id}")
    public ResponseEntity<String> addRecord(@PathVariable(value="id") long id,
                                            @RequestBody Record record) {
        if (userService.getUserById(id).isPresent()) {
            recordService.addRecord(id, record);
            return recordService.getRecordById(record.getId()).isPresent()
                    ? new ResponseEntity<>("Record successfully added", HttpStatus.CREATED)
                    : new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<>("User doesn't exists", HttpStatus.NOT_FOUND);
    }

    /**
     * Method for update existing record by id
     * If you want leave any parameter without changes, do not write it
     * <br>URI is /records/{id}?category={"new category"}&amount={new amount}&comment={"new comment"}
     * <br>method PUT
     *
     * @param id (long) id of record to update
     * @param category (String) new category
     * @param amount (double) new amount
     * @param comment (String) new comment
     */
    @PutMapping("{id}")
    public ResponseEntity<String> updateRecord(@PathVariable(value="id") long id,
                                             @RequestParam(value="category", required = false, defaultValue = "") String category,
                                             @RequestParam(value="amount", required = false, defaultValue = "0.0") double amount,
                                             @RequestParam(value="comment", required = false, defaultValue = "") String comment) {
        Optional<Record> record = recordService.getRecordById(id);
        if (record.isPresent()) {
            recordService.updateRecord(id, category, amount, comment);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Method for delete record by id
     * <br>URI is /records/{id}
     * <br>method DELETE
     *
     * @param id (long) id of record to delete
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRecord(@PathVariable(value="id") long id) {
        if (recordService.getRecordById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        recordService.deleteRecord(id);
        Optional<Record> record = recordService.getRecordById(id);
        return record.isEmpty()
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Method to get spends per month for user in the giving time-frame starts from current month.
     * If you want to get info about current month only, leave field "month" empty.
     * <br>URI is /records/{id}/spends&month={quantity of months}
     * <br>method GET
     *
     * @param id (long) user id
     * @param period (int) quantity of months
     * @return {@code Map<String, Double>}
     */
    @GetMapping("{id}/spends")
    public ResponseEntity<Map<String, Double>> getSpendsForUser(@PathVariable(value="id") long id,
                                                                @RequestParam(value="month", required = false, defaultValue = "0") int period) {
        return userService.getUserById(id).isPresent()
                ? new ResponseEntity<>(recordService.getSpendsForUser(id, period), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Method to get prediction of spends for user for the next month. Prediction is based on spends
     * of three last months
     * <br>URI is /records/{id}/prediction
     * <br>method GET
     *
     * @param id (long) user id
     * @return {@code Map<String, Double>}
     */
    @GetMapping("{id}/prediction")
    public ResponseEntity<Map<String, Double>> getPredictionForUser(@PathVariable(value="id") long id) {
        return userService.getUserById(id).isPresent()
                ? new ResponseEntity<>(recordService.getPredictionForUser(id), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
