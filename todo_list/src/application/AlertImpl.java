package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertImpl {
	public static Alert loginAlert() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Check Input Value");
		alert.setHeaderText("Warning!!");
		alert.setContentText("아이디와 비밀번호를 확인해주세요");
		alert.showAndWait();
		
		return alert;
	}
	
	public static Alert checkAlert() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Check Input Value");
		alert.setHeaderText("Warning!!");
		alert.setContentText("빈칸을 모두 입력하세요");
		alert.showAndWait();
		
		return alert;
	}
	
	public static Alert ErrorAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Occure Error");
		alert.setContentText("오류가 발생했습니다.");
		alert.showAndWait();
		
		return alert;
	}
	
	public static Alert successAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Sucess Change");
		alert.setContentText("변경사항이 업데이트 되었습니다.");
		alert.showAndWait();
		
		return alert;
	}
}
