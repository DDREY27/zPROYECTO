package Interfaz;

import java.awt.EventQueue;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import BaseDato.ConexionMySQL;

public class Registrarse extends JFrame {

    protected static final long serialVersionUID = 1L;
    protected JPanel contentPane;
    protected JTextField txtUsuario;
    protected JPasswordField txtContrasena;
    protected String tablaSeleccionada;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Crear una instancia de la ventana de registro y hacerla visible
                    Registrarse frame = new Registrarse();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Registrarse() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 460, 378);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
        setLocationRelativeTo(null); 
        contentPane.setLayout(null);

        // Caja de texto para el usuario
        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        txtUsuario.setText("Usuario");
        txtUsuario.setBounds(108, 92, 227, 34);
        txtUsuario.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (txtUsuario.getText().equals("Usuario")) {
                    txtUsuario.setText("");
                }
            }
            public void focusLost(FocusEvent e) {
                if (txtUsuario.getText().equals("")) {
                    txtUsuario.setText("Usuario");
                }
            }
        });
        contentPane.add(txtUsuario);

        // Campo de contraseña
        txtContrasena = new JPasswordField();
        txtContrasena.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtContrasena.setHorizontalAlignment(SwingConstants.CENTER);
        txtContrasena.setBounds(113, 150, 222, 34);
        txtContrasena.setText("Contraseña");
        txtContrasena.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(txtContrasena.getPassword()).equals("Contraseña")) {
                    txtContrasena.setText("");
                }
            }
            public void focusLost(FocusEvent e) {
                if (String.valueOf(txtContrasena.getPassword()).equals("")) {
                    txtContrasena.setText("Contraseña");
                }
            }
        });
        contentPane.add(txtContrasena);
        
        JLabel lblNewLabel = new JLabel("Registro de Usuario");
        lblNewLabel.setForeground(new Color(255, 128, 128));
        lblNewLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 30));
        lblNewLabel.setBounds(99, 10, 269, 56);
        contentPane.add(lblNewLabel);
        
        JButton btnNewButton = new JButton("Registrar");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String contrasena = String.valueOf(txtContrasena.getPassword());

                // Verifica que el usuario y la contraseña no estén vacíos
                if (usuario.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor ingresa usuario y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Crea una instancia de la clase de conexión a la base de datos
                ConexionMySQL conexion = new ConexionMySQL("freedb_usuario1", "YzKK@dKXqQ92dnR", "freedb_ProyectoBaseDatos");
                Connection conexionBD = null;

                try {
                    // Establece la conexión a la base de datos
                    conexionBD = conexion.conectar();

                    // Prepara la consulta SQL para insertar en la tabla "Credenciales"
                    String consulta = "INSERT INTO Credenciales (usuario, contrasena) VALUES (?, ?)";
                    PreparedStatement statement = conexionBD.prepareStatement(consulta);
                    statement.setString(1, usuario);
                    statement.setString(2, contrasena);

                    // Ejecuta la consulta
                    int filasAfectadas = statement.executeUpdate();

                    if (filasAfectadas > 0) {
                        JOptionPane.showMessageDialog(null, "Usuario registrado correctamente.", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al registrar usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    dispose();//Hacer que se cierre la pestaña
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    // Cierra la conexión a la base de datos
                    if (conexionBD != null) {
                        try {
                            conexionBD.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
                      
        btnNewButton.setBackground(new Color(255, 128, 128));
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnNewButton.setForeground(new Color(255, 255, 255));
        btnNewButton.setBounds(161, 231, 120, 34);
        contentPane.add(btnNewButton);
    }
}
