package com.quizapp.util;

import com.quizapp.db.DBConnection;
import com.quizapp.model.Question;
import java.sql.*;
import java.util.*;

public class QuestionUtil {

    public static List<Question> getRandomQuestions(int count) {
        List<Question> allQuestions = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM questions";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Question q = new Question(
                        rs.getInt("id"),
                        rs.getString("question_text"),
                        rs.getString("option_a"),
                        rs.getString("option_b"),
                        rs.getString("option_c"),
                        rs.getString("option_d"),
                        rs.getString("correct_option")
                );
                allQuestions.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.shuffle(allQuestions);
        if (count > allQuestions.size()) {
            count = allQuestions.size();
        }
        return allQuestions.subList(0, count);
    }
}
