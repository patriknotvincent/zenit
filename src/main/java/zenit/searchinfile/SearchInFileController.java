package main.java.zenit.searchinfile;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SearchInFileController extends AnchorPane {

	@FXML
	private TextField fldInputField;
	
	@FXML
	private TextField fldReplaceWord;
	
	@FXML
	private Button btnUp;
	
	@FXML
	private Button btnDown;
	
	@FXML 
	private Button btnEsc;
	
	@FXML
	private Button btnReplaceOne;
	
	@FXML
	private Button btnReplaceAll;
	
	@FXML
	private Label lblOccurrences;

	private Search search;
	
	private int occurrences = 0;
	
	private Stage window;

	public SearchInFileController(Search search) {

		FXMLLoader loader = new FXMLLoader();

		loader.setLocation(getClass().getResource("/zenit/searchinfile/SearchInFileWindow.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		window = new Stage();
		Scene scene = new Scene(this);

		window.initStyle(StageStyle.UNDECORATED);

		window.setScene(scene);
		
		window.setX(1050);
		window.setY(160);
		
		initialize();
		scene.getStylesheets()
				.add(getClass().getResource("/zenit/searchinfile/searchInFileDarkMode.css").toExternalForm());

		window.show();
		
		
		this.search = search;

	}
	
	private void makeNewSearch(String searchWord) {
		
		search.clearZen();
		
		occurrences = search.searchInFile(searchWord);
//		System.out.println("clear");
		if(occurrences < 1) {
			if(fldInputField.getText().length() > 0) {
				lblOccurrences.setText("0/" + occurrences);
			}else {
//				lblOccurrences.setText("0/" + occurrences);		vet inte vad som är snyggast
				lblOccurrences.setText("");
			}

		}else {
			lblOccurrences.setText("1/" + occurrences);
		}	
	}

	private void initialize() {
		
		fldInputField.textProperty().addListener((observable, oldValue, newValue) -> {	
			makeNewSearch(newValue);
		});
		
		btnReplaceAll.setPickOnBounds(true);
		btnReplaceAll.setOnAction(event -> 
			search.replaceAll(fldReplaceWord.getText()));
		
		btnReplaceOne.setPickOnBounds(true);
		btnReplaceOne.setOnAction(event -> 
			search.replaceOne(fldReplaceWord.getText()));
		
		btnUp.setPickOnBounds(true);
		btnUp.setOnAction(event ->{
			int i = search.jumpUp();
			i++;
			lblOccurrences.setText(i + "/" + occurrences);
		});
		
		btnDown.setPickOnBounds(true);
		btnDown.setOnAction(event -> {
			int i = search.jumpDown();
			i++;
			lblOccurrences.setText(i + "/" + occurrences);
		
		});
		
		btnEsc.setPickOnBounds(true);
		btnEsc.setOnAction(event -> window.close());
	}
}
