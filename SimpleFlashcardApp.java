package Flashcard2;
import javax.swing.*;
import java.awt.event.*;

public class SimpleFlashcardApp extends JFrame {
    private String question = "";
    private String answer = "";
    private JTextArea displayArea;

    public SimpleFlashcardApp() {
        setTitle("Flashcard App");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Display Area
        displayArea = new JTextArea("Welcome to Flashcard App");
        displayArea.setEditable(false);
        add(displayArea);

        // Button Panel
        JButton addButton = new JButton("Add Flashcard");
        JButton viewButton = new JButton("View Flashcard");

        addButton.addActionListener(e -> addFlashcard());
        viewButton.addActionListener(e -> viewFlashcard());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);

        add(buttonPanel, "South");
    }

    private void addFlashcard() {
        question = JOptionPane.showInputDialog("Enter the question:");
        answer = JOptionPane.showInputDialog("Enter the answer:");
        if (question != null && answer != null) {
            displayArea.setText("Flashcard added! Click 'View Flashcard' to see it.");
        }
    }

    private void viewFlashcard() {
        if (question.isEmpty() || answer.isEmpty()) {
            displayArea.setText("No flashcard available. Add one first.");
        } else {
            displayArea.setText("Q: " + question + "\n\nClick here to see the answer.");
            displayArea.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    displayArea.setText("A: " + answer);
                }
            });
        }
    }

    public static void main(String[] args) {
        SimpleFlashcardApp app = new SimpleFlashcardApp();
        app.setVisible(true);
    }
}
