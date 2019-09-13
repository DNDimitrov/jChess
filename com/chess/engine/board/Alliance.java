package com.chess.engine.board;

import com.chess.engine.player.Player;

public enum Alliance {
    WHITE {
        @Override
        public int getDirection() {
            return -1;
        }
        @Override
        public int getOppositeDirection() {return 1;}
        @Override
        public boolean isWhite() {
            return true;
        }
        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public boolean isPawnPromotionSquare(int position) {
            return BoardUtils.INSTANCE.FIRST_ROW.get(position);
        }

        @Override
        public Player choosePlayer(final Player whitePlayer,
                                   final Player blackPlayer) {
            return whitePlayer;
        }
    },
    BLACK {
        @Override
        public int getDirection() {
            return 1;
        }
        @Override
        public int getOppositeDirection() {return -1;}
        @Override
        public boolean isWhite() {
            return false;
        }
        @Override
        public  boolean isBlack(){
            return true;
        }

        @Override
        public boolean isPawnPromotionSquare(int position) {
            return BoardUtils.INSTANCE.EIGHTH_ROW.get(position);
        }

        public Player choosePlayer(final Player whitePlayer,
                                   final Player blackPlayer) {
            return blackPlayer;
        }
    };

    public abstract int getDirection();
    public abstract int getOppositeDirection();
    public abstract boolean isWhite();
    public abstract  boolean isBlack();
    public abstract boolean isPawnPromotionSquare(int position);

    public abstract Player choosePlayer(Player whitePlayer, Player blackPlayer);
}
