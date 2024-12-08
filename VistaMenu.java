package vista;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import controlador.Reportes;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class VistaMenu extends JFrame {
	
	public static JDesktopPane jDesktopPane_menu;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaMenu frame = new VistaMenu();
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
	public VistaMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 700);
		setExtendedState(this.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setTitle("Punto de Venta");
		
		getContentPane().setLayout(null);
		jDesktopPane_menu=new JDesktopPane();
		int ancho=Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto=Toolkit.getDefaultToolkit().getScreenSize().height;
		
		jDesktopPane_menu.setBounds(0, 0, ancho, (alto-110));
		getContentPane().add(jDesktopPane_menu);
		
		JLabel wallpaper = new JLabel("");
		wallpaper.setBounds(0, 0, 1920, 1200);
		jDesktopPane_menu.add(wallpaper);
		wallpaper.setIcon(new ImageIcon(VistaMenu.class.getResource("/img/wallpaper.jpg")));
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Usuario");
		mnNewMenu.setFont(new Font("Skia", Font.PLAIN, 16));
		mnNewMenu.setIcon(new ImageIcon(VistaMenu.class.getResource("/img/usuario.png")));
		menuBar.add(mnNewMenu);
		
		JMenuItem jMenuItem_nuevoUsuario = new JMenuItem("Nuevo Usuario");
		jMenuItem_nuevoUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaUsuario vistaUsuario=new VistaUsuario();
				jDesktopPane_menu.add(vistaUsuario);
				vistaUsuario.setVisible(true);
			}
		});
		mnNewMenu.add(jMenuItem_nuevoUsuario);
		
		JMenuItem jMenuItem_gestionarUsuarios = new JMenuItem("Gestionar Usuarios");
		jMenuItem_gestionarUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaGestionarUsuario vistaGestionarUsuario=new VistaGestionarUsuario();
				jDesktopPane_menu.add(vistaGestionarUsuario);
				vistaGestionarUsuario.setVisible(true);
			}
		});
		mnNewMenu.add(jMenuItem_gestionarUsuarios);
		
		JMenu mnNewMenu_1 = new JMenu("Producto");
		mnNewMenu_1.setFont(new Font("Skia", Font.PLAIN, 16));
		mnNewMenu_1.setIcon(new ImageIcon(VistaMenu.class.getResource("/img/producto.png")));
		menuBar.add(mnNewMenu_1);
		
		JMenuItem jMenuItem_nuevoProducto = new JMenuItem("Nuevo Producto");
		jMenuItem_nuevoProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaProducto vistaProducto=new VistaProducto();
				jDesktopPane_menu.add(vistaProducto);
				vistaProducto.setVisible(true);
			}
		});
		jMenuItem_nuevoProducto.setFont(new Font("Verdana", Font.PLAIN, 14));
		mnNewMenu_1.add(jMenuItem_nuevoProducto);
		
		JMenuItem jMenuItem_gestionarProductos = new JMenuItem("Gestionar Productos");
		jMenuItem_gestionarProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaGestionarProducto vistaGestionarProducto=new VistaGestionarProducto();
				jDesktopPane_menu.add(vistaGestionarProducto);
				vistaGestionarProducto.setVisible(true);
			}
			
		});
		jMenuItem_gestionarProductos.setFont(new Font("Verdana", Font.PLAIN, 14));
		mnNewMenu_1.add(jMenuItem_gestionarProductos);
		
		JMenuItem jMenuItem_actualizarStock = new JMenuItem("Actualizar Stock");
		jMenuItem_actualizarStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaActualizarStock vistaActualizarStock=new VistaActualizarStock();
				jDesktopPane_menu.add(vistaActualizarStock);
				vistaActualizarStock.setVisible(true);
			}
		});
		jMenuItem_actualizarStock.setFont(new Font("Verdana", Font.PLAIN, 14));
		mnNewMenu_1.add(jMenuItem_actualizarStock);
		
		JMenu mnNewMenu_2 = new JMenu("Cliente");
		mnNewMenu_2.setFont(new Font("Skia", Font.PLAIN, 16));
		mnNewMenu_2.setIcon(new ImageIcon(VistaMenu.class.getResource("/img/cliente.png")));
		menuBar.add(mnNewMenu_2);
		
		JMenuItem jMenuItem_nuevoCliente = new JMenuItem("Nuevo Cliente");
		jMenuItem_nuevoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaCliente vistaCliente=new VistaCliente();
				jDesktopPane_menu.add(vistaCliente);
				vistaCliente.setVisible(true);
			}
		});
		mnNewMenu_2.add(jMenuItem_nuevoCliente);
		
		JMenuItem jMenuItem_gestionarClientes = new JMenuItem("Gestionar Clientes");
		jMenuItem_gestionarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaGestionarCliente vistaGestionarCliente=new VistaGestionarCliente();
				jDesktopPane_menu.add(vistaGestionarCliente);
				vistaGestionarCliente.setVisible(true);
			}
		});
		mnNewMenu_2.add(jMenuItem_gestionarClientes);
		
		JMenu mnNewMenu_3 = new JMenu("Categoria");
		mnNewMenu_3.setFont(new Font("Skia", Font.PLAIN, 16));
		mnNewMenu_3.setIcon(new ImageIcon(VistaMenu.class.getResource("/img/categorias.png")));
		menuBar.add(mnNewMenu_3);
		
		JMenuItem jMenuItem_nuevaCategoria = new JMenuItem("Nueva Categoria");
		jMenuItem_nuevaCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaCategoria vistaCategoria=new VistaCategoria();
				jDesktopPane_menu.add(vistaCategoria);
				vistaCategoria.setVisible(true);
			}
		});
		mnNewMenu_3.add(jMenuItem_nuevaCategoria);
		
		JMenuItem jMenuItem_gestionarCategorias = new JMenuItem("Gestionar Categorias");
		jMenuItem_gestionarCategorias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaGestionarCategoria vistaGestionarCategoria=new VistaGestionarCategoria();
				jDesktopPane_menu.add(vistaGestionarCategoria);
				vistaGestionarCategoria.setVisible(true);
			}
		});
		mnNewMenu_3.add(jMenuItem_gestionarCategorias);
		
		JMenu mnNewMenu_4 = new JMenu("Facturar");
		mnNewMenu_4.setFont(new Font("Skia", Font.PLAIN, 16));
		mnNewMenu_4.setIcon(new ImageIcon(VistaMenu.class.getResource("/img/carrito.png")));
		menuBar.add(mnNewMenu_4);
		
		JMenuItem jMenuItem_nuevaVenta = new JMenuItem("Nueva Venta");
		jMenuItem_nuevaVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaFacturacion vistaFacturacion=new VistaFacturacion();
				jDesktopPane_menu.add(vistaFacturacion);
				vistaFacturacion.setVisible(true);
			}
		});
		mnNewMenu_4.add(jMenuItem_nuevaVenta);
		
		JMenuItem jMenuItem_gestionarVentas = new JMenuItem("Gestionar Ventas");
		jMenuItem_gestionarVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VistaGestionarVentas vistaGestionarVentas=new VistaGestionarVentas();
				jDesktopPane_menu.add(vistaGestionarVentas);
				vistaGestionarVentas.setVisible(true);
			}
		});
		mnNewMenu_4.add(jMenuItem_gestionarVentas);
		
		JMenu mnNewMenu_5 = new JMenu("Reportes");
		mnNewMenu_5.setFont(new Font("Skia", Font.PLAIN, 16));
		mnNewMenu_5.setIcon(new ImageIcon(VistaMenu.class.getResource("/img/reportes.png")));
		menuBar.add(mnNewMenu_5);
		
		JMenuItem jMenuItem_reportesClientes = new JMenuItem("Reportes Clientes");
		jMenuItem_reportesClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Reportes reporte=new Reportes();
				reporte.ReporteClientes();
			}
		});
		mnNewMenu_5.add(jMenuItem_reportesClientes);
		
		JMenuItem jMenuItem_reportesCategorias = new JMenuItem("Reportes Categorias");
		jMenuItem_reportesCategorias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Reportes reporte=new Reportes();
				reporte.ReporteCategorias();
			}
		});
		mnNewMenu_5.add(jMenuItem_reportesCategorias);
		
		JMenuItem jMenuItem_reportesProductos = new JMenuItem("Reportes Productos");
		jMenuItem_reportesProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Reportes reporte=new Reportes();
				reporte.ReporteProductos();
			}
		});
		mnNewMenu_5.add(jMenuItem_reportesProductos);
		
		JMenuItem jMenuItem_reportesVentas = new JMenuItem("Reportes Ventas");
		jMenuItem_reportesVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Reportes reporte=new Reportes();
				reporte.ReporteVentas();
			}
		});
		mnNewMenu_5.add(jMenuItem_reportesVentas);
		
		JMenu mnNewMenu_7 = new JMenu("Cerrar Sesion");
		mnNewMenu_7.setFont(new Font("Skia", Font.PLAIN, 16));
		mnNewMenu_7.setIcon(new ImageIcon(VistaMenu.class.getResource("/img/cerrar-sesion.png")));
		menuBar.add(mnNewMenu_7);
		
		JMenuItem jMenuItem_cerrarSesion = new JMenuItem("Cerrar Sesion");
		jMenuItem_cerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu_7.add(jMenuItem_cerrarSesion);
		
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//
//		setContentPane(contentPane);
//		contentPane.setLayout(null);
	}
}
