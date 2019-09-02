public abstract class Tile {

    int tileCoordinate;

    Tile (int Coordinate) {
        this.tileCoordinate = Coordinate;
    }

    public abstract boolean isOccupied();

    public  abstract Piece getPiece();

    public final class EmptryTile  extends Tile{

        EmptryTile(int coordinates) {
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

    public final class OccupiedTile extends Tile {

        Piece pieceOnTile;
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
