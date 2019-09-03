package com.chess.engine.board;

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

    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }

    public static final class MajorMove extends Move {

        public MajorMove(final Board board, final Piece movedPieace, final int destinationCoordinate) {
            super(board, movedPieace, destinationCoordinate);
        }
    }

    public static final class AttackMove extends Move {

        final Piece attackedPiece;
        public AttackMove(final Board board,final Piece movedPieace,final int destinationCoordinate, final Piece attackedPiece) {
            super(board, movedPieace, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }
    }
}
