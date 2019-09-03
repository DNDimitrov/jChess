package com.chess.engine;

import com.chess.engine.board.Board;

public class jChess {

    public static void main(String[] args) {
        Board board  = Board.createStandartBoard();

        System.out.println(board);
    }
}
