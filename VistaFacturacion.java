package vista;

import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import conexion.Conexion;
import controlador.Ctrl_RegistrarVenta;
import controlador.VentaTXT;
import modelo.CabeceraVenta;
import modelo.DetalleVenta;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Panel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;

public class VistaFacturacion extends JInternalFrame {
	private JTextField txt_cliente_buscar;
	private JTextField txt_cantidad;
	public static JTable table;
	private JTextField txt_subtotal;
	private JTextField txt_descuento;
	private JTextField txt_iva;
	private JTextField txt_totalapagar;
	private JTextField txt_efectivo;
	private JTextField txt_cambio;
	JScrollPane scrollPane;
	JComboBox comboBox_cliente;
	JComboBox comboBox_producto;
	JButton btn_calcularCambio;
	JButton btn_registro;
	private DefaultTableModel modeloDatosProductos;
	private int cantidad=0;//cantidad de productos a comprar
	private double subtotal=0.0;//cantidad por precio
	private double descuento=0.0;
	private double iva=0.0;
	private double totalPagar=0.0;
	private int idProducto=0;
	private String nombre="";
	private int cantidadProductoBBDD;
	private double precioUnitario=0.0;
	private int porcentajeIva=0;
	private int auxIdDetalle=1;//id del detalle de venta
	private ArrayList<DetalleVenta> listaProductos=new ArrayList<>();//lista para el detalle de venta de los productos
	private DetalleVenta producto;
	//variables para calculos globales
	private double subtotalGeneral=0.0;
	private double descuentoGeneral=0.0;
	private double ivaGeneral=0.0;
	private double totalPagarGeneral=0.0;
	int idArrayList=0;
	private int idCliente=0;//id del  cliente seleccionado

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaFacturacion frame = new VistaFacturacion();
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
	public VistaFacturacion() {
		setClosable(true);
		//setBounds(100, 100, 450, 300);
		setSize(900,700);
		setTitle("Facturacion");
		
		JLabel lblNewLabel = new JLabel("Cliente:");
		lblNewLabel.setBounds(45, 28, 61, 16);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Producto:");
		lblNewLabel_1.setBounds(45, 70, 61, 16);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Cantidad:");
		lblNewLabel_2.setBounds(316, 70, 61, 16);
		getContentPane().add(lblNewLabel_2);
		
		comboBox_cliente = new JComboBox();
		comboBox_cliente.setModel(new DefaultComboBoxModel(new String[] {"Seleccione cliente:", "1", "2", "3"}));
		comboBox_cliente.setBounds(107, 24, 178, 27);
		getContentPane().add(comboBox_cliente);
		
		comboBox_producto = new JComboBox();
		comboBox_producto.setModel(new DefaultComboBoxModel(new String[] {"Seleccione producto:"}));
		comboBox_producto.setBounds(107, 66, 187, 27);
		getContentPane().add(comboBox_producto);
		
		txt_cliente_buscar = new JTextField();
		txt_cliente_buscar.setBounds(286, 23, 121, 26);
		getContentPane().add(txt_cliente_buscar);
		txt_cliente_buscar.setColumns(10);
		
		txt_cantidad = new JTextField();
		txt_cantidad.setColumns(10);
		txt_cantidad.setBounds(389, 65, 104, 26);
		getContentPane().add(txt_cantidad);
		
		JButton btn_buscarCliente = new JButton("Buscar");
		btn_buscarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String clienteBuscar=txt_cliente_buscar.getText().trim();
				Connection cn=Conexion.conectar();
				String sql="select nombre, apellido from tb_cliente where cedula = '"+ clienteBuscar+"'";
				Statement st;
				
				try {
					st=cn.createStatement();
					ResultSet rs=st.executeQuery(sql);
					
					if(rs.next()) {
						comboBox_cliente.setSelectedItem(rs.getString("nombre")+" "+rs.getString("apellido"));
					}else {
						comboBox_cliente.setSelectedItem("Seleccione cliente:");
						JOptionPane.showMessageDialog(null, "cedula de cliente incorrecta o no encontrada");
					}
					txt_cliente_buscar.setText("");
					cn.close();
					
				}catch(SQLException x) {
					System.out.println("Error al buscar cliente"+x);
				}
			}
		});
		btn_buscarCliente.setBounds(408, 24, 85, 27);
		getContentPane().add(btn_buscarCliente);
		
		JButton btn_añadir = new JButton("Add");
		btn_añadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String combo=comboBox_producto.getSelectedItem().toString();
				//validar que seleccione un producto
				if(combo.equalsIgnoreCase("Seleccione producto:")) {
					JOptionPane.showMessageDialog(null, "Seleccione un producto");
				}else {//validar que ingrese una cantidad
					if(!txt_cantidad.getText().isEmpty()) {
						//validar que no ingrese numeros
						boolean validacion=validar(txt_cantidad.getText());
						if(validacion) {
							//validar que la cantidad sea mayor a cero
							if(Integer.parseInt(txt_cantidad.getText())>0) {
								cantidad=Integer.parseInt(txt_cantidad.getText());
								//obtener datos del producto
								DatosDelProducto();
								//validar la cantidad de productos seleccionados no sea mayor al stock
								if(cantidad<=cantidadProductoBBDD) {
									subtotal=precioUnitario*cantidad;
									totalPagar=subtotal+iva+descuento;
									
									subtotal=(double)Math.round(subtotal*100)/100;
									iva=(double)Math.round(iva*100)/100;
									descuento=(double)Math.round(descuento*100)/100;
									totalPagar=(double)Math.round(totalPagar*100)/100;
									
									//se crea un nuevo producto
									producto=new DetalleVenta(auxIdDetalle, 1, idProducto, nombre, Integer.parseInt(txt_cantidad.getText()),
											precioUnitario,subtotal, descuento, iva, totalPagar, 1);
									
									listaProductos.add(producto);
//									if (listaProductos.isEmpty()) {
//									    System.out.println("La lista de productos está vacía.");
//									} else {
//									    JOptionPane.showMessageDialog(null, "Producto agregado");
//									    listaTablaProductos();
//									}
									JOptionPane.showMessageDialog(null, "Producto agregado");
									listaTablaProductos();
									auxIdDetalle++;
									txt_cantidad.setText("");
									CargarComboProductos();
									CalcularTotalPagar();
									txt_efectivo.setEnabled(true);
									btn_calcularCambio.setEnabled(true);
									
								}else {
									JOptionPane.showMessageDialog(null, "La cantidad supera el Stock");
								}
							}else {
								JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0");
							}
						}else {
							JOptionPane.showMessageDialog(null, "No se admiten caracteres no numericos");
						}
					}else {
						JOptionPane.showMessageDialog(null, "Ingresa la cantidad de productos");
					}
				}
			}
		});
		btn_añadir.setBounds(493, 66, 75, 27);
		getContentPane().add(btn_añadir);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(45, 126, 686, 231);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int fila_point=table.rowAtPoint(e.getPoint());
				int columna_point=0;
				
				if(fila_point > -1) {
					idArrayList= (int) modeloDatosProductos.getValueAt(fila_point, columna_point);
				}
				int opcion=JOptionPane.showConfirmDialog(null, "Eliminar Producto?");
				switch(opcion) {
				case 0:
					listaProductos.remove(idArrayList -1);
					CalcularTotalPagar();
					listaTablaProductos();
					break;
				case 1:
					break;
				default:
					break;
				}
			}
		});
		scrollPane.setViewportView(table);
		
		Panel panel = new Panel();
		panel.setBounds(397, 368, 334, 165);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Subtotal:");
		lblNewLabel_3.setBounds(16, 16, 61, 16);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Descuento:");
		lblNewLabel_3_1.setBounds(16, 39, 71, 16);
		panel.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_2 = new JLabel("IVA:");
		lblNewLabel_3_2.setBounds(16, 62, 35, 16);
		panel.add(lblNewLabel_3_2);
		
		JLabel lblNewLabel_3_3 = new JLabel("Total a pagar:");
		lblNewLabel_3_3.setBounds(16, 90, 92, 16);
		panel.add(lblNewLabel_3_3);
		
		JLabel lblNewLabel_3_4 = new JLabel("Efectivo:");
		lblNewLabel_3_4.setBounds(16, 116, 61, 16);
		panel.add(lblNewLabel_3_4);
		
		JLabel lblNewLabel_3_5 = new JLabel("Cambio:");
		lblNewLabel_3_5.setBounds(16, 138, 61, 16);
		panel.add(lblNewLabel_3_5);
		
		txt_subtotal = new JTextField();
		txt_subtotal.setEnabled(false);
		txt_subtotal.setBounds(76, 11, 118, 26);
		panel.add(txt_subtotal);
		txt_subtotal.setColumns(10);
		
		txt_descuento = new JTextField();
		txt_descuento.setEnabled(false);
		txt_descuento.setColumns(10);
		txt_descuento.setBounds(86, 34, 118, 26);
		panel.add(txt_descuento);
		
		txt_iva = new JTextField();
		txt_iva.setEnabled(false);
		txt_iva.setColumns(10);
		txt_iva.setBounds(46, 57, 118, 26);
		panel.add(txt_iva);
		
		txt_totalapagar = new JTextField();
		txt_totalapagar.setEnabled(false);
		txt_totalapagar.setColumns(10);
		txt_totalapagar.setBounds(105, 85, 118, 26);
		panel.add(txt_totalapagar);
		
		txt_efectivo = new JTextField();
		txt_efectivo.setColumns(10);
		txt_efectivo.setBounds(76, 111, 118, 26);
		panel.add(txt_efectivo);
		
		txt_cambio = new JTextField();
		txt_cambio.setEnabled(false);
		txt_cambio.setColumns(10);
		txt_cambio.setBounds(76, 133, 118, 26);
		panel.add(txt_cambio);
		
		btn_calcularCambio = new JButton("Calcular");
		btn_calcularCambio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txt_efectivo.getText().isEmpty()) {
					//ver que no ingrese caracteres no numericos
					boolean validacion=validarDouble(txt_efectivo.getText());
						if(validacion==true) {
							double efc=Double.parseDouble(txt_efectivo.getText().toString());
							double top=Double.parseDouble(txt_totalapagar.getText().toString());
							
							if(efc<top) {
								JOptionPane.showMessageDialog(null, "La cantidad no es suficiente");
							}else {
								double cambio=(efc-top);
								double cambi=(double)Math.round(cambio*100d)/100;
								String camb=String.valueOf(cambi);
								txt_cambio.setText(camb);
							}
							
						}else {
							JOptionPane.showMessageDialog(null, "No se admiten caracteres no numericos");
						}
					
				}else {
					JOptionPane.showMessageDialog(null, "Ingrese el dinero en efectivo");
				}
			}
		});
		btn_calcularCambio.setBounds(194, 111, 95, 26);
		panel.add(btn_calcularCambio);
		
		btn_registro = new JButton("");
		btn_registro.setBounds(260, 393, 131, 40);
		getContentPane().add(btn_registro);
		
		CargarComboClientes();
		CargarComboProductos();
		inicializarTablaProductos();
		txt_efectivo.setEnabled(false);
		btn_calcularCambio.setEnabled(false);
		txt_subtotal.setText("0.0");
		txt_iva.setText("0.0");
		txt_descuento.setText("0.0");
		txt_totalapagar.setText("0.0");
		
		JButton btn_registrarVenta = new JButton("Registrar Venta");
		btn_registrarVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CabeceraVenta cabeceraVenta=new CabeceraVenta();
				DetalleVenta detalleVenta= new DetalleVenta();
				Ctrl_RegistrarVenta controlVenta=new Ctrl_RegistrarVenta();
				
				String fechaActual="";
				Date date=new Date();
				fechaActual=new SimpleDateFormat("yyyy/MM/dd").format(date);
				
				if(!comboBox_cliente.getSelectedItem().equals("Seleccione cliente:")) {
					if(listaProductos.size()>0) {
						ObtenerIdCliente();
						//registrar la cabecera
						cabeceraVenta.setIdCabeceraventa(0);
						cabeceraVenta.setIdCliente(idCliente);
						cabeceraVenta.setValorPagar(Double.parseDouble(txt_totalapagar.getText()));
						cabeceraVenta.setFechaVenta(fechaActual);
						cabeceraVenta.setEstado(1);
						
						if(controlVenta.guardar(cabeceraVenta)) {
							JOptionPane.showMessageDialog(null, "Venta Registrada");
							
							VentaTXT pdf=new VentaTXT();
							pdf.DatosClientes(idCliente);
							pdf.generarFacturaTXT();
							
							for(DetalleVenta elemento:listaProductos) {
								detalleVenta.setIdDetalleVenta(0);
								detalleVenta.setIdCabeceraVenta(0);
								detalleVenta.setIdProducto(elemento.getIdProducto());
								detalleVenta.setCantidad(elemento.getCantidad());
								detalleVenta.setPrecioUnitario(elemento.getPrecioUnitario());
								detalleVenta.setSubtotal(elemento.getSubtotal());
								detalleVenta.setDescuento(elemento.getDescuento());
								detalleVenta.setIva(elemento.getIva());
								detalleVenta.setTotalPagar(elemento.getTotalPagar());
								detalleVenta.setEstado(elemento.getEstado());

								if(controlVenta.guardarDetalle(detalleVenta)) {
									//System.out.println("Detalle de venta registrada");
									
									txt_subtotal.setText("0.0");
									txt_iva.setText("0.0");
									txt_descuento.setText("0.0");
									txt_totalapagar.setText("0.0");
									txt_efectivo.setText("0.0");
									txt_cambio.setText("0.0");
									auxIdDetalle=1;
									
									CargarComboClientes();
									RestarStockProductos(elemento.getIdProducto(), elemento.getCantidad());
									
								}else {
									JOptionPane.showMessageDialog(null, "Error al guardar detalle de venta");
								}
							}
							listaProductos.clear();
							listaTablaProductos();
						}else {
							JOptionPane.showMessageDialog(null, "Error al guardar cabecera de venta");
						}
					}else {
						JOptionPane.showMessageDialog(null, "Seleccione un producto");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Seleccione un cliente");
				}
			}
		});
		getContentPane().add(btn_registrarVenta, BorderLayout.SOUTH);

