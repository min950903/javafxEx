package application;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.swing.JOptionPane;

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
			+ ",CASE STATE WHEN 'R' THEN '미완료' WHEN 'I' THEN '실행중' WHEN 'F' THEN '완료' END AS STATE"
			+ ",DATE FROM TODO ";
	
	
	private static Connection connection = connectDb();
	private static PreparedStatement preparedStatement= null;

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

	public static ObservableList<User> selectUser(User user) {
		ObservableList<User> userList = FXCollections.observableArrayList();
		String sql = "SELECT * FROM USER WHERE ID=? AND PASSWORD=?";

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getId());
			preparedStatement.setString(2,user.getPw());
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				userList.add(new User(resultSet.getString("ID"), resultSet.getString("PASSWORD"), resultSet.getString("NAME")));
			}

			return userList;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);

			return null;
		}
	}
	
	public static boolean addUser(User user) {
		boolean isSuccess = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO USER ");
		sql.append("VALUES (?, ?, ?)");

		try {
			preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setString(1, user.getId());
			preparedStatement.setString(2, user.getPw());
			preparedStatement.setString(3, user.getName());
			
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

	public static ObservableList<Todo> getTodayList(String id) {
		String sql = SELECT_SQL + "WHERE DATE(DATE) = CURDATE() AND USER_ID=?";
		return getTodoList(id, sql);
	}
	
	public static ObservableList<Todo> getUpcomingList(String id) {
		String sql = SELECT_SQL + "WHERE DATE(DATE) > CURDATE() AND USER_ID=?";
		return getTodoList(id, sql);
	}
	
	public static ObservableList<Todo> getAllTodoList(String id) {
		String sql = SELECT_SQL + "WHERE USER_ID=?";
		return getTodoList(id, sql);
	}
	
	public static ObservableList<Todo> getTodoList(String id, String sql) {
		ObservableList<Todo> todoList = FXCollections.observableArrayList();
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
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
			preparedStatement = connection.prepareStatement(sql.toString());
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
	
	public static boolean updateTodo(Todo todo ) {
		boolean isSuccess = false;
		
		String sql = "UPDATE TODO SET TITLE=?, DESCRIPTION=?, STATE=?, DATE=? WHERE NO=?";

		try {
			preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setString(1, todo.getTitle());
			preparedStatement.setString(2, todo.getDesc());
			preparedStatement.setString(3, todo.getState());
			preparedStatement.setDate(4, todo.getDate());
			preparedStatement.setInt(5, todo.getNo());
			
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
	
	public static boolean deleteTodo(Todo todo ) {
		boolean isSuccess = false;
		
		String sql = "DELETE FROM TODO WHERE NO=?";

		try {
			preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setInt(1, todo.getNo());
			
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
