/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * Describes a basic character in a game or animation for a java applet.
 *
 * @author José Alberto Esquivel Patiño A01139626
 */
public class Character extends Applet {

    //Default code for direction
    private final int UP = 1;
    private final int DOWN = 2;
    private final int LEFT = 3;
    private final int RIGHT = 4;

    //Default code for movement
    private int DEFAULT_POS_X = 0;
    private int DEFAULT_POS_Y = 0;
    private int DEFAULT_SPEED = 1;
    private int DEFAULT_DIRECTION = 4;
    private int DEFAULT_SPEED_CHANGE = 1;

    //dimensions
    private int width;
    private int height;

    //movement of the character
    private int pos_x; //position in x
    private int pos_y; //position in y
    private int speed; //movement speed
    private int direction; //speed direction

    //colision control
    private boolean in_collision; //true when in collision
    private int collision_cycles_counter; //counts from waiting_cycles to 0
    private int collision_duration_in_cycles = 25; //contains waiting cycles when in collision

    //icon control
    private ImageIcon icon; //icon
    private URL image_URL; //image_URL
    private URL collision_image_URL; //collision image_URL
    private boolean change_image; //true when an image change is need

    //sound effects
    private AudioClip collision_sound;//collision sound
    private URL collision_sound_URL; //collision image_URL
    
    public Character(int pos_x, int pos_y, URL image_URL, URL collision_image_URL, AudioClip collision_sound) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.speed = DEFAULT_SPEED;
        this.direction = DEFAULT_DIRECTION;

