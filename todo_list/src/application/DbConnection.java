package application;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.swing.JOptionPane;

import application.dto.Todo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DbConnection {

	private static String DATABASE_URL;
	private static String DATABASE_USERNAME;
	private static String DATABASE_PASSWORD;
	private static Connection connection = connectDb();
	
	private static void getProperties() throws Exception {
		File propertyPath = new File("src/application/dbInfo.properties");
		FileReader file = new FileReader(propertyPath);

		Properties properties = new Properties();
		properties.load(file);
		DATABASE_URL = properties.getProperty("MYSQL_DATABASE_URL");
		DATABASE_USERNAME = properties.getProperty("MYSQL_DATABASE_USERNAME");
		DATABASE_PASSWORD = properties.getProperty("MYSQL_DATABASE_PASSWORD");
	}

	public static Connection connectDb() {
		try {
			getProperties();
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

			return connection;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	public static boolean getUser(String id, String pw) {
		String sql = "SELECT * FROM USER WHERE ID=? AND PASSWORD=?";

		boolean isSuccess = false;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, pw);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {return !isSuccess;}

			return isSuccess;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);

			return isSuccess;
		}
	}

	public static ObservableList<Todo> getTodayList(String id) {
		ObservableList<Todo> list = FXCollections.observableArrayList();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TITLE, CASE STATE WHEN 'R' THEN '미완료' WHEN 'I' THEN '실행중' WHEN 'F' THEN '완료' END AS STATE ");
		sql.append("FROM TODO ");
		sql.append("WHERE DATE(DATE) = CURDATE() AND USER_ID=?");

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setString(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				list.add(new Todo(resultSet.getString(1), resultSet.getString(2)));
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);

			return null;
		}
	}
	
	public static ObservableList<Todo> getUpcomingList(String id) {
		ObservableList<Todo> list = FXCollections.observableArrayList();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TITLE, CASE STATE WHEN 'R' THEN '미완료' WHEN 'I' THEN '실행중' WHEN 'F' THEN '완료' END AS STATE, DATE ");
		sql.append("FROM TODO ");
		sql.append("WHERE DATE(DATE) > CURDATE() AND USER_ID=?");

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setString(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				list.add(new Todo(resultSet.getString(1), resultSet.getString(2), resultSet.getDate(3)));
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);

			return null;
		}
	}
	
	public static ObservableList<Todo> getAllTodoList(String id) {
		ObservableList<Todo> list = FXCollections.observableArrayList();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TITLE, CASE STATE WHEN 'R' THEN '미완료' WHEN 'I' THEN '실행중' WHEN 'F' THEN '완료' END AS STATE, DATE ");
		sql.append("FROM TODO ");
		sql.append("WHERE USER_ID=?");

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setString(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				list.add(new Todo(resultSet.getString(1), resultSet.getString(2), resultSet.getDate(3)));
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);

			return null;
		}
	}
	
	public static boolean addTodo(Todo todo ) {
		boolean isSuccess = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO TODO (TITLE, DESCRIPTION, DATE, STATE, USER_ID) ");
		sql.append("VALUES (?, ?, ?, ?, ?)");

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setString(1, todo.getTitle());
			preparedStatement.setString(2, todo.getDesc());
			preparedStatement.setDate(3, todo.getDate());
			preparedStatement.setString(4, todo.getState());
			preparedStatement.setString(5, todo.getUserId());
			
			int result = preparedStatement.executeUpdate();
			if(result > 0) {
				return !isSuccess;
			}
			
			return isSuccess;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
			
			return isSuccess;
		}
	}
}
