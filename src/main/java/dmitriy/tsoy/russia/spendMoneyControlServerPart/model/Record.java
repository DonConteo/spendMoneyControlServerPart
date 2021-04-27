package dmitriy.tsoy.russia.spendMoneyControlServerPart.model;

import javax.persistence.*;
import java.util.Date;

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
    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Record(String category, double amount, String comment, Date date, User user) {
        this.category = category;
        this.amount = amount;
        this.comment = comment;
        this.date = date;
        this.user = user;
    }

    public Record() {
    }
}
