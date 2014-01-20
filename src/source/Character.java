/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package source;

import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author José Alberto Esquivel Patiño A01139626
 */
public class Character {
    //variables for the movement of the character
    private int pos_x; //position in x
    private int pos_y; //position in y
    private int speed; //movement speed
    private int direction; //speed direction
    
    //variables for colision control
    private boolean en_collision; //true when in collision
    private int contador_ciclos_colision; //counts from waiting_cycles to 0
    private int ciclos_colision_espera; //contains waiting cycles when in collision
    
    //variables for icon control
    private ImageIcon icon; //icon
    private URL image_URL; //image_URL
}
