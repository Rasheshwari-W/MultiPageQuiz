package com.quizapp.model;

import java.sql.Timestamp;

public class Result {
    private int id;
    private int userId;
    private int score;
    private int totalQuestions;
    private Timestamp dateTaken;

    public Result() {}

    public Result(int id, int userId, int score, int totalQuestions, Timestamp dateTaken) {
        this.id = id;
        this.userId = userId;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.dateTaken = dateTaken;
    }

    public Result(int userId, int score, int totalQuestions) {
        this.userId = userId;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.dateTaken = new Timestamp(System.currentTimeMillis());
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }

    public Timestamp getDateTaken() { return dateTaken; }
    public void setDateTaken(Timestamp dateTaken) { this.dateTaken = dateTaken; }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", userId=" + userId +
                ", score=" + score +
                ", totalQuestions=" + totalQuestions +
                ", dateTaken=" + dateTaken +
                '}';
    }
}
