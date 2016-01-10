
/**
 * Class for min-max and alpha beta algorithms implementation
 * Created by Vipul Somani (vasomani@usc.edu) on 10/10/2015.
 */
public class BaseAlgorithm {
    public static int sDepth;
    public static int sMyPlayer;
    private static final String PLAYER_2 = "A";
    private static final String PLAYER_1 = "B";
    private static final String SEPARATOR = ",";
    private static final String NEG_INF = "-Infinity";
    private static final String POS_INF = "Infinity";
    private static final String ROOT = "root";

    /**
     * @param boardState current state of the game
     * @param depth      level of cutoff
     * @param player     player 1 or 2
     * @return evaluation at Max Level
     */
    public static int MaxValue(MancalaBoard boardState, int depth, int player, int prevIndex) {
        if (depth == sDepth) {
            //If leaf print direct value
            int leafValue = boardState.getEvaluation(sMyPlayer);
            boardState.setValue(leafValue);
            if (!boardState.isExtraTurnCase()) {
                mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_2 : PLAYER_1) + (prevIndex + 2) + SEPARATOR
                        + depth + SEPARATOR + boardState.getValue()));
            }
            return leafValue;
        }
        int[] playerPits = boardState.getPits(player);
        int value = Integer.MIN_VALUE;
        for (int i = 0; i < playerPits.length; i++) {
            if (playerPits[i] != 0) {
                MancalaBoard newState = new MancalaBoard(boardState);
                boolean extraTurn = newState.play(i, player);
                newState.setExtraTurnCase(extraTurn);
                int newValue;
                if (!newState.isEndGameCase()) {
                    if (extraTurn) {
                        mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_1 : PLAYER_2) + (i + 2) + SEPARATOR
                                + (depth + 1) + SEPARATOR + NEG_INF));
                        newValue = MaxValue(newState, depth, player, i);
                    } else {
                        if ((depth + 1) != sDepth) {
                            mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_1 : PLAYER_2) + (i + 2) + SEPARATOR
                                    + (depth + 1) + SEPARATOR + POS_INF));
                        }
                        newValue = MinValue(newState, depth + 1, 3 - player, i);
                    }
                } else {
                    if(extraTurn){
                        mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_1 : PLAYER_2) + (i + 2) + SEPARATOR
                                + (depth + 1) + SEPARATOR + NEG_INF));
                    }else {
                        if ((depth + 1) != sDepth) {
                            mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_1 : PLAYER_2) + (i + 2) + SEPARATOR
                                    + (depth + 1) + SEPARATOR + POS_INF));
                        }
                    }
                    newValue = newState.getEvaluation(sMyPlayer);
                    mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_1 : PLAYER_2) + (i + 2) + SEPARATOR
                            + (depth + 1) + SEPARATOR + newValue));
                }
                if (value < newValue) {
                    value = newValue;
                    if (depth == 0) {
                        boardState.setSelectedPit(i);
                    }
                }
                if (boardState.isExtraTurnCase()) {
                    mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_1 : PLAYER_2) + (prevIndex + 2) + SEPARATOR
                            + (depth + 1) + SEPARATOR + value));
                } else {
                    if (depth == 0) {
                        mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, (ROOT + SEPARATOR + (depth) + SEPARATOR + value));
                    } else {
                        mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_2 : PLAYER_1) + (prevIndex + 2) + SEPARATOR
                                + depth + SEPARATOR + value));
                    }
                }
            }
        }
        boardState.setValue(value);
        return value;
    }

    /**
     * @param boardState current state of the game
     * @param depth      level of cutoff
     * @param player     player 1 or 2
     * @return evaluation at Min Level
     */
    public static int MinValue(MancalaBoard boardState, int depth, int player, int prevIndex) {
        if (depth == sDepth) {
            //If leaf print direct value
            int leafValue = boardState.getEvaluation(sMyPlayer);
            boardState.setValue(leafValue);
            if (!boardState.isExtraTurnCase()) {
                mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_1 : PLAYER_2) + (prevIndex + 2) + SEPARATOR
                        + depth + SEPARATOR + boardState.getValue()));
            }
            return leafValue;
        }
        int[] playerPits = boardState.getPits(player);
        int value = Integer.MAX_VALUE;
        for (int i = 0; i < playerPits.length; i++) {
            if (playerPits[i] != 0) {
                MancalaBoard newState = new MancalaBoard(boardState);
                boolean extraTurn = newState.play(i, player);
                newState.setExtraTurnCase(extraTurn);
                int newValue;
                if (!newState.isEndGameCase()) {
                    if (extraTurn) {
                        mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_2 : PLAYER_1) + (i + 2) + SEPARATOR
                                + (depth + 1) + SEPARATOR + POS_INF));
                        newValue = MinValue(newState, depth, player, i);
                    } else {
                        if ((depth + 1) != sDepth) {
                            mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_2 : PLAYER_1) + (i + 2) + SEPARATOR
                                    + (depth + 1) + SEPARATOR + NEG_INF));
                        }
                        newValue = MaxValue(newState, depth + 1, 3 - player, i);
                    }
                } else {
                    if(extraTurn){
                        mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_2 : PLAYER_1) + (i + 2) + SEPARATOR
                                + (depth + 1) + SEPARATOR + POS_INF));
                    }else {
                        if ((depth + 1) != sDepth) {
                            mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_2 : PLAYER_1) + (i + 2) + SEPARATOR
                                    + (depth + 1) + SEPARATOR + NEG_INF));
                        }
                    }
                    newValue = newState.getEvaluation(sMyPlayer);
                    mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_2 : PLAYER_1) + (i + 2) + SEPARATOR
                            + (depth + 1) + SEPARATOR + newValue));
                }
                if (value > newValue) {
                    value = newValue;
                }
                if (boardState.isExtraTurnCase()) {
                    mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_2 : PLAYER_1) + (prevIndex + 2) + SEPARATOR
                            + (depth + 1) + SEPARATOR + value));
                } else {
                    mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_1 : PLAYER_2) + (prevIndex + 2) + SEPARATOR
                            + (depth) + SEPARATOR + value));
                }
            }
        }
        boardState.setValue(value);
        return value;
    }

    public static int MaxValueAlphaBeta(MancalaBoard boardState, int depth, int player, int alpha, int beta, int prevIndex) {
        if (depth == sDepth) {
            //If leaf print direct value
            int leafValue = boardState.getEvaluation(sMyPlayer);
            boardState.setValue(leafValue);
            if (!boardState.isExtraTurnCase()) {
                mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_2 : PLAYER_1) + (prevIndex + 2) + SEPARATOR
                        + depth + SEPARATOR + boardState.getValue() + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
            }
            return leafValue;
        }
        int[] playerPits = boardState.getPits(player);
        int value = Integer.MIN_VALUE;
        for (int i = 0; i < playerPits.length; i++) {
            if (playerPits[i] != 0) {
                MancalaBoard newState = new MancalaBoard(boardState);
                boolean extraTurn = newState.play(i, player);
                newState.setExtraTurnCase(extraTurn);
                int newValue;
                if (extraTurn) {
                    mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_1 : PLAYER_2) + (i + 2) + SEPARATOR
                            + (depth + 1) + SEPARATOR + NEG_INF + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
                    if(newState.isEndGameCase()){
                        newValue = newState.getEvaluation(sMyPlayer);
                        mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_1 : PLAYER_2) + (i + 2) + SEPARATOR
                                + (depth + 1) + SEPARATOR + newValue + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
                    }else {
                        newValue = MaxValueAlphaBeta(newState, depth, player, alpha, beta, i);
                    }
//                    if (newValue >= beta) {
//                        if (boardState.isExtraTurnCase()) {
//                            mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_1 : PLAYER_2) + (prevIndex + 2) + SEPARATOR
//                                    + (depth + 1) + SEPARATOR + newValue + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
//                        } else {
//                            mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_2 : PLAYER_1) + (prevIndex + 2) + SEPARATOR
//                                    + (depth) + SEPARATOR + newValue + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
//                        }
//                        return newValue;
//                    }
//                    alpha = Math.max(alpha, newValue);
                } else {
                    if ((depth + 1) != sDepth) {
                        mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_1 : PLAYER_2) + (i + 2) + SEPARATOR
                                + (depth + 1) + SEPARATOR + POS_INF + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
                    }
                    if(newState.isEndGameCase()){
                        newValue = newState.getEvaluation(sMyPlayer);
                        mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_1 : PLAYER_2) + (i + 2) + SEPARATOR
                                + (depth + 1) + SEPARATOR + newValue + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
                    }else {
                        newValue = MinValueAlphaBeta(newState, depth + 1, 3 - player, alpha, beta, i);
                    }
                }
                if (value < newValue) {
                    value = newValue;
                    if (depth == 0) {
                        boardState.setSelectedPit(i);
                    }
                }
                if (value >= beta) {
                    if (boardState.isExtraTurnCase()) {
                        mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_1 : PLAYER_2) + (prevIndex + 2) + SEPARATOR
                                + (depth + 1) + SEPARATOR + value + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
                    } else {
                        if (depth == 0) {
                            mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, (ROOT + SEPARATOR + (depth) + SEPARATOR + value + SEPARATOR
                                    + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
                        } else {
                            mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_2 : PLAYER_1) + (prevIndex + 2) + SEPARATOR
                                    + depth + SEPARATOR + value + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
                        }
                    }
                    return value;
                }
                alpha = Math.max(alpha, value);
                if (boardState.isExtraTurnCase()) {
                    mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_1 : PLAYER_2) + (prevIndex + 2) + SEPARATOR
                            + (depth + 1) + SEPARATOR + value + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
                } else {
                    if (depth == 0) {
                        mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, (ROOT + SEPARATOR
                                + (depth) + SEPARATOR + value + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
                    } else {
                        mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_2 : PLAYER_1) + (prevIndex + 2) + SEPARATOR
                                + depth + SEPARATOR + value + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
                    }
                }
            }
        }
        boardState.setValue(value);
        return value;
    }

    public static int MinValueAlphaBeta(MancalaBoard boardState, int depth, int player, int alpha, int beta, int prevIndex) {
        if (depth == sDepth) {
            //If leaf print direct value
            int leafValue = boardState.getEvaluation(sMyPlayer);
            boardState.setValue(leafValue);
            if (!boardState.isExtraTurnCase()) {
                mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_1 : PLAYER_2) + (prevIndex + 2) + SEPARATOR
                        + depth + SEPARATOR + boardState.getValue() + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
            }
            return leafValue;
        }
        int[] playerPits = boardState.getPits(player);
        int value = Integer.MAX_VALUE;
        for (int i = 0; i < playerPits.length; i++) {
            if (playerPits[i] != 0) {
                MancalaBoard newState = new MancalaBoard(boardState);
                boolean extraTurn = newState.play(i, player);
                newState.setExtraTurnCase(extraTurn);
                int newValue;
                if (extraTurn) {
                    mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_2 : PLAYER_1) + (i + 2) + SEPARATOR
                            + (depth + 1) + SEPARATOR + POS_INF + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
                    if(newState.isEndGameCase()){
                        newValue = newState.getEvaluation(sMyPlayer);
                        mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_2 : PLAYER_1) + (i + 2) + SEPARATOR
                                + (depth + 1) + SEPARATOR + newValue + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
                    }else{
                        newValue = MinValueAlphaBeta(newState, depth, player, alpha, beta, i);
                    }
//                    if (newValue <= alpha) {
//                        if (boardState.isExtraTurnCase()) {
//                            mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_2 : PLAYER_1) + (prevIndex + 2) + SEPARATOR
//                                    + (depth + 1) + SEPARATOR + newValue + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
//                        } else {
//                            mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_1 : PLAYER_2) + (prevIndex + 2) + SEPARATOR
//                                    + depth + SEPARATOR + newValue + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
//                        }
//                        return newValue;
//                    }
//                    beta = Math.min(beta, newValue);
                } else {
                    if ((depth + 1) != sDepth) {
                        mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_2 : PLAYER_1) + (i + 2) + SEPARATOR
                                + (depth + 1) + SEPARATOR + NEG_INF + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
                    }
                    if(newState.isEndGameCase()){
                        newValue = newState.getEvaluation(sMyPlayer);
                        mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_2 : PLAYER_1) + (i + 2) + SEPARATOR
                                + (depth + 1) + SEPARATOR + newValue + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
                    }else{
                        newValue = MaxValueAlphaBeta(newState, depth + 1, 3 - player, alpha, beta, i);
                    }
                }
                if (value > newValue) {
                    value = newValue;
                }
                if (value <= alpha) {
                    if (boardState.isExtraTurnCase()) {
                        mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_2 : PLAYER_1) + (prevIndex + 2) + SEPARATOR
                                + (depth + 1) + SEPARATOR + value + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
                    } else {
                        mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_1 : PLAYER_2) + (prevIndex + 2) + SEPARATOR
                                + (depth) + SEPARATOR + value + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
                    }
                    return value;
                }
                beta = Math.min(beta, value);
                if (boardState.isExtraTurnCase()) {
                    mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_2 : PLAYER_1) + (prevIndex + 2) + SEPARATOR
                            + (depth + 1) + SEPARATOR + value + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
                } else {
                    mancala.writeToFile(mancala.OUTPUT_TRAVERSE_LOG, ((sMyPlayer == 1 ? PLAYER_1 : PLAYER_2) + (prevIndex + 2) + SEPARATOR
                            + (depth) + SEPARATOR + value + SEPARATOR + getStringValue(alpha) + SEPARATOR + getStringValue(beta)));
                }
            }
        }
        boardState.setValue(value);
        return value;
    }

    private static String getStringValue(int val) {
        switch (val) {
            case Integer.MAX_VALUE:
                return POS_INF;
            case Integer.MIN_VALUE:
                return NEG_INF;
            default:
                return String.valueOf(val);
        }
    }
}
