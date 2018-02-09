package com.example.sam.game.chess.helper;

import com.example.sam.game.chess.model.Coordinates;
import com.example.sam.game.chess.model.PieceTracker;

import java.util.ArrayList;
import java.util.HashMap;

public class MoveHelper {

    public MoveHelper() {

    }

    private ArrayList<Coordinates> getTheoreticalMovesNorthEast(Coordinates position) {
        ArrayList<Coordinates> moves = new ArrayList<>();
        for (int x = 1; x < 8; x++) {
            moves.add(new Coordinates(position.getX() + x, position.getY() - x));
        }
        return moves;
    }

    private ArrayList<Coordinates> getTheoreticalMovesNorthWest(Coordinates position) {
        ArrayList<Coordinates> moves = new ArrayList<>();
        for (int x = 1; x < 8; x++) {
            moves.add(new Coordinates(position.getX() - x, position.getY() - x));
        }
        return moves;
    }

    private ArrayList<Coordinates> getTheoreticalMovesSouthEast(Coordinates position) {
        ArrayList<Coordinates> moves = new ArrayList<>();
        for (int x = 1; x < 8; x++) {
            moves.add(new Coordinates(position.getX() + x, position.getY() + x));
        }
        return moves;
    }

    private ArrayList<Coordinates> getTheoreticalMovesSouthWest(Coordinates position) {
        ArrayList<Coordinates> moves = new ArrayList<>();
        for (int x = 1; x < 8; x++) {
            moves.add(new Coordinates(position.getX() - x, position.getY() + x));
        }
        return moves;
    }

    public ArrayList<Coordinates> getTheoreticalMovesEast(Coordinates position) {
        ArrayList<Coordinates> moves = new ArrayList<>();
        for (int x = 1; x < 8; x++) {
            moves.add(new Coordinates(position.getX() + x, position.getY()));
        }
        return moves;
    }

    public ArrayList<Coordinates> getTheoreticalMovesWest(Coordinates position) {
        ArrayList<Coordinates> moves = new ArrayList<>();
        for (int x = 1; x < 8; x++) {
            moves.add(new Coordinates(position.getX() - x, position.getY()));
        }
        return moves;
    }

    public ArrayList<Coordinates> getTheoreticalMovesNorth(Coordinates position) {
        ArrayList<Coordinates> moves = new ArrayList<>();
        for (int x = 1; x < 8; x++) {
            moves.add(new Coordinates(position.getX(), position.getY() + x));
        }
        return moves;
    }

    public ArrayList<Coordinates> getTheoreticalMovesSouth(Coordinates position) {
        ArrayList<Coordinates> moves = new ArrayList<>();
        for (int x = 1; x < 8; x++) {
            moves.add(new Coordinates(position.getX(), position.getY() - x));
        }
        return moves;
    }

