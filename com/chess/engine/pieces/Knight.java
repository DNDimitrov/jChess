package com.chess.engine.pieces;

import com.chess.engine.board.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17,-15,-10,-6,6,10,15,17};

    public Knight(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition,pieceAlliance);
    }

    @Override
    public List<Move> calculatedMoves(Board board) {

        int candidateDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidate : CANDIDATE_MOVE_COORDINATES) {
            candidateDestinationCoordinate = this.piecePosition + currentCandidate;

            //if it is inside the matrix
            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

                //if it is impossible case of the movement of the knight we skip and continue to the next possible move
                if((isFirstColumnExclusion(this.piecePosition, candidateDestinationCoordinate)) &&
                        (isSecontColumnExclusion(this.piecePosition, candidateDestinationCoordinate)) &&
                        (isSeventhColumnExclusion(this.piecePosition, candidateDestinationCoordinate)) &&
                        (isEightColumnExclusion(this.piecePosition, candidateDestinationCoordinate))) {
                    continue;
                }
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if(!candidateDestinationTile.isOccupied()) {
                    legalMoves.add(new Move.MajorMove(board,this, candidateDestinationCoordinate));
                }
                else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAtDestinationAlliance = pieceAtDestination.getAlliance();

                    if(this.pieceAlliance != pieceAtDestinationAlliance) {
                        legalMoves.add(new Move.AttackMove(board,this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    private  static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 ||
                candidateOffset == -10 || candidateOffset ==6 || candidateOffset==15);
    }

    private static boolean isSecontColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SECONT_COLUMN[currentPosition] && (candidateOffset == -10 ||
                candidateOffset == 6);
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == 10 ||
                candidateOffset == -6);
    }

    private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == -15 ||
                candidateOffset == -6 || candidateOffset == 10 || candidateOffset == 17);
    }
}
