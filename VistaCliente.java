package vista;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.SwingConstants;

import controlador.Ctrl_Cliente;
import modelo.Cliente;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VistaCliente extends JInternalFrame {
	
	private JTextField txt_nombre;
	private JTextField txt_apellido;
	private JTextField txt_cedula;
	private JTextField txt_telefono;
    private JLabel jLabel_nombre;
	private JLabel jLabel_cantidad;
	private JLabel jLabel_precio;
	private JLabel jLabel_descripcion;
	private JLabel jLabel_iva;
	private JTextField txt_direccion;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaCliente frame = new VistaCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VistaCliente() {
		
		setClosable(true);
        setIconifiable(true);
        setSize(new Dimension(450, 300));
        setTitle("Nuevo Cliente");
        //setBounds(100, 100, 450, 300);
		
		
		jLabel_nombre = new JLabel("Nombre:");
		jLabel_nombre.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel_nombre.setForeground(new Color(0, 0, 0));
		jLabel_nombre.setBounds(59, 48, 71, 16);
		getContentPane().add(jLabel_nombre);
		
		jLabel_cantidad = new JLabel("Apellido:");
		jLabel_cantidad.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel_cantidad.setForeground(new Color(0, 0, 0));
		jLabel_cantidad.setBounds(59, 75, 71, 16);
		getContentPane().add(jLabel_cantidad);
		
		jLabel_precio = new JLabel("Cedula:");
		jLabel_precio.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel_precio.setForeground(new Color(0, 0, 0));
		jLabel_precio.setBounds(59, 103, 71, 16);
		getContentPane().add(jLabel_precio);
		
		jLabel_descripcion = new JLabel("Telefono:");
		jLabel_descripcion.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel_descripcion.setForeground(new Color(0, 0, 0));
		jLabel_descripcion.setBounds(51, 131, 79, 16);
		getContentPane().add(jLabel_descripcion);
		
		jLabel_iva = new JLabel("Direccion:");
		jLabel_iva.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel_iva.setForeground(new Color(0, 0, 0));
		jLabel_iva.setBounds(51, 159, 79, 16);
		getContentPane().add(jLabel_iva);
		
		txt_nombre = new JTextField();
		txt_nombre.setBounds(175, 43, 192, 26);
		getContentPane().add(txt_nombre);
		txt_nombre.setColumns(10);
		
		txt_apellido = new JTextField();
		txt_apellido.setColumns(10);
		txt_apellido.setBounds(175, 71, 192, 26);
		getContentPane().add(txt_apellido);
		
		txt_cedula = new JTextField();
		txt_cedula.setColumns(10);
		txt_cedula.setBounds(175, 98, 192, 26);
		getContentPane().add(txt_cedula);
		
		txt_telefono = new JTextField();
		txt_telefono.setColumns(10);
		txt_telefono.setBounds(175, 126, 192, 26);
		getContentPane().add(txt_telefono);
				
		JButton btn_guardar = new JButton("Guardar");
		btn_guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cliente cliente=new Cliente();
				Ctrl_Cliente controlCliente=new Ctrl_Cliente();
				
				if(!txt_nombre.getText().isEmpty()&&!txt_apellido.getText().isEmpty()&&!txt_cedula.getText().isEmpty()) {
					if(!controlCliente.existeCliente(txt_cedula.getText().trim())) {
						
						cliente.setNombre(txt_nombre.getText().trim());
						cliente.setApellido(txt_apellido.getText().trim());
						cliente.setCedula(txt_cedula.getText().trim());
						cliente.setTelefono(txt_telefono.getText().trim());
						cliente.setDireccion(txt_direccion.getText().trim());
						cliente.setEstado(1);

						
						if(controlCliente.guardar(cliente)) {
							JOptionPane.showMessageDialog(null, "Registro guardado");

						}else {
							JOptionPane.showMessageDialog(null, "Error al guardar");

						}
						
					}else {
						JOptionPane.showMessageDialog(null, "El cliente ya esta registrado en la BD");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Completa todos los campos");
				}
				Limpiar();
			}});
		btn_guardar.setBounds(157, 205, 117, 29);
		getContentPane().add(btn_guardar);
		

//		 Configuración del layout y demás componentes
        getContentPane().setLayout(null);
        
        txt_direccion = new JTextField();
        txt_direccion.setColumns(10);
        txt_direccion.setBounds(175, 154, 192, 26);
        getContentPane().add(txt_direccion);
	}

    
    private void Limpiar() {
    	txt_nombre.setText("");
    	txt_apellido.setText("");
    	txt_cedula.setText("");
    	txt_telefono.setText("");
    	txt_direccion.setText("");

    }
}