    public ArrayList<Coordinates> getTheoreticalKingMoves(Coordinates position) {
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

    public ArrayList<Coordinates> getTheoreticalKnightMoves(Coordinates position) {
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

    public ArrayList<Coordinates> getTheoreticalPawnMoves(Coordinates position, boolean hasMoved, int color) {
        ArrayList<Coordinates> moves = new ArrayList<>();
        if (color == 0) {
            moves.add(new Coordinates(position.getX(), position.getY() - 1));
            if (!hasMoved) {
                moves.add(new Coordinates(position.getX(), position.getY() - 2));
            }


        } else {
            moves.add(new Coordinates(position.getX(), position.getY() + 1));
            if (!hasMoved) {
                moves.add(new Coordinates(position.getX(), position.getY() + 2));
            }

        }
        return moves;
    }

    public ArrayList<Coordinates> getTheoreticalPawnAttacks(Coordinates position, int color){
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


    public ArrayList<Coordinates> getMovesAndAttacksForKing(PieceTracker selectedPiece, HashMap<Coordinates, PieceTracker> boardState) {
        ArrayList<Coordinates> list = new ArrayList<>();
        Coordinates position = selectedPiece.getPosition();

        for (Coordinates c :
                getTheoreticalKingMoves(position)) {
            PieceTracker destination = boardState.get(c);
            if (withinBounds(c) && !destination.isOccupied()) {
                list.add(c);
            }
            if (withinBounds(c) && destination.isOccupied() && checkUnfriendly(selectedPiece, destination)) {
                list.add(c);
            }
        }

        return list;
    }

    public ArrayList<Coordinates> getMovesAndAttacksForKnight(PieceTracker selectedPiece, HashMap<Coordinates, PieceTracker> boardState) {
        ArrayList<Coordinates> list = new ArrayList<>();
        Coordinates position = selectedPiece.getPosition();

        for (Coordinates c :
                getTheoreticalKnightMoves(position)) {
            PieceTracker destination = boardState.get(c);

            if (withinBounds(c) && !destination.isOccupied()) {
                list.add(c);
            }
            if (withinBounds(c) && destination.isOccupied() && checkUnfriendly(selectedPiece, destination)) {
                list.add(c);
            }
        }


        return list;
    }

    public ArrayList<Coordinates> getMovesAndAttacksForPawn(PieceTracker selectedPiece, HashMap<Coordinates, PieceTracker> boardState) {
        ArrayList<Coordinates> list = new ArrayList<>();
        Coordinates position = selectedPiece.getPosition();

        for (Coordinates c :
                getTheoreticalPawnMoves(position, selectedPiece.hasMoved(), selectedPiece.getColor())) {
            if (withinBounds(c) && !boardState.get(c).isOccupied()) {

                list.add(c);

            } else break;
        }

        for (Coordinates c :
                getTheoreticalPawnAttacks(position, selectedPiece.getColor())) {
            if (withinBounds(c) && boardState.get(c).isOccupied() && checkUnfriendly(selectedPiece, boardState.get(c))) {
                list.add(c);
            }
        }

        return list;

    }

    private ArrayList<Coordinates> getAllHorizontalsAndVerticals(PieceTracker selectedPiece, HashMap<Coordinates, PieceTracker> boardState){

        ArrayList<Coordinates> list = new ArrayList<>();
        Coordinates position = selectedPiece.getPosition();

        for (Coordinates c :
                getTheoreticalMovesEast(position)) {
            if (findMoves(selectedPiece, boardState, list, c)) break;

        }
        for (Coordinates c :
                getTheoreticalMovesWest(position)) {
            if (findMoves(selectedPiece, boardState, list, c)) break;

        }
        for (Coordinates c :
                getTheoreticalMovesSouth(position)) {
            if (findMoves(selectedPiece, boardState, list, c)) break;

        }
        for (Coordinates c :
                getTheoreticalMovesNorth(position)) {
            if (findMoves(selectedPiece, boardState, list, c)) break;
        }

        return list;
    }

    private ArrayList<Coordinates> getAllDiagonals(PieceTracker selectedPiece, HashMap<Coordinates, PieceTracker> boardState) {
        ArrayList<Coordinates> list = new ArrayList<>();
        Coordinates position = selectedPiece.getPosition();

        for (Coordinates c :
                getTheoreticalMovesNorthEast(position)) {
            if (findMoves(selectedPiece, boardState, list, c)) break;

        }
        for (Coordinates c :
                getTheoreticalMovesNorthWest(position)) {
            if (findMoves(selectedPiece, boardState, list, c)) break;

        }
        for (Coordinates c :
                getTheoreticalMovesSouthEast(position)) {
            if (findMoves(selectedPiece, boardState, list, c)) break;

        }
        for (Coordinates c :
                getTheoreticalMovesSouthWest(position)) {
            if (findMoves(selectedPiece, boardState, list, c)) break;

        }

        return list;
    }

    private boolean findMoves(PieceTracker selectedPiece, HashMap<Coordinates, PieceTracker> boardState, ArrayList<Coordinates> list, Coordinates c) {
        if (withinBounds(c) && boardState.get(c).isOccupied()) {
            if (checkUnfriendly(selectedPiece, boardState.get(c))) {
                list.add(c);
            }
            return true;
        } else if (withinBounds(c)) {
            list.add(c);
        }
        return false;
    }

    public ArrayList<Coordinates> getMovesAndAttacksForQueen(PieceTracker selectedPiece, HashMap<Coordinates, PieceTracker> boardState) {
        ArrayList<Coordinates> list = new ArrayList<>();

        list.addAll(getAllDiagonals(selectedPiece, boardState));
        list.addAll(getAllHorizontalsAndVerticals(selectedPiece, boardState));

        return list;
    }


    public ArrayList<Coordinates> getMovesAndAttacksForBishop(PieceTracker selectedPiece, HashMap<Coordinates, PieceTracker> boardState) {
        return getAllDiagonals(selectedPiece, boardState);
    }

    public ArrayList<Coordinates> getMovesAndAttacksForRook(PieceTracker selectedPiece, HashMap<Coordinates, PieceTracker> boardState) {
        return getAllHorizontalsAndVerticals(selectedPiece, boardState);
    }

    private boolean withinBounds(Coordinates coordinates) {
        return coordinates.getY() > 0 && coordinates.getY() < 9 && coordinates.getX() > 0 && coordinates.getX() < 9;
    }

    private boolean checkUnfriendly(PieceTracker piece, PieceTracker threatenedPiece) {
        return piece.getColour() != threatenedPiece.getColor();
    }
}
