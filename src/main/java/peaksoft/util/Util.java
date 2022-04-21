package peaksoft.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
private final Connection connection;

    public Util() throws SQLException {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres");
        }
    public Connection getConnection() {
        // ushul methoddgo connectino kaitarynyzdar
        return connection;
    }
}
