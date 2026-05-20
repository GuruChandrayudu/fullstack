import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentDAO {

    public void addStudent(Student s) {

        try {

            Connection con = DBConnection.getConnection();

            // Check Duplicate ID
            String checkQuery = "SELECT * FROM students WHERE id=?";

            PreparedStatement checkPs = con.prepareStatement(checkQuery);

            checkPs.setInt(1, s.id);

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {

                System.out.println("Student ID already exists");
                return;
            }

            String query = "INSERT INTO students VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, s.id);
            ps.setString(2, s.name);
            ps.setString(3, s.department);
            ps.setInt(4, s.marks);

            ps.executeUpdate();

            System.out.println("Student Added Successfully");

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    public void viewStudents() {

        try {

            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM students";

            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                System.out.println(
                        rs.getInt(1) + " " +
                                rs.getString(2) + " " +
                                rs.getString(3) + " " +
                                rs.getInt(4));
            }

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    public void updateStudent(int id, int marks) {

        try {

            Connection con = DBConnection.getConnection();

            String query = "UPDATE students SET marks=? WHERE id=?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, marks);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                System.out.println("Student Updated Successfully");

            } else {

                System.out.println("Student Not Found");
            }

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    public void deleteStudent(int id) {

        try {

            Connection con = DBConnection.getConnection();

            String query = "DELETE FROM students WHERE id=?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                System.out.println("Student Deleted Successfully");

            } else {

                System.out.println("Student Not Found");
            }

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    public void searchStudent(int id) {

        try {

            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM students WHERE id=?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println(
                        rs.getInt(1) + " " +
                                rs.getString(2) + " " +
                                rs.getString(3) + " " +
                                rs.getInt(4));

            } else {

                System.out.println("Student Not Found");
            }

        } catch (Exception e) {

            System.out.println(e);
        }
    }
}