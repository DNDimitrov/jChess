package com.chess.engine.pieces;

import com.chess.engine.board.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rook extends Piece {
    private static final int[] CANDIDATE_MOVE_COORDINATES = {-8, -1, 1, 8};

    public Rook(int piecePosition, Alliance pieceAlliance) {
        super(PieceType.ROOK,piecePosition, pieceAlliance, true);
    }
    public Rook(final int piecePosition,final Alliance pieceAlliance, final boolean isFirstMove) {
        super(PieceType.ROOK,piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (int offset : CANDIDATE_MOVE_COORDINATES) {
            int candidateCoordinate = this.piecePosition;
            while (BoardUtils.isValidTileCoordinate(candidateCoordinate)) {
                if (isColumnExclusion(offset,candidateCoordinate)) {
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
                            legalMoves.add(new Move.MajorAttackMove(board, this, candidateCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }
    @Override
    public Rook movePiece(final Move move) {
        return new Rook(move.getDestinationCoordinate(),move.getMovedPieace().getAlliance());
    }
    @Override
    public String toString() {
        return PieceType.ROOK.toString();
    }

    private static boolean isColumnExclusion(final int currentCandidate,
                                             final int candidateDestinationCoordinate) {
        return (BoardUtils.INSTANCE.FIRST_COLUMN.get(candidateDestinationCoordinate) && (currentCandidate == -1)) ||
                (BoardUtils.INSTANCE.EIGHTH_COLUMN.get(candidateDestinationCoordinate) && (currentCandidate == 1));
    }
}
