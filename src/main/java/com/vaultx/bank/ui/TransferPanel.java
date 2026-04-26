package com.vaultx.bank.ui;

import com.vaultx.bank.dao.AccountDAO;
import com.vaultx.bank.model.Account;
import com.vaultx.bank.service.AuthService;
import com.vaultx.bank.service.BankService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TransferPanel extends JPanel {
    private BankService bankService;
    private AccountDAO accountDAO;

    private JComboBox<Account> fromAccountCombo = new JComboBox<>();
    private JTextField txtToAccount = new JTextField(15);
    private JLabel lblReceiverName = new JLabel("Enter account number to verify...");
    private JTextField txtAmount = new JTextField(10);
    private JPasswordField txtPin = new JPasswordField(10);
    private JButton btnTransfer = new JButton("Confirm Transfer");

    public TransferPanel(BankService bankService, AccountDAO accountDAO) {
        this.bankService = bankService;
        this.accountDAO = accountDAO;

        setLayout(new GridBagLayout());
        setBackground(new Color(242, 244, 246));

        JPanel transferCard = new JPanel(new GridBagLayout());
        transferCard.setBackground(Color.WHITE);
        transferCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // From Account
        gbc.gridx = 0; gbc.gridy = 0; transferCard.add(new JLabel("From Account:"), gbc);
        gbc.gridx = 1; transferCard.add(fromAccountCombo, gbc);

        // To Account
        gbc.gridx = 0; gbc.gridy = 1; transferCard.add(new JLabel("To Account:"), gbc);
        gbc.gridx = 1; transferCard.add(txtToAccount, gbc);

        // Receiver Name Label
        gbc.gridx = 1; gbc.gridy = 2;
        lblReceiverName.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        transferCard.add(lblReceiverName, gbc);

        // Amount
        gbc.gridx = 0; gbc.gridy = 3; transferCard.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1; transferCard.add(txtAmount, gbc);

        // PIN
        gbc.gridx = 0; gbc.gridy = 4; transferCard.add(new JLabel("Transaction PIN:"), gbc);
        gbc.gridx = 1; transferCard.add(txtPin, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 0, 10);
        btnTransfer.setBackground(new Color(41, 128, 185));
        btnTransfer.setForeground(Color.WHITE);
        btnTransfer.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnTransfer.setFocusPainted(false);
        btnTransfer.setBorderPainted(false);
        btnTransfer.setOpaque(true);
        transferCard.add(btnTransfer, gbc);

        add(transferCard);

        setupListeners();
        loadUserAccounts();
    }

    private void setupListeners() {
        txtToAccount.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String name = accountDAO.getAccountHolderName(txtToAccount.getText());
                if (name != null) {
                    lblReceiverName.setText("Recipient: " + name);
                    lblReceiverName.setForeground(new Color(39, 174, 96));
                } else {
                    lblReceiverName.setText("Invalid Account Number!");
                    lblReceiverName.setForeground(Color.RED);
                }
            }
        });

        btnTransfer.addActionListener(e -> handleTransfer());
    }

    private void handleTransfer() {
        try {
            Account selected = (Account) fromAccountCombo.getSelectedItem();
            double amount = Double.parseDouble(txtAmount.getText());
            String pin = new String(txtPin.getPassword());

            String result = bankService.processTransfer(selected, txtToAccount.getText(), amount, pin);
            JOptionPane.showMessageDialog(this, result);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
        }
    }

    private void loadUserAccounts() {
        int userId = AuthService.getCurrentUser().getUserId();
        java.util.List<Account> accounts = accountDAO.getAccountsByUserId(userId);
        fromAccountCombo.removeAllItems();
        for (Account acc : accounts) {
            fromAccountCombo.addItem(acc);
        }
    }
}