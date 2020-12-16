package application;

import javax.swing.JOptionPane;

import application.dto.Todo;

public class TodoItemService {
	private DbConnection dbConnection = new DbConnection();
	
	public int addTodo(Todo todo) {
		int result = 0;
		if(null != todo.getTitle() 
				&& null != todo.getDesc() 
				&& null != todo.getState() 
				&& null != todo.getDate() 
				&& null != todo.getUserId()) {
			todo.setState(convertState(todo.getState()));
			
			result = dbConnection.insertTodo(todo);
			if(result > 0) {return result;}
			JOptionPane.showMessageDialog(null, "등록에 실패 했습니다.");
		} else {
			JOptionPane.showMessageDialog(null, "빈 칸을 모두 입력해주세요");
		}
		return result;
	}
	
	public int updateTodo(Todo todo) {
		int result = 0;
		if(0 < todo.getNo()
				&& null != todo.getTitle() 
				&& null != todo.getDesc() 
				&& null != todo.getState() 
				&& null != todo.getDate()) {
			todo.setState(convertState(todo.getState()));
			
			result = dbConnection.updateTodo(todo);
			if(result > 0) {return result;}
			JOptionPane.showMessageDialog(null, "업데이트에 실패 했습니다.");
		} else {
			JOptionPane.showMessageDialog(null, "빈 칸을 모두 입력해주세요");
		}
		return result;
	}
	
	public int deleteTodo(Todo todo) {
		int result = 0;
		if(0 < todo.getNo()) {
			result = dbConnection.deleteTodo(todo);
			if(result > 0) {return result;}
			JOptionPane.showMessageDialog(null, "업데이트에 실패 했습니다.");
		} else {
			JOptionPane.showMessageDialog(null, "빈 칸을 모두 입력해주세요");
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
