package com.example.sam.game.chess.helper;

import com.example.sam.game.chess.model.Coordinates;

import java.util.ArrayList;

public class MoveHelper {

    public MoveHelper() {

    }

    public ArrayList<Coordinates> getMovesNorthEast(Coordinates position) {
        ArrayList<Coordinates> moves = new ArrayList<>();
        for (int x = 1; x < 8; x++) {
            moves.add(new Coordinates(position.getX() + x, position.getY() - x));
        }
        return moves;
    }

    public ArrayList<Coordinates> getMovesNorthWest(Coordinates position) {
        ArrayList<Coordinates> moves = new ArrayList<>();
        for (int x = 1; x < 8; x++) {
            moves.add(new Coordinates(position.getX() - x, position.getY() - x));
        }
        return moves;
    }

    public ArrayList<Coordinates> getMovesSouthEast(Coordinates position) {
        ArrayList<Coordinates> moves = new ArrayList<>();
        for (int x = 1; x < 8; x++) {
            moves.add(new Coordinates(position.getX() + x, position.getY() + x));
        }
        return moves;
    }

    public ArrayList<Coordinates> getMovesSouthWest(Coordinates position) {
        ArrayList<Coordinates> moves = new ArrayList<>();
        for (int x = 1; x < 8; x++) {
            moves.add(new Coordinates(position.getX() - x, position.getY() + x));
        }
        return moves;
    }

    public ArrayList<Coordinates> getMovesEast(Coordinates position) {
        ArrayList<Coordinates> moves = new ArrayList<>();
        for (int x = 1; x < 8; x++) {
            moves.add(new Coordinates(position.getX() + x, position.getY()));
        }
        return moves;
    }

    public ArrayList<Coordinates> getMovesWest(Coordinates position) {
        ArrayList<Coordinates> moves = new ArrayList<>();
        for (int x = 1; x < 8; x++) {
            moves.add(new Coordinates(position.getX() - x, position.getY()));
        }
        return moves;
    }

    public ArrayList<Coordinates> getMovesNorth(Coordinates position) {
        ArrayList<Coordinates> moves = new ArrayList<>();
        for (int x = 1; x < 8; x++) {
            moves.add(new Coordinates(position.getX(), position.getY() + x));
        }
        return moves;
    }

    public ArrayList<Coordinates> getMovesSouth(Coordinates position) {
        ArrayList<Coordinates> moves = new ArrayList<>();
        for (int x = 1; x < 8; x++) {
            moves.add(new Coordinates(position.getX(), position.getY() - x));
        }
        return moves;
    }

    public ArrayList<Coordinates> getKingMoves(Coordinates position) {
        ArrayList<Coordinates> moves = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        moves.add(new Coordinates(x - 1, y + 1));
        moves.add(new Coordinates(x, y + 1));
        moves.add(new Coordinates(x + 1, y + 1));
        moves.add(new Coordinates(x - 1, y));
        moves.add(new Coordinates(x + 1, y));
        moves.add(new Coordinates(x - 1, y - 1));
        moves.add(new Coordinates(x, y - 1));
        moves.add(new Coordinates(x + 1, y - 1));
        return moves;
    }

    public ArrayList<Coordinates> getKnightMoves(Coordinates position) {
        ArrayList<Coordinates> moves = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        moves.add(new Coordinates(x - 2, y + 1));
        moves.add(new Coordinates(x + 2, y + 1));
        moves.add(new Coordinates(x - 2, y - 1));
        moves.add(new Coordinates(x + 2, y - 1));
        moves.add(new Coordinates(x - 1, y + 2));
        moves.add(new Coordinates(x - 1, y - 2));
        moves.add(new Coordinates(x + 1, y + 2));
        moves.add(new Coordinates(x + 1, y - 2));
        return moves;
    }

    public ArrayList<Coordinates> getPawnMoves(Coordinates position, boolean hasMoved, int color) {
        ArrayList<Coordinates> moves = new ArrayList<>();
        if (color == 0) {
            if (!hasMoved) {
                moves.add(new Coordinates(position.getX(), position.getY() - 2));
            }
            moves.add(new Coordinates(position.getX(), position.getY() - 1));

        } else {
            if (!hasMoved) {
                moves.add(new Coordinates(position.getX(), position.getY() + 2));
            }
            moves.add(new Coordinates(position.getX(), position.getY() + 1));
        }
        return moves;
    }

    public ArrayList<Coordinates> getPawnAttacks(Coordinates position, int color){
        ArrayList<Coordinates> moves = new ArrayList<>();
        if (color == 0) {
            moves.add(new Coordinates(position.getX() - 1, position.getY() - 1));
            moves.add(new Coordinates(position.getX() + 1, position.getY() - 1));

        } else {
            moves.add(new Coordinates(position.getX() - 1, position.getY() + 1));
            moves.add(new Coordinates(position.getX() + 1, position.getY() + 1));
        }
        return moves;
    }
}
