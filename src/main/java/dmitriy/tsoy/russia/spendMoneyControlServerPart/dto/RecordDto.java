package dmitriy.tsoy.russia.spendMoneyControlServerPart.dto;

import java.time.LocalDate;

public class RecordDto {
    String category;
    double amount;
    String comment;
    LocalDate date;

    public String getCategory() {
        return category;
    }
    public double getAmount() {
        return amount;
    }
    public String getComment() {
        return comment;
    }
    public LocalDate getDate() {
        return date;
    }

    private RecordDto() {
    }

    public static RecordDto.Builder newBuilder(){
        return new RecordDto().new Builder();
    }

    public class Builder {
        public Builder() {
        }

        public RecordDto.Builder category(String category) {
            RecordDto.this.category = category;
            return this;
        }

        public RecordDto.Builder amount(double amount) {
            RecordDto.this.amount = amount;
            return this;
        }

        public RecordDto.Builder comment(String comment) {
            RecordDto.this.comment = comment;
            return this;
        }

        public RecordDto.Builder date(LocalDate date) {
            RecordDto.this.date = date;
            return this;
        }

        public RecordDto build() {
            return RecordDto.this;
        }
    }
}
