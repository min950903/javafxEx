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
			JOptionPane.showMessageDialog(null, "��Ͽ� ���� �߽��ϴ�.");
		} else {
			JOptionPane.showMessageDialog(null, "�� ĭ�� ��� �Է����ּ���");
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
			JOptionPane.showMessageDialog(null, "������Ʈ�� ���� �߽��ϴ�.");
		} else {
			JOptionPane.showMessageDialog(null, "�� ĭ�� ��� �Է����ּ���");
		}
		return result;
	}
	
	public int deleteTodo(Todo todo) {
		int result = 0;
		if(0 < todo.getNo()) {
			result = dbConnection.deleteTodo(todo);
			if(result > 0) {return result;}
			JOptionPane.showMessageDialog(null, "������Ʈ�� ���� �߽��ϴ�.");
		} else {
			JOptionPane.showMessageDialog(null, "�� ĭ�� ��� �Է����ּ���");
		}
		return result;
	}
	
	public String convertState(String state) {
		switch (state) {
		case "�̿Ϸ�":
			return "R";
		case "������":
			return "I";
		case "�Ϸ�":
			return "F";
		default:
			return "Occure Error";
		}

	}
}
