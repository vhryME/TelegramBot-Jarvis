package birthdays.dao;


import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;


public interface PersonDAO {
    void setDataSource(DataSource dataSource);

    void add(String name, Date date, String present);

    void delete(String name);

    void update(String name, Date date, String present);

    ArrayList personList();
}
