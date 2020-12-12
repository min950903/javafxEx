package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.dto.Todo;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Controller implements Initializable {
	@FXML
	private PasswordField signUpPw;

	@FXML
	private BorderPane loginPane;

	@FXML
	private BorderPane signUpPane;

	@FXML
	private BorderPane allTodoPane;

	@FXML
	private BorderPane todayPane;

	@FXML
	private BorderPane upcomingPane;

	@FXML
	private TextField loginId;

	@FXML
	private Button btnToSignUp;

	@FXML
	private Button btnLogin;

	@FXML
	private TextField signUpName;

	@FXML
	private Button btnSignUp;

	@FXML
	private PasswordField loginPw;

	@FXML
	private Button btnToLogin;

	@FXML
	private TextField signUpId;

	@FXML
	private TableView<Todo> todayTable;

	@FXML
	private TableColumn<Todo, String> todayTitle;

	@FXML
	private TableColumn<Todo, Character> todayState;

	@FXML
	private TableView<Todo> allTodoTable;

	@FXML
	private TableColumn<Todo, String> allTodoTitle;

	@FXML
	private TableColumn<Todo, Character> allTodoState;

	@FXML
	private TableColumn<Todo, Date> allTodoDate;

	@FXML
	private TableView<Todo> upcomingTable;

	@FXML
	private TableColumn<Todo, String> upcomingTitle;

	@FXML
	private TableColumn<Todo, Date> upcomingDate;

	Connection connection = null;
	ResultSet resultSet = null;
	PreparedStatement preparedStatement = null;
	ObservableList<Todo> todayList = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	@FXML
	public void login(ActionEvent event) {
		connection = DbConnection.connectDb();
		String sql = "SELECT * FROM USER WHERE ID=? AND PASSWORD=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, loginId.getText());
			preparedStatement.setString(2, loginPw.getText());

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				JOptionPane.showMessageDialog(null, "ID and PW is Correct");
				goToTodo(event);
				System.out.println(todayPane);
				//getTodayTodoList();
			} else {
				JOptionPane.showMessageDialog(null, "Invalide ID or PW");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		System.out.println(todayPane);
	}

	@FXML
	public void signUp(ActionEvent event) {
		connection = DbConnection.connectDb();
		String sql = "INSERT INTO USER VALUES(?, ?, ?)";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, signUpId.getText());
			preparedStatement.setString(2, signUpPw.getText());
			preparedStatement.setString(3, signUpName.getText());

			int result = preparedStatement.executeUpdate();
			if (result > 0) {
				JOptionPane.showMessageDialog(null, "Success Sign Up");

				signUpPane.setVisible(false);
				loginPane.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "Fail Sign Up");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	public void getTodayTodoList() {
		todayTitle.setCellValueFactory(new PropertyValueFactory<Todo, String>("title"));
		todayState.setCellValueFactory(new PropertyValueFactory<Todo, Character>("state"));
		
		todayList = DbConnection.getTodoayList();
		todayTable.setItems(todayList);
	}

	@FXML
	void showSignUpPane(ActionEvent event) {
		signUpPane.setVisible(true);
		loginPane.setVisible(false);
	}

	@FXML
	void showLoginPane(ActionEvent event) {
		signUpPane.setVisible(false);
		loginPane.setVisible(true);
	}

	@FXML
	void showTodayPane(ActionEvent event) {
		todayPane.setVisible(true);
		upcomingPane.setVisible(false);
		allTodoPane.setVisible(false);
		
		getTodayTodoList();
	}

	@FXML
	void showUpcomingPane(ActionEvent event) {
		todayPane.setVisible(false);
		upcomingPane.setVisible(true);
		allTodoPane.setVisible(false);
	}

	@FXML
	void showAllTodoPane(ActionEvent event) {
		todayPane.setVisible(false);
		upcomingPane.setVisible(false);
		allTodoPane.setVisible(true);
	}

	@FXML
	void goToTodo(ActionEvent event) throws Exception {
		Parent todoList = FXMLLoader.load(getClass().getResource("todo_list.fxml"));
		StackPane root = (StackPane) btnLogin.getScene().getRoot();
		root.getChildren().add(todoList);
	}

	@FXML
	void openTodoItemScene(ActionEvent event) throws Exception {
		Parent item = FXMLLoader.load(getClass().getResource("todo_item.fxml"));

		Stage stage = new Stage();
		stage.setScene(new Scene(item));
		stage.show();
	}
}
