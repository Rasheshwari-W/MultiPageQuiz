package com.quizapp.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.quizapp.model.Question;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/submitAnswer")
public class SubmitAnswerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("index.html");
            return;
        }

        String answer = request.getParameter("answer");
        int currentIndex = (int) session.getAttribute("currentIndex");
        List<Question> questions = (List<Question>) session.getAttribute("questions");

        HashMap<Integer, String> answers = (HashMap<Integer, String>) session.getAttribute("answers");
        answers.put(questions.get(currentIndex).getId(), answer);

        session.setAttribute("answers", answers);
        session.setAttribute("currentIndex", currentIndex + 1);

        response.sendRedirect("question");
    }
}

