package vista;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.SwingConstants;

import conexion.Conexion;
import controlador.Ctrl_Producto;
import modelo.Producto;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class VistaProducto extends JInternalFrame {
	int obtenerIdCategoriaCombo = 0;
	private JTextField txt_nombre;
	private JTextField txt_cantidad;
	private JTextField txt_precio;
	private JTextField txt_descripcion;
	private JComboBox comboBox_iva;
    private JComboBox comboBox_categoria;
    private JLabel jLabel_nombre;
	private JLabel jLabel_cantidad;
	private JLabel jLabel_precio;
	private JLabel jLabel_descripcion;
	private JLabel jLabel_iva;
	private JLabel jLabel_categorias;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaProducto frame = new VistaProducto();
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
	public VistaProducto() {
		
		setClosable(true);
        setIconifiable(true);
        setSize(new Dimension(450, 300));
        setTitle("Nuevo Producto");
        //setBounds(100, 100, 450, 300);
		
		
		jLabel_nombre = new JLabel("Nombre:");
		jLabel_nombre.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel_nombre.setForeground(new Color(0, 0, 0));
		jLabel_nombre.setBounds(59, 48, 71, 16);
		getContentPane().add(jLabel_nombre);
		
		jLabel_cantidad = new JLabel("Cantidad:");
		jLabel_cantidad.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel_cantidad.setForeground(new Color(0, 0, 0));
		jLabel_cantidad.setBounds(59, 75, 71, 16);
		getContentPane().add(jLabel_cantidad);
		
		jLabel_precio = new JLabel("Precio:");
		jLabel_precio.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel_precio.setForeground(new Color(0, 0, 0));
		jLabel_precio.setBounds(59, 103, 71, 16);
		getContentPane().add(jLabel_precio);
		
		jLabel_descripcion = new JLabel("Descripcion:");
		jLabel_descripcion.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel_descripcion.setForeground(new Color(0, 0, 0));
		jLabel_descripcion.setBounds(51, 131, 79, 16);
		getContentPane().add(jLabel_descripcion);
		
		jLabel_iva = new JLabel("IVA:");
		jLabel_iva.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel_iva.setForeground(new Color(0, 0, 0));
		jLabel_iva.setBounds(51, 159, 79, 16);
		getContentPane().add(jLabel_iva);
		
		jLabel_categorias = new JLabel("Categorias:");
		jLabel_categorias.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel_categorias.setForeground(new Color(0, 0, 0));
		jLabel_categorias.setBounds(51, 188, 79, 16);
		getContentPane().add(jLabel_categorias);
		
		txt_nombre = new JTextField();
		txt_nombre.setBounds(175, 43, 192, 26);
		getContentPane().add(txt_nombre);
		txt_nombre.setColumns(10);
		
		txt_cantidad = new JTextField();
		txt_cantidad.setColumns(10);
		txt_cantidad.setBounds(175, 71, 192, 26);
		getContentPane().add(txt_cantidad);
		
		txt_precio = new JTextField();
		txt_precio.setColumns(10);
		txt_precio.setBounds(175, 98, 192, 26);
		getContentPane().add(txt_precio);
		
		txt_descripcion = new JTextField();
		txt_descripcion.setColumns(10);
		txt_descripcion.setBounds(175, 126, 192, 26);
		getContentPane().add(txt_descripcion);
		
		comboBox_iva = new JComboBox();
		comboBox_iva.setModel(new DefaultComboBoxModel(new String[] {"Seleccione iva:", "No grava iva", "16%", "8%"}));
		comboBox_iva.setBounds(175, 155, 192, 27);
		getContentPane().add(comboBox_iva);
		
		comboBox_categoria = new JComboBox();
		comboBox_categoria.setModel(new DefaultComboBoxModel(new String[] {"Seleccione Categoria:", "1", "2", "3"}));
		comboBox_categoria.setBounds(175, 184, 192, 27);
		getContentPane().add(comboBox_categoria);
				
		JButton btn_guardar = new JButton("Guardar");
		btn_guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Producto producto=new Producto();
				Ctrl_Producto controlProducto=new Ctrl_Producto();
				String iva="";
				String categoria="";
				iva=comboBox_iva.getSelectedItem().toString();
				categoria=comboBox_categoria.getSelectedItem().toString().trim();
				
				//validar campos
				if(txt_nombre.getText().equals("")||txt_cantidad.getText().equals("")||txt_precio.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Complete todos los campos");
					//txt_nombre.setBackground(Color.red); --igual con cantidad y precio
				}else {
					if(!controlProducto.existeProducto(txt_nombre.getText().trim())) {
						if(iva.equalsIgnoreCase("Seleccione iva:")) {
							JOptionPane.showMessageDialog(null, "Seleccione iva.");
						}else {
							if(categoria.equalsIgnoreCase("Seleccione Categoria:")) {
								JOptionPane.showMessageDialog(null, "Seleccione categoria.");
							}else {
								try {
									producto.setNombre(txt_nombre.getText().trim());
									producto.setCantidad(Integer.parseInt(txt_cantidad.getText().trim()));
									String precioTXT="";
									double Precio=0.0;
									precioTXT=txt_precio.getText().trim();
									boolean aux=false;
									
									//si el usuario ingresa una coma lo tranforma a punto
									for(int i=0;i<precioTXT.length(); i++) {
										if(precioTXT.charAt(i) == ',') {
											String precioNuevo=precioTXT.replace(",",".");
											Precio=Double.parseDouble(precioNuevo);
											aux=true;
										}
									}
									
									//evaluar la condicion
									if(aux==true) {
										producto.setPrecio(Precio);
									}else {
										Precio=Double.parseDouble(precioTXT);
										producto.setPrecio(Precio);
									}
									producto.setDescripcion(txt_descripcion.getText().trim());
									
									//porcentaje iva
									if(iva.equalsIgnoreCase("No grava iva")) {
										producto.setPorcentajeIva(0);
									}else if(iva.equalsIgnoreCase("16%")) {
										producto.setPorcentajeIva(16);
									}else if(iva.equalsIgnoreCase("8%")) {
										producto.setPorcentajeIva(8);
									}
									
									//idcategoria
									IdCategoria();
									producto.setIdCategoria(obtenerIdCategoriaCombo);
									producto.setEstado(1);
									if(controlProducto.guardar(producto)) {
										JOptionPane.showMessageDialog(null, "Registro Guardado");
										//se puede volver los colores a verde lol
										CargarComboCategorias();
										comboBox_iva.setSelectedItem("Seleccione iva:");
										Limpiar();
									}
									else {
										JOptionPane.showMessageDialog(null, "Error al Guardar");
									}
								}catch(HeadlessException | NumberFormatException x) {
									System.out.println("Error en: "+x.getMessage());
								}
							}
						}
					}else {
						JOptionPane.showMessageDialog(null, "El producto ya existe en la BD");
					}
				}
			}
		});
		btn_guardar.setBounds(156, 219, 117, 29);
		getContentPane().add(btn_guardar);
		
		CargarComboCategorias();

