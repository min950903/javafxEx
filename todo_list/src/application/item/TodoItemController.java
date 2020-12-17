package application.item;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import application.app.Controller;
import application.common.AlertImpl;
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

public class TodoItemController implements Initializable {
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
	private Button addBtn;

	@FXML
	private Button cancleBtn;

	private ObservableList<String> comboBoxList = FXCollections.observableArrayList("미완료", "진행중", "완료");
	private Controller controller = new Controller();
	private TodoItemService todoItemService = new TodoItemService();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		itemState.setItems(comboBoxList);
		itemState.getSelectionModel().selectFirst();
	}

	@FXML
	void addItem(ActionEvent event) {
		if(null == itemDate.getValue()) {
			AlertImpl.checkAlert();
		} else {
			int result = todoItemService.addTodo(
					new Todo(itemTitle.getText()
							, itemDesc.getText()
							, Date.valueOf(itemDate.getValue())
							, itemState.getValue()
							, controller.getUserId()));
			if (result > 0) {
				AlertImpl.successAlert();
				closeDialog(event);
			}
		}
	}

	@FXML
	void closeDialog(ActionEvent event) {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	

}
