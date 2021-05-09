package dmitriy.tsoy.russia.spendMoneyControlServerPart.service;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.dto.RecordDto;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.Record;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.User;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.repo.RecordRepo;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class RecordService {

    @Autowired
    RecordRepo recordRepo;
    @Autowired
    UserRepo userRepo;

    public Optional<Record> getRecordById(long id) {
        return recordRepo.findById(id);
    }

    public List<Record> getAllRecords() {
        return recordRepo.findAll();
    }

    public List<Record> getRecordsForUser(long id) {
        return recordRepo.getRecordsForUser(id);
    }

    public void addRecord(long id, Record record) {
        Optional<User> user = userRepo.findById(id);
        Record r = Record.newBuilder().
                category(record.getCategory()).
                amount(record.getAmount()).
                comment(record.getComment()).
                date(LocalDate.now()).
                user(user.get()).build();
        recordRepo.save(r);
    }

    public void updateRecord(long id, String category, double amount, String comment) {
        Optional<Record> record = recordRepo.findById(id);
        if(category.equals("")) {
            category = record.get().getCategory();
        }
        if(amount == 0.0) {
            amount = record.get().getAmount();
        }
        if(comment.equals("")) {
            comment = record.get().getComment();
        }
        Record r = Record.newBuilder().
                category(category).
                amount(amount).
                comment(comment).
                date(record.get().getDate()).
                user(record.get().getUser()).build();
        recordRepo.updateRecord(id, r.getCategory(), r.getAmount(), r.getComment());
    }

    public void deleteRecord(long id) {
        recordRepo.deleteById(id);
    }

    public Map<String, Double> getSpendsForUser(long id, int period) {
        Map<String, Double> spends = new LinkedHashMap<>();
        LocalDate startDate = LocalDate.now();
        for(int i = 0; i <= period; i++) {
            LocalDate endDate = startDate.minusMonths(i);
            double amount;
            try {
                amount = recordRepo.getSpendsForPeriod(id, endDate.getMonth().getValue(), endDate.getYear());
                spends.put(endDate.getMonth().toString() + ", " + endDate.getYear(), amount);
            } catch (Exception e) {
                amount = 0.0;
                spends.put(endDate.getMonth().toString() + ", " + endDate.getYear(), amount);
            }
        }
        return spends;
    }

    public Map<String, Double> getPredictionForUser(long id) {
        Map<String, Double> spends = new LinkedHashMap<>();
        LocalDate startDate = LocalDate.now();
        for(int i = 1; i <= 3; i++) {
            LocalDate endDate = startDate.minusMonths(i);
            double amount;
            try {
                amount = recordRepo.getSpendsForPeriod(id, endDate.getMonth().getValue(), endDate.getYear());
                spends.put(endDate.getMonth().toString() + ", " + endDate.getYear(), amount);
            } catch (Exception e) {
                amount = 0.0;
                spends.put(endDate.getMonth().toString() + ", " + endDate.getYear(), amount);
            }
        }
        double delimeter = 0;
        double average = 0.0;
        for (Map.Entry<String, Double> entry : spends.entrySet()) {
            average += entry.getValue();
            if (entry.getValue() != 0.0) {
                delimeter++;
            }
        }
        if (delimeter == 0) {
            delimeter = 1;
        }
        average = average/delimeter;
        spends.put("PREDICTION", average);
        return spends;
    }

    public List<RecordDto> getRecordDto(long id) {
        List<RecordDto> recordDtos = new ArrayList<>();
        List<Record> records = recordRepo.getRecordsForUser(id);
        for(Record record : records) {
            RecordDto recordDto = new RecordDto(record.getCategory(), record.getAmount(), record.getComment(), record.getDate());
            recordDtos.add(recordDto);
        }
        recordDtos.sort(Comparator.comparing(RecordDto::getDate));
        return recordDtos;
    }
}
