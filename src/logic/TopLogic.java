package logic;

import java.awt.Dimension;
import java.awt.print.Printable;

import data.Settings;
import game.GameTable;
import game.Games;
import gui.Central;
import gui.Filtr;
import gui.Welcome;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import sql.Sql;

public class TopLogic {
	public static void setTopButtonsStyle(HBox top, int start, int end) {
		for(int i=start;i<end;i++) {
			Button button =(Button) top.getChildren().get(i);
			button.setOnMouseEntered(e->{button.setStyle("-fx-background-color: #AAAAAA;");});
			button.setOnMouseExited(e->{button.setStyle("-fx-background-color: TRANSPARENT;");});
		}
	}

	public static void setTopButtonsAction(HBox top, int start, int end) {
		for(int i=start;i<end;i++) {
			Button button =(Button) top.getChildren().get(i);
			switch(i) {
				case 0:
					homeAction(button);
					break;
				case 1:
					quickShopAction(button);
					break;
				case 2:
					advanceShopAction(button);
					break;
				case 3:
					pickingAction(button);
					break;
				default:
					System.out.println("Wrong number of buttons in top");
					break;
			}
		}
		
	}
	private static void homeAction(Button button) {
		button.setOnAction(e->{
			TableLogic.games=null;
			TableLogic.addGameToTable();
		});
		
	}

	private static void quickShopAction(Button button) {
		button.setOnAction(e->{
			GameFinder gameFinder = new GameFinder(GameFinder.QUICK_SEARCH);
			gameFinder.setBudget(150); // TODO in settings set value
			gameFinder.randomWeight();
			TableLogic.games=gameFinder.findGames(TableLogic.games);
			ObservableList<GameTable> gamesList = FXCollections.observableArrayList();
			gamesList.addAll(TableLogic.games.getGames());
			Central.table.setItems(gamesList);
		});
		
	}

	private static void advanceShopAction(Button button) {
		button.setOnMouseClicked(e->{
			if(e.getButton()==MouseButton.PRIMARY) {
				GameFinder gameFinder = new GameFinder(GameFinder.ADVANCE_SEARCH);
				gameFinder.getWeights();
				TableLogic.games=gameFinder.findGames(TableLogic.games);
				ObservableList<GameTable> gamesList = FXCollections.observableArrayList();
				gamesList.addAll(TableLogic.games.getGames());
				Central.table.setItems(gamesList);
			}else if(e.getButton()==MouseButton.SECONDARY) {
				
			}
		});
		
	}

	private static void pickingAction(Button button) {
		// TODO Auto-generated method stub
		
	}
	
	
	public static void setFiltrAction(Button filtrButton) {
		final Filtr filtr=  new Filtr();
		filtrButton.setOnAction(e->{
			Task<Integer> filtrTask= new Task<Integer>() {
	            @Override
	            public Integer call() throws Exception {
	            	setFiltrAction(filtr);
					filtr.setLocation(new Dimension((int) (filtrButton.getLayoutX()+filtrButton.getWidth()/2),(int) (filtrButton.getLayoutY()+filtrButton.getHeight())));
					((Central) filtrButton.getScene().getRoot()).getChildren().add(filtr);
	    			succeeded();
	                return 0;
	            }
	            
	        };
	        filtrTask.setOnSucceeded(f->{filtr.setAction();});
			filtrTask.run();
		});
	}

	private static void setFiltrAction(Filtr filtr) {
		// TODO Auto-generated method stub
		
	}
}
