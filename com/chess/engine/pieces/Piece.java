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

        Piece(final PieceType pieceType, int piecePosition,final Alliance pieceAlliance, final boolean isFirstMove){
            this.pieceType = pieceType;
        this.pieceAlliance=pieceAlliance;
        this.piecePosition=piecePosition;
        this.isFirstMove = isFirstMove;
        this.cashedHashCode = computeHashCode();
        }

    protected int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + pieceAlliance.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return  result;
    };



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
    public int getPieceValue() {
        return this.getPieceType().getPieceValue();
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }
    public final Alliance getAlliance() {
        return pieceAlliance;
    }
    public boolean isFirstMove() {
        return this.isFirstMove;
    }
    public abstract List<Move> calculateLegalMoves(final Board board);
        public abstract Piece movePiece(Move piece) ;



    public enum PieceType {

        PAWN("P",100) {
            @Override
            public boolean isKing() {
                return false;
            }
            @Override
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT("N",300) {
            @Override
            public boolean isKing() {
                return false;

            }
            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("B",300) {
            @Override
            public boolean isKing() {
            return false;
            }
            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK("R",500) {
            @Override
            public boolean isKing() {
                return false;
            }
            @Override
            public boolean isRook() {
                return true;
            }
        },
        QUEEN("Q",900) {
            @Override
            public boolean isKing() {
                return false;
            }
            @Override
            public boolean isRook() {
                return false;
            }
        },

        KING("K",10000) {
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
        private int pieceValue;

        PieceType(final String pieceName, final int pieceValue) {
            this.pieceName = pieceName;
            this.pieceValue = pieceValue;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }

        public int getPieceValue() {
            return this.pieceValue;
        }
        public abstract boolean isKing();
        public abstract boolean isRook();
    }
}

