package com.example.sam.game.chess.model;

import android.widget.ImageView;

public class GameSquare {

    private Coordinates coordinates;
    private int position;
    private boolean occupied;
    private int colourOfPiece;
    private int colourOfSquare;
    ImageView imageView;

    public GameSquare() {

    }

    public GameSquare(Coordinates coordinates, int position, boolean occupied, int color) {
        this.coordinates = coordinates;
        this.position = position;
        this.occupied = occupied;
        this.colourOfPiece = color;
    }



    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public int getColourOfPiece() {
        return colourOfPiece;
    }

    public void setColourOfPiece(int colourOfPiece) {
        this.colourOfPiece = colourOfPiece;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public int getColourOfSquare() {
        return colourOfSquare;
    }

    public void setColourOfSquare(int colourOfSquare) {
        this.colourOfSquare = colourOfSquare;
    }

    public String printInfo() {
        return "Info for location: ";
    }
}
