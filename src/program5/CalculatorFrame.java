
package program5;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class CalculatorFrame extends JFrame{
    private final int HEIGHT = 285;
    private final int WIDTH = 400;
    private JTextField calcBar;
    private JPanel panel1, panel2, panel3;
    private int operation = -1; // 0 = plus, 1 = minus, 2 = times, 3 = divided by
    private BigDecimal answer = BigDecimal.ZERO;
    private boolean firstOperation = true;
    private boolean reset = true;
    
    public CalculatorFrame(String title)
    {
        super(title);
        this.setLayout(null);
        this.setSize(new Dimension(WIDTH, HEIGHT));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel1 = new MainButtonBuilder();
        panel2 = new TopButtonBuilder();
        panel3 = new SideBarBuilder();
        
        calcBar = new JTextField();
        calcBar.setEditable(false);
        calcBar.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        calcBar.setBackground(Color.white);
        calcBar.setBounds(6, 20, 382, 25);
        calcBar.setHorizontalAlignment(JTextField.RIGHT);
        
        JLabel editLabel = new JLabel("Edit");
        editLabel.setBounds(10, 1, 100, 20);
        editLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        JLabel viewLabel = new JLabel("View");
        viewLabel.setBounds(50, 1, 100, 20);
        viewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        JLabel helpLabel = new JLabel("Help");
        helpLabel.setBounds(95, 1, 100, 20);
        helpLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        add(editLabel);
        add(viewLabel);
        add(helpLabel);
        add(calcBar);
        add(panel1);
        add(panel2);
        add(panel3);
    }
    
    public class MainButtonBuilder extends JPanel
    {
        JButton[] buttons;
        public MainButtonBuilder()
        {
            this.setBounds(60, 90, 330, 160);
            this.setLayout(new GridLayout(4, 5, 3, 3));
            buttons = new JButton[20];
            
            for(int i = 0; i < buttons.length; i++)
            {
                buttons[i] = new JButton();
                buttons[i].setFont(new Font("Times New Roman", Font.PLAIN, 14));
                if(i % 5 < 3 && i / 5 < 3)
                {
                    buttons[i].setText(Integer.toString((3 * (i / 5)) + (i % 5) + 1));
                    buttons[i].setForeground(Color.blue);
                    buttons[i].addActionListener(new TextboxAdder());
                }
            }
            
            buttons[3].setText("/");
            buttons[3].addActionListener(new OperationExecuted());
            buttons[4].setText("sqrt");
            buttons[4].addActionListener(new TextboxChanger());
            buttons[8].setText("*");
            buttons[8].addActionListener(new OperationExecuted());
            buttons[9].setText("%");
            buttons[9].addActionListener(new TextboxChanger());
            buttons[13].setText("-");
            buttons[13].addActionListener(new OperationExecuted());
            buttons[14].setText("1/x");
            buttons[14].addActionListener(new TextboxChanger());
            buttons[15].setText("0");
            buttons[15].addActionListener(new TextboxAdder());
            buttons[16].setText("-/+");
            buttons[16].addActionListener(new TextboxAdder());
            buttons[17].setText(".");
            buttons[17].addActionListener(new TextboxAdder());
            buttons[18].setText("+");
            buttons[18].addActionListener(new OperationExecuted());
            buttons[19].setText("=");
            buttons[19].addActionListener(new OperationExecuted());
            
            for (JButton button : buttons) {
                add(button);
            }
        }
    }
    
    public class TopButtonBuilder extends JPanel
    {
        public JButton[] buttons;
        public TopButtonBuilder()
        {
            this.setBounds(60, 50, 329, 38);
            this.setLayout(new GridLayout(1, 3, 3, 3));
            buttons = new JButton[3];
            
            for(int i = 0; i < buttons.length; i++)
            {
                buttons[i] = new JButton();
                buttons[i].setFont(new Font("Times New Roman", Font.PLAIN, 14));
            }
            
            buttons[0].setText("Backspace");
            buttons[0].setMargin(new Insets(0,0,0,0));
            buttons[0].addActionListener(new TextboxRemover());
            buttons[1].setText("CE");
            buttons[1].addActionListener(new TextboxRemover());
            buttons[2].setText("C");
            buttons[2].addActionListener(new TextboxRemover());
            
            for (JButton button : buttons) {
                add(button);
            }
        }
    }
    
    public class SideBarBuilder extends JPanel
    {
        JButton[] buttons;
        JTextField memoryHolder;
        public SideBarBuilder()
        {
            this.setBounds(6, 50, 53, 200);
            this.setLayout(new GridLayout(5, 1, 3, 3));
            memoryHolder = new JTextField();
            memoryHolder.setEditable(false);
            memoryHolder.setHorizontalAlignment(JTextField.CENTER);
            memoryHolder.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            
            buttons = new JButton[4];
            
            for(int i = 0; i < buttons.length; i++)
            {
                buttons[i] = new JButton();
                buttons[i].setFont(new Font("Times New Roman", Font.PLAIN, 14));
                buttons[i].setMargin(new Insets(0,0,0,0));
            }
            
            buttons[0].setText("MC");
            buttons[0].addActionListener(new MemoryOperation());
            buttons[1].setText("MR");
            buttons[1].addActionListener(new MemoryOperation());
            buttons[2].setText("MS");
            buttons[2].addActionListener(new MemoryOperation());
            buttons[3].setText("M+");
            buttons[3].addActionListener(new MemoryOperation());
            
            add(memoryHolder);
            for (JButton button : buttons) {
                add(button);
            }
            
        }
    }
    
    public class TextboxChanger implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
            JButton button = (JButton)(e.getSource());
            BigDecimal input = new BigDecimal(Double.parseDouble(calcBar.getText()));
            if(button.getText().equals("sqrt"))
            {
                DecimalFormat df = new DecimalFormat("0.####################");
                calcBar.setText(df.format(Math.sqrt(Integer.parseInt(calcBar.getText()))));
                reset = true;
            }
            else if(button.getText().equals("%"))
            {
                DecimalFormat df = new DecimalFormat("0.########################################");
                calcBar.setText(df.format(input.divide(new BigDecimal(100))));
                reset = true;
            }
            else if(button.getText().equals("1/x"))
            {
                if(calcBar.getText().equals("0"))
                {
                    calcBar.setText("Undefined");
                }
                else
                {
                    DecimalFormat df = new DecimalFormat("0.########################################");
                    calcBar.setText(df.format(BigDecimal.ONE.divide(input, 40, RoundingMode.FLOOR)));
                }
                reset = true;
            }
        }
    }
    
    public class TextboxAdder implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            JButton button = (JButton)(e.getSource());
            if(button.getText().equals("-/+"))
            {
                if(reset)
                {
                    if(calcBar.getText().contains("-"))
                    {
                        calcBar.setText("0");
                    }
                    else
                    {
                        calcBar.setText("-0");
                    }
                }
                else
                {
                    if(calcBar.getText().contains("-"))
                    {
                        calcBar.setText(calcBar.getText().substring(1));
                    }
                    else
                    {
                        calcBar.setText("-" + calcBar.getText());
                    }
                }
            }
            else if((!button.getText().equals(".") || !calcBar.getText().contains(".")) || (button.getText().equals("0") && !calcBar.getText().contains("0")))
            {
                if(reset)
                {
                    calcBar.setText(button.getText());
                    reset = false;
                }
                else
                {
                    calcBar.setText(calcBar.getText() + button.getText());
                }
            }
        }
    }
    
    public class TextboxRemover implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            JButton button = (JButton)(e.getSource());
            if(button.getText().equals("C"))
            {
                calcBar.setText("");
                answer = BigDecimal.ZERO;
                
            }
            else if(button.getText().equals("Backspace"))
            {
                
            }
        }
    }
    
    public class MemoryOperation implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae) {
        }
        
    }
    
    public class OperationExecuted implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            JButton button = (JButton)(e.getSource());
            if(button.getText().equals("+"))
            {
                storeAnswer(0);
            }
            else if(button.getText().equals("-"))
            {
                storeAnswer(1);
            }
            else if(button.getText().equals("*"))
            {
                storeAnswer(2);
            }
            else if(button.getText().equals("/"))
            {
                storeAnswer(3);
            }
            else if(button.getText().equals("="))
            {
                String temp = calcBar.getText();
                    switch(operation)
                    {
                        case(0):
                            calcBar.setText(answer.add(new BigDecimal(calcBar.getText())).toString());
                            break;
                        case(1):
                            calcBar.setText(answer.subtract(new BigDecimal(calcBar.getText())).toString());
                            break;
                        case(2):
                            calcBar.setText(answer.multiply(new BigDecimal(calcBar.getText())).toString());
                            break;
                        case(3):
                            calcBar.setText(answer.divide(new BigDecimal(calcBar.getText()), 20, RoundingMode.CEILING).toString());
                            break;
                    }
                if(firstOperation)
                {
                    answer = new BigDecimal(temp);
                    firstOperation = false;
                }
                reset = true;
            }
        }
        
        public void storeAnswer(int i)
        {
            operation = i;
            try
            {
                answer = new BigDecimal(calcBar.getText());
            }
            catch(NumberFormatException e)
            {
                JOptionPane.showMessageDialog(null, calcBar.getText() + " is apparently not a number.");
            }
            calcBar.setText("");
        }
    }
}
