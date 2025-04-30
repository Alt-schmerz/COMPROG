package src.main.java.banking.attached_assets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUImain extends JFrame implements ActionListener {
    private JButton createAccountButton;
    private JButton balanceInquiryButton;
    private JButton depositButton;
    private JButton accountInformationButton;
    private JButton withdrawButton;
    private JButton moneyTransferButton;
    private JButton closeAccountButton;
    private JButton exitButton;
    private JPanel mainPanel;

    public GUImain(){
        setBackground(Color.CYAN);
        setContentPane(mainPanel);
        setTitle("SABOG FINANCE");
        setSize(500,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AccountTypes accType = new AccountTypes();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AccountTypes accType = new AccountTypes();
            }
        });
        moneyTransferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AccountTypes accType = new AccountTypes();
            }
        });
        balanceInquiryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AccountTypes accType = new AccountTypes();
            }
        });
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AccountTypes accType = new AccountTypes();
            }
        });
        accountInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        closeAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
