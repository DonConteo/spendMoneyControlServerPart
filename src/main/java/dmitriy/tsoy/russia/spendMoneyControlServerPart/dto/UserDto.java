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
    public String getUsername() {
        return username;
    }
    public int getAge() {
        return age;
    }
    public String getSex() {
        return sex;
    }
    public Map<String, Double> getSpends() {
        return spends;
    }
    public List<RecordDto> getRecords() {
        return records;
    }

    private UserDto() {
    }

    public static UserDto.Builder newBuilder(){
        return new UserDto().new Builder();
    }

    public class Builder {
        public Builder() {
        }

        public UserDto.Builder id(long id) {
            UserDto.this.id = id;
            return this;
        }

        public UserDto.Builder username(String username) {
            UserDto.this.username = username;
            return this;
        }

        public UserDto.Builder age(int age) {
            UserDto.this.age = age;
            return this;
        }

        public UserDto.Builder sex(String sex) {
            UserDto.this.sex = sex;
            return this;
        }

        public UserDto.Builder spends(Map<String, Double> spends) {
            UserDto.this.spends = spends;
            return this;
        }

        public UserDto.Builder records(List<RecordDto> records) {
            UserDto.this.records = records;
            return this;
        }

        public UserDto build() {
            return UserDto.this;
        }
    }
}
