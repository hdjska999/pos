package vista;

import java.awt.EventQueue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextField;

import conexion.Conexion;
import controlador.Ctrl_Producto;
import modelo.Producto;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VistaActualizarStock extends JInternalFrame {
	private JTextField txt_cantidad_actual;
	private JTextField txt_cantidad_nueva;
	JComboBox comboBox;
	int idProducto=0;
	int cantidad=0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaActualizarStock frame = new VistaActualizarStock();
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
	public VistaActualizarStock() {
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 450, 300);
		setTitle("Actualizar Stock de los Productos");
		setSize(400,300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Producto:");
		lblNewLabel.setBounds(21, 25, 61, 16);
		getContentPane().add(lblNewLabel);
		
		JLabel lblStockActual = new JLabel("Stock Actual:");
		lblStockActual.setBounds(21, 63, 90, 16);
		getContentPane().add(lblStockActual);
		
		JLabel lblStockNuevo = new JLabel("Stock Nuevo:");
		lblStockNuevo.setBounds(21, 101, 90, 16);
		getContentPane().add(lblStockNuevo);
		
		txt_cantidad_actual = new JTextField();
		txt_cantidad_actual.setEnabled(false);
		txt_cantidad_actual.setBounds(115, 58, 130, 26);
		getContentPane().add(txt_cantidad_actual);
		txt_cantidad_actual.setColumns(10);
		
		txt_cantidad_nueva = new JTextField();
		txt_cantidad_nueva.setColumns(10);
		txt_cantidad_nueva.setBounds(115, 96, 130, 26);
		getContentPane().add(txt_cantidad_nueva);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MostrarStock();
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Seleccione producto:", "1", "2", "3"}));
		comboBox.setBounds(96, 21, 199, 27);
		getContentPane().add(comboBox);
		
		CargarComboProductos();
		
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!comboBox.getSelectedItem().equals("Seleccione producto:")) {
					
					if(!txt_cantidad_nueva.getText().isEmpty()) {
						
						boolean validacion=validar(txt_cantidad_nueva.getText().trim());
						if(validacion==true) {
							
							if(Integer.parseInt(txt_cantidad_nueva.getText())>0) {
								Producto producto=new Producto();
								Ctrl_Producto controlProducto=new Ctrl_Producto();
								int stockActual=Integer.parseInt(txt_cantidad_actual.getText().trim());
								int stockNuevo=Integer.parseInt(txt_cantidad_nueva.getText().trim());
								
								stockNuevo=stockActual+stockNuevo;
								producto.setCantidad(stockNuevo);
								if(controlProducto.actualizarStock(producto, idProducto)) {
									JOptionPane.showMessageDialog(null, "Stock Actulizado");
									comboBox.setSelectedItem("Seleccione producto:");
									txt_cantidad_actual.setText(" ");
									txt_cantidad_nueva.setText(" ");
									CargarComboProductos();
								}else {
									JOptionPane.showMessageDialog(null, "Error al actualizar Stock");
								}
								
							}else {
								JOptionPane.showMessageDialog(null, "La cantidad no puede ser 0");
							}
						}else {
							JOptionPane.showMessageDialog(null, "No se admiten caracteres no numericos");
						}
					}else {
						JOptionPane.showMessageDialog(null, "Ingrese una nueva cantidad al Stock");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Seleccione un producto");
				}
			}
		});
		btnActualizar.setBounds(71, 148, 117, 29);
		getContentPane().add(btnActualizar);
//		getContentPane().setLayout(null);

	}
	
	//metodo para cargar los producto en el comboBox
	private void CargarComboProductos() {
		Connection cn=Conexion.conectar();
		String sql="select * from tb_producto";
		Statement st;
		
		try {
			st=cn.createStatement();
			ResultSet rs=st.executeQuery(sql);
			comboBox.removeAllItems();
			comboBox.addItem("Seleccione productos:");
			
			while(rs.next()) {
				comboBox.addItem(rs.getString("nombre"));
			}
		}catch(SQLException e) {
			System.out.println("Error al cargar los productos en :"+e);
		}
		
	}
	
	//metodo para mostrar el stock del producto
	private void MostrarStock() {
		try {
			Connection cn=Conexion.conectar();
			String sql="select * from tb_producto where nombre = '"+comboBox.getSelectedItem()+"'";
			Statement st;
			st=cn.createStatement();
			ResultSet rs=st.executeQuery(sql);
			
			if(rs.next()) {
				idProducto=rs.getInt("idProducto");
				cantidad=rs.getInt("cantidad");
				txt_cantidad_actual.setText(String.valueOf(cantidad));
			}else {
				txt_cantidad_actual.setText(" ");
			}
			
		}catch(SQLException e) {
			System.out.println("Error al obtener Stock del producto en:"+e);
		}
	}
	
	//metodo de caracteres no numericos
	private boolean validar(String valor) {
		int num;
		try {
			num=Integer.parseInt(valor);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
	}
}
