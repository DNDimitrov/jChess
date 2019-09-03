package com.chess.engine.player;

import com.chess.engine.board.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;

import java.util.List;

public class WhitePlayer extends Player{
    public WhitePlayer(Board board, List<Move> whitesStandartLegalMoves, List<Move> blacksStandartLegalMoves) {
        super(board,whitesStandartLegalMoves,blacksStandartLegalMoves);
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    public List<Piece>  getActivePieces() {
        return this.board.getWhitePieces();
    }
}
