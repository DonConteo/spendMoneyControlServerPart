package dmitriy.tsoy.russia.spendMoneyControlServerPart.dto;

import java.time.LocalDate;
import java.util.Date;

public class RecordDto {
    String category;
    double amount;
    String comment;
    LocalDate date;

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

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public RecordDto(String category, double amount, String comment, LocalDate date) {
        this.category = category;
        this.amount = amount;
        this.comment = comment;
        this.date = date;
    }
}
