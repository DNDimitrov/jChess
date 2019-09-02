package com.chess.engine.board;

public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECONT_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHT_COLUMN = initColumn(7);


    private static boolean[] initColumn(int i) {
        final boolean[] column = new boolean[64];
        while(i<64) {
            column[i] = true;
            i+=8;
        }
        return column;
    }

    private BoardUtils() {
       throw new RuntimeException("Can't touch this!");
    }
    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate>=0 && coordinate <64;
    }
}
