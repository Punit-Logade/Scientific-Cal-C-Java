 package Scientific_Calculator;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class CalculatorGUI extends JFrame implements ActionListener {

    private JTextField display;
    private JPanel panel;
    private CalculateLogic logic = new CalculateLogic();
    private ArrayList<JButton> buttonsList = new ArrayList<>();
    private boolean isDarkMode = false;

    private String[][] layout = {
            {"(", ")", "%", "C", "←"},
            {"sin", "cos", "tan", "/", "√"},
            {"7", "8", "9", "*", "^"},
            {"4", "5", "6", "-", "!"},
            {"1", "2", "3", "+", "Dark"},
            {"0", "00", ".", "=", ""}
    };

    public CalculatorGUI() {

        setTitle("Scientific Calculator");
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        display = new JTextField();
        display.setFont(new Font("Segoe UI", Font.BOLD, 24));
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.weightx = 1;
        gbc.weighty = 1;

        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {

                String text = layout[row][col];
                if (text.equals("")) continue;

                JButton btn = new JButton(text);
                btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
                btn.setFocusPainted(false);
                btn.setBorderPainted(false);
                btn.setOpaque(true);

                btn.addActionListener(this);
                buttonsList.add(btn);

                gbc.gridx = col;
                gbc.gridy = row;
                gbc.gridwidth = 1;

                // Make "=" span 2 columns
                if (text.equals("=")) {
                    gbc.gridwidth = 2;
                }

                panel.add(btn, gbc);
            }
        }

        defaultTheme();

        add(display, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setSize(420, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        if (cmd.equals("C")) {
            display.setText("");
        }

        else if (cmd.equals("=")) {
            try {
                String input = display.getText();
                if (!logic.isValidExpression(input)) {
                    display.setText("Invalid");
                    return;
                }
                double result = logic.evaluateExpression(input);
                display.setText(String.valueOf(result));
            } catch (Exception ex) {
                display.setText("Error");
            }
        }

        else if (cmd.equals("Dark")) {
            if (!isDarkMode) enableDarkMode();
            else defaultTheme();
        }

        else if (cmd.equals("←")) {
            String text = display.getText();
            if (!text.isEmpty()) {
                display.setText(text.substring(0, text.length() - 1));
            }
        }

        else {
            if ("+-*/^".contains(cmd)) {
                display.setText(display.getText() + " " + cmd + " ");
            }
            else if (cmd.equals("sin") || cmd.equals("cos") || cmd.equals("tan") || cmd.equals("√")) {
                display.setText(display.getText() + cmd + "(");
            }
            else {
                display.setText(display.getText() + cmd);
            }
        }
    }

    private void enableDarkMode() {

        panel.setBackground(new Color(28, 28, 30));
        display.setBackground(new Color(40, 40, 42));
        display.setForeground(new Color(230, 230, 230));

        for (JButton b : buttonsList) {

            String t = b.getText();

            if (t.equals("=")) {
                b.setBackground(new Color(72, 140, 120));
                b.setForeground(Color.WHITE);
            }
            else if ("+-*/^".contains(t)) {
                b.setBackground(new Color(50, 50, 52));
                b.setForeground(new Color(255, 170, 110));
            }
            else if (t.equals("C")) {
                b.setBackground(new Color(85, 55, 55));
                b.setForeground(new Color(255, 180, 180));
            }
            else {
                b.setBackground(new Color(55, 55, 58));
                b.setForeground(new Color(210, 170, 120));
            }
        }

        isDarkMode = true;
    }

    private void defaultTheme() {

        panel.setBackground(new Color(245, 245, 247));
        display.setBackground(Color.WHITE);
        display.setForeground(new Color(40, 40, 40));

        for (JButton b : buttonsList) {

            String t = b.getText();

            if (t.equals("=")) {
                b.setBackground(new Color(72, 140, 120));
                b.setForeground(Color.WHITE);
            }
            else if ("+-*/^".contains(t)) {
                b.setBackground(new Color(230, 230, 232));
                b.setForeground(new Color(200, 120, 70));
            }
            else if (t.equals("C")) {
                b.setBackground(new Color(240, 210, 210));
                b.setForeground(new Color(150, 60, 60));
            }
            else {
                b.setBackground(new Color(235, 235, 238));
                b.setForeground(new Color(60, 60, 60));
            }
        }

        isDarkMode = false;
    }
}
