/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.AccountService;
import models.User;

/**
 *
 * @author 821052
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String logout = request.getParameter("logout");

        if (logout != null) {
            session.invalidate();
            session = request.getSession();
        }

        String username = (String) session.getAttribute("username");

        if (username != null) {
            response.sendRedirect("home");
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username.equals("") && password.equals("")) {

            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);

            return;
        }
        AccountService accountService = new AccountService();
        User user = accountService.login(username, password);

        if (user != null) {

            session.setAttribute("username", user.getUsername());
            response.sendRedirect("home");

        } else {

            request.setAttribute("message", "Login Invalid");
            request.setAttribute("username", username);
            request.setAttribute("password", password);

            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }

    }
}
