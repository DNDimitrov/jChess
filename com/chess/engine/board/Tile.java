package com.chess.engine.board;
import com.chess.engine.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

    protected final int tileCoordinate;
    private static final Map<Integer,EmptyTile> EMPTY_TILES = createAllPossibleEmptyTiles();

    private static Map<Integer,EmptyTile> createAllPossibleEmptyTiles () {
        Map<Integer,EmptyTile> emptyTilesMap = new HashMap<>();
            for(int i = 0; i < 63; i++) {
                emptyTilesMap.put(i,new EmptyTile(i));
            }
            return  emptyTilesMap;

    }

    private Tile (int Coordinate) {
        this.tileCoordinate = Coordinate;
    }

    public  static  Tile createTile( final int tileCoordinate, final Piece piece) {
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES.get(tileCoordinate);
    }
    public abstract boolean isOccupied();

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
        public Piece getPiece(){
            return null;
        }
    }

    public static final class OccupiedTile extends Tile {

        private final Piece pieceOnTile;
        OccupiedTile(int coordinates , Piece piece) {
            super(coordinates);
            this.pieceOnTile = piece;
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
