package it.polito.tdp.metroparis.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

/**
 * Utility class for connecting to the database
 * 
 * Uses the HikariCP library for managing a connection pool
 * @see <a href="https://brettwooldridge.github.io/HikariCP/">HikariCP</a>
 */
public class DBConnect 
{
	private static final String jdbcURL = "jdbc:mariadb://127.0.0.1/metroparis?serverTimezone=UTC";
	private static final String user = "root";
	private static final String password = "root";
	private static final HikariDataSource dataSource;

	static 
	{
		dataSource = new HikariDataSource();

		dataSource.setJdbcUrl(jdbcURL);
		dataSource.setUsername(user);
		dataSource.setPassword(password);

		// configurazione MySQL
		dataSource.addDataSourceProperty("cachePrepStmts", "true");
		dataSource.addDataSourceProperty("prepStmtCacheSize", "250");
		dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
	}

	public static Connection getConnection() 
	{
		try 
		{
			return dataSource.getConnection();
		} 
		catch (SQLException sqle) 
		{
			System.err.println("Errore connessione al DB, url: " + jdbcURL);
			throw new RuntimeException(sqle);
		}
	}

}
