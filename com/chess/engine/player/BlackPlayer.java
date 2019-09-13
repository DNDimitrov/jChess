package com.chess.engine.player;

import com.chess.engine.board.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlackPlayer extends Player {
    public BlackPlayer(final Board board,final List<Move> whitesStandartLegalMoves,final List<Move> blacksStandartLegalMoves) {
        super(board,blacksStandartLegalMoves,whitesStandartLegalMoves);
    }

    @Override
    protected List<Move> calculateKingCastle(final List<Move> playerLegalMoves,final List<Move> opponentLegalMoves) {

        final List<Move> kingCastles = new ArrayList<>();
        //In order to execute the move is has to be the 1st Move of the King and he mustn't be in check
        if(this.playerKing.isFirstMove() && !this.isInCheck()) {

            //there must not be pieces between the rook and king --- King castle move
            if(!this.board.getTile(5).isOccupied() &&
               !this.board.getTile(6).isOccupied()) {

                final Tile rookTile = this.board.getTile(7);
                //it must be the first move of the rook and we also check if the rook is on his start position
                if(rookTile.isOccupied() && rookTile.getPiece().isFirstMove()) {
                    //if the tile is not attacked by other player pieces
                    if(Player.calculateAttacksOnTile(5,opponentLegalMoves).isEmpty() &&
                       Player.calculateAttacksOnTile(6,opponentLegalMoves).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new Move.KingSideCastleMove(this.board,this.playerKing,
                                6,5,
                                rookTile.getTileCoordinate(),(Rook)rookTile.getPiece()));
                    }

                }
            }
            //there must not be pieces between the rook and king --- Queen castle move
            if(!this.board.getTile(1).isOccupied() &&
                    !this.board.getTile(2).isOccupied() &&
                    !this.board.getTile(3).isOccupied()) {

                final Tile rookTile = this.board.getTile(0);
                //it must be the first move of the rook and we also check if the rook is on his start position
                if(rookTile.isOccupied() && rookTile.getPiece().isFirstMove() &&
                   Player.calculateAttacksOnTile(2,opponentLegalMoves).isEmpty() &&
                   Player.calculateAttacksOnTile(3,opponentLegalMoves).isEmpty() &&
                   rookTile.getPiece().getPieceType().isRook()) {
                    kingCastles.add(new Move.QueenSideCastleMove(this.board,this.playerKing,
                            2,3,
                            rookTile.getTileCoordinate(),(Rook)rookTile.getPiece()));
                }
            }
        }

        return Collections.unmodifiableList(kingCastles);
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
