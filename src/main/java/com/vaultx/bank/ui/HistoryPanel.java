package com.vaultx.bank.ui;

import com.vaultx.bank.model.Transaction;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class HistoryPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public HistoryPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(242, 244, 246));
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel lblTitle = new JLabel("Transaction History", JLabel.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(lblTitle, BorderLayout.NORTH);

        String[] columnNames = {"Txn ID", "Type", "Amount (₹)", "Description", "Timestamp"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setGridColor(new Color(230, 230, 230));
        table.setShowVerticalLines(false); // Clean modern look

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(245, 245, 245));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        table.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        scrollPane.getViewport().setBackground(Color.WHITE);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void setTransactions(List<Transaction> transactions) {
        tableModel.setRowCount(0);
        for (Transaction txn : transactions) {
            Object[] row = {
                    txn.getAccId(),
                    txn.getTxnType(),
                    String.format("%.2f", txn.getAmount()),
                    txn.getDescription(),
                    txn.getTimestamp()
            };
            tableModel.addRow(row);
        }
    }
}