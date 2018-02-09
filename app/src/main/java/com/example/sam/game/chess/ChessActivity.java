package com.example.sam.game.chess;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sam.game.R;
import com.example.sam.game.chess.helper.MoveHelper;
import com.example.sam.game.chess.model.Coordinates;
import com.example.sam.game.chess.model.PieceTracker;

import java.util.ArrayList;
import java.util.HashMap;

public class ChessActivity extends AppCompatActivity {

    private final int EMPTY = -1;
    private final int PAWN = 0;
    private final int KNIGHT = 1;
    private final int BISHOP = 2;
    private final int ROOK = 3;
    private final int QUEEN = 4;
    private final int KING = 5;


    private final int GAME_STATE_MOVING = 0;
    private final int GAME_STATE_SELECTING = 1;


    private final static int WHITE = 0;
    private final static int BLACK = 1;
    private int turn = WHITE;

    String TAG = "tag";
    private Coordinates blackKingPosition;
    private Coordinates whiteKingPosition;
    private ArrayList<PieceTracker> piecesCausingCheck = new ArrayList<>();
    private HashMap<Coordinates, PieceTracker> piecePositions;
    private ArrayList<PieceTracker> takenPieces = new ArrayList<>();
    private PieceTracker selectedPiece;
    private ArrayList<PieceTracker> editedSquares;
    private ArrayList<Coordinates> attackMarkers;
    private ArrayList<Coordinates> legalMoves;
    private TextView turnTracker;
    private boolean kingInCheck = false;
    private int gameState = GAME_STATE_SELECTING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chess);
        setUpGame();
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    private void setUpGame() {
        GridLayout piecesLayout = findViewById(R.id.pieces);
        turnTracker = findViewById(R.id.turnTracker);
        piecePositions = new HashMap<>();
        int counter = 1;
        int squareColour = WHITE;
        for (int y = 1; y < 9; y++) {

            // Cancel switch if stating a new row

            if (squareColour == WHITE) {
                squareColour = BLACK;
            } else squareColour = WHITE;


            for (int x = 1; x < 9; x++) {

                ImageView pieceView = (ImageView) piecesLayout.getChildAt(counter - 1);
                Coordinates coordinates = new Coordinates(x, y);


                setUpPieces(counter, pieceView, coordinates, squareColour);
                counter++;
                if (squareColour == WHITE) {
                    squareColour = BLACK;
                } else squareColour = WHITE;
            }
        }
    }

    private void setUpPieces(int counter, ImageView imageView, Coordinates coordinates, int squareColor) {
        PieceTracker piece;

        if (counter == 1 || counter == 8) {
            piece = new PieceTracker(coordinates, imageView, BLACK, ROOK, true, squareColor);
        } else if (counter == 2 || counter == 7) {
            piece = new PieceTracker(coordinates, imageView, BLACK, KNIGHT, true, squareColor);
        } else if (counter == 3 || counter == 6) {
            piece = new PieceTracker(coordinates, imageView, BLACK, BISHOP, true, squareColor);
        } else if (counter == 4) {
            piece = new PieceTracker(coordinates, imageView, BLACK, KING, true, squareColor);
            blackKingPosition = coordinates;
        } else if (counter == 5) {
            piece = new PieceTracker(coordinates, imageView, BLACK, QUEEN, true, squareColor);
        } else if (counter > 8 && counter < 17) {
            piece = new PieceTracker(coordinates, imageView, BLACK, PAWN, true, squareColor);
        } else if (counter > 48 && counter < 57) {
            piece = new PieceTracker(coordinates, imageView, WHITE, PAWN, true, squareColor);
        } else if (counter == 57 || counter == 64) {
            piece = new PieceTracker(coordinates, imageView, WHITE, ROOK, true, squareColor);
        } else if (counter == 58 || counter == 63) {
            piece = new PieceTracker(coordinates, imageView, WHITE, KNIGHT, true, squareColor);
        } else if (counter == 59 || counter == 62) {
            piece = new PieceTracker(coordinates, imageView, WHITE, BISHOP, true, squareColor);
        } else if (counter == 60) {
            piece = new PieceTracker(coordinates, imageView, WHITE, KING, true, squareColor);
            whiteKingPosition = coordinates;
        } else if (counter == 61) {
            piece = new PieceTracker(coordinates, imageView, WHITE, QUEEN, true, squareColor);
        } else piece = new PieceTracker(coordinates, imageView, EMPTY, EMPTY, false, squareColor);

        piecePositions.put(coordinates, piece);

        final PieceTracker finalPiece = piece;

        piece.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameState == GAME_STATE_SELECTING) {
                    if (finalPiece.getColor() == turn && finalPiece.isOccupied()) {
                        onPieceSelected(finalPiece);
                    }
                } else if (gameState == GAME_STATE_MOVING) {
                    if (finalPiece.isEligibleForMove()) {

                        //means it is an enemy piece
                        if (finalPiece.isOccupied()) {
                            takenPieces.add(finalPiece);
                        }
                        movePiece(selectedPiece.getPosition(), finalPiece.getPosition());
                    } else if (finalPiece.isOccupied() && finalPiece.getColor() == turn) {
                        onPieceSelected(finalPiece);
                    }
                }

            }
        });


    }

    private void onPieceSelected(PieceTracker piece) {

        // change colours back to the original (If user had clicked another piece before this)
        resetBackgroundColours();

        // make sure all squares have move disabled (else they carry on from the old clicked pieces)
        resetMoveOptions();

        gameState = GAME_STATE_MOVING;


        legalMoves = new ArrayList<>();

        editedSquares = new ArrayList<>();

        selectedPiece = piece;

        piecePositions.get(piece.position()).getImageView().setBackgroundColor(0xFFFFFF00);
        editedSquares.add(piecePositions.get(piece.position()));
        showPossibleMoves();
    }

    private void resetMoveOptions() {
        // old legal moves are no longer valid
        if (legalMoves != null) {
            for (Coordinates moves :
                    legalMoves) {
                piecePositions.get(moves).setEligibleForMove(false);
            }
        }

        // nor are the attack markers
        if (attackMarkers != null) {
            for (Coordinates moves :
                    attackMarkers) {
                piecePositions.get(moves).setEligibleForMove(false);
            }
        }
    }

    private void showPossibleMoves() {

        attackMarkers = new ArrayList<>();
        legalMoves = new ArrayList<>();
        legalMoves.addAll(getPossibleMoves(selectedPiece));

        for (Coordinates c :
                legalMoves) {

            if (isOccupied(c)) {
                // if it is occupied that means this is an attacking move
                PieceTracker attackedPiece = piecePositions.get(c);
                attackedPiece.setEligibleForMove(true);
                attackedPiece.getImageView().setBackgroundColor(0xFFFF0000);
                editedSquares.add(piecePositions.get(c));
            }else {
                editedSquares.add(piecePositions.get(c));
                piecePositions.get(c).getImageView().setImageResource(0);
                piecePositions.get(c).getImageView().setBackgroundColor(0xFF00FF00);
                piecePositions.get(c).setEligibleForMove(true);
            }
        }
    }

    private boolean isOccupied(Coordinates coordinates) {
        return piecePositions.get(coordinates).isOccupied();
    }



    private void movePiece(Coordinates startingLocation, Coordinates endLocation) {
        if (kingInCheck) {
            if (!checkIfMoveWillEndCheck(endLocation)) {
                Toast.makeText(this.getApplicationContext(), "Illegal Move", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        kingInCheck = false;

        PieceTracker oldPiece = piecePositions.get(startingLocation);
        checkIfKingMoved(oldPiece);
        resetDepartureSquare(oldPiece);
        resetBackgroundColours();

        PieceTracker newPiece = piecePositions.get(endLocation);
        updatePiece(newPiece, oldPiece);

        piecesCausingCheck = new ArrayList<>();

        if (checkForCheck(newPiece)) {
            Toast.makeText(this.getApplicationContext(), "Check", Toast.LENGTH_SHORT).show();
            piecesCausingCheck.add(newPiece);
            kingInCheck = true;
        }

        for (Coordinates move :
                legalMoves) {
            piecePositions.get(move).setEligibleForMove(false);
        }

        oldPiece.setOccupied(false);
        oldPiece.setEligibleForMove(false);
        newPiece.setEligibleForMove(false);
        gameState = GAME_STATE_SELECTING;
        changeTurn();

    }

    private boolean checkIfMoveWillEndCheck(Coordinates location) {
        piecePositions.get(location).setOccupied(true);
        for (PieceTracker piece :
                piecesCausingCheck) {
            Log.i(TAG, "checkIfMoveWillEndCheck: " + piece.getPosition().printCoords());
            if (checkForCheck(piece)) {
                piecePositions.get(location).setOccupied(false);
                return false;
            }
        }
        piecePositions.get(location).setOccupied(false);
        return true;
    }

    private void checkIfKingMoved(PieceTracker piece) {
        if (piece.getType() == KING) {
            if (piece.getColor() == BLACK) {
                blackKingPosition = piece.getPosition();
            } else whiteKingPosition = piece.getPosition();
        }
    }

    private void updatePiece(PieceTracker newPiece, PieceTracker oldPiece) {
        newPiece.setOccupied(true);
        newPiece.setType(oldPiece.getType());
        newPiece.setColour(oldPiece.getColour());
        newPiece.getImageView().setImageResource(getPieceImage(oldPiece));
        newPiece.setHasMoved(true);
    }

    private void resetDepartureSquare(PieceTracker piece) {
        piece.setOccupied(false);
        piece.getImageView().setImageResource(0);
    }

    private ArrayList<Coordinates> getPossibleMoves(PieceTracker piece) {
        MoveHelper moveHelper = new MoveHelper();
        ArrayList<Coordinates> temp = new ArrayList<>();

        switch (selectedPiece.getType()) {
            case PAWN:
                temp = moveHelper.getMovesAndAttacksForPawn(piece, piecePositions);
                break;
            case KNIGHT:
                temp = moveHelper.getMovesAndAttacksForKnight(piece, piecePositions);
                break;
            case BISHOP:
                temp = moveHelper.getMovesAndAttacksForBishop(piece, piecePositions);
                break;
            case ROOK:
                temp = moveHelper.getMovesAndAttacksForRook(piece, piecePositions);
                break;
            case QUEEN:
                temp = moveHelper.getMovesAndAttacksForQueen(piece, piecePositions);
                break;
            case KING:
                temp = moveHelper.getMovesAndAttacksForKing(piece, piecePositions);
                break;
        }

        return temp;
    }

    private boolean checkForCheck(PieceTracker piece) {

        attackMarkers = new ArrayList<>();

        getPossibleMoves(piece);

        for (Coordinates attackPoint :
                attackMarkers) {
            if (piecePositions.get(attackPoint).isOccupied() && (piecePositions.get(attackPoint).getColor() != piece.getColor())) {
                if (piecePositions.get(attackPoint).getType() == KING) {
                    return true;
                }
            }
        }
        return false;
    }

    private void changeTurn() {
        if (turn == WHITE) {
            turn = BLACK;
            turnTracker.setText(R.string.black_to_move);
        } else {
            turn = WHITE;
            turnTracker.setText(R.string.white_to_move);
        }
    }

    private int getPieceImage(PieceTracker pieceTracker) {
        switch (pieceTracker.getType()) {
            case PAWN:
                if (pieceTracker.getColor() == BLACK) {
                    return R.drawable.pawn_b;
                } else return R.drawable.pawn_w;
            case KNIGHT:
                if (pieceTracker.getColor() == BLACK) {
                    return R.drawable.knight_b;
                } else return R.drawable.knight_w;
            case BISHOP:
                if (pieceTracker.getColor() == BLACK) {
                    return R.drawable.bishop_b;
                } else return R.drawable.bishop_w;
            case ROOK:
                if (pieceTracker.getColor() == BLACK) {
                    return R.drawable.rook_b;
                } else return R.drawable.rook_w;
            case QUEEN:
                if (pieceTracker.getColor() == BLACK) {
                    return R.drawable.queen_b;
                } else return R.drawable.queen_w;
            case KING:
                if (pieceTracker.getColor() == BLACK) {
                    return R.drawable.king_b;
                } else return R.drawable.king_w;
        }
        return 0;
    }


    private void resetBackgroundColours() {
        if (editedSquares != null) {
            for (PieceTracker pieceTracker :
                    editedSquares) {
                if (pieceTracker.getSquareColor() == 0) {
                    pieceTracker.getImageView().setBackgroundColor(Color.WHITE);
                } else pieceTracker.getImageView().setBackgroundColor(Color.LTGRAY);

            }
        }
    }
}