        this.image_URL = image_URL;
        this.collision_image_URL = collision_image_URL;
        this.collision_sound = collision_sound;
        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(image_URL));
        width = icon.getIconWidth();
        height = icon.getIconHeight();

        //collision_sound = getAudioClip(collision_sound_URL);
        in_collision = false;
        change_image = false;

    }

    public int getDEFAULT_POS_X() {
        return DEFAULT_POS_X;
    }

    public void setDEFAULT_POS_X(int DEFAULT_POS_X) {
        this.DEFAULT_POS_X = DEFAULT_POS_X;
    }

    public int getDEFAULT_POS_Y() {
        return DEFAULT_POS_Y;
    }

    public void setDEFAULT_POS_Y(int DEFAULT_POS_Y) {
        this.DEFAULT_POS_Y = DEFAULT_POS_Y;
    }

    public int getDEFAULT_SPEED() {
        return DEFAULT_SPEED;
    }

    public void setDEFAULT_SPEED(int DEFAULT_SPEED) {
        this.DEFAULT_SPEED = DEFAULT_SPEED;
    }

    public int getDEFAULT_DIRECTION() {
        return DEFAULT_DIRECTION;
    }

    public void setDEFAULT_DIRECTION(int DEFAULT_DIRECTION) {
        this.DEFAULT_DIRECTION = DEFAULT_DIRECTION;
    }

    public int getDEFAULT_SPEED_CHANGE() {
        return DEFAULT_SPEED_CHANGE;
    }

    public void setDEFAULT_SPEED_CHANGE(int DEFAULT_SPEED_CHANGE) {
        this.DEFAULT_SPEED_CHANGE = DEFAULT_SPEED_CHANGE;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPos_x() {
        return pos_x;
    }

    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isIn_collision() {
        return in_collision;
    }

    public void setIn_collision(boolean in_collision) {
        this.in_collision = in_collision;
    }

    public int getCollision_cycles_counter() {
        return collision_cycles_counter;
    }

    public void setCollision_cycles_counter(int collision_cycles_counter) {
        this.collision_cycles_counter = collision_cycles_counter;
    }

    public int getCollision_duration_in_cycles() {
        return collision_duration_in_cycles;
    }

    public void setCollision_duration_in_cycles(int collision_duration_in_cycles) {
        this.collision_duration_in_cycles = collision_duration_in_cycles;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public URL getImage_URL() {
        return image_URL;
    }

    public void setImage_URL(URL image_URL) {
        this.image_URL = image_URL;
    }

    public URL getCollision_image_URL() {
        return collision_image_URL;
    }

    public void setCollision_image_URL(URL collision_image_URL) {
        this.collision_image_URL = collision_image_URL;
    }

    public AudioClip getCollision_sound() {
        return collision_sound;
    }

    public void setCollision_sound(AudioClip collision_sound) {
        this.collision_sound = collision_sound;
    }

    public URL getCollision_sound_URL() {
        return collision_sound_URL;
    }

    public void setCollision_sound_URL(URL collision_sound_URL) {
        this.collision_sound_URL = collision_sound_URL;
    }

    //ACTIONS
    /**
     * Method <I>stopColliding</I>
     * Takes the character out of the collision state. 
     */
    public void stopColliding() {
        in_collision = false;
        change_image = true;
    }

    /**
     * Method <I>decreaseCollisionCyclesCounter</I>
     * Decreases the collision cycles counter by 1.
     * This method is used over setCollision_cycles_counter
     * for readability purposes.
     */
    public void decreaseCollisionCyclesCounter() {
        setCollision_cycles_counter(collision_cycles_counter - 1);
    }

    /**
     * Method <I>changeDirection</I>
     * Changes the direction of the character, the only difference from setter
     * is that here eventually I will check for future collisions. It also
     * updates the speed to the default.
     *
     * @param direction
     */
    public void changeDirection(int direction) {
        this.direction = direction;
        setSpeed(DEFAULT_SPEED);
    }

    /**
     * Method <I>deccelerate</I>
     * Decreases speed by acceleration parameter a
     *
     * @param a
     */
    public void deccelerate(int a) {
        setSpeed(speed - a);
    }

    /**
     * Method <I>deccelerate (Default)</I>
     * Decreases speed by default speed change...
     */
    public void deccelerate() {
        setSpeed(speed - DEFAULT_SPEED_CHANGE);
    }

    /**
     * Method <I>accelerate</I>
     * Increases speed by acceleration parameter a
     *
     * @param a
     */
    public void accelerate(int a) {
        setSpeed(speed + a);
    }

    /**
     * Method <I>accelerate (Default)</I>
     * Increases speed by default speed change...
     */
    public void accelerate() {
        setSpeed(speed + DEFAULT_SPEED_CHANGE);
    }

    /**
     * Method <I>updateCharacterImage</I>
     * updates the character image based on status
     */
    private void updateCharacterImage() {
        if (in_collision) {
            icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(collision_image_URL));

        } else {
            icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(image_URL));
        }
        change_image = false;
    }

    /**
     * Method <I>updateCharacterPosition</I>
     * updates the character position based on speed and direction class
     * attributes
     */
    private void updateCharacterPosition() {
        switch (direction) {
            case UP: {
                pos_y -= speed;
                break;    //se mueve hacia arriba
            }
            case DOWN: {
                pos_y += speed;
                break;    //se mueve hacia abajo
            }
            case LEFT: {
                pos_x -= speed;
                break;    //se mueve hacia izquierda
            }
            case RIGHT: {
                pos_x += speed;
                break;    //se mueve hacia derecha	
            }
        }
    }

    /**
     * Method <I>updateCharacter</I>
     * updates the character position and image based on status
     */
    public void updateCharacter() {
        updateCharacterPosition();
        if (change_image) {
            updateCharacterImage();
        }
    }

    /**
     * Method <I>collide</I>
     * puts the object in a state of collision
     *
     * @param direction
     */
    public void collide(int direction) {
        in_collision = true;
        change_image = true;
        collision_cycles_counter = collision_duration_in_cycles;
        collision_sound.play();
        switch (direction) {
            case UP:
                changeDirection(DOWN);
                break;
            case DOWN:
                changeDirection(UP);
                break;
            case RIGHT:
                changeDirection(LEFT);
                break;
            case LEFT:
                changeDirection(RIGHT);
                break;
        }
    }
}
