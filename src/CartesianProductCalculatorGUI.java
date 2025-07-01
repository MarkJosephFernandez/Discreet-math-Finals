import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class CartesianProductCalculatorGUI extends JFrame {
    private JTextField setAField, setBField, setCField;
    private JTextArea resultArea;
    private JLabel statusLabel;
    private JButton calculateButton, clearButton;

    public CartesianProductCalculatorGUI() {
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Cartesian Product Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Create input panel
        JPanel inputPanel = createInputPanel();

        // Create button panel
        JPanel buttonPanel = createButtonPanel();

        // Create result panel
        JPanel resultPanel = createResultPanel();

        // Create status panel
        JPanel statusPanel = createStatusPanel();

        // Add panels to main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(resultPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);

        // Set window properties
        setSize(700, 600);
        setLocationRelativeTo(null);
        setResizable(true);

        // Add action listeners
        addActionListeners();
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title
        JLabel titleLabel = new JLabel("Cartesian Product Calculator");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 100, 200));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 15, 0);
        panel.add(titleLabel, gbc);

        // Instructions
        JTextArea instructionsArea = new JTextArea(
                "INPUT FORMAT GUIDE:\n" +
                        "• Enter elements separated by commas (e.g., 1, 2, 3)\n" +
                        "• Maximum 10 elements per set\n" +
                        "• Spaces around commas will be trimmed\n" +
                        "• Empty elements will be ignored\n" +
                        "• OUTPUT FORMAT: [ {a1,b1,c1}, {a1,b1,c2}, ..., {aN,bN,cN} ]"
        );
        instructionsArea.setEditable(false);
        instructionsArea.setBackground(new Color(245, 245, 245));
        instructionsArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Instructions"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        instructionsArea.setFont(new Font("Arial", Font.PLAIN, 12));

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(instructionsArea, gbc);

        // Set A input
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 10);
        panel.add(new JLabel("Set A:"), gbc);

        setAField = new JTextField(30);
        setAField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0);
        panel.add(setAField, gbc);

        // Set B input
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(5, 0, 5, 10);
        panel.add(new JLabel("Set B:"), gbc);

        setBField = new JTextField(30);
        setBField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0);
        panel.add(setBField, gbc);

        // Set C input
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(5, 0, 5, 10);
        panel.add(new JLabel("Set C:"), gbc);

        setCField = new JTextField(30);
        setCField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 1; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0);
        panel.add(setCField, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        calculateButton = new JButton("Calculate Cartesian Product");
        calculateButton.setBackground(new Color(0, 150, 0));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14));
        calculateButton.setPreferredSize(new Dimension(200, 35));

        clearButton = new JButton("Clear All");
        clearButton.setBackground(new Color(200, 100, 0));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setPreferredSize(new Dimension(100, 35));

        panel.add(calculateButton);
        panel.add(clearButton);

        return panel;
    }

    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        resultArea = new JTextArea(12, 50);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setBackground(Color.WHITE);
        resultArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Cartesian Product Result",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12)
        ));

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createEtchedBorder());

        statusLabel = new JLabel("Ready to calculate...");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        panel.add(statusLabel);

        return panel;
    }

    private void addActionListeners() {
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateCartesianProduct();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAll();
            }
        });

        // Add Enter key listeners to text fields
        ActionListener enterListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateCartesianProduct();
            }
        };

        setAField.addActionListener(enterListener);
        setBField.addActionListener(enterListener);
        setCField.addActionListener(enterListener);
    }

    private void calculateCartesianProduct() {
        try {
            // Parse input sets
            Set<String> setA = parseSet(setAField.getText(), "A");
            Set<String> setB = parseSet(setBField.getText(), "B");
            Set<String> setC = parseSet(setCField.getText(), "C");

            // Compute Cartesian product
            List<String> product = computeCartesianProduct(setA, setB, setC);

            // Display result
            displayResult(product, setA, setB, setC);

            statusLabel.setText("Calculation completed successfully! Total combinations: " + product.size());
            statusLabel.setForeground(new Color(0, 150, 0));

        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
            statusLabel.setForeground(Color.RED);
            resultArea.setText("Please fix the input errors and try again.");
        }
    }

    private Set<String> parseSet(String input, String setName) throws Exception {
        if (input == null || input.trim().isEmpty()) {
            throw new Exception("Set " + setName + " cannot be empty");
        }

        String[] elements = input.split(",");
        Set<String> set = new LinkedHashSet<>();

        for (String element : elements) {
            String trimmed = element.trim();
            if (!trimmed.isEmpty()) {
                set.add(trimmed);
            }
        }

        if (set.isEmpty()) {
            throw new Exception("Set " + setName + " has no valid elements");
        }

        if (set.size() > 10) {
            throw new Exception("Set " + setName + " has " + set.size() + " elements (maximum 10 allowed)");
        }

        return set;
    }

    private List<String> computeCartesianProduct(Set<String> setA, Set<String> setB, Set<String> setC) {
        List<String> product = new ArrayList<>();

        for (String a : setA) {
            for (String b : setB) {
                for (String c : setC) {
                    product.add("{" + a + "," + b + "," + c + "}");
                }
            }
        }

        return product;
    }

    private void displayResult(List<String> product, Set<String> setA, Set<String> setB, Set<String> setC) {
        StringBuilder result = new StringBuilder();

        result.append("INPUT SETS:\n");
        result.append("Set A: ").append(setA).append(" (").append(setA.size()).append(" elements)\n");
        result.append("Set B: ").append(setB).append(" (").append(setB.size()).append(" elements)\n");
        result.append("Set C: ").append(setC).append(" (").append(setC.size()).append(" elements)\n\n");

        result.append("CARTESIAN PRODUCT A × B × C:\n");
        result.append("Total combinations: ").append(product.size()).append("\n\n");

        if (product.size() <= 100) {
            // Display all combinations if not too many
            result.append("Result: [ ");
            for (int i = 0; i < product.size(); i++) {
                result.append(product.get(i));
                if (i < product.size() - 1) {
                    result.append(", ");
                    if ((i + 1) % 5 == 0) {
                        result.append("\n        ");
                    }
                }
            }
            result.append(" ]");
        } else {
            // Display first and last few if too many
            result.append("Result (showing first 20 and last 20 due to large size):\n");
            result.append("[ ");

            // First 20
            for (int i = 0; i < Math.min(20, product.size()); i++) {
                result.append(product.get(i));
                if (i < 19 && i < product.size() - 1) {
                    result.append(", ");
                    if ((i + 1) % 5 == 0) {
                        result.append("\n  ");
                    }
                }
            }

            if (product.size() > 20) {
                result.append(",\n  ...\n  ");

                // Last 20
                int start = Math.max(20, product.size() - 20);
                for (int i = start; i < product.size(); i++) {
                    result.append(product.get(i));
                    if (i < product.size() - 1) {
                        result.append(", ");
                        if ((i - start + 1) % 5 == 0) {
                            result.append("\n  ");
                        }
                    }
                }
            }

            result.append(" ]");
        }

        resultArea.setText(result.toString());
        resultArea.setCaretPosition(0);
    }

    private void clearAll() {
        setAField.setText("");
        setBField.setText("");
        setCField.setText("");
        resultArea.setText("");
        statusLabel.setText("Ready to calculate...");
        statusLabel.setForeground(Color.BLACK);
        setAField.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getLookAndFeel());
                } catch (Exception e) {
                    // Use default look and feel if system L&F is not available
                }

                new CartesianProductCalculatorGUI().setVisible(true);
            }
        });
    }
}