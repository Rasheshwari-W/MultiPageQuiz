package com.quizapp.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.quizapp.db.DBConnection;
import com.quizapp.model.Question;
import com.quizapp.model.User;
import com.quizapp.util.QuestionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                User user = new User(rs.getInt("id"), username, password, rs.getString("email"));
                session.setAttribute("user", user);

                // Store random questions in session (change 5 to 3 if you want 3 Qs)
                List<Question> quizQuestions = QuestionUtil.getRandomQuestions(5);
                session.setAttribute("questions", quizQuestions);
                session.setAttribute("currentIndex", 0);
                session.setAttribute("answers", new java.util.HashMap<Integer, String>());

                response.sendRedirect("question");
            } else {
                response.getWriter().println("Invalid credentials.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

