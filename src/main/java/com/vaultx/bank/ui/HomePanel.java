package com.vaultx.bank.ui;

import com.vaultx.bank.dao.AccountDAO;
import com.vaultx.bank.model.Account;
import com.vaultx.bank.service.AuthService;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HomePanel extends JPanel {
    private AccountDAO accountDAO;
    private JPanel listContainer = new JPanel();
    private JLabel lblTotalBalance = new JLabel("₹0.00");
    private Color virtusaBlue = new Color(0, 161, 225);

    public HomePanel(AuthService authService, AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(createHeader(), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(Color.WHITE);
        body.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel contentStack = new JPanel();
        contentStack.setLayout(new BoxLayout(contentStack, BoxLayout.Y_AXIS));
        contentStack.setOpaque(false);

        JLabel lblSection = new JLabel("All Accounts");
        lblSection.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblSection.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSection.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JPanel tableHeader = createTableHeader();
        tableHeader.setAlignmentX(Component.LEFT_ALIGNMENT);

        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setBackground(Color.WHITE);
        listContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentStack.add(lblSection);
        contentStack.add(tableHeader);
        contentStack.add(listContainer);

        body.add(contentStack, BorderLayout.NORTH);

        add(new JScrollPane(body), BorderLayout.CENTER);
        refreshAccounts();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, virtusaBlue, getWidth(), 0, new Color(0, 120, 215));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setPreferredSize(new Dimension(0, 120));
        header.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        left.setOpaque(false);

        JPanel avatar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillOval(0, 0, 50, 50);
                g2.setColor(virtusaBlue);
                g2.drawString("SV", 17, 30); // Initials
            }
        };
        avatar.setPreferredSize(new Dimension(50, 50));
        avatar.setOpaque(false);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        JLabel lblGreet = new JLabel("Welcome back, " + AuthService.getCurrentUser().getFullName() + "!");
        lblGreet.setForeground(Color.WHITE);
        lblGreet.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel lblSub = new JLabel("Your last login was today");
        lblSub.setForeground(new Color(230, 230, 230));
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        textPanel.add(lblGreet);
        textPanel.add(lblSub);

        left.add(avatar);
        left.add(textPanel);

        // Right Side: Total Balance
        JPanel right = new JPanel(new GridLayout(2, 1));
        right.setOpaque(false);
        JLabel lblTag = new JLabel("Total Balance", JLabel.RIGHT);
        lblTag.setForeground(Color.WHITE);
        lblTotalBalance.setForeground(Color.WHITE);
        lblTotalBalance.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTotalBalance.setHorizontalAlignment(JLabel.RIGHT);

        right.add(lblTag);
        right.add(lblTotalBalance);

        header.add(left, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        return header;
    }

    private JPanel createTableHeader() {
        JPanel row = new JPanel(new GridLayout(1, 4));
        row.setBackground(new Color(250, 250, 250));
        row.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"Type", "Account Number", "Status", "Currency"};
        for (String col : cols) {
            JLabel lbl = new JLabel(col);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lbl.setForeground(Color.GRAY);
            row.add(lbl);
        }
        return row;
    }

    public void refreshAccounts() {
        listContainer.removeAll();
        double total = 0;
        List<Account> accounts = accountDAO.getAccountsByUserId(AuthService.getCurrentUser().getUserId());

        for (Account acc : accounts) {
            total += acc.getBalance();
            listContainer.add(createAccountRow(acc));
        }

        lblTotalBalance.setText("₹" + String.format("%,.2f", total));
        revalidate();
        repaint();
    }

    private JPanel createAccountRow(Account acc) {
        JPanel row = new JPanel(new GridLayout(1, 4));
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 240)),
                BorderFactory.createEmptyBorder(15, 10, 15, 10)
        ));

        row.add(new JLabel(acc.getClass().getSimpleName().replace("Account", "")));
        row.add(new JLabel(acc.getAccountNumber()));

        JLabel lblStatus = new JLabel("Active");
        lblStatus.setForeground(new Color(39, 174, 96)); // Green
        row.add(lblStatus);

        row.add(new JLabel("INR"));

        return row;
    }
}