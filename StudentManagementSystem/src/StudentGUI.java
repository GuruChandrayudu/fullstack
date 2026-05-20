import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentGUI extends JFrame implements ActionListener {

    JLabel l1, l2, l3, l4;

    JTextField t1, t2, t3, t4;

    JButton addBtn, viewBtn, updateBtn, deleteBtn, searchBtn;

    JTextArea area;

    JScrollPane sp;

    Connection con;

    Font f;

    public StudentGUI() {

        setTitle("Student Management System");

        setSize(600, 550);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(240, 248, 255));

        f = new Font("Arial", Font.BOLD, 16);

        // ID
        l1 = new JLabel("ID");
        l1.setBounds(50, 30, 100, 30);
        l1.setFont(f);
        add(l1);

        t1 = new JTextField();
        t1.setBounds(180, 30, 250, 30);
        add(t1);

        // Name
        l2 = new JLabel("Name");
        l2.setBounds(50, 80, 100, 30);
        l2.setFont(f);
        add(l2);

        t2 = new JTextField();
        t2.setBounds(180, 80, 250, 30);
        add(t2);

        // Department
        l3 = new JLabel("Department");
        l3.setBounds(50, 130, 120, 30);
        l3.setFont(f);
        add(l3);

        t3 = new JTextField();
        t3.setBounds(180, 130, 250, 30);
        add(t3);

        // Marks
        l4 = new JLabel("Marks");
        l4.setBounds(50, 180, 100, 30);
        l4.setFont(f);
        add(l4);

        t4 = new JTextField();
        t4.setBounds(180, 180, 250, 30);
        add(t4);

        // Buttons
        addBtn = new JButton("Add");
        addBtn.setBounds(30, 250, 100, 35);
        add(addBtn);

        viewBtn = new JButton("View");
        viewBtn.setBounds(140, 250, 100, 35);
        add(viewBtn);

        updateBtn = new JButton("Update");
        updateBtn.setBounds(250, 250, 100, 35);
        add(updateBtn);

        deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(360, 250, 100, 35);
        add(deleteBtn);

        searchBtn = new JButton("Search");
        searchBtn.setBounds(470, 250, 100, 35);
        add(searchBtn);

        // Text Area
        area = new JTextArea();

        area.setFont(new Font("Monospaced", Font.PLAIN, 14));

        sp = new JScrollPane(area);

        sp.setBounds(50, 320, 500, 150);

        add(sp);

        // Button Events
        addBtn.addActionListener(this);
        viewBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        searchBtn.addActionListener(this);

        // Connect Database
        connectDB();

        setVisible(true);
    }

    // Database Connection
    public void connectDB() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/studentdb",
                    "root",
                    "Mysql@51");

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    // Button Actions
    public void actionPerformed(ActionEvent e) {

        try {

            // VIEW STUDENTS
            if (e.getSource() == viewBtn) {

                String query = "SELECT * FROM students";

                PreparedStatement ps = con.prepareStatement(query);

                ResultSet rs = ps.executeQuery();

                area.setText("");

                while (rs.next()) {

                    area.append(
                            rs.getInt(1) + "    " +
                                    rs.getString(2) + "    " +
                                    rs.getString(3) + "    " +
                                    rs.getInt(4) + "\n");
                }
            }

            // SEARCH STUDENT
            else if (e.getSource() == searchBtn) {

                int id = Integer.parseInt(t1.getText());

                String query = "SELECT * FROM students WHERE id=?";

                PreparedStatement ps = con.prepareStatement(query);

                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    area.setText(
                            rs.getInt(1) + "    " +
                                    rs.getString(2) + "    " +
                                    rs.getString(3) + "    " +
                                    rs.getInt(4));

                } else {

                    area.setText("Student Not Found");
                }
            }

            // ADD STUDENT
            else if (e.getSource() == addBtn) {

                int id = Integer.parseInt(t1.getText());
                String name = t2.getText();
                String dept = t3.getText();
                int marks = Integer.parseInt(t4.getText());

                // Validate Marks
                if (marks < 0 || marks > 100) {

                    area.setText("Invalid Marks");
                    return;
                }

                // Check Duplicate ID
                String checkQuery = "SELECT * FROM students WHERE id=?";

                PreparedStatement checkPs = con.prepareStatement(checkQuery);

                checkPs.setInt(1, id);

                ResultSet checkRs = checkPs.executeQuery();

                if (checkRs.next()) {

                    area.setText("Student ID already exists");
                    return;
                }

                String query = "INSERT INTO students VALUES (?, ?, ?, ?)";

                PreparedStatement ps = con.prepareStatement(query);

                ps.setInt(1, id);
                ps.setString(2, name);
                ps.setString(3, dept);
                ps.setInt(4, marks);

                ps.executeUpdate();

                area.setText("Student Added Successfully");

                clearFields();
            }

            // UPDATE STUDENT
            else if (e.getSource() == updateBtn) {

                int id = Integer.parseInt(t1.getText());

                int marks = Integer.parseInt(t4.getText());

                String query = "UPDATE students SET marks=? WHERE id=?";

                PreparedStatement ps = con.prepareStatement(query);

                ps.setInt(1, marks);
                ps.setInt(2, id);

                int rows = ps.executeUpdate();

                if (rows > 0) {

                    area.setText("Student Updated Successfully");

                } else {

                    area.setText("Student Not Found");
                }

                clearFields();
            }

            // DELETE STUDENT
            else if (e.getSource() == deleteBtn) {

                int id = Integer.parseInt(t1.getText());

                String query = "DELETE FROM students WHERE id=?";

                PreparedStatement ps = con.prepareStatement(query);

                ps.setInt(1, id);

                int rows = ps.executeUpdate();

                if (rows > 0) {

                    area.setText("Student Deleted Successfully");

                } else {

                    area.setText("Student Not Found");
                }

                clearFields();
            }

        } catch (Exception ex) {

            area.setText(ex.toString());
        }
    }

    // Clear Fields
    public void clearFields() {

        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
    }

    // Main Method
    public static void main(String[] args) {

        new StudentGUI();
    }
}