package com.fiit.gui;

import com.fiit.logic.*;
import com.fiit.logic.events.EventSource;
import com.fiit.logic.events.InputEventListener;
import com.fiit.logic.events.MoveEvent;

public class GameController implements InputEventListener {

    private final SimpleBoard board = new SimpleBoard(25, 10);
    private final GuiController viewController;

    public GameController(GuiController c) {
        this.viewController = c;
        this.viewController.setEventLister(this);
    }

    /**
     * Executes the onEnter event with the given length parameter.
     * This method is called when the user presses the "Enter" key.
     *
     * @param length the length parameter passed to the method
     */
    @Override
    public void onEnter(int length) {
        System.out.println(length);
        board.setScore(new Score() {{
            add(length);
        }});
        board.createNewBrick();
        this.viewController.init();
        this.viewController.bindScore(board.getScore().scoreProperty());
        this.viewController.initGameView(board.getBoardMatrix(), board.getViewData());
    }

    /**
     * Executes the onDownEvent method with the given MoveEvent parameter.
     * This method is called when the user triggers a down movement event.
     *
     * @param event the MoveEvent parameter passed to the method
     * @return a DownData object containing the clearRow and viewData information
     */
    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(-clearRow.getScoreBonus());
            }

            if (board.createNewBrick()) {
                viewController.gameOver(false);
            }
        } else {
            if (event.getEventSource() == EventSource.USER) {
                board.getScore().add(-1);
            }
        }

        if (board.getScore().scoreProperty().get() <= 0) {
            viewController.gameOver(true);
        }

        viewController.refreshGameBackground(board.getBoardMatrix());

        return new DownData(clearRow, board.getViewData());
    }

    /**
     * Executes the onLeftEvent method.
     * This method is called when the user triggers a left movement event.
     *
     * @return a ViewData object containing the viewData information
     */
    @Override
    public ViewData onLeftEvent() {
        board.moveBrickLeft();

        return board.getViewData();
    }

    /**
     * Executes the onRightEvent method.
     * This method is called when the user triggers a right movement event.
     *
     * @return a ViewData object containing the viewData information
     */
    @Override
    public ViewData onRightEvent() {
        board.moveBrickRight();

        return board.getViewData();
    }

    /**
     * Executes the onRotateEvent method.
     * This method is called when the user triggers a rotate movement event.
     *
     * @return a ViewData object containing the viewData information
     */
    @Override
    public ViewData onRotateEvent() {
        board.rotateBrickLeft();

        return board.getViewData();
    }
}
