package prockot.sos4.view;
import javafx.scene.control.TextField;

public class ValidTextField extends TextField
{	
	public ValidTextField(String anEntryRegex, int anEntryLength)
	{		
		entryRegex = anEntryRegex;
		entryLength = anEntryLength;
		
		limitTextFieldInput();
	}
	
	private String entryRegex;
	private int entryLength;
	
	private void limitTextFieldInput()
	{
		textProperty().addListener((obj, oldText, newText) -> 
		{
			if (!newText.isEmpty())
			{
				if (!(newText.substring(newText.length() - 1)).matches(entryRegex)
					|| newText.length() > entryLength)
				{
					setText(oldText);
				}
			}
     	});
	}
}