package peaksoft.service;

import peaksoft.dao.UserDao;
import peaksoft.dao.UserDaoJdbcImpl;
import peaksoft.model.User;
import peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final Connection connection;


    public UserServiceImpl() throws SQLException {
        connection = new Util().getConnection();
    }

    public void createUsersTable() throws SQLException {
        String query = ("""
                create table if not exists users (
                id serial primary key,
                name varchar not null,
                last_name varchar not null,
                age smallint not null
                );    
                """);

        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            System.out.println("You're table created successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String query = ("""
                drop table  if exists users
                """);
        try (Statement statement = connection.createStatement();) {
            statement.execute(query);
            System.out.println("You're table deleted successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = ("""
                insert into users (name, last_name, age) values (?, ?, ?);
                """);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            System.out.println(name + " saved successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void removeUserById(long id) {
        String query = """
                delete  from users where id = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public List<User> getAllUsers() {
        String query = ("select * from users");
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte(4));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }

    public void cleanUsersTable() {
        String query = ("""
                truncate table users;
                """);
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            System.out.println("You're table cleaned successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public boolean existsByFirstName(String firstName) {
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
