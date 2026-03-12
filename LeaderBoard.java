package MCQGame;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LeaderBoard extends Application {
    
    @Override
    public void start(Stage stage) throws Exception{
        //Label MainLabel = new Label(" Kill yourself");


        TableView<ObservableList<String>> table = new TableView<>();

        
        String[] headers = {"Score%", "Name", "Time taken"};
        for (int i = 0; i < headers.length; i++) {
            final int col = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(headers[i]);
            column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get(col)));
            table.getColumns().add(column);
        }
        
        //column.setPrefWidth(150);
        Label MainLabel = new Label("Hi");
        MainLabel.setTextFill(Color.WHITE);
        MainLabel.setAlignment(Pos.TOP_CENTER);

        MainLabel.setFont(new Font("Times New Roman",15));


        Image backgroundImage = new Image("file:\\D:\\Java\\MCQGame\\lbbgpic.jpg"); // Correct file path usage
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setPreserveRatio(false); // Allow full stretch
        backgroundView.setFitWidth(600); // Set width to match scene width
        backgroundView.setFitHeight(400); // Set height to match scene height

        backgroundView.fitWidthProperty().bind(stage.widthProperty());
        backgroundView.fitHeightProperty().bind(stage.heightProperty());



        LoadLeaderBoard(table);

        //VBox root = new VBox(table);
        //root.setPrefSize(300, 400);

        StackPane bglol = new StackPane(table);

        Scene scene = new Scene(bglol, 255, 400);

        scene.getStylesheets().add(getClass().getResource("Button-style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Leader Board");
        stage.setResizable(false);
        stage.show();
    }


    public String[][] Sort(String[][] DArray){
        /*Uses insertion sort to sort the leaderboard from highest to lowest score */

        for (int i = 1; i < DArray.length; i++) {
            String[] key = DArray[i];
            int j = i - 1;
    
            while (j >= 0 && Integer.parseInt(DArray[j][0]) <= Integer.parseInt(key[0])) {

                if(Integer.parseInt(DArray[j][0]) == Integer.parseInt(key[0]) && Integer.parseInt(DArray[j][2]) < Integer.parseInt(key[2])){
                    break;
                }

                DArray[j + 1] = DArray[j];
                j = j - 1;
            }
            DArray[j + 1] = key;
        }

        return DArray;
    
    }

    public void LoadLeaderBoard(TableView<ObservableList<String>> table) throws UnknownHostException, IOException{

        try{

            int numPos = 1;
            String leaderBaord = "LEADER BOARD \n User | Score% | Seconds\n";

            Socket serverSocket = new Socket("localhost", 12345);
            System.out.println("Server started. Waiting for client to connect...");

            //Socket ServerSocket = serverSocket.accept();
            System.out.println("Client connected.");

            PrintWriter out = new PrintWriter(serverSocket.getOutputStream());
            out.println(0);
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

            String strArr = in.readLine();

            String[][] LeaderArray = new Gson().fromJson(strArr, String[][].class);

            System.out.println(Arrays.deepToString(LeaderArray));

            in.close();
            out.close();

            /*
            while(numPos - 1 < LeaderArray.length && numPos <= 5){
                leaderBaord = leaderBaord + numPos + ": " + LeaderArray[numPos - 1][1] + " | " + LeaderArray[numPos - 1][0] + " | " + LeaderArray[numPos - 1][2] + '\n';
                numPos++;
            }
            */

            ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        
            for(String[] row : LeaderArray){

                ObservableList<String> obersvRow = FXCollections.observableArrayList();
                obersvRow.addAll(Arrays.asList(row));
                data.add(obersvRow);
            }

            //data.add(FXCollections.observableArrayList("85", "Alice", "2m 30s"));

            table.setItems(data);
            //toLoad.setText(leaderBaord);

            serverSocket.close();
        }      
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}


