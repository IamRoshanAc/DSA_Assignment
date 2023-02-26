package Views;

import Controller.UserController;
import Model.Job;
import Model.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateJobScreen extends JFrame implements ActionListener {
    private JLabel jobNameLabel;
    private JTextField jobNameField;
    private JButton createButton;
    private JButton backButton;

    public CreateJobScreen() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        jobNameLabel = new JLabel("Job Name:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        jobNameLabel.setForeground(Color.decode("#16B13B"));
        add(jobNameLabel, constraints);

        jobNameField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(jobNameField, constraints);

        createButton = new JButton("Create");
        createButton.addActionListener(this);
        constraints.gridx = 0;
        constraints.gridy = 1;
        createButton.setBackground(Color.decode("#16B13B"));
        createButton.setForeground(Color.decode("#ffffff"));
        LineBorder lineBorder =new LineBorder(Color.decode("#16B13B"), 2, true);
        createButton.setBorder(lineBorder);
        add(createButton, constraints);

        backButton = new JButton("Go back");
        backButton.addActionListener(this);
        constraints.gridx = 1;
        constraints.gridy = 1;
        backButton.setBackground(Color.decode("#16B13B"));
        backButton.setForeground(Color.decode("#ffffff"));
        backButton.setBorder(lineBorder);
        add(backButton, constraints);

        setTitle("Create Job Screen");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == createButton) {
            String jobName = jobNameField.getText().trim();
            if (jobName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the job name.");
            } else {
                Job job = new Job(jobName);
                UserController jobController = new UserController();
                int result = jobController.jobCustomerPreparedStatement(job);
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Job created successfully.");
                    Dashboard dashboard = new Dashboard();
                    dashboard.setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error creating job.");
                }
            }
        } else if (event.getSource() == backButton) {
            Dashboard dashboard = new Dashboard();
            dashboard.setVisible(true);
            this.dispose();
        }
    }

}
