package hospital;

import java.sql.*;
import java.util.Scanner;

public class Patient {

    // ================= PATIENT MENU =================

    public static void patientMenu(Scanner sc)
            throws SQLException {

        int choice;

        do {

            System.out.println("\n--- PATIENT MENU ---");
            System.out.println("1. Add Patient");
            System.out.println("2. Read Patient");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Back");

            System.out.println("Enter the choice:");
            choice = sc.nextInt();

            switch (choice) {

            case 1:
                addPatient(sc);
                break;

            case 2:
                readPatient();
                break;

            case 3:
                updatePatient(sc);
                break;

            case 4:
                deletePatient(sc);
                break;

            case 5:
                break;

            default:
                System.out.println("Invalid choice");
            }

        } while (choice != 5);
    }


    // ================= ADD PATIENT =================

    public static void addPatient(Scanner sc)
            throws SQLException {

        sc.nextLine();

        System.out.println("Enter patient name:");
        String name = sc.nextLine();

        System.out.println("Enter patient age:");
        int age = sc.nextInt();

        sc.nextLine();

        System.out.println("Enter patient phone:");
        String phone = sc.nextLine();

        String insert =
                "insert into patients(name,age,phone) values (?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(insert)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, phone);

            ps.executeUpdate();

            System.out.println("Patient record inserted");
        }
    }


    // ================= READ PATIENT =================

    public static void readPatient()
            throws SQLException {

        String fetch =
                "select * from patients";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(fetch)) {

            System.out.println("Patient Records:");

            while (rs.next()) {

                System.out.println(
                        rs.getInt("patient_id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getInt("age") + " | " +
                        rs.getString("phone")
                );
            }
        }
    }


 // ================= UPDATE PATIENT =================

    public static void updatePatient(Scanner sc)
            throws SQLException {

        sc.nextLine();

        System.out.println("Enter patient name:");
        String name = sc.nextLine();

        // Check whether patient exists
        String check =
                "select patient_id from patients where name=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(check)) {

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {

                System.out.println(
                        "Patient not found. Please enter the name properly.");

                return;
            }

            int patientId = rs.getInt("patient_id");

            System.out.println("\nWhat do you want to update?");
            System.out.println("1. Name");
            System.out.println("2. Age");
            System.out.println("3. Phone");

            System.out.println("Enter your choice:");
            int choice = sc.nextInt();

            if (choice == 1) {

                sc.nextLine();

                System.out.println("Enter new patient name:");
                String newName = sc.nextLine();

                String update =
                        "update patients set name=? where patient_id=?";

                try (PreparedStatement ps2 =
                             con.prepareStatement(update)) {

                    ps2.setString(1, newName);
                    ps2.setInt(2, patientId);

                    ps2.executeUpdate();

                    System.out.println(
                            "Patient name updated successfully.");
                }

            } else if (choice == 2) {

                System.out.println("Enter new age:");
                int age = sc.nextInt();

                String update =
                        "update patients set age=? where patient_id=?";

                try (PreparedStatement ps2 =
                             con.prepareStatement(update)) {

                    ps2.setInt(1, age);
                    ps2.setInt(2, patientId);

                    ps2.executeUpdate();

                    System.out.println(
                            "Patient age updated successfully.");
                }

            } else if (choice == 3) {

                sc.nextLine();

                System.out.println("Enter new phone:");
                String phone = sc.nextLine();

                String update =
                        "update patients set phone=? where patient_id=?";

                try (PreparedStatement ps2 =
                             con.prepareStatement(update)) {

                    ps2.setString(1, phone);
                    ps2.setInt(2, patientId);

                    ps2.executeUpdate();

                    System.out.println(
                            "Patient phone updated successfully.");
                }

            } else {

                System.out.println("Invalid choice.");
            }
        }
    }

    // ================= DELETE PATIENT =================

    public static void deletePatient(Scanner sc)
            throws SQLException {

        System.out.println("Enter the patient id to delete:");
        int id = sc.nextInt();

        String delete =
                "delete from patients where patient_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(delete)) {

            ps.setInt(1, id);

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Patient record deleted");
            } else {
                System.out.println("Record not found");
            }
        }
    }
}