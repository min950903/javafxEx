package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertImpl {
	public static Alert loginAlert() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Check Input Value");
		alert.setHeaderText("Warning!!");
		alert.setContentText("���̵�� ��й�ȣ�� Ȯ�����ּ���");
		alert.showAndWait();
		
		return alert;
	}
	
	public static Alert checkAlert() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Check Input Value");
		alert.setHeaderText("Warning!!");
		alert.setContentText("��ĭ�� ��� �Է��ϼ���");
		alert.showAndWait();
		
		return alert;
	}
	
	public static Alert ErrorAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Occure Error");
		alert.setContentText("������ �߻��߽��ϴ�.");
		alert.showAndWait();
		
		return alert;
	}
	
	public static Alert successAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Sucess Change");
		alert.setContentText("��������� ������Ʈ �Ǿ����ϴ�.");
		alert.showAndWait();
		
		return alert;
	}
}
