package com.fiit.logic.bricks;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class RandomBrick {
    private final List<Brick> brickList;
    private final Deque<Brick> brickDeque = new ArrayDeque<>();

    /**
     * Constructor for RandomBrick class.
     * It creates a list of all possible bricks and adds 4 random bricks to the deque.
     */
    public RandomBrick() {
        brickList = Arrays.asList(new ZBrick(), new TBrick(), new SBrick(), new OBrick(), new LBrick(), new JBrick(), new IBrick());

        brickDeque.add(brickList.get((int) (Math.random() * brickList.size())));
        brickDeque.add(brickList.get((int) (Math.random() * brickList.size())));
        brickDeque.add(brickList.get((int) (Math.random() * brickList.size())));
        brickDeque.add(brickList.get((int) (Math.random() * brickList.size())));
    }

    public Brick getNext() {
        return brickDeque.peek();
    }

    /**
     * Returns a random brick from the deque.
     * If the deque has less than 2 bricks, it adds a new random brick to the deque.
     * @return a random brick from the deque
     */
    public Brick getBrick() {
        if (brickDeque.size() <= 1) {
            brickDeque.add(brickList.get((int) (Math.random() * brickList.size())));
        }
        return brickDeque.poll();
    }
}
