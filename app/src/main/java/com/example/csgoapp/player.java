package com.example.csgoapp;

public class player {

    String name;
    int rank;
    int kills;
    int kdratio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getKdratio() {
        return kdratio;
    }

    public void setKdratio(int kdratio) {
        this.kdratio = kdratio;
    }

    public player(String name, int rank, int kills, int kdratio) {
        this.name = name;
        this.rank = rank;
        this.kills = kills;
        this.kdratio = kdratio;

    }
}
