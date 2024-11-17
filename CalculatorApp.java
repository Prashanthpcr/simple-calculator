import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorApp extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder currentInput = new StringBuilder();
    private double result = 0;
    private String operator = "";
    private boolean operatorClicked = false;

    public CalculatorApp() {
        setTitle("Calculator");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up the display
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 26));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(new Color(30, 31, 41)); // Dark background
        display.setForeground(Color.ORANGE); // Text color similar to your example
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Set up the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        buttonPanel.setBackground(new Color(30, 31, 41)); // Dark background

        // Buttons with a theme similar to the example
        String[] buttons = {
            "C", "DEL", "%", "/", 
            "9", "8", "7", "*", 
            "6", "5", "4", "-", 
            "3", "2", "1", "+", 
            "+/-", "0", ".", "="
        };

        for (String text : buttons) {
            JButton button = createStyledButton(text);
            buttonPanel.add(button);
        }

        // Layout for the frame
        setLayout(new BorderLayout(10, 10));
        add(display, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        // Set a unique background color
        getContentPane().setBackground(new Color(50, 50, 60));
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        
        if (text.matches("[0-9]")) {
            button.setBackground(new Color(50, 50, 70)); // Number button color
            button.setForeground(Color.WHITE);
        } else if (text.equals("=")) {
            button.setBackground(Color.ORANGE); // Equal button color
            button.setForeground(Color.WHITE);
        } else if (text.equals("C")) {
            button.setBackground(new Color(220, 20, 60)); // Clear button color
            button.setForeground(Color.WHITE);
        } else if (text.equals("DEL")) {
            button.setBackground(new Color(255, 140, 0)); // Delete button color
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(new Color(70, 70, 90)); // Operator button color
            button.setForeground(Color.ORANGE);
        }

        button.setFocusPainted(false);
        button.addActionListener(this);
        button.setBorder(BorderFactory.createLineBorder(new Color(30, 31, 41), 5));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Clear (C) button handling
        if (command.equals("C")) {
            clearAll();
        }
        // Delete (DEL) button handling
        else if (command.equals("DEL")) {
            deleteLastCharacter();
        }
        // Percentage button handling
        else if (command.equals("%")) {
            try {
                double value = Double.parseDouble(currentInput.toString()) / 100;
                currentInput.setLength(0);
                currentInput.append(value);
                display.setText(display.getText() + "%");
            } catch (Exception ex) {
                display.setText("Error");
            }
        }
        // Number or decimal point handling
        else if (command.matches("[0-9.]")) {
            if (operatorClicked) {
                currentInput.setLength(0);  // Clear input after operator
                operatorClicked = false;
            }
            currentInput.append(command);
            display.setText(display.getText() + command);
        }
        // Operator handling
        else if (command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/")) {
            operatorClicked = true;
            performOperation();
            operator = command;
            display.setText(display.getText() + " " + command + " ");
        }
        // Equals handling
        else if (command.equals("=")) {
            performOperation();
            display.setText(String.valueOf(result));
            operator = "";
            currentInput.setLength(0);
            currentInput.append(result);
        }
        // Toggle positive/negative (+/-)
        else if (command.equals("+/-")) {
            if (currentInput.length() > 0 && currentInput.charAt(0) == '-') {
                currentInput.deleteCharAt(0);
            } else {
                currentInput.insert(0, "-");
            }
            display.setText(display.getText());
        }
    }

    private void performOperation() {
        try {
            double inputValue = currentInput.length() > 0 ? Double.parseDouble(currentInput.toString()) : 0;

            switch (operator) {
                case "+":
                    result += inputValue;
                    break;
                case "-":
                    result -= inputValue;
                    break;
                case "*":
                    result *= inputValue;
                    break;
                case "/":
                    if (inputValue != 0) {
                        result /= inputValue;
                    } else {
                        display.setText("Cannot divide by zero");
                        return;
                    }
                    break;
                default:
                    result = inputValue;
                    break;
            }
            currentInput.setLength(0);
        } catch (Exception e) {
            display.setText("Error");
            clearAll();
        }
    }

    private void clearAll() {
        result = 0;
        operator = "";
        currentInput.setLength(0);
        display.setText("");
    }

    private void deleteLastCharacter() {
        if (currentInput.length() > 0) {
            currentInput.setLength(currentInput.length() - 1);
            display.setText(display.getText().substring(0, display.getText().length() - 1));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorApp app = new CalculatorApp();
            app.setVisible(true);
        });
    }
}
