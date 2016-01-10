/**
 * Data Structure for Mancala board.
 * Created by Vipul Somani (vasomani@usc.edu) on 10/10/2015.
 */
public class MancalaBoard {
    private int mSize;
    private int mAMancala;
    private int mBMancala;
    private int[] mAPits;
    private int[] mBPits;
    private int mSelectedPit;
    private int mValue;
    private boolean mExtraTurnCase;
    private boolean mEndGameCase;

    public MancalaBoard(int mancalaA, int mancalaB, int[] pitsA, int[] pitsB) {
        mAMancala = mancalaA;
        mBMancala = mancalaB;
        mAPits = pitsA;
        mBPits = pitsB;
        mSize = pitsA.length;
    }

    public MancalaBoard(MancalaBoard board) {
        mAMancala = board.getMancala(2);
        mBMancala = board.getMancala(1);
        mAPits = board.getPits(2).clone();
        mBPits = board.getPits(1).clone();
        mSize = mAPits.length;
    }

    public int getEvaluation(int player) {
        if (player == 2) {
            return mAMancala - mBMancala;
        } else {
            return mBMancala - mAMancala;
        }
    }

    public int[] getPits(int player) {
        if (player == 1) {
            return mBPits;
        } else {
            return mAPits;
        }
    }

    public int getMancala(int player) {
        if (player == 2) {
            return mAMancala;
        } else {
            return mBMancala;
        }
    }

    public boolean play(int pit, int player) {
        boolean extraTurnCase = false;
        boolean emptyPitCase = false;
        mEndGameCase = false;
        int emptyPitIndex = 0;
        if (player == 1) {
            //Player 1 - B plays
            int marbles = mBPits[pit];
            mBPits[pit] = 0;
            while (marbles > 0) {
                for (int j = pit + 1; j < mSize && marbles > 0; j++) {
                    if (mBPits[j] == 0 && marbles == 1) {
                        emptyPitCase = true;
                        emptyPitIndex = j;
                    }
                    mBPits[j]++;
                    marbles--;
                }
                if (marbles > 0) {
                    if (marbles == 1) {
                        extraTurnCase = true;
                    }
                    mBMancala++;
                    marbles--;
                }
                for (int k = mSize - 1; k >= 0 && marbles > 0; k--) {
                    mAPits[k]++;
                    marbles--;
                }
                for (int j = 0; j <= pit && marbles > 0; j++) {
                    if (mBPits[j] == 0 && marbles == 1) {
                        emptyPitCase = true;
                        emptyPitIndex = j;
                    }
                    mBPits[j]++;
                    marbles--;
                }
            }
            if (emptyPitCase) {
                mBMancala += mAPits[emptyPitIndex] + mBPits[emptyPitIndex];
                mAPits[emptyPitIndex] = 0;
                mBPits[emptyPitIndex] = 0;
            }
        } else {
            // Player 2 -A plays
            int marbles = mAPits[pit];
            mAPits[pit] = 0;
            while (marbles > 0) {
                for (int j = pit - 1; j >= 0 && marbles > 0; j--) {
                    if (mAPits[j] == 0 && marbles == 1) {
                        emptyPitCase = true;
                        emptyPitIndex = j;
                    }
                    mAPits[j]++;
                    marbles--;
                }
                if (marbles > 0) {
                    if (marbles == 1) {
                        extraTurnCase = true;
                    }
                    mAMancala++;
                    marbles--;
                }
                for (int k = 0; k < mSize && marbles > 0; k++) {
                    mBPits[k]++;
                    marbles--;
                }
                for (int j = mSize - 1; j >= pit && marbles > 0; j--) {
                    if (mAPits[j] == 0 && marbles == 1) {
                        emptyPitCase = true;
                        emptyPitIndex = j;
                    }
                    mAPits[j]++;
                    marbles--;
                }
            }
            if (emptyPitCase) {
                mAMancala += mAPits[emptyPitIndex] + mBPits[emptyPitIndex];
                mAPits[emptyPitIndex] = 0;
                mBPits[emptyPitIndex] = 0;
            }
        }
        if (!isLegalMovePresent(player) || !isLegalMovePresent(3 - player)) {
            endGame();
        }
        return extraTurnCase;
    }

    private void endGame() {
        mEndGameCase = true;
        for (int a = 0; a < mAPits.length; a++) {
            mAMancala += mAPits[a];
            mAPits[a] = 0;
        }
        for (int b = 0; b < mBPits.length; b++) {
            mBMancala += mBPits[b];
            mBPits[b] = 0;
        }
    }

    private boolean isLegalMovePresent(int player) {
        int[] playerPits = getPits(player);
        for (int a : playerPits) {
            if (a != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getString(mAPits)).append("\n")
                .append(getString(mBPits)).append("\n")
                .append(mAMancala).append("\n")
                .append(mBMancala);
        return sb.toString();
    }

    private String getString(int[] a) {
        StringBuilder sb = new StringBuilder();
        for (int i : a) {
            sb.append(i + " ");
        }
        return sb.toString();
    }

    public int getSelectedPit() {
        return mSelectedPit;
    }

    public void setSelectedPit(int mSelectedPit) {
        this.mSelectedPit = mSelectedPit;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int mValue) {
        this.mValue = mValue;
    }

    public boolean isExtraTurnCase() {
        return mExtraTurnCase;
    }

    public void setExtraTurnCase(boolean mExtraTurnCase) {
        this.mExtraTurnCase = mExtraTurnCase;
    }

    public boolean isEndGameCase() {
        return mEndGameCase;
    }
}

