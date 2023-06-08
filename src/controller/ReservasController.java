package controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import DAO.ReservasDAO;
import factory.ConnectionFactory;
import model.Reservas;

public class ReservasController {

	private ReservasDAO reservaDAO;
	
	public ReservasController() {
		
		this.reservaDAO = new ReservasDAO(new ConnectionFactory().getConexion());
		
	}

	public List<Reservas> listar() {
		return this.reservaDAO.listar();
	}

	public long editar(Long id, LocalDate fechaEntrada, LocalDate fechaSalida, BigDecimal valor, String formaDePago) {
		return (this.reservaDAO.editar(id, fechaEntrada, fechaSalida, valor, formaDePago));		
	}

	public long eliminar(Long idReserva) {
		return(this.reservaDAO.eliminar(idReserva));
	}

	public List<Reservas> listar(Long id) {
		return this.reservaDAO.listar(id);
	}
	
	
	
	
	
	
}
