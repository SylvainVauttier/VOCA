package application;


import dao.DaoObjet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
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
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.IoTClassNames;
import model.Thing;

public class VocaView {
	
	static ThingView draggedThing = null;
	
	static ContextMenu thingContextMenu = null;
	
	static Pane centerPane = null;
	
	static Rectangle dragRectangle = null;
	
	static int selectedIoTtool = -1;

	public static ThingView pressedThing;
	
	BorderPane root;
	VocaController controler;
	//protected boolean paneDragged;
	
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
			
			primaryStage.show();
			
			dao.DAO.getDAO().test();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private void buildContextMenu() {
		// TODO Auto-generated method stub
		ContextMenu cm = new ContextMenu();
		MenuItem it1 = new MenuItem("supprimer");
		MenuItem it2 = new MenuItem("editer");
		cm.getItems().addAll(it1,it2);
		
		thingContextMenu = cm;
		
		it1.setOnAction(actionEvent->{
			//System.out.println(it1.getParentPopup().getOwnerNode().getClass().getName());
			//centerPane.getChildren().remove(it1.getParentPopup().getOwnerNode());
			((ThingView)it1.getParentPopup().getOwnerNode()).erase(centerPane);
		});
	}

	private void buildControlPane() {
		// TODO Auto-generated method stub
		GridPane ctrl = new GridPane();
		//TilePane ctrl = new TilePane(Orientation.VERTICAL);
		Label lb1 = new Label("Huteur :");
		TextField huteur = new TextField("Huteur");
		Label lb2 = new Label("Scénario :");
		TextField scen = new TextField("Scénario");
		
		Button charger = new Button("Charger");
		charger.setMaxWidth(Double.MAX_VALUE);
		//charger.getStyleClass().add("round-red");
		Button sauver = new Button("Sauver");
		sauver.setMaxWidth(Double.MAX_VALUE);
		//sauver.getStyleClass().add("round-red");
		Button nouveau = new Button("Nouveau");
		nouveau.setMaxWidth(Double.MAX_VALUE);
		//nouveau.getStyleClass().add("round-red");
		//ctrl.getChildren().addAll(lb1,huteur,lb2,scen,nouveau,charger,sauver);
		ctrl.addColumn(0,lb1,huteur,lb2,scen,nouveau,charger,sauver);
		
		root.setRight(ctrl);
		
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
				
				
				if (draggedThing==null)
				{
				//Text text = new Text(x,y+40,"name");
				ThingView view = ThingViewFactory.buildView(selectedIoTtool, x, y);
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
					draggedThing=null;
				}
				
				DaoObjet.getDao().persist(new Thing());
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
