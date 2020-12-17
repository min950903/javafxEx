package application;

import java.io.File;
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
	
	public User getUser(User user) throws Exception {
		if(user.getId().equals("") || user.getPw().equals("")) {
			AlertImpl.checkAlert();
		} else {
			user.setPw(cryptogram.encrypt(user.getPw()));
			ObservableList<User> userList = dbConnection.selectUser(user);
			if(userList != null) {return userList.get(0);}
			
			AlertImpl.loginAlert();
		}
		
		return null;
	}
	
	public int addUser(User user) throws Exception {
		int result = 0; 
		
		if(user.getId().equals("") 
				|| user.getPw().equals("")
				|| user.getName().equals("")) {
			AlertImpl.checkAlert();
		} else {
			user.setPw(cryptogram.encrypt(user.getPw()));
			result = dbConnection.insertUser(user);
			if(result > 0) {return result;}
			
			AlertImpl.ErrorAlert();
		}
		
		return result;
	}
}
