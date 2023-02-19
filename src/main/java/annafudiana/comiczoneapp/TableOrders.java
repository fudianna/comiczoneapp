package annafudiana.comiczoneapp;

import java.util.Date;

public class TableOrders {
    private String bookCode;
    private String bookName;
    private Date DateBorrow;
    private Date DateReturn;
    private int price;

    public TableOrders(String bookCode, String bookName, Date DateBorrow, Date DateReturn, int price) {
        this.bookCode = bookCode;
        this.bookName = bookName;
        this.DateBorrow = DateBorrow;
        this.DateReturn = DateReturn;
        this.price = price;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getDateBorrow() {
        return DateBorrow;
    }

    public void setBorrow(Date DateBorrow) {
        this.DateBorrow = DateBorrow;
    }

    public Date getDateReturn() {
        return DateReturn;
    }

    public void setDateReturn(Date DateReturn) {
        this.DateReturn = DateReturn;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
