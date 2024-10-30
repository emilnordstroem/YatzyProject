package gui;

import javafx.scene.control.*;
import models.RaffleCup;
import models.Die;
import models.YatzyResultCalculator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

/*
    - Muligt at spille to spillere
        - Lav ekstra tekstfelt
    - Lav hold funktionalitet - radioButton
    - Lav Bonus og Total metode
        - Bonus: Se Bonus metode for beskrivelse
        - Total: sum + value of each label from index 7 - 16
    - Opdel funktionalitet i metoder - simplificer læsbarhed
 */


public class YatzyGui extends Application {
    private final RaffleCup raffleCup = new RaffleCup();
    private final ArrayList<Label> diceFaceLabelList = new ArrayList<>();

    // Integrate
    private final ToggleGroup holdDice = new ToggleGroup();
    private final ArrayList<RadioButton> radioButtonList = new ArrayList<>();

    private int counter;
    private final Label numberOfRolls = new Label("0");
    private final Button rollDiceButton = new Button("Throw dice");

    private final ArrayList<Label> tableLabelList = new ArrayList<>();
    private final ArrayList<TextField> textFieldList = new ArrayList<>();

    private final ArrayList<Integer> resultsList = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Yatzy");
        GridPane primaryPane = new GridPane();
        this.innerContents(primaryPane);
        Scene firstScene = new Scene(primaryPane);
        primaryStage.setScene(firstScene);
        primaryStage.show();
    }
    private void innerContents(GridPane pane) {
        // upper GUI elements
        GridPane diceBox = new GridPane();
        for (int row = 0; row < 3; row++) {
            for (int columnn = 0; columnn < 5; columnn++) {
                if (row == 0) {
                    Label diceFaceLabel = new Label();
                    diceFaceLabelList.add(diceFaceLabel);
                    diceBox.add(diceFaceLabel, columnn, row);
                } else if (row == 1){
                    RadioButton radioButton = new RadioButton("Hold");
                    radioButtonList.add(radioButton);
                    diceBox.add(radioButton, columnn, row);
                    // radioButton.setOnAction(e -> ); Need to make a method for holding a dice from changing
                }
            }

            if(row == 2) {
                Label numberOfRollsLabel = new Label("Number of throws:");
                HBox hbox = new HBox(numberOfRollsLabel, numberOfRolls, rollDiceButton);
                diceBox.add(hbox, 0,2);
            }
        }
        pane.add(diceBox, 0, 0);

        // User interactions
        rollDiceButton.setOnMouseClicked(e -> {
            if(counter < 3){
                Die[] raffleCup = throwRaffleCup();
                addDiceToLabel(raffleCup);
                updateNumberOfRolls();
                checkPotentialPoints(raffleCup);
            }

        });

        // lower GUI elements
        GridPane scoreBoardTable = new GridPane();

        String[] labels = {
                "1'ere", "2'ere", "3'ere", "4'ere", "5'ere", "6'ere", "Sum", "Bonus",
                "Et par", "To par", "3 ens", "4 ens", "Lille straight", "Store straight",
                "Fuldt hus", "Chance", "Yatzy", "Total"
        };

        for(int row = 0; row < labels.length; row++){
            Label label = new Label(labels[row]);
            TextField textField = new TextField();
            tableLabelList.add(label);
            textFieldList.add(textField);
            scoreBoardTable.add(label, 0, row);
            scoreBoardTable.add(textField, 1, row);
        }
        pane.add(scoreBoardTable,0,1);
    }

    private Die[] throwRaffleCup(){
        this.raffleCup.throwDice();
        return raffleCup.getDice();
    }

    private void addDiceToLabel(Die[] raffleCup){
        for(int index = 0; index < raffleCup.length; index++){
            String currentDiceEyes = toString(raffleCup[index].getEyes());
            diceFaceLabelList.get(index).setText(currentDiceEyes);
        }
    }

    private void updateNumberOfRolls(){
        this.counter++;
        numberOfRolls.setText(toString(counter));
    }

    private void checkPotentialPoints(Die[] raffleCup){
        YatzyResultCalculator currentThrow = new YatzyResultCalculator(raffleCup);

        resultsList.add(currentThrow.upperSectionSum());
        resultsList.add(bonusScore());
        resultsList.add(currentThrow.onePairScore());
        resultsList.add(currentThrow.twoPairScore());
        resultsList.add(currentThrow.threeOfAKindScore());
        resultsList.add(currentThrow.fourOfAKindScore());
        resultsList.add(currentThrow.smallStraightScore());
        resultsList.add(currentThrow.largeStraightScore());
        resultsList.add(currentThrow.fullHouseScore());
        resultsList.add(currentThrow.chanceScore());
        resultsList.add(currentThrow.yatzyScore());
        resultsList.add(totalScore());

        for (int index = 0; index < textFieldList.size(); index++) {
            if(index < 6){
                int currentDice = index + 1;
                String upperScore = toString(currentThrow.upperSectionScore(currentDice));
                textFieldList.get(index).setText(upperScore);
            } else {
                String text = toString(resultsList.get(index - 6)); // Defaults the index to 0 from 6
                textFieldList.get(index).setText(text);
            }
        }
    }

    private int bonusScore(){
        // iterate thorugh first label index 0-5
        // if average of each diceface is 3
        // return 50 points
        return 0;
    }

    private int totalScore(){
        return 0;
    }

    private String toString(int eyes) {
        return Integer.toString(eyes);
    }
}
