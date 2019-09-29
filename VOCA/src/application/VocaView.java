package application;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dao.DaoObjet;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import model.Huteur;
import model.IoTClassNames;
import model.Scenario;
import model.Thing;

public class VocaView {
	
	static ThingView draggedThing = null;
	
	static ContextMenu thingContextMenu = null;
	
	static Pane centerPane = null;
	
	static Rectangle dragRectangle = null;
	
	static int selectedIoTtool = -1;

	public static ThingView pressedThing;
	
	BorderPane root;
	
	static public VocaController controler;

	public Scenario currentScenario=null;
	private List<Scenario> scenarioList = new ArrayList<Scenario>();
	private List<ThingView> currentThingViewList = null;
	private HashMap<String,List<ThingView>> agencementList = new HashMap<String,List<ThingView>>();
	
	//protected boolean paneDragged;

	private Huteur currentHuteur;

	private ScenarioEditor scenarioEditor=null;
	private TextArea scenarioDescriptor = null;
	private ListView<String> scenarioListView;
	
	//TabPane tp = new TabPane();
	
	
	
	public VocaView(Stage primaryStage, VocaController ctr) {
		
		controler=ctr;
		
		try {
			root = (BorderPane)FXMLLoader.load(getClass().getResource("Voca.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("VOCA");
			
			buildCenterPane();
				
			buildIotPane();
			
			buildControlPane();
			
			buildContextMenu();
			
//			
			
//			MenuBar mb = new MenuBar();
//			Menu user = new Menu("Utilisateur");
//			MenuItem mue = new MenuItem("Editer nom");
//			user.getItems().add(mue);
//			
//			Menu sc = new Menu("Scenario");
//			MenuItem sce = new MenuItem("Editer nom");
//			MenuItem scc = new MenuItem("Charger");
//			MenuItem scs = new MenuItem("Sauver");
//			sc.getItems().addAll(sce,scc,scs);
//
//			mb.getMenus().addAll(user,sc);
//			
//			root.setTop(mb);
			
//			
			primaryStage.setMaximized(true);
			primaryStage.show();
			
			buildAndShowWelcome(primaryStage);
			
			//dao.DAO.getDAO().test();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private void buildAndShowWelcome(Stage primaryStage) {
		// TODO Auto-generated method stub
		Image image = new Image(getClass().getResourceAsStream("../img/Consignes.png"));
		
		
		Stage welcome = new Stage(StageStyle.UTILITY);
		
		BackgroundImage bi = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
		Background bg = new Background(bi);
		BorderPane wp = new BorderPane();
		
		welcome.setScene(new Scene(wp,1200,680));
		wp.setBackground(bg);
		wp.setEffect(new DropShadow());
		
		Button wb = new Button("Commencer");
		wb.setOnAction(actionEvent->{
			welcome.hide();
			currentHuteur = controler.creerHuteur();
		});
		//ca ne marche pas et je ne sais pas pourquoi...
//		wb.getStyleClass().add("round-red");
//		wb.getStyleClass().add("welcome-button");
		wb.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n" + 
				"    -fx-background-radius: 30;\r\n" + 
				"    -fx-border-radius: 30;\r\n" + 
				"    -fx-background-insets: 0;\r\n" + 
				"    -fx-text-fill: white;"+
				"-fx-font-size: 24");
		
		wp.setCenter(wb);
		wp.setAlignment(wb,Pos.BOTTOM_RIGHT);
		wp.setPadding(new Insets(20));
		
		
		welcome.initModality(Modality.APPLICATION_MODAL);
		welcome.setResizable(false);
		welcome.centerOnScreen();
		
		
		welcome.setOnCloseRequest(windowEvent->{
			//Platform.exit();
			welcome.hide();
			currentHuteur = controler.creerHuteur();
		});
		
		welcome.show();
		
	}

	private void buildContextMenu() {
		// TODO Auto-generated method stub
		ContextMenu cm = new ContextMenu();
		MenuItem it1 = new MenuItem("Supprimer");
		MenuItem it2 = new MenuItem("Editer");
		MenuItem it3 = new MenuItem("Activer/Désactiver");
		cm.getItems().addAll(it1,it2,it3);
		
		thingContextMenu = cm;
		
		it1.setOnAction(actionEvent->{
			//System.out.println(it1.getParentPopup().getOwnerNode().getClass().getName());
			//centerPane.getChildren().remove(it1.getParentPopup().getOwnerNode());
			ThingView tv = (ThingView)it1.getParentPopup().getOwnerNode();
			if (tv.model.getActivations()==0)
			{	
			tv.erase(centerPane);
			controler.supprimerThing(tv.model);
			}
			else
			{
				Alert al = new Alert (Alert.AlertType.INFORMATION,"Objet utilisé dans un scénario");
				al.showAndWait();
			}
		});
		
		it3.setOnAction(actionEvent->{
			if (currentScenario==null) return;
			ThingView tv = (ThingView)it3.getParentPopup().getOwnerNode();
			tv.toggle();
			if (tv.active) 
				{
				controler.activate(tv.model);
				currentThingViewList.add(tv);
				}
			else 
				{
				controler.deactivate(tv.model);
				currentThingViewList.remove(tv);
				}
		});
	}

	private void buildControlPane() {
		// TODO Auto-generated method stub
		GridPane ctrl = new GridPane();
		
		Button editer = new Button("Editer");
		editer.setMaxWidth(Double.MAX_VALUE);
		editer.getStyleClass().add("round-red");
		Button supprimer = new Button("Supprimer");
		supprimer.setMaxWidth(Double.MAX_VALUE);
		supprimer.getStyleClass().add("round-red");
		Button nouveau = new Button("Nouveau");
		nouveau.setMaxWidth(Double.MAX_VALUE);
		nouveau.getStyleClass().add("round-red");
		Button quitter = new Button("Quitter");
		quitter.setMaxWidth(Double.MAX_VALUE);
		quitter.getStyleClass().add("round-red");
		
		
		Label ls = new Label("Scénarios");
		scenarioListView = new ListView<String>();
		scenarioListView.setMaxWidth(200);
		
		Label ld = new Label("Description");
		scenarioDescriptor = new TextArea();
		scenarioDescriptor.setMaxWidth(200);
		
		ctrl.addColumn(0,nouveau,editer,supprimer,ls,scenarioListView,ld,scenarioDescriptor,quitter);
		
		root.setRight(ctrl);
		
		scenarioListView.setOnMouseClicked(mouseEvent->{
			Scenario selectedScenario=scenarioList.get(scenarioListView.getSelectionModel().getSelectedIndex());
			if (currentScenario==null||selectedScenario!=currentScenario)
			{
				if (currentScenario!=null) hideActiveIoT();
				currentScenario=selectedScenario;
				currentThingViewList=agencementList.get(Integer.toString(currentScenario.getOid()));
				showActiveIoT();
				scenarioDescriptor.setText(currentScenario.getDescription());
				//return;
			}
//			if (selectedScenario!=currentScenario)
//			{
//				hideActiveIoT();
//				currentScenario=selectedScenario;
//				currentThingViewList=agencementList.get(Integer.toString(currentScenario.getOid()));
//				showActiveIoT();
//			}
		});
		
		nouveau.setOnAction(actionEvent->{
			Scenario nouveauScenario = controler.creerScenario();
			scenarioList.add(nouveauScenario);
			currentScenario=nouveauScenario;
			
			currentThingViewList = new ArrayList<ThingView>();
			agencementList.put(Integer.toString(currentScenario.getOid()), currentThingViewList);
			
			scenarioListView.getItems().add(nouveauScenario.getName());
			scenarioListView.getSelectionModel().selectLast();
			
			scenarioDescriptor.setText("");
		});
		
		supprimer.setOnAction(actionEvent->{
			if (currentScenario==null) return;
			controler.supprimerScenario(currentScenario);
			hideActiveIoT();
			int i=0;
			for (;;i++)
				if (currentScenario==scenarioList.get(i)) break;
			scenarioList.remove(i);
			scenarioListView.getItems().remove(i);
			scenarioListView.getSelectionModel().clearSelection();
			
			scenarioDescriptor.setText("");
			
			currentScenario=null;
			currentThingViewList=null;
		});
		
		editer.setOnAction(actionEvent->{
			if (currentScenario==null) return;
			openScenarioEditor();
		});
		
		
		
	}

	private void openScenarioEditor() {
		// TODO Auto-generated method stub
		if (scenarioEditor==null) buildScenarioEditor();
		scenarioEditor.nameTF.setText(currentScenario.getName());
		if (currentScenario.getDescription()==null)scenarioEditor.descTA.setText("");
		else scenarioEditor.descTA.setText(currentScenario.getDescription());
		scenarioEditor.show();
	}

	private void buildScenarioEditor() {
		// TODO Auto-generated method stub
		scenarioEditor=new ScenarioEditor();
		
		GridPane grid = new GridPane();
		grid.setVgap(5);
        grid.setHgap(10);
        
        Label name = new Label("Nom");
        GridPane.setConstraints(name, 0, 0);
        grid.getChildren().add(name);
        
        scenarioEditor.nameTF = new TextField();
        GridPane.setConstraints(scenarioEditor.nameTF, 1, 0);
        grid.getChildren().add(scenarioEditor.nameTF);
        
        Label desc = new Label("Description");
        GridPane.setConstraints(desc, 0, 1);
        grid.getChildren().add(desc);
        
        scenarioEditor.descTA = new TextArea();
        scenarioEditor.descTA.setWrapText(true);
        GridPane.setConstraints(scenarioEditor.descTA, 1, 2);
        grid.getChildren().add(scenarioEditor.descTA);
        
        Button ok = new Button("Valider");
        GridPane.setConstraints(ok, 0, 3);
        grid.getChildren().add(ok);
        
        Button ko = new Button("Annuler");
        GridPane.setConstraints(ko, 1, 3);
        grid.getChildren().add(ko);
        
        Scene scene = new Scene(grid);
        scenarioEditor.setScene(scene);

        ok.setOnAction(eventAction->{
        	scenarioEditor.hide();
        	controler.modifierScenario(currentScenario,scenarioEditor.nameTF.getText(),scenarioEditor.descTA.getText());
        	
        	int selection = scenarioListView.getSelectionModel().getSelectedIndex();
        	scenarioListView.getItems().set(selection, scenarioEditor.nameTF.getText());
        	scenarioDescriptor.setText(scenarioEditor.descTA.getText());
        });
        
        ko.setOnAction(eventAction->{
        	scenarioEditor.hide();
        });
        
	}

	private void showActiveIoT() {
		// TODO Auto-generated method stub
		for (ThingView v : currentThingViewList)
		v.active();
	}

	private void hideActiveIoT() {
		// TODO Auto-generated method stub
		for (ThingView v : currentThingViewList)
			v.deactive();
	}

	private void buildCenterPane() {
		// TODO Auto-generated method stub
		Image image = new Image(getClass().getResourceAsStream("../img/PlanHut.png"));
		BackgroundImage bi = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
		Background bg = new Background(bi);
		
		Screen screen = Screen.getPrimary();
				
		Pane pane = new Pane();
		pane.setBackground(bg);
		centerPane=pane;
		//pane.setMinSize(600, 400);
		
		pane.setMinSize(screen.getVisualBounds().getWidth(), screen.getVisualBounds().getHeight());
		pane.setMaxSize(screen.getVisualBounds().getWidth(), screen.getVisualBounds().getHeight());

		
		ScrollPane sp = new ScrollPane();
		sp.setContent(pane);
		sp.setPannable(false);
		sp.setFitToHeight(true);
		sp.setFitToWidth(true);
		
		root.setCenter(sp);
		
//		pane.setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
//
//			@Override
//			public void handle(MouseDragEvent event) {
//				// TODO Auto-generated method stub
//				paneDragged=true;
//			}
//		});
		
		//pane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		
		pane.setOnMouseReleased(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent event) {
				
				//if (event.isConsumed()) return;
				
				if (selectedIoTtool==-1) return;
				
				
				double x = event.getX();
				double y = event.getY();
				
				x -=15;
				y -=15;
				
				if (x<0) x=0;
				if (y<0) y=0;
				
				// à corriger à terme
				// l'évenement n'est pas reçu par le pane si l'on clique sur un thingview
				// le code fonctionne mais peut être amélioré
				if (draggedThing==null)
				{
				//Text text = new Text(x,y+40,"name");
				ThingView view = ThingViewFactory.buildView(selectedIoTtool, x, y);
				view.model=controler.creerThing(selectedIoTtool, x, y);
				//view.name=IoTClassNames.names[selectedIoTtool];
				//pane.getChildren().addAll(view,text);
				view.paint(pane);
				}
				else
				{
//					Translate translate = new Translate();
//					translate.setX(x);
//					translate.setY(y);
					
					//draggedThing.getTransforms().add(translate);
					pane.getChildren().remove(dragRectangle);
					dragRectangle=null;	
					
					//draggedThing.setX(x);
					//draggedThing.setY(y);
					draggedThing.move(x, y);
					controler.moveThing(draggedThing.model,x,y);
					draggedThing=null;					
				}
				
				//DaoObjet.getDao().persist(new Thing());
			}
			
		});
		
		pane.setOnMouseDragged(mouseEvent->{
			
			if (pressedThing!=null)
			{
				draggedThing=pressedThing;
				pressedThing=null;
			}
			
			double x = mouseEvent.getX();
			double y = mouseEvent.getY();
			
			x -=15;
			y -=15;
			
			if (x<0) x=0;
			if (y<0) y=0;
			
			if (dragRectangle==null)
			{
				dragRectangle=new Rectangle(x,y,30,30);
				dragRectangle.setFill(Color.TRANSPARENT);
				dragRectangle.setStroke(Color.BLACK);
				centerPane.getChildren().add(dragRectangle);
			}
			else
			{
				dragRectangle.setX(x);
				dragRectangle.setY(y);
			}
		});
	}

