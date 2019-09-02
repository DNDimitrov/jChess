package com.chess.engine.pieces;

import com.chess.engine.board.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17,-15, -10,-6,6,10,15, 17};

    Knight(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition,pieceAlliance);
    }

    @Override
    public List<Move> calculatedMoves(Board board) {

        int candidateDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidate : CANDIDATE_MOVE_COORDINATES) {
            candidateDestinationCoordinate = this.piecePosition + currentCandidate;

            //if it is inside the matrix
            if(true) {
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                if(!candidateDestinationTile.isOccupied()) {
                    legalMoves.add(new Move());
                }
                else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAtDestinationAlliance = pieceAtDestination.getAlliance();

                    if(this.pieceAlliance != pieceAtDestinationAlliance) {
                        legalMoves.add(new Move());
                    }
                }
            }

        }
        return Collections.unmodifiableList(legalMoves);
    }
}
