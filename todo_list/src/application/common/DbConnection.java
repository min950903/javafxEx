package application.common;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import application.dto.Todo;
import application.dto.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DbConnection {

	private static String DATABASE_URL = null;
	private static String DATABASE_USERNAME = null;
	private static String DATABASE_PASSWORD = null;
	private static String SELECT_SQL = 
			"SELECT NO, TITLE, DESCRIPTION"
			+ ",CASE STATE WHEN 'R' THEN '미완료' WHEN 'I' THEN '진행중' WHEN 'F' THEN '완료' END AS STATE"
			+ ",DATE FROM TODO ";
	
	
	private static Connection connection = connectDb();
	private PreparedStatement preparedStatement= null;

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

	public ObservableList<User> selectUser(User user) {
		ObservableList<User> userList = FXCollections.observableArrayList();
		String sql = "SELECT * FROM USER WHERE ID=? AND PASSWORD=?";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getId());
			preparedStatement.setString(2,user.getPw());
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				userList.add(new User(resultSet.getString("ID")
						, resultSet.getString("PASSWORD"), resultSet.getString("NAME")));
			}

			return userList;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}
	
	public int insertUser(User user) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO USER ");
		sql.append("VALUES (?, ?, ?)");

		int result = 0;
		try {
			preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setString(1, user.getId());
			preparedStatement.setString(2, user.getPw());
			preparedStatement.setString(3, user.getName());
			
			result = preparedStatement.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			
			return result;
		}
	}
	
	public ObservableList<Todo> selectTodoList(Todo todo, String whereSql) {
		String sql = SELECT_SQL + whereSql;
		ObservableList<Todo> todoList = FXCollections.observableArrayList();
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, todo.getUserId());
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				todoList.add(new Todo(
						resultSet.getInt("no")
						,resultSet.getString("title")
						,resultSet.getString("description")
						,resultSet.getString("state")
						,resultSet.getDate("date")));
			}
			return todoList;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}
	
	public int insertTodo(Todo todo ) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO TODO (TITLE, DESCRIPTION, DATE, STATE, USER_ID) ");
		sql.append("VALUES (?, ?, ?, ?, ?)");

		int result = 0;
		try {
			preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setString(1, todo.getTitle());
			preparedStatement.setString(2, todo.getDesc());
			preparedStatement.setDate(3, todo.getDate());
			preparedStatement.setString(4, todo.getState());
			preparedStatement.setString(5, todo.getUserId());
			
			result = preparedStatement.executeUpdate();
			if(result > 0) {return result;}
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			
			return result;
		}
	}
	
	public int updateTodo(Todo todo ) {
		String sql = "UPDATE TODO SET TITLE=?, DESCRIPTION=?, STATE=?, DATE=? WHERE NO=?";

		int result = 0;
		try {
			preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setString(1, todo.getTitle());
			preparedStatement.setString(2, todo.getDesc());
			preparedStatement.setString(3, todo.getState());
			preparedStatement.setDate(4, todo.getDate());
			preparedStatement.setInt(5, todo.getNo());
			
			result = preparedStatement.executeUpdate();
			if(result > 0) {return result;}
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			
			return result;
		}
	}
	
	public int deleteTodo(Todo todo ) {
		String sql = "DELETE FROM TODO WHERE NO=?";

		int result = 0;
		try {
			preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setInt(1, todo.getNo());
			
			result = preparedStatement.executeUpdate();
			if(result > 0) {return result;}
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			
			return result;
		}
	}
}
