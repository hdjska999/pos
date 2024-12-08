package vista;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.SwingConstants;

import controlador.Ctrl_Categoria;
import modelo.Categoria;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VistaCategoria extends JInternalFrame {
	private JTextField txt_descripcion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaCategoria frame = new VistaCategoria();
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
	public VistaCategoria() {
		setIconifiable(true);
		setClosable(true);
		setSize(new Dimension(400, 200));
		setTitle("Nueva Categoria");
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Descripcion categoria:");
		lblNewLabel_1.setAlignmentY(50.0f);
		lblNewLabel_1.setAlignmentX(20.0f);
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setBounds(22, 49, 140, 16);
		getContentPane().add(lblNewLabel_1);
		
		txt_descripcion = new JTextField();
		txt_descripcion.setAlignmentY(50.0f);
		txt_descripcion.setAlignmentX(190.0f);
		txt_descripcion.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		txt_descripcion.setBounds(170, 45, 171, 26);
		getContentPane().add(txt_descripcion);
		txt_descripcion.setColumns(10);
		
		JButton btnNewButton = new JButton("Guardar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Categoria categoria=new Categoria();
				Ctrl_Categoria controlCategoria=new Ctrl_Categoria();
				categoria.setDescripcion(txt_descripcion.getText().trim());
				
				if(txt_descripcion.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Complete todos los campos");
				}else {
					
					if(!controlCategoria.existeCategoria(txt_descripcion.getText().trim())) {
						categoria.setDescripcion(txt_descripcion.getText().trim());
						categoria.setEstado(1);
						if(controlCategoria.guardar(categoria)) {
							JOptionPane.showMessageDialog(null, "Registro Guardado");
						}else {
							JOptionPane.showMessageDialog(null, "Error al Guardar");
						}
					}else {
						JOptionPane.showMessageDialog(null, "Esta categoria ya existe");
					}
				}
				txt_descripcion.setText("");
			}
		});
		btnNewButton.setAlignmentY(90.0f);
		btnNewButton.setAlignmentX(190.0f);
		btnNewButton.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton.setBackground(SystemColor.textHighlight);
		btnNewButton.setBounds(177, 101, 100, 26);
		getContentPane().add(btnNewButton);
		//setBounds(100, 100, 450, 300);

	}
}
