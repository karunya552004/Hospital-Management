package hospital;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    static final String url =
            "jdbc:mysql://localhost:3306/hospital_db";

    static final String user = "root";
    static final String pass = "Karunya#5504";

    public static Connection getConnection()
            throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(url, user, pass);
    }
}