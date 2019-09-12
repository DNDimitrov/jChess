package com.chess.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.MoveTransition;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#593E1A");
    private Board chessBoard;
    private static String defaultPieceImagesPath = "E:/jChess/art/fancy/";
    private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;
    private BoardDirection boardDirection;
    private boolean higlightLegalMoves;

    private static final Dimension FRAME_DIMENSION_SIZE = new Dimension(600,600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400,350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);

    public Table() {
        this.gameFrame = new JFrame("JChess");
        final JMenuBar tableManuBar = createTableMenuBar();
        this.gameFrame.setLayout(new BorderLayout());
        this.gameFrame.setJMenuBar(tableManuBar);
        this.gameFrame.setSize(FRAME_DIMENSION_SIZE);
        this.chessBoard = Board.createStandartBoard();
        this.boardPanel = new BoardPanel();
        this.boardDirection = BoardDirection.NORMAL;
        this.higlightLegalMoves = false;
        this.gameFrame.add(this.boardPanel,BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableManuBar = new JMenuBar();
        tableManuBar.add(createFileMenu());
        tableManuBar.add(createPreferencesMenu());
        return  tableManuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("open up that pgn file");
            }
        });

        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);
        fileMenu.add(openPGN);
        return fileMenu;
    }

    private JMenu createPreferencesMenu() {
        final JMenu preferencesMenu = new JMenu("Preferencses");
        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip board");
        flipBoardMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardDirection = boardDirection.opposite();
                boardPanel.drawBoard(chessBoard);
            }
        });
        preferencesMenu.add(flipBoardMenuItem);
        preferencesMenu.addSeparator();
        final JCheckBoxMenuItem legalMoveHiglighterCheckbox = new JCheckBoxMenuItem("Highlight legal moves",false);
        legalMoveHiglighterCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                higlightLegalMoves = legalMoveHiglighterCheckbox.isSelected();
            }
        });

        preferencesMenu.add(legalMoveHiglighterCheckbox);
        return preferencesMenu;
    }

    public enum BoardDirection {
        NORMAL {
            @Override
            java.util.List<TilePanel> traverse(final java.util.List<TilePanel> boardTiles) {
                return boardTiles;
            }
            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            java.util.List<TilePanel> traverse(final java.util.List<TilePanel> boardTiles) {
                List<TilePanel> list = reverseList(boardTiles);
                return list;
            }
            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };
        abstract java.util.List<TilePanel> traverse(final java.util.List<TilePanel> boardTiles);
        abstract BoardDirection opposite();
    }

    public static<T> List<T> reverseList(List<T> list)
    {
        return list.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(ArrayList::new), lst -> {
                            Collections.reverse(lst);
                            return lst.stream();
                        }
                )).collect(Collectors.toCollection(ArrayList::new));
    }

    private class BoardPanel extends JPanel {
        final java.util.List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for(int i=0;i< BoardUtils.NUM_TILES;i++) {
                final TilePanel tilePanel = new TilePanel(this,i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();

        }

        public void drawBoard(final Board board) {
            removeAll();
            for(final TilePanel tilePanel : boardDirection.traverse(boardTiles)) {
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    public static class MoveLog {
        private final List<Move> moves;
        MoveLog() {
            this.moves = new ArrayList<>();
        }
        public List<Move> getMoves() {
            return this.moves;
        }
        public void addMove(final Move move) {
            this.moves.add(move);
        }
        public int size() {
            return this.moves.size();
        }
        public void clear() {
            this.moves.clear();
        }
        public Move removeMove(int index) {
            return this.moves.remove(index);
        }
        public boolean removeMove(final Move move) {
            return this.moves.remove(move);
        }
    }

    private class TilePanel extends JPanel {
        private final int tileId;
        TilePanel(final BoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColour();
            assignPieceIcon(chessBoard);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (isRightMouseButton(e)) {
                        sourceTile = null;
                        destinationTile = null;
                        humanMovedPiece = null;
                    }
                    if (isLeftMouseButton(e)) {
                        if (sourceTile == null) {
                            sourceTile = chessBoard.getTile(tileId);
                            humanMovedPiece = sourceTile.getPiece();
                            if (humanMovedPiece == null) {
                                sourceTile = null;
                            }
                        }
                        else {
                                destinationTile = chessBoard.getTile(tileId);
                                final Move move = Move.MoveFactory.createMove(chessBoard,
                                                                              sourceTile.getTileCoordinate(),
                                                                              destinationTile.getTileCoordinate());
                                final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                                if(transition.getMoveStatus().isDone()) {
                                    chessBoard = transition.getTransitionBoard();
                                    //TODO move log
                                }
                                sourceTile = null;
                                destinationTile = null;
                                humanMovedPiece = null;
                            }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                boardPanel.drawBoard(chessBoard);
                            }
                        });
                        }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            validate();
        }

        private void highlightLegals(final Board board) {
            if(higlightLegalMoves) {
                for(final Move move : pieceLegalMoves(board)) {
                    if(move.getDestinationCoordinate() == this.tileId) {
                        try {
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("E:/jChess/art/misc/green_dot.png")))));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private List<Move> pieceLegalMoves (final Board board) {
            if(humanMovedPiece != null && humanMovedPiece.getAlliance() == board.currentPlayer().getAlliance()) {
                return humanMovedPiece.calculateLegalMoves(board);
            }
            return Collections.emptyList();
        }

        public void drawTile(final Board board){
            assignTileColour();
            assignPieceIcon(board);
            highlightLegals(board);
            validate();
            repaint();
        }
        private void assignPieceIcon(final Board board) {
            this.removeAll();
            if(board.getTile(this.tileId).isOccupied()) {

                //search for folder in the path for files starting with first letter and alliance .gif (example Black knight - BK.gif )
                try {
                    final BufferedImage image =
                            ImageIO.read(new File(defaultPieceImagesPath  + board.getTile(this.tileId).getPiece().getAlliance().toString().substring(0, 1) +
                                    board.getTile(this.tileId).getPiece().toString()+ ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                }
            catch (Exception e) {
                e.printStackTrace();
                }
            }
        }

        private void assignTileColour() {
            boolean isLight = ((tileId + tileId / 8) % 2 == 0);
            setBackground(isLight ? lightTileColor : darkTileColor);
        }

    }
}
