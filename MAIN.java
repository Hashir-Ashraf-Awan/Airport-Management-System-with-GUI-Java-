package task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AirplaneTaskManagementForm extends JFrame {

    private JTextArea airplaneInfoTextArea;
    private JButton addTaskButton;
    private JButton prioritizeTasksButton;
    private JButton dispatchTasksButton;
    private TaskEngine taskEngine;
    private JLabel clockLabel;

    private Timer clockTimer;
    private int elapsedSeconds;

    public AirplaneTaskManagementForm() {

        airplaneInfoTextArea = new JTextArea();
        addTaskButton = new JButton("Add Task");
        prioritizeTasksButton = new JButton("Prioritize Tasks");
        dispatchTasksButton = new JButton("Dispatch Tasks");
        taskEngine = new TaskEngine();
        clockLabel = new JLabel("Time: ");


        setLayout(new BorderLayout());


        add(airplaneInfoTextArea, BorderLayout.CENTER);
        add(addTaskButton, BorderLayout.NORTH);
        add(prioritizeTasksButton, BorderLayout.WEST);
        add(dispatchTasksButton, BorderLayout.EAST);
        add(clockLabel, BorderLayout.SOUTH);


        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Task newTask = new Task();
                taskEngine.addTask(newTask);
                updateTextArea();
            }
        });

        prioritizeTasksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taskEngine.prioritizeTasks();
                updateTextArea();
            }
        });

        dispatchTasksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taskEngine.dispatchTasks();
                updateTextArea();
            }
        });


        setTitle("Airplane Task Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        elapsedSeconds = 0;
        clockTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateClockLabel();
            }
        });
        clockTimer.start();
    }

    private void updateTextArea() {

        Airplane a = new Airplane(2, "Lahore", "Karachi", Airplane.AirplaneState.HOLDING);
        airplaneInfoTextArea.setText(a.toString());
    }

    private void updateClockLabel() {

        int hours = elapsedSeconds / 3600;
        int minutes = (elapsedSeconds % 3600) / 60;
        int seconds = elapsedSeconds % 60;

        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        clockLabel.setText("Time: " + timeString);

        elapsedSeconds++;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AirplaneTaskManagementForm();
            }
        });
    }
}