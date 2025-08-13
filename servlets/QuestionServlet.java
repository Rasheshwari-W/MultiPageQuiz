package com.quizapp.servlets;

import java.io.IOException;
import java.util.List;

import com.quizapp.model.Question;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/question")
public class QuestionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("index.html");
            return;
        }

        List<Question> questions = (List<Question>) session.getAttribute("questions");
        int currentIndex = (int) session.getAttribute("currentIndex");

        if (currentIndex >= questions.size()) {
            response.sendRedirect("finalScore");
            return;
        }

        Question q = questions.get(currentIndex);

        response.setContentType("text/html");
        response.getWriter().println("<html><head><link rel='stylesheet' href='css/style.css'></head><body>");
        response.getWriter().println("<form action='submitAnswer' method='post'>");
        response.getWriter().println("<h3>Q" + (currentIndex + 1) + ": " + q.getQuestionText() + "</h3>");
        response.getWriter().println("<input type='radio' name='answer' value='A' required> " + q.getOptionA() + "<br>");
        response.getWriter().println("<input type='radio' name='answer' value='B'> " + q.getOptionB() + "<br>");
        response.getWriter().println("<input type='radio' name='answer' value='C'> " + q.getOptionC() + "<br>");
        response.getWriter().println("<input type='radio' name='answer' value='D'> " + q.getOptionD() + "<br><br>");
        response.getWriter().println("<input type='submit' value='Next'>");
        response.getWriter().println("</form>");
        response.getWriter().println("</body></html>");
    }
}

