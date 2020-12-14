package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.dto.Todo;
import javafx.collections.FXCollections;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controller implements Initializable {
	@FXML
	private BorderPane loginPane, signUpPane;

	@FXML
	private TextField loginId;

	@FXML
	private PasswordField loginPw ;
	
	@FXML
	private Button btnLogin;

	@FXML
	private TextField signUpName, signUpId;
	
	@FXML
	private PasswordField signUpPw;

	@FXML
	private Button btnSignUp;

	@FXML
	private Button btnToLogin, btnToSignUp;

	private static String userId = null;
	
	public String getUserId() {
		return this.userId;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	@FXML
	public void login(ActionEvent event) {
		boolean isLoginSuccess = DbConnection.getUser(loginId.getText(), loginPw.getText());
		if(isLoginSuccess) {
			userId = loginId.getText();
			JOptionPane.showMessageDialog(null, "Login Success");
			try {
				goToTodo(event);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Login Fail");
		}
	}

	@FXML
	public void signUp(ActionEvent event) {
		/*
		 * connection = DbConnection.connectDb(); String sql =
		 * "INSERT INTO USER VALUES(?, ?, ?)"; try { preparedStatement =
		 * connection.prepareStatement(sql); preparedStatement.setString(1,
		 * signUpId.getText()); preparedStatement.setString(2, signUpPw.getText());
		 * preparedStatement.setString(3, signUpName.getText());
		 * 
		 * int result = preparedStatement.executeUpdate(); if (result > 0) {
		 * JOptionPane.showMessageDialog(null, "Success Sign Up");
		 * 
		 * signUpPane.setVisible(false); loginPane.setVisible(true); } else {
		 * JOptionPane.showMessageDialog(null, "Fail Sign Up"); } } catch (Exception e)
		 * { JOptionPane.showMessageDialog(null, e); }
		 */
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
	void goToTodo(ActionEvent event) throws Exception {
		Parent todoList = FXMLLoader.load(getClass().getResource("layout/todo_list.fxml"));
		StackPane root = (StackPane) btnLogin.getScene().getRoot();
		root.getChildren().add(todoList);
	}
}
