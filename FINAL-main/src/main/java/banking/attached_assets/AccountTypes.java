package src.main.java.banking.attached_assets;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountTypes implements ActionListener {
    private JPanel Types;
    private JButton bankAccountsButton;
    private JButton investmentAccountsButton;
    private JButton checkingAccountsButton;
    private JButton creditCardAccountsButton;
    private JButton backButton;
    JFrame typeFrame = new JFrame();

    public AccountTypes(){
        typeFrame.setContentPane(Types);
        typeFrame.setTitle("SABOG FINANCE");
        typeFrame.setSize(500,500);
        typeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        typeFrame.setVisible(true);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typeFrame.dispose();
                GUImain guiMain = new GUImain();
            }
        });
        bankAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        checkingAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        investmentAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        creditCardAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
