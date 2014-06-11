package todo.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CommonMySQLDAO {
	protected Connection connection = null;

	public void getConnection() throws Exception {
		try {
			if (connection == null || connection.isClosed()) {
				InitialContext initCtx = new InitialContext();

				DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/dbtest");
				connection = ds.getConnection();
				connection.setAutoCommit(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			connection = null;
			throw e;
		}
	}
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection = null;
		}
	}
}