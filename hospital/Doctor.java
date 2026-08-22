package hospital;

import java.sql.*;
import java.util.Scanner;

public class Doctor {

    // ================= DOCTOR MENU =================

    public static void doctorMenu(Scanner sc)
            throws SQLException {

        int choice;

        do {

            System.out.println("\n--- DOCTOR MENU ---");
            System.out.println("1. Add Doctor");
            System.out.println("2. Read Doctor");
            System.out.println("3. Update Doctor");
            System.out.println("4. Delete Doctor");
            System.out.println("5. Doctor Appointment Schedule");
            System.out.println("6. Back");

            System.out.println("Enter the choice:");
            choice = sc.nextInt();

            switch (choice) {

            case 1:
                addDoctor(sc);
                break;

            case 2:
                readDoctor();
                break;

            case 3:
                updateDoctor(sc);
                break;

            case 4:
                deleteDoctor(sc);
                break;

            case 5:
                doctorSchedule(sc);
                break;

            case 6:
                break;

            default:
                System.out.println("Invalid choice");
            }

        } while (choice != 6);
    }


    // ================= ADD DOCTOR =================

    public static void addDoctor(Scanner sc)
            throws SQLException {

        sc.nextLine();

        System.out.println("Enter doctor name:");
        String name = sc.nextLine();

        System.out.println("Enter specialization:");
        String specialization = sc.nextLine();

        String insert =
                "insert into doctors(name,specialization) values (?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(insert)) {

            ps.setString(1, name);
            ps.setString(2, specialization);

            ps.executeUpdate();

            System.out.println("Doctor record inserted");
        }
    }


    // ================= READ DOCTOR =================

    public static void readDoctor()
            throws SQLException {

        String fetch =
                "select * from doctors";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(fetch)) {

            System.out.println("Doctor Records:");

            while (rs.next()) {

                System.out.println(
                        rs.getInt("doctor_id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getString("specialization")
                );
            }
        }
    }


    // ================= UPDATE DOCTOR =================

 // ================= UPDATE DOCTOR =================

    public static void updateDoctor(Scanner sc)
            throws SQLException {

        sc.nextLine();

        System.out.println("Enter doctor name:");
        String name = sc.nextLine();

        String check =
                "select doctor_id from doctors where name=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(check)) {

            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {

                System.out.println(
                        "Doctor not found. Please enter the name properly.");

                return;
            }

            int doctorId = rs.getInt("doctor_id");

            System.out.println("\nWhat do you want to update?");
            System.out.println("1. Specialization");

            System.out.println("Enter your choice:");
            int choice = sc.nextInt();

            if (choice == 1) {

                sc.nextLine();

                System.out.println("Enter new specialization:");
                String specialization = sc.nextLine();

                String update =
                        "update doctors set specialization=? " +
                        "where doctor_id=?";

                try (PreparedStatement ps2 =
                             con.prepareStatement(update)) {

                    ps2.setString(1, specialization);
                    ps2.setInt(2, doctorId);

                    ps2.executeUpdate();

                    System.out.println(
                            "Doctor specialization updated successfully.");
                }

            } else {

                System.out.println("Invalid choice.");
            }
        }
    }


    // ================= DELETE DOCTOR =================

    public static void deleteDoctor(Scanner sc)
            throws SQLException {

        System.out.println("Enter doctor id to delete:");
        int id = sc.nextInt();

        String delete =
                "delete from doctors where doctor_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(delete)) {

            ps.setInt(1, id);

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Doctor record deleted");
            } else {
                System.out.println("Record not found");
            }
        }
    }


    // ================= DOCTOR SCHEDULE =================

    public static void doctorSchedule(Scanner sc)
            throws SQLException {

        System.out.println("Enter doctor id:");
        int doctorId = sc.nextInt();

        String fetch =
                "select p.name, a.appointment_date " +
                "from appointments a " +
                "join patients p " +
                "on a.patient_id=p.patient_id " +
                "where a.doctor_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(fetch)) {

            ps.setInt(1, doctorId);

            ResultSet rs = ps.executeQuery();

            System.out.println("Doctor Appointment Schedule:");

            while (rs.next()) {

                System.out.println(
                        "Patient: " +
                        rs.getString("name") +
                        " | Date: " +
                        rs.getString("appointment_date")
                );
            }
        }
    }
}