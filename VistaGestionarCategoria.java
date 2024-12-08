package vista;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import conexion.Conexion;
import controlador.Ctrl_Categoria;
import modelo.Categoria;

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

public class VistaGestionarCategoria extends JInternalFrame {
    
    private int idCategoria;
    public static JScrollPane jScrollPanel;
    public static JTable jTable_categoria;
    private JTextField txt_descripcion;

    // Declaración de los botones
    private JButton jButton_actualizar;
    private JButton jButton_eliminar;
    private JLabel lblNewLabel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VistaGestionarCategoria frame = new VistaGestionarCategoria();
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
    public VistaGestionarCategoria() {
        setIconifiable(true);
        setClosable(true);
        setSize(new Dimension(600, 400));
        setTitle("Gestionar Categorias");
        
        // Inicializamos la tabla antes de usarla
        jTable_categoria = new JTable();
        jTable_categoria.setModel(new DefaultTableModel()); // Inicializa con un modelo vacío
        CargarTablaCategorias(); // Luego de inicializar la tabla, cargamos los datos

        getContentPane().setLayout(null);

        // Inicialización del JScrollPanel con la tabla
        jScrollPanel = new JScrollPane(jTable_categoria);
        jScrollPanel.setAlignmentY(60.0f);
        jScrollPanel.setAlignmentX(10.0f);
        jScrollPanel.setBackground(SystemColor.menu);
        jScrollPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        jScrollPanel.setBounds(10, 60, 560, 240);
        getContentPane().add(jScrollPanel);

        // Inicialización de los botones
        jButton_actualizar = new JButton("Actualizar");
        jButton_actualizar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(!txt_descripcion.getText().isEmpty()) {
        			Categoria categoria=new Categoria();
        			Ctrl_Categoria controlCategoria=new Ctrl_Categoria();
        			
        			categoria.setDescripcion(txt_descripcion.getText().trim());
        		if(controlCategoria.actualizar(categoria, idCategoria)){
        			JOptionPane.showMessageDialog(null, "Categoria Actualizada");
        			txt_descripcion.setText("");
        			CargarTablaCategorias();
        		}else{
        			JOptionPane.showMessageDialog(null, "Error al Actualizar Categoria");
        		}
        		} else{
        			JOptionPane.showMessageDialog(null, "Seleccione una categoria");
        		}
        	}});
        
        jButton_actualizar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        jButton_actualizar.setBounds(10, 310, 100, 30);
        getContentPane().add(jButton_actualizar);

        jButton_eliminar = new JButton("Eliminar");
        jButton_eliminar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(!txt_descripcion.getText().isEmpty()) {
        			Categoria categoria=new Categoria();
        			Ctrl_Categoria controlCategoria=new Ctrl_Categoria();
        			
        			categoria.setDescripcion(txt_descripcion.getText().trim());
        		if(controlCategoria.eliminar(idCategoria)){
        			JOptionPane.showMessageDialog(null, "Categoria Eliminada");
        			txt_descripcion.setText("");
        			CargarTablaCategorias();
        		}else{
        			JOptionPane.showMessageDialog(null, "Error al Eliminar Categoria");
        		} 
        		}else {
        			JOptionPane.showMessageDialog(null, "Seleccione una categoria");
        		}
        	}
        });
        jButton_eliminar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        jButton_eliminar.setBounds(120, 310, 100, 30);
        getContentPane().add(jButton_eliminar);

        // Aquí puedes agregar las acciones de los botones, si las tienes

        // Definir el área de texto para descripción si lo necesitas
        txt_descripcion = new JTextField();
        txt_descripcion.setBounds(350, 310, 220, 30);
        getContentPane().add(txt_descripcion);
        txt_descripcion.setColumns(10);
        
        lblNewLabel = new JLabel("Descripcion:");
        lblNewLabel.setBounds(252, 316, 86, 16);
        getContentPane().add(lblNewLabel);
    }

    // Método para cargar los datos en la tabla
    public void CargarTablaCategorias() {
        //System.out.println("Cargando tabla...");
        
        DefaultTableModel model = (DefaultTableModel) jTable_categoria.getModel();
        model.setColumnIdentifiers(new Object[]{"ID", "Descripción", "Estado"});  // Ajusta según tu base de datos

        try (Connection cn = Conexion.conectar()) {
            String sql = "SELECT * FROM tb_categoria";
            Statement stmt = cn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("idCategoria"),
                    rs.getString("descripcion"),
                    rs.getInt("estado")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        jTable_categoria.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		int fila_point=jTable_categoria.rowAtPoint(e.getPoint());
        		int columna_point=0;
        		
        		if (fila_point > -1) {
        			idCategoria=(int) model.getValueAt(fila_point, columna_point);
        			EnviarDatosCategoriaSeleccionada(idCategoria);
        		}
        	}
        });
    }
        private void EnviarDatosCategoriaSeleccionada(int idCategoria) {
        	try {
        		Connection con=Conexion.conectar();
        		PreparedStatement pst=con.prepareStatement("select * from tb_categoria where idCategoria = '"+ idCategoria + "'");
        		ResultSet rs=pst.executeQuery();
        		
        		if(rs.next()) {
        			txt_descripcion.setText(rs.getString("descripcion"));
        		}
        		con.close();
        	}catch(SQLException e) {
        		System.out.println("Error al seleccionar categoria "+ e);
        	}
    
        }
    }

