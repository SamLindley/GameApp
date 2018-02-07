package com.example.sam.game.chess.model;

public enum PositionConverter {
    H1(1, 0, 0),
    H2(2, 0, 1),
    H3(3, 0, 2),
    H4(4, 0, 3);

    private final int numericalPosition;
    private final Coordinates coordinates;

    private PositionConverter(int numericalPosition, int xCoord, int yCoord) {
        this.coordinates = new Coordinates(xCoord, yCoord);
        this.numericalPosition = numericalPosition;
    }

    public int getNumericalPosition() {
        return numericalPosition;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
