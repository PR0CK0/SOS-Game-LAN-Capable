package prockot.sos4.view.websetup;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import prockot.sos4.view.ValidTextField;

public class IP_AddressEntryModule extends HBox
{
	public IP_AddressEntryModule()
	{
		createAndLayoutControls();
		listenToControls();
	}
	
	public String getHostIP()
	{
		String IP1 = tfIP1.getText();
		String IP2 = tfIP2.getText();
		String IP3 = tfIP3.getText();
		String IP4 = tfIP4.getText();
		
		String hostIP = IP1 + "." + IP2 + "." + IP3 + "." + IP4;
		return hostIP;
	}
	
	public SimpleBooleanProperty hasValidIP()
	{
		return allIPNumbersValid;
	}
	
	private final String entryRegex = "[0-9]";
	private final int maxIPNumberLength = 3;
	
	private SimpleBooleanProperty allIPNumbersValid;
	
	private TextField tfIP1;
	private Label lblDot1;
	private TextField tfIP2;
	private Label lblDot2;
	private TextField tfIP3;
	private Label lblDot3;
	private TextField tfIP4;
	
	private void listenToControls()
	{
		allIPNumbersValid = new SimpleBooleanProperty();
		
		allIPNumbersValid.bind(tfIP1.textProperty().isEmpty()
				.or(tfIP2.textProperty().isEmpty())
				.or(tfIP3.textProperty().isEmpty())
				.or(tfIP4.textProperty().isEmpty()));
	}
	
	private void createAndLayoutControls()
	{		
		tfIP1 = new ValidTextField(entryRegex, maxIPNumberLength);
		lblDot1 = new Label(".");
		tfIP2 = new ValidTextField(entryRegex, maxIPNumberLength);
		lblDot2 = new Label(".");
		tfIP3 = new ValidTextField(entryRegex, maxIPNumberLength);
		lblDot3 = new Label(".");
		tfIP4 = new ValidTextField(entryRegex, maxIPNumberLength);
		
		tfIP1.setMaxWidth(50);
		tfIP2.setMaxWidth(50);
		tfIP3.setMaxWidth(50);
		tfIP4.setMaxWidth(50);
		
		lblDot1.setStyle("-fx-font-size: 2em");
		lblDot2.setStyle("-fx-font-size: 2em");
		lblDot3.setStyle("-fx-font-size: 2em");
		
		getChildren().addAll(tfIP1, lblDot1, tfIP2, lblDot2, tfIP3, lblDot3, tfIP4);
		setAlignment(Pos.CENTER);
		setSpacing(10);
	}
}