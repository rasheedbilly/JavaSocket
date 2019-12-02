package terminal;

import javax.swing.*;

/**
 *
 * @author Rasheed
 */
public class Window {
    
    public JTextArea serverArea, clientArea, clientInput;
    public JButton submit;
    
    private JPanel panel;
    private JFrame f;
    private final int WIDTH = 800, HEIGHT = 600;

    public Window() {
        initPanel();
        f = new JFrame("Testing");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(WIDTH, HEIGHT);
        f.setResizable(false);
        f.add(panel);
        
        f.setLocationRelativeTo(null);
        f.setVisible(true);

    }

    private void initPanel() {
        //Init Panel
        panel = new JPanel();
        panel.setLayout(null);

        //Server Box
        serverArea = new JTextArea();
        serverArea.setEditable(false);
        serverArea.setBounds(10, 10, 360, 400);
        serverArea.setLineWrap(true);
        panel.add(serverArea);

        //Client Box
        clientArea = new JTextArea();
        clientArea.setEditable(false);
        clientArea.setBounds(400, 10, 380, 400);
        clientArea.setLineWrap(true);
        panel.add(clientArea);
        
        //Client Input Box
        clientInput = new JTextArea();
        clientInput.setBounds(400, 470, 380, 20);
        clientInput.setLineWrap(true);
        panel.add(clientInput);
        
        submit = new JButton();
        submit.setName("Submit");
        submit.setBounds(600, 500, 100, 20);
        panel.add(submit);
        

    }

}
