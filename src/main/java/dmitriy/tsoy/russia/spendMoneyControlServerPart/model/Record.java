package dmitriy.tsoy.russia.spendMoneyControlServerPart.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name="records")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String category;
    private double amount;
    private String comment;
    private Date date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }
    public double getAmount() {
        return amount;
    }
    public String getComment() {
        return comment;
    }
    public Date getDate() {
        return date;
    }
    public User getUser() {
        return user;
    }

    private Record() {
    }

    public static Builder newBuilder(){
        return new Record().new Builder();
    }

    public class Builder {

        public Builder() {
        }

        public Builder category(String category){
            Record.this.category = category;
            return this;
        }

        public Builder amount(double amount){
            Record.this.amount = amount;
            return this;
        }

        public Builder comment(String comment) {
            Record.this.comment = comment;
            return this;
        }

        public Builder date(Date date) {
            Record.this.date = date;
            return this;
        }

        public Builder user(User user) {
            Record.this.user = user;
            return this;
        }

        public Record build() {
            return Record.this;
        }
    }
}
