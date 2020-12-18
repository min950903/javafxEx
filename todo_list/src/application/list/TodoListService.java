package application.list;

import application.common.AlertImpl;
import application.common.DbConnection;
import application.dto.Todo;
import javafx.collections.ObservableList;

public class TodoListService {
	private DbConnection dbconnetcion = new DbConnection();
	
	public ObservableList<Todo> getTodoList(Todo todo, String tableName) {
		String whereSql = converSql(tableName);
		ObservableList<Todo> list =  dbconnetcion.selectTodoList(todo, whereSql);
		
		if(null == list) {
			AlertImpl.ErrorAlert();
		} else {
			if(list.size() > 0) {return list;}
		}
		
		return null;
	}
	
	private String converSql(String tableName) {
		switch(tableName) {
		case "today": 
			return "WHERE DATE(DATE) = CURDATE() AND USER_ID=? ORDER BY DATE";
		case "upcoming" :
			return "WHERE DATE(DATE) > CURDATE() AND USER_ID=? ORDER BY DATE";
		default :
			return "WHERE USER_ID=? ORDER BY DATE";
		}
	}
	
}
