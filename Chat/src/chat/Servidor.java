package chat;
import java.awt.event.*;
import javax.swing.*;

import java.net.*;
import java.io.*;

public class Servidor extends Thread {
	
	BotonServidor escuchaboton; 
	TextoServidor escuchatexto;
	Chat ventanaservidor;
	ServerSocket socketservidor;
	Socket nuevaconexion;
	boolean conectado = true;
	int puerto = 950;
	Eco eco;
	
	Servidor() {
		ventanaservidor = new Chat (120,80, "Servidor del chat");
		escuchaboton = new BotonServidor(this); 
		escuchatexto = new TextoServidor();
		ventanaservidor.texto.addActionListener(escuchatexto);
		ventanaservidor.boton.addActionListener(escuchaboton);
		eco = new Eco(ventanaservidor);
		eco.start();
	}
	
	public void run() {
		while(true){
			try{
				ventanaservidor.boton.setText("Desconectar");
				//ventanaservidor.area.append("Hola1\n");
				socketservidor = new ServerSocket(puerto);
				while(conectado){
//					socketservidor.setSoTimeout(5000);
					ventanaservidor.area.append("Esperando la conexión de clientes a través del puerto " + puerto + "...\n");
					nuevaconexion = socketservidor.accept();
					ventanaservidor.area.append("Conectado con el cliente " + nuevaconexion.getInetAddress() + ".\n");
					DataInputStream entrada = new DataInputStream(nuevaconexion.getInputStream());
					DataOutputStream salida = new DataOutputStream(nuevaconexion.getOutputStream());
					eco.nueva_conexion(entrada, salida, nuevaconexion);
				}
			}
			catch(SocketTimeoutException e){
				ventanaservidor.area.append("Conexión caducada.\n");
				conectado = false;
				ventanaservidor.boton.setText("Conectar");
			}
			catch(BindException e){
				ventanaservidor.area.append("La dirección IP y el puerto ya están en uso\n");
				return;
			}
			catch(SocketException e){
				ventanaservidor.area.append("Conexión finalizada.\n");
			}
			catch(IOException e){
				e.printStackTrace();
			}
			try{
				sleep(100);
				conectado = false;
				System.out.println(conectado);
				ventanaservidor.boton.setText("Conectar");
				while(!conectado){
					sleep(100);
//					System.out.println("Estamos desconectados");
				}
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	public void conectar_servidor(){
		conectado=true;
	}

	public void desconectar_servidor(){
		conectado = false;
		try{
			socketservidor.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

}



class BotonServidor implements ActionListener {
	
	Servidor servidor;
	
	BotonServidor(Servidor servidor){
		this.servidor = servidor;
	}

	public void actionPerformed(ActionEvent b){
		if (servidor.conectado){
//			System.out.println("Acabas de desconectar");
			((JButton)b.getSource()).setText("Conectar");
			servidor.desconectar_servidor();
		}else{
//			System.out.println("Acabas de conectar");
			((JButton)b.getSource()).setText("Desconectar");
			servidor.conectar_servidor();
		}
//		System.out.println(servidor.conectado);
		
	}
	
}

class TextoServidor implements ActionListener {
	
	public void actionPerformed(ActionEvent t){
		
		System.out.println(((JTextField)t.getSource()).getText());
		((JTextField)t.getSource()).setText("");
		
	}
	
}