package chat;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class Notificacion extends Thread {

	String mensaje;
	VentanaNotificacion ventana;
	
	Notificacion(String mensaje){
		this.mensaje = mensaje;

	}

	public void run(){
		System.out.println(mensaje);
		ventana = new VentanaNotificacion(mensaje);
		ventana.setVisible(true);
		ventana.subir_ventana();
		try{
			sleep(3000);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		ventana.bajar_ventana();
		ventana.setVisible(false);
	}
	

	
	public static void pausa(int milisegundos) {
		try{
			TimeUnit.MILLISECONDS.sleep(milisegundos);
			}
		catch(InterruptedException e){
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args){

		VentanaNotificacion ventana = new VentanaNotificacion ("Holaaaaa");
		System.out.print(ventana.texto_formateado("Textoooo"));
		ventana.setVisible(true);
		ventana.subir_ventana();
		pausa(3000);
		ventana.bajar_ventana();
		ventana.setVisible(false);
		return;
	}
	
}