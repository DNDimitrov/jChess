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

        Piece(final PieceType pieceType, int piecePosition,final Alliance pieceAlliance){
            this.pieceType = pieceType;
        this.pieceAlliance=pieceAlliance;
        this.piecePosition=piecePosition;
        this.isFirstMove = false;
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

    public int getPiecePosition() {
            return this.piecePosition;
    }

    public abstract List<Move> calculatedMoves(final Board board);


    public enum PieceType {

        PAWN("P") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT("N") {
            @Override
            public boolean isKing() {
                return false;

            }
        },
        BISHOP("B") {
            @Override
            public boolean isKing() {
            return false;
            }
        },
        ROOK("R") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN("Q") {
            @Override
            public boolean isKing() {
                return false;
            }
        },

        KING("K") {
            @Override
            public boolean isKing() {
                return true;
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
    }
}

