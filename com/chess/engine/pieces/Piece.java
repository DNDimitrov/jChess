package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.Alliance;
import com.chess.engine.board.Move;

import java.util.List;

public abstract class Piece{

    protected final PieceType pieceType;
protected final int piecePosition;
protected final Alliance pieceAlliance;
protected final boolean isFirstMove;
private final int cashedHashCode;

        Piece(final PieceType pieceType, int piecePosition,final Alliance pieceAlliance){
            this.pieceType = pieceType;
        this.pieceAlliance=pieceAlliance;
        this.piecePosition=piecePosition;
        this.isFirstMove = false;
        this.cashedHashCode = computeHashCode();
        }

    protected int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + pieceAlliance.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return  result;
    };

    public PieceType getPieceType() {
            return this.pieceType;
        }
    public final Alliance getAlliance() {
        return pieceAlliance;
    }
    public boolean isFirstMove() {
            return this.isFirstMove;
    }

    @Override
    public boolean equals(final Object other) {
            if(this == other) {
                return true;
            }
            if(!(other instanceof Piece)) {
                return false;
            }
            final Piece otherPiece = (Piece) other;
            return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.pieceType &&
                    pieceAlliance == otherPiece.pieceAlliance && isFirstMove == otherPiece.isFirstMove;
    }
    @Override
    public int hashCode() {
            return this.cashedHashCode;

    }

    public int getPiecePosition() {
            return this.piecePosition;
    }
    public abstract List<Move> calculateLegalMoves(final Board board);
        public abstract Piece movePiece(Move piece) ;



    public enum PieceType {

        PAWN("P") {
            @Override
            public boolean isKing() {
                return false;
            }
            @Override
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT("N") {
            @Override
            public boolean isKing() {
                return false;

            }
            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("B") {
            @Override
            public boolean isKing() {
            return false;
            }
            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK("R") {
            @Override
            public boolean isKing() {
                return false;
            }
            @Override
            public boolean isRook() {
                return true;
            }
        },
        QUEEN("Q") {
            @Override
            public boolean isKing() {
                return false;
            }
            @Override
            public boolean isRook() {
                return false;
            }
        },

        KING("K") {
            @Override
            public boolean isKing() {
                return true;
            }
            @Override
            public boolean isRook() {
                return false;
            }

        };

        private String pieceName;

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }

        public abstract boolean isKing();
        public abstract boolean isRook();
    }
}

