package application;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.dto.Todo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
	
	private ObservableList<String> comboBoxList = 
			FXCollections.observableArrayList("미완료", "실행중", "완료");
	private Controller controller = new Controller();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		itemState.setItems(comboBoxList);
		itemState.getSelectionModel().selectFirst();
	}
	
	@FXML
    void addItem(ActionEvent event) {
		
		Todo todo = new Todo(
				itemTitle.getText()
				,itemDesc.getText()
				,Date.valueOf(itemDate.getValue())
				,convertState(itemState.getValue())
				,controller.getUserId());
		
		boolean isSuccess = DbConnection.addTodo(todo);
		if(isSuccess) {
			JOptionPane.showMessageDialog(null, "Insert Success");
		} else {
			JOptionPane.showMessageDialog(null, "Insert Fail");
		}
		
		closeDialog(event);
    }

    @FXML
    void closeDialog(ActionEvent event) {
    	Node source = (Node) event.getSource();
    	Stage stage = (Stage) source.getScene().getWindow();
    	stage.close();
    }
    
    public String convertState(String state) {
    	switch (state) {
		case "미완료": return "R";
		case "진행중": return "I";
		case "완료": return "F";
		default:
			return "Occure Error";
		}
    	
    }

}
