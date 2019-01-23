package chat;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class Eco extends Thread {
	
	DataInputStream[] entradas = new DataInputStream[20];
	DataOutputStream[] salidas = new DataOutputStream[20];
	Socket[] conexiones = new Socket [20];
	Gestor_conexion[] gestores = new Gestor_conexion[20];
	String[] nicks = new String[20];
	String mensaje;
	static int clientes = 0;
	Chat ventanaservidor;
	
	Eco(Chat ventanaservidor){
		this.ventanaservidor=ventanaservidor;
	}
	
	public void nueva_conexion(DataInputStream entrada, DataOutputStream salida, Socket nuevaconexion){
		gestores[clientes] = new Gestor_conexion(this, entrada, nuevaconexion, clientes);
		gestores[clientes].start();
		clientes++;
		ventanaservidor.area.append(clientes + " clientes conectados.\n");
		this.entradas[clientes-1]=entrada;
		this.salidas[clientes-1]=salida;
		this.conexiones[clientes-1]=nuevaconexion;
		this.nicks[clientes-1]="";
	}
	
	public void run() {
		while(true){
			while(clientes==0){
				try{
					sleep(100);
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public void difusion(String mensaje){
		for (int j=0;j<clientes;j++){
			System.out.println("Mensaje al usuario " + j +" (" + nicks[j] +").");
			try{
				salidas[j].writeUTF(mensaje);}
			catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public void alta_usuario(int id1, String nick){
		nicks[id1]=nick;
		System.out.println(nicks[id1]);
		actualizar_usuarios();
	}
	
	public void actualizar_usuarios(){
		difusion("/lista");
		difusion(Integer.toString(clientes));
		for (int i = 0; i<clientes; i++){
			difusion(nicks[i]);
		}
	}
	
	public void eliminar_usuario(int usuario){
		System.out.println("Un usuario se ha desconectado");
		ventanaservidor.area.append("El usuario " + conexiones[usuario].getInetAddress() + " se ha desconectado.\n");
		difusion(nicks[usuario] + " se ha desconectado.\n");
		try{conexiones[usuario].close();
		}
		catch(IOException c){
			c.printStackTrace();
		}
		int paso=0;
		for (int i=0;i<(clientes-2);i++){
			if (i==usuario){
				paso=1;
			}
			entradas[i] = entradas [i+paso];
			salidas[i] = salidas [i+paso];
			conexiones[i] = conexiones [i+paso];
		}
		clientes--;
		ventanaservidor.area.append(clientes + " clientes conectados.\n");
		actualizar_usuarios();
	}

}
