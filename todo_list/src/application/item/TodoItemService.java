package application.item;

import application.common.AlertImpl;
import application.common.DbConnection;
import application.dto.Todo;

public class TodoItemService {
	private DbConnection dbConnection = new DbConnection();
	
	public int addTodo(Todo todo) {
		int result = 0;
		if(todo.getTitle().equals("") 
				|| todo.getDesc().equals("") 
				|| todo.getState().equals("") 
				|| null == todo.getDate() 
				|| todo.getUserId().equals("")) {
			AlertImpl.checkAlert();
		} else {
			todo.setState(convertState(todo.getState()));
			
			result = dbConnection.insertTodo(todo);
			if(result > 0) {return result;}
			AlertImpl.ErrorAlert();
		}
		return result;
	}
	
	public int updateTodo(Todo todo) {
		int result = 0;
		if(todo.getNo() <= 0
				|| todo.getTitle().equals("") 
				|| todo.getDesc().equals("") 
				|| todo.getState().equals("") 
				|| null == todo.getDate()) {
			AlertImpl.checkAlert();
		} else {
			todo.setState(convertState(todo.getState()));
			
			result = dbConnection.updateTodo(todo);
			if(result > 0) {return result;}
			AlertImpl.ErrorAlert();
		}
		return result;
	}
	
	public int deleteTodo(Todo todo) {
		int result = 0;
		if(todo.getNo() <= 0) {
			AlertImpl.checkAlert();
		} else {
			result = dbConnection.deleteTodo(todo);
			if(result > 0) {return result;}
			AlertImpl.ErrorAlert();
		}
		return result;
	}
	
	public String convertState(String state) {
		switch (state) {
		case "미완료":
			return "R";
		case "진행중":
			return "I";
		case "완료":
			return "F";
		default:
			return "Occure Error";
		}
	}
}
