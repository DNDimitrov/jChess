package com.chess.engine.board;
import com.chess.engine.pieces.Piece;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.chess.engine.board.BoardUtils.NUM_TILES;

public abstract class Tile {

    protected final int tileCoordinate;
    private static final Map<Integer,EmptyTile> EMPTY_TILES = createAllPossibleEmptyTiles();

    private static Map<Integer,EmptyTile> createAllPossibleEmptyTiles () {
        Map<Integer,EmptyTile> emptyTilesMap = new HashMap<>();
            for(int i = 0; i < NUM_TILES; i++) {
                emptyTilesMap.put(i,new EmptyTile(i));
            }
            return Collections.unmodifiableMap(emptyTilesMap);

    }

    private Tile (int Coordinate) {
        this.tileCoordinate = Coordinate;
    }

    public  static  Tile createTile( final int tileCoordinate, final Piece piece) {
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES.get(tileCoordinate);
    }
    public abstract boolean isOccupied();

    public int getTileCoordinate() {
        return this.tileCoordinate;
    }
    public  abstract Piece getPiece();

    public static final class EmptyTile  extends Tile{

        EmptyTile(final int coordinates) {
            super(coordinates);
        }

        @Override
        public boolean isOccupied() {
            return false;
        }

        @Override
        public String toString() {
            return "-";
        }

        @Override
        public Piece getPiece(){
            return null;
        }
    }

    public static final class OccupiedTile extends Tile {

        private final Piece pieceOnTile;
        private OccupiedTile(int coordinates , Piece piece) {
            super(coordinates);
            this.pieceOnTile = piece;
        }

        @Override
        public String toString() {
            return getPiece().getAlliance().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString();
        }

        @Override
        public  boolean isOccupied(){
            return true;
        }

        @Override
        public  Piece getPiece () {
            return pieceOnTile;
        }

    }
}
