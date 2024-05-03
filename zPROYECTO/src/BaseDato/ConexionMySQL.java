package BaseDato;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Clase para la conexión con una base de datos MySQL
 *
 * @author Francisco Jesús Delgado Almirón
 */
public class ConexionMySQL {

	// Base de datos a la que nos conectamos
	private String BD;
	// Usuario de la base de datos
	private String USUARIO;
	// Contraseña del usuario de la base de datos
	private String PASS;
	// Objeto donde se almacenará nuestra conexión
	private Connection connection;
	// Indica que está en localhost
	private String HOST;
	// Zona horaria
	private TimeZone zonahoraria;

	/**
	 * Constructor de la clase
	 *
	 * @param usuario Usuario de la base de datos
	 * @param pass    Contraseña del usuario
	 * @param bd      Base de datos a la que nos conectamos
	 */
	public ConexionMySQL(String usuario, String pass, String bd) {
		HOST = "sql.freedb.tech";
		USUARIO = usuario;
		PASS = pass;
		BD = bd;
		connection = null;
	}

	/**
	 * Comprueba que el driver de MySQL esté correctamente integrado
	 *
	 * @throws SQLException Se lanzará cuando haya un fallo con la base de datos
	 */
	private void registrarDriver() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new SQLException("Error al conectar con MySQL: " + e.getMessage());
		}
	}

	/**
	 * Conecta con la base de datos
	 * 
	 * @return
	 *
	 * @throws SQLException Se lanzará cuando haya un fallo con la base de datos
	 */
	/*
	 * public void conectar() throws SQLException { if (connection == null ||
	 * connection.isClosed()) { registrarDriver(); // Obtengo la zona horaria
	 * Calendar now = Calendar.getInstance(); zonahoraria = now.getTimeZone();
	 * connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + HOST
	 * + "/" + BD + "?user=" + USUARIO + "&password=" + PASS +
	 * "&useLegacyDatetimeCode=false&serverTimezone=" + zonahoraria.getID()); } }
	 */

	public Connection conectar() throws SQLException {
		System.out.println("Intentando conectar a la base de datos...");
		if (connection == null || connection.isClosed()) {
			registrarDriver();
			// Obtengo la zona horaria
			Calendar now = Calendar.getInstance();
			zonahoraria = now.getTimeZone();
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + BD + "?user=" + USUARIO
					+ "&password=" + PASS + "&useLegacyDatetimeCode=false&serverTimezone=" + zonahoraria.getID());
			System.out.println("Conexión establecida correctamente.");
		}
		return connection; // Devolver la conexión establecida
	}

	/*
	 * public Connection conectar() throws SQLException { Connection conn = null;
	 * System.out.println("Intentando conectar a la base de datos..."); if
	 * (connection == null || connection.isClosed()) { registrarDriver(); // Obtengo
	 * la zona horaria Calendar now = Calendar.getInstance(); zonahoraria =
	 * now.getTimeZone(); connection = (Connection)
	 * DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + BD + "?user=" +
	 * USUARIO + "&password=" + PASS +
	 * "&useLegacyDatetimeCode=false&serverTimezone=" + zonahoraria.getID());
	 * System.out.println("Conexión establecida correctamente."); } return conn; }
	 */

	/**
	 * Cierra la conexión con la base de datos
	 *
	 * @throws SQLException Se lanzará cuando haya un fallo con la base de datos
	 */
	public void desconectar() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}

	/**
	 * Ejecuta una consulta SELECT
	 *
	 * @param consulta Consulta SELECT a ejecutar
	 * @return Resultado de la consulta
	 * @throws SQLException Se lanzará cuando haya un fallo con la base de datos
	 */
	public ResultSet ejecutarSelect(String consulta) throws SQLException {
		Statement stmt = connection.createStatement();
		ResultSet rset = stmt.executeQuery(consulta);

		return rset;
	}

	/**
	 * Ejecuta una consulta INSERT, DELETE o UPDATE
	 *
	 * @param consulta Consulta INSERT, DELETE o UPDATE a ejecutar
	 * @return Cantidad de filas afectadas
	 * @throws SQLException Se lanzará cuando haya un fallo con la base de datos
	 */
	public int ejecutarInsertDeleteUpdate(String consulta) throws SQLException {
		Statement stmt = connection.createStatement();
		int fila = stmt.executeUpdate(consulta);

		return fila;
	}

	public Statement createStatement() throws SQLException {
		if (connection == null || connection.isClosed()) {
			throw new SQLException("La conexión no está establecida.");
		}
		return connection.createStatement();
	}

	/*
	 * public class PacienteDAO { private ConexionMySQL conexion;
	 * 
	 * public PacienteDAO(ConexionMySQL conexion) { this.conexion = conexion; }
	 * 
	 * public void insertarPaciente(Paciente paciente) { String consulta =
	 * "INSERT INTO Paciente (Nombre, Especie, Raza, Edad) VALUES ('" +
	 * paciente.getNombre() + "', '" + paciente.getEspecie() + "', '" +
	 * paciente.getRaza() + "', " + paciente.getEdad() + ")"; try {
	 * conexion.conectar(); conexion.ejecutarInsertDeleteUpdate(consulta); } catch
	 * (SQLException e) { e.printStackTrace(); } finally { try {
	 * conexion.desconectar(); } catch (SQLException e) { e.printStackTrace(); } } }
	 * 
	 * // Implementa las demás operaciones CRUD según sea necesario
	 * 
	 * 
	 * }
	 * 
	 */
}
