package dmitriy.tsoy.russia.spendMoneyControlServerPart.service;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.dto.RecordDto;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.Record;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.User;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.repo.RecordRepo;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class RecordService {

    @Autowired
    RecordRepo recordRepo;
    @Autowired
    UserRepo userRepo;

    /**
     * Find record by id
     *
     * @param id (long) record id
     * @return Record
     */
    public Optional<Record> getRecordById(long id) {
        return recordRepo.findById(id);
    }

    /**
     * Get all records from database
     *
     * @return {@code List<Record>}
     */
    public List<Record> getAllRecords() {
        return recordRepo.findAll();
    }

    /**
     * Get all records for chosen user by his id
     *
     * @param id (long) user id
     * @return {@code List<Record>}
     */
    public List<Record> getRecordsForUser(long id) {
        return recordRepo.getRecordsForUser(id);
    }

    /**
     * Add record to user by his id
     *
     * @param id (long) user id
     * @param record Record
     */
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

    /**
     * Update record by id. If value of any parameter will be default, the value for this parameter
     * will be taken from database (value leaves without changes)
     *
     * @param id (long) record id
     * @param category (String) default value = ""
     * @param amount (double) default value = 0.0
     * @param comment (String) default value = ""
     */
    public void updateRecord(long id, String category, double amount, String comment) {
        Optional<Record> record = recordRepo.findById(id);
        Record r = Record.newBuilder().
                category(category.equals("") ? record.get().getCategory() : category).
                amount(amount == 0.0 ? record.get().getAmount() : amount).
                comment(comment.equals("") ? record.get().getComment() : comment).
                date(record.get().getDate()).
                user(record.get().getUser()).build();
        recordRepo.updateRecord(id, r.getCategory(), r.getAmount(), r.getComment());
    }

    /**
     * Delete record by id
     *
     * @param id (long) record id
     */
    public void deleteRecord(long id) {
        recordRepo.deleteById(id);
    }

    /**
     * Get spends per month for user in the giving time-frame starts from current month. Returns {@code Map<Month, Spends>}
     *
     * @param id (long) user id
     * @param period (int) quantity of months. Default value = 0
     * @return {@code Map<String, Double>}
     */
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

    /**
     * Get prediction of spends for user for the next month. Prediction is based on spends
     * of three last months. Current month do not includes in the calculations. Months without
     * spends will not includes as well.
     * <br>In example: if user has spends only for two last months, the sum of spends will be
     * divided by 2
     *
     * @param id (long) user id
     * @return {@code Map<String, Double>}
     */
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
        delimeter = (delimeter == 0) ? 1 : delimeter;
        average = average/delimeter;
        spends.put("PREDICTION", average);
        return spends;
    }

    /**
     * Get record Dto for user by his id
     *
     * @param id (long) user id
     * @return {@code List<RecordDto>}
     */
    public List<RecordDto> getRecordDto(long id) {
        List<RecordDto> recordDtos = new ArrayList<>();
        List<Record> records = recordRepo.getRecordsForUser(id);
        for(Record record : records) {
            RecordDto recordDto = RecordDto.newBuilder().
                    category(record.getCategory()).
                    amount(record.getAmount()).
                    comment((record.getComment())).
                    date(record.getDate()).build();
            recordDtos.add(recordDto);
        }
        recordDtos.sort(Comparator.comparing(RecordDto::getDate));
        return recordDtos;
    }
}
