package dmitriy.tsoy.russia.spendMoneyControlServerPart.controller;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.service.RecordService;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("records")
public class RecordController {

    @Autowired
    RecordService recordService;

    @GetMapping()
    public ResponseEntity<List<Record>> getAllRecords() {
        return ResponseEntity.ok(recordService.getAllRecords());
    }

    @GetMapping("{id}")
    public ResponseEntity<List<Record>> getRecordsForUser(@PathVariable(value="id") long id) {
        return ResponseEntity.ok(recordService.getRecordsForUser(id));
    }

    @PostMapping("{id}")
    public ResponseEntity<String> addRecord(@PathVariable(value="id") long id,
                                            @RequestBody Record record) {
        recordService.addRecord(id, record);
        return new ResponseEntity<>("Record successfully added", HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateRecord(@PathVariable(value="id") long id,
                                             @RequestParam(value="category", required = false, defaultValue = "") String category,
                                             @RequestParam(value="amount", required = false, defaultValue = "0.0") double amount,
                                             @RequestParam(value="comment", required = false, defaultValue = "") String comment) {
        recordService.updateRecord(id, category, amount, comment);
        return new ResponseEntity<>("Record successfully updated", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRecord(@PathVariable(value="id") long id) {
        recordService.deleteRecord(id);
        return new ResponseEntity<>("Record successfully deleted", HttpStatus.OK);
    }
//
//    @GetMapping("{id}/spends/month")
//    public ResponseEntity<Map<String, Double>> getSpendsForUser(@PathVariable(value="id") long id,
//                                                                @RequestParam(value="period", required = false, defaultValue = "0") int period) {
//        return ResponseEntity.ok(recordService.getSpendsForUser(id, period));
//    }

    @GetMapping("{id}/spends")
    public ResponseEntity<Map<String, Double>> getSpendsForUserInPeriod(@PathVariable(value="id") long id,
                                                                @RequestParam(value="period", required = false, defaultValue = "0") int period) {
        return ResponseEntity.ok(recordService.getSpendsForUser(id, period));
    }
}
