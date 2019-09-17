package com.chess.engine.board;

import com.chess.engine.pieces.*;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

import java.util.*;

public class Board {

    private final List<Tile> gameBoard;
    private final List<Piece> whites;
    private final List<Piece> blacks;

    public Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;


    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;

    private final Pawn enPessantPawn;

    private Board (Builder builder) {
        this.gameBoard = createGameBoard(builder);
        this.blacks = calculateActivePieces(this.gameBoard,Alliance.BLACK);
        this.whites = calculateActivePieces(this.gameBoard,Alliance.WHITE);
        this.enPessantPawn = builder.enPessantPawn;
        final List<Move> whitesStandartLegalMoves = calculateStandartMoves(this.whites);
        final List<Move> blacksStandartLegalMoves = calculateStandartMoves(this.blacks);
        this.whitePlayer = new WhitePlayer(this,whitesStandartLegalMoves,blacksStandartLegalMoves);
        this.blackPlayer = new BlackPlayer(this,whitesStandartLegalMoves,blacksStandartLegalMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer(),this.blackPlayer());

    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for(int i=0;i<BoardUtils.NUM_TILES;i++) {
            final String tileText = this.gameBoard.get(i).toString();
            builder.append(String.format("%3s",tileText));
            if((i+1) % BoardUtils.NUM_TILES_PER_ROW == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public Player currentPlayer() {
        return this.currentPlayer;
    }


    public List<Piece> getBlackPieces(){
        return this.blacks;
    }

    public Player whitePlayer() {
        return  this.whitePlayer;
    }

    public Player blackPlayer() {
        return  this.blackPlayer;
    }

    public List<Piece> getWhitePieces(){
        return this.whites;
    }

    private List<Move> calculateStandartMoves(final List<Piece> alliance) {
        final List<Move> legalMoves = new ArrayList<>();
        for(final Piece piece : alliance) {
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }
        return Collections.unmodifiableList(legalMoves);
    }

   // private List<Move> calculateLegalMoves(final List<Piece> pieces) {
   //     return pieces.stream().flatMap(piece -> piece.calculateLegalMoves(this).stream())
   //             .collect(Collectors.toList());
  //  }

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
        builder.setPiece(new King(4,Alliance.BLACK,true,true));
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
        builder.setPiece(new Pawn(48,Alliance.WHITE));
        builder.setPiece(new Pawn(49,Alliance.WHITE));
        builder.setPiece(new Pawn(50,Alliance.WHITE));
        builder.setPiece(new Pawn(51,Alliance.WHITE));
        builder.setPiece(new Pawn(52,Alliance.WHITE));
        builder.setPiece(new Pawn(53,Alliance.WHITE));
        builder.setPiece(new Pawn(54,Alliance.WHITE));
        builder.setPiece(new Pawn(55,Alliance.WHITE));
        builder.setPiece(new Rook(56,Alliance.WHITE));
        builder.setPiece(new Knight(57,Alliance.WHITE));
        builder.setPiece(new Bishop(58,Alliance.WHITE));
        builder.setPiece(new Queen(59,Alliance.WHITE));
        builder.setPiece(new King(60,Alliance.WHITE,true,true));
        builder.setPiece(new Bishop(61,Alliance.WHITE));
        builder.setPiece(new Knight(62,Alliance.WHITE));
        builder.setPiece(new Rook(63,Alliance.WHITE));

        builder.setMoveMaker(Alliance.WHITE);

        return builder.build();

    }

    public List<Move> getAllMoves() {
        List<Move> allMoves = new ArrayList<>();
        allMoves.addAll(this.whitePlayer.getLegalMoves());
        allMoves.addAll(this.blackPlayer.getLegalMoves());

        return allMoves;

    }

    public Pawn getEnPessantPawn() {
        return this.enPessantPawn;
    }

    public static class Builder {

        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;
        Pawn enPessantPawn;

        public Builder() {
            this.boardConfig = new HashMap<>();
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

        public void setEnPessantPawn(Pawn movedPawn) {
            this.enPessantPawn = movedPawn;
        }
    }
}
