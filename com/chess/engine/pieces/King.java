package com.chess.engine.pieces;

import com.chess.engine.board.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class King extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-7,-8,-9,-1,1,7,8,9};

    public King(final int piecePosition,final Alliance pieceAlliance) {
        super(PieceType.KING, piecePosition, pieceAlliance,true);
    }
    public King(final int piecePosition,final Alliance pieceAlliance,final boolean isFirstMove) {
        super(PieceType.KING, piecePosition, pieceAlliance,isFirstMove);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(int offset : CANDIDATE_MOVE_COORDINATES) {
            if (isFirstColumnExclusion(this.piecePosition, offset) ||
                    isEighthColumnExclusion(this.piecePosition, offset)) {
                continue;
            }
            int candidateDestinationCoordinate = this.piecePosition + offset;
            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if(!candidateDestinationTile.isOccupied()) {
                    legalMoves.add(new Move.MajorMove(board,this, candidateDestinationCoordinate));
                }
                else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAtDestinationAlliance = pieceAtDestination.getAlliance();

                    if(this.pieceAlliance != pieceAtDestinationAlliance) {
                        legalMoves.add(new Move.MajorAttackMove(board,this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }

        }


        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public King movePiece(final Move move) {
        return new King(move.getDestinationCoordinate(),move.getMovedPieace().getAlliance());
    }

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }

    private static boolean isFirstColumnExclusion(final int currentCandidate,
                                                  final int candidateDestinationCoordinate) {
        return BoardUtils.INSTANCE.FIRST_COLUMN.get(currentCandidate)
                && ((candidateDestinationCoordinate == -9) || (candidateDestinationCoordinate == -1) ||
                (candidateDestinationCoordinate == 7));
    }

    private static boolean isEighthColumnExclusion(final int currentCandidate,
                                                   final int candidateDestinationCoordinate) {
        return BoardUtils.INSTANCE.EIGHTH_COLUMN.get(currentCandidate)
                && ((candidateDestinationCoordinate == -7) || (candidateDestinationCoordinate == 1) ||
                (candidateDestinationCoordinate == 9));
    }
}
