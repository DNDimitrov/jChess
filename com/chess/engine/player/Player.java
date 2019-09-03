package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;

import java.util.List;

public abstract class Player {

    protected final Board board;
    protected final Piece playerKing;
    protected final List<Move> legalMoves;

    Player(final Board board, final List<Move> legalMoves, final List<Move> opponentMove) {
        this.board=board;
        this.playerKing = establishKing();
        this.legalMoves = legalMoves;
    }

    private King establishKing() {
        for (final Piece piece : getActivePieces()) {
            if (piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("There is no king on the board");
    }

    public abstract List<Piece> getActivePieces();
}
