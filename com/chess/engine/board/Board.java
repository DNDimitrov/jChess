package com.chess.engine.board;

import com.chess.engine.pieces.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Board {

    private final List<Tile> gameBoard;
    private final List<Piece> whites;
    private final List<Piece> blacks;


    private Board (Builder builder) {
        this.gameBoard = createGameBoard(builder);
        this.blacks = calculateActivePieces(this.gameBoard,Alliance.BLACK);
        this.whites = calculateActivePieces(this.gameBoard,Alliance.WHITE);

        final List<Move> whitesStandartLegalMoves = calculateStandartMoves(this.whites);
        final List<Move> blacksStandartLegalMoves = calculateStandartMoves(this.blacks);

    }

    private List<Move> calculateStandartMoves(final List<Piece> alliance) {
        final List<Move> legalMoves = new ArrayList<>();
        for(final Piece piece : alliance) {
            legalMoves.addAll(piece.calculatedMoves(this));
        }
        return Collections.unmodifiableList(legalMoves);
    }

    private static List<Piece> calculateActivePieces(List<Tile> gameBoard, Alliance alliance) {
        final List<Piece> activePieces = new ArrayList<>();
        for(final Tile tile : gameBoard) {
            if(tile.isOccupied()) {
                final Piece piece = tile.getPiece();
                if(piece.getAlliance() == alliance) {
                    activePieces.add(piece);
                }
             }
        }
        return Collections.unmodifiableList(activePieces);
    }

    public Tile getTile(final int tileCoordinates) {
        return gameBoard.get(tileCoordinates);
    }

    private static List<Tile> createGameBoard(final Builder builder) {
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        final List<Tile> board = new ArrayList<>();
        for(int i=0;i<BoardUtils.NUM_TILES;i++) {
            tiles[i]=Tile.createTile(i,builder.boardConfig.get(i));

            //check if it creates the right objects and puts them in the list
            board.add(tiles[i]);
        }
        return Collections.unmodifiableList(board);
    }

    public static Board createStandartBoard() {
        final Builder builder = new Builder();

        //Blacks
        builder.setPiece(new Rook(0,Alliance.BLACK));
        builder.setPiece(new Knight(1,Alliance.BLACK));
        builder.setPiece(new Bishop(2,Alliance.BLACK));
        builder.setPiece(new Queen(3,Alliance.BLACK));
        builder.setPiece(new King(4,Alliance.BLACK));
        builder.setPiece(new Bishop(5,Alliance.BLACK));
        builder.setPiece(new Knight(6,Alliance.BLACK));
        builder.setPiece(new Rook(7,Alliance.BLACK));
        builder.setPiece(new Pawn(8,Alliance.BLACK));
        builder.setPiece(new Pawn(9,Alliance.BLACK));
        builder.setPiece(new Pawn(10,Alliance.BLACK));
        builder.setPiece(new Pawn(11,Alliance.BLACK));
        builder.setPiece(new Pawn(12,Alliance.BLACK));
        builder.setPiece(new Pawn(13,Alliance.BLACK));
        builder.setPiece(new Pawn(14,Alliance.BLACK));
        builder.setPiece(new Pawn(15,Alliance.BLACK));

        //Whites
        builder.setPiece(new Rook(48,Alliance.WHITE));
        builder.setPiece(new Knight(49,Alliance.WHITE));
        builder.setPiece(new Bishop(50,Alliance.WHITE));
        builder.setPiece(new Queen(51,Alliance.WHITE));
        builder.setPiece(new King(52,Alliance.WHITE));
        builder.setPiece(new Bishop(53,Alliance.WHITE));
        builder.setPiece(new Knight(54,Alliance.WHITE));
        builder.setPiece(new Rook(55,Alliance.WHITE));
        builder.setPiece(new Pawn(56,Alliance.WHITE));
        builder.setPiece(new Pawn(57,Alliance.WHITE));
        builder.setPiece(new Pawn(58,Alliance.WHITE));
        builder.setPiece(new Pawn(59,Alliance.WHITE));
        builder.setPiece(new Pawn(60,Alliance.WHITE));
        builder.setPiece(new Pawn(61,Alliance.WHITE));
        builder.setPiece(new Pawn(62,Alliance.WHITE));
        builder.setPiece(new Pawn(63,Alliance.WHITE));

        builder.setMoveMaker(Alliance.WHITE);

        return builder.build();

    }

    public static class Builder {

        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;

        public Builder() {
        }

        public  Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker(final Alliance nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }
        public Board build() {
            return new Board(this);
        }
    }
}
