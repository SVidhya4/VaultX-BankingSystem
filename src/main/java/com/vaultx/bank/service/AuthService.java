package com.vaultx.bank.service;

import com.vaultx.bank.dao.UserDAO;
import com.vaultx.bank.model.User;

public class AuthService {
    private final UserDAO userDAO; // The "Battery" for this service
    private static User currentUser;

    // DATA INJECTION HAPPENS HERE
    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean login(String username, String password) {
        User user = userDAO.loginUser(username, password);
        if (user != null) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public static User getCurrentUser() { return currentUser; }

    public void logout() { currentUser = null; }
}