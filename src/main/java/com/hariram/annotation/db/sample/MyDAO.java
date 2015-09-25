/**
 * 
 */
package com.hariram.annotation.db.sample;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hariram.annotation.db.DAO;
import com.hariram.annotation.db.DB;

/**
 * Sample DAO class that has DB annotation
 * 
 * @author hariram
 * date 14-Nov-2014
 */
@DB(connUrl="jdbc:mysql://192.168.1.1", userName="username", password="password", driverName="com.mysql.jdbc.Driver")
public class MyDAO extends DAO {
	private static MyDAO MYDAO = new MyDAO();
	/**
	 * Private constructor as following singleton pattern
	 */
	private MyDAO() {
	}
	
	/**
	 * Returns the singleton instance of the class
	 * 
	 * @return instance of the class
	 */
	public static MyDAO getInstance() {
		return MYDAO;
	}

	/**
	 * Return the list of table names in the particular db
	 * 
	 * @return Object list of table names
	 */
	public Object getData() {
		Map<String, List<String>> map = new HashMap<String, List<String>>();

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SHOW TABLES");

			while (rs.next()) {
				int colCount = rs.getMetaData().getColumnCount();
				String col1 = rs.getString(1);
				List<String> colValues = new ArrayList<String>();
				if(colCount > 1) {
					for(int i = 2; i <= colCount; i++) {
						String col = rs.getString(i);
						colValues.add(col);
					}
				}
				map.put(col1, colValues);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
}
