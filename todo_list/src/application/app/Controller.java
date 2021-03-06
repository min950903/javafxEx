package application.app;

import java.net.URL;
import java.util.ResourceBundle;

import application.dto.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class Controller implements Initializable {
	@FXML
	private BorderPane loginPane, signUpPane;

	@FXML
	private TextField loginId;

	@FXML
	private PasswordField loginPw;

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
	private static String userName = null;
	private static StackPane rootStackPane = null;
	private Service service = new Service();

	public StackPane getStackPane() {
		return Controller.rootStackPane;
	}

	public String getUserId() {
		return Controller.userId;
	}

	public String getUserName() {
		return Controller.userName;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	@FXML
	public void login(ActionEvent event) {
		try {
			User user = service.getUser(new User(loginId.getText(), loginPw.getText()));
			if (null != user) {
				userId = user.getId();
				userName = user.getName();
				
				goToTodo(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void signUp(ActionEvent event) {
		try {
			int result = service.addUser(new User(signUpId.getText(), signUpPw.getText(), signUpName.getText()));
			if (result > 0) {
				showLoginPane(event);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
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
		Parent todoList = FXMLLoader.load(getClass().getResource("../layout/todo_list.fxml"));
		rootStackPane = (StackPane) btnLogin.getScene().getRoot();
		rootStackPane.getChildren().add(todoList);

		loginId.clear();
		loginPw.clear();
	}
}
