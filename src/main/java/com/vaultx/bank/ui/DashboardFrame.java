package com.vaultx.bank.ui;

import com.vaultx.bank.dao.AccountDAO;
import com.vaultx.bank.dao.TransactionDAO;
import com.vaultx.bank.model.Account;
import com.vaultx.bank.model.Transaction;
import com.vaultx.bank.service.AuthService;
import com.vaultx.bank.service.BankService;
import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private AuthService authService;
    private BankService bankService;
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainContent = new JPanel(cardLayout);
    private AccountDAO accountDAO;
    private JComboBox<Account> comboAccounts = new JComboBox<>();
    private TransactionDAO transactionDAO;
    private HistoryPanel historyPanel;
    private HomePanel homePanel;



    public DashboardFrame(AuthService authService, BankService bankService, AccountDAO accountDAO, TransactionDAO transactionDAO) {
        this.authService = authService;
        this.bankService = bankService;
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
        this.homePanel = new HomePanel(authService, accountDAO);

        setTitle("VaultX - Welcome " + AuthService.getCurrentUser().getFullName());
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel sidebar = createSidebar();

        this.historyPanel = new HistoryPanel();

        mainContent.add(new HomePanel(authService, accountDAO), "HOME");
        mainContent.add(new TransferPanel(bankService, accountDAO), "TRANSFER");
        mainContent.add(historyPanel, "HISTORY");

        add(sidebar, BorderLayout.WEST);
        add(mainContent, BorderLayout.CENTER);

        loadAccounts();
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(10, 1, 5, 5)); // 10 rows, 1 column, with gaps
        sidebar.setBackground(new Color(33, 47, 61)); // Dark Slate
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton btnHome = createSidebarButton("Home");
        JButton btnTransfer = createSidebarButton("Transfer");
        JButton btnHistory = createSidebarButton("History");
        JButton btnLogout = createSidebarButton("Logout");

        btnHome.addActionListener(e -> {
            homePanel.refreshAccounts();
            cardLayout.show(mainContent, "HOME");
        });

        btnHistory.addActionListener(e -> {
            int userId = AuthService.getCurrentUser().getUserId();
            java.util.List<Account> accounts = accountDAO.getAccountsByUserId(userId);

            if (!accounts.isEmpty()) {
                java.util.List<Transaction> history = transactionDAO.getHistoryByAccountId(accounts.get(0).getAccId());
                historyPanel.setTransactions(history);
            }
            cardLayout.show(mainContent, "HISTORY");
        });

        btnTransfer.addActionListener(e -> cardLayout.show(mainContent, "TRANSFER"));

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                authService.logout();
                new LoginFrame(authService, bankService, accountDAO, transactionDAO).setVisible(true); // Return to gate
                this.dispose();
            }
        });

        sidebar.add(btnHome);
        sidebar.add(btnTransfer);
        sidebar.add(btnHistory);
        sidebar.add(new JLabel("")); // Spacer
        sidebar.add(btnLogout);

        return sidebar;
    }
    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);

        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);

        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effect: Shows you know how to handle UX events manually
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setContentAreaFilled(true);
                btn.setBackground(new Color(52, 73, 94));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setContentAreaFilled(false);
            }
        });

        return btn;
    }

    private void loadAccounts() {
        int userId = AuthService.getCurrentUser().getUserId();
        java.util.List<com.vaultx.bank.model.Account> accounts = accountDAO.getAccountsByUserId(userId);

        for (Account acc : accounts) {
            comboAccounts.addItem(acc);
        }
    }
}
