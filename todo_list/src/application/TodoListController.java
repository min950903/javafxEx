package application;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import application.dto.Todo;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TodoListController implements Initializable {
	@FXML
	private BorderPane todayPane;

	@FXML
	private BorderPane upcomingPane;

	@FXML
	private BorderPane allTodoPane;
	
	@FXML
	private TableView<Todo> todayTable;

	@FXML
	private TableColumn<Todo, String> todayTitle;

	@FXML
	private TableColumn<Todo, String> todayState;
	
	@FXML
	private TableView<Todo> upcomingTable;

	@FXML
	private TableColumn<Todo, String> upcomingTitle;
	
	@FXML
	private TableColumn<Todo, String> upcomingState;

	@FXML
	private TableColumn<Todo, Date> upcomingDate;

	@FXML
	private TableView<Todo> allTodoTable;

	@FXML
	private TableColumn<Todo, String> allTodoTitle;

	@FXML
	private TableColumn<Todo, String> allTodoState;
	
	@FXML
	private TableColumn<Todo, Date> allTodoDate;
	
	private ObservableList<Todo> todoList = null;
	private Controller controller = new Controller();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getTodayTodoList();
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
		
		getUpcomingTodoList();
	}

	@FXML
	void showAllTodoPane(ActionEvent event) {
		todayPane.setVisible(false);
		upcomingPane.setVisible(false);
		allTodoPane.setVisible(true);
		
		getAllTodoList();
	}
	
	@FXML
	public void getTodayTodoList() {
		todayTitle.setCellValueFactory(new PropertyValueFactory<Todo, String>("title"));
		todayState.setCellValueFactory(new PropertyValueFactory<Todo, String>("state"));
		
		todoList = DbConnection.getTodayList(controller.getUserId());
		todayTable.setItems(todoList);
	}
	
	@FXML
	public void getUpcomingTodoList() {
		upcomingTitle.setCellValueFactory(new PropertyValueFactory<Todo, String>("title"));
		upcomingState.setCellValueFactory(new PropertyValueFactory<Todo, String>("state"));
		upcomingDate.setCellValueFactory(new PropertyValueFactory<Todo, Date>("date"));
		
		todoList = DbConnection.getUpcomingList(controller.getUserId());
		upcomingTable.setItems(todoList);
	}
	
	@FXML
	public void getAllTodoList() {
		allTodoTitle.setCellValueFactory(new PropertyValueFactory<Todo, String>("title"));
		allTodoState.setCellValueFactory(new PropertyValueFactory<Todo, String>("state"));
		allTodoDate.setCellValueFactory(new PropertyValueFactory<Todo, Date>("date"));
		
		todoList = DbConnection.getAllTodoList(controller.getUserId());
		allTodoTable.setItems(todoList);
	}
	
	@FXML
	void showTodoItem(ActionEvent event) throws Exception {
		Stage custom = new Stage(StageStyle.UTILITY);
		custom.initModality(Modality.WINDOW_MODAL);
		
		Parent todoItem = FXMLLoader.load(getClass().getResource("layout/todo_item.fxml"));
		Scene scene = new Scene(todoItem);
		custom.setScene(scene);
		custom.setTitle("Todo Item");
		custom.showAndWait();
		
		getTodayTodoList();
	}
}
