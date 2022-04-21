package peaksoft;

import peaksoft.service.UserService;
import peaksoft.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserService underTest = new UserServiceImpl();

        underTest.createUsersTable();

//         underTest.saveUser("Will","Smith", (byte) 40);
//        underTest.saveUser("Zamir","Sabyrjanov", (byte) 28);
//        underTest.saveUser("Muhammed","Allanov", (byte) 21);
//        underTest.saveUser("Bekzhan","Rakhmankulov", (byte) 20);
       // System.out.println(underTest.getAllUsers());
        //underTest.removeUserById(1);
        underTest.cleanUsersTable();



    }
}
