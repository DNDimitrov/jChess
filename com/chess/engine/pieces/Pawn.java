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
        super(PieceType.PAWN, piecePosition, pieceAlliance);
    }

    @Override
    public List<Move> calculatedMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int offset : CANDIDATE_MOVE_COORDINATES) {
            final int candidateDestinationCoordinates = this.piecePosition + offset * this.getAlliance().getDirection();
            if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinates)) {
                continue;
            }
            if(offset == 8 && !board.getTile(candidateDestinationCoordinates).isOccupied()) {
                //to do the other moves
                legalMoves.add(new Move.MajorMove(board,this, candidateDestinationCoordinates));
            }
            else if(offset == 16 && this.isFirstMove() &&
                    (BoardUtils.SEVENTH_RANK[this.piecePosition] && this.getAlliance().isBlack())
                    && (BoardUtils.SECOND_RANK[this.piecePosition] && this.getAlliance().isWhite())){
                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
                if(!board.getTile(behindCandidateDestinationCoordinate).isOccupied() &&
                        !board.getTile(candidateDestinationCoordinates).isOccupied()) {
                    legalMoves.add(new Move.MajorMove(board,this, candidateDestinationCoordinates));
                }
            }
            else if(offset == 7 && !((BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()||
                    (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))) {
                if(board.getTile(candidateDestinationCoordinates).isOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinates).getPiece();
                    if(pieceOnCandidate.pieceAlliance != this.getAlliance()) {
                        //todo
                        legalMoves.add(new Move.MajorMove(board,this, candidateDestinationCoordinates));
                    }
                }

            }
            else if(offset == 9 && !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()||
                    (BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))))  {
                if(board.getTile(candidateDestinationCoordinates).isOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinates).getPiece();
                    if(pieceOnCandidate.pieceAlliance != this.getAlliance()) {
                        //todo
                        legalMoves.add(new Move.MajorMove(board,this, candidateDestinationCoordinates));
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
