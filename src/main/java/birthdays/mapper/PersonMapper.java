package birthdays.mapper;


import birthdays.model.Person;

import javax.swing.tree.RowMapper;
import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PersonMapper implements RowMapper {
    @Override
    public int[] getRowsForPaths(TreePath[] path) {
        return new int[0];
    }

    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Person person = new Person();

        person.setName(resultSet.getString("name"));
        person.setDate(resultSet.getDate("date"));
        person.setPresent(resultSet.getString("present"));

        return person;
    }
}
