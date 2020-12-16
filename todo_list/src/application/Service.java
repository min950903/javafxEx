package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;

import javax.swing.JOptionPane;

import application.dto.User;
import javafx.collections.ObservableList;

public class Service {
	private static String ENCRYPTION_KEY = null;
	private static CryptogramImpl cryptogram = null;
	
	private DbConnection dbConnection = new DbConnection();
	
	static {
		File propertyPath = new File("src/application/dbInfo.properties");
		FileReader file;
		try {
			file = new FileReader(propertyPath);
			Properties properties = new Properties();
			properties.load(file);
			
			ENCRYPTION_KEY = properties.getProperty("ENCRYPTION_KEY");
			cryptogram = new CryptogramImpl(ENCRYPTION_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public User getUser(User user) {
		if((user.getId() != null) && (user.getPw() != null)) {
			ObservableList<User> userList = dbConnection.selectUser(user);
			if(userList != null) {return userList.get(0);}
			
			JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 확인해주세요");
		} else {
			JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 입력해주세요");
		}
		
		return null;
	}
	
	public boolean addUser(User user) {
		boolean isSuccess = false;
		if(user.getId() != null 
				&& user.getPw() != null
				&& user.getName() != null) {
			int result = dbConnection.insertUser(user);
			if(result > 0) {return !isSuccess; }
			
			JOptionPane.showMessageDialog(null, "회원가입을 실패했습니다.");
		} else {
			JOptionPane.showMessageDialog(null, "빈 칸을 모두 입력해주세요.");
		}
		
		return isSuccess;
	}
}
