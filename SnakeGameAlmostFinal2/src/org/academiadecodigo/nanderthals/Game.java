package org.academiadecodigo.nanderthals;

import org.academiadecodigo.simplegraphics.graphics.Canvas;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.pictures.Picture;
import org.academiadecodigo.simplegraphics.graphics.Text;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Game implements KeyboardHandler {

    private Snake snake;
    private Food food;
    private SnakeGraphics graphics;
    private Picture collisionImage;
    private boolean gameOver;
    private boolean restart;
    private HighScore highScore;
    private int factor = 5;
    private int fps = 5;
    private boolean canDisplayScore;


    public static final int WIDTH = 40;
    public static final int HEIGHT = 30;
    public static final int DIMENSION = 20;

    public void setFps(int fps) {
        this.fps = fps;
    }

    public int getFps() {
        return fps;
    }

    public Game() throws IOException {

        Menu menu = new Menu();
        menu.show();
        menu.waitForEnterKey();
        snake = new Snake(DIMENSION);
        food = new Food(snake);
        graphics = new SnakeGraphics(WIDTH, HEIGHT, DIMENSION);
        gameOver = false;
        restart = false;
        music();
        collisionImage = new Picture(10, 10, "/gameover.jpeg");
        //displayScores(true);

        Keyboard keyboard = new Keyboard(this);
        registerKeys(keyboard);
    }

    public void showImage(Picture image) {
        image.draw();
    }

    public void start() throws IOException {

        while (!gameOver) {

            snake.move();

            if (snake.collidesWithBody() || snake.isOutOfBounds()) {
                gameOver = true;
                canDisplayScore = true;
                System.out.println("Game Over");
                showImage(collisionImage);
                displayScores(true);
                break;
            }

            graphics.repaint(snake, food);

            if (snake.collidesWithFood(food)) {
                food.respawn();
                snake.grow();
                System.out.println(snake.getFoodEaten());
                switch (snake.getFoodEaten()){

                    case 3:
                        setFps(10);
                        break;
                    case 5:
                        setFps(15);
                        break;
                    case 10:
                        setFps(30);
                        break;
                    case 15:
                        setFps(45);
                        break;
                    case 20:
                        setFps(60);
                        break;
                }
            }

            long frameDuration = Math.round(1000.0 / getFps());

            try {
                Thread.sleep(frameDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (!restart) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        // Reiniciar o jogo
        restart();
    }

    public void displayScores(boolean canDisplayScore) throws IOException {
        if(gameOver){

            Text text = new Text(270, 400, "Score:");
            int finalScore = calculateFinalScore(snake.getFoodEaten());

            highScore = new HighScore();
            highScore.readFile(finalScore);
            System.out.println(finalScore);

            String scoreGame = "Score: " + finalScore + " \n" + "     Highest score: " + highScore.getHighestScore() + " \n" + "     Press Enter to try again!";

            text.setText(scoreGame);
            text.setColor(Color.WHITE);
            text.grow(20,20);
            text.draw();

        }
    }

    private int calculateFinalScore(int foodEaten){
        int finalScore = factor * foodEaten;
        return finalScore;
    }

    private void restart() throws IOException {
        /*snake = new Snake(DIMENSION);
        food = new Food(snake);
        graphics.show();
        gameOver = false;
        restart = false;
        start();*/


        graphics.clear();
        snake = new Snake(DIMENSION);
        food = new Food(snake);
        setFps(5);
        graphics = new SnakeGraphics(WIDTH, HEIGHT, DIMENSION);
        gameOver = false;
        restart = false;
        canDisplayScore = true;
        collisionImage = new Picture(10, 10, "/gameover.jpeg");
       // displayScores(true);


        Keyboard keyboard = new Keyboard(this);
        registerKeys(keyboard);
        start();
    }

    public void music() {
        try {
            URL wavFile = getClass().getClassLoader().getResource("audio.wav");
            Clip clip = null;
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            try {
                clip.open(AudioSystem.getAudioInputStream(wavFile));
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void registerKeys(Keyboard keyboard) {
        KeyboardEvent event = new KeyboardEvent();
        event.setKey(KeyboardEvent.KEY_W);
        event.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(event);

        event = new KeyboardEvent();
        event.setKey(KeyboardEvent.KEY_S);
        event.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(event);

        event = new KeyboardEvent();
        event.setKey(KeyboardEvent.KEY_A);
        event.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(event);

        event = new KeyboardEvent();
        event.setKey(KeyboardEvent.KEY_D);
        event.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(event);

        event = new KeyboardEvent();
        event.setKey(KeyboardEvent.KEY_ENTER);
        event.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(event);

    }

    @Override
    public void keyPressed(KeyboardEvent event) {
        int keyCode = event.getKey();

        if (gameOver && keyCode == KeyboardEvent.KEY_ENTER) {
            restart = true;
        } else if (keyCode == KeyboardEvent.KEY_W && snake.getDirection() != Snake.Direction.DOWN) {
            snake.setDirection(Snake.Direction.UP);
        } else if (keyCode == KeyboardEvent.KEY_S && snake.getDirection() != Snake.Direction.UP) {
            snake.setDirection(Snake.Direction.DOWN);
        } else if (keyCode == KeyboardEvent.KEY_A && snake.getDirection() != Snake.Direction.RIGHT) {
            snake.setDirection(Snake.Direction.LEFT);
        } else if (keyCode == KeyboardEvent.KEY_D && snake.getDirection() != Snake.Direction.LEFT) {
            snake.setDirection(Snake.Direction.RIGHT);
        }
    }

    @Override
    public void keyReleased(KeyboardEvent event) {
        // Handle key-released events
    }
}
