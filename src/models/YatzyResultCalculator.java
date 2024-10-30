package models;

import java.util.Arrays;

/**
 * Used to calculate the score of throws with 5 dice
 */
public class YatzyResultCalculator {
    private final int[] diceFaceArray = new int[6];
    private final Die[] dice;

    /**
     *
     * @param dice
     */
    public YatzyResultCalculator(Die[] dice) {
        this.dice = dice;
        for(int diceIndex = 0; diceIndex < dice.length; diceIndex++){
            diceFaceArray[dice[diceIndex].getEyes() - 1]++;
        }
    }

    /**
     * Calculates the score for Yatzy uppersection
     * @param eyes eye value to calculate score for. eyes should be between 1 and 6
     * @return the score for specified eye value
     */
    public int upperSectionScore(int eyes) {
        return diceFaceArray[eyes - 1] * eyes;
    }

    public int onePairScore() {
        int seekPair = 2; // The pair we seek
        for(int diceIndex : diceFaceArray){ // Don't understand: Works with descending classic for-loop and casual for each
            if(diceIndex >= seekPair){
                int diceFace = diceIndex + 1;
                return sumOfPair(diceFace, seekPair);
            }
        }
        return 0;
    }

    public int twoPairScore() {
        int seekPair = 2; // The pair we seek
        int pairCounter = 0;
        int sum = 0;

        for(int diceIndex = 0; diceIndex < diceFaceArray.length; diceIndex++){
            if(diceFaceArray[diceIndex] >= seekPair){
                int diceFace = diceIndex + 1;
                sum += sumOfPair(diceFace, seekPair);
                pairCounter++;
            }
            if(pairCounter == 2){
                return sum;
            }
        }
        return 0;
    }

    public int threeOfAKindScore() {
        int seekPair = 3; // The pair we seek
        for(int diceEyes = 0; diceEyes < diceFaceArray.length; diceEyes++){
            if(diceFaceArray[diceEyes] >= seekPair){
                return (diceEyes + 1) * seekPair;
            }
        }
        return 0;
    }

    public int fourOfAKindScore() {
        int seekPair = 4; // The pair we seek
        for(int diceEyes = 0; diceEyes < diceFaceArray.length; diceEyes++){
            if(diceFaceArray[diceEyes] >= seekPair){
                return (diceEyes + 1) * seekPair;
            }
        }
        return 0;
    }

    public int smallStraightScore() {
        int sum = 0;
        int length = diceFaceArray.length - 1;
        for(int index = 0; index < length; index++){
            if(diceFaceArray[index] == 1){
                sum++;
            }
        }
        if(sum == 5){
            return 15;
        }
        return 0;
    }

    public int largeStraightScore() {
        int sum = 0;
        int length = diceFaceArray.length;
        for(int index = 1; index < length; index++){
            if(diceFaceArray[index] == 1){
                sum++;
            }
        }
        if(sum == 5){
            return 20;
        }
        return 0;
    }

    public int fullHouseScore() {
        int seekPairTwo = 2; // The pair we seek
        int seekPairThree = 3;
        int pairCounter = 0;
        int sum = 0;

        for(int diceIndex = 0; diceIndex < diceFaceArray.length; diceIndex++){
            int diceRolled = diceFaceArray[diceIndex];
            if(diceRolled == seekPairTwo || diceRolled == seekPairThree){
                int diceFace = diceIndex + 1;
                sum += sumOfPair(diceFace, diceRolled);
                pairCounter++;
            }
            if(pairCounter == 2){
                return sum;
            }
        }
        return 0;
    }

    public int chanceScore() {
        int sum = 0;
        for(int index = 0; index < diceFaceArray.length; index++){
            int diceEye = diceFaceArray[index];
            int numberOfDice = index + 1;
            sum += (diceEye * numberOfDice);
        }
        return sum;
    }

    public int yatzyScore() {
        int seekPair = 5; // The pair we seek
        for(int diceIndex : diceFaceArray){ // Don't understand: Works with descending classic for-loop and casual for each
            if(diceIndex >= seekPair){
                return 50;
            }
        }
        return 0;
    }

    public int upperSectionSum(){
        int sum = 0;
        for(int index = 0; index < diceFaceArray.length; index++){
            sum += ((index + 1) * diceFaceArray[index]);
        }
        return sum;
    }

    private int sumOfPair(int diceFace, int pair){
        return diceFace * pair;
    }
}