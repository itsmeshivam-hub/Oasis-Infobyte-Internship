
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class main {

    private static final String DB_URL = "jdbc:sqlite:reservation.db";

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin123";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private static void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS reservations (
                    pnr TEXT PRIMARY KEY,
                    passenger_name TEXT NOT NULL,
                    train_number INTEGER NOT NULL,
                    train_name TEXT NOT NULL,
                    class_type TEXT NOT NULL,
                    journey_date TEXT NOT NULL,
                    source TEXT NOT NULL,
                    destination TEXT NOT NULL
                )
                """;

        try (Connection con = getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private static String getTrainName(String number) {
        switch (number) {
            case "12155": return "Bhopal Express";
            case "19321": return "Indore Express";
            case "12002": return "Shatabdi Express";
            case "12951": return "Mumbai Rajdhani";
            case "12627": return "Karnataka Express";
            default: return null;
        }
    }

    private static String generatePNR() {
        return UUID.randomUUID().toString()
                .replace("-", "").substring(0, 12).toUpperCase();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createTable();
            new LoginFrame();
        });
    }

    // LOGIN WINDOW
    static class LoginFrame extends JFrame {

        LoginFrame() {
            setTitle("Online Reservation System - Login");
            setSize(400, 280);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel(new GridLayout(4, 2, 10, 15));
            panel.setBorder(new EmptyBorder(25, 25, 25, 25));

            JTextField username = new JTextField();
            JPasswordField password = new JPasswordField();

            JButton login = new JButton("Login");

            panel.add(new JLabel("Username:"));
            panel.add(username);
            panel.add(new JLabel("Password:"));
            panel.add(password);
            panel.add(new JLabel());
            panel.add(login);

            login.addActionListener(e -> {
                String user = username.getText().trim();
                String pass = new String(password.getPassword());

                if (USERNAME.equals(user) && PASSWORD.equals(pass)) {
                    dispose();
                    new DashboardFrame();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid Username or Password!",
                            "Access Denied",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            add(panel);
            setVisible(true);
        }
    }

    // DASHBOARD
    static class DashboardFrame extends JFrame {

        DashboardFrame() {
            setTitle("Online Reservation System");
            setSize(850, 550);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTabbedPane tabs = new JTabbedPane();

            tabs.addTab("Book Reservation", new ReservationPanel());
            tabs.addTab("Cancel Reservation", new CancellationPanel());

            add(tabs);
            setVisible(true);
        }
    }

    // RESERVATION PANEL
    static class ReservationPanel extends JPanel {

        JTextField passenger, trainNumber, trainName;
        JTextField date, source, destination;
        JComboBox<String> classType;

        ReservationPanel() {
            setLayout(new GridLayout(9, 2, 10, 12));
            setBorder(new EmptyBorder(25, 40, 25, 40));

            passenger = new JTextField();
            trainNumber = new JTextField();
            trainName = new JTextField();
            trainName.setEditable(false);

            date = new JTextField();
            source = new JTextField();
            destination = new JTextField();

            classType = new JComboBox<>(new String[]{
                    "Sleeper", "AC 3 Tier", "AC 2 Tier",
                    "AC First Class", "General"
            });

            JButton book = new JButton("Book Ticket");

            add(new JLabel("Passenger Name:"));
            add(passenger);

            add(new JLabel("Train Number:"));
            add(trainNumber);

            add(new JLabel("Train Name:"));
            add(trainName);

            add(new JLabel("Class Type:"));
            add(classType);

            add(new JLabel("Journey Date (YYYY-MM-DD):"));
            add(date);

            add(new JLabel("Source Station:"));
            add(source);

            add(new JLabel("Destination Station:"));
            add(destination);

            add(new JLabel());
            add(book);

            trainNumber.addActionListener(e -> updateTrainName());

            trainNumber.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    updateTrainName();
                }
            });

            book.addActionListener(e -> bookTicket());
        }

        private void updateTrainName() {
            String name = getTrainName(trainNumber.getText().trim());

            trainName.setText(name == null ? "" : name);
        }

        private void bookTicket() {

            String p = passenger.getText().trim();
            String number = trainNumber.getText().trim();
            String t = trainName.getText().trim();
            String c = (String) classType.getSelectedItem();
            String d = date.getText().trim();
            String s = source.getText().trim();
            String dest = destination.getText().trim();

            if (p.isEmpty() || number.isEmpty() || t.isEmpty()
                    || d.isEmpty() || s.isEmpty() || dest.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please fill all required fields!");
                return;
            }

            if (!number.matches("\\d+")) {
                JOptionPane.showMessageDialog(this,
                        "Train number must be numeric!");
                return;
            }

            try {
                LocalDate journeyDate = LocalDate.parse(d);

                if (journeyDate.isBefore(LocalDate.now())) {
                    JOptionPane.showMessageDialog(this,
                            "Journey date cannot be in the past!");
                    return;
                }

            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this,
                        "Enter date in YYYY-MM-DD format!");
                return;
            }

            if (s.equalsIgnoreCase(dest)) {
                JOptionPane.showMessageDialog(this,
                        "Source and destination cannot be same!");
                return;
            }

            String pnr = generatePNR();

            String sql = """
                    INSERT INTO reservations
                    (pnr, passenger_name, train_number, train_name,
                    class_type, journey_date, source, destination)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                    """;

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, pnr);
                ps.setString(2, p);
                ps.setInt(3, Integer.parseInt(number));
                ps.setString(4, t);
                ps.setString(5, c);
                ps.setString(6, d);
                ps.setString(7, s);
                ps.setString(8, dest);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "BOOKING SUCCESSFUL!\n\n"
                        + "PNR: " + pnr
                        + "\nPassenger: " + p
                        + "\nTrain: " + t
                        + "\nTrain Number: " + number
                        + "\nClass: " + c
                        + "\nJourney Date: " + d
                        + "\nFrom: " + s
                        + "\nTo: " + dest);

                passenger.setText("");
                trainNumber.setText("");
                trainName.setText("");
                date.setText("");
                source.setText("");
                destination.setText("");

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "Database Error: " + e.getMessage());
            }
        }
    }

    // CANCELLATION PANEL
    static class CancellationPanel extends JPanel {

        JTextField pnrField;
        JTextArea details;
        JButton cancelButton;

        CancellationPanel() {
            setLayout(new BorderLayout(10, 10));
            setBorder(new EmptyBorder(25, 35, 25, 35));

            JPanel top = new JPanel(new FlowLayout());

            pnrField = new JTextField(20);
            JButton fetch = new JButton("Fetch Booking");

            top.add(new JLabel("Enter PNR:"));
            top.add(pnrField);
            top.add(fetch);

            details = new JTextArea();
            details.setEditable(false);
            details.setFont(new Font("Monospaced", Font.PLAIN, 14));

            cancelButton = new JButton("Confirm Cancellation");
            cancelButton.setEnabled(false);

            add(top, BorderLayout.NORTH);
            add(new JScrollPane(details), BorderLayout.CENTER);
            add(cancelButton, BorderLayout.SOUTH);

            fetch.addActionListener(e -> fetchBooking());
            cancelButton.addActionListener(e -> cancelBooking());
        }

        private void fetchBooking() {

            String pnr = pnrField.getText().trim();

            if (pnr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter PNR number!");
                return;
            }

            String sql = "SELECT * FROM reservations WHERE pnr = ?";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, pnr);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        details.setText(
                                "PNR: " + rs.getString("pnr")
                                + "\nPassenger: " + rs.getString("passenger_name")
                                + "\nTrain Number: " + rs.getInt("train_number")
                                + "\nTrain Name: " + rs.getString("train_name")
                                + "\nClass: " + rs.getString("class_type")
                                + "\nJourney Date: " + rs.getString("journey_date")
                                + "\nSource: " + rs.getString("source")
                                + "\nDestination: " + rs.getString("destination")
                        );

                        cancelButton.setEnabled(true);

                    } else {
                        details.setText("No booking found for this PNR.");
                        cancelButton.setEnabled(false);
                    }
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "Database Error: " + e.getMessage());
            }
        }

        private void cancelBooking() {

            String pnr = pnrField.getText().trim();

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to cancel this reservation?",
                    "Confirm Cancellation",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice != JOptionPane.YES_OPTION) {
                return;
            }

            String sql = "DELETE FROM reservations WHERE pnr = ?";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, pnr);

                int deleted = ps.executeUpdate();

                if (deleted > 0) {
                    JOptionPane.showMessageDialog(this,
                            "Reservation Cancelled Successfully!");

                    details.setText("");
                    pnrField.setText("");
                    cancelButton.setEnabled(false);
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "Database Error: " + e.getMessage());
            }
        }
    }
}