	private void buildIotPane() {
		// TODO Auto-generated method stub
		TabPane tp = new TabPane();
		Tab tab1 = new Tab("IoT");
		GridPane gp1 = new GridPane();
		
		int IoTSize = IoTClassNames.names.length;
		int column=0;
		int row=0;
		
		for (int i=0;i<IoTSize;i++)
		{
			Image ic = new Image(getClass().getResourceAsStream("../img/"+IoTClassNames.names[i]+".png"),30,30,true,true);
			Button bt = new Button();
			bt.setGraphic(new ImageView(ic));
			gp1.add(bt,column,row);
			
			if (column==1)
			{
				column=0;
				row++;
			}
			else column=1;
			
			EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
				
				int IoTindex;
				
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					selectedIoTtool=IoTindex;
					//VocaView.centerPane.requestFocus();
				}

				public EventHandler<ActionEvent> init(int i) {
					// TODO Auto-generated method stub
					IoTindex=i;
					return this;
				}
				
			}.init(i);
			
			bt.setOnAction(handler);
		}
		
//		for (int c=0;c<2;c++)
//		for (int r=0;r<10;r++)
//		{
//			Image ic = new Image(getClass().getResourceAsStream("../img/Fauteuil.png"),30,30,true,true);
//			Button bt = new Button();
//			bt.setGraphic(new ImageView(ic));
//			gp1.add(bt,c,r);
//		}
		
		tab1.setContent(gp1);
		tp.getTabs().add(tab1);
		root.setLeft(tp);
	}

}
