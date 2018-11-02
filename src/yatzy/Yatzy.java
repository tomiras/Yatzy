package yatzy;

import java.util.Random;

/**
 * Models a game of Yatzy. 
 */
public class Yatzy {
    /** 
     * Face values of the 5 dice. <br/>
     * 1 <= values[i] <= 6.
     */
    private int[] values = new int[5];

    /** 
     * Number of times the 5 dice have been thrown. <br/>
     * 0 <= throwCount <= 3.
     */
    private int throwCount = 0;

    /** 
     * Random number generator.
     */
    private Random random = new Random();
    
    /**
     * Rolls the 5 dice. <br/>
     * Only roll dice that are not hold. <br/>
     * Requires: holds contain 5 boolean values.
     */
    public void throwDice(boolean[] holds) {
        // TODO: Implement
        throwCount++;
        if(throwCount <= 3) {
            for (int i = 0; i < values.length; i++) {
                if (!holds[i]) {
                    int value = random.nextInt(6) + 1;
                    values[i] = value;
                }
            }
        }
    }

    /**
     * Returns the number of times the five dice have been thrown.
     */
    public int getThrowCount() {
        return throwCount;
    }
    
    /**
     * Resets the throw count.
     */
    public void resetThrowCount() {
        throwCount = 0;
    }
    
    /**
     * Get current dice values
     */
    public int[] getValues() {
        return values;
    }
    
    /**
     * Set the current dice values
     */
    public void setValues(int[] values) {
        this.values = values;
    }
    
    /**
     * Returns all results possible with the current face values. <br/>
     * The order of the results is the same as on the score board.
     */
    public int[] getPossibleResults() {
        int[] results = new int[15];
        for (int i = 0; i <= 5; i++) {
            results[i] = this.valueSpecificFace(i + 1);
        }
        results[6] = this.valueOnePair();
        results[7] = this.valueTwoPair();
        results[8] = this.valueThree();
        results[9] = this.valueFour();
        results[10] = this.valueFullHouse();
        results[11] = this.valueSmallStraight();
        results[12] = this.valueLargeStraight();
        results[13] = this.valueChance();
        results[14] = this.valueYatzy();
        return results;
    }
    
    /** 
     * Returns an int[7] containing the frequency of face values. <br/>
     * Frequency at index v is the number of dice with the face value v, 1 <= v <= 6. <br/>
     * Index 0 is not used.
     */
    public int[] freqFaceValue() {
        // TODO: Implement
        int[] freq = new int[7];
        int count = 0;
        for (int i = 1; i <= 6; i++){
            for(int j = 0; j < values.length; j++){
                if(i == values[j]){
                    count++;
                    freq[i] = count; //Only a value > 0 is inserted into the 'freq' array as all values are already 0 when initializing the array
                }
            }
            count = 0; //Counter is reset to 0 after each loop of value check
        }
        return freq;
    }

    /**
     * Returns the total value for the dice currently showing the given face value
     * @param face the face value to return values for
     */
    public int valueSpecificFace(int face) {
        // TODO: Implement
        int totValue = 0;
        //Looping true each element of 'values' array and if a match to input add the value of the element to totValue.
        // If no element matching input is found, totValue is 0
         for(int eyes : values){
            if(eyes == face){
                totValue += eyes;
            }
        }
        return totValue;
    }

    /**
     * Returns the maximum value for n-of-a-kind for the given n. <br/>
     * For example, valueManyOfAKind(3) returns the maximum value for 3 of-a-kind. <br/>
     * Requires: 1 <= faceValue and faceValue <= 6
     * @param n number of kind
     */
    public int valueManyOfAKind(int n) {
        // TODO: Implement
        //Looping true freqFaceValue() to get faceValue that occurs n times or less
        //For each faceValue that occurs n times or less is multiplied with n and the maximum value of that calculation is then returned
        //Reason for finding less frequency then n is to also find values that occurs more then n times so we can see max values of n*value for values
        //occurring more then n times
        //Eg. dice values 2, 2, 4, 4, 4 with n = 2 will make it possible to select the 2*4 instead of only 2*2
        int tmpValue = 0;
        int maxValue = 0;
        for (int faceValue = 1; faceValue <= 6; faceValue++){
            if(n <= freqFaceValue()[faceValue]){
                for(int j = 0; j < values.length; j++){
                    if(values[j] == faceValue){
                        tmpValue = values[j]*n;
                    }
                }
                if(tmpValue > maxValue){
                    maxValue = tmpValue;
                }
                tmpValue = 0;
            }
        }
        return maxValue;
    }

