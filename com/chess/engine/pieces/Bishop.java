package com.chess.engine.pieces;

import com.chess.engine.board.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bishop extends Piece {

    private static final int[] CANDIDATE_MOVE_COORDINATES = {-9, -7, 7, 9};

    public Bishop(final int piecePosition,final Alliance pieceAlliance) {
        super(PieceType.BISHOP, piecePosition, pieceAlliance,true);
    }
    public Bishop(final int piecePosition,final Alliance pieceAlliance, boolean isFirstMove) {
        super(PieceType.BISHOP, piecePosition, pieceAlliance,isFirstMove);
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition;
            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if(isFirstColumnExclusion(currentCandidateOffset,candidateDestinationCoordinate) ||
                        isEightColumnExclusion(currentCandidateOffset,candidateDestinationCoordinate)) {
                    break;
                }
                candidateDestinationCoordinate += currentCandidateOffset;
                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if (!candidateDestinationTile.isOccupied()) {
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAtDestinationAlliance = pieceAtDestination.getAlliance();

                        if (this.pieceAlliance != pieceAtDestinationAlliance) {
                            legalMoves.add(new Move.MajorAttackMove(board, this,
                                    candidateDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public Bishop movePiece(final Move move) {
        return new Bishop(move.getDestinationCoordinate(),move.getMovedPieace().getAlliance());
    }

    @Override
    public String toString() {
        return PieceType.BISHOP.toString();
    }

    private  static  boolean isFirstColumnExclusion(final int currentCandidate, final int candidateDestinationCoordinate) {
        return BoardUtils.INSTANCE.FIRST_COLUMN.get(candidateDestinationCoordinate) && (currentCandidate == -9 || currentCandidate == 7);
    }

    private  static  boolean isEightColumnExclusion(final int currentCandidate, final int candidateDestinationCoordinate) {
        return BoardUtils.INSTANCE.EIGHTH_COLUMN.get(candidateDestinationCoordinate) && (currentCandidate == -7 || currentCandidate == 9);
    }
}



