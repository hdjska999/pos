package vista;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import conexion.Conexion;
import controlador.Ctrl_RegistrarVenta;
import modelo.CabeceraVenta;
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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class VistaGestionarVentas extends JInternalFrame {
    
    private int idCliente=0;
    private int idVenta;

    public static JScrollPane jScrollPanel;
    public static JTable jTable_producto;
    private JTextField txt_totalpagar;

    // Declaración de los botones
    private JButton jButton_actualizar;
    private JLabel lblNewLabel;
    private JLabel lblCantidad;
    private JLabel lblDescripicion;
    private JLabel lblIva;
    private JTextField txt_fecha;
    JComboBox comboBox_cliente;
    JComboBox comboBox_estado;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VistaGestionarVentas frame = new VistaGestionarVentas();
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
    public VistaGestionarVentas() {
        setIconifiable(true);
        setClosable(true);
        setSize(new Dimension(900, 500));
        setTitle("Gestionar Ventas");
        
        // Inicializamos la tabla antes de usarla
        jTable_producto = new JTable();
        //jTable_categoria.setModel(new DefaultTableModel()); // Inicializa con un modelo vacío
        
        
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
			CabeceraVenta cabeceraVenta= new CabeceraVenta();
			Ctrl_RegistrarVenta controlRegistrarVenta=new Ctrl_RegistrarVenta();
			String cliente, estado;
			cliente=comboBox_cliente.getSelectedItem().toString().trim();
			estado=comboBox_estado.getSelectedItem().toString().trim();
			
			//obtener el id del cliente
			try {
				Connection cn=Conexion.conectar();
				PreparedStatement pst=cn.prepareStatement("select idCliente, concat(nombre, ' ', apellido) as cliente from tb_cliente where concat(nombre, ' ', apellido) = '"+cliente+"'");
				ResultSet rs=pst.executeQuery();
				if(rs.next()) {
					idCliente=rs.getInt("idCliente");
				}
				cn.close();
			}catch(SQLException x) {
				System.out.println("Error al cargar el id del cliente "+x);
			}
			
			//actualizar datos
			if(!cliente.equalsIgnoreCase("Seleccione cliente:")) {
				cabeceraVenta.setIdCliente(idCliente);
				if(estado.equalsIgnoreCase("Activo")) {
					cabeceraVenta.setEstado(0);
				}else {
					cabeceraVenta.setEstado(0);
				}
				
				if(controlRegistrarVenta.actualizar(cabeceraVenta, idVenta)) {
					JOptionPane.showMessageDialog(null, "Registro actualizado");
					CargarTablaVentas();
					Limpiar();
				}else {
					JOptionPane.showMessageDialog(null, "Error al actualizar");
				}
			}else {
				JOptionPane.showMessageDialog(null, "Seleccione un registro para actualizar datos");
			}
			}
        	});
        
        jButton_actualizar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        jButton_actualizar.setBounds(747, 36, 100, 30);
        getContentPane().add(jButton_actualizar);


        // Definir el área de texto para descripción si lo necesitas
        txt_totalpagar = new JTextField();
        txt_totalpagar.setEnabled(false);
        txt_totalpagar.setBounds(107, 317, 203, 30);
        getContentPane().add(txt_totalpagar);
        txt_totalpagar.setColumns(10);
        
        lblNewLabel = new JLabel("Total Pagar:");
        lblNewLabel.setBounds(26, 324, 76, 16);
        getContentPane().add(lblNewLabel);
        
        lblCantidad = new JLabel("Fecha:");
        lblCantidad.setBounds(26, 353, 76, 16);
        getContentPane().add(lblCantidad);
        
        lblDescripicion = new JLabel("Cliente:");
        lblDescripicion.setBounds(26, 380, 94, 16);
        getContentPane().add(lblDescripicion);
        
        lblIva = new JLabel("Estado:");
        lblIva.setBounds(26, 404, 76, 16);
        getContentPane().add(lblIva);
        
        txt_fecha = new JTextField();
        txt_fecha.setEnabled(false);
        txt_fecha.setBounds(89, 348, 147, 26);
        getContentPane().add(txt_fecha);
        txt_fecha.setColumns(10);
        
        comboBox_cliente = new JComboBox();
        comboBox_cliente.setModel(new DefaultComboBoxModel(new String[] {"Seleccione cliente:", "1", "2"}));
        comboBox_cliente.setBounds(89, 376, 178, 27);
        getContentPane().add(comboBox_cliente);
        
        comboBox_estado = new JComboBox();
        comboBox_estado.setModel(new DefaultComboBoxModel(new String[] {"Activo", "Inactivo"}));
        comboBox_estado.setBounds(89, 400, 178, 27);
        getContentPane().add(comboBox_estado);
        CargarComboClientes();
        CargarTablaVentas(); // Luego de inicializar la tabla, cargamos los datos
        
    }
    

    // Método para cargar los datos en la tabla
    public void CargarTablaVentas() {
        DefaultTableModel model = new DefaultTableModel();
        try (Connection cn = Conexion.conectar()) {
            String sql = "select cv.idCabeceraVenta as id, concat(c.nombre, ' ', c.apellido) as cliente, cv.valorPagar as total, cv.fechaVenta as fecha, cv.estado from tb_cabecera_venta as cv, tb_cliente as c where cv.idCliente = c.idCliente";
            Statement stmt = cn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            model.addColumn("Nº");
            model.addColumn("Cliente");
            model.addColumn("Total Pagar");
            model.addColumn("Fecha Venta");
            model.addColumn("estado");

            while (rs.next()) {
                
                // Crear una fila con 8 columnas
                Object fila[] = new Object[5];
                fila[0] = rs.getObject("id"); // Nº
                fila[1] = rs.getObject("cliente");    // Nombre
                fila[2] = rs.getObject("total");  // Cantidad
                fila[3] = rs.getObject("fecha");    // fecha
                fila[4] = rs.getObject("estado");     // Estado

                String estado=String.valueOf(rs.getObject("estado"));
                if(estado.equalsIgnoreCase("1")) {
                	fila[4]="Activo";
                }else {
                	fila[4]="Inactivo";
                }
                
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
        			idVenta=(int) model.getValueAt(fila_point, columna_point);
        			EnviarDatosVentasSeleccionada(idVenta);
        		}
        	}
        });
    }
    
        private void EnviarDatosVentasSeleccionada(int idVenta) {
        	try {
        		Connection con=Conexion.conectar();
        		PreparedStatement pst=con.prepareStatement("select cv.idCabeceraVenta, cv.idCliente, concat(c.nombre, ' ', c.apellido) as cliente, cv.valorPagar, cv.fechaVenta, cv.estado "
        				+ "from tb_cabecera_venta as cv, tb_cliente as c where cv.idCabeceraVenta = '"+idVenta+"' and cv.idCliente = c.idCliente");
        		ResultSet rs=pst.executeQuery();
        		
        		if(rs.next()) {
        			comboBox_cliente.setSelectedItem(rs.getString("cliente"));
        			txt_totalpagar.setText(rs.getString("valorPagar"));
        			txt_fecha.setText(rs.getString("fechaVenta"));
        			int estado=rs.getInt("estado");
        			if(estado==1) {
        				comboBox_estado.setSelectedItem("Activo");
        			}else {
        				comboBox_estado.setSelectedItem("Inactivo");
        			}

        		}
        		con.close();
        	}catch(SQLException e) {
        		System.out.println("Error al seleccionar venta: "+ e);
        	}
    
        }
        
     
        private void Limpiar() {
        	this.txt_totalpagar.setText("");
        	this.txt_fecha.setText("");
        	this.comboBox_cliente.setSelectedItem("Seleccione cliente:");
        	this.comboBox_estado.setSelectedItem("Activo");
        	idCliente=0;
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
    }


