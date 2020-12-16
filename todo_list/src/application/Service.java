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
			
			JOptionPane.showMessageDialog(null, "���̵�� ��й�ȣ�� Ȯ�����ּ���");
		} else {
			JOptionPane.showMessageDialog(null, "���̵� �Ǵ� ��й�ȣ�� �Է����ּ���");
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
			
			JOptionPane.showMessageDialog(null, "ȸ�������� �����߽��ϴ�.");
		} else {
			JOptionPane.showMessageDialog(null, "�� ĭ�� ��� �Է����ּ���.");
		}
		
		return isSuccess;
	}
}
