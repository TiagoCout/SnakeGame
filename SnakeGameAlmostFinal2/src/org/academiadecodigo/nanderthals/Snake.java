package org.academiadecodigo.nanderthals;

import org.academiadecodigo.simplegraphics.pictures.Picture;

import java.util.LinkedList;

public class Snake {

    private LinkedList<SnakeCell> body;
    private Direction direction;
    private int dimension;
    private int foodEaten = 0;
    private boolean collision;

    public Snake(int dimension) {
        body = new LinkedList<>();
        this.dimension = dimension;
        direction = Direction.RIGHT;

        // Initialize the snake's body with three cells
        int startX = Game.WIDTH / 2;
        int startY = Game.HEIGHT / 2;
        for (int i = 0; i < 3; i++) {
            body.add(new SnakeCell(startX - i, startY));
        }
    }

    public void move() {
        SnakeCell head = body.getFirst();
        int nextX = head.getX();
        int nextY = head.getY();

        if (direction == Direction.UP) {
            nextY--;
        } else if (direction == Direction.DOWN) {
            nextY++;
        } else if (direction == Direction.LEFT) {
            nextX--;
        } else if (direction == Direction.RIGHT) {
            nextX++;
        }

        SnakeCell newHead = new SnakeCell(nextX, nextY);
        body.addFirst(newHead);
        body.removeLast();
    }

    public boolean collidesWithBody() {
        SnakeCell head = body.getFirst();
        for (int i = 1; i < body.size(); i++) {
            SnakeCell cell = body.get(i);
            if (head.equals(cell)) {
                return true;
            }
        }
        return false;
    }

    public boolean isOutOfBounds() {
        SnakeCell head = body.getFirst();
        int x = head.getX();
        int y = head.getY();
        return x < 0 || x >= Game.WIDTH || y < 0 || y >= Game.HEIGHT;
    }

    public boolean collidesWithFood(Food food) {
        SnakeCell head = body.getFirst();
        collision = head.equals(food.getPosition());
        if(collision){
            foodEaten++;
        }
        return collision;
    }

    public int getFoodEaten(){
        return foodEaten;
    }


    public void grow() {
        SnakeCell tail = body.getLast();
        int x = tail.getX();
        int y = tail.getY();
        body.add(new SnakeCell(x, y));
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public LinkedList<SnakeCell> getBody() {
        return body;
    }

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}
