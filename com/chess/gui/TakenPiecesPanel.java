package com.chess.gui;

import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.WhitePlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TakenPiecesPanel extends JPanel {

    private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(40,80);
    private final JPanel northPanel;
    private final JPanel southPanel;
    private static final Color PANEL_COLOR = Color.decode("0xFDFE6");
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
    public TakenPiecesPanel() {
        super(new BorderLayout());
        setBackground(PANEL_COLOR);
        setBorder(PANEL_BORDER);
        this.northPanel = new JPanel(new GridLayout(8,2));
        this.southPanel = new JPanel(new GridLayout(8,2));
        this.northPanel.setBackground(PANEL_COLOR);
        this.southPanel.setBackground(PANEL_COLOR);
        this.add(this.northPanel,BorderLayout.NORTH);
        this.add(this.southPanel,BorderLayout.SOUTH);
        setPreferredSize(TAKEN_PIECES_DIMENSION);

    }
    public void redo(final Table.MoveLog moveLog) {
        southPanel.removeAll();
        northPanel.removeAll();
        final java.util.List<Piece> whiteTakenPieces = new ArrayList<>();
        final java.util.List<Piece> blackTakenPieces = new ArrayList<>();
        for(final Move move : moveLog.getMoves()) {
            if(move.isAttackMove()) {
                final Piece takenPiece = move.getAttackedPiece();
                if(takenPiece.getAlliance().isWhite()) {
                    whiteTakenPieces.add(takenPiece);
                }
                else if(takenPiece.getAlliance().isBlack()) {
                    blackTakenPieces.add(takenPiece);
                }
                else {
                    throw new RuntimeException("Should not reached here");
                }
            }
        }
        Collections.sort(whiteTakenPieces, new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return Integer.compare(o1.getPieceValue(),o2.getPieceValue());
            }
        });
        Collections.sort(blackTakenPieces, new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return Integer.compare(o1.getPieceValue(),o2.getPieceValue());
            }
        });
        for(final Piece takenPiece : whiteTakenPieces) {
            try {
                final BufferedImage image= ImageIO.read(new File("E:/jChess/art/fancy" +
                        takenPiece.getAlliance().toString().substring(0,1) + "" + takenPiece.toString()));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLebel = new JLabel();
                this.southPanel.add(imageLebel);

            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        for(final Piece takenPiece : blackTakenPieces) {
            try {
                final BufferedImage image= ImageIO.read(new File("E:/jChess/art/fancy" +
                        takenPiece.getAlliance().toString().substring(0,1) + "" + takenPiece.toString()));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLebel = new JLabel();
                this.southPanel.add(imageLebel);

            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        validate();
    }
}
