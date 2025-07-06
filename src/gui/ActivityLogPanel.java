package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import ds.ActivityLogList;

public class ActivityLogPanel extends JFrame {
    private JTextArea logArea;
    private JButton refreshButton, backButton;
    private ActivityLogList logList = ActivityLogList.getInstance();

    public ActivityLogPanel() {
        setTitle("Activity Log");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        refreshButton = new JButton("Refresh Logs");
        backButton = new JButton("Back to Dashboard");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> displayLogs());
        backButton.addActionListener(e -> {
            dispose();
            new Dashboard();
        });

        displayLogs();
        setVisible(true);
    }

    private void displayLogs() {
        logArea.setText("Activity Logs:\n\n");
        logArea.append(logList.getAllLogs());
    }
}
