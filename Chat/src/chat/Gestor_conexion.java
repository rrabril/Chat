package chat;
import java.io.*;
import java.net.*;

public class Gestor_conexion extends Thread {
	
	DataInputStream entrada;
	String mensaje;
	Eco eco;
	Socket conexion;
	int id;
	
	Gestor_conexion(Eco eco, DataInputStream entrada, Socket conexion, int id){
		this.eco = eco;
		this.entrada = entrada;
		this.id = id;
	}
	
	public void run(){
		while(true){
			try{
				mensaje = entrada.readUTF();
				if (mensaje.equals("/alta")){
					eco.alta_usuario(id, entrada.readUTF());
				}else{
				eco.difusion(mensaje);
				}
			}
			catch(IOException e){
				eco.eliminar_usuario(id);
				try{
					conexion.close();
				}
				catch(IOException b){
					b.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}

}
