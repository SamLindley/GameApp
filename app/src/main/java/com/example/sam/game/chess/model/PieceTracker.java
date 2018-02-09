package com.example.sam.game.chess.model;

import android.graphics.Color;
import android.widget.ImageView;

public class PieceTracker {
    private boolean hasMoved;
    private Coordinates position;
    private ImageView imageView;
    private int color;
    private int type;
    private boolean isOccupied;
    private boolean eligibleForMove;
    private int squareColor;

    public PieceTracker(Coordinates coordinates, ImageView imageView, int color, int type, boolean isOccupied, int squareColor) {
        this.position = coordinates;
        this.hasMoved = false;
        this.imageView = imageView;
        this.color = color;
        this.type = type;
        this.isOccupied = isOccupied;
        this.squareColor = squareColor;
        this.eligibleForMove = false;

        if (squareColor == 1) {
            this.imageView.setBackgroundColor(Color.LTGRAY);
        }else this.imageView.setBackgroundColor(Color.WHITE);
    }

    public boolean isEligibleForMove() {
        return eligibleForMove;
    }

    public void setEligibleForMove(boolean eligibleForMove) {
        this.eligibleForMove = eligibleForMove;
    }

    public int getSquareColor() {
        return squareColor;
    }

    public void setSquareColor(int squareColor) {
        this.squareColor = squareColor;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public Coordinates position() {
        return position;
    }

    public int getColour() {
        return color;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public int getType() {
        return type;
    }


    public void setPosition(Coordinates coordinates) {
        this.position = coordinates;
    }

    public void setColour(int colour) {
        this.color = colour;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public Coordinates getPosition() {
        return position;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
