package com.abc.bankingApp.properties;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Value;

public class UserIdGenerator implements IdentifierGenerator{
	
	@Value("${query.userId}")
	private String query;

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Connection connection = session.connection();
		int x=100;	
		try {
			Statement createStatement = connection.createStatement();
			ResultSet resultSet = createStatement.executeQuery(query);
			if(resultSet.next()) {
				x+=resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return x;
	}

}
