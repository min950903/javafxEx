package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;

import application.dto.User;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public User getUser(User user) {
		if((user.getId() != null) && (user.getPw() != null)) {
			DbConnection.selectUser(user);
		}
		return null;
	}

}
