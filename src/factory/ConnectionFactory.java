package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {

	private Connection cn;
	
	public ConnectionFactory() {

		String url ="jdbc:mysql://localhost/hotel_alura?useTimezone=true&serverTimeZone=UTC";
		
		try {
			this.cn = DriverManager.getConnection(url, "root", "12345678");
		} catch (SQLException e) {
			throw new RuntimeException ("No se pudeo establecer la conexion con la base de datos");
		}
	}
		
	public Connection getConexion() {
			return this.cn;
	}

}
