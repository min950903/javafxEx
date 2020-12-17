package application.list;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import application.app.Controller;
import application.dto.Todo;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TodoListController implements Initializable {
	@FXML
	private AnchorPane todoListPane;

	@FXML
	private BorderPane todayPane, upcomingPane, allTodoPane;

	@FXML
	private Label userName;

	@FXML
	private Button logoutBtn;

	@FXML
	private TableView<Todo> todayTable, upcomingTable, allTodoTable;

	@FXML
	private TableColumn<Todo, String> todayTitle, todayState;

	@FXML
	private TableColumn<Todo, String> upcomingTitle, upcomingState;
	
	@FXML
	private TableColumn<Todo, Date> upcomingDate;

	@FXML
	private TableColumn<Todo, String> allTodoTitle, allTodoState;

	@FXML
	private TableColumn<Todo, Date> allTodoDate;
	
	private ObservableList<Todo> todoList = null;
	private Controller controller = new Controller();
	private TodoListService service = new TodoListService();
	private BorderPane currentPane = null;

	private static Todo updateTodo = null;

	public Todo getUpdateTodo() {
		return TodoListController.updateTodo;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getTodayTodoList();
		userName.setText(controller.getUserName() + "´Ô ¹Ý°©½À´Ï´Ù.");
	}

	@FXML
	void onLogout(ActionEvent event) {
		controller.getStackPane().getChildren().remove(2);
	}

	@FXML
	void showTodayPane(ActionEvent event) {
		currentPane = todayPane;

		todayPane.setVisible(true);
		upcomingPane.setVisible(false);
		allTodoPane.setVisible(false);

		getList();
	}

	@FXML
	void showUpcomingPane(ActionEvent event) {
		currentPane = upcomingPane;
		
		todayPane.setVisible(false);
		upcomingPane.setVisible(true);
		allTodoPane.setVisible(false);
		
		getList();
	}
	
	@FXML
	void showAllTodoPane(ActionEvent event) {
		currentPane = allTodoPane;
		
		todayPane.setVisible(false);
		upcomingPane.setVisible(false);
		allTodoPane.setVisible(true);

		getList();
	}
	
	@FXML
	void showTodoItem(ActionEvent event) throws Exception {
		if(null == currentPane) {
			currentPane = todayPane;
		}
		Stage custom = new Stage(StageStyle.UTILITY);
		custom.initModality(Modality.WINDOW_MODAL);
		
		Parent todoItem = FXMLLoader.load(getClass().getResource("../layout/todo_item.fxml"));
		Scene scene = new Scene(todoItem);
		custom.setScene(scene);
		custom.setTitle("Todo Item");
		custom.showAndWait();
		
		getList();
	}
	
	private void getList() {
		if(currentPane.equals(todayPane)) {
			getTodayTodoList();
		} else if(currentPane.equals(upcomingPane)) {
			getUpcomingTodoList();
		} else {
			getAllTodoList();
		}
	}
	
	@FXML
	public void getTodayTodoList() {
		todayTitle.setCellValueFactory(new PropertyValueFactory<Todo, String>("title"));
		todayState.setCellValueFactory(new PropertyValueFactory<Todo, String>("state"));

		todoList = service.getTodoList(new Todo(controller.getUserId()), "today");
		todayTable.setItems(todoList);
	}

	@FXML
	public void getUpcomingTodoList() {
		upcomingTitle.setCellValueFactory(new PropertyValueFactory<Todo, String>("title"));
		upcomingState.setCellValueFactory(new PropertyValueFactory<Todo, String>("state"));
		upcomingDate.setCellValueFactory(new PropertyValueFactory<Todo, Date>("date"));

		todoList = service.getTodoList(new Todo(controller.getUserId()), "upcoming");
		upcomingTable.setItems(todoList);
	}
	
	@FXML
	public void getAllTodoList() {
		allTodoTitle.setCellValueFactory(new PropertyValueFactory<Todo, String>("title"));
		allTodoState.setCellValueFactory(new PropertyValueFactory<Todo, String>("state"));
		allTodoDate.setCellValueFactory(new PropertyValueFactory<Todo, Date>("date"));

		todoList = service.getTodoList(new Todo(controller.getUserId()), "allTodo");
		allTodoTable.setItems(todoList);
	}

	@FXML
	void selectTodayRow(MouseEvent event) throws IOException {
		updateTodo = todayTable.getSelectionModel().getSelectedItem();
		if(null == updateTodo ) {return;}

		Stage updateDialog = getDialog(updateTodo);
		updateDialog.showAndWait();

		getTodayTodoList();
	}

	@FXML
	void selectUpcomingRow(MouseEvent event) throws IOException {
		updateTodo = upcomingTable.getSelectionModel().getSelectedItem();
		if(null == updateTodo ) {return;}

		Stage updateDialog = getDialog(updateTodo);
		updateDialog.showAndWait();

		getUpcomingTodoList();
	}

	@FXML
	void selectAllTodoRow(MouseEvent event) throws IOException {
		updateTodo = allTodoTable.getSelectionModel().getSelectedItem();
		if(null == updateTodo ) {return;}

		Stage updateDialog = getDialog(updateTodo);
		updateDialog.showAndWait();
		
		getAllTodoList();
	}

	public Stage getDialog(Todo todo) throws IOException {
		Stage updateDialog = new Stage(StageStyle.UTILITY);
		updateDialog.initModality(Modality.WINDOW_MODAL);

		Parent todoItem = FXMLLoader.load(getClass().getResource("../layout/todo_update.fxml"));
		Scene scene = new Scene(todoItem);
		updateDialog.setScene(scene);
		updateDialog.setTitle("Todo Update");
		updateDialog.setUserData(todo);

		return updateDialog;
	}
}
