package Interfaz;

//Importaciones necesarias
import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import BaseDato.*;
import javax.swing.JTable;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.Toolkit;

//Declaración de la clase Principal que hereda de JFrame
public class Principal extends JFrame {

	// Declaración de variables miembro
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable TABLA;
	private String tablaSeleccionada; // Variable para almacenar el nombre de la tabla seleccionada
	private final JLabel lblNewLabel_2 = new JLabel("Pablo Almellones Ramos & David Domínguez Reina");

	// Método principal que inicia la aplicación
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Constructor de la clase Principal
	public Principal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/Interfaz/Logo.png")));
		// Configuración de la ventana principal
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280, 720); // Establecer el tamaño de la ventana
		setLocationRelativeTo(null); // Centrar la ventana en la pantalla
		setTitle("Administrador Veterinaria"); // Establecer el título de la ventana
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Panel izquierdo
		JPanel panel_izq = new JPanel();
		panel_izq.setBackground(SystemColor.activeCaption);
		panel_izq.setBounds(0, 193, 263, 488);
		contentPane.add(panel_izq);
		panel_izq.setLayout(null);

		// Botón Agregar
		JButton BotonAgregar = new JButton("Agregar");
		BotonAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tablaSeleccionada != null) {
					// Abre la ventana de inserción pasando el nombre de la tabla seleccionada
					Insertar ventanaInsertar = new Insertar(tablaSeleccionada); // Pasar el nombre de la tabla
																				// seleccionada a la ventana Insertar
					ventanaInsertar.setLocationRelativeTo(null);
					ventanaInsertar.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Primero selecciona una tabla.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		BotonAgregar.setIcon(new ImageIcon(Principal.class.getResource("/Interfaz/Boton5.png")));
		BotonAgregar.setVerifyInputWhenFocusTarget(false);
		BotonAgregar.setHorizontalTextPosition(SwingConstants.LEFT);
		BotonAgregar.setHorizontalAlignment(SwingConstants.RIGHT);
		BotonAgregar.setFont(new Font("Arial", Font.BOLD, 16));
		BotonAgregar.setBounds(56, 99, 149, 34);
		panel_izq.add(BotonAgregar);

		// Botón Modificar
		JButton BotonModificar = new JButton("Modificar");
		BotonModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tablaSeleccionada != null) {
					// Abre la ventana de modificación pasando el nombre de la tabla seleccionada
					Modificar ventanaModificar = new Modificar(tablaSeleccionada); // Crear una instancia de la
																					// claseModificar
					ventanaModificar.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
					ventanaModificar.setVisible(true); // Hacer visible la nueva ventana
				} else {
					JOptionPane.showMessageDialog(null, "Primero selecciona una tabla.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		BotonModificar.setIcon(new ImageIcon(Principal.class.getResource("/Interfaz/Boton7.png")));
		BotonModificar.setVerifyInputWhenFocusTarget(false);
		BotonModificar.setHorizontalTextPosition(SwingConstants.LEFT);
		BotonModificar.setHorizontalAlignment(SwingConstants.RIGHT);
		BotonModificar.setFont(new Font("Arial", Font.BOLD, 16));
		BotonModificar.setBounds(56, 201, 149, 34);
		panel_izq.add(BotonModificar);

		// Botón Eliminar
		JButton BotonEliminar = new JButton("Eliminar");
		BotonEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tablaSeleccionada != null) {
					// Abre la ventana de eliminación pasando el nombre de la tabla seleccionada
					Eliminar ventanaEliminar = new Eliminar(tablaSeleccionada); // Crear una instancia de la clase
																				// Eliminar
					ventanaEliminar.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
					ventanaEliminar.setVisible(true); // Hacer visible la nueva ventana
				} else {
					JOptionPane.showMessageDialog(null, "Primero selecciona una tabla.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		BotonEliminar.setIcon(new ImageIcon(Principal.class.getResource("/Interfaz/Boton6.png")));
		BotonEliminar.setVerifyInputWhenFocusTarget(false);
		BotonEliminar.setHorizontalTextPosition(SwingConstants.LEFT);
		BotonEliminar.setHorizontalAlignment(SwingConstants.RIGHT);
		BotonEliminar.setFont(new Font("Arial", Font.BOLD, 16));
		BotonEliminar.setBounds(56, 300, 149, 34);
		panel_izq.add(BotonEliminar);

		// Panel central
		JPanel panel_centro = new JPanel();
		panel_centro.setBackground(SystemColor.inactiveCaption);
		panel_centro.setBounds(0, 0, 1264, 681);
		contentPane.add(panel_centro);
		panel_centro.setLayout(null);

		// Etiqueta de título
		JLabel lblNewLabel = new JLabel("Los Amigos de los niños");
		lblNewLabel.setBackground(SystemColor.textHighlight);
		lblNewLabel.setForeground(SystemColor.desktop);
		lblNewLabel.setFont(new Font("Lucida Console", Font.PLAIN, 35));
		lblNewLabel.setBounds(506, 40, 498, 41);
		panel_centro.add(lblNewLabel);

		// Botones de selección de tabla
		JButton BotonPaciente = new JButton("Paciente");
		BotonPaciente.setVerticalTextPosition(SwingConstants.BOTTOM);
		BotonPaciente.setHorizontalTextPosition(SwingConstants.CENTER);
		BotonPaciente.setVerifyInputWhenFocusTarget(false);
		BotonPaciente.setVerticalAlignment(SwingConstants.TOP);
		BotonPaciente.setIcon(new ImageIcon(Principal.class.getResource("/Interfaz/Boton1.png")));
		BotonPaciente.setFont(new Font("Arial", Font.PLAIN, 12));
		BotonPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrar("Paciente");
				tablaSeleccionada = "Paciente"; // Al mostrar la tabla Paciente, asigna el nombre de la tabla
												// seleccionada
			}
		});
		BotonPaciente.setBounds(412, 157, 116, 91);
		panel_centro.add(BotonPaciente);

		// Botones de selección de tabla
		JButton BotonPropietario = new JButton("Propietario");
		BotonPropietario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrar("Propietario");
				tablaSeleccionada = "Propietario"; // Al mostrar la tabla Propietario, asigna el nombre de la tabla
													// seleccionada
			}
		});
		BotonPropietario.setIcon(new ImageIcon(Principal.class.getResource("/Interfaz/Boton2.png")));
		BotonPropietario.setVerticalTextPosition(SwingConstants.BOTTOM);
		BotonPropietario.setVerticalAlignment(SwingConstants.TOP);
		BotonPropietario.setVerifyInputWhenFocusTarget(false);
		BotonPropietario.setHorizontalTextPosition(SwingConstants.CENTER);
		BotonPropietario.setFont(new Font("Arial", Font.PLAIN, 12));
		BotonPropietario.setBounds(589, 157, 116, 91);
		panel_centro.add(BotonPropietario);

		// Botones de selección de tabla
		JButton BotonHistorial = new JButton("Historial Médico");
		BotonHistorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrar("HistorialMedico");
				tablaSeleccionada = "HistorialMedico"; // Al mostrar la tabla HistorialMedico, asigna el nombre de la
														// tabla seleccionada
			}
		});
		BotonHistorial.setIcon(new ImageIcon(Principal.class.getResource("/Interfaz/Boton3.png")));
		BotonHistorial.setVerticalTextPosition(SwingConstants.BOTTOM);
		BotonHistorial.setVerticalAlignment(SwingConstants.TOP);
		BotonHistorial.setVerifyInputWhenFocusTarget(false);
		BotonHistorial.setHorizontalTextPosition(SwingConstants.CENTER);
		BotonHistorial.setFont(new Font("Arial", Font.PLAIN, 12));
		BotonHistorial.setBounds(777, 157, 127, 91);
		panel_centro.add(BotonHistorial);

		// Botones de selección de tabla
		JButton BotonDoctores = new JButton("Doctores");
		BotonDoctores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrar("Doctores");
				tablaSeleccionada = "Doctores"; // Al mostrar la tabla Doctores, asigna el nombre de la tabla
												// seleccionada
			}
		});
		BotonDoctores.setIcon(new ImageIcon(Principal.class.getResource("/Interfaz/Boton4.png")));
		BotonDoctores.setVerticalTextPosition(SwingConstants.BOTTOM);
		BotonDoctores.setVerticalAlignment(SwingConstants.TOP);
		BotonDoctores.setVerifyInputWhenFocusTarget(false);
		BotonDoctores.setHorizontalTextPosition(SwingConstants.CENTER);
		BotonDoctores.setFont(new Font("Arial", Font.PLAIN, 12));
		BotonDoctores.setBounds(963, 157, 116, 91);
		panel_centro.add(BotonDoctores);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(412, 354, 667, 233);
		panel_centro.add(scrollPane);

		// Tabla donde se muestra la información
		TABLA = new JTable();
		TABLA.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setViewportView(TABLA);
		TABLA.setColumnSelectionAllowed(true);
		TABLA.setCellSelectionEnabled(true);
		TABLA.setModel(new DefaultTableModel(new Object[][] {}, new String[] {}));

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Principal.class.getResource("/Interfaz/Logo.png")));
		lblNewLabel_1.setBounds(50, 11, 172, 162);
		panel_centro.add(lblNewLabel_1);
		lblNewLabel_2.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 9));
		lblNewLabel_2.setBounds(994, 657, 270, 24);
		panel_centro.add(lblNewLabel_2);
	}

	// Método para mostrar los datos de una tabla en la JTable
	public void mostrar(String tablaSeleccionada) {
		// Conexión a la base de datos
		ConexionMySQL conec = new ConexionMySQL("freedb_usuario1", "YzKK@dKXqQ92dnR", "freedb_ProyectoBaseDatos");
		//// ConexionMySQL conec = new ConexionMySQL("root", "test",
		//// "ProyectoBaseDatos");
		Connection Conexion = null;

		try {
			// Establecer conexión
			Conexion = conec.conectar();

			// Obtener metadatos de la consulta
			ResultSet resultSet = conec.ejecutarSelect("SELECT * FROM " + tablaSeleccionada);
			ResultSetMetaData metaData = resultSet.getMetaData();

			DefaultTableModel model = new DefaultTableModel();

			// Agregamos las columnas al modelo usando los nombres de columna obtenidos
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				model.addColumn(metaData.getColumnName(i));
			}

			TABLA.setModel(model);

			String[] datos = new String[metaData.getColumnCount()];

			while (resultSet.next()) {
				// Agregamos los datos de cada fila
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					datos[i] = resultSet.getString(i + 1);
				}
				model.addRow(datos);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (Conexion != null) {
				try {
					Conexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
