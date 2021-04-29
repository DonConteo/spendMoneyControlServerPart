package dmitriy.tsoy.russia.spendMoneyControlServerPart.service;

import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.Record;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.model.User;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.repo.RecordRepo;
import dmitriy.tsoy.russia.spendMoneyControlServerPart.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        record.setDate(new Date());
        record.setUser(user.get());
        recordRepo.save(record);
    }

    public void updateRecord(long id, String category, double amount, String comment) {
        Optional<Record> record = recordRepo.findById(id);
        if(!category.equals("")) {
            record.get().setCategory(category);
        }
        if(amount != 0.0) {
            record.get().setAmount(amount);
        }
        if(!comment.equals("")) {
            record.get().setComment(comment);
        }
        recordRepo.updateRecord(id, record.get().getCategory(), record.get().getAmount(), record.get().getComment());
    }

    public void deleteRecord(long id) {
        recordRepo.deleteById(id);
    }
}
