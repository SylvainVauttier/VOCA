package application;
	
import dao.DAO;
import dao.DaoObjet;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.Objet;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.input.MouseEvent;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Voca.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("VOCA");
			
			Image image = new Image(getClass().getResourceAsStream("../img/PlanHut.png"));
			//BackgroundPosition bp = new BackgroundPosition(horizontalSide, horizontalPosition, horizontalAsPercentage, verticalSide, verticalPosition, verticalAsPercentage)
			BackgroundImage bi = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
			Background bg = new Background(bi);
			
			Pane pane = new Pane();
			pane.setBackground(bg);
			pane.setMinSize(600, 400);
			
			ScrollPane sp = new ScrollPane();
			sp.setContent(pane);
			sp.setPannable(true);
			sp.setFitToHeight(true);
			sp.setFitToWidth(true);
			
			root.setCenter(sp);
			
			TabPane tp = new TabPane();
			
			Tab tab1 = new Tab("Tab1");
			GridPane gp1 = new GridPane();
			
			for (int c=0;c<2;c++)
			for (int r=0;r<10;r++)
			{
				Image ic = new Image(getClass().getResourceAsStream("../img/IconeSofa.png"),30,30,true,true);
				Button bt = new Button();
				bt.setGraphic(new ImageView(ic));
				gp1.add(bt,c,r);
			}
			
			tab1.setContent(gp1);
			tp.getTabs().add(tab1);
			root.setLeft(tp);
			
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
			
			//VBox ctrl = new VBox();
			GridPane ctrl = new GridPane();
			//TilePane ctrl = new TilePane(Orientation.VERTICAL);
			Label lb1 = new Label("Huteur :");
			TextField huteur = new TextField("Huteur");
			Label lb2 = new Label("Scénario :");
			TextField scen = new TextField("Scénario");
			
			Button charger = new Button("Charger");
			charger.setMaxWidth(Double.MAX_VALUE);
			Button sauver = new Button("Sauver");
			sauver.setMaxWidth(Double.MAX_VALUE);
			Button nouveau = new Button("Nouveau");
			nouveau.setMaxWidth(Double.MAX_VALUE);
			//ctrl.getChildren().addAll(lb1,huteur,lb2,scen,nouveau,charger,sauver);
			ctrl.addColumn(0,lb1,huteur,lb2,scen,nouveau,charger,sauver);
			
			root.setRight(ctrl);
			
			//Rectangle r = new Rectangle(30, 30);
			//pane.getChildren().add(r);
			
			pane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				
				public void handle(MouseEvent event) {
					double x = event.getX();
					double y = event.getY();
					
					x -=15;
					y -=15;
					
					if (x<0) x=0;
					if (y<0) y=0;
					
					Rectangle r = new Rectangle(x,y,30, 30);
					
					r.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
						
						public void handle(MouseEvent event) {
							Rectangle r = (Rectangle) event.getSource();
							r.setFill(Color.ALICEBLUE);
							event.consume();
						}
						
					});
					
					
					pane.getChildren().add(r);
					
					DaoObjet.getDao().persist(new Objet());
				}
				
			});
			
			primaryStage.show();
			
			dao.DAO.getDAO().test();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		super.stop();
		dao.DAO.getDAO().stopServer();
	}



	public static void main(String[] args) {
		launch(args);
	}
}
