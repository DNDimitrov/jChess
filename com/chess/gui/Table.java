package com.chess.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Table {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#593E1A");
    private final Board chessBoard;
    private static String defaultPieceImagesPath = "E:/jChess/art/fancy/";

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
        this.gameFrame.add(this.boardPanel,BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableManuBar = new JMenuBar();
        tableManuBar.add(createFileMenu());
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
    }

    private class TilePanel extends JPanel {
        private final int tileId;
        TilePanel(final BoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColour();
            assignPieceIcon(chessBoard);
            validate();
        }

        private void assignPieceIcon(final Board board) {
            this.removeAll();
            if(board.getTile(this.tileId).isOccupied()) {

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
