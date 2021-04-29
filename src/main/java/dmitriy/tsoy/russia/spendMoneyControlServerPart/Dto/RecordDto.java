package dmitriy.tsoy.russia.spendMoneyControlServerPart.Dto;

import java.util.Date;

public class RecordDto {
    String category;
    double amount;
    String comment;
    Date date;

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

    public RecordDto(String category, double amount, String comment, Date date) {
        this.category = category;
        this.amount = amount;
        this.comment = comment;
        this.date = date;
    }
}
