package vista;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import conexion.Conexion;
import controlador.Ctrl_Cliente;
import modelo.Cliente;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VistaGestionarCliente extends JInternalFrame {
    
    private int idCliente;

    public static JScrollPane jScrollPanel;
    public static JTable jTable_producto;
    private JTextField txt_nombre;
    private JLabel lblPrecio;
    private JTextField txt_cedula;
    private JTextField txt_direccion;

    // Declaración de los botones
    private JButton jButton_actualizar;
    private JButton jButton_eliminar;
    private JLabel lblNewLabel;
    private JLabel lblCantidad;
    private JLabel lblDescripicion;
    private JLabel lblIva;
    private JTextField txt_apellido;
    private JTextField txt_telefono;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VistaGestionarCliente frame = new VistaGestionarCliente();
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
    public VistaGestionarCliente() {
        setIconifiable(true);
        setClosable(true);
        setSize(new Dimension(900, 500));
        setTitle("Gestionar Clientes");
        
        // Inicializamos la tabla antes de usarla
        jTable_producto = new JTable();
        //jTable_categoria.setModel(new DefaultTableModel()); // Inicializa con un modelo vacío
        CargarTablaClientes(); // Luego de inicializar la tabla, cargamos los datos
        
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
				if(txt_nombre.getText().isEmpty()&&txt_apellido.getText().isEmpty()&&txt_cedula.getText().isEmpty()&&txt_telefono.getText().isEmpty()&&txt_direccion.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "completa todos los campos");
				}else {
					Cliente cliente=new Cliente();
					Ctrl_Cliente controlCliente=new Ctrl_Cliente();
					
					cliente.setNombre(txt_nombre.getText().trim());
					cliente.setApellido(txt_apellido.getText().trim());
					cliente.setCedula(txt_cedula.getText().trim());
					cliente.setTelefono(txt_telefono.getText().trim());
					cliente.setDireccion(txt_direccion.getText().trim());
					
					if(controlCliente.actualizar(cliente, idCliente)) {
						JOptionPane.showMessageDialog(null, "Datos del cliente actualizados");
						CargarTablaClientes();
						Limpiar();
					}else {
						JOptionPane.showMessageDialog(null, "Error al actualizar");
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
        		Ctrl_Cliente controlCliente=new Ctrl_Cliente();
        		if(idCliente==0) {
					JOptionPane.showMessageDialog(null, "Seleccione un cliente");

        		}else {
					if(!controlCliente.eliminar(idCliente)) {
						JOptionPane.showMessageDialog(null, "Cliente Eliminado");
						CargarTablaClientes();
						Limpiar();
					}else {
						JOptionPane.showMessageDialog(null, "Error al eliminar cliente");
						Limpiar();
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
        
        lblCantidad = new JLabel("Apellido:");
        lblCantidad.setBounds(26, 353, 76, 16);
        getContentPane().add(lblCantidad);
        
        lblDescripicion = new JLabel("Telefono:");
        lblDescripicion.setBounds(26, 380, 94, 16);
        getContentPane().add(lblDescripicion);
        
        lblIva = new JLabel("Direccion:");
        lblIva.setBounds(26, 404, 76, 16);
        getContentPane().add(lblIva);
        
        txt_apellido = new JTextField();
        txt_apellido.setBounds(89, 348, 147, 26);
        getContentPane().add(txt_apellido);
        txt_apellido.setColumns(10);
        
        txt_telefono = new JTextField();
        txt_telefono.setBounds(89, 375, 180, 26);
        getContentPane().add(txt_telefono);
        txt_telefono.setColumns(10);
        
        lblPrecio = new JLabel("Cedula:");
        lblPrecio.setBounds(269, 353, 76, 16);
        getContentPane().add(lblPrecio);
        
        txt_cedula = new JTextField();
        txt_cedula.setColumns(10);
        txt_cedula.setBounds(325, 348, 147, 26);
        getContentPane().add(txt_cedula);
        
        txt_direccion = new JTextField();
        txt_direccion.setColumns(10);
        txt_direccion.setBounds(99, 399, 180, 26);
        getContentPane().add(txt_direccion);
        
    }
    

    // Método para cargar los datos en la tabla
    public void CargarTablaClientes() {
        DefaultTableModel model = new DefaultTableModel();
        try (Connection cn = Conexion.conectar()) {
            String sql = "select * from tb_cliente";
            Statement stmt = cn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            model.addColumn("Nº");
            model.addColumn("nombre");
            model.addColumn("apellido");
            model.addColumn("cedula");
            model.addColumn("telefono");
            model.addColumn("direccion");
            model.addColumn("estado");

            while (rs.next()) {
                
                // Crear una fila con 8 columnas
                Object fila[] = new Object[7];
                fila[0] = rs.getObject("idCliente"); // Nº
                fila[1] = rs.getObject("nombre");    // Nombre
                fila[2] = rs.getObject("apellido");  // Cantidad
                fila[3] = rs.getObject("cedula");    // Precio
                fila[4] = rs.getObject("telefono"); // Descripción
                fila[5] = rs.getObject("direccion"); // Categoria
                fila[6] = rs.getObject("estado");     // Estado

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
        			idCliente=(int) model.getValueAt(fila_point, columna_point);
        			EnviarDatosClientesSeleccionada(idCliente);
        		}
        	}
        });
    }
    
        private void EnviarDatosClientesSeleccionada(int idCliente) {
        	try {
        		Connection con=Conexion.conectar();
        		PreparedStatement pst=con.prepareStatement("select * from tb_cliente where idCliente = '"+ idCliente+"'");
        		ResultSet rs=pst.executeQuery();
        		
        		if(rs.next()) {
                    
        			txt_nombre.setText(rs.getString("nombre"));
        			txt_apellido.setText(rs.getString("apellido"));
        			txt_cedula.setText(rs.getString("cedula"));
        			txt_telefono.setText(rs.getString("telefono"));
        			txt_direccion.setText(rs.getString("direccion"));

        		}
        		con.close();
        	}catch(SQLException e) {
        		System.out.println("Error al seleccionar cliente: "+ e);
        	}
    
        }
        
     
        private void Limpiar() {
        	txt_nombre.setText("");
        	txt_apellido.setText("");
        	txt_cedula.setText("");
        	txt_telefono.setText("");
        	txt_direccion.setText("");

        }
    }