//		listaTablaProductos();
		
		//getContentPane().setLayout(null);


	}
	
	//tabla de los productos
	private void inicializarTablaProductos() {
		modeloDatosProductos=new DefaultTableModel();
		modeloDatosProductos.addColumn("N");
		modeloDatosProductos.addColumn("Nombre");
		modeloDatosProductos.addColumn("Cantidad");
		modeloDatosProductos.addColumn("P. Unitario");
		modeloDatosProductos.addColumn("SubTotal");
		modeloDatosProductos.addColumn("Descuento");
		modeloDatosProductos.addColumn("Iva");
		modeloDatosProductos.addColumn("Total Pagar");
		modeloDatosProductos.addColumn("Accion");
		
		table.setModel(modeloDatosProductos);

	}
	//metodo para mostrar informacion de la tabla DetalleVenta
	private void listaTablaProductos() {
		modeloDatosProductos.setRowCount(listaProductos.size());
		for(int i=0; i<listaProductos.size(); i++) {
			modeloDatosProductos.setValueAt(i+1, i, 0);
			modeloDatosProductos.setValueAt(listaProductos.get(i).getNombre(), i, 1);
			modeloDatosProductos.setValueAt(listaProductos.get(i).getCantidad(), i, 2);
			modeloDatosProductos.setValueAt(listaProductos.get(i).getPrecioUnitario(), i, 3);
			modeloDatosProductos.setValueAt(listaProductos.get(i).getSubtotal(), i, 4);
			modeloDatosProductos.setValueAt(listaProductos.get(i).getDescuento(), i, 5);
			modeloDatosProductos.setValueAt(listaProductos.get(i).getIva(), i, 6);
			modeloDatosProductos.setValueAt(listaProductos.get(i).getTotalPagar(), i, 7);
			modeloDatosProductos.setValueAt("Eliminar", i, 8); //LUEGO PONER UN BOTON ELIMINAR
		}
		//añadir al table
		table.setModel(modeloDatosProductos);
	}
	
	//metodo para cargar clientes en el comboBox
	private void CargarComboClientes() {
		Connection cn=Conexion.conectar();
		String sql="select * from tb_cliente";
		Statement st;
		
		try {
			st=cn.createStatement();
			ResultSet rs=st.executeQuery(sql);
			comboBox_cliente.removeAllItems();
			comboBox_cliente.addItem("Seleccione cliente:");
			
			while(rs.next()) {
				comboBox_cliente.addItem(rs.getString("nombre")+" "+rs.getString("apellido"));
			}
			cn.close();
		}catch(SQLException e) {
			System.out.println("Error al cargar clientes");

		}
	}
	
	private void CargarComboProductos() {
		Connection cn=Conexion.conectar();
		String sql="select * from tb_producto";
		Statement st;
		
		try {
			st=cn.createStatement();
			ResultSet rs=st.executeQuery(sql);
			comboBox_producto.removeAllItems();
			comboBox_producto.addItem("Seleccione producto:");
			
			while(rs.next()) {
				comboBox_producto.addItem(rs.getString("nombre"));
			}
			cn.close();
		}catch(SQLException e) {
			System.out.println("Error al cargar productos:");
		}
	}
	
	//metodo para validar que el usuario no ingrese caracteres no numericos
	private boolean validar(String valor) {
		try {
			int num=Integer.parseInt(valor);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
	}
	
	private boolean validarDouble(String valor) {
		try {
			double num=Double.parseDouble(valor);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
	}
	
	private void DatosDelProducto(){
		try {
			String sql="select * from tb_producto where nombre = '"+comboBox_producto.getSelectedItem().toString()+"'";
			Connection cn=Conexion.conectar();
			Statement st;
			st=cn.createStatement();
			ResultSet rs=st.executeQuery(sql);
			
			while(rs.next()) {
				idProducto=rs.getInt("idProducto");
				nombre=rs.getString("nombre");
				cantidadProductoBBDD=rs.getInt("cantidad");
				precioUnitario = rs.getDouble("precio");
				porcentajeIva=rs.getInt("porcentajeIva");
				CalcularIva(precioUnitario, porcentajeIva);//calcula y retorna iva
				
			}
		}catch(SQLException e) {
			System.out.println("Error al obtener datos del producto"+e);
		}
	}
	
	private double CalcularIva(double precio, int porcentaje) {
		int p_iva=porcentaje;
		
		switch(p_iva) {
		case 0:
			iva=0.0;
			break;
		case 16:
			iva=(precio*cantidad)*0.16;
			break;
		case 8:
			iva=(precio*cantidad)*0.08;
			break;

		}
		return iva;
	}
	
	private void CalcularTotalPagar(){
		subtotalGeneral=0;
		descuentoGeneral=0;
		ivaGeneral=0;
		totalPagarGeneral=0;
		
		for(DetalleVenta elemento: listaProductos) {
			subtotalGeneral +=elemento.getSubtotal();
			descuentoGeneral +=elemento.getDescuento();
			ivaGeneral +=elemento.getIva();
			totalPagarGeneral +=elemento.getTotalPagar();
		}
		subtotalGeneral=(double)Math.round(subtotalGeneral*100)/100;
		ivaGeneral=(double)Math.round(ivaGeneral*100)/100;
		descuentoGeneral=(double)Math.round(descuentoGeneral*100)/100;
		totalPagarGeneral=(double)Math.round(totalPagarGeneral*100)/100;
		
		txt_subtotal.setText(String.valueOf(subtotalGeneral));
		txt_iva.setText(String.valueOf(ivaGeneral));
		txt_descuento.setText(String.valueOf(descuentoGeneral));
		txt_totalapagar.setText(String.valueOf(totalPagarGeneral));
	}
	
	private void ObtenerIdCliente(){
		try {
			String sql="select * from tb_cliente where concat(nombre,' ',apellido) = '"+comboBox_cliente.getSelectedItem()+"'";
			Connection cn=Conexion.conectar();
			Statement st;
			st=cn.createStatement();
			ResultSet rs= st.executeQuery(sql);
			while(rs.next()) {
				idCliente=rs.getInt("idCliente");
			}
		}catch(SQLException e) {
			System.out.println("Error al obtener id del cliente "+e);
		}
	}
	//metodo para restar la cantidad de productos vendidos
	private void RestarStockProductos(int idProducto, int cantidad){
		int cantidadProductosBD=0;
		try {
			Connection cn=Conexion.conectar();
			String sql="select idProducto, cantidad from tb_producto where idProducto = '"+idProducto+"'";
			Statement st;
			st=cn.createStatement();
			ResultSet rs=st.executeQuery(sql);
			while(rs.next()) {
				cantidadProductosBD=rs.getInt("cantidad");
			}
			cn.close();
		}catch(SQLException e) {
			System.out.println("Error al restar cantidad 1,"+ e);
		}
		
		try {
			Connection cn=Conexion.conectar();
			PreparedStatement consulta=cn.prepareStatement("update tb_producto set cantidad=? where idProducto = '"+idProducto+"'");
			int cantidadNueva=cantidadProductosBD-cantidad;
			consulta.setInt(1, cantidadNueva);
			if(consulta.executeUpdate()>0) {
				System.out.println("Todo bien");
			}
			cn.close();
		}catch(SQLException e) {
			System.out.println("Error al restar cantidad 2, "+e);
		}
	}
}
