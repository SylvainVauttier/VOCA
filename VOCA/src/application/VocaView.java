package application;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import javax.xml.ws.handler.MessageContext.Scope;

import dao.DaoObjet;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
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

	public static ThingView pressedThing=null;
	
	BorderPane root;
	
	static public VocaController controler;

	public Scenario currentScenario=null;
	private List<Scenario> scenarioList = new ArrayList<Scenario>();
	private List<ThingView> currentThingViewList = null;
	private HashMap<String,List<ThingView>> agencementList = new HashMap<String,List<ThingView>>();
	private List<ThingView> globalThingViewList = new ArrayList<ThingView>();
	
	//protected boolean paneDragged;

	private Huteur currentHuteur;

	private ScenarioEditor scenarioEditor=null;
	private TextArea scenarioDescriptor = null;
	private ListView<String> scenarioListView;

	private ThingEditor thingEditor;
	
	private Stage welcome=null;
	private Stage consigne=null;
	private Stage alerteSuppression=null;
	private Stage alerteSortie=null;
	
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
	
	private void buildAndShowSortie()
	{
		if (alerteSortie==null)
		{
				alerteSortie = new Stage(StageStyle.UTILITY);
				alerteSortie.initModality(Modality.APPLICATION_MODAL);
				alerteSortie.setResizable(false);
				alerteSortie.centerOnScreen();
				
				BorderPane wp = new BorderPane();
				Scene scene = new Scene(wp,600,340);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				alerteSortie.setScene(scene);
				
				Image image = new Image(getClass().getResourceAsStream("../img/AlerteSortie.png"));
				BackgroundImage bi = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
				Background bg = new Background(bi);
				wp.setBackground(bg);
				wp.setEffect(new DropShadow());
				
				HBox buttons = new HBox();
				buttons.setAlignment(Pos.BOTTOM_RIGHT);
				buttons.setPadding(new Insets(10,10,10,10));
				buttons.setSpacing(10);
				
				Button cb = new Button("Ciao !");
				cb.getStyleClass().add("round-red");
				cb.setPadding(new Insets(20));
				
				Button rb = new Button("Je reste encore un peu...");
				rb.getStyleClass().add("round-red");
				rb.setPadding(new Insets(20));
				
				buttons.getChildren().addAll(rb,cb);
				wp.setAlignment(buttons,Pos.BOTTOM_CENTER);
				
				wp.setBottom(buttons);
				
				cb.setOnAction(actionEvent->{
					alerteSortie.hide();
					reinitGame();
				});
				
				rb.setOnAction(actionEvent->{
					alerteSortie.hide();
				});
				
				consigne.setOnCloseRequest(windowEvent->{
					alerteSortie.hide();
				});
		}
		
		alerteSortie.show();
	}
	
	private void buildAndShowSuppression()
	{
		if (alerteSuppression==null)
		{
			alerteSuppression = new Stage(StageStyle.UTILITY);
			alerteSuppression.initModality(Modality.APPLICATION_MODAL);
			alerteSuppression.setResizable(false);
			alerteSuppression.centerOnScreen();
			
			BorderPane wp = new BorderPane();
			Scene scene = new Scene(wp,600,340);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			alerteSuppression.setScene(scene);
			
			Image image = new Image(getClass().getResourceAsStream("../img/AlerteSuppression.png"));
			BackgroundImage bi = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
			Background bg = new Background(bi);
			wp.setBackground(bg);
			wp.setEffect(new DropShadow());
			
			Button wb = new Button("Ok");
			wb.getStyleClass().add("round-red");
			wp.setCenter(wb);
			wp.setAlignment(wb,Pos.BOTTOM_RIGHT);
			wp.setPadding(new Insets(20));
			
			wb.setOnAction(actionEvent->{
				alerteSuppression.hide();
			});
			
			consigne.setOnCloseRequest(windowEvent->{
				alerteSuppression.hide();
			});
		}
			
			alerteSuppression.show();
	}
	
	private void buildAndShowConsigneWindow()
	{
		if (consigne==null)
		{
		consigne = new Stage(StageStyle.UTILITY);
		consigne.initModality(Modality.APPLICATION_MODAL);
		consigne.setResizable(false);
		consigne.centerOnScreen();
		
		
		BorderPane wp = new BorderPane();
		Scene scene = new Scene(wp,1200,680);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		consigne.setScene(scene);
		
		Image image = new Image(getClass().getResourceAsStream("../img/Consignes2.png"));
		BackgroundImage bi = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
		Background bg = new Background(bi);
		wp.setBackground(bg);
		wp.setEffect(new DropShadow());
		
		
		Button wb = new Button("Ok");
		wb.getStyleClass().add("round-red");
		wp.setCenter(wb);
		wp.setAlignment(wb,Pos.BOTTOM_RIGHT);
		wp.setPadding(new Insets(20));
		
		
		wb.setOnAction(actionEvent->{
			enter();
		});
		
		consigne.setOnCloseRequest(windowEvent->{
			enter();
		});
		}
		
		consigne.show();
		
	}

	private void enter() {
		// TODO Auto-generated method stub
		consigne.hide();
	}

	private void buildAndShowWelcome(Stage primaryStage) {
		// TODO Auto-generated method stub
		if (welcome==null)
		{
		Image image = new Image(getClass().getResourceAsStream("../img/Consignes.png"));
		
		
		welcome = new Stage(StageStyle.UTILITY);
		BorderPane wp = new BorderPane();
		Scene scene = new Scene(wp,1200,680);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		BackgroundImage bi = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
		Background bg = new Background(bi);
		
		
		welcome.setScene(scene);
		wp.setBackground(bg);
		wp.setEffect(new DropShadow());
		
		Button wb = new Button("Entrer");
		wb.setOnAction(actionEvent->{
//			welcome.hide();
//			currentHuteur = controler.creerHuteur();
			startGame();
		});
		

//		wb.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n" + 
//				"    -fx-background-radius: 30;\r\n" + 
//				"    -fx-border-radius: 30;\r\n" + 
//				"    -fx-background-insets: 0;\r\n" + 
//				"    -fx-text-fill: white;"+
//				"-fx-font-size: 24");
		
		wb.getStyleClass().add("round-red");
		//wb.getStyleClass().add("welcome-button");
		wp.setCenter(wb);
		wp.setAlignment(wb,Pos.BOTTOM_RIGHT);
		wp.setPadding(new Insets(20));
		
		
		welcome.initModality(Modality.APPLICATION_MODAL);
		welcome.setResizable(false);
		welcome.centerOnScreen();
		
		
		welcome.setOnCloseRequest(windowEvent->{
			//Platform.exit();
//			welcome.hide();
//			currentHuteur = controler.creerHuteur();
			startGame();
		});
		}
		welcome.show();
		
	}
	
	private void startGame()
	{
		welcome.hide();
		buildUserInfo();
		currentHuteur = controler.creerHuteur();
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
			globalThingViewList.remove(tv);
			}
			else
			{
				//Alert al = new Alert (Alert.AlertType.INFORMATION,"Objet utilisé dans un scénario");
				//al.showAndWait();
				buildAndShowSuppression();
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
		
		it2.setOnAction(actionEvent->{
			ThingView tv = (ThingView)it3.getParentPopup().getOwnerNode();
			showThingEditor(tv);
		});
	}

	private void showThingEditor(ThingView tv) {
		// TODO Auto-generated method stub
		if (thingEditor==null) thingEditor=new ThingEditor(controler);
		thingEditor.show(tv);
	}

	private void buildControlPane() {
		// TODO Auto-generated method stub
		GridPane ctrl = new GridPane();
		
		Button aide = new Button("Aide");
		aide.setMaxWidth(Double.MAX_VALUE);
		aide.getStyleClass().add("round-red");
		Button editer = new Button("Editer scénario");
		editer.setMaxWidth(Double.MAX_VALUE);
		editer.getStyleClass().add("round-red");
		Button supprimer = new Button("Supprimer scénario");
		supprimer.setMaxWidth(Double.MAX_VALUE);
		supprimer.getStyleClass().add("round-red");
		Button nouveau = new Button("Créer scénario");
		nouveau.setMaxWidth(Double.MAX_VALUE);
		nouveau.getStyleClass().add("round-red");
		Button quitter = new Button("Sortir");
		quitter.setMaxWidth(Double.MAX_VALUE);
		quitter.getStyleClass().add("round-red");
		
		
		Label ls = new Label("Scénarios");
		scenarioListView = new ListView<String>();
		scenarioListView.setMaxWidth(300);
		
		Label ld = new Label("Description");
		scenarioDescriptor = new TextArea();
		scenarioDescriptor.setMaxWidth(300);
		
		//ctrl.addColumn(0,nouveau,editer,supprimer,aide,ls,scenarioListView,ld,scenarioDescriptor,quitter);
		ctrl.addColumn(0,nouveau,editer,supprimer,aide,scenarioListView,scenarioDescriptor,quitter);

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
			
			if (currentScenario!=null) hideActiveIoT();
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
		
		quitter.setOnAction(actionEvent->{
//			Alert al = new Alert (Alert.AlertType.CONFIRMATION,"Attention ! Toute sortie est définitive !!!");
//			Optional<ButtonType> result=al.showAndWait();
//			if (result.isPresent() && result.get() == ButtonType.OK)
//			reinitGame();
			buildAndShowSortie();
		});
		
		aide.setOnAction(actionEvent->{
			consigne.show();
		});
		
	}

	private void reinitGame() {
		// TODO Auto-generated method stub
		selectedIoTtool=-1;
		
		cleanHouse(centerPane);
		
		scenarioListView.getItems().clear();
		scenarioDescriptor.setText("");
		scenarioList = new ArrayList<Scenario>();
		agencementList = new HashMap<String,List<ThingView>>();
		globalThingViewList = new ArrayList<ThingView>();
		currentThingViewList = null;
		
		currentHuteur=null;
		currentScenario=null;
		welcome.show();
	}

	private void cleanHouse(Pane p) {
		// TODO Auto-generated method stub
		for (ThingView tv : globalThingViewList)
			tv.erase(p);
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
        
        Scene scene = new Scene(grid);
        scenarioEditor.setScene(scene);
        
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Font smallFont = Font.font("Calibri",FontWeight.BOLD,FontPosture.REGULAR,16);
        
        Label name = new Label("Nom");
        name.setFont(smallFont);
        GridPane.setConstraints(name, 0, 0);
        grid.getChildren().add(name);
        
        scenarioEditor.nameTF = new TextField();
        scenarioEditor.nameTF.setFont(smallFont);
        GridPane.setConstraints(scenarioEditor.nameTF, 1, 0);
        grid.getChildren().add(scenarioEditor.nameTF);
        
        Label desc = new Label("Description");
        desc.setFont(smallFont);
        GridPane.setConstraints(desc, 0, 1);
        grid.getChildren().add(desc);
        
        scenarioEditor.descTA = new TextArea();
        scenarioEditor.descTA.setWrapText(true);
        scenarioEditor.descTA.setFont(smallFont);
        GridPane.setConstraints(scenarioEditor.descTA, 1, 2);
        grid.getChildren().add(scenarioEditor.descTA);
        
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setSpacing(20);
        
        Button ok = new Button("Valider");
        ok.getStyleClass().add("round-red");
//        GridPane.setConstraints(ok, 0, 3);
//        grid.getChildren().add(ok);
        
        Button ko = new Button("Annuler");
        ko.getStyleClass().add("round-red");
//        GridPane.setConstraints(ko, 1, 3);
//        grid.getChildren().add(ko);
        
        buttons.getChildren().addAll(ok,ko);
        grid.add(buttons, 1, 3);

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
		sp.setVvalue(0.5);
		sp.setHvalue(0.5);
		
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
				globalThingViewList.add(view);
				//évite d'ajouter à chaque clic un Iot
				selectedIoTtool=-1;
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
				//pressedThing=null;
			}
			if (draggedThing==null) return;
			
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
		Tab tab1 = new Tab("Objets");
		GridPane gp1 = new GridPane();
		
		int IoTSize = IoTClassNames.names.length;
		int column=0;
		int row=0;
		
		for (int i=0;i<IoTSize;i++)
		{
			Image ic = new Image(getClass().getResourceAsStream("../img/"+IoTClassNames.names[i]+".png"),30,30,true,true);
			Button bt = new Button();
			bt.setGraphic(new ImageView(ic));
			Tooltip tt = new Tooltip(IoTClassNames.names[i]+"\n"+"Cliquer sur l'outil puis sur le plan pour créer un objet de ce type");
			bt.setTooltip(tt);
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
	
	private void buildUserInfo() {
		Stage userInfo = new Stage(StageStyle.UTILITY);
		userInfo.setTitle("Informations utilisateur");
		BorderPane userPane = new BorderPane();
		Scene scene = new Scene(userPane,960, 768);
		userInfo.setScene(scene);
		//userPane.setPadding(new Insets(10,10,10,10));
		
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		//userPane.setB(Color.PALETURQUOISE);
		
		Font bigFont = Font.font("Calibri",FontWeight.BOLD,FontPosture.REGULAR,24);
		Font smallFont = Font.font("Calibri",FontWeight.BOLD,FontPosture.REGULAR,16);

		userInfo.setOnCloseRequest(windowEvent->{
			windowEvent.consume();
		});
		
		GridPane grid = new GridPane();
		
		Label userInstruction = new Label("Merci de répondre à ces quelques questions :");
		userInstruction.setFont(bigFont);
		
		grid.add(userInstruction,0,0,2,1);
		
		Label userNameLabel = new Label("Quel est votre pseudo ?");
		userNameLabel.setFont(bigFont);
		TextField userNameTextField = new TextField();
		userNameTextField.setFont(bigFont);
		//HBox userNameBox = new HBox(50, userNameLabel,userNameTextField);
		grid.add(userNameLabel, 0, 1);
		grid.add(userNameTextField,1,1);
		
		grid.setHgap(30);
		grid.setVgap(10);
		grid.setPadding(new Insets(30,10,10,70));

		Label userAgeLabel = new Label("Quel est votre âge ?");
		userAgeLabel.setFont(bigFont);
		
		Slider userAgeSlider = new Slider();
		userAgeSlider.setMin(15);
		userAgeSlider.setMax(80);
		userAgeSlider.setValue(35);	         
		userAgeSlider.setShowTickLabels(true);
		userAgeSlider.setShowTickMarks(true);	         
		userAgeSlider.setMajorTickUnit(5);
		userAgeSlider.setMinorTickCount(4);
		userAgeSlider.setSnapToTicks(true);
		userAgeSlider.setBlockIncrement(1);
		userAgeSlider.setPrefWidth(350);
		
		Label userAgeValueLabel = new Label ("35");
		//userAgeValueLabel.setTextFill(Color.MAGENTA);

		userAgeValueLabel.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderWidths.FULL)));
		
		userAgeValueLabel.setFont(smallFont);
		
		grid.add(userAgeLabel, 0, 2);
		grid.add(userAgeSlider, 1, 2);
		grid.add(userAgeValueLabel, 2, 2);
		
		userAgeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			userAgeValueLabel.setText(Integer.toString(newValue.intValue()));
		});

		//HBox userAgeBox = new HBox(70, userAgeLabel, userAgeSlider, userAgeValueLabel);

		Label userGenderLabel = new Label("Quel est votre genre ?");
		userGenderLabel.setFont(bigFont);
		
		ToggleGroup userGenderGroup = new ToggleGroup();
		ToggleButton male = new RadioButton("Masculin");
		male.setFont(smallFont);
		male.setToggleGroup(userGenderGroup);
		ToggleButton female = new RadioButton("Féminin");
		female.setFont(smallFont);
		female.setToggleGroup(userGenderGroup);
		ToggleButton neutral = new RadioButton("Neutre");
		neutral.setFont(smallFont);
		neutral.setToggleGroup(userGenderGroup);
		female.setSelected(true);
		
		HBox toggleButtons = new HBox(60,female, male, neutral);
		
		grid.add(userGenderLabel, 0, 3);
		grid.add(toggleButtons, 1, 3);
		
		//HBox userGenderBox = new HBox(60, userGenderLabel, female, male, neutral);
		
		Label userZipcodeLabel = new Label("Quel est votre code postal ?");
		userZipcodeLabel.setFont(bigFont);
		TextField userZipcodeTextField = new TextField();
		userZipcodeTextField.setFont(bigFont);
		//HBox userZipcodeBox = new HBox(25, userZipcodeLabel, userZipcodeTextField);
		grid.add(userZipcodeLabel, 0, 4);
		grid.add(userZipcodeTextField, 1, 4);
		//TODO : controle de la valeur

		Label userQuestions = new Label ("Que vous inspirent les objets connectés ? (1 pour faible; 5 pour fort)");
		//Label levelExplanation = new Label ("(1 pour faible; 5 pour fort)");
		userQuestions.setFont(bigFont);
		//levelExplanation.setFont(smallFont);
		grid.add(userQuestions,0,5,2,1);
		//grid.add(levelExplanation, 0, 6);
		
		String [] questions = {"Peur", "Admiration", "Inquiétude", "Interêt", "Aversion", "Curiosité"};

		//VBox userQuestionBox = new VBox(20, userQuestions);
		
		Slider [] userAnswerSliders = new Slider[6];
		for(int i=0; i<6; i++)
		{
			userAnswerSliders[i] = new Slider (1,5,0);
			userAnswerSliders[i].setShowTickLabels(true);
			userAnswerSliders[i].setShowTickMarks(true);
			userAnswerSliders[i].setMajorTickUnit(1);
			userAnswerSliders[i].setMinorTickCount(0);
			userAnswerSliders[i].setBlockIncrement(1);
			userAnswerSliders[i].setPrefWidth(250);
			userAnswerSliders[i].setSnapToTicks(true);

			Label userAnswerValue = new Label("1");
			userAnswerValue.setFont(smallFont);
			
			userAnswerSliders[i].valueProperty().addListener((observable, oldValue, newValue) -> {
				userAnswerValue.setText(Integer.toString(newValue.intValue()));
			});
			
			Label label = new Label (questions[i]);
			label.setFont(bigFont);
			
//			HBox userAnswerBox = new HBox(50, label, userAnswerSliders[i], userAnswerValue);
//			userQuestionBox.getChildren().add(userAnswerBox);
			grid.add(label, 0, i+6);
			grid.add(userAnswerSliders[i], 1, i+6);
			grid.add(userAnswerValue,2,i+6);
		}
		
		Button userInfoButton = new Button("A vous de jouer !");
		userInfoButton.getStyleClass().add("round-red");
		userPane.setAlignment(userInfoButton,Pos.TOP_RIGHT);
		//BorderPane.setAlignment(userInfoButton,Pos.BASELINE_RIGHT);
		//userInfoButton.setPadding(new Insets(20));
		userPane.setBottom(userInfoButton);
		
		userInfoButton.setOnAction(actionEvent->{
	        String name = userNameTextField.getText();
	        int age = (int) userAgeSlider.getValue();
	        ToggleButton selectedGender = (ToggleButton) userGenderGroup.getSelectedToggle();
	        String gender =  selectedGender.getText();
	        String zipcode = userZipcodeTextField.getText();
	        
	        int userAnswers[] = new int [6];
	        for(int i =0; i<6; i++)
	        {
	        	userAnswers[i] = userAnswerSliders[i].valueProperty().intValue();
	        	//System.out.print(" - " + userAnswers[i]);
	        }
	        currentHuteur.setHuteur(name,gender,age,zipcode,userAnswers);
	        userInfo.hide();
	        buildAndShowConsigneWindow();
		});
		
		
		//VBox userBox = new VBox(10,userInstruction,userNameBox,userGenderBox,userAgeBox,userZipcodeBox,userQuestionBox);


		
		Image banniere = new Image(getClass().getResourceAsStream("../img/Banniere.png"));
		ImageView banniereFond = new ImageView();
		banniereFond.setImage(banniere);
		
//		BackgroundImage bi =new BackgroundImage(banniere, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(960, 600, true, true, true, false));
//		Background bg = new Background(bi);
//		topBox.setBackground(bg);
		
		userPane.setTop(new HBox(banniereFond));
	    
		//userPane.setTop(topBox);
		
		userPane.setCenter(grid);
		//userBox.setEffect(new DropShadow());	
		
		
		
		

		
//		wb.getStyleClass().add("round-red");
//		wb.getStyleClass().add("welcome-button");
//		userPane.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);\r\n" + 
//				"    -fx-background-radius: 30;\r\n" + 
//				"    -fx-border-radius: 30;\r\n" + 
//				"    -fx-background-insets: 0;\r\n" + 
//				"    -fx-text-fill: white;"+
//				"-fx-font-size: 24");
		
//		userBox.setCenter(userInfoButton);
//		userBox.setAlignment(userInfoButton,Pos.BOTTOM_RIGHT);
//		userBox.setPadding(new Insets(20));
		
		userInfo.initModality(Modality.APPLICATION_MODAL);
		userInfo.setResizable(false);
		userInfo.centerOnScreen();
		
		userInfo.show();
		
	}
}
