package org.academiadecodigo.nanderthals;

import org.academiadecodigo.simplegraphics.graphics.Canvas;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class SnakeGraphics {

    private Picture[][] grid;
    private Picture[] snakeBodyPictures;

    private Rectangle background;
    private Picture menuBackground;

    public SnakeGraphics(int width, int height, int dimension) {
        background = new Rectangle(10, 10, 800, 600);
        menuBackground = new Picture(10, 10, "/game_background.jpg");
        show();
        grid = new Picture[width][height];
        snakeBodyPictures = new Picture[width * height];
    }

    public void show() {
        background.setColor(Color.BLACK);
        background.fill();

        menuBackground.draw();

        Canvas.getInstance().repaint();
    }

    public void repaint(Snake snake, Food food) {
        clear();

        // Draw snake body
        int index = 0;
        for (SnakeCell cell : snake.getBody()) {
            int x = cell.getX();
            int y = cell.getY();
            Picture picture = new Picture(x * 20, y * 20, "/snake_body.png");
            picture.draw();
            grid[x][y] = picture;
            snakeBodyPictures[index] = picture;
            index++;
        }

        // Draw food
        SnakeCell foodPosition = food.getPosition();
        int x = foodPosition.getX();
        int y = foodPosition.getY();
        Picture foodPicture = new Picture(x * 20, y * 20, "/food.png");
        foodPicture.draw();
        grid[x][y] = foodPicture;
    }

    public void clear() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null) {
                    grid[i][j].delete();
                    grid[i][j] = null;
                }
            }
        }

        for (Picture picture : snakeBodyPictures) {
            if (picture != null) {
                picture.delete();
            }
        }
    }
}
