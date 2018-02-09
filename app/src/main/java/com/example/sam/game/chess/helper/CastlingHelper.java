package com.example.sam.game.chess.helper;

public class CastlingHelper {

    private int colour;
    private boolean kingHasMoved;
    private boolean rookHasMoved;

    public CastlingHelper(int colour) {
        this.colour = colour;
        kingHasMoved = false;
        rookHasMoved = false;
    }

    public boolean isKingHasMoved() {
        return kingHasMoved;
    }

    public void setKingHasMoved(boolean kingHasMoved) {
        this.kingHasMoved = kingHasMoved;
    }

    public boolean isRookHasMoved() {
        return rookHasMoved;
    }

    public void setRookHasMoved(boolean rookHasMoved) {
        this.rookHasMoved = rookHasMoved;
    }
}
