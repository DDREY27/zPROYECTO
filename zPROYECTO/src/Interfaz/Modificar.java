package Interfaz;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import BaseDato.ConexionMySQL;

public class Modificar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private String tableName;
	private ConexionMySQL conexion;

	public Modificar(String nombreTabla) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		setTitle("Modificar registros");

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		this.tableName = nombreTabla;

		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					modificarDatos();
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error al modificar datos: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		contentPane.add(btnModificar, BorderLayout.SOUTH);

		// Cargar y mostrar los datos en la tabla
		mostrarDatos();
	}

	// Método para cargar y mostrar los datos de la tabla en la ventana
	private void mostrarDatos() {
		if (conexion == null) {
			conexion = new ConexionMySQL("freedb_usuario1", "YzKK@dKXqQ92dnR", "freedb_ProyectoBaseDatos");
		}

		Connection connection = null;

		try {
			connection = conexion.conectar();
			ResultSet resultSet = conexion.ejecutarSelect("SELECT * FROM " + tableName);

			DefaultTableModel model = new DefaultTableModel();
			table = new JTable(model);
			scrollPane.setViewportView(table);

			// Agregar las columnas al modelo
			for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
				model.addColumn(resultSet.getMetaData().getColumnName(i));
			}

			// Agregar los datos al modelo
			while (resultSet.next()) {
				Object[] rowData = new Object[resultSet.getMetaData().getColumnCount()];
				for (int i = 0; i < rowData.length; i++) {
					rowData[i] = resultSet.getObject(i + 1);
				}
				model.addRow(rowData);
			}

			// Ajustar el ancho de las columnas para que se ajusten al contenido
			for (int column = 0; column < table.getColumnCount(); column++) {
				TableColumn tableColumn = table.getColumnModel().getColumn(column);
				int preferredWidth = 0;
				int maxWidth = 0;
				for (int row = 0; row < table.getRowCount(); row++) {
					TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
					Component comp = table.prepareRenderer(cellRenderer, row, column);
					preferredWidth = Math.max(comp.getPreferredSize().width, preferredWidth);
					maxWidth = Math.max(comp.getMaximumSize().width, maxWidth);
				}
				tableColumn.setPreferredWidth(Math.min(preferredWidth, maxWidth));
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
	
	// Método para modificar los datos de la tabla en la base de datos
	private void modificarDatos() throws SQLException {
		if (table.isEditing()) {
			table.getCellEditor().stopCellEditing();
		}

		if (conexion == null) {
			conexion = new ConexionMySQL("freedb_usuario1", "YzKK@dKXqQ92dnR", "freedb_ProyectoBaseDatos");
		}

		try {
			conexion.conectar();

			int rowCount = table.getRowCount(); // Obtener cantidad de filas en la tabla
			int columnCount = table.getColumnCount(); // Obtener cantidad de columnas en la tabla

			for (int row = 0; row < rowCount; row++) {
				StringBuilder consulta = new StringBuilder("UPDATE " + tableName + " SET ");
				for (int column = 1; column < columnCount; column++) {
					String columnName = table.getColumnName(column); // Obtener nombre de la columna
					Object value = table.getValueAt(row, column); // Obtener valor de la celda
					consulta.append(columnName).append(" = '").append(value).append("'"); // Agregar nombre de columna y valor a la consulta
					if (column < columnCount - 1) {
						consulta.append(", "); // Agregar coma si no es la última columna
					}
				}
				String primaryKeyColumnName = table.getColumnName(0); // Obtener nombre de la columna de la clave primaria
				Object primaryKeyValue = table.getValueAt(row, 0); // Obtener valor de la clave primaria
				consulta.append(" WHERE ").append(primaryKeyColumnName).append(" = '").append(primaryKeyValue)
						.append("'"); // Agregar condición WHERE para la clave primaria

				conexion.ejecutarInsertDeleteUpdate(consulta.toString()); // Ejecutar consulta de actualización
			}

			JOptionPane.showMessageDialog(null, "Datos modificados correctamente.", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
			dispose(); // Cerrar la ventana actual después de agregar datos
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al modificar datos: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			if (conexion != null) {
				conexion.desconectar();
			}
		}
	}

}
