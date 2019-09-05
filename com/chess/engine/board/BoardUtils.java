package com.chess.engine.board;

public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECONT_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHT_COLUMN = initColumn(7);

    public static final boolean[] EIGHT_RANK = initRow(0);
    public static final boolean[] SEVENTH_RANK = initRow(8);
    public static final boolean[] SIXTH_RANK = initRow(16);
    public static final boolean[] FIFTH_RANK = initRow(24);
    public static final boolean[] FOURTH_RANK = initRow(32);
    public static final boolean[] THIRD_RANK = initRow(40);
    public static final boolean[] SECOND_RANK = initRow(48);
    public static final boolean[] FIRST_RANK = initRow(56);



    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_ON_ROW = 8;

    private static boolean[] initRow(int rowNumber) {
        final boolean[] row = new boolean[NUM_TILES];
        while(rowNumber % NUM_TILES_ON_ROW != 0) {
            row[rowNumber] = true;
            rowNumber++;
        }
        return row;
    }

    private static boolean[] initColumn(int i) {
        final boolean[] column = new boolean[NUM_TILES];
        while(i < NUM_TILES) {
            column[i] = true;
            i+=NUM_TILES_ON_ROW;
        }
        return column;
    }

    private BoardUtils() {
       throw new RuntimeException("Can't touch this!");
    }
    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate>=0 && coordinate <64;
    }
}
