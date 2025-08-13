package com.quizapp.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.quizapp.db.DBConnection;
import com.quizapp.model.Question;
import com.quizapp.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/finalScore")
public class FinalScoreServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("index.html");
            return;
        }

        List<Question> questions = (List<Question>) session.getAttribute("questions");
        HashMap<Integer, String> answers = (HashMap<Integer, String>) session.getAttribute("answers");
        User user = (User) session.getAttribute("user");

        int score = 0;
        for (Question q : questions) {
            String userAnswer = answers.get(q.getId());
            if (q.getCorrectOption().equalsIgnoreCase(userAnswer)) {
                score++;
            }
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO results (user_id, score, total_questions) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ps.setInt(2, score);
            ps.setInt(3, questions.size());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.setContentType("text/html");
        response.getWriter().println("<html><head><link rel='stylesheet' href='css/style.css'></head><body>");
        response.getWriter().println("<h2>Quiz Finished!</h2>");
        response.getWriter().println("<p>Your score: " + score + " out of " + questions.size() + "</p>");
        response.getWriter().println("<a href='logout'>Logout</a>");
        response.getWriter().println("</body></html>");
    }
}
