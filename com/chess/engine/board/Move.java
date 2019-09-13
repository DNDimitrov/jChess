package com.chess.engine.board;

import com.chess.engine.board.Board.Builder;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

public abstract class Move {

    protected final Board board;
    protected final Piece movedPieace;
    protected final int destinationCoordinate;
    protected final boolean isFirstMove;


    public static final Move NULL_MOVE = new NullMove();

    private Move(final Board board, final Piece movedPieace, final int destinationCoordinate) {
        this.board = board;
        this.movedPieace = movedPieace;
        this.destinationCoordinate = destinationCoordinate;
        this.isFirstMove = movedPieace.isFirstMove();
    }

    private Move(final Board board, final int destinationCoordinate) {
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.movedPieace = null;
        this.isFirstMove = false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.destinationCoordinate;
        result = prime * result + this.movedPieace.hashCode();
        result = prime * result + this.movedPieace.getPiecePosition();
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if(this == other) {
            return  true;
        }
        if(!(other instanceof Move)) {
            return  false;
        }
        final Move otherMove = (Move) other;
        return  getCurrentCoordinate() == otherMove.getCurrentCoordinate() &&
                getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
                getMovedPieace().equals(otherMove.getMovedPieace());
    }

    public int getCurrentCoordinate() {
        return  this.movedPieace.getPiecePosition();
    }

    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }

    public Piece getMovedPieace() {
        return this.movedPieace;
    }

    public boolean isAttackMove() {
        return false;
    }

    public boolean isCastledMove() {
        return false;
    }
    //TODO
    public  Piece getAttackedPiece() {
        return null;
    }
    public Board execute() {
        final Builder builder = new Builder();

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

    public static class MajorAttackMove extends AttackMove {
        public MajorAttackMove(final Board board, final Piece pieceMoved, final int destinationCoordinate,
                               final Piece attackedPiece) {
            super(board,pieceMoved,destinationCoordinate,attackedPiece);
        }
        @Override
        public boolean equals(final Object other) {
            return  this == other || other instanceof MajorAttackMove && super.equals(other);
        }
        @Override
        public String toString() {
            return movedPieace.getPieceType() + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }

    }

    public static class MajorMove
            extends Move {

        public MajorMove(final Board board,
                         final Piece pieceMoved,
                         final int destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MajorMove && super.equals(other);
        }

        @Override
        public String toString() {
            return movedPieace.getPieceType().toString() +
                    BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }


    }

    public static class AttackMove extends Move {

        final Piece attackedPiece;
        public AttackMove(final Board board,final Piece movedPieace,final int destinationCoordinate, final Piece attackedPiece) {
            super(board, movedPieace, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public int hashCode() {
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(final Object other) {
            if(this == other) {
                return true;
            }
            if(!(other instanceof AttackMove)) {
                return false;
            }
            final AttackMove otherAttackMove = (AttackMove) other;
            return  super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
        }

        @Override
        public boolean isAttackMove() {
            return true;
        }

        @Override
        public Piece getAttackedPiece() {
            return this.attackedPiece;
        }
    }



    public static final class PawnMove extends Move {

        public PawnMove(final Board board,
                        final Piece movedPieace,
                        final int destinationCoordinate) {
            super(board, movedPieace, destinationCoordinate);
        }
        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnMove && super.equals(other);
        }
        @Override
        public String toString() {
            return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }

    }
    public static class PawnAttackMove extends AttackMove {

        public PawnAttackMove(final Board board, final Piece movedPieace,
                         final int destinationCoordinate, final Piece attackedPiece) {
            super(board, movedPieace, destinationCoordinate, attackedPiece);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnAttackMove && super.equals(other);
        }

        @Override
        public String toString() {
            return BoardUtils.getPositionAtCoordinate(this.movedPieace.getPiecePosition()).substring(0,1) + "x" +
                    BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }

    }



    public static final class PawnEnPessantAttackMove extends AttackMove {

        public PawnEnPessantAttackMove(final Board board, final Piece movedPieace,
                              final int destinationCoordinate, final Piece attackedPiece) {
            super(board, movedPieace, destinationCoordinate, attackedPiece);
        }

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof PawnEnPessantAttackMove && super.equals(other);
        }
        @Override
        public Board execute() {
            final Builder builder = new Builder();
            for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if(!this.movedPieace.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                if(!piece.equals(this.attackedPiece)) {
                    builder.setPiece(piece);
                }
            }
            builder.setPiece(this.movedPieace.movePiece(this));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }

    }

    public static final class PawnJump extends Move {

        public PawnJump(final Board board,
                        final Piece movedPieace,
                        final int destinationCoordinate) {
            super(board, movedPieace, destinationCoordinate);
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();
            for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if(!this.movedPieace.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            final Pawn movedPawn = (Pawn) this.movedPieace.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPessantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return  builder.build();
        }
        @Override
        public String toString() {
            return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
    }

    static abstract class CastleMove extends Move {

        protected final Rook castleRook;
        protected final int castleRookStart;
        protected final int castleRookDestination;

        public CastleMove(final Board board, final Piece movedPieace,
                          final int destinationCoordinate,
                          final int castleRookDestination,
                          final int castleRookStart,
                          final Rook castleRook) {
            super(board, movedPieace, destinationCoordinate);
            this.castleRook = castleRook;
            this.castleRookStart = castleRookStart;
            this.castleRookDestination = castleRookDestination;
        }

        public Rook getCastleRook() {
            return this.castleRook;
        }

        public int getGetCastleRookDestination() {
            return this.castleRookDestination;
        }

        public int getCastleRookStart() {
            return this.castleRookStart;
        }

        @Override
        public boolean isCastledMove() {
            return true;
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();
            for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if(!this.movedPieace.equals(piece)  && !this.castleRook.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            builder.setPiece(this.movedPieace.movePiece(this));
            builder.setPiece(new Rook(this.castleRookDestination,this.castleRook.getAlliance()));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + this.castleRook.hashCode();
            result = prime * result + this.castleRookDestination;
            return  result;
        }

        @Override
        public boolean equals(final Object other) {
            if(this == other) {
                return  true;
            }
            if(!(other instanceof CastleMove)) {
                return  false;
            }
            final CastleMove otherCastleMove = (CastleMove) other;
            return super.equals(otherCastleMove) && this.castleRook.equals(otherCastleMove.getCastleRook());
        }
    }

    public static final class KingSideCatleMove extends CastleMove {

        public KingSideCatleMove(final Board board,
                                 final Piece movedPieace,
                                 final int destinationCoordinate,
                                 final int castleRookDestination,
                                 final int castleRookStart,
                                 final Rook castleRook) {
            super(board, movedPieace, destinationCoordinate,castleRookDestination,castleRookStart,castleRook);
        }

        @Override
        public String toString() {
            return "0-0";
        }
    }

    public static final class QueenSideCastleMove extends CastleMove {

        public QueenSideCastleMove(final Board board,
                                   final Piece movedPieace,
                                   final int destinationCoordinate,
                                   final int castleRookDestination,
                                   final int castleRookStart,
                                   final Rook castleRook) {
            super(board, movedPieace, destinationCoordinate,castleRookDestination,castleRookStart,castleRook);
        }
        public String toString() {
            return "0-0-0";
        }
    }

    public static final class NullMove extends Move {

        public NullMove() {
            super(null, 65);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("Cannot make the null move");
        }
        @Override
        public int getCurrentCoordinate() {
            return -1;
        }

    }

    public static class MoveFactory {
        private MoveFactory() {
            throw new RuntimeException("Not instantiable");
        }
        public static Move createMove(final Board board,
                                      final int currentCoordinate,
                                      final int destinationCoordinate) {
            for(final Move move: board.getAllMoves()) {
                if(move.getCurrentCoordinate() == currentCoordinate && move.getDestinationCoordinate() == destinationCoordinate) {
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }
}
