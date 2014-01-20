
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
    // Se declaran las variables.
    private int x_pos;				// Posicion x del elefante
    private int y_pos;				// Posicion y del elefante
    private int direccion;			// Direccion del elefante
    private int velocidad;

    private ImageIcon elefante;		// Imagen del elefante
    URL eURL_derecha = this.getClass().getResource("/imagenes/elefante.gif");
    URL eURL_izquierda = this.getClass().getResource("/imagenes/elefante-izquierda.gif");

    private int altura_elefante;
    private int largo_elefante;

    private Image dbImage;	// Imagen a proyectar	
    private Graphics dbg;	// Objeto grafico
    private boolean cambio_imagen;
    private boolean en_colision;
    private final int ciclos_en_colision_default = 25;
    private int contador_ciclos_en_colision;

    private AudioClip sonido;       // Objeto AudioClip

    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     *
     */
    public void init() {

        direccion = 4; //direccion inicial del elefante
        velocidad = 1; //velocidad inicial del elefante
        en_colision = false;
        contador_ciclos_en_colision = -1;

        x_pos = (int) (Math.random() * (getWidth() / 4));    // posicion en x es un cuarto del applet;
        y_pos = (int) (Math.random() * (getHeight() / 4));    // posicion en y es un cuarto del applet

        elefante = new ImageIcon(Toolkit.getDefaultToolkit().getImage(eURL_derecha));
        altura_elefante = elefante.getIconHeight();
        largo_elefante = elefante.getIconWidth();
        cambio_imagen = false;

        setBackground(Color.yellow);

        addKeyListener(this);

        URL eaURL = this.getClass().getResource("/sonidos/elephant.wav");
        sonido = getAudioClip(eaURL);
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
            actualiza();
            checaColision();
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
     * Metodo que actualiza la posicion del objeto elefante
     */
    public void actualiza() {
        if (!en_colision) {
            switch (direccion) {
                case 1: {
                    y_pos -= velocidad;
                    break;    //se mueve hacia arriba
                }
                case 2: {
                    y_pos += velocidad;
                    break;    //se mueve hacia abajo
                }
                case 3: {
                    x_pos -= velocidad;
                    break;    //se mueve hacia izquierda
                }
                case 4: {
                    x_pos += velocidad;
                    break;    //se mueve hacia derecha	
                }
            }
        }
    }

    /**
     * Metodo que checa la colision del objeto elefante al colisionar con las
     * orillas del <code>Applet</code>.
     */
    public void checaColision() {
        if (!en_colision) {
            if (y_pos == 0 || y_pos + altura_elefante > getHeight()
                    || x_pos == 0 || x_pos + largo_elefante > getWidth()) {
                en_colision = true;
                velocidad = 0;
                contador_ciclos_en_colision = ciclos_en_colision_default;
                sonido.play();
            }
        } else {
            contador_ciclos_en_colision--;
            if (contador_ciclos_en_colision == -1) {
                en_colision = false;
                invertirDireccion();
                velocidad = 1;
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
            if (direccion == 2) {
                velocidad--;
                if (velocidad < 0) {
                    velocidad = 1;
                    direccion = 1;
                }
            } else if (direccion == 1) {
                velocidad++;
            } else {
                velocidad = 1;
                direccion = 1;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {    //Presiono flecha abajo
            if (direccion == 1) {
                velocidad--;
                if (velocidad < 0) {
                    velocidad = 1;
                    direccion = 2;
                }
            } else if (direccion == 2) {
                velocidad++;
            } else {
                velocidad = 1;
                direccion = 2;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {    //Presiono flecha izquierda
            if (direccion == 4) {
                velocidad--;
                if (velocidad < 0) {
                    velocidad = 1;
                    direccion = 3;
                    cambio_imagen = true;
                }
            } else if (direccion == 3) {
                velocidad++;
            } else {
                velocidad = 1;
                direccion = 3;
                cambio_imagen = true;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {    //Presiono flecha derecha
            if (direccion == 3) {
                velocidad--;
                if (velocidad < 0) {
                    velocidad = 1;
                    direccion = 4;
                    cambio_imagen = true;
                }
            } else if (direccion == 4) {
                velocidad++;
            } else {
                velocidad = 1;
                direccion = 4;
                cambio_imagen = true;
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
        if (elefante != null) {
            //Dibuja la imagen en la posicion actualizada
            if (cambio_imagen) {
                if (en_colision) {
//                    Cambio la imagen del elefante por la imagen de su colision.
//                    elefante = new ImageIcon(Toolkit.getDefaultToolkit().getImage(eURL_derecha));
                } else {
                    if (direccion == 3) {
                        elefante = new ImageIcon(Toolkit.getDefaultToolkit().getImage(eURL_izquierda));
                    } else {
                        elefante = new ImageIcon(Toolkit.getDefaultToolkit().getImage(eURL_derecha));
                    }
                }
                cambio_imagen = false;
            }
            g.drawImage(elefante.getImage(), x_pos, y_pos, this);

        } else {
            //Da un mensaje mientras se carga el dibujo	
            g.drawString("Estoy cargando..", 10, 10);
        }

    }

    public void invertirDireccion() {
        switch (direccion) {
            case 1:
                direccion = 2;
                break;
            case 2:
                direccion = 1;
                break;
            case 3:
                direccion = 4;
                break;
            case 4:
                direccion = 3;
                break;
        }
    }
}
