package vista;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import conexion.Conexion;
import controlador.Ctrl_Producto;
import modelo.Producto;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.border.EtchedBorder;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class VistaGestionarProducto extends JInternalFrame {
    
    private int idProducto;
    int obtenerIdCategoriaCombo=0;
    public static JScrollPane jScrollPanel;
    public static JTable jTable_producto;
    private JTextField txt_nombre;

    // Declaración de los botones
    private JButton jButton_actualizar;
    private JButton jButton_eliminar;
    private JLabel lblNewLabel;
    private JLabel lblCantidad;
    private JLabel lblDescripicion;
    private JLabel lblIva;
    private JLabel lblCategoria;
    private JTextField txt_cantidad;
    private JTextField txt_descripcion;
    private JComboBox comboBox_categoria;
    private JComboBox comboBox_iva;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VistaGestionarProducto frame = new VistaGestionarProducto();
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
    public VistaGestionarProducto() {
        setIconifiable(true);
        setClosable(true);
        setSize(new Dimension(900, 500));
        setTitle("Gestionar Productos");
        
        // Inicializamos la tabla antes de usarla
        jTable_producto = new JTable();
        //jTable_categoria.setModel(new DefaultTableModel()); // Inicializa con un modelo vacío
        CargarTablaProductos(); // Luego de inicializar la tabla, cargamos los datos
        
        getContentPane().setLayout(null);

        // Inicialización del JScrollPanel con la tabla
        jScrollPanel = new JScrollPane(jTable_producto);
        jScrollPanel.setAlignmentY(60.0f);
        jScrollPanel.setAlignmentX(10.0f);
        jScrollPanel.setBackground(SystemColor.menu);
        jScrollPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        jScrollPanel.setBounds(26, 44, 669, 256);
        getContentPane().add(jScrollPanel);

        // Inicialización de los botones
        jButton_actualizar = new JButton("Actualizar");
        jButton_actualizar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				Producto producto=new Producto();
				Ctrl_Producto controlProducto=new Ctrl_Producto();
				String iva="";
				String categoria="";
				iva=comboBox_iva.getSelectedItem().toString();
				categoria=comboBox_categoria.getSelectedItem().toString().trim();
				
				//validar campos
				if(txt_nombre.getText().isEmpty()||txt_cantidad.getText().isEmpty()||txt_precio.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Complete todos los campos");
					//txt_nombre.setBackground(Color.red); --igual con cantidad y precio
				}else {
					
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
									if(controlProducto.actualizar(producto, idProducto)) {
										JOptionPane.showMessageDialog(null, "Registro Actualizado");
										//se puede volver los colores a verde lol
										CargarComboCategoria();
										CargarTablaProductos();
										comboBox_iva.setSelectedItem("Seleccione iva:");
										Limpiar();
									}
									else {
										JOptionPane.showMessageDialog(null, "Error al Actualizar");
									}
								}catch(HeadlessException | NumberFormatException x) {
									System.out.println("Error en: "+x.getMessage());
								}
							}
						}
					
				}
			}
        	});
        
        jButton_actualizar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        jButton_actualizar.setBounds(747, 36, 100, 30);
        getContentPane().add(jButton_actualizar);

        jButton_eliminar = new JButton("Eliminar");
        jButton_eliminar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
    			Ctrl_Producto controlProducto=new Ctrl_Producto();
    			if(idProducto==0) {
    				JOptionPane.showMessageDialog(null, "Seleccione un producto.");
    			}else {
    				if(!controlProducto.eliminar(idProducto)){
            			JOptionPane.showMessageDialog(null, "Producto Eliminado");
            			CargarTablaProductos();
            			CargarComboCategoria();
            			Limpiar();
            		}else{
            			JOptionPane.showMessageDialog(null, "Error al Eliminar Categoria");
            		} 
    			}
        	}
        });
        jButton_eliminar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        jButton_eliminar.setBounds(747, 75, 100, 30);
        getContentPane().add(jButton_eliminar);


        // Definir el área de texto para descripción si lo necesitas
        txt_nombre = new JTextField();
        txt_nombre.setBounds(89, 317, 203, 30);
        getContentPane().add(txt_nombre);
        txt_nombre.setColumns(10);
        
        lblNewLabel = new JLabel("Nombre:");
        lblNewLabel.setBounds(26, 324, 76, 16);
        getContentPane().add(lblNewLabel);
        
        lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(26, 353, 76, 16);
        getContentPane().add(lblCantidad);
        
        lblDescripicion = new JLabel("Descripicion:");
        lblDescripicion.setBounds(26, 380, 94, 16);
        getContentPane().add(lblDescripicion);
        
        lblIva = new JLabel("IVA:");
        lblIva.setBounds(26, 404, 76, 16);
        getContentPane().add(lblIva);
        
        lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(245, 404, 76, 16);
        getContentPane().add(lblCategoria);
        
        txt_cantidad = new JTextField();
        txt_cantidad.setBounds(99, 348, 147, 26);
        getContentPane().add(txt_cantidad);
        txt_cantidad.setColumns(10);
        
        txt_descripcion = new JTextField();
        txt_descripcion.setBounds(116, 375, 180, 26);
        getContentPane().add(txt_descripcion);
        txt_descripcion.setColumns(10);
        
        comboBox_iva = new JComboBox();
        comboBox_iva.setModel(new DefaultComboBoxModel(new String[] {"Seleccione iva:", "No grava iva", "16%", "8%"}));
        comboBox_iva.setBounds(62, 400, 154, 27);
        getContentPane().add(comboBox_iva);
        
        comboBox_categoria = new JComboBox();
        comboBox_categoria.setModel(new DefaultComboBoxModel(new String[] {"Seleccione Categoria:", "1", "2", "3"}));
        comboBox_categoria.setBounds(322, 400, 192, 27);
        getContentPane().add(comboBox_categoria);
        
        lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(269, 353, 76, 16);
        getContentPane().add(lblPrecio);
        
        txt_precio = new JTextField();
        txt_precio.setColumns(10);
        txt_precio.setBounds(332, 348, 147, 26);
        getContentPane().add(txt_precio);
        CargarComboCategoria();
    }
    
