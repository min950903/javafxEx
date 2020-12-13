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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DbConnection {

	private static String DATABASE_URL;
	private static String DATABASE_USERNAME;
	private static String DATABASE_PASSWORD;

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
		Connection connection = null;
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
		Connection connection = connectDb();
		String sql = "SELECT * FROM USER WHERE ID=? AND PASSWORD=?";

		boolean isSuccess = false;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, pw);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return !isSuccess;
			}

			return isSuccess;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);

			return isSuccess;
		}
	}

	public static ObservableList<Todo> getTodayList(String id) {
		Connection connection = connectDb();
		ObservableList<Todo> list = FXCollections.observableArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TITLE, DATE_FORMAT(DATE, '%Y-%m-%d') AS DATE");
		sql.append("FROM TODO");
		sql.append("WHERE DATE(DATE) = CURDATE() AND ID=?");

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setString(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				list.add(new Todo(resultSet.getString(0), resultSet.getString(1).charAt(0)));
			}

			return list;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);

			return null;
		}
	}
}
