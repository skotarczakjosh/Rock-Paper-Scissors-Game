/*
File: RockPaperScissorsGame.java
Description: A simple Rock,Paper,Scissors game GUI.
Author: Joshua Skotarczak
 */
package com.example.project2joshskot;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class RockPaperScissorsGame extends Application {
    // Game Variables
    private int numberRounds = 0;
    private int currentRounds = 0;
    private int numberWins = 0;
    private int numberLosses = 0;
    private int numberTies = 0;
    private String userChoice;
    private String computerChoice;
    // Game Labels
    private final Label gameDirections = createLabel( "",Color.BLACK,15);
    private final Label numRounds = createLabel(Integer.toString(currentRounds),Color.BLACK,15);
    private final Label numWins =  createLabel(Integer.toString(numberWins),Color.BLACK,15);
    private final Label numLosses =  createLabel(Integer.toString(numberLosses),Color.BLACK,15);
    private final Label numTies =  createLabel(Integer.toString(numberTies),Color.BLACK,15);
    public static Button createButton(String text, int height) {
        Button button = new Button(text);
        button.setPrefWidth(Region.USE_COMPUTED_SIZE); // Conform
        button.setPrefHeight(height);
        button.setPadding(new Insets(10));
        button.setFont(Font.font("Times New Roman", FontWeight.BOLD, 17));
        return button;
    }
    // Reusable Label Function
    public static Label createLabel(String text, Color color, int fontsize) {
        Label label = new Label(text);
        label.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, fontsize));
        label.setTextFill(color);
        label.setWrapText(true);
        return label;
    }
    @Override
    public void start(Stage stage){
        // Stop User Resizing of Window
        stage.setResizable(false);
        //Begin Scene
        // Labels
        Label homeScreenLabel = createLabel("Rock, Paper, Scissors", Color.BLUE, 40);
        homeScreenLabel.setAlignment(Pos.CENTER);
        Label nameLabel = createLabel("Name:",Color.BLACK,20);
        Label roundLabel = createLabel("Rounds:", Color.BLACK, 20);
        Label nameError = createLabel("", Color.RED, 20);
        Label roundError = createLabel("", Color.RED, 20);
        // Text Fields
        TextField nameTextBox = new TextField();
        TextField roundTextBox = new TextField();
        nameTextBox.setFont(Font.font("Times New Roman", FontWeight.MEDIUM, 15));
        roundTextBox.setFont(Font.font("Times New Roman", FontWeight.MEDIUM, 15));
        // Buttons
        Button beginButton = createButton("Begin Game", 5);
        beginButton.setAlignment(Pos.CENTER);
        // Grid Pane
        GridPane homeScreen = new GridPane();
        homeScreen.setAlignment(Pos.CENTER);
        homeScreen.setPadding(new Insets(10));
        homeScreen.add(nameLabel, 0,0);
        homeScreen.add(nameTextBox,0,1);
        homeScreen.add(roundLabel,0,2);
        homeScreen.add(roundTextBox, 0,3);
        homeScreen.add(nameError,0,4);
        homeScreen.add(roundError,0,5);
        // Column Constraints To Line Everything Up !
        homeScreen.getColumnConstraints().addAll( new ColumnConstraints(300));
        // Home Root!
        VBox homeRoot = new VBox(homeScreenLabel, homeScreen, beginButton);
        homeRoot.setAlignment(Pos.CENTER);
        homeRoot.setSpacing(10);
        Scene homeScene = new Scene(homeRoot, 500, 300);
        stage.setTitle("Rock, Paper, Scissors");
        stage.setScene(homeScene);
        stage.show();
        //Play Game Scene
        // Images
        Image rockIcon = new Image("file:src/main/resources/images/ROCK.png",150,0,true,true);
        ImageView rockView = new ImageView(rockIcon);
        Image paperIcon = new Image("file:src/main/resources/images/PAPER.png",150,0,true,true);
        ImageView paperView = new ImageView(paperIcon);
        Image scissorsIcon = new Image("file:src/main/resources/images/SCISSORS.png",150,0,true,true);
        ImageView scissorsView = new ImageView(scissorsIcon);
        // Game Info Labels
        Label roundsLabel = createLabel("Rounds: ",Color.BLACK,15);
        Label winsLabel = createLabel("Wins: ",Color.BLACK,15);
        Label lossesLabel = createLabel("Losses: ",Color.BLACK,15);
        Label tiesLabel = createLabel("Ties: ",Color.BLACK,15);
        // Game Buttons
        Button resetButton = createButton("Reset Game", 5);
        Button menuButton = createButton("Main Menu", 5);
        // Game Layout
        GridPane gameScreen = new GridPane();
        gameScreen.setHgap(15);
        gameScreen.setVgap(15);
        gameScreen.setPadding(new Insets(10));
        gameScreen.add(rockView, 0, 0);
        gameScreen.add(paperView, 1, 0);
        gameScreen.add(scissorsView, 2, 0);
        gameScreen.add(gameDirections, 0,1,3,1);
        gameDirections.setAlignment(Pos.CENTER); // Cant Seem to get the game directions to center
        // Game Info
        HBox gameInfo = new HBox(new HBox(roundsLabel,numRounds), new HBox(winsLabel,numWins),
                new HBox(lossesLabel,numLosses), new HBox(tiesLabel,numTies));
        gameInfo.setAlignment(Pos.CENTER);
        gameInfo.setSpacing(40);
        gameScreen.add(gameInfo, 0, 2, 3, 2);
        // Game Buttons
        HBox gameButtons = new HBox(resetButton,menuButton);
        gameButtons.setSpacing(20);
        gameButtons.setAlignment(Pos.CENTER);
        gameScreen.add(gameButtons,0,4,3,4);
        // Game Screen
        VBox playRoot = new VBox(gameScreen);
        playRoot.setAlignment(Pos.CENTER);
        playRoot.setSpacing(10);
        playRoot.setPadding(new Insets(10));
        Scene playGameScene = new Scene(new StackPane(playRoot), 500, 300);
        // PlayGame Lambda Expression
        beginButton.setOnAction(e -> {
            String name = nameTextBox.getText();
            String rounds = roundTextBox.getText();
            if(name.isEmpty()){
                nameError.setText("Name cannot be blank.");
            } else {
                nameError.setText("");
            }
            //https://stackoverflow.com/questions/2841550/what-does-d-mean-in-a-regular-expression
            if (!rounds.matches("\\d+")){ // \d+ is a character class that represents any digits,\\ for many num
                roundError.setText("Number of rounds must be a positive integer.");
            } else {
                roundError.setText("");
            }
            if(!(name.isEmpty() || !rounds.matches("\\d+"))){
                numberRounds = Integer.parseInt(rounds);
                stage.setScene(playGameScene);
                gameDirections.setText(nameTextBox.getText() + ", select image to begin.");
            }
        });
        // Rock Image On Mouse Click
        rockView.setOnMouseClicked(e -> playGame(1));
        // Paper Image On Mouse Click
        paperView.setOnMouseClicked(e -> playGame(2));
        // Scissors Image On Mouse Click
        scissorsView.setOnMouseClicked(e -> playGame(3));
        // Reset Game Button
        resetButton.setOnAction(e -> {
            // Reset ALl Variables
            resetGameInfo();
            gameDirections.setText(nameTextBox.getText() + ", select image to begin.");
        });
        // Main Menu Button
        menuButton.setOnAction(e -> {
            // Reset ALl Variables
            resetGameInfo();
            // Revert Back to Main Menu
            stage.setScene(homeScene);
        });
    }
    public static void main(String[] args) {
        launch();
    }
    private void playGame(int choice){
        // Check if Number Rounds Has Reached its limit
        if(currentRounds == numberRounds){
            gameDirections.setText("Game Over!");
        } else{
            //Increment Rounds
            currentRounds++;
            // Get Computer Choice
            int computerChoice = computerChoice();
            // Declare Winner
            declareWinner(choice,computerChoice);
        }
    }
    private int computerChoice() {
        Random random = new Random();
        int num = random.nextInt(3);
        return switch (num) {
            case 0 -> 1;  //ROCK
            case 1 -> 2;  //PAPER
            case 2 -> 3;  //SCISSORS
            default -> 1;
        };
    }
    private void declareWinner(int user, int computer){
        numRounds.setText(Integer.toString(currentRounds));
        updateChoices(user,computer); // Update Labels
        String result;
        if(user == computer){
            numberTies++;
            numTies.setText(Integer.toString(numberTies));
            result = "You Tie!";
        } else if(user == (computer % 3)+1){  // Computer Choice Mod 3 then Add 1 to condense logic to determine win
            numberWins++;
            numWins.setText(Integer.toString(numberWins));
            result = "You Win!";
        } else {
            numberLosses++;
            numLosses.setText(Integer.toString(numberLosses));
            result = "You Lose!";
        }
        // Update Game Directions
        gameDirections.setText("You chose "+ userChoice +". Computer chose " + computerChoice + ". " + result);
    }
    private void updateChoices(int user, int computer){
        Map<Integer, String> choice = new LinkedHashMap<>();
        choice.put(1,"Rock");
        choice.put(2,"Paper");
        choice.put(3,"Scissors");
        userChoice = choice.get(user);
        computerChoice = choice.get(computer);
    }
    private void resetGameInfo(){
        currentRounds = 0;
        numberWins = 0;
        numberLosses = 0;
        numberTies = 0;
        numRounds.setText("0");
        numWins.setText("0");
        numLosses.setText("0");
        numTies.setText("0");
    }
}