package com.chess.engine.pieces;

import com.chess.engine.board.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bishop extends Piece {

    private static final int[] CANDIDATE_MOVE_COORDINATES = {-9, -7, 7, 9};

    Bishop(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public List<Move> calculatedMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (int offset : CANDIDATE_MOVE_COORDINATES) {
            int candidateCoordinate = this.piecePosition;
            while (BoardUtils.isValidTileCoordinate(candidateCoordinate)) {
                if(isFirstColumnExclusion(candidateCoordinate,offset) || isEightColumnExclusion(candidateCoordinate,offset)) {
                    break;
                }
                candidateCoordinate += offset;
                if (BoardUtils.isValidTileCoordinate(candidateCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateCoordinate);
                    if (!candidateDestinationTile.isOccupied()) {
                        legalMoves.add(new Move.MajorMove(board, this, candidateCoordinate));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAtDestinationAlliance = pieceAtDestination.getAlliance();

                        if (this.pieceAlliance != pieceAtDestinationAlliance) {
                            legalMoves.add(new Move.AttackMove(board, this, candidateCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    private  static  boolean isFirstColumnExclusion(final int currentPosition, final int offset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (offset == -9 && offset == 7);
    }

    private  static  boolean isEightColumnExclusion(final int currentPosition, final int offset) {
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (offset == -7 && offset == 9);
    }
}



