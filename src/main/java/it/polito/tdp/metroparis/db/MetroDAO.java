package it.polito.tdp.metroparis.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metroparis.model.Connessione;
import it.polito.tdp.metroparis.model.Fermata;
import it.polito.tdp.metroparis.model.Linea;

public class MetroDAO 
{
	public List<Fermata> getAllFermate() 
	{
		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try 
		{
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) 
			{
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"),
							new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}

			rs.close();
			st.close();
			conn.close();
		} 
		catch (SQLException sqle) 
		{
			sqle.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database in getAllFermate()");
		}

		return fermate;
	}

	public List<Linea> getAllLinee() 
	{
		final String sql = "SELECT id_linea, nome, velocita, intervallo FROM linea ORDER BY nome ASC";

		List<Linea> linee = new ArrayList<Linea>();

		try 
		{
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) 
			{
				Linea f = new Linea(rs.getInt("id_linea"), rs.getString("nome"), 
									rs.getDouble("velocita"), rs.getDouble("intervallo"));
				linee.add(f);
			}
			
			rs.close();
			st.close();
			conn.close();
		} 
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database in getAllLinee()");
		}
		
		return linee;
	}

	public boolean sonoFermateCollegate(Fermata f1, Fermata f2)
	{
		String sqlQuery = String.format("%s %s %s %s",
											"SELECT COUNT(*) AS count",
											"FROM connessione",
											"WHERE (id_stazP = ? AND id_stazA = ?) OR",
											"(id_stazP = ? AND id_stazA = ?)");
		try
		{
			Connection connection = DBConnect.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, f1.getIdFermata());
			statement.setInt(4, f1.getIdFermata());
			statement.setInt(2, f2.getIdFermata());
			statement.setInt(3, f2.getIdFermata());
			
			ResultSet queryResult = statement.executeQuery();
			queryResult.first();
			
			int count = queryResult.getInt("count");
			
			queryResult.close();
			statement.close();
			connection.close();
			
			return count >= 1;
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
			throw new RuntimeException("Errore in .sonoFermateCollegate()", sqle);
		}
	}
	
	public Set<Connessione> getAllConnessioni(Map<Integer, Fermata> fermateById)
	{
		String sqlQuery = "SELECT id_connessione, id_linea, id_stazP, id_stazA FROM connessione WHERE id_stazP > id_stazA";
		
		Set<Connessione> set = new HashSet<>();

		try
		{
			Connection connection = DBConnect.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet queryResult = statement.executeQuery();
			
			
			while(queryResult.next())
			{
				int id_connessione = queryResult.getInt("id_connessione");
				int id_partenza = queryResult.getInt("id_stazP");
				
				Fermata fermataPartenza = fermateById.get(id_partenza);
				
				int id_arrivo = queryResult.getInt("id_stazA");
				Fermata fermataArrivo = fermateById.get(id_arrivo);
				
				Connessione newConnessione = new Connessione(id_connessione, null, fermataPartenza, fermataArrivo);
				set.add(newConnessione);
			}
			
			queryResult.close();
			statement.close();
			connection.close();
		}
		catch(SQLException sqle)
		{
			throw new RuntimeException("Error DAO in getAllConnessioni()", sqle);
		}
		
		return set;
	}

}
