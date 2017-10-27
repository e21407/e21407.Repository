package com.lbc.util;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBConnector {
	Reader reader;
	SqlSessionFactory sqlSessionFactory;
	SqlSession session;
	
	String configFilePath = "mybatis-conf.xml";
	
	static DBConnector INSTANCE = new DBConnector();
	
	private DBConnector() {
		try {
			reader = Resources.getResourceAsReader(configFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		session = sqlSessionFactory.openSession();
	}
	
	public static DBConnector getDBConnectorInstance() {
		return INSTANCE;
	}
	
	public <T> T getMapper(Class<T> type) {
		return session.getMapper(type);
	}
	
	public void commit() {
		session.commit();
	}
}
