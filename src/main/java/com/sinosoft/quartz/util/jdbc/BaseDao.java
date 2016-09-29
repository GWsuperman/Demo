package com.sinosoft.quartz.util.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface BaseDao  {
	public Connection getConnection();

	public void closeConnection(Connection connection,ResultSet resultSet,PreparedStatement preparedStatement) throws Exception;
}
