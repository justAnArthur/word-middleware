package com.fiit.logic.bricks;

import java.util.ArrayList;

public class ZBrick extends Brick {
    public ZBrick() {
        super(new ArrayList<>() {{
            add(new int[][]{
                    {0, 0, 0, 0},
                    {1, 1, 0, 0},
                    {0, 1, 1, 0},
                    {0, 0, 0, 0}
            });
            add(new int[][]{
                    {0, 1, 0, 0},
                    {1, 1, 0, 0},
                    {1, 0, 0, 0},
                    {0, 0, 0, 0}
            });
        }});
    }

    @Override
    String getBrickName() {
        return this.getClass().getName();
    }
}
