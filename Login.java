package Flashcard2;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login Form");
        frame.setSize(400, 600);
        frame.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(100, 100, 200, 30);

        JTextField userTextField = new JTextField();
        userTextField.setBounds(100, 130, 200, 30);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(100, 170, 200, 30);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(100, 200, 200, 30);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 250, 100, 30);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userTextField.getText();
                String password = new String(passField.getPassword());

                // Database connection parameters
                String url = "jdbc:postgresql://localhost:5432/JavaPro"; // Replace with your DB name
                String dbUsername = "postgres"; // Replace with your DB username
                String dbPassword = "5432"; // Replace with your DB password

                // Insert user credentials into the database
                if (storeUserCredentials(username, password, url, dbUsername, dbPassword)) {
                    JOptionPane.showMessageDialog(frame, "Login Successful! User stored.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Navigate to SimpleFlashcardApp
                    SwingUtilities.invokeLater(() -> {
                        SimpleFlashcardApp flashcardApp = new SimpleFlashcardApp();
                        flashcardApp.setVisible(true);
                        frame.dispose(); // Close the login frame
                    });
                } else {
                    JOptionPane.showMessageDialog(frame, "Error storing user.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.add(userLabel);
        frame.add(userTextField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(loginButton);

        frame.setVisible(true);
    }

    public static boolean storeUserCredentials(String username, String password, String url, String dbUsername, String dbPassword) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DriverManager.getConnection(url, dbUsername, dbPassword);

            String sql = "INSERT INTO javalogin (username, password) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; // Returns true if insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
