package application;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ScenarioEditor extends Stage {

	public TextField nameTF;
	public TextArea descTA;
	
	public ScenarioEditor()
	{
		super(StageStyle.UTILITY);
		initModality(Modality.APPLICATION_MODAL);
		setResizable(false);
		centerOnScreen();
		
		setOnCloseRequest(windowEvent->{
			hide();
		});
	}

}
