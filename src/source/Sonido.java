package source;

/**
 * Clase para ejemplificar el uso de sonido en el Applet
 *
 * Sonido Applet application
 *
 * @author Antonio Mejorado
 * @version 1.00 2008/6/12
 */
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import javax.swing.ImageIcon;

public class Sonido extends Applet implements Runnable, KeyListener {

    private static final long serialVersionUID = 1L;

    //Default code for direction
    private final int UP = 1;
    private final int DOWN = 2;
    private final int LEFT = 3;
    private final int RIGHT = 4;

    // Se declaran las variables.
    private int x_pos;				// Posicion x del elefante
    private int y_pos;				// Posicion y del elefante
    private int direccion;			// Direccion del elefante
    private int velocidad;

    private Omni mySaucer;
    URL eURL_saucer = this.getClass().getResource("/imagenes/flying-saucer2.gif");
    URL eURL_collision = this.getClass().getResource("/imagenes/flying-saucer-collision2.gif");

    private Image dbImage;	// Imagen a proyectar	
    private Graphics dbg;	// Objeto grafico

    private AudioClip sonido;       // Objeto AudioClip
    URL eaURL = this.getClass().getResource("/sonidos/8-bit-explosion.wav");

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     *
     */
    public void init() {

        x_pos = (int) (Math.random() * (getWidth() / 4));    // posicion en x es un cuarto del applet;
        y_pos = (int) (Math.random() * (getHeight() / 4));    // posicion en y es un cuarto del applet

        sonido = getAudioClip(eaURL);
        mySaucer = new Omni(x_pos, y_pos, eURL_saucer, eURL_collision, sonido);
        
        setBackground(Color.WHITE);
        addKeyListener(this);

    }

    /**
     * Metodo <I>start</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo para la animacion este metodo
     * es llamado despues del init o cuando el usuario visita otra pagina y
     * luego regresa a la pagina en donde esta este <code>Applet</code>
     *
     */
    public void start() {
        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }

    /**
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
     * incrementa la posicion en x o y dependiendo de la direccion, finalmente
     * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
     *
     */
    public void run() {
        while (true) {
            mySaucer.updateCharacter();
            checkCollision();
            repaint();    // Se actualiza el <code>Applet</code> repintando el contenido.
            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }
    }

    /**
     * Metodo que checa la colision del objeto elefante al colisionar con las
     * orillas del <code>Applet</code>.
     */
    public void checkCollision() {
        if (!mySaucer.isIn_collision()) {
            switch (mySaucer.getDirection()) {
                case UP: { //Revisa colision cuando sube
                    if (mySaucer.getPos_y() < 0) {
                        mySaucer.collide(UP);
                    }
                    break;
                }
                case DOWN: { //Revisa colision cuando baja
                    if (mySaucer.getPos_y() + mySaucer.getHeight() > getHeight()) {
                        mySaucer.collide(DOWN);
                    }
                    break;
                }
                case LEFT: { //Revisa colision cuando va izquierda.
                    if (mySaucer.getPos_x() < 0) {
                        mySaucer.collide(LEFT);
                    }
                    break;
                }
                case RIGHT: { //Revisa colision cuando va derecha.
                    if (mySaucer.getPos_x() + mySaucer.getWidth() > getWidth()) {
                        mySaucer.collide(RIGHT);
                    }
                    break;
                }
            }
        } else {
            mySaucer.decreaseCollisionCyclesCounter();
            if (mySaucer.getCollision_cycles_counter() == -1) {
                mySaucer.stopColliding();
            }
        }
    }

    /**
     * Metodo <I>update</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void update(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }

    /**
     * Metodo <I>keyPressed</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar cualquier la
     * tecla.
     *
     * @param e es el <code>evento</code> generado al presionar las teclas.
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {    //Presiono flecha arriba
            if (mySaucer.getDirection() == DOWN) {
                mySaucer.deccelerate();
                if (mySaucer.getSpeed() <= 0) {
                    mySaucer.changeDirection(UP);
                }
            } else if (mySaucer.getDirection() == UP) {
                mySaucer.accelerate();
            } else {
                mySaucer.changeDirection(UP);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {    //Presiono flecha abajo
            if (mySaucer.getDirection() == UP) {
                mySaucer.deccelerate();
                if (mySaucer.getSpeed() <= 0) {
                    mySaucer.changeDirection(DOWN);
                }
            } else if (mySaucer.getDirection() == DOWN) {
                mySaucer.accelerate();
            } else {
                mySaucer.changeDirection(DOWN);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {    //Presiono flecha izquierda
            if (mySaucer.getDirection() == RIGHT) {
                mySaucer.deccelerate();
                if (mySaucer.getSpeed() <= 0) {
                    mySaucer.changeDirection(LEFT);
                }
            } else if (mySaucer.getDirection() == LEFT) {
                mySaucer.accelerate();
            } else {
                mySaucer.changeDirection(LEFT);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {    //Presiono flecha derecha
            if (mySaucer.getDirection() == LEFT) {
                mySaucer.deccelerate();
                if (mySaucer.getSpeed() <= 0) {
                    mySaucer.changeDirection(RIGHT);
                }
            } else if (mySaucer.getDirection() == RIGHT) {
                mySaucer.accelerate();
            } else {
                mySaucer.changeDirection(RIGHT);
            }
        }
    }

    /**
     * Metodo <I>keyTyped</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una tecla que
     * no es de accion.
     *
     * @param e es el <code>evento</code> que se genera en al presionar las
     * teclas.
     */
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Metodo <I>keyReleased</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla
     * presionada.
     *
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        if (mySaucer != null) {
            //Dibuja la imagen en la posicion actualizada
            g.drawImage(mySaucer.getIcon().getImage(), mySaucer.getPos_x(), mySaucer.getPos_y(), this);
        } else {
            //Da un mensaje mientras se carga el dibujo	
            g.drawString("Loading...", 10, 10);
        }
    }

}
