package com.fiit.logic;

public class ClearRow {

    private final int scoreBonus;
    private final int linesRemoved;
    private final int[][] nextMatrix;

    public ClearRow(int linesRemoved, int[][] nextMatrix, int scoreBonus) {
        this.linesRemoved = linesRemoved;
        this.nextMatrix = nextMatrix;
        this.scoreBonus = scoreBonus;
    }

    public int getScoreBonus() {
        return scoreBonus;
    }

    public int getLinesRemoved() {
        return linesRemoved;
    }

    public int[][] getNextMatrix() {
        return nextMatrix;
    }
}
