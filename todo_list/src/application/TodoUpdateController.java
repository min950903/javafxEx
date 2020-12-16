package application;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.dto.Todo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TodoUpdateController implements Initializable {
	@FXML
	private BorderPane itemPane;

	@FXML
	private TextField itemTitle;

	@FXML
	private TextArea itemDesc;

	@FXML
	private DatePicker itemDate;

	@FXML
	private ComboBox<String> itemState;

	@FXML
	private Button updateBtn;

	@FXML
	private Button cancleBtn;

	private ObservableList<String> comboBoxList = FXCollections.observableArrayList("미완료", "진행중", "완료");
	private TodoListController controller = new TodoListController();
	private TodoItemService todoItemService = new TodoItemService();
	
	private Todo todo = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		todo = controller.getUpdateTodo();
		itemTitle.setText(todo.getTitle());
		itemDesc.setText(todo.getDesc());
		itemDate.setValue(todo.getDate().toLocalDate());
		itemState.setItems(comboBoxList);
		itemState.getSelectionModel().select(todo.getState());
	}

	@FXML
	void updateItem(ActionEvent event) {
		int result = todoItemService.updateTodo(
				new Todo(todo.getNo()
						, itemTitle.getText()
						, itemDesc.getText()
						, itemState.getValue()
						, Date.valueOf(itemDate.getValue())));
		
		if (result > 0) {
			JOptionPane.showMessageDialog(null, "업데이트 성공");
		}

		closeDialog(event);
	}

	@FXML
	void deleteItem(ActionEvent event) {
		int result = todoItemService.deleteTodo(new Todo(todo.getNo()));
		if (result > 0) {
			JOptionPane.showMessageDialog(null, "삭제 성공");
		}

		closeDialog(event);
	}

	@FXML
	void closeDialog(ActionEvent event) {

		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();

	}
}
