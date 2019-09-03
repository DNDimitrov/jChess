package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.Alliance;
import com.chess.engine.board.Move;

import java.util.List;

public abstract class Piece{

protected final int piecePosition;
protected final Alliance pieceAlliance;
protected final boolean isFirstMove;

        Piece(final int piecePosition,final Alliance pieceAlliance){
        this.pieceAlliance=pieceAlliance;
        this.piecePosition=piecePosition;
        this.isFirstMove = false;
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
}

