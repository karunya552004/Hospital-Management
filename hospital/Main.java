package hospital;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int choice;

        try {

            do {

                System.out.println("\n===== HOSPITAL MANAGEMENT =====");
                System.out.println("1. Patient");
                System.out.println("2. Doctor");
                System.out.println("3. Appointment");
                System.out.println("4. Prescription");
                System.out.println("5. Exit");

                System.out.println("Enter the choice:");
                choice = sc.nextInt();

                switch (choice) {

                case 1:
                    Patient.patientMenu(sc);
                    break;

                case 2:
                    Doctor.doctorMenu(sc);
                    break;

                case 3:
                    Appointment.appointmentMenu(sc);
                    break;

                case 4:
                    Prescription.prescriptionMenu(sc);
                    break;

                case 5:
                    System.out.println("Exit");
                    break;

                default:
                    System.out.println("Invalid choice");
                }

            } while (choice != 5);

        } catch (Exception e) {

            e.printStackTrace();
        }

        sc.close();
    }
}