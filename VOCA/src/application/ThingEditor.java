package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Thing;

public class ThingEditor extends Stage {
	
	ThingView thingview=null;
	VocaController controler=null;
	TextField nameTF=null;
	TextArea descTA=null;

	
	public ThingEditor(VocaController ctr) {
		super(StageStyle.UTILITY);
		controler=ctr;
		
		build();
	}
	
	public void build() {
		initModality(Modality.APPLICATION_MODAL);
		setResizable(false);
		centerOnScreen();
		
		//Font bigFont = Font.font("Calibri",FontWeight.BOLD,FontPosture.REGULAR,24);
		Font smallFont = Font.font("Calibri",FontWeight.BOLD,FontPosture.REGULAR,16);
		
		GridPane grid = new GridPane();
		Scene scene = new Scene(grid);
        setScene(scene);
        
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		setOnCloseRequest(windowEvent->{
			hide();
		});
	
		grid.setVgap(5);
        grid.setHgap(10);
        
        Label name = new Label("Nom");
        name.setFont(smallFont);
        GridPane.setConstraints(name, 0, 0);
        grid.getChildren().add(name);
        
        nameTF = new TextField();
        nameTF.setFont(smallFont);
        GridPane.setConstraints(nameTF, 1, 0);
        grid.getChildren().add(nameTF);
        
        Label desc = new Label("Description");
        desc.setFont(smallFont);
        GridPane.setConstraints(desc, 0, 1);
        grid.getChildren().add(desc);
        
        descTA = new TextArea();
        descTA.setFont(smallFont);
        descTA.setWrapText(true);
        GridPane.setConstraints(descTA, 1, 2);
        grid.getChildren().add(descTA);
        
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setSpacing(20);
        
        Button ok = new Button("Valider");
        //GridPane.setConstraints(ok, 0, 3);
        ok.getStyleClass().add("round-red");
		//ok.setPadding(new Insets(20));
        //grid.getChildren().add(ok);
        
        Button ko = new Button("Annuler");
        //GridPane.setConstraints(ko, 1, 3);
        ko.getStyleClass().add("round-red");
		//ko.setPadding(new Insets(20));
        //grid.getChildren().add(ko);
        
        buttons.getChildren().addAll(ok,ko);
        
        grid.add(buttons, 1, 3);
		
        ok.setOnAction(eventAction->{
        	hide();
        	controler.modifier(thingview.model,nameTF.getText(),descTA.getText());
        	thingview.changeLabel(nameTF.getText());
        });
        
        ko.setOnAction(eventAction->{
        	hide();
        });
        
	}
	
	public void show(ThingView tv) {
		thingview=tv;
		if (thingview.model.getName()!=null) nameTF.setText(thingview.model.getName());
		else nameTF.setText("");
		if (thingview.model.getDescription()!=null)descTA.setText(thingview.model.getDescription());
		else descTA.setText("");
		show();
	}

}
