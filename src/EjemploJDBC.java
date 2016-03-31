import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EjemploJDBC {
	//http://www.adictosaltrabajo.com/tutoriales/tutorial-basico-jdbc/
	
	
	public static void main(String[] args) {
		
		Connection conn = null;
	
		final String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
		final String url="jdbc:ucanaccess://C:/Datos/Curso inicial java/Registro.accdb;memory=false"; 
	
		
		// CONEXIÓN A LA BASE DE DATOS
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url);
			
			if (!conn.isClosed()) {
				System.out.println("Conectados a la base de datos!");
			}
		//Realicemos operaciones CRUD (Create, Read, Update, y Delete) simples con JDBC
		// CONSULTA DE REGISTROS --> MANEJO DE CURSORES
			PreparedStatement stmtConsulta = 
					conn.prepareStatement("SELECT * FROM UNIDADES");
			//stmtConsulta.setString(1, "123456");
			
			ResultSet rs = stmtConsulta.executeQuery();
			while (rs.next()) {
				System.out.println("Nombre: " + rs.getString("UOR_NOM"));
			}
			
			rs.close();
			stmtConsulta.close();
			
			// UPDATE DE UN REGISTRO
			PreparedStatement stmtUpdate = 
			conn.prepareStatement("UPDATE UNIDADES SET UOR_NOM = ? WHERE UOR_NOM = ?");
			stmtUpdate.setString(1, "ALCALDÍA Y PRESIDENCIA");
			stmtUpdate.setString(2, "ALCALDIA");
			// conn.setAutoCommit(false);
			int count = stmtUpdate.executeUpdate();
			System.out.println("Registro afectados por el update " + count);
			stmtUpdate.close();
			// conn.commit();
			
			// DELETE DE UN REGISTRO
			PreparedStatement stmtDelete = 
			conn.prepareStatement("DELETE FROM UNIDADES WHERE UOR_COD=?");
			stmtDelete.setInt(1, 19);
			int cont2 = stmtDelete.executeUpdate();
			System.out.println("Registros eliminados " + cont2);
			stmtDelete.close();
				
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			//conn.rollback();
		} finally {
	//  !!! Al final siempre cerramos conexión
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.err.println("Exception: " + e.getMessage());
			}
		}

			
	
		
	}

}
