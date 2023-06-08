package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Huespedes;
import model.Reservas;

public class HuespedesDAO {
	
	private Connection cn;

	public HuespedesDAO(Connection cn) {
		this.cn = cn;
	}

	public Long guardarHuesped(Huespedes huesped) {
		try {
			PreparedStatement pstmt = cn.prepareStatement("INSERT INTO HUESPEDES (NOMBRE, APELLIDO, FECHADENACIMIENTO,NACIONALIDAD"
															+ ", TELEFONO, IDRESERVA) VALUES (?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			try (pstmt){
				pstmt.setString(1, huesped.getNombre());				
				pstmt.setString(2, huesped.getApellido());
				pstmt.setObject(3, huesped.getFechaDeNacimiento());
				pstmt.setString(4, huesped.getNacionalidad());
				pstmt.setString(5, huesped.getTelefono());
				pstmt.setLong(6, huesped.getIdReserva());

				pstmt.execute();
				
				final ResultSet resultSet = pstmt.getGeneratedKeys();
	        	try(resultSet){
	    	    	while (resultSet.next()) {
	    	    		huesped.setId(resultSet.getLong(1));
	    	    	}
	        	}
				pstmt.getGeneratedKeys();
				return huesped.getIdReserva();
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}	
	}

	public List<Huespedes> listar() {

		List<Huespedes> huespedes = new ArrayList<>(); 

		try{
			final PreparedStatement stmt = this.cn
					.prepareStatement("SELECT ID, NOMBRE, APELLIDO, FECHADENACIMIENTO, NACIONALIDAD, TELEFONO, IDRESERVA FROM HUESPEDES");
			
			try (stmt){
				stmt.execute();
				
				final ResultSet rs = stmt.getResultSet();
				
				try (rs){
					while (rs.next()) {
						huespedes.add(new Huespedes(
								rs.getInt("ID"),
								rs.getString("NOMBRE"),
								rs.getString("APELLIDO"),
								rs.getDate("FECHADENACIMIENTO").toLocalDate(),
								rs.getString("NACIONALIDAD"),
								rs.getString("TELEFONO"),
								rs.getLong("IDRESERVA")));
					}
				}
			}
			
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return huespedes;
	}

	public long editar(Long id, String nombre, String apellido, LocalDate fechaDeNacimiento, String nacionalidad, String telefono, Long idReserva) {

		try {
		final PreparedStatement stmt = cn.prepareStatement("UPDATE HUESPEDES SET NOMBRE = ? ," + 
					" APELLIDO = ? , NACIONALIDAD = ?, TELEFONO= ?, IDRESERVA = ?  WHERE ID = ?");
			try (stmt){
				stmt.setString(1, nombre);
				stmt.setString(2, apellido);
				stmt.setObject(3, fechaDeNacimiento);
				stmt.setString(4, nacionalidad);
				stmt.setString(5, telefono);
				stmt.setLong(6, id);
				
				stmt.execute();		
				
			    return id;
			}
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public long eliminar(Long idHuesped, Long idReserva) {
		try {
			final PreparedStatement stmt = this.cn.prepareStatement("DELETE FROM HUESPEDES WHERE ID = ?");
				try(stmt){
					stmt.setLong(1, idHuesped);					
					stmt.execute();							
				}
			final PreparedStatement stmt2 = this.cn.prepareStatement("DELETE FROM RESERVAS WHERE ID = ?");
				try(stmt2){
					stmt2.setLong(1, idReserva);
					stmt2.execute();							
				}
			return idHuesped;
		}catch (SQLException e) {
			return -1;
		}		
	}

	public List<Huespedes> listar(String apellido) {
		List<Huespedes> huespedes = new ArrayList<>(); 
		String str = apellido.toUpperCase();
		System.out.println(str);
		try{
			final PreparedStatement stmt = this.cn
					.prepareStatement("SELECT ID, NOMBRE, APELLIDO, FECHADENACIMIENTO, NACIONALIDAD, TELEFONO, IDRESERVA FROM HUESPEDES WHERE UPPER(APELLIDO) = ?");
			
			try (stmt){
				stmt.setString(1, str);
				stmt.execute();
				
				final ResultSet rs = stmt.getResultSet();
				
				try (rs){
					while (rs.next()) {
						huespedes.add(new Huespedes(
								rs.getInt("ID"),
								rs.getString("NOMBRE"),
								rs.getString("APELLIDO"),
								rs.getDate("FECHADENACIMIENTO").toLocalDate(),
								rs.getString("NACIONALIDAD"),
								rs.getString("TELEFONO"),
								rs.getLong("IDRESERVA")));
					}
				}
			}
			
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return huespedes;
	}
}
