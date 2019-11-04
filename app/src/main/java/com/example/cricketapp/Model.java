package com.example.cricketapp;

public class Model {
    String id;
    String team1;
    String team2;
    String matchtype;
    String matchstatus;

    public Model(String id, String team1, String team2, String matchtype, String matchstatus, String date) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.matchtype = matchtype;
        this.matchstatus = matchstatus;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public String getMatchtype() {
        return matchtype;
    }

    public String getMatchstatus() {
        return matchstatus;
    }

    public String getDate() {
        return date;
    }

    String date;
}
