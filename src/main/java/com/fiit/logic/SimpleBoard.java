package com.fiit.logic;

import com.fiit.logic.bricks.Brick;
import com.fiit.logic.bricks.RandomBrick;
import javafx.geometry.Point2D;

public class SimpleBoard {

    private final RandomBrick brickGenerator;
    private Score score;
    private int[][] currentGameMatrix;
    private Brick brick;
    private int currentShape = 0;
    private Point2D currentOffset;

    public SimpleBoard(int width, int height) {
        currentGameMatrix = new int[width][height];
        brickGenerator = new RandomBrick();
        score = new Score();
    }

    /**
     * Creates new brick and sets it to the board
     *
     * @return true if brick intersects with other bricks
     */
    public boolean createNewBrick() {
        currentShape = 0;
        Brick currentBrick = brickGenerator.getBrick();
        setBrick(currentBrick);
        currentOffset = new Point2D(4.0, 0.0);

        return MatrixOperations.intersects(currentGameMatrix,
                getCurrentShape(),
                (int) currentOffset.getX(),
                (int) currentOffset.getY());
    }

    public boolean moveBrickDown() {
        Point2D p = currentOffset.add(0, 1);
        currentOffset = p;
        boolean conflict = MatrixOperations.intersects(currentGameMatrix, getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    public boolean moveBrickLeft() {
        Point2D p = currentOffset.add(-1, 0);

        boolean conflict = MatrixOperations.intersects(currentGameMatrix, getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    public boolean moveBrickRight() {
        Point2D p = currentOffset.add(1, 0);

        boolean conflict = MatrixOperations.intersects(currentGameMatrix, getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    public ViewData getViewData() {
        return new ViewData(getCurrentShape(),
                (int) currentOffset.getX(),
                (int) currentOffset.getY(),
                brickGenerator.getNext().getMatrix().get(0));
    }

    public int[][] getCurrentShape() {
        return this.brick.getMatrix().get(currentShape);
    }

    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    public void setBrick(Brick brick) {
        this.brick = brick;
        currentOffset = new Point2D(4.0, 0.0);
    }

    public Score getScore() {
        return score;
    }

    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    /**
     * Merges the current brick with the background matrix
     */
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations
                .merge(currentGameMatrix, getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());

    }

    public NextShapeInfo getNextShape() {
        int nextShape = currentShape;
        nextShape = ++nextShape % brick.getMatrix().size();
        return new NextShapeInfo(brick.getMatrix().get(nextShape), nextShape);
    }

    public boolean rotateBrickLeft() {
        NextShapeInfo nextShape = getNextShape();
        boolean conflict = MatrixOperations.intersects(currentGameMatrix,
                nextShape.getShape(),
                (int) currentOffset.getX(),
                (int) currentOffset.getY());
        if (conflict) {
            return false;
        } else {
            setCurrentShape(nextShape.getPosition());
            return true;
        }
    }

    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNextMatrix();

        return clearRow;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}
