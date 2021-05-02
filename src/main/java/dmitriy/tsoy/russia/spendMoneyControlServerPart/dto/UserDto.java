package dmitriy.tsoy.russia.spendMoneyControlServerPart.dto;

import java.util.List;
import java.util.Map;

public class UserDto {

    long id;
    String username;
    int age;
    String sex;
    Map<String, Double> spends;
    List<RecordDto> records;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    public Map<String, Double> getSpends() {
        return spends;
    }
    public void setSpends(Map<String, Double> spends) {
        this.spends = spends;
    }

    public List<RecordDto> getRecords() {
        return records;
    }
    public void setRecords(List<RecordDto> records) {
        this.records = records;
    }

    public UserDto(long id, String username, int age, String sex, Map<String, Double> spends, List<RecordDto> records) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.sex = sex;
        this.spends = spends;
        this.records = records;
    }
}
