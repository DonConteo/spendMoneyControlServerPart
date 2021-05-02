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
                date(new Date()).
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
        Map<String, Double> spends = new HashMap<>();
        LocalDate startDate = LocalDate.now();
        for(int i = period; i >= 0; i--) {
            LocalDate endDate = startDate.minusMonths(i);
            String month = String.valueOf(endDate.getMonth());
            double amount = recordRepo.getSpendsForMonth(id, endDate);
            spends.put(month, amount);
        }
        return spends;
    }

//    public Map<String, Double> getSpendsForUser(long id, String period) {
//        Map<String, Double> spends = new HashMap<>();
//        if(period.equalsIgnoreCase("thisMonth")) {
//            try{
//                spends.put("Траты за текущий месяц", recordRepo.getSpendsForUserThisMonth(id));
//            } catch (Exception e) {
//                spends.put("Траты за текущий месяц", 0.0);
//            }
//        }
//        if(period.equalsIgnoreCase("lastMonth")) {
//            try{
//                spends.put("Траты за прошлый месяц", recordRepo.getSpendsForUserLastMonth(id));
//            } catch (Exception e) {
//                spends.put("Траты за прошлый месяц", 0.0);
//            }
//        }
//        if(period.equalsIgnoreCase("allTime")) {
//            try{
//                spends.put("Траты за все время", recordRepo.getSpendsForUserAllTime(id));
//            } catch (Exception e) {
//                spends.put("Траты за все время", 0.0);
//            }
//        }
//        if(period.equalsIgnoreCase("")) {
//            try{
//                spends.put("Траты за текущий месяц", recordRepo.getSpendsForUserThisMonth(id));
//            } catch (Exception e) {
//                spends.put("Траты за текущий месяц", 0.0);
//            }
//            try{
//                spends.put("Траты за прошлый месяц", recordRepo.getSpendsForUserLastMonth(id));
//            } catch (Exception e) {
//                spends.put("Траты за прошлый месяц", 0.0);
//            }
//            try{
//                spends.put("Траты за все время", recordRepo.getSpendsForUserAllTime(id));
//            } catch (Exception e) {
//                spends.put("Траты за все время", 0.0);
//            }
//        }
//        return spends;
//    }
//
//    public Map<String, Double> getSpendsForUser(long id, int period) {
//        Map<String, Double> spends = new HashMap<>();
//        try {
//            spends.put("Траты за запрошенный период", recordRepo.getSpendsForUser(id, period));
//        } catch (Exception e) {
//            spends.put("Указанный период недействителен", 0.0);
//        }
//        return spends;
//    }

    public List<RecordDto> getRecordDto(long id) {
        List<RecordDto> recordDtos = new ArrayList<>();
        List<Record> records = recordRepo.getRecordsForUser(id);
        for(Record record : records) {
            RecordDto recordDto = new RecordDto(record.getCategory(), record.getAmount(), record.getComment(), record.getDate());
            recordDtos.add(recordDto);
        }
        return recordDtos;
    }
}
