package com.chess.engine.player;

import com.chess.engine.board.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    public  final List<Move> legalMoves;
    private final Boolean isInCheck;

    Player(final Board board, final List<Move> legalMoves, final List<Move> opponentMove) {
        this.board=board;
        this.playerKing = establishKing();
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(),opponentMove).isEmpty();
        this.legalMoves = merge(legalMoves, calculateKingCastle(legalMoves,opponentMove));


    }

    public static<Move> List<Move> merge(List<Move> list1, List<Move> list2)
    {
        List<Move> list = new ArrayList<>(list1);
        list.addAll(list2);
        return Collections.unmodifiableList(list);
    }

    //Not sure if this function should return Piece or King object
    public King getPlayerKing() {
        return this.playerKing;
    }

    public List<Move> getLegalMoves() {
        return this.legalMoves;
    }
    protected static List<Move>  calculateAttacksOnTile(int piecePosition, List<Move> opponentMoves) {
        final List<Move> attackMoves = new ArrayList<>();
        for(final Move move: opponentMoves) {
            if(piecePosition == move.getDestinationCoordinate()) {
                attackMoves.add(move);
            }
        }
        return Collections.unmodifiableList(attackMoves);
    }

    private King establishKing() {
        for (final Piece piece : getActivePieces()) {
            if (piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("There is no king on the board");
    }

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck() {
        return this.isInCheck;
    }
    public boolean isInCheckMate() {
        return this.isInCheck && !hasEscapeMoves();
    }

    public boolean isInStaleMate() {
        return !this.isInCheck && !hasEscapeMoves();
    }

    public boolean isKingSideCastleCapable() {
        return this.playerKing.isKingSideCatleCapable();
    }
    public boolean isQueenSideCastleCapable() {
        return this.playerKing.isQueenSideCatleCapable();
    }


    protected boolean hasEscapeMoves() {
        for(Move move : this.legalMoves) {
            final MoveTransition transition = makeMove(move);
            if(transition.getMoveStatus().isDone()) {
                return true;
            }
        }
        return false;
    }


    public boolean isCastled() {
        return false;
    }

    public MoveTransition makeMove(final Move move) {
        //if this move isnt an option the player has we return the same board and the move status is illegal
        if(!isMoveLegal(move)) {
            return  new MoveTransition(this.board,move, MoveStatus.ILLEGAL_MOVE);
        }

        //if the move is option for the player we make the move and check if his king is still in check after the move we shouldnt have done such a move
        final Board transitionBoard = move.execute();
        final List<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                transitionBoard.currentPlayer().getLegalMoves());

        if(!kingAttacks.isEmpty()) {
            return new MoveTransition(this.board,move,MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }
        //if the move is ok we return the new transition
        return new MoveTransition(transitionBoard,move,MoveStatus.DONE);
    }


    protected abstract List<Move> calculateKingCastle(List<Move> playerLegalMoves,List<Move> opponentLegalMoves);
    public abstract List<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
}