////    //cargar las categorias
//    private void CargarComboCategoria() {
//    	Connection cn=Conexion.conectar();
//    	String sql="select * from tb_categoria";
//    	Statement st;
//    	try {
//    		st=cn.createStatement();
//    		ResultSet rs=st.executeQuery(sql);
//    		comboBox_categoria.removeAllItems();
//    		comboBox_categoria.addItem("Seleccione categoria:");
//    		while(rs.next()) {
//    			comboBox_categoria.addItem(rs.getString("descripcion"));
//    		}
//    		cn.close();
//    	}catch(SQLException e) {
//    		System.out.println("Error al cargar categorias");
//    	}
//    }
  //cargar productos registrados
    String descripcionCategoria="";
    double precio=0.0;
    int porcentajeIva=0;
    double IVA=0;
    private JLabel lblPrecio;
    private JTextField txt_precio;

    // Método para cargar los datos en la tabla
    public void CargarTablaProductos() {
        DefaultTableModel model = new DefaultTableModel();
        try (Connection cn = Conexion.conectar()) {
            String sql = "select p.idProducto, p.nombre, p.cantidad, p.precio, p.descripcion, p.porcentajeIva, c.descripcion, p.estado " +
                         "from tb_producto As p, tb_categoria As c " +
                         "where p.idCategoria = c.idCategoria";
            Statement stmt = cn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            model.addColumn("Nº");
            model.addColumn("nombre");
            model.addColumn("cantidad");
            model.addColumn("precio");
            model.addColumn("descripcion");
            model.addColumn("Iva");
            model.addColumn("Categoria");
            model.addColumn("estado");

            while (rs.next()) {
                double precio = rs.getDouble("precio");
                int porcentajeIva = rs.getInt("porcentajeIva");

                // Crear una fila con 8 columnas
                Object fila[] = new Object[8];
                fila[0] = rs.getObject("idProducto"); // Nº
                fila[1] = rs.getObject("nombre");    // Nombre
                fila[2] = rs.getObject("cantidad");  // Cantidad
                fila[3] = rs.getObject("precio");    // Precio
                fila[4] = rs.getObject("descripcion"); // Descripción
                fila[5] = calcularIva(precio, porcentajeIva); // IVA calculado
                fila[6] = rs.getObject("descripcion"); // Categoria
                fila[7] = rs.getObject("estado");     // Estado

                // Añadir la fila al modelo de la tabla
                model.addRow(fila);
            }
            
            // Asignar el modelo a la JTable (si tienes una JTable en tu UI)
            jTable_producto.setModel(model);

        } catch (SQLException e) {
            System.out.println("Error al rellenar tabla productos");
            e.printStackTrace();
        }
        jTable_producto.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		int fila_point=jTable_producto.rowAtPoint(e.getPoint());
        		int columna_point=0;
        		
        		if (fila_point > -1) {
        			idProducto=(int) model.getValueAt(fila_point, columna_point);
        			EnviarDatosProductoSeleccionada(idProducto);
        		}
        	}
        });
    }
    
