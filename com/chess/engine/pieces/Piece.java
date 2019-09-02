package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.Alliance;
import com.chess.engine.board.Move;

import java.util.List;

public abstract class Piece{

protected final int piecePosition;
protected final Alliance pieceAlliance;

        Piece(final int piecePosition,final Alliance pieceAlliance){
        this.pieceAlliance=pieceAlliance;
        this.piecePosition=piecePosition;
        }

    final Alliance getAlliance() {
        return pieceAlliance;
    }

    public abstract List<Move> calculatedMoves(final Board board);
}

