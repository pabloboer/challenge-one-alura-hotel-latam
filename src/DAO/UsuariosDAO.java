package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import model.Usuarios;


public class UsuariosDAO {
	
	private Connection con;

	public UsuariosDAO(Connection con) {
		this.con = con;
	}

	public void nuevoUsuario(Usuarios usr) {
		
		try {
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO USUARIOS (NOMBRE, CONTRASENA) VALUES (?,?)",Statement.RETURN_GENERATED_KEYS);
			
			try (pstmt){
				pstmt.setString(1, usr.getNombre());
				pstmt.setString(2, usr.getContrasena());
				
				final ResultSet resultSet = pstmt.getGeneratedKeys();
	        	
	        	try(resultSet){
	    	    	while (resultSet.next()) {
	    	    		usr.setId(resultSet.getLong(1));
	    	    		System.out.println(String.format("Fue agrgado el usuario %s", usr));
	    	    	}
	        	}
				pstmt.getGeneratedKeys();
			}
		}catch(SQLException e) {
			throw new RuntimeException();
		}
	
	}
	
	public List<Usuarios> listarUsuarios() {
		List <Usuarios> resultado = new ArrayList<>();
		
		try{
			final PreparedStatement ptmt = this.con
					.prepareStatement("SELECT * FROM USUARIOS");
			
			try (ptmt){
				ptmt.executeQuery();				
				
				final ResultSet rs = ptmt.getResultSet();
				
				try (rs){
					while (rs.next()) {
						Usuarios usr= new Usuarios(rs.getString("NOMBRE"),rs.getString("CONTRASENA"));
						resultado.add(usr);
					}
				}
			}
			
		}catch (SQLException e) {
			System.out.println("error en la tabla");
			throw new RuntimeException(e);
		}
			return resultado;
	}

	public boolean existeUsuario(String nombre, String pass) {
		try{
			
			PreparedStatement pstmt = this.con.prepareStatement("SELECT ID FROM USUARIOS WHERE NOMBRE = ? AND CONTRASENA = ?");
			
			try (pstmt){
				pstmt.setString(1, nombre);
				pstmt.setString(2, pass);
				
				final ResultSet rs = pstmt.executeQuery();
				
				try (rs){
					if(!rs.next()) {
						return false;
					}
					return true;
				}
			}
		}catch (SQLException e) {
			System.out.println("error en la tabla");
			throw new RuntimeException(e);
		}
	}
}
