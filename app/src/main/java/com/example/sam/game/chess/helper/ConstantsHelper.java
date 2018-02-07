package com.example.sam.game.chess.helper;

public enum ConstantsHelper {
    KING_SIDE_BLACK_ROOK("ksbr"),
    KING_SIDE_BLACK_KNIGHT("ksbk"),
    KING_SIDE_BLACK_BISHOP("ksbb"),
    BLACK_KING("bk"),
    BLACK_QUEEN("bq"),
    QUEEN_SIDE_BLACK_BISOP("qsbb"),
    QUEEN_SIDE_BLACK_KNIGHT("qsbk"),
    QUEEN_SIDE_BLACK_ROOK("qsbr"),
    BLACK_PAWN_0("bp0"),
    BLACK_PAWN_1("bp1"),
    BLACK_PAWN_2("bp2"),
    BLACK_PAWN_3("bp3"),
    BLACK_PAWN_4("bp4"),
    BLACK_PAWN_5("bp5"),
    BLACK_PAWN_6("bp6"),
    BLACK_PAWN_7("bp7"),
    KING_SIDE_WHITE_ROOK("kswr"),
    KING_SIDE_WHITE_KNIGHT("kswk"),
    KING_SIDE_WHITE_BISHOP("kswb"),
    WHITE_KING("wk"),
    WHITE_QUEEN("wq"),
    QUEEN_SIDE_WHITE_BISOP("qswb"),
    QUEEN_SIDE_WHITE_KNIGHT("qswk"),
    QUEEN_SIDE_WHITE_ROOK("qswr"),
    WHITE_PAWN_0("wp0"),
    WHITE_PAWN_1("wp1"),
    WHITE_PAWN_2("wp2"),
    WHITE_PAWN_3("wp3"),
    WHITE_PAWN_4("wp4"),
    WHITE_PAWN_5("wp5"),
    WHITE_PAWN_6("wp6"),
    WHITE_PAWN_7("wp7");


    private String s;

    ConstantsHelper(String s) {
        this.s = s;
    }

    public String s() {
        return s;
    }
    }
