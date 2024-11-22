package com.rishab.model;

public class Map {
    String[][] goal;

    public Map(String[][] goal) {
        System.out.println("BIG BAAANNGGGG!!!!");
        this.goal = goal;
    }

    public Map() {
        this.goal = new String[1][1];
        goal[0][0] = "BLACKHOLE";
    }

    public String[][] getGoal() {
        return goal;
    }

    public void printGoal() {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(goal[i][j] + " ");
            }
            System.out.println();
        }
    }
}