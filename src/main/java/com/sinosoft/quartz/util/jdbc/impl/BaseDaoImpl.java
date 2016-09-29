package com.sinosoft.quartz.util.jdbc.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.sinosoft.quartz.util.jdbc.BaseDao;

public class BaseDaoImpl implements BaseDao {

	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;

	public Connection getConnection() {
		try {
			/************ 关联配置文件 *****************/
			Properties properties = new Properties();
			InputStream inputStream = BaseDaoImpl.class.getClassLoader()
					.getResourceAsStream("jdbc.properties");
			properties.load(inputStream);
			/*************** 驱动 *********************/
			Class.forName(properties.getProperty("jdbc.driverClass"));
			/**************** 链接字符串 ************************/
			connection = DriverManager.getConnection(
					properties.getProperty("jdbc.url"),
					properties.getProperty("jdbc.username"),
					properties.getProperty("jdbc.password"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void closeConnection(Connection connection, ResultSet resultSet,
			PreparedStatement preparedStatement) throws SQLException {
		if (connection != null) {
			connection.close();
		}
		if (resultSet != null) {
			resultSet.close();
		}
		if (preparedStatement != null) {
			preparedStatement.close();
		}
	}
}
