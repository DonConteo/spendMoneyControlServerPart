package dmitriy.tsoy.russia.spendMoneyControlServerPart.ServiceTest;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.dto.RecordDto;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.Record;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.service.RecordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RecordServiceTest {

    @Autowired
    RecordService recordService;

    @Test
    @DisplayName("get record by id")
    void getRecordByIdTest() {
        assertEquals(recordService.getRecordById(2).get().getCategory(), "продукты питания");
    }

    @Test
    @DisplayName("get all records")
    void getAllRecordsTest() {
        List<Record> records = recordService.getAllRecords();
        assertEquals(records.get(1).getCategory(), "продукты питания");
    }

    @Test
    @DisplayName("get records for user")
    void getRecordsForUserTest() {
        List<Record> records = recordService.getRecordsForUser(2);
        assertEquals(records.size(), 5);
    }

    @Test
    @DisplayName("update record")
    void updateRecordTest() {
        recordService.updateRecord(4, "табак", 0, "");
        assertEquals(recordService.getRecordById(4).get().getCategory(), "табак");
        assertEquals(recordService.getRecordById(4).get().getAmount(), 150);
        assertEquals(recordService.getRecordById(4).get().getComment(), "уголь");
        recordService.updateRecord(4, "прочее", 0, "");
    }

    @Test
    @DisplayName("get spends for user")
    void getSpendsForUserTest() {
        Map<String, Double> map = recordService.getSpendsForUser(2, 3);
        assertEquals(map.size() == 4, true);
        assertEquals(map.get("MAY, 2021"), 280.0);
    }

    @Test
    @DisplayName("get prediction for user")
    void getPredictionForUser() {
        Map<String, Double> map = recordService.getPredictionForUser(2);
        assertEquals(map.get("PREDICTION"), 2249.3333333333335);
    }

    @Test
    @DisplayName("get record dto")
    void getRecordDtoTest() {
        List<RecordDto> list = recordService.getRecordDto(2);
        assertEquals(list.size(), 5);
        assertEquals(list.get(0).getCategory(), "спорт");
    }
}