private double calcularIva(double precio, int porcentajeIva) {
    // Calcular IVA y retornar el valor
    return precio * porcentajeIva / 100.0;
}
    
//    private double calcularIva(double precio, int iva) {
//    	int p_iva=iva;
//    	switch(p_iva) {
//    	case 0: 
//    		IVA= 0.0;
//    		break;
//    	case 16:
//    		IVA= precio * 0.16;
//    		break;
//    	case 8:
//    		IVA= precio * 0.08;
//    		break;
//    	default:
//    		break;
//    	}
//    	IVA=(double)Math.round(IVA*100)/100;
//    	return IVA;
//    }

        private void EnviarDatosProductoSeleccionada(int idProducto) {
        	try {
        		Connection con=Conexion.conectar();
        		PreparedStatement pst=con.prepareStatement("select * from tb_producto where idProducto = '"+ idProducto+"'");
        		ResultSet rs=pst.executeQuery();
        		
        		if(rs.next()) {
        			double precio = rs.getDouble("precio");
                    int porcentajeIva = rs.getInt("porcentajeIva");
                    
        			txt_nombre.setText(rs.getString("nombre"));
        			txt_cantidad.setText(rs.getString("cantidad"));
        			txt_precio.setText(rs.getString("precio"));
        			txt_descripcion.setText(rs.getString("descripcion"));
        			double iva=calcularIva(precio, porcentajeIva);
        			int idCate=rs.getInt("idCategoria");
        			comboBox_categoria.setSelectedItem(relacionarCategoria(idCate));

        		}
        		con.close();
        	}catch(SQLException e) {
        		System.out.println("Error al seleccionar producto "+ e);
        	}
    
        }
        
        private String relacionarCategoria(int idCategoria) {
        	String sql="select descripcion from tb_categoria where idCategoria = '" + idCategoria + "'";
        	Statement st;
        	try {
            	Connection cn=Conexion.conectar();
        		st=cn.createStatement();
        		ResultSet rs=st.executeQuery(sql);
        		while(rs.next()) {
        			descripcionCategoria=rs.getString("descripcion");
        		}
        		cn.close();
        	}catch(SQLException e) {
        		System.out.println("Error al obtener el id categoria");
        	}
        	return descripcionCategoria;
        }
     // Método para cargar las categorías desde la base de datos
        private void CargarComboCategoria() {
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
        
        private void Limpiar() {
        	txt_nombre.setText("");
        	txt_cantidad.setText("");
        	txt_precio.setText("");
        	txt_descripcion.setText("");
        	comboBox_iva.setSelectedItem("Seleccione iva:");
        	comboBox_categoria.setSelectedItem("Seleccione Categoria:");

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
    }


