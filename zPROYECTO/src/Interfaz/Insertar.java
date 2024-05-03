package Interfaz;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import BaseDato.ConexionMySQL;

//Clase para la ventana de inserción de datos
public class Insertar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField[] textFields;
	private String tableName;
	private ConexionMySQL conexion;

	// Método principal para ejecutar la ventana de inserción de datos
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Insertar frame = new Insertar("Nombre_Tabla_Aqui");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Constructor de la clase Insertar
	public Insertar(String nombreTabla) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 345, 340);
		setTitle("Agregar registros");

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.tableName = nombreTabla;

		// Campo de texto para mostrar el nombre de la tabla
		JTextField textFieldTabla = new JTextField();
		textFieldTabla.setBackground(new Color(245, 255, 250));
		textFieldTabla.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldTabla.setEditable(false); // Hace que el campo de texto sea inmodificable
		textFieldTabla.setFont(new Font("Tahoma", Font.BOLD, 16));
		textFieldTabla.setBounds(94, 11, 142, 22);
		textFieldTabla.setText(nombreTabla); // Establece el nombre de la tabla como texto en el campo
		contentPane.add(textFieldTabla);

		obtenerColumnasYCrearCampos();

		// Botón para agregar los datos ingresados
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					agregarDatos();
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error al agregar datos: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAgregar.setBounds(10, 265, 309, 30);
		btnAgregar.setHorizontalTextPosition(SwingConstants.CENTER);
		contentPane.add(btnAgregar, BorderLayout.SOUTH);

	}

	// Método para obtener las columnas de la tabla y crear los campos de texto
	// correspondientes
	private void obtenerColumnasYCrearCampos() {
		// Obtener instancia de ConexionMySQL
		if (conexion == null) {
			conexion = new ConexionMySQL("freedb_usuario1", "YzKK@dKXqQ92dnR", "freedb_ProyectoBaseDatos");
		}

		// Establecer conexión y obtener nombres de columnas
		try {
			conexion.conectar();
			ResultSetMetaData metaData = conexion.ejecutarSelect("SELECT * FROM " + tableName).getMetaData();
			int numColumns = metaData.getColumnCount(); // Obtener número de columnas
			textFields = new JTextField[numColumns]; // Crear array de campos de texto
			int y = 50; // Posición inicial en el eje Y
			for (int i = 0; i < numColumns; i++) {
				String columnName = metaData.getColumnName(i + 1); // Obtener nombre de la columna
				JLabel label = new JLabel(columnName + ": "); // Crear etiqueta con el nombre de la columna
				label.setBounds(10, y, 120, 14); // Establecer posición y tamaño de la etiqueta
				contentPane.add(label); // Agregar etiqueta al panel

				JTextField textField = new JTextField(); // Crear campo de texto para ingresar datos
				textField.setBounds(120, y, 200, 20); // Establecer posición y tamaño del campo de texto
				contentPane.add(textField); // Agregar campo de texto al panel
				textFields[i] = textField; // Guardar referencia al campo de texto en el arreglo

				y += 30; // Incrementar posición en el eje Y para la próxima etiqueta y campo de texto
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conexion != null) {
				try {
					conexion.desconectar();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Método para agregar los datos ingresados en los campos de texto a la base de
	// datos
	private void agregarDatos() throws SQLException {
		StringBuilder consulta = new StringBuilder("INSERT INTO ");
		consulta.append(tableName).append(" VALUES ("); // Construir consulta SQL para inserción de datos
		for (int i = 0; i < textFields.length; i++) {
			consulta.append("'").append(textFields[i].getText()).append("'"); // Agregar cada valor del campo de texto a
																				// la consulta
			if (i < textFields.length - 1) {
				consulta.append(","); // Agregar coma si no es el último valor
			}
		}
		consulta.append(")");

		// Obtener instancia de ConexionMySQL
		if (conexion == null) {
			conexion = new ConexionMySQL("freedb_usuario1", "YzKK@dKXqQ92dnR", "freedb_ProyectoBaseDatos");
		}

		// Establecer conexión y ejecutar consulta
		try {
			conexion.conectar();
			int filasAfectadas = conexion.ejecutarInsertDeleteUpdate(consulta.toString());
			System.out.println("Filas afectadas: " + filasAfectadas);
			JOptionPane.showMessageDialog(null, "Datos agregados correctamente.", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
			dispose(); // Cerrar la ventana actual después de agregar datos
		} finally {
			if (conexion != null) {
				conexion.desconectar();
			}
		}
	}
}
