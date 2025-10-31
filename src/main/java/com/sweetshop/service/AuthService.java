package com.sweetshop.service;

import com.sweetshop.dao.UserDAO;
import com.sweetshop.model.User;

public class AuthService {

    private UserDAO userDAO;

    public AuthService() {
        userDAO = new UserDAO();
    }

    public User login(String username, String password) {
        return userDAO.validateUser(username, password);
    }
}
