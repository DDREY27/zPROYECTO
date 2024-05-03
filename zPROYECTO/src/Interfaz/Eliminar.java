package Interfaz;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import BaseDato.ConexionMySQL;

public class Eliminar extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabla;
	private String tablaSeleccionada;
	private ConexionMySQL conexion;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Modificar frame = new Modificar("Nombre_Tabla_Aqui");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Constructor de la clase Eliminar
	public Eliminar(String tablaSeleccionada) {
		this.tablaSeleccionada = tablaSeleccionada;
		cargarDatos();
		mostrarDatos();
	}

	// Método para cargar los elementos de la interfaz gráfica
	private void cargarDatos() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		setLocationRelativeTo(null);
		setTitle("Eliminar registros");

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		tabla = new JTable();
		scrollPane.setViewportView(tabla);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					eliminarRegistro();
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error al agregar datos: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		contentPane.add(btnEliminar, BorderLayout.SOUTH);
	}

	// Método para mostrar los datos en la tabla
	private void mostrarDatos() {
		if (conexion == null) {
			conexion = new ConexionMySQL("freedb_usuario1", "YzKK@dKXqQ92dnR", "freedb_ProyectoBaseDatos");
		}
		Connection connection = null;

		try {
			connection = conexion.conectar();
			ResultSet resultSet = conexion.ejecutarSelect("SELECT * FROM " + tablaSeleccionada);

			DefaultTableModel model = new DefaultTableModel();
			tabla.setModel(model);

			// Agregamos las columnas al modelo
			for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
				model.addColumn(resultSet.getMetaData().getColumnName(i));
			}

			// Agregamos los datos al modelo
			while (resultSet.next()) {
				Object[] rowData = new Object[resultSet.getMetaData().getColumnCount()];
				for (int i = 0; i < rowData.length; i++) {
					rowData[i] = resultSet.getObject(i + 1);
				}
				model.addRow(rowData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// Método para eliminar un registro de la tabla
	private void eliminarRegistro() throws SQLException {
		int filaSeleccionada = tabla.getSelectedRow();
		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(this, "Por favor, selecciona una fila para eliminar.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Confirmar si el usuario desea eliminar el registro
		int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres eliminar este registro?",
				"Confirmación", JOptionPane.YES_NO_OPTION);
		if (confirmacion == JOptionPane.YES_OPTION) {
			DefaultTableModel model = (DefaultTableModel) tabla.getModel();

			// Eliminar el registro de la base de datos
			if (conexion == null) {
				conexion = new ConexionMySQL("freedb_usuario1", "YzKK@dKXqQ92dnR", "freedb_ProyectoBaseDatos");
			}
			Connection connection = null;

			try {
				connection = conexion.conectar();

				// Construir la sentencia SQL para eliminar la fila
				StringBuilder sentencia = new StringBuilder("DELETE FROM " + tablaSeleccionada + " WHERE ");
				for (int columna = 0; columna < model.getColumnCount(); columna++) {
					Object valor = model.getValueAt(filaSeleccionada, columna);
					String nombreColumna = model.getColumnName(columna);
					// Añadir cada columna a la condición de eliminación
					sentencia.append(nombreColumna).append(" = '").append(valor).append("'");

					// Agregar operador lógico AND si no es la última columna
					if (columna < model.getColumnCount() - 1) {
						sentencia.append(" AND ");
					}
				}

				conexion.ejecutarInsertDeleteUpdate(sentencia.toString());

				// Eliminar la fila de la tabla
				model.removeRow(filaSeleccionada);
				JOptionPane.showMessageDialog(null, "Datos eliminados correctamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
				dispose(); // Cerrar la ventana actual después de agregar datos

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}