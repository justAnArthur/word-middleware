package com.fiit.logic.bricks;

import java.util.*;

public abstract class Brick {
    private final List<int[][]> matrix = new ArrayList<>();

    public Brick(Collection<int[][]> collection) {
        this.matrix.addAll(collection);
    }

    public final List<int[][]> getMatrix() {
        return matrix;
    }

    @Override
    public int hashCode() {
        return Objects.hash(matrix);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Brick brick = (Brick) obj;
        return Objects.equals(matrix, brick.matrix);
    }

    @Override
    public String toString() {
        return "Brick{" + "matrix=" + matrixToString() + '}';
    }

    private String matrixToString() {
        StringBuilder sb = new StringBuilder("[");

        for (int[][] array : matrix)
            sb.append(Arrays.deepToString(array)).append(", ");

        if (!matrix.isEmpty())
            sb.setLength(sb.length() - 2);

        sb.append("]");
        return sb.toString();
    }

    abstract String getBrickName();
}
