package peaksoft.dao;

import peaksoft.model.User;
import peaksoft.service.UserService;
import peaksoft.service.UserServiceImpl;
import peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {

        private final Connection connection;
        UserService userService = new UserServiceImpl();

    public UserDaoJdbcImpl() throws SQLException {
        connection = new Util().getConnection();
    }

    public void createUsersTable() throws SQLException {
        userService.createUsersTable();
    }
    public void dropUsersTable() {
      userService.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
           userService.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        userService.removeUserById(id);
    }

    public List<User> getAllUsers() {
        String query = ("select * from users");
        List<User>userList=new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte(4));
                userList.add(user);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return userList;
    }

    public void cleanUsersTable() {
userService.cleanUsersTable();
    }

    public boolean existsByFirstName(String firstName) {
        // eger databasede parametrine kelgen firstnamege okshosh adam bar bolso
        // anda true kaitarsyn
        // jok bolso anda false kaitarsyn.
        String query = "select case when count(*)>0  then true else false end from users where name like ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean(1);
            }
            resultSet.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;

    }
}