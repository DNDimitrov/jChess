package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;

import java.util.List;

public class BlackPlayer extends Player {
    public BlackPlayer(Board board, List<Move> whitesStandartLegalMoves, List<Move> blacksStandartLegalMoves) {
        super(board,blacksStandartLegalMoves,whitesStandartLegalMoves);
    }

    @Override
    public List<Piece>  getActivePieces() {
        return this.board.getBlackPieces();
    }
}
