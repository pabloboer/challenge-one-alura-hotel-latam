package DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Reservas;

public class ReservasDAO {
	
	private Connection cn;
	

	public ReservasDAO(Connection cn) {
		this.cn = cn;
	}

	public long guardarReserva(Reservas reserva) {
		try {
			PreparedStatement pstmt = cn.prepareStatement("INSERT INTO RESERVAS (FECHAENTRADA, FECHASALIDA, VALOR, FORMADEPAGO) VALUES (?,?,?,?)"
															,Statement.RETURN_GENERATED_KEYS);
			try (pstmt){
				pstmt.setObject(1, reserva.getFechaEntrada());
				pstmt.setObject(2, reserva.getFechaSalida());
				pstmt.setBigDecimal(3, reserva.getValor());
				pstmt.setString(4, reserva.getFormaPago());
				
				pstmt.execute();
				
				final ResultSet resultSet = pstmt.getGeneratedKeys();
	        	try(resultSet){
	    	    	while (resultSet.next()) {
	    	    		reserva.setId(resultSet.getLong(1));
	    	    		System.out.println(String.format("Fue agregada la reserva %s", reserva.getId()));
	    	    	}
	        	}
				pstmt.getGeneratedKeys();
				return reserva.getId();
			}
		}catch(SQLException e) {
			throw new RuntimeException();
		}
	}

	public List<Reservas> listar() {

		List<Reservas> reservas = new ArrayList<>(); 

		try{
			final PreparedStatement stmt = this.cn
					.prepareStatement("SELECT ID, FECHAENTRADA, FECHASALIDA, VALOR, FORMADEPAGO FROM RESERVAS");
			
			try (stmt){
				stmt.execute();
				
				final ResultSet rs = stmt.getResultSet();
				
				try (rs){
					while (rs.next()) {
						reservas.add(new Reservas(
								rs.getInt("ID"),
								rs.getDate("FECHAENTRADA").toLocalDate(),
								rs.getDate("FECHASALIDA").toLocalDate(),
								rs.getString("VALOR"),
								rs.getString("FORMADEPAGO")));
					}
				}
			}
			
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return reservas;
	}

	public long editar(Long id, LocalDate fechaEntrada, LocalDate fechaSalida, BigDecimal valor, String formaDePago) {
		
		try {
		final PreparedStatement stmt = cn.prepareStatement("UPDATE RESERVAS SET FECHAENTRADA = ? ," + 
					" FECHASALIDA = ? , VALOR = ?, FORMADEPAGO = ?  WHERE ID = ?");
			try (stmt){
				stmt.setObject(1, fechaEntrada);
				stmt.setObject(2, fechaSalida);
				stmt.setBigDecimal(3, valor);
				stmt.setString(4, formaDePago);
				stmt.setLong(5, id);
				
				stmt.execute();		
				
			    return id;
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public long eliminar(Long idReserva) {
		try {
			final PreparedStatement stmt = this.cn.prepareStatement("DELETE FROM HUESPEDES WHERE IDRESERVA = ?");
				try(stmt){
					stmt.setLong(1, idReserva);
					stmt.execute();							
				}
			final PreparedStatement stmt2 = this.cn.prepareStatement("DELETE FROM RESERVAS WHERE ID = ?");
				try(stmt2){
					stmt2.setLong(1, idReserva);
					stmt2.execute();							
				}
			return idReserva;
		}catch (SQLException e) {
			return -1;
		}		
	}

	
	public List<Reservas> listar(Long id) {

		List<Reservas> reservas = new ArrayList<>(); 

		try{
			final PreparedStatement stmt = this.cn
					.prepareStatement("SELECT ID, FECHAENTRADA, FECHASALIDA, VALOR, FORMADEPAGO FROM RESERVAS WHERE ID = ?");
			
			try (stmt){
				stmt.setLong(1, id);
				stmt.execute();
				
				final ResultSet rs = stmt.getResultSet();
				
				try (rs){
					while (rs.next()) {
						reservas.add(new Reservas(
								rs.getInt("ID"),
								rs.getDate("FECHAENTRADA").toLocalDate(),
								rs.getDate("FECHASALIDA").toLocalDate(),
								rs.getString("VALOR"),
								rs.getString("FORMADEPAGO")));
					}
				}
			}
			
		}catch (SQLException e) {
			System.out.println("error listando la tabla");
			throw new RuntimeException(e);
		}
		return reservas;
	}
}
