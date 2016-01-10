import java.io.*;

/**
 * Main Class. Parses Input file, selects the algorithm to run and writes output file
 * Created by Vipul Somani (vasomani@usc.edu) on 10/10/2015.
 */
public class mancala {
    private static final int GREEDY = 1;
    private static final int MIN_MAX = 2;
    private static final int ALPHA_BETA = 3;
    private static final String INPUT_FILE = "input.txt";
    private static final String OUTPUT_NEXT_STATE = "next_state.txt";
    public static final String OUTPUT_TRAVERSE_LOG = "traverse_log.txt";

    private static BufferedWriter mOutputWriter;
    private static boolean sWriteTraversal = true;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        BufferedReader inputReader;
        try {
            inputReader = new BufferedReader(new FileReader(INPUT_FILE));
        } catch (FileNotFoundException e) {
            System.out.println("Cannot read input file");
            return;
        }
        try {
            // Read number of test cases
            int algorithm = Integer.parseInt(inputReader.readLine().trim());
            int player = Integer.parseInt(inputReader.readLine().trim());
            BaseAlgorithm.sMyPlayer = player;
            int depth = Integer.parseInt(inputReader.readLine().trim());
            String[] playerAState = inputReader.readLine().trim().split(" ");
            String[] playerBState = inputReader.readLine().trim().split(" ");
            int mancalaA = Integer.parseInt(inputReader.readLine().trim());
            int mancalaB = Integer.parseInt(inputReader.readLine().trim());
            int[] pitsA = new int[playerAState.length];
            int[] pitsB = new int[playerBState.length];
            for(int i = 0; i < playerAState.length; i++){
                pitsA[i] = Integer.parseInt(playerAState[i].trim());
                pitsB[i] = Integer.parseInt(playerBState[i].trim());
            }
            MancalaBoard mainBoardState = new MancalaBoard(mancalaA, mancalaB, pitsA, pitsB);
            switch (algorithm){
                case GREEDY:
                    sWriteTraversal = false;
                    BaseAlgorithm.sDepth = 1;
                    boolean turnGreedy;
                    do {
                        int backedUpvalue = BaseAlgorithm.MaxValue(mainBoardState, 0, player, 0);
                        System.out.println("backed Up value = " + backedUpvalue);
                        int nextGreedy = mainBoardState.getSelectedPit();
                        turnGreedy = mainBoardState.play(nextGreedy, player);
                    }while(turnGreedy);
                    writeToFile(OUTPUT_NEXT_STATE, mainBoardState.toString());
                    break;
                case MIN_MAX:
                    writeToFile(OUTPUT_TRAVERSE_LOG, null);
                    writeToFile(OUTPUT_TRAVERSE_LOG, "Node,Depth,Value");
                    BaseAlgorithm.sDepth = depth;
                    sWriteTraversal = true;
                    writeToFile(OUTPUT_TRAVERSE_LOG, "root,0,-Infinity");
                    boolean turnMinMax;
                    do {
                        int backedUpvalue = BaseAlgorithm.MaxValue(mainBoardState, 0, player, 0);
                        if(mOutputWriter!= null){
                            mOutputWriter.close();
                            mOutputWriter = null;
                            System.out.println("backed Up value = " + backedUpvalue);
                            System.out.println("Time of Execution Only Algo " + String.valueOf((double)(System.currentTimeMillis() - start)/1000));
                        }
                        sWriteTraversal = false;
                        int nextMinMax = mainBoardState.getSelectedPit();
                        turnMinMax = mainBoardState.play(nextMinMax, player);
                    }while(turnMinMax);
                    writeToFile(OUTPUT_NEXT_STATE, mainBoardState.toString());
                    break;
                case ALPHA_BETA:
                    writeToFile(OUTPUT_TRAVERSE_LOG, null);
                    writeToFile(OUTPUT_TRAVERSE_LOG, "Node,Depth,Value,Alpha,Beta");
                    BaseAlgorithm.sDepth = depth;
                    sWriteTraversal = true;
                    writeToFile(OUTPUT_TRAVERSE_LOG, "root,0,-Infinity,-Infinity,Infinity");
                    boolean turnAlphaBeta;
                    do {
                        int backedUpvalue = BaseAlgorithm.MaxValueAlphaBeta(mainBoardState, 0, player, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
                        if(mOutputWriter!= null){
                            mOutputWriter.close();
                            mOutputWriter = null;
                            System.out.println("backed Up value = " + backedUpvalue);
                            System.out.println("Time of Execution Only Algo " + String.valueOf((double)(System.currentTimeMillis() - start)/1000));
                        }
                        sWriteTraversal = false;
                        int nextAlphaBeta = mainBoardState.getSelectedPit();
                        turnAlphaBeta = mainBoardState.play(nextAlphaBeta, player);
                    }while (turnAlphaBeta);
                    writeToFile(OUTPUT_NEXT_STATE, mainBoardState.toString());
                    break;
            }
        } catch (Exception e) {
            System.out.println("Exception while writing");
        } finally {
            try {
                inputReader.close();
            } catch (IOException e) {
                System.out.println("IO Exception while closing buffer reader");
            }
        }
        System.out.println("Time of Execution " + String.valueOf((double)(System.currentTimeMillis() - start)/1000));
    }

    public static void writeToFile(String filename, String output){
        if(OUTPUT_TRAVERSE_LOG.equals(filename) && !sWriteTraversal){
            return;
        }
        if(OUTPUT_NEXT_STATE.equals(filename)){
            try {
                mOutputWriter = new BufferedWriter(new FileWriter(filename, false));
                mOutputWriter.append(output);
                mOutputWriter.close();
                mOutputWriter = null;
            } catch (IOException e) {
                System.out.print("Cannot Create/Write output file");
            }
        }else{
            try {
                if(output == null) {
                    mOutputWriter = new BufferedWriter(new FileWriter(filename, false));
                }else{
                    if(mOutputWriter == null){
                        mOutputWriter = new BufferedWriter(new FileWriter(filename, true));
                    }
                    mOutputWriter.append(output).append("\n");
                }
            } catch (IOException e) {
                System.out.print("Cannot Create/Write output file");
            }
        }
    }
}