//		 Configuración del layout y demás componentes
        getContentPane().setLayout(null);

	}
	
	// Método para cargar las categorías desde la base de datos
    private void CargarComboCategorias() {
        Connection cn = Conexion.conectar(); // Conexión a la base de datos
        if (cn != null) {
            try {
                // Consultar las categorías desde la base de datos
                String query = "SELECT * FROM tb_categoria";
                Statement stmt = cn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                // Limpiar el comboBox antes de cargar los nuevos elementos
                comboBox_categoria.removeAllItems();

                // Añadir un item por defecto al comboBox
                comboBox_categoria.addItem("Seleccione Categoria:");

                // Llenar el comboBox con las categorías obtenidas
                while (rs.next()) {
                    //String categoria = rs.getString("descripcion");
                    //comboBox_categoria.addItem(categoria); // Añadir cada categoría al comboBox
                	comboBox_categoria.addItem(rs.getString("descripcion"));
                }
                cn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    cn.close(); // Cerrar la conexión
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    //obtener idcategoria
    private int IdCategoria() {
        String selectedCategoria = comboBox_categoria.getSelectedItem().toString();

        String sql = "SELECT idCategoria FROM tb_categoria WHERE descripcion = ?";
        

        try (Connection cn = Conexion.conectar(); 
             PreparedStatement pst = cn.prepareStatement(sql)) {

             pst.setString(1, selectedCategoria);  // Evitar inyecciones SQL

             ResultSet rs = pst.executeQuery();

             if (rs.next()) {
                 obtenerIdCategoriaCombo = rs.getInt("idCategoria");
             }
             cn.close();
         } catch (SQLException e) {
             e.printStackTrace();
         }

         return obtenerIdCategoriaCombo;
    }


    
    private void Limpiar() {
    	txt_nombre.setText("");
    	txt_cantidad.setText("");
    	txt_precio.setText("");
    	txt_descripcion.setText("");
    }
}
