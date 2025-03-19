package yorku.eecs3311.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {
    //VARIABLES
    Container container;
    CardLayout layout;
    JPanel mainPanel;
    JPanel test1, test2, test3;

    JButton testButton;

    //INNIT
    App(){
        mainPanel = new JPanel();
        layout = new CardLayout();
        mainPanel.setLayout(layout);

        testButton = new JButton();
        testButton.setText("NEXT PAGE");
        testButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                layout.next(mainPanel);
            }
        });

        test1 = new JPanel();
        test1.setBackground(Color.BLACK);

        test2 = new JPanel();
        test2.setBackground(Color.BLUE);

        test3 = new JPanel();
        test3.setBackground(Color.RED);

        mainPanel.add("test1", test1);
        mainPanel.add("test2", test2);
        mainPanel.add("test3", test3);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        add(testButton, BorderLayout.NORTH);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public boolean addPanel(JPanel p, String name){

        return true;
    }
}
