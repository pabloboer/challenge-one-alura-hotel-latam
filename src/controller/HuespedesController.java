package controller;

import java.time.LocalDate;
import java.util.List;

import DAO.HuespedesDAO;
import factory.ConnectionFactory;
import model.Huespedes;

public class HuespedesController {
	

	private HuespedesDAO huespedDAO;
	
	public HuespedesController() {
		
		this.huespedDAO = new HuespedesDAO(new ConnectionFactory().getConexion());
		
	}

	public List<Huespedes> listar() {
		return this.huespedDAO.listar();
	}

	public long editar(Long id, String nombre, String apellido, LocalDate fechaDeNacimiento, String nacionalidad, String telefono, Long idReserva) {
		return(this.huespedDAO.editar(id, nombre, apellido, fechaDeNacimiento, nacionalidad, telefono, idReserva));
	}

	public long eliminar(Long idHuesped, Long idReserva) {
		return (this.huespedDAO.eliminar(idHuesped, idReserva));
	}

	public List<Huespedes> listar(String apellido) {
		return this.huespedDAO.listar(apellido);
	}
	

}
