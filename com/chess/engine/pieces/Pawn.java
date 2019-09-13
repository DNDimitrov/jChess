package com.chess.engine.pieces;

import com.chess.engine.board.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pawn extends Piece {
    private final static int[] CANDIDATE_MOVE_COORDINATES = {7,8,9,16};

    public Pawn(final int piecePosition, final Alliance pieceAlliance) {
        super(PieceType.PAWN, piecePosition, pieceAlliance,true);
    }
    public Pawn(final int piecePosition, final Alliance pieceAlliance,boolean isFirstMove) {
        super(PieceType.PAWN, piecePosition, pieceAlliance,isFirstMove);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset  : CANDIDATE_MOVE_COORDINATES) {
            final int candidateDestinationCoordinates = this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidateOffset);
            if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinates)) {
                continue;
            }
            if(currentCandidateOffset  == 8 && !board.getTile(candidateDestinationCoordinates).isOccupied()) {
                //to do the other moves
                legalMoves.add(new Move.MajorMove(board,this, candidateDestinationCoordinates));
            }
            else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                    ((BoardUtils.INSTANCE.SECOND_ROW.get(this.piecePosition) && this.pieceAlliance.isBlack()) ||
                            (BoardUtils.INSTANCE.SEVENTH_ROW.get(this.piecePosition) && this.pieceAlliance.isWhite())))  {
                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
                if(!board.getTile(behindCandidateDestinationCoordinate).isOccupied() &&
                        !board.getTile(candidateDestinationCoordinates).isOccupied()) {
                    legalMoves.add(new Move.PawnJump(board,this, candidateDestinationCoordinates));
                }
            }


            else if(currentCandidateOffset == 7 &&
                    !((BoardUtils.INSTANCE.EIGHTH_COLUMN.get(this.piecePosition) && this.pieceAlliance.isWhite()) ||
                            (BoardUtils.INSTANCE.FIRST_COLUMN.get(this.piecePosition) && this.pieceAlliance.isBlack()))) {
                if(board.getTile(candidateDestinationCoordinates).isOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinates).getPiece();
                    if(pieceOnCandidate.pieceAlliance != this.getAlliance()) {
                        //todo
                        legalMoves.add(new Move.PawnAttackMove(board,this, candidateDestinationCoordinates, pieceOnCandidate));
                    }
                }

            }
            else if(currentCandidateOffset == 9 &&
                    !((BoardUtils.INSTANCE.FIRST_COLUMN.get(this.piecePosition) && this.pieceAlliance.isWhite()) ||
                            (BoardUtils.INSTANCE.EIGHTH_COLUMN.get(this.piecePosition) && this.pieceAlliance.isBlack())))  {
                if(board.getTile(candidateDestinationCoordinates).isOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinates).getPiece();
                    if(pieceOnCandidate.pieceAlliance != this.getAlliance()) {
                        //todo
                        legalMoves.add(new Move.PawnAttackMove(board,this, candidateDestinationCoordinates,pieceOnCandidate));
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }
    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getDestinationCoordinate(),move.getMovedPieace().getAlliance());
    }
    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}
