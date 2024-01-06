package com.fiit.gui;

import com.fiit.logic.DownData;
import com.fiit.logic.ViewData;
import com.fiit.logic.events.EventSource;
import com.fiit.logic.events.EventType;
import com.fiit.logic.events.InputEventListener;
import com.fiit.logic.events.MoveEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class GuiController {

    private static final int BRICK_SIZE = 20;
    private final BooleanProperty paused = new SimpleBooleanProperty();
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();
    Timeline timeLine;
    private InputEventListener eventLister;
    private Rectangle[][] displayMatrix;
    private Rectangle[][] rectangles;

    @FXML
    private ToggleButton pauseButton;
    @FXML
    private GridPane gamePanel;
    @FXML
    private GridPane nextBrick;
    @FXML
    private GridPane brickPanel;
    @FXML
    private Text scoreValue;
    @FXML
    private TextField textField;

    /**
     * Initializes the game view with the given boardMatrix and viewData.
     *
     * @param boardMatrix the boardMatrix parameter passed to the method
     * @param viewData    the viewData parameter passed to the method
     */
    public void initGameView(int[][] boardMatrix, ViewData viewData) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }

        rectangles = new Rectangle[viewData.getBrickData().length][viewData.getBrickData()[0].length];

        for (int i = 0; i < viewData.getBrickData().length; i++) {
            for (int j = 0; j < viewData.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(viewData.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }

        brickPanel.setLayoutX(5 + gamePanel.getLayoutX() + (viewData.getxPosition() * BRICK_SIZE + 1));
        brickPanel.setLayoutY(-25 + gamePanel.getLayoutY() + (viewData.getyPosition() * BRICK_SIZE + 1));

        generatePreviewPanel(viewData.getNextBrickData());

        timeLine = new Timeline(
                new KeyFrame(Duration.millis(400), ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))));

        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    /**
     * Generates the preview panel with the given nextBrickData.
     *
     * @param nextBrickData the nextBrickData parameter passed to the method
     */
    private void generatePreviewPanel(int[][] nextBrickData) {
        nextBrick.getChildren().clear();
        for (int i = 0; i < nextBrickData.length; i++) {
            for (int j = 0; j < nextBrickData[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                setRectangleData(nextBrickData[i][j], rectangle);
                if (nextBrickData[i][j] != 0) {
                    nextBrick.add(rectangle, j, i);
                }
            }
        }
    }

    /**
     * Refreshes the game background with the given board.
     *
     * @param board the board parameter passed to the method
     */
    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    /**
     * Sets the rectangle data with the given color and rectangle.
     *
     * @param color     the color parameter passed to the method
     * @param rectangle the rectangle parameter passed to the method
     */
    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }


    /**
     * Binds the scoreValue text with the given integerProperty.
     *
     * @param integerProperty the integerProperty parameter passed to the method
     */
    public void bindScore(IntegerProperty integerProperty) {
        scoreValue.textProperty().bind(integerProperty.asString());
    }

    /**
     * Moves the brick down with the given MoveEvent parameter.
     *
     * @param event the MoveEvent parameter passed to the method
     */
    private void moveDown(MoveEvent event) {
        DownData downData = eventLister.onDownEvent(event);

        refreshBrick(downData.getViewData());
    }

    /**
     * Refreshes the brick with the given viewData.
     *
     * @param viewData the viewData parameter passed to the method
     */
    private void refreshBrick(ViewData viewData) {
        brickPanel.setLayoutX(5 + gamePanel.getLayoutX() + (viewData.getxPosition() * BRICK_SIZE + 1));
        brickPanel.setLayoutY(-25 + gamePanel.getLayoutY() + (viewData.getyPosition() * BRICK_SIZE + 1));

        for (int i = 0; i < viewData.getBrickData().length; i++) {
            for (int j = 0; j < viewData.getBrickData()[i].length; j++) {
                setRectangleData(viewData.getBrickData()[i][j], rectangles[i][j]);
            }
        }

        generatePreviewPanel(viewData.getNextBrickData());
    }

    public void setEventLister(InputEventListener eventLister) {
        this.eventLister = eventLister;
    }

    /**
     * Returns the fill color with the given integer.
     *
     * @param i the i parameter passed to the method
     * @return the fill color with the given integer
     */
    public Paint getFillColor(int i) {

        return switch (i) {
            case 0 -> Color.TRANSPARENT;
            case 1 -> Color.AQUA;
            case 2 -> Color.BLUEVIOLET;
            case 3 -> Color.DARKGREEN;
            case 4 -> Color.YELLOW;
            case 5 -> Color.RED;
            case 6 -> Color.BEIGE;
            case 7 -> Color.BURLYWOOD;
            default -> Color.WHITE;
        };
    }

    /**
     * Initializes the game panel.
     */
    public void init() {
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (paused.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
                    if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
                        refreshBrick(eventLister.onRotateEvent());
                        event.consume();
                    }
                    if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
                        moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                        System.out.println("Down");
                        event.consume();
                    }
                    if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                        refreshBrick(eventLister.onLeftEvent());
                        event.consume();
                    }
                    if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                        refreshBrick(eventLister.onRightEvent());
                        event.consume();
                    }
                }

                if (event.getCode() == KeyCode.P) {
                    pauseButton.selectedProperty().setValue(!pauseButton.selectedProperty().getValue());
                }
            }
        });

        pauseButton.selectedProperty().bindBidirectional(paused);
        pauseButton.selectedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    timeLine.pause();
                    pauseButton.setText("Resume");
                } else {
                    timeLine.play();
                    pauseButton.setText("Pause");
                }
            }
        });
    }

    public void onEnter() {
        eventLister.onEnter(textField.getCharacters().length());
    }

    /**
     * Sets the isGameOver property with the given boolean.
     *
     * @param bool the bool parameter passed to the method
     */
    public void gameOver(boolean bool) {
        timeLine.stop();
        if (bool) {
            textField.clear();
        }
    }
}
