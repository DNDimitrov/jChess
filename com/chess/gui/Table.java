package com.chess.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Table {

    private final JFrame gameFrame;

    private static final Dimension FRAME_DIMENSION_SIZE = new Dimension(600,600);

    public Table() {
        this.gameFrame = new JFrame("JChess");
        final JMenuBar tableManuBar = new JMenuBar();
        populateMenuBar(tableManuBar);
        this.gameFrame.setJMenuBar(tableManuBar);
        this.gameFrame.setSize(FRAME_DIMENSION_SIZE);
        this.gameFrame.setVisible(true);
    }

    private void populateMenuBar(JMenuBar tableManuBar) {
        tableManuBar.add(createFileMenu());
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
        fileMenu.add(openPGN);
        return fileMenu;
    }
}
