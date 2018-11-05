package yatzy;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javax.management.StringValueExp;
import java.util.Optional;

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
        stage.setResizable(false);
        stage.show();
    }

    // -------------------------------------------------------------------------

    // Private class to help interact with the Yatzy game object and the GUI
    private Controller controller = new Controller();
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
    private Label lblGameStatus;
    private int rolled = 0;
    private final String ICON_IMG = "dice_icon.png";
    private final String GAMES_IMG = "play_game.png";
    private boolean txfResultIsFocus = false; //Variable to handle if a Result Textfield has already been selected

    private HBox gfxBox;
    private ToggleGroup gfxGroup;
    private Label lblGfx;

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
        for(int i = 0; i < txfValues.length; i++){
            TextField txfDice = new TextField();
            txfDice.setPrefSize(50,50);
            txfDice.setEditable(false);
            txfDice.setDisable(true);
            txfDice.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            txfDice.setAlignment(Pos.CENTER);
            txfValues[i] = txfDice;
            dicePane.add(txfDice, i+1, 1, 1,2);
        }

        chbHolds = new CheckBox[5];
        for(int i = 0; i < 5; i++){
            CheckBox chbHold = new CheckBox("Hold");
            chbHold.setDisable(true);
            chbHolds[i] = chbHold;
            dicePane.add(chbHold, i+1, 3, 1, 1);
        }

        lblGameStatus = new Label("Press Roll to start");
        dicePane.add(lblGameStatus, 1, 4, 2, 1);

        btnRoll = new Button("Roll");
        dicePane.add(btnRoll, 4, 4, 1, 1);

        lblRolled = new Label("Rolled: " + rolled);
        dicePane.add(lblRolled, 5, 4, 1, 1);

        // ---------------------------------------------------------------------

        GridPane gfxSelectPane = new GridPane();
        pane.add(gfxSelectPane, 0, 1);
        gfxSelectPane.setGridLinesVisible(false);
        gfxSelectPane.setPadding(new Insets(10));
        gfxSelectPane.setVgap(10);
        gfxSelectPane.setHgap(20);
        gfxSelectPane.setStyle("-fx-border-color: black");

        lblGfx = new Label("Select dice style");
        gfxSelectPane.add(lblGfx, 0, 1, 1, 1);

        gfxBox = new HBox();
        gfxBox.setSpacing(30.0);
        gfxGroup = new ToggleGroup();
        RadioButton rb;
        String[] diceGfx = { "Numbers", "Cubes"};
        for(int i = 0; i < diceGfx.length; i++){
            rb = new RadioButton();
            rb.setToggleGroup(gfxGroup);
            rb.setText(diceGfx[i]);
            gfxBox.getChildren().add(rb);
        }

        gfxSelectPane.add(gfxBox,2, 1, 6, 1);



        // ---------------------------------------------------------------------

        GridPane scorePane = new GridPane();
        pane.add(scorePane, 0, 2);
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

        //Initializing the 15 different result Textfield in the scorepane and
        // giving them actions depending on if a txfResult Textfield is selected or not to be able to de-select a Textfield
        txfResults = new TextField[15];
        for (int i = 0; i < 15; i++){
            TextField txfResult = new TextField();
            txfResult.setPrefSize(50, 5);
            txfResult.setDisable(true);
            txfResult.setOnMouseClicked(event -> {
                if(event.getButton() == MouseButton.PRIMARY)
                    if(txfResultIsFocus){
                        this.controller.deSelectResultAction(scorePane);
                    }
                    else {
                        this.controller.selectResultAction();
                    }
                if(event.getButton() == MouseButton.MIDDLE)
                    this.controller.easterEggAction();
            });
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

        txfSumSame.setDisable(true);
        txfBonus.setDisable(true);
        txfSumOther.setDisable(true);
        txfTotal.setDisable(true);

        txfSumSame.setPrefSize(40, 5);
        txfBonus.setPrefSize(40, 5);
        txfSumOther.setPrefSize(40, 5);
        txfTotal.setPrefSize(40, 5);

        scorePane.add(lblSumSame, 2, 6, 1, 1);
        scorePane.add(txfSumSame, 3, 6, 1, 1);

        scorePane.add(lblBonus, 4,6, 1, 1);
        scorePane.add(txfBonus, 5, 6, 1, 1);

        scorePane.add(lblSumOther, 2, 15, 1, 1);
        scorePane.add(txfSumOther, 3, 15, 1, 1);

        scorePane.add(lblTotal, 4, 15, 1, 1);
        scorePane.add(txfTotal, 5, 15, 1, 1);

        btnRoll.setOnAction(event -> {
            if(!this.controller.getGameStatus())
                this.controller.startGameAction();
            else
                this.controller.rollDiceAction();
        });
    }

    // ---------------------------------------------------------------------

    /**
     * This class controls access to the model in this application. In this
     * case, the model is a Yatzy game object that controls dice rolls and possible scores for each dice roll.
     * The controller class itself maintain accumulated points and game rules.
     */
    private class Controller{
        // The Yatzy game object
        private Yatzy yatzy = new Yatzy();
        private boolean gameStarted = false; //Variable for btnRoll.setOnAction to check if it needs to call startGameAction() or just rollDiceAction()
        private boolean gameEnded = false; //Variable to check if startGameAction() can start the game or not.
        private int resultSelected; //Index variable to use for disabling a txfResult Textfield in txfResults array when calling rollDiceAction()
        private int throwCount; //Counter for maintaining throws per count | throwCount <= MAX_ROLLS
        private int roundCount; //Counter for maintaining amount of rounds | roundCount <= MAX_ROUNDS
        private boolean isSelected; //Check variable to check if a txtResult Textfield has been selected

        //Variables to maintain accumulated scores
        private int sumSame;
        private int sumBonus;
        private int sumOther;
        private int sumTotal;

        private final int BONUS_REG = 63; //Required point to get bonus point
        private final int BONUS = 50; //Bonus point

        private final int MAX_ROLLS = 3; //Maximum rolls per round.
        private final int MAX_ROUNDS = 15; //Maximum rounds possible before game ends

        private boolean getGameStatus(){
            return gameStarted;
        }

        /**
         * Method to initialize all sum variables and enabling fields
         */
        private void startGameAction(){
            if(!gameEnded) {
                resetRound();

                gameStarted = true;
                isSelected = false;

                sumSame = 0;
                sumBonus = 0;
                sumOther = 0;
                sumTotal = 0;

                txfSumSame.setText(String.valueOf(sumSame));
                txfBonus.setText(String.valueOf(sumBonus));
                txfSumOther.setText(String.valueOf(sumOther));
                txfTotal.setText(String.valueOf(sumTotal));

                for (int i = 0; i < 5; i++) {
                    txfValues[i].setDisable(false);
                }

                for (int i = 0; i < chbHolds.length; i++) {
                    chbHolds[i].setDisable(false);
                }
                for (int i = 0; i < txfResults.length; i++) {
                    txfResults[i].setDisable(false);
                    txfResults[i].setEditable(false);
                }

                gfxGroup.getToggles().forEach(toggle -> {
                    RadioButton rb = (RadioButton) toggle ;
                    rb.setDisable(true);
                });

                txfSumSame.setDisable(false);
                txfSumSame.setEditable(false);
                txfBonus.setDisable(false);
                txfBonus.setEditable(false);
                txfSumOther.setDisable(false);
                txfSumOther.setEditable(false);
                txfTotal.setDisable(false);
                txfTotal.setEditable(false);

                btnRoll.setDisable(false);

                lblGameStatus.setText("Game in Progress");

                roundCount = 1;

                //Rolling the dices
                rollDiceAction();
            }
        }

        // -------------------------------------------------------------------------

        // TODO: Create a method for btnRoll's action.
        // Hint: Create small helper methods to be used in the action method.

        /**
         * Method to roll dices and maintain visual output for dice and hold textfields updated
         */
        private void rollDiceAction(){
            if (roundCount <= MAX_ROUNDS) {
                //Checking if a txtResult has not been selected and count of throws is 3 - If so user is alerted and is forced to select a txtResult Textfield
                if (!isSelected && throwCount >= MAX_ROLLS) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Select Result");
                    alert.setHeaderText("No Result selected");
                    alert.setContentText("Select a desired and available result");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(ICON_IMG));
                    alert.show();
                } else if (isSelected) {
                    resetRound();
                }
                //Calculating and setting scores from the 4 sum Textfields
                calcScores();

                txfSumSame.setText(String.valueOf(sumSame));
                txfBonus.setText(String.valueOf(sumBonus));
                txfSumOther.setText(String.valueOf(sumOther));
                txfTotal.setText(String.valueOf(sumTotal));

                //Disabling the selected txtResult Textfield in txtResults array at 'resultSelect' index if
                // dices has been thrown and a txfResult Textfield has been selected
                if (throwCount > 0 && isSelected) {
                    txfResults[resultSelected].setDisable(true);
                }

                //Generating array for dices that are being hold
                boolean[] holds = new boolean[chbHolds.length];
                for (int i = 0; i < chbHolds.length; i++) {
                    holds[i] = chbHolds[i].isSelected();
                }

                yatzy.throwDice(holds);

                throwCount = yatzy.getThrowCount();
                isSelected = false;

                getDices();
                getRolls();
                getResults();

            }
            else {
                endGame();
            }
        }

        /**
         * Method to resetting all holds when a result as been selected and increasing roundCount +1
         */
        private void resetRound(){
            for (int i = 0; i < chbHolds.length; i++) {
                chbHolds[i].setSelected(false);
            }
            yatzy.resetThrowCount();
            roundCount++;
        }

        /**
         * Method for maintaining dialog and actions when a game has ended | roundCount <= MAX_ROUNDS
         */
        private void endGame(){
            gameEnded = true;
            lblGameStatus.setText("Game ended");
            btnRoll.setDisable(true);
            txfSumSame.setDisable(true);
            txfSumOther.setDisable(true);
            txfBonus.setDisable(true);
            txfTotal.setDisable(true);
            for (int i = 0; i < 5; i++) {
                txfValues[i].setDisable(true);
            }

            for (int i = 0; i < chbHolds.length; i++) {
                chbHolds[i].setDisable(true);
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("GAME OVER");
            alert.setHeaderText("The game has ended \n" + "Total score is: " + txfTotal.getText());
            alert.setContentText("Would you like to play another game David?");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(ICON_IMG));

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                gameEnded = false;
                startGameAction();
            }
        }

        /**
         * Method to calculate and set values for the 4 different totals
         * Calculation is done at dicerolls or 'game end' as this makes it easier to manipulate the live Textfields at runtime
         */
        private void calcScores(){
            if(txfSumSame.getText().trim().isEmpty()){
                sumSame = 0;
            }
            else{
                sumSame = Integer.valueOf(txfSumSame.getText().trim());
            }

            if(txfSumOther.getText().trim().isEmpty()){
                sumOther = 0;
            }
            else{
                sumOther = Integer.valueOf((txfSumOther.getText().trim()));
            }
            if(txfBonus.getText().trim().isEmpty()){
                sumBonus = 0;
            }
            else{
                sumBonus = Integer.valueOf((txfBonus.getText().trim()));
            }
            if(txfTotal.getText().trim().isEmpty()){
                sumTotal = 0;
            }
            else{
                sumTotal = Integer.valueOf((txfTotal.getText().trim()));
            }
        }

        /**
         * Method to show the values of the 5 dices from the Yatzy game object in the 5 txfDice Textfields
         */
        private void getDices(){
            for(int i = 0; i < txfValues.length; i++){
                txfValues[i].setText(String.valueOf(yatzy.getValues()[i]));
            }
        }

        private void getCubes(){

        }

        /**
         * Method to update the lblRolled Label with amount of rolls.
         */
        private void getRolls(){
            if(throwCount <= 3)
                lblRolled.setText("Rolled: " + throwCount);
        }

        /**
         * Method to update the txfResult Textfields with possible results from the yatzy game. Only Textfields in the txfResults array
         * that is not disabled from the rollDiceAction() method will be set with a value.
         */
        private void getResults(){
            for(int i = 0; i < txfResults.length; i++){
                if(!txfResults[i].isDisabled()) {
                    txfResults[i].setText(String.valueOf(yatzy.getPossibleResults()[i]));
                }
            }
        }

        // -------------------------------------------------------------------------

        // TODO: Create a method for mouse click on one of the text fields in txfResults.
        // Hint: Create small helper methods to be used in the mouse click method.

        /**
         * Method to set accumulated values in the accumulated Textfields.
         * Setting the index value of txfResults (resultSelected) for rollDiceAction()
         * Setting txfResultIsFocus for the txfResult.setOnMouseClicked event.
         * Setting isSelected for rollDiceAction() to check if a Texfield needs to be disabled.
         *
         */
        private void selectResultAction(){
            int sameSum = 0;
            int otherSum = 0;
            for(int i = 0; i < 15; i++){
                if(txfResults[i].isFocused() && i < 6){
                    sameSum = Integer.valueOf(txfResults[i].getText().trim());
                    resultSelected = i; //Index of the selected Textfield in txfResults array
                    txfResultIsFocus = true;
                }
                else if(txfResults[i].isFocused() && i > 5){
                    otherSum = Integer.valueOf(txfResults[i].getText().trim());
                    resultSelected = i; //Index of the selected Textfield in txfResults array
                    txfResultIsFocus = true;
                }
            }

            txfSumSame.setText(String.valueOf(sumSame+sameSum));
            txfSumOther.setText(String.valueOf(sumOther+otherSum));
            isSelected = true;

            setBonus();
            setTotal();

        }

         /**
         * Method to remove focus from a Result Textfield and reverse any accumulated values from select action
          * Setting isSelected for rollDiceAction() to false
          * Taking the current scorePane Gridpane as parameter to be able to force focus the overall scorePane.
         * @param scorePane
         */
        private void deSelectResultAction(GridPane scorePane){
            for(int i = 0; i < 15; i++){
                if(txfResults[i].isFocused() && i < 6){
                    resultSelected = i; //Index of the selected Textfield in txfResults array
                    txfResultIsFocus = false;
                }
                else if(txfResults[i].isFocused() && i > 5){
                    resultSelected = i; //Index of the selected Textfield in txfResults array
                    txfResultIsFocus = false;
                }
            }

            txfSumSame.setText(String.valueOf(sumSame));
            txfSumOther.setText(String.valueOf(sumOther));

            isSelected = false;
            scorePane.requestFocus();

            setBonus();
            setTotal();

        }

        /**
         * Method for setting the txfBonus Textfield = 50 if accumulated sum of txfSumSame is min. 63
         */
        private void setBonus(){
            if(Integer.valueOf(txfSumSame.getText().trim()) >= BONUS_REG) {
                txfBonus.setText(String.valueOf(BONUS));
            }
            else
                txfBonus.setText(String.valueOf(sumBonus));
        }

        /**
         * Method for setting txfTotal for all accumulated Textfields
         */
        private void setTotal(){
            int total = Integer.valueOf(txfSumSame.getText().trim()) + Integer.valueOf(txfSumOther.getText().trim()) + Integer.valueOf(txfBonus.getText().trim());
            txfTotal.setText(String.valueOf(total));
        }

        private void easterEggAction(){
            Image easter = new Image(GAMES_IMG);
            ImageView gameImageView = new ImageView(easter);
            //gameImageView.setFitWidth(256);
            //gameImageView.setFitHeight(256);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PLAY GAME!");
            alert.setGraphic(gameImageView);
            alert.setContentText(null);
            alert.setHeaderText(null);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(ICON_IMG));
            alert.show();
        }
    }
}



