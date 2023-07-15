package org.academiadecodigo.nanderthals;

import org.academiadecodigo.simplegraphics.graphics.Canvas;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.pictures.Picture;


public class Menu implements KeyboardHandler {

    private boolean shouldStartGame;
    private boolean keyPressed;
    private Rectangle background;
    private Picture menuBackground;

    public Menu() {
        shouldStartGame = false;
        keyPressed = false;
        background = new Rectangle(10, 10, 800, 600);
        menuBackground = new Picture(10, 10, "/menu_background.png");
        //background.setColor(Color.BLACK);
        clear();

        Keyboard keyboard = new Keyboard(this);
        registerEnterKey(keyboard);
    }

    public void show() {
        background.setColor(Color.BLACK);
        background.fill();

        menuBackground.draw();

        Canvas.getInstance().repaint();
    }

    public boolean shouldStartGame() {
        return shouldStartGame;
    }

    private void registerEnterKey(Keyboard keyboard) {
        KeyboardEvent event = new KeyboardEvent();
        event.setKey(KeyboardEvent.KEY_ENTER);
        event.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(event);
    }

    public void clear(){
        if(shouldStartGame == true){
            background.delete();
            menuBackground.delete();
        }
    }

    @Override
    public void keyPressed(KeyboardEvent event) {
        int keyCode = event.getKey();

        if (keyCode == KeyboardEvent.KEY_ENTER) {
            shouldStartGame = true;
            keyPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent event) {
        // Handle key-released events
    }

    public void waitForEnterKey() {
        while (!keyPressed) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
