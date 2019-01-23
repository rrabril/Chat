package chat;

import java.awt.*;
import javax.swing.*;
import java.awt.Shape;
import java.util.concurrent.TimeUnit;
import java.awt.geom.RoundRectangle2D;

public class VentanaNotificacion extends JFrame {
	
	JTextArea area;
	int posicion_x;
	int posicion_y;
	int alto;
	int ancho;
	static int filas = 4;
	static int columnas = 20;
	static int notificaciones = 0;
	private String mensaje;
	
	VentanaNotificacion(String mensaje){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Insets barra = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		setUndecorated(true);
		Container contenedor = getContentPane();
		JPanel panel = new JPanel();
		contenedor.add(panel);
		area = new JTextArea(filas,columnas);
		Dimension dimension = area.getPreferredSize();
		alto = (int)dimension.getHeight();
		ancho = (int)dimension.getWidth();
		setSize(ancho,alto);
		posicion_x = (int)pantalla.getWidth()-ancho;
		posicion_y = (int)pantalla.getHeight()-(barra.bottom);
		setLocation (posicion_x,posicion_y);
		Color colorfondo = new Color (80,250,210);
		Shape forma = new RoundRectangle2D.Double(0, 0, this.getBounds().width, this.getBounds().height, 30, 30); 
		this.setShape(forma);
		area.setBackground(colorfondo);
		area.setEditable(false);
		panel.setBackground(colorfondo);
		this.mensaje = mensaje;
//		System.out.println("El mensaje es: " + ventana.dame_mensaje());
		area.append(texto_formateado(mensaje));
		panel.add(area);
	}

	public void subir_ventana(){
		for (int i=alto;i>0;i--) {
			pausa(10);
			setLocation (posicion_x,posicion_y--);
			repaint();
		}
	}

	public void bajar_ventana(){
		for (int i=0;i<alto;i++) {
			pausa(10);
			setLocation (posicion_x,posicion_y++);
			repaint();
		}
	}
		
	public static void pausa(int milisegundos) {
		try{
			TimeUnit.MILLISECONDS.sleep(milisegundos);
			}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public String texto_formateado (String texto) {

		int posicion = 0;
		String formateado = "";
		int linea = (columnas-2)*2;
		int filastexto;
		if (texto.length()>((filas-1)*linea)) {
			texto = texto.substring(0, (((filas-1)*linea)-3)) + "...";
			filastexto = filas;
			}else {
				filastexto=(texto.length()/linea)+1;
			}
//		System.out.println(filastexto + " " + filas);
		int almohadillado = (texto.length())%linea;
		for (int i=0;i<((filastexto));i++) {
			if(i==(filastexto-1)) {
				formateado = formateado + " " + texto.substring(posicion,posicion+almohadillado) + "\n";
			}else {
				formateado = formateado + " " + texto.substring(posicion,posicion+linea) + "\n";
				posicion = posicion + linea;
			}
		}
		return formateado;
	}

	
	public static void main(String[] args){
		
		String saludo2 = "Lorem ipsum dolor.";
		VentanaNotificacion ventana2 = new VentanaNotificacion(saludo2);
		ventana2.setVisible(true);
		ventana2.subir_ventana();
		pausa(3000);
		ventana2.bajar_ventana();
		ventana2.setVisible(false);
		return;
	}
}