    /**
     * The current value if you try to score the current face values as Yatzy.
     */
    public int valueYatzy() {
        // TODO: Implement
        int yatzy = 0;
        if(valueManyOfAKind(5) > yatzy){
            yatzy = 50;
        }

        return yatzy;
    }

    /**
     * Returns the current score if used as "chance".
     */
    public int valueChance() {
        // TODO: Implement
        int chance = 0;
        for(int i = 0; i < values.length; i++){
            chance += values[i];
        }
        return chance;
    }

    /**
     * Returns the current score for one pair.
     */
    public int valueOnePair() {
        // TODO: Implement
        int onePair = valueManyOfAKind(2);
        return onePair;
    }

    /**
     * Returns the current score for two pairs.
     */
    public int valueTwoPair() {
        // TODO: Implement
        int count = 0;
        int tmpValue;
        int maxValueTwoPair = 0;
        for(int faceValue = 1; faceValue <= 6; faceValue++){
            if(freqFaceValue()[faceValue] >= 2){
                count++;
                tmpValue = faceValue*2;
                maxValueTwoPair += tmpValue;
            }
        }
        if (count == 2) {
            return maxValueTwoPair;
        }
        else return 0;
    }
    
    /**
     * Returns the current score for three of a kind.
     */
    public int valueThree() {
        // TODO: Implement
        int valueThree = valueManyOfAKind(3);
        return valueThree;
    }
    
    /**
     * Returns the current score for four of a kind.
     */
    public int valueFour() {
        // TODO: Implement
        int valueFour = valueManyOfAKind(4);
        return valueFour;
    }

    /**
     * Returns the value of a small straight with the current face values.
     */
    public int valueSmallStraight() {
        // TODO: Implement
        int correctHit = 0;
        //Looping true diceface 1-5 and checking if the maxValue of the current diceface is the same as the diceface it self among all dices.
        //If so the correctHit counter will increase with 1
        //If the correctHit count is 5 (meaning all diceface 1-5 have a maxValue of its own value and therefore exist only once)
        //It means dices 1, 2, 3, 4, 5 exists only 1 time
        for (int i = 1; i <= 5; i++) {
            if (valueSpecificFace(i) == i){
                correctHit++;
            }
        }
        if(correctHit == 5){
            return 15;
        }
        else {
            return 0;
        }
    }

    /**
     * Returns the value of a large straight with the current face values.
     */
    public int valueLargeStraight() {
        // TODO: Implement
        int correctHit = 0;
        //Looping true diceface 2-6 and checking if the maxValue of the current diceface is the same as the diceface among all dices.
        //If so the correctHit counter will increase with 1
        //If the correctHit count is 5 (meaning all diceface 2-6 have a maxValue of its own value and therefore exist only once)
        //If means dices 2, 3, 4, 5, 6 exists 1 time
        for (int i = 2; i <= 6; i++) {
            if (valueSpecificFace(i) == i){
                correctHit++;
            }
        }
        if(correctHit == 5){
            return 20;
        }
        else {
            return 0;
        }
    }

    /**
     * Returns the value of a full house with the current face values.
     */
    public int valueFullHouse() {
        // TODO: Implement
        int tmpValue;
        int fullHouse = 0;
        for(int i = 1; i <= 6; i++){
            if(freqFaceValue()[i] == 3){
                tmpValue = valueSpecificFace(i);
                for(int j = 1; j <= 6; j++){
                    if(freqFaceValue()[j] == 2){
                        fullHouse = tmpValue + valueSpecificFace(j);
                    }
                }
            }
        }
        return fullHouse;
    }

}
