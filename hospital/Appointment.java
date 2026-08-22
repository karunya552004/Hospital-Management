package hospital;

import java.sql.*;
import java.util.Scanner;

public class Appointment {

    // ================= APPOINTMENT MENU =================

    public static void appointmentMenu(Scanner sc)
            throws SQLException {

        int choice;

        do {

            System.out.println("\n--- APPOINTMENT MENU ---");
            System.out.println("1. Book Appointment");
            System.out.println("2. View Appointments");
            System.out.println("3. Delete Appointment");
            System.out.println("4. Back");

            System.out.println("Enter the choice:");
            choice = sc.nextInt();

            switch (choice) {

            case 1:
                bookAppointment(sc);
                break;

            case 2:
                viewAppointments();
                break;

            case 3:
                deleteAppointment(sc);
                break;

            case 4:
                break;

            default:
                System.out.println("Invalid choice");
            }

        } while (choice != 4);
    }


    // ================= BOOK APPOINTMENT =================

    public static void bookAppointment(Scanner sc)
            throws SQLException {

        sc.nextLine();

        // Patient name

        System.out.println("Enter patient name:");
        String patientName = sc.nextLine();

        String patientQuery =
                "select patient_id from patients where name=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(patientQuery)) {

            ps.setString(1, patientName);

            ResultSet rs = ps.executeQuery();

            // Patient does not exist

            if (!rs.next()) {

                System.out.println(
                        "Patient not registered. " +
                        "Please register patient details first.");

                return;
            }

            int patientId =
                    rs.getInt("patient_id");


            // Doctor name

            System.out.println("Enter doctor name:");
            String doctorName = sc.nextLine();

            String doctorQuery =
                    "select doctor_id from doctors where name=?";

            try (PreparedStatement ps2 =
                         con.prepareStatement(doctorQuery)) {

                ps2.setString(1, doctorName);

                ResultSet rs2 =
                        ps2.executeQuery();

                // Doctor does not exist

                if (!rs2.next()) {

                    System.out.println(
                            "Doctor not found. " +
                            "Please enter the doctor name properly.");

                    return;
                }

                int doctorId =
                        rs2.getInt("doctor_id");


                // Appointment date

                System.out.println(
                        "Enter appointment date (YYYY-MM-DD):");

                String date = sc.nextLine();


                // Insert appointment

                String insert =
                        "insert into appointments" +
                        "(patient_id,doctor_id,appointment_date)" +
                        " values (?,?,?)";

                try (PreparedStatement ps3 =
                             con.prepareStatement(insert)) {

                    ps3.setInt(1, patientId);
                    ps3.setInt(2, doctorId);
                    ps3.setString(3, date);

                    ps3.executeUpdate();

                    System.out.println(
                            "Appointment booked successfully");
                }
            }
        }
    }


    // ================= VIEW APPOINTMENTS =================

    public static void viewAppointments()
            throws SQLException {

        String fetch =
                "select a.appointment_id," +
                "p.name as patient," +
                "d.name as doctor," +
                "d.specialization," +
                "a.appointment_date " +
                "from appointments a " +
                "join patients p " +
                "on a.patient_id=p.patient_id " +
                "join doctors d " +
                "on a.doctor_id=d.doctor_id";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(fetch)) {

            System.out.println("Appointment Details:");

            while (rs.next()) {

                System.out.println(
                        rs.getInt("appointment_id") +
                        " | " +
                        rs.getString("patient") +
                        " | " +
                        rs.getString("doctor") +
                        " | " +
                        rs.getString("specialization") +
                        " | " +
                        rs.getString("appointment_date")
                );
            }
        }
    }


    // ================= DELETE APPOINTMENT =================

    public static void deleteAppointment(Scanner sc)
            throws SQLException {

        System.out.println("Enter appointment id:");
        int id = sc.nextInt();

        String delete =
                "delete from appointments " +
                "where appointment_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(delete)) {

            ps.setInt(1, id);

            int row =
                    ps.executeUpdate();

            if (row > 0) {

                System.out.println(
                        "Appointment deleted");

            } else {

                System.out.println(
                        "Record not found");
            }
        }
    }
}