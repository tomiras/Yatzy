package yatzy;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javax.management.StringValueExp;

public class MainAppGUI extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        Image imgIcon = new Image(ICON_IMG);

        stage.setTitle("Yatzy");
        stage.getIcons().add(imgIcon);
        GridPane pane = new GridPane();
        this.initContent(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    // -------------------------------------------------------------------------

    // The Yatzy game object
    private Yatzy yatzy = new Yatzy();
    // Shows the face values of the 5 dice.
    private TextField[] txfValues;
    // Shows the hold status of the 5 dice.
    private CheckBox[] chbHolds;
    private TextField[] txfResults;
    private Label[] lblResultLabels;
    private Label lblResult;
    private TextField txfSumSame, txfBonus, txfSumOther, txfTotal;
    private Label lblSumSame, lblBonus, lblSumOther, lblTotal;
    private Label lblRolled;
    private Button btnRoll;
    private int rolled = 0;
    private final String ICON_IMG = "dice_icon.png";

    // ---------------------------------------------------------------------

    private void initContent(GridPane pane) {
        pane.setGridLinesVisible(false);
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);

        // ---------------------------------------------------------------------

        GridPane dicePane = new GridPane();
        pane.add(dicePane, 0, 0);
        dicePane.setGridLinesVisible(false);
        dicePane.setPadding(new Insets(10));
        dicePane.setHgap(10);
        dicePane.setVgap(10);
        dicePane.setStyle("-fx-border-color: black");

        // ---------------------------------------------------------------------

        // TODO: initialize txfValues, chbHolds, btnRoll and lblRolled
        txfValues = new TextField[5];
        for(int i = 0; i < 5; i++){
            TextField txfDice = new TextField();
            txfDice.setPrefSize(50,50);
            txfDice.setDisable(true);
            txfDice.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            txfDice.setAlignment(Pos.CENTER);
            txfDice.setText(String.valueOf(i+1));
            txfValues[i] = txfDice;
            dicePane.add(txfDice, i+1, 1, 1,2);
        }

        chbHolds = new CheckBox[5];
        for(int i = 0; i < 5; i++){
            CheckBox chbHold = new CheckBox("Hold");
            chbHolds[i] = chbHold;
            dicePane.add(chbHold, i+1, 3, 1, 1);
        }

        btnRoll = new Button("Roll");
        dicePane.add(btnRoll, 4, 4, 1, 1);

        lblRolled = new Label("Rolled: " + rolled);
        dicePane.add(lblRolled, 5, 4, 1, 1);

        // ---------------------------------------------------------------------

        GridPane scorePane = new GridPane();
        pane.add(scorePane, 0, 1);
        scorePane.setGridLinesVisible(false);
        scorePane.setPadding(new Insets(10));
        scorePane.setVgap(5);
        scorePane.setHgap(10);
        scorePane.setStyle("-fx-border-color: black");

        // TODO: Initialize labels for results, txfResults,
        // labels and text fields for sums, bonus and total.
        lblResultLabels = new Label[15];
        String resultType;
        for(int i = 0; i < 15; i++){
            switch(i){
                case 0: resultType = "1's: "; break;
                case 1: resultType = "2's: "; break;
                case 2: resultType = "3's: "; break;
                case 3: resultType = "4's: "; break;
                case 4: resultType = "5's: "; break;
                case 5: resultType = "6's: "; break;
                case 6: resultType = "One Pair: "; break;
                case 7: resultType = "Two Pair: "; break;
                case 8: resultType = "Three Same: "; break;
                case 9: resultType = "Four Same: "; break;
                case 10: resultType = "Full House: "; break;
                case 11: resultType = "Small Straight: "; break;
                case 12: resultType = "Large Straight: "; break;
                case 13: resultType = "Change: "; break;
                case 14: resultType = "Yatzy: "; break;
                default: resultType = ""; break;
            }
            lblResult = new Label(resultType);
            lblResultLabels[i] = lblResult;
            scorePane.add(lblResult, 0, 1+i, 1, 1);
        }

        txfResults = new TextField[15];
        for (int i = 0; i < 15; i++){
            TextField txfResult = new TextField();
            txfResult.setPrefSize(50, 5);
            txfResults[i] = txfResult;
            scorePane.add(txfResult, 1, 1+i, 1, 1);
        }

        lblSumSame = new Label("Sum: ");
        lblBonus = new Label("Bonus: ");
        lblSumOther = new Label("Sum: ");
        lblTotal = new Label("Total: ");

        txfSumSame = new TextField();
        txfBonus = new TextField();
        txfSumOther = new TextField();
        txfTotal = new TextField();

        txfSumSame.setPrefSize(30, 5);
        txfBonus.setPrefSize(30, 5);
        txfSumOther.setPrefSize(30, 5);
        txfTotal.setPrefSize(30, 5);

        scorePane.add(lblSumSame, 2, 6, 1, 1);
        scorePane.add(txfSumSame, 3, 6, 1, 1);

        scorePane.add(lblBonus, 4,6, 1, 1);
        scorePane.add(txfBonus, 5, 6, 1, 1);

        scorePane.add(lblSumOther, 2, 15, 1, 1);
        scorePane.add(txfSumOther, 3, 15, 1, 1);

        scorePane.add(lblTotal, 4, 15, 1, 1);
        scorePane.add(txfTotal, 5, 15, 1, 1);
    }

    // -------------------------------------------------------------------------

    // TODO: Create a method for btnRoll's action.
    // Hint: Create small helper methods to be used in the action method.

    // -------------------------------------------------------------------------

    // TODO: Create a method for mouse click on one of the text fields in txfResults.
    // Hint: Create small helper methods to be used in the mouse click method.

}
