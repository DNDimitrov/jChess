package com.chess.engine.board;

import com.chess.engine.board.Board.Builder;
import com.chess.engine.pieces.Piece;

public abstract class Move {

    final Board board;
    final Piece movedPieace;
    final int destinationCoordinate;

    private Move(final Board board, final Piece movedPieace, final int destinationCoordinate) {
        this.board = board;
        this.movedPieace = movedPieace;
        this.destinationCoordinate = destinationCoordinate;
    }
    public abstract Board execute();
    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }
    public Piece getMovedPieace() {
        return this.movedPieace;
    }

    public static final class MajorMove extends Move {

        public MajorMove(final Board board, final Piece movedPieace,
                         final int destinationCoordinate) {
            super(board, movedPieace, destinationCoordinate);
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();
            //TODO hashcode and equals to pieces
            //after we make move we make new board and move the pieces to the new board
            //if the piece is not the moved pieace we just copy it to the new board
            for(final Piece piece: this.board.currentPlayer().getActivePieces()) {
                if(!this.movedPieace.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            //we simply copy the pieces of the opponent to the new board, they are note moved
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            //move the moved piece
            builder.setPiece(this.movedPieace.movePiece(this));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }
    }

    public static final class AttackMove extends Move {

        final Piece attackedPiece;
        public AttackMove(final Board board,final Piece movedPieace,final int destinationCoordinate, final Piece attackedPiece) {
            super(board, movedPieace, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            return null;
        }
    }
}
