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
 * @author hariram
 *
 */
@DB(connUrl="jdbc:mysql://10.30.60.225/AD21.0", userName="is2iotm", password="amma123", driverName="com.mysql.jdbc.Driver")
public class MyDAO extends DAO {
	private static MyDAO MYDAO = new MyDAO();
	private MyDAO() {
	}
	
	public static MyDAO getInstance() {
		return MYDAO;
	}

	/*@Override
	public Object process(String driverName, String connUrl, String userName,
			String password, String dbMethodName, Object[] dbMethodArgs) {
		Object returnObj = null;
		processBefore(driverName, connUrl, userName, password);
		returnObj = AnnotationUtil.callMethod(this, dbMethodName, dbMethodArgs);
		processAfter();
		return returnObj;
	}*/
	
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
