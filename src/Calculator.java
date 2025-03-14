import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator implements ActionListener{

    JFrame frame;
    JTextField textField;
    JButton[] numberButtons = new JButton[10];
    JButton[] functionButtons = new JButton[8];
    JButton addButton, subsButton, multButton, divButton,
            deciButton, equalButton, delButton, clearButton;
    JPanel panel;

    Font myFont = new Font("New Times Roman", Font.BOLD, 30);

    double num1=0, num2, result=0;
    char operator = ' ';
    boolean negativeSwitch = false;

    Calculator() {

        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 550);
        frame.setLayout(null);

        textField = new JTextField();
        textField.setBounds(75, 25, 300, 50);
        textField.setFont(myFont);
        textField.setEditable(false);


        addButton = new JButton("+");
        subsButton = new JButton("-");
        multButton = new JButton("*");
        divButton = new JButton("/");
        deciButton = new JButton(".");
        equalButton = new JButton("=");
        delButton = new JButton("⌫");
        clearButton = new JButton("Clear");

        functionButtons[0] = addButton;
        functionButtons[1] = subsButton;
        functionButtons[2] = multButton;
        functionButtons[3] = divButton;
        functionButtons[4] = deciButton;
        functionButtons[5] = equalButton;
        functionButtons[6] = delButton;
        functionButtons[7] = clearButton;

        for (JButton functionButton : functionButtons) {
            functionButton.addActionListener(this);
            functionButton.setFont(myFont);
            functionButton.setFocusable(false);
        }

        for(int i=0; i<numberButtons.length;i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(myFont);
            numberButtons[i].setFocusable(false);
        }
        delButton.setBounds(75,460, 145, 50);
        clearButton.setBounds(225, 460, 145, 50);

        panel = new JPanel();
        panel.setBounds(75, 120, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10));
        //panel.setBackground(Color.GRAY);
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subsButton);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(multButton);
        panel.add(deciButton);
        panel.add(numberButtons[0]);
        panel.add(equalButton);
        panel.add(divButton);

        frame.add(panel);
        frame.add(delButton);
        frame.add(clearButton);
        frame.add(textField);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for(int i =0; i<10;i++) {
            if (e.getSource() == numberButtons[i]) {
                if(textField.getText().indexOf("0",textField.getText().length()-1) !=0) {
                    textField.setText(textField.getText().concat(String.valueOf(i)));
                }
                else if(textField.getText().charAt(0)=='-' && negativeSwitch) {
                    subsButton.setEnabled(false);
                }
                else textField.setText(String.valueOf(i));
            }

        }

        if(e.getSource()==deciButton && /*multiple decimal points should not be entered at any times*/
                !(textField.getText().contains("."))) {

            //if the first input is a decimal point, then a zero will be added before that.
            if(textField.getText().isEmpty()) {
                textField.setText("0.");
            }
            else if(textField.getText().charAt(0)=='-' && textField.getText().length()==1) {
                textField.setText("-0.");
            }
            else textField.setText(textField.getText().concat("."));
        }

        if(e.getSource()==addButton && !textField.getText().isEmpty()) {

            //num1 = Double.parseDouble(textField.getText());
            disableOperations();
            parseOperation();
            operator = '+';
            textField.setText("");

        }

        if(e.getSource()== subsButton) {
            if(textField.getText().isEmpty()) {
                textField.setText("-");
            }
            else if(textField.getText().length() > 1){
                //num1 = Double.parseDouble(textField.getText());
                parseOperation();
                operator = '-';
                textField.setText("");
                disableOperations();
            }
            else if(negativeSwitch) {
                textField.setText("-");
            }
            if(negativeSwitch && textField.getText().indexOf("-") == 0) {
                subsButton.setEnabled(false);
            }
        }

        if(e.getSource()== multButton && !textField.getText().isEmpty()) {
            //num1 = Double.parseDouble(textField.getText());
            parseOperation();
            operator = '*';
            textField.setText("");
            disableOperations();
        }

        if(e.getSource()==divButton && !textField.getText().isEmpty()) {
            //num1 = Double.parseDouble(textField.getText());
            parseOperation();
            operator = '/';
            textField.setText("");
            disableOperations();
        }
        if(e.getSource()==equalButton && !textField.getText().isEmpty() && operator != ' ') {
            num2 = Double.parseDouble(textField.getText());
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    result = num1 / num2;
                    break;
                default:
                    result = 0;
                    break;
            }

            textField.setText(String.valueOf(result));
            operator = ' ';
            num1 = result;
            enableOperations();
            if(result ==Double.valueOf(result).intValue()) {
                textField.setText(String.valueOf(Double.valueOf(result).intValue()));
            }

        }
        if(e.getSource()==clearButton) {
            textField.setText("");
            num1=0;
            num2=0;
            result=0;
            enableOperations();

        }
        if(e.getSource()==delButton) {
            deleteOperation();
        }
    }
    public void deleteOperation() {
        String temp = textField.getText();
        textField.setText("");
        if (temp.charAt(temp.length() - 1) == '-') {
            subsButton.setEnabled(true);
        }
        for (int i = 0; i < temp.length() - 1; i++) {
            textField.setText(textField.getText() + temp.charAt(i));
        }

    }
    public void enableOperations() {
        addButton.setEnabled(true);
        subsButton.setEnabled(true);
        multButton.setEnabled(true);
        divButton.setEnabled(true);

        negativeSwitch = false;
    }
    public void disableOperations() {
        addButton.setEnabled(false);
        multButton.setEnabled(false);
        divButton.setEnabled(false);

        negativeSwitch = true;
    }

    public void parseOperation() {
        try {
            num1 = Double.parseDouble(textField.getText());
        } catch (Exception ex) {
            //throw new RuntimeException(ex);
            enableOperations();
        }
    }
}
