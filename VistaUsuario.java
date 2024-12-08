package vista;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.SwingConstants;

import controlador.Ctrl_Usuario;
import modelo.Usuario;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VistaUsuario extends JInternalFrame {
	
	private JTextField txt_nombre;
	private JTextField txt_apellido;
	private JTextField txt_usuario;
    private JLabel jLabel_nombre;
	private JLabel jLabel_cantidad;
	private JLabel jLabel_precio;
	private JLabel jLabel_descripcion;
	private JLabel jLabel_iva;
	private JTextField txt_telefono;
	private JPasswordField txt_password;
	private JTextField txt_passwordvisible;
	private JCheckBox chckbx_verclave;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaUsuario frame = new VistaUsuario();
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
	public VistaUsuario() {
		
		setClosable(true);
        setIconifiable(true);
        setSize(new Dimension(450, 300));
        setTitle("Nuevo Usuario");
        //setBounds(100, 100, 450, 300);
//        txt_password.setVisible(true);
//        txt_passwordvisible.setVisible(false);
		
		
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
		
		jLabel_precio = new JLabel("Usuario:");
		jLabel_precio.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel_precio.setForeground(new Color(0, 0, 0));
		jLabel_precio.setBounds(59, 103, 71, 16);
		getContentPane().add(jLabel_precio);
		
		jLabel_descripcion = new JLabel("Password:");
		jLabel_descripcion.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel_descripcion.setForeground(new Color(0, 0, 0));
		jLabel_descripcion.setBounds(51, 131, 79, 16);
		getContentPane().add(jLabel_descripcion);
		
		jLabel_iva = new JLabel("Telefono:");
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
		
		txt_usuario = new JTextField();
		txt_usuario.setColumns(10);
		txt_usuario.setBounds(175, 98, 192, 26);
		getContentPane().add(txt_usuario);
				
		JButton btn_guardar = new JButton("Guardar");
		btn_guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txt_nombre.getText().isEmpty()||txt_apellido.getText().isEmpty()||txt_usuario.getText().isEmpty()||txt_password.getText().isEmpty()||txt_telefono.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Completa todos los campos");
				}else {
					Usuario usuario=new Usuario();
					Ctrl_Usuario controlUsuario=new Ctrl_Usuario();
					
					if(!controlUsuario.existeUsuario(txt_usuario.getText().trim())) {
						usuario.setNombre(txt_nombre.getText().trim());
						usuario.setApellido(txt_apellido.getText().trim());
						usuario.setUsuario(txt_usuario.getText().trim());
						usuario.setPassword(txt_password.getText().trim());
						usuario.setTelefono(txt_telefono.getText().trim());
						usuario.setEstado(1);
						
						if(controlUsuario.guardar(usuario)) {
							JOptionPane.showMessageDialog(null, "Usuario registrado");
						}else {
							JOptionPane.showMessageDialog(null, "Error al registrar usuario");
						}
					}
				}
				Limpiar()
;			}});
		btn_guardar.setBounds(157, 205, 117, 29);
		getContentPane().add(btn_guardar);
		

//		 Configuración del layout y demás componentes
        getContentPane().setLayout(null);
        
        txt_telefono = new JTextField();
        txt_telefono.setColumns(10);
        txt_telefono.setBounds(175, 154, 192, 26);
        getContentPane().add(txt_telefono);
        
        txt_password = new JPasswordField();
        txt_password.setBounds(175, 126, 145, 26);
        getContentPane().add(txt_password);
        
        chckbx_verclave = new JCheckBox("");
        chckbx_verclave.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		if(chckbx_verclave.isSelected()==true) {
        			String pass="";
        			char[] passIngresado=txt_password.getPassword();
        			for(int i=0; i<passIngresado.length;i++) {
        				pass +=passIngresado[i];
        			}
        			txt_passwordvisible.setText(pass);
        			txt_password.setVisible(false);
        			txt_passwordvisible.setVisible(true);
        		}else{
        			String passIngresado=txt_passwordvisible.getText().trim();
        			txt_password.setVisible(true);
        			txt_passwordvisible.setVisible(false);
        		}
        	}
        });
        chckbx_verclave.setBounds(332, 127, 35, 23);
        getContentPane().add(chckbx_verclave);
        
        txt_passwordvisible = new JTextField();
        txt_passwordvisible.setBounds(175, 126, 145, 26);
        getContentPane().add(txt_passwordvisible);
        txt_passwordvisible.setColumns(10);
	}

    
    private void Limpiar() {
    	txt_nombre.setText("");
    	txt_apellido.setText("");
    	txt_usuario.setText("");
    	txt_password.setText("");
    	txt_passwordvisible.setText("");
    	txt_telefono.setText("");

    }
}
