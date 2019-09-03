package com.chess.engine.player;

import com.chess.engine.board.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;

import java.util.List;

public class BlackPlayer extends Player {
    public BlackPlayer(final Board board,final List<Move> whitesStandartLegalMoves,final List<Move> blacksStandartLegalMoves) {
        super(board,blacksStandartLegalMoves,whitesStandartLegalMoves);
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }

    @Override
    public List<Piece>  getActivePieces() {
        return this.board.getBlackPieces();
    }
}
