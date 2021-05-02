package dmitriy.tsoy.russia.spendMoneyControlServerPart.model;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String username;
    @NotNull
    private int age;
    @NotNull
    private String sex;

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

    private User() {
    }

    public static User.Builder newBuilder(){
        return new User().new Builder();
    }

    public class Builder {

        public Builder() {
        }

        public User.Builder username(String username){
            User.this.username = username;
            return this;
        }

        public User.Builder age(int age){
            User.this.age = age;
            return this;
        }

        public User.Builder sex(String sex) {
            User.this.sex = sex;
            return this;
        }

        public User build() {
            return User.this;
        }
    }
}
