package birthdays.model;


import java.util.Date;


public class Person {
    private String name;
    private Date date;
    private String present;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getPresent() { return present; }
    public void setPresent(String present) { this.present = present; }

    public String toString() { return name + "|" + date + "|" + present; }
}
