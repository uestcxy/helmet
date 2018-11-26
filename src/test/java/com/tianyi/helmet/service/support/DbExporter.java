package com.tianyi.helmet.service.support;

import com.tianyi.helmet.BaseServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;


public class DbExporter extends BaseServiceTest{
	@Autowired
	private DataSource dataSource;

	@Test
	public void exportStruct(){
		Connection con = null;
		try {
			con = dataSource.getConnection();
			DatabaseMetaData dbmd = con.getMetaData();
			ResultSet rs = dbmd.getTables(null,"hmserver",null,null);
			while(rs.next()){
				String tabName = rs.getString("TABLE_NAME");
				System.out.println("\t"+tabName.toUpperCase());
				ResultSet rs2 = dbmd.getColumns(null,"hmserver",tabName,null);
				System.out.println("字段,格式,长度,键,允许空,默认值,备注");
				while(rs2.next()){
					String colName = rs2.getString("COLUMN_NAME");
					System.out.println(colName+","+rs2.getString("TYPE_NAME")+","+rs2.getInt("COLUMN_SIZE")+","+ ( ("id".equalsIgnoreCase(colName)) ? "PK" :"" )+","+rs2.getString("IS_NULLABLE")+","+rs2.getString("COLUMN_DEF")+","+rs2.getString("REMARKS"));
				}
				rs2.close();
				System.out.println(", , ");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
