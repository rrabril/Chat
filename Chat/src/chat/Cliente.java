package chat;
import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

public class Cliente extends Thread implements ActionListener {
	
	Chat ventanacliente;
	String servidor = "ASES171212-PC";
	int puerto = 950;
	Socket conexion;
	DataInputStream entrada;
	DataOutputStream salida;
	TextoCliente textocliente;
	BotonCliente botoncliente;
	String minick;
	Boolean conectado=true;
	DateTimeFormatter formato_hora = DateTimeFormatter.ofPattern("hh:mm");
	JMenuBar barramenu;
	JMenu archivo, colores;
	JMenuItem enviararchivo, salir, blanco, turquesa, naranja, esmeralda, rosa;
	Container contenedor;
	static int num_notificaciones = 0;
	

	//cliente.texto.append("Hola");
	
	Cliente(){
		
		ventanacliente = new Chat(400,300, "Chat Bodega la Plata");
		ventanacliente.setLocationRelativeTo(null);
		textocliente = new TextoCliente();
		botoncliente = new BotonCliente(this);
		ventanacliente.texto.addActionListener(textocliente);
		ventanacliente.boton.addActionListener(botoncliente);
		minick = JOptionPane.showInputDialog("Introduce tu nick.");
		ventanacliente.texto.requestFocusInWindow();
		barramenu = new JMenuBar();
		ventanacliente.setJMenuBar(barramenu);
		archivo = new JMenu("Archivo");
		archivo.addActionListener(this);
		barramenu.add(archivo);
		enviararchivo = new JMenuItem("Enviar archivo");
		enviararchivo.addActionListener(this);
		archivo.add(enviararchivo);
		salir = new JMenuItem("Salir");
		salir.addActionListener(this);
		archivo.add(salir);
		colores = new JMenu("Colores");
		barramenu.add(colores);
		blanco = new JMenuItem("Blanco");
		blanco.addActionListener(this);
		colores.add(blanco);
		turquesa = new JMenuItem("Turquesa");
		turquesa.addActionListener(this);
		colores.add(turquesa);
		naranja = new JMenuItem("Naranja");
		naranja.addActionListener(this);
		colores.add(naranja);
		esmeralda = new JMenuItem("Esmeralda");
		esmeralda.addActionListener(this);
		colores.add(esmeralda);
		rosa = new JMenuItem("Rosa");
		rosa.addActionListener(this);
		colores.add(rosa);
		contenedor = ventanacliente.dame_contenedor();
		ventanacliente.setVisible(true);

				
	}
	
	
	public void run(){
		while(true){
			conectado = true;
			ventanacliente.boton.setText("Desconectar");
			try{
				try{
					sleep(1000);
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
				ventanacliente.area.append("Intentando conectar con el servidor " + servidor + " (ordenador de Rubén) a través del puerto " + puerto + ".\n");
				conexion = new Socket (servidor, puerto);
				entrada = new DataInputStream (conexion.getInputStream());
				salida = new DataOutputStream (conexion.getOutputStream());
				ventanacliente.area.append("Conectado.\n");
				salida.writeUTF(minick + " acaba de conectarse al chat.\n");
				salida.writeUTF("/alta");
				salida.writeUTF(minick);
				while(conectado){ //Bucle correspondiente al estado conectado
					String mensaje = entrada.readUTF();
					if (mensaje.equals("/lista")){
						int num = Integer.parseInt(entrada.readUTF());
						ventanacliente.listanicks.setText("");
//						ventanacliente.listanicks.append("Lista de usuarios conectados:\n");
						for (int i=0;i<num;i++){
							ventanacliente.listanicks.append(entrada.readUTF() + "\n");
						}
					}else{
					ventanacliente.area.append(mensaje);
					if (!(ventanacliente.isActive())){
						Thread notificacion = new Notificacion(mensaje);
						notificacion.start();
						}
					}
/*					ventanacliente.area.append(mensaje);
					if (!(ventanacliente.isActive())){
						Thread notificacion = new Notificacion(mensaje);
						notificacion.start();
						}*/
					ventanacliente.setVisible(true);
				}
				System.out.println("¿Se llega a ejecutar este bloque?");
				//conexion.close();
			}
			catch(UnknownHostException e){
					System.out.println("Servidor desconocido.\n");	
				}
			catch(ConnectException e){
				ventanacliente.area.append("Conexión rechazada por el servidor " + servidor + " a través del puerto " + puerto + ".\n");
				e.printStackTrace();
			}
			catch(SocketException e){
				ventanacliente.area.append("Conexión finalizada.\n");
			}
			catch(IOException e){
				e.printStackTrace();
			}
			conectado = false;
			try{
				while(!conectado){ //Bucle correspondiente al estado desconectado
					sleep(100); //Esto es para que el programa pille los eventos del botón
				}
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
			ventanacliente.boton.setText("Conectar");
		}
	}
	
	public void set_conectado(){
		conectado = true;
	}
	
	public void set_desconectado(){
		conectado = false;
		try{
			conexion.close();
			}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void actionPerformed (ActionEvent e) {
		if (e.getSource()==enviararchivo){
			
		}
		if (e.getSource()==salir){
			System.exit(0);
		}
		if (e.getSource()==blanco){
			ventanacliente.area.setBackground(new Color(255,255,255));
		}
		if (e.getSource()==turquesa){
			ventanacliente.area.setBackground(new Color(93,193,185));
		}
		if (e.getSource()==naranja){
			ventanacliente.area.setBackground(new Color(193,127,23));
		}
		if (e.getSource()==esmeralda){
			ventanacliente.area.setBackground(new Color(0,157,113));
		}
		if (e.getSource()==rosa){
			ventanacliente.area.setBackground(new Color(255,150,150));
		}
	}

	
	class BotonCliente implements ActionListener {
		
		Cliente cliente;
		boolean conectado = true;
		
		BotonCliente(Cliente cliente){
			this.cliente = cliente;
		}

		public void actionPerformed(ActionEvent b){
			if (conectado){
//				System.out.println("Acabas de desconectar");
				set_desconectado();
				((JButton)b.getSource()).setText("Conectar");
				conectado = false;
			}else{
//				System.out.println("Acabas de conectar");
				set_conectado();
				((JButton)b.getSource()).setText("Desconectar");
				conectado = true;
			}
//			System.out.println(servidor.conectado);
			
		}
		
	}


	class TextoCliente implements ActionListener{
		
		public void actionPerformed (ActionEvent t){
			
			try{
				String hora = (LocalTime.now()).format(formato_hora);				
				
				salida.writeUTF("<" + minick + "> " + ((JTextField)t.getSource()).getText() + " (" + hora + ")\n");
			}
			catch(IOException e){
				e.printStackTrace();
			}
			((JTextField)t.getSource()).setText("");
		}
		
	}
	
}
