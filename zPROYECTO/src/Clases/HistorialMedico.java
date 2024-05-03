package Clases;

import java.util.Date;

public class HistorialMedico {
	private int idHistorialMedico;
	private Date fechaConsulta;
	private String diagnostico;
	private String tratamiento;
	private String recomendaciones;
	private int doctoresIdDoctores;

	public HistorialMedico() {
	}

	public int getIdHistorialMedico() {
		return idHistorialMedico;
	}

	public void setIdHistorialMedico(int idHistorialMedico) {
		this.idHistorialMedico = idHistorialMedico;
	}

	public Date getFechaConsulta() {
		return fechaConsulta;
	}

	public void setFechaConsulta(Date fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

	public String getRecomendaciones() {
		return recomendaciones;
	}

	public void setRecomendaciones(String recomendaciones) {
		this.recomendaciones = recomendaciones;
	}

	public int getDoctoresIdDoctores() {
		return doctoresIdDoctores;
	}

	public void setDoctoresIdDoctores(int doctoresIdDoctores) {
		this.doctoresIdDoctores = doctoresIdDoctores;
	}
}
