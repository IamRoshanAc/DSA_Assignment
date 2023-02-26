package Views;

import Controller.UserController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;

public class ScheduleJobScreen extends JFrame implements ActionListener {
    private JLabel jobIdLabel;
    private JComboBox<String> jobIdComboBox;
    private JLabel startTimeLabel;
    private JSpinner startTimeSpinner;
    private JButton scheduleJobButton;
    private JButton backButton;

    public ScheduleJobScreen() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        jobIdLabel = new JLabel("Job ID:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        jobIdLabel.setForeground(Color.decode("#16B13B"));
        add(jobIdLabel, constraints);
        LineBorder lineBorder =new LineBorder(Color.decode("#16B13B"), 2, true);
        jobIdComboBox = new JComboBox<String>();
        jobIdComboBox.setBackground(Color.decode("#16B13B"));
        jobIdComboBox.setForeground(Color.decode("#ffffff"));
        jobIdComboBox.addItem("Select Job ID");
        // Add job IDs to the combo box
        // Add job IDs to the combo box
        try {
            String username = "root";
            String password = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            // establishing connection in the connection object
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/assignment",
                    username,
                    password);
            PreparedStatement statement = connection.prepareStatement("SELECT jobId FROM taskscreen");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                jobIdComboBox.addItem(resultSet.getString("jobId"));
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(jobIdComboBox, constraints);

        startTimeLabel = new JLabel("Start Time:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        startTimeLabel.setForeground(Color.decode("#16B13B"));
        add(startTimeLabel, constraints);

        Date date = new Date();
        SpinnerDateModel spinnerModel = new SpinnerDateModel(date, null, null, Calendar.SECOND);
        startTimeSpinner = new JSpinner(spinnerModel);
        startTimeSpinner.setEditor(new JSpinner.DateEditor(startTimeSpinner, "yyyy/MM/dd HH:mm:ss"));
        constraints.gridx = 1;
        constraints.gridy = 1;
        startTimeSpinner.setForeground(Color.decode("#16B13B"));
        startTimeSpinner.setBackground(Color.decode("#16B13B"));
        startTimeSpinner.setForeground(Color.decode("#ffffff"));
        add(startTimeSpinner, constraints);

        scheduleJobButton = new JButton("Schedule Job");
        scheduleJobButton.addActionListener(this);
        constraints.gridx = 1;
        constraints.gridy = 2;
        scheduleJobButton.setBackground(Color.decode("#16B13B"));
        scheduleJobButton.setForeground(Color.decode("#ffffff"));
        scheduleJobButton.setBorder(lineBorder);

        add(scheduleJobButton, constraints);

        backButton = new JButton("Go Back");
        backButton.addActionListener(this);
        constraints.gridx = 0;
        constraints.gridy = 2;
        backButton.setBackground(Color.decode("#16B13B"));
        backButton.setForeground(Color.decode("#ffffff"));
        backButton.setBorder(lineBorder);

        add(backButton, constraints);

        setTitle("Schedule Job Screen");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == scheduleJobButton) {
            String jobId = jobIdComboBox.getSelectedItem().toString();
            Date startTime = (Date) startTimeSpinner.getValue();
            if (jobId.equals("Select Job ID") || startTime == null) {
                JOptionPane.showMessageDialog(this, "Please select a Job ID and enter a Start Time");
            } else {
                try {
                    String username = "root";
                    String password = null;
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/assignment",
                            username,
                            password);
                    String query = "INSERT INTO job_schedule (job_id, start_time) VALUES (?, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, jobId);
                    statement.setTimestamp(2, new java.sql.Timestamp(startTime.getTime()));
                    statement.executeUpdate();
                    connection.close();
                    JOptionPane.showMessageDialog(this, "Job scheduled successfully");

                    // Calculate the delay time in milliseconds
                    long delay = startTime.getTime() - System.currentTimeMillis();
                    if (delay > 0) {
                        // Create a new Timer with the delay time
                        Timer timer = new Timer((int) delay, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    String username = "root";
                                    String password = null;
                                    Class.forName("com.mysql.cj.jdbc.Driver");
                                    Connection connection = DriverManager.getConnection(
                                            "jdbc:mysql://localhost:3306/assignment",
                                            username,
                                            password);
                                    String query = "DELETE FROM job_schedule WHERE job_id = ?";
                                    PreparedStatement statement = connection.prepareStatement(query);
                                    statement.setString(1, jobId);
                                    statement.executeUpdate();
                                    connection.close();
                                    JOptionPane.showMessageDialog(ScheduleJobScreen.this, "Job:"+jobId+" has been finished and removed from the database");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                        // Start the Timer
                        timer.start();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (event.getSource() == backButton) {
            Dashboard dashboard = new Dashboard();
            dashboard.setVisible(true);
            this.dispose();
        }
    }


}
