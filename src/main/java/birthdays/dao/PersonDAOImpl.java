package birthdays.dao;


import birthdays.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;


//Не доделан метод personList()
public class PersonDAOImpl implements PersonDAO {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate();
    }

    @Override
    public void add(String name, Date date, String present) {
        String INSERT = "INSERT INTO birthdays (name, date, present) VALUES (?, ?, ?)";

        jdbcTemplate.update(INSERT, name, date, present);
    }

    @Override
    public void delete(String name) {
        String DELETE = "DELETE FROM birthdays WHERE name = ?";

        jdbcTemplate.update(DELETE, name);
    }

    @Override
    public void update(String name, Date date, String present) {
        String UPDATE = "UPDATE birthdays SET name = ?, date = ?, present = ?";

        jdbcTemplate.update(UPDATE, name, date, present);
    }

    @Override
    public ArrayList personList() {
        ArrayList persons = new ArrayList();

        String SELECT = "SELECT * FROM birthdays WHERE GETDATE() - date < 7";

        int personsI = jdbcTemplate.update(SELECT, new PersonMapper());

        return persons;
    }
}
