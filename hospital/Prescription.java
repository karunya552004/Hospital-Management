package hospital;

import java.sql.*;
import java.util.Scanner;

public class Prescription {

    // ================= PRESCRIPTION MENU =================

    public static void prescriptionMenu(Scanner sc)
            throws SQLException {

        int choice;

        do {

            System.out.println("\n--- PRESCRIPTION MENU ---");
            System.out.println("1. Add Prescription");
            System.out.println("2. Read Prescription");
            System.out.println("3. Delete Prescription");
            System.out.println("4. Back");

            System.out.println("Enter the choice:");
            choice = sc.nextInt();

            switch (choice) {

            case 1:
                addPrescription(sc);
                break;

            case 2:
                readPrescription();
                break;

            case 3:
                deletePrescription(sc);
                break;

            case 4:
                break;

            default:
                System.out.println("Invalid choice");
            }

        } while (choice != 4);
    }


    // ================= ADD PRESCRIPTION =================

    public static void addPrescription(Scanner sc)
            throws SQLException {

        System.out.println("Enter patient id:");
        int patientId = sc.nextInt();

        System.out.println("Enter doctor id:");
        int doctorId = sc.nextInt();

        sc.nextLine();

        System.out.println("Enter medicine:");
        String medicine = sc.nextLine();

        String insert =
                "insert into prescriptions" +
                "(patient_id,doctor_id,medicine)" +
                " values (?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(insert)) {

            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setString(3, medicine);

            ps.executeUpdate();

            System.out.println(
                    "Prescription added");
        }
    }


    // ================= READ PRESCRIPTION =================

    public static void readPrescription()
            throws SQLException {

        String fetch =
                "select pr.prescription_id," +
                "p.name as patient," +
                "d.name as doctor," +
                "pr.medicine " +
                "from prescriptions pr " +
                "join patients p " +
                "on pr.patient_id=p.patient_id " +
                "join doctors d " +
                "on pr.doctor_id=d.doctor_id";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(fetch)) {

            System.out.println(
                    "Prescription Details:");

            while (rs.next()) {

                System.out.println(
                        rs.getInt("prescription_id") +
                        " | Patient: " +
                        rs.getString("patient") +
                        " | Doctor: " +
                        rs.getString("doctor") +
                        " | Medicine: " +
                        rs.getString("medicine")
                );
            }
        }
    }


    // ================= DELETE PRESCRIPTION =================

    public static void deletePrescription(Scanner sc)
            throws SQLException {

        System.out.println(
                "Enter prescription id:");

        int id = sc.nextInt();

        String delete =
                "delete from prescriptions " +
                "where prescription_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(delete)) {

            ps.setInt(1, id);

            int row =
                    ps.executeUpdate();

            if (row > 0) {

                System.out.println(
                        "Prescription deleted");

            } else {

                System.out.println(
                        "Record not found");
            }
        }
    }
}