package chat;
import javax.swing.*;

import java.awt.*;
import java.net.*;
import java.io.*;

public class Chat extends JFrame implements Runnable {
	
	boolean salir = false;
	JTextField texto;
	JButton boton;
	TextArea area;
	TextArea listanicks;
	Container contenedor;
	
	Chat(int x, int y, String titulo){
		
		super(titulo);
		setSize(600,400);
		setLocation(x,y);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panelcentral = new JPanel(new FlowLayout(FlowLayout.LEADING));
		area = new TextArea (15,80);
		area.setEditable(false);
		listanicks = new TextArea (15,20);
		listanicks.setEditable(false);
		JScrollPane deslizable = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JScrollPane escrolnicks = new JScrollPane (listanicks);
		JPanel panelderecho = new JPanel();
		panelderecho.add(escrolnicks);
		panelcentral.add(deslizable);
		JPanel panelinferior = new JPanel();
		panelinferior.setLayout(new FlowLayout());
		texto = new JTextField(40);
		boton = new JButton ("Conectar");
		panelinferior.add(texto);
		panelinferior.add(boton);
		Container contenedor = getContentPane();
		contenedor.add(panelcentral,BorderLayout.CENTER);
		contenedor.add(panelinferior,BorderLayout.SOUTH);
		setVisible(true);
		TextArea listanicks = new TextArea (15,20);
		listanicks.setEditable(false);
		panelderecho.add(escrolnicks);
		contenedor.add(panelderecho,BorderLayout.EAST); // Sólo en el cliente

				
	}
	
	public void run(){
			
	}
	
	public Container dame_contenedor(){
		return contenedor;
	}
	
	public static void main(String[] args){
		
		Thread servidor = new Servidor();
		servidor.start();
		Thread cliente = new Cliente();
		cliente.start();
		
	}

}
