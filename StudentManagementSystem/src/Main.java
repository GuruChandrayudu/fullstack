import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        StudentDAO dao = new StudentDAO();

        while (true) {

            System.out.println("\n1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Search Student");
            System.out.println("6. Exit");

            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();

                    System.out.print("Enter Marks: ");
                    int marks = sc.nextInt();

                    if (marks < 0 || marks > 100) {

                        System.out.println("Invalid Marks");
                        break;
                    }

                    Student s = new Student(id, name, dept, marks);

                    dao.addStudent(s);

                    break;

                case 2:

                    dao.viewStudents();

                    break;

                case 3:

                    System.out.print("Enter Student ID: ");
                    int updateId = sc.nextInt();

                    System.out.print("Enter New Marks: ");
                    int newMarks = sc.nextInt();

                    dao.updateStudent(updateId, newMarks);

                    break;

                case 4:

                    System.out.print("Enter Student ID: ");
                    int deleteId = sc.nextInt();

                    dao.deleteStudent(deleteId);

                    break;

                case 5:

                    System.out.print("Enter Student ID: ");
                    int searchId = sc.nextInt();

                    dao.searchStudent(searchId);

                    break;

                case 6:

                    System.exit(0);

                default:

                    System.out.println("Invalid Choice");
            }
        }
    }
}