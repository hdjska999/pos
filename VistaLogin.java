package vista;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import controlador.Ctrl_Usuario;
import modelo.Usuario;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;

public class VistaLogin extends JFrame {
	private TextField txt_usuario;
	private JPasswordField txt_password;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaLogin frame = new VistaLogin();
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
	@Override
	public Image getIconImage() {
		Image retValue=Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("img/ventas.png"));
		return retValue;
	}
	
	public VistaLogin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VistaLogin.class.getResource("/img/ventas.png")));
		        
		//initComponents();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Login - PUNTO DE VENTA");
		this.setSize(new Dimension(700, 500));
		getContentPane().setLayout(null);
		
		JPanel jPanel1 = new JPanel();
		jPanel1.setBackground(UIManager.getColor("Desktop.background"));
		jPanel1.setBounds(0, 0, 363, 482);
		getContentPane().add(jPanel1);
		jPanel1.setLayout(null);
		
		JLabel Label2 = new JLabel("");
		Label2.setBounds(0, 0, 764, 622);
		jPanel1.add(Label2);
		Label2.setIcon(new ImageIcon(VistaLogin.class.getResource("/img/wallpaperVenom.jpg")));
		Label2.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel jPanel2 = new JPanel();
		jPanel2.setBackground(UIManager.getColor("InternalFrame.inactiveTitleBackground"));
		jPanel2.setBounds(361, 0, 339, 472);
		getContentPane().add(jPanel2);
		jPanel2.setLayout(null);
		
		JLabel Label5 = new JLabel("user:");
		Label5.setHorizontalAlignment(SwingConstants.CENTER);
		Label5.setBounds(31, 260, 91, 16);
		jPanel2.add(Label5);
		
		txt_usuario = new TextField();
		txt_usuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()== e.VK_ENTER) {
					txt_password.requestFocus();
				}
			}
		});
		txt_usuario.setFont(new Font("Devanagari Sangam MN", Font.BOLD, 13));
		txt_usuario.setBounds(131, 260, 124, 22);
		jPanel2.add(txt_usuario);
		txt_usuario.setBackground(UIManager.getColor("Table.background"));
		
		JLabel Label4 = new JLabel("");
		Label4.setFont(new Font("Devanagari Sangam MN", Font.BOLD, 13));
		Label4.setBounds(0, -85, 341, 472);
		jPanel2.add(Label4);
		Label4.setIcon(new ImageIcon(VistaLogin.class.getResource("/img/user1.png")));
		Label4.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel Label6 = new JLabel("password:");
		Label6.setHorizontalAlignment(SwingConstants.CENTER);
		Label6.setBounds(31, 297, 106, 16);
		jPanel2.add(Label6);
		
		txt_password = new JPasswordField();
		txt_password.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()== e.VK_ENTER) {
	            	VistaLogin.this.Login();
				}
			}
		});
		txt_password.setBounds(131, 292, 129, 26);
		jPanel2.add(txt_password);
		
		JButton jButton_IniciarSesion = new JButton("Login");
		jButton_IniciarSesion.setForeground(UIManager.getColor("InternalFrame.borderShadow"));
		jButton_IniciarSesion.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		jButton_IniciarSesion.setBounds(122, 357, 117, 29);
		jPanel2.add(jButton_IniciarSesion);
		
		JLabel Label3 = new JLabel("©SoFIGang");
		Label3.setBounds(0, 416, 341, 16);
		jPanel2.add(Label3);
		Label3.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel Label1 = new JLabel("Punto de Venta");
		Label1.setBounds(0, 22, 341, 16);
		jPanel2.add(Label1);
		Label1.setForeground(new Color(0, 0, 0));
		Label1.setFont(new Font("Lao MN", Font.BOLD, 16));
		Label1.setHorizontalAlignment(SwingConstants.CENTER);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 606, 348);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//
//		setContentPane(contentPane);
//		contentPane.setLayout(null);
		
		jButton_IniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción que ocurre cuando se hace clic en el botón
            	VistaLogin.this.Login();
            }
        });
		
	}
	
private void Login() {
	if(!txt_usuario.getText().isEmpty() && !txt_password.getText().isEmpty()) {
		
		Ctrl_Usuario controlUsuario=new Ctrl_Usuario();
		Usuario usuario=new Usuario();
		usuario.setUsuario(txt_usuario.getText().trim());
		usuario.setPassword(txt_password.getText().trim());
		if(controlUsuario.loginUser(usuario)) {
			//JOptionPane.showMessageDialog(null, "Login Correcto...");
			VistaMenu menu= new VistaMenu();
			menu.setVisible(true);
			this.dispose();

		}else {
			JOptionPane.showMessageDialog(null, "Usuario o Clave incorrectos");
		}
	}else {
		JOptionPane.showMessageDialog(null, "Ingrese los campos");
	}
}

}

	