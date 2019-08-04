package org.tollring.ic360.mssql;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcMsSql {

	public static Connection connObj;
	public static String JDBC_URL = "jdbc:sqlserver://localhost:5001;databaseName=ic360Test_20190416;user=ankit;password=ankit";
	private static final String PROPERTIES_FILE_NAME = "./observation.properties";

	
	public static void main(String[] args) {
		try {
		Properties prop = loadPropertiesFile();		
		new JdbcMsSql().populateObservations(prop);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	private static Properties loadPropertiesFile () {
		Properties prop = new Properties();
		try (FileInputStream inputStream = new FileInputStream(PROPERTIES_FILE_NAME)) {
            prop.load(inputStream);
        	} catch (IOException ex) {
            ex.printStackTrace();
        } return prop;
	}

	private void populateObservations( Properties prop ) throws SQLException{
		Connection conn = getDbConnection();
		updateObservations(conn,prop);
		conn.close();
	}
	
	public Connection getDbConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connObj = DriverManager.getConnection(JDBC_URL);
			if(connObj != null) {
				DatabaseMetaData metaObj = (DatabaseMetaData) connObj.getMetaData();
				System.out.println("Driver Name?= " + metaObj.getDriverName() + ", Driver Version?= " + metaObj.getDriverVersion() + ", Product Name?= " + metaObj.getDatabaseProductName() + ", Product Version?= " + metaObj.getDatabaseProductVersion());
			}
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		}
		return connObj;
	}

	private void updateObservations(Connection conn , Properties prop) {
			prop.forEach((key, value) -> { hitQuery( conn, Integer.parseInt((String)key), (String) value); });
	}

	private void hitQuery(Connection conn, int observation_id,String observation_sql) {
		try {
				Statement statement = conn.createStatement();
				String sql = generateUpdateQuery(observation_id,observation_sql);
				statement.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
		}
	}

	private String generateUpdateQuery(int observation_id, String sql) {
		String updateQuery  = "update ic360.observation set sql = "+"' "+sql+" '"+" where observation_id = "+observation_id;
		return updateQuery;
	}
}