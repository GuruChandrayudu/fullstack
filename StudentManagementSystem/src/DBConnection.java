import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    static Connection con;

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/studentdb",
                    "root",
                    "YOUR_PASSWORD");// Replace YOUR_PASSWORD with your local MySQL password

        } catch (Exception e) {

            System.out.println(e);
        }

        return con;
    }
}