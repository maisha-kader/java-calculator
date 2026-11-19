import javax.swing.*;  //Swing GUI classes (JFrame, JTextField, JButton, JPanel, etc.)
import java.awt.*;     //AWT classes for layout managers and fonts (BorderLayout, GridLayout, Font)
import java.awt.event.ActionEvent; //created when a button is clicked
import java.awt.event.ActionListener; //interface implemented to receive said events


public  class Calculator extends JFrame implements ActionListener {
    JTextField display;
    double firstNumber = 0;
    String operator = "";
    boolean newNumber = true;

    double lastAnswer = 0;
    String expression = "";


    public Calculator () {
        setTitle("Calculator"); //to appear in the window's title bar
        setSize(550,450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        getContentPane().setBackground(Color.BLACK);


        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.BLACK);
        JLabel nameLabel = new JLabel("Womp Womp");
        nameLabel.setForeground(Color.DARK_GRAY);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(-45, 10,0,0));
        nameLabel.setFont(new Font("Dialog Input", Font.BOLD, 12));
        headerPanel.add(nameLabel, BorderLayout.WEST);


        display = new JTextField("0");   //building the display
        display.setFont(new Font("Arial", Font.BOLD, 42));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false); //makes it read-only


        //Styling the entire display
        display.setBackground(Color.BLACK);
        display.setForeground(Color.WHITE);
        display.setCaretColor(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        headerPanel.add(display, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);


        //Styling the buttons
        JPanel panel = new JPanel();  //invisible container we use to arrange the buttons.
        panel.setLayout(new GridLayout(5, 4, 5, 5)); // rows, cols, hgap, vgap; 5-px gaps between buttons horizontally and vertically.
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));


        String[] buttons = {
                "↑", "C", "%", "÷",
                "7", "8", "9", "x",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "☻", "0", ".", "=",
        };


        for (String text : buttons) {    //building a JButton
            JButton b = new JButton(text);
            b.setFont(new Font("Arial", Font.BOLD, 22));


            b.setBackground(Color.BLACK);
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setOpaque(true);
            b.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); //thin white line to show the button edges


            b.addActionListener(this);
            panel.add(b);
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();

        if (button.equals("↑")) {
            display.setText(String.valueOf(lastAnswer));
            expression = "";
            newNumber = true;

        } else if (button.equals("C")) {
            display.setText("0");
            firstNumber = 0;
            operator = "";
            expression = "";
            newNumber = true;


        } else if (button.equals("+") || button.equals("-") || button.equals("x") || button.equals("÷") || button.equals("%")) {
            if (!operator.isEmpty() && !newNumber) {
                String currentNuString = display.getText().replace(expression, "");
                double secondNumber = Double.parseDouble(currentNuString);
                calculate(firstNumber, secondNumber);
            } else if (newNumber && !operator.isEmpty()) {
            } else {
                firstNumber = Double.parseDouble(display.getText());
            }


            operator = button;
            newNumber = true;
            expression = formatNumber(firstNumber) + " " + button + " ";
            display.setText(expression);


        } else if (button.equals("=")) {
            if (!operator.isEmpty()) {
                String currentNumString = display.getText().replace(expression, "");
                double secondNumber = Double.parseDouble(currentNumString);
                calculate(firstNumber, secondNumber);
                lastAnswer = firstNumber;
                expression = "";
                operator = "";
                newNumber = true;
            }



        } else if (button.equals(".")) {
            if (newNumber) {
                display.setText(expression + "0.");
                newNumber = false;
            } else if (!display.getText().contains(".")){
                display.setText(display.getText() + button);
            }

        } else if (button.equals("☻")){
            display.setText("☻☻☻");


        } else {
            if (newNumber) {
                display.setText(expression + button);
                newNumber = false;
            } else {
                display.setText(display.getText() + button);
            }
        }

    }


    private void calculate(double num1, double num2) {
        double result = 0;

        if (operator.equals("+")) result = num1 + num2;
        if (operator.equals("-")) result = num1 - num2;
        if (operator.equals("x")) result = num1 * num2;
        if (operator.equals("÷")) {
            if (num2 == 0) {
                display.setText("Error");
                firstNumber = 0;
                operator = "";
                return;
            }
            result = num1 / num2;
        }

        if (operator.equals("%")) result = num1 % num2;

        display.setText(formatNumber(result));
        firstNumber = result;
    }

    private String formatNumber (double num) {
        if (num == Math.floor(num) && !Double.isInfinite(num)) {
            return String.valueOf((int) num);
        } else {
            return String.valueOf(num);
        }
    }



    public static void main(String[] args) {
        new Calculator();
    }
}