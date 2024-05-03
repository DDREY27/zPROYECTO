package BaseDato;

import java.sql.SQLException;
import java.util.Scanner;

import BaseDato.ConexionMySQL.*;
import Clases.*;

public class Base {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner teclado = new Scanner(System.in);
		ConexionMySQL conec = new ConexionMySQL("root", "", "ProyectoBaseDatos");

		try {
			conec.conectar();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conec.desconectar();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*
		 * PacienteDAO pacienteDAO = new PacienteDAO(conec);
		 * 
		 * // Aquí puedes llamar a los métodos de pacienteDAO para realizar operaciones
		 * en la base de datos Paciente paciente = new Paciente();
		 * paciente.setNombre("Nombre del paciente");
		 * paciente.setEspecie("Especie del paciente");
		 * paciente.setRaza("Raza del paciente"); paciente.setEdad(5.5);
		 * 
		 * pacienteDAO.insertarPaciente(paciente);
		 */

	}

}
