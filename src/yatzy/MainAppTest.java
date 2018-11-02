package yatzy;

public class MainAppTest {
    public static void main(String[] args) {
        boolean[] holds1 = {false, false, false, false, false};
        boolean[] holds2 = {true, true, false, false, false};
        boolean[] holds3 = {true, true, true, false, false};

        boolean[] holds4 = {true, true, true, true, false};
        boolean[] holds5 = {false, false, false, false, false};

        int[] dices = {6, 6, 2, 5, 1};

        Yatzy yatzyGame = new Yatzy();
        yatzyGame.setValues(dices);

        yatzyGame.throwDice(holds2);
        TestValues(yatzyGame);
        //TestFreq(yatzyGame); //Method freqFaceValue needs to be change to public to be able to be used in TestMainApp
        //TestTotValue(yatzyGame, 4);
        //TestValueManyOfAKind(yatzyGame, 4);
        //TestValues(yatzyGame);
        TestGame(yatzyGame);

        yatzyGame.throwDice(holds2);
        TestValues(yatzyGame);
        //TestFreq(yatzyGame); //Method freqFaceValue needs to be change to public to be able to be used in TestMainApp
        //TestTotValue(yatzyGame, 4);
        //TestValueManyOfAKind(yatzyGame, 3);
        TestGame(yatzyGame);

        //yatzyGame.throwDice(holds3);
        //TestValues(yatzyGame);

        //yatzyGame.throwDice(holds4);
        //TestValues(yatzyGame);

        //yatzyGame.resetThrowCount();

        //yatzyGame.throwDice((holds5));
        //TestValues(yatzyGame);
    }

    static void TestValues(Yatzy yatzyGame){
        /**
         * Test function to print out content of dice values
         */
        System.out.println();
        System.out.printf("Dices : ");
        for (int i = 0; i < yatzyGame.getValues().length; i++){
            System.out.printf("%12d ",yatzyGame.getValues()[i]);
        }
        System.out.println();
    }


    static void TestFreq(Yatzy yatzyGame){
        for(int i = 1; i <= 6; i++){
            System.out.println(i + " occurs " + yatzyGame.freqFaceValue()[i] + " times");
        }
    }


    static void TestTotValue(Yatzy yatzyGame, int face){
        System.out.println("Total værdi af " + face + " : " + yatzyGame.valueSpecificFace(face));
    }

    static void TestValueManyOfAKind(Yatzy yatzyGame, int n){
        System.out.println("Maximum værdi af øjne, som findes " + n + " gange: " + yatzyGame.valueManyOfAKind(n));
    }

    static void TestGame(Yatzy yatzyGame){
        String resultType;
        for(int i = 0; i <= 14; i++){
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
            System.out.println(resultType + yatzyGame.getPossibleResults()[i]);
        }
    }


}
