package dmitriy.tsoy.russia.spendMoneyControlServerPart.controller;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.User;
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

    @GetMapping()
    public ResponseEntity<List<Record>> getAllRecords() {
        List<Record> records = recordService.getAllRecords();
        return records.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(records, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<Record>> getRecordsForUser(@PathVariable(value="id") long id) {
        Optional<User> user = userService.getUserById(id);
        if (user != null) {
            List<Record> records = recordService.getRecordsForUser(id);
            return records.isEmpty()
                    ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(records, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("{id}")
    public ResponseEntity<String> addRecord(@PathVariable(value="id") long id,
                                            @RequestBody Record record) {
        if (userService.getUserById(id) != null) {
            recordService.addRecord(id, record);
            Optional<Record> r = recordService.getRecordById(record.getId());
            return r != null
                    ? new ResponseEntity<>("Record successfully added", HttpStatus.CREATED)
                    : new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        } else {
            return new ResponseEntity<>("User doesn't exists", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateRecord(@PathVariable(value="id") long id,
                                             @RequestParam(value="category", required = false, defaultValue = "") String category,
                                             @RequestParam(value="amount", required = false, defaultValue = "0.0") double amount,
                                             @RequestParam(value="comment", required = false, defaultValue = "") String comment) {
        Optional<Record> record = recordService.getRecordById(id);
        if (record != null) {
            recordService.updateRecord(id, category, amount, comment);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRecord(@PathVariable(value="id") long id) {
        if (recordService.getRecordById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            recordService.deleteRecord(id);
            Optional<Record> record = recordService.getRecordById(id);
            return record == null
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("{id}/spends")
    public ResponseEntity<Map<String, Double>> getSpendsForUser(@PathVariable(value="id") long id,
                                                                @RequestParam(value="month", required = false, defaultValue = "0") int period) {
        return userService.getUserById(id) != null
                ? new ResponseEntity<>(recordService.getSpendsForUser(id, period), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("{id}/prediction")
    public ResponseEntity<Map<String, Double>> getPredictionForUser(@PathVariable(value="id") long id) {
        return userService.getUserById(id) != null
                ? new ResponseEntity<>(recordService.getPredictionForUser(id), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
