package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import BaseDato.ConexionMySQL;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtUsuario;
    private JPasswordField textField;
    private JLabel lblNewLabel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
            	// Crea una instancia de la ventana de inicio de sesión y la hace visible
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1163, 637);
        getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(128, 128, 128));
        panel.setBounds(0, 0, 535, 600);
        getContentPane().add(panel);

        //Imagen logo de veterinaria
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setEnabled(false);
        lblNewLabel_1.setIcon(new ImageIcon(Login.class.getResource("/Interfaz/LogoRegistro.png")));
        panel.add(lblNewLabel_1);

        JButton btnNewButton = new JButton("Acceder");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener la contraseña ingresada por el usuario desde el JPasswordField
                char[] inputPassword = textField.getPassword();
                String inputPasswordString = new String(inputPassword);
                String userName = txtUsuario.getText(); // Obtener el nombre de usuario ingresado

                // Cargar credenciales almacenadas en la base de datos
                String usuarioCorrecto = ""; // Usuario obtenido de la base de datos
                String contrasenaCorrecta = ""; // Contraseña obtenida de la base de datos
                
                // Crear una instancia de la clase de conexión a la base de datos
                ConexionMySQL conexion = new ConexionMySQL("freedb_usuario1", "YzKK@dKXqQ92dnR", "freedb_ProyectoBaseDatos");
                Connection conexionBD = null;
                try {
                    // Establecer la conexión a la base de datos
                    conexionBD = conexion.conectar();

                    // Preparar la consulta SQL para obtener el usuario y la contraseña
                    String consulta = "SELECT usuario, contrasena FROM Credenciales WHERE usuario = ?";
                    PreparedStatement statement = conexionBD.prepareStatement(consulta);
                    statement.setString(1, userName);

                    // Ejecutar la consulta
                    ResultSet rs = statement.executeQuery();

                    // Si se encontró un resultado, obtener las credenciales
                    if (rs.next()) {
                        usuarioCorrecto = rs.getString("usuario");
                        contrasenaCorrecta = rs.getString("contrasena");
                       
                    } 
                    
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    // Cerrar la conexión a la base de datos
                    if (conexionBD != null) {
                        try {
                            conexionBD.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                // Comparar la contraseña ingresada con la contraseña obtenida de la base de datos
                if (userName.equals(usuarioCorrecto) && inputPasswordString.equals(contrasenaCorrecta)) {
                    // Contraseña correcta, permitir el acceso
                    Principal nuevaPestanaPrincipal = new Principal();
                    nuevaPestanaPrincipal.setVisible(true);
                    dispose();
                } else {
                    // Contraseña incorrecta, mostrar un mensaje de error
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos. Por favor, inténtelo de nuevo.", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
                } 
            }
        });

        btnNewButton.setBackground(new Color(255, 128, 128));
        btnNewButton.setBounds(720, 402, 307, 47);
        getContentPane().add(btnNewButton);

        txtUsuario = new JTextField("Introduzca su usuario");
        txtUsuario.setToolTipText("Introduzca su usuario");
        txtUsuario.setFont(new Font("Tahoma", Font.PLAIN, 17));
        txtUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        txtUsuario.setBounds(720, 193, 307, 49);
        getContentPane().add(txtUsuario);
        txtUsuario.setColumns(10);

        // Agregar FocusListener para manejar el texto por defecto
        txtUsuario.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtUsuario.getText().equals("Introduzca su usuario")) {
                    txtUsuario.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtUsuario.getText().isEmpty()) {
                    txtUsuario.setText("Introduzca su usuario");
                }
            }
        });

        //Texto encima del inicio usuario.
        lblNewLabel = new JLabel("INICIAR SESION");
        lblNewLabel.setForeground(new Color(255, 128, 128));
        lblNewLabel.setFont(new Font("MS UI Gothic", Font.PLAIN, 39));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(586, 65, 563, 82);
        getContentPane().add(lblNewLabel);

        //Texto que sale cuando pones el raton encima del marco de contraseña.
        textField = new JPasswordField("Contraseña");
        textField.setToolTipText("Introduzca su contraseña");
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        textField.setColumns(10);
        textField.setBounds(720, 296, 312, 49);
        getContentPane().add(textField);
        
        JButton BotonRegistro = new JButton("Registrarse");
        BotonRegistro.setBackground(new Color(255, 255, 255));
        BotonRegistro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == BotonRegistro) {
                	// Abrir la ventana de registro cuando se hace click en el botón "Registrarse"
                    Registrarse Registrarse = new Registrarse(); // Crear una instancia del JFrame "Principal"
                    Registrarse.setVisible(true); // Hacer visible el JFrame "Principal"
                }
                
            }
        });
        BotonRegistro.setBounds(795, 503, 150, 34);
        getContentPane().add(BotonRegistro);

        // Agregar FocusListener para manejar el texto por defecto
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String password = new String(textField.getPassword());
                if (password.equals("Contraseña")) {
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String password = new String(textField.getPassword());
                if (password.isEmpty()) {
                    textField.setText(""); //Texto que se pondria si la contraseña se queda vacia
                }
            }

        });
    }
}
