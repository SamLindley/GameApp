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
import com.example.sam.game.R;
import com.example.sam.game.chess.helper.MoveHelper;
import com.example.sam.game.chess.model.Coordinates;
import com.example.sam.game.chess.model.GameSquare;
import com.example.sam.game.chess.model.PieceTracker;

import java.util.ArrayList;
import java.util.HashMap;

public class ChessActivity extends AppCompatActivity {

    private final int PAWN = 0;
    private final int KNIGHT = 1;
    private final int BISHOP = 2;
    private final int ROOK = 3;
    private final int QUEEN = 4;
    private final int KING = 5;
    private final int EMPTY = -1;


    private final static int WHITE = 0;
    private final static int BLACK = 1;
    private int turn = WHITE;
    private GridLayout board;
    private GridLayout piecesLayout;
    private HashMap<Coordinates, PieceTracker> piecePositions;
    private PieceTracker selectedPiece;
    private ArrayList<PieceTracker> editedSquares;
    ArrayList<Coordinates> attacks;
    ArrayList<Coordinates> legalMoves;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chess);
        board = findViewById(R.id.chessBoard);
        piecesLayout = findViewById(R.id.pieces);
        piecePositions = new HashMap<>();
        int counter = 0;
        int squareColour = 0;
        for (int y = 0; y < 8; y++) {
            if (squareColour == 0) {
                squareColour = 1;
            } else squareColour = 0;
            for (int x = 0; x < 8; x++) {
                ImageView square = (ImageView) board.getChildAt(counter);
                ImageView pieceView = (ImageView) piecesLayout.getChildAt(counter);
                Coordinates coordinates = new Coordinates(x, y);


                GameSquare gameSquare = new GameSquare();
                if (counter < 16) {
                    gameSquare.setColourOfPiece(BLACK);
                }
                if (counter > 56) {
                    gameSquare.setColourOfPiece(WHITE);
                }
                gameSquare.setColourOfSquare(squareColour);
                gameSquare.setImageView(square);
                gameSquare.setCoordinates(coordinates);
                gameSquare.setPosition(counter);

                setUpPieces(counter, pieceView, coordinates, squareColour);
                counter++;
                if (squareColour == 0) {
                    squareColour = 1;
                } else squareColour = 0;
            }
        }
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    private void onPieceSelected(PieceTracker piece) {
        resetSquareImage();
        resetClickListeners();

        editedSquares = new ArrayList<>();

        selectedPiece = piece;

        Log.i("TEST", piece.position().printCoords());

        piecePositions.get(piece.position()).getImageView().setBackgroundColor(0xFFFFFF00);
        editedSquares.add(piecePositions.get(piece.position()));
        showPossibleMoves();
    }

    private void resetClickListeners() {
        if (legalMoves != null) {
            for (final Coordinates coordinates :
                    legalMoves) {
                if (piecePositions.get(coordinates).getImageView() != null) {
                    piecePositions.get(coordinates).getImageView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (piecePositions.get(coordinates).isOccupied()) {
                                if (piecePositions.get(coordinates).getColor() == turn){
                                    onPieceSelected(piecePositions.get(coordinates));
                                }
                            }
                        }
                    });


                }

            }
        }

    }

    private void showPossibleMoves() {

        attacks = new ArrayList<>();
        legalMoves = new ArrayList<>();
        ArrayList<Coordinates> temp = null;

        switch (selectedPiece.getType()) {
            case PAWN:
                temp = getMovesForPawn(selectedPiece);
                break;
            case KNIGHT:
                temp = getMovesForKnight(selectedPiece);
                break;
            case BISHOP:
                temp = getMovesForBishop(selectedPiece);
                break;
            case ROOK:
                temp = getMovesForRook(selectedPiece);
                break;
            case QUEEN:
                temp = getMovesForQueen(selectedPiece);
                break;
            case KING:
                temp = getMovesForKing(selectedPiece);
                break;
        }

        for (Coordinates c :
                temp) {
            if (withinBounds(c)) {
                legalMoves.add(c);
            }
        }

        if (attacks != null) {
            for (final Coordinates coordinates :
                    attacks) {
                PieceTracker attackedPiece = piecePositions.get(coordinates);
                attackedPiece.getImageView().setBackgroundColor(0xFFFF0000);
                attackedPiece.getImageView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        movePiece(selectedPiece.getType(), selectedPiece.position(), piecePositions.get(coordinates).getPosition(), true);
                    }
                });
                editedSquares.add(piecePositions.get(coordinates));
            }
        }
        for (final Coordinates c :
                legalMoves) {
            if (withinBounds(c) && !isOccupied(c)) {
                editedSquares.add(piecePositions.get(c));
                piecePositions.get(c).getImageView().setImageResource(0);
                piecePositions.get(c).getImageView().setBackgroundColor(0xFF00FF00);
                piecePositions.get(c).getImageView().bringToFront();
                piecePositions.get(c).getImageView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        movePiece(selectedPiece.getType(), selectedPiece.position(), piecePositions.get(c).getPosition(), false);
                    }
                });
            }
        }
    }

    private ArrayList<Coordinates> getMovesForKing(PieceTracker selectedPiece) {
        MoveHelper moveHelper = new MoveHelper();
        ArrayList<Coordinates> list = new ArrayList<>();
        Coordinates position = selectedPiece.getPosition();

        for (Coordinates c :
                moveHelper.getKingMoves(position)) {
            if (withinBounds(c) && !piecePositions.get(c).isOccupied()) {
                list.add(c);
            }
            if (withinBounds(c) && piecePositions.get(c).isOccupied() && checkFriendly(selectedPiece, c)) {
                attacks.add(c);
            }
        }


        return list;
    }

    private ArrayList<Coordinates> getMovesForKnight(PieceTracker selectedPiece) {
        MoveHelper moveHelper = new MoveHelper();
        ArrayList<Coordinates> list = new ArrayList<>();
        Coordinates position = selectedPiece.getPosition();

        for (Coordinates c :
                moveHelper.getKnightMoves(position)) {
            if (withinBounds(c) && !piecePositions.get(c).isOccupied()) {
                list.add(c);
            }
            if (withinBounds(c) && piecePositions.get(c).isOccupied() && checkFriendly(selectedPiece, c)) {
                attacks.add(c);
            }
        }


        return list;
    }

    private ArrayList<Coordinates> getMovesForPawn(PieceTracker selectedPiece) {
        MoveHelper moveHelper = new MoveHelper();
        ArrayList<Coordinates> list = new ArrayList<>();
        Coordinates position = selectedPiece.getPosition();

        for (Coordinates c :
                moveHelper.getPawnMoves(position, selectedPiece.hasMoved(), selectedPiece.getColor())) {
            if (withinBounds(c) && !piecePositions.get(c).isOccupied()) {
                list.add(c);

            } else break;
        }

        for (Coordinates c :
                moveHelper.getPawnAttacks(position, selectedPiece.getColor())) {
            if (withinBounds(c) && piecePositions.get(c).isOccupied() && checkFriendly(selectedPiece, c)) {
                attacks.add(c);
            }
        }

        Log.i("ATTASKS", "" + attacks.size());
        return list;

    }

    private ArrayList<Coordinates> getMovesForQueen(PieceTracker selectedPiece) {
        MoveHelper moveHelper = new MoveHelper();
        ArrayList<Coordinates> list = new ArrayList<>();
        Coordinates position = selectedPiece.getPosition();

        for (Coordinates c :
                moveHelper.getMovesEast(position)) {
            if (withinBounds(c) && piecePositions.get(c).isOccupied()) {
                if (checkFriendly(selectedPiece, c)) {
                    attacks.add(c);
                }
                break;
            } else if (withinBounds(c)) {
                list.add(c);
            }

        }
        for (Coordinates c :
                moveHelper.getMovesWest(position)) {
            if (withinBounds(c) && piecePositions.get(c).isOccupied()) {
                if (checkFriendly(selectedPiece, c)) {
                    attacks.add(c);
                }
                break;
            } else if (withinBounds(c)) {
                list.add(c);
            }

        }
        for (Coordinates c :
                moveHelper.getMovesSouth(position)) {
            if (withinBounds(c) && piecePositions.get(c).isOccupied()) {
                if (checkFriendly(selectedPiece, c)) {
                    attacks.add(c);
                }
                break;
            } else if (withinBounds(c)) {
                list.add(c);
            }

        }
        for (Coordinates c :
                moveHelper.getMovesNorth(position)) {
            if (withinBounds(c) && piecePositions.get(c).isOccupied()) {
                if (checkFriendly(selectedPiece, c)) {
                    attacks.add(c);
                }
                break;
            } else if (withinBounds(c)) {
                list.add(c);
            }

        }
        for (Coordinates c :
                moveHelper.getMovesNorthEast(position)) {
            if (withinBounds(c) && piecePositions.get(c).isOccupied()) {
                if (checkFriendly(selectedPiece, c)) {
                    attacks.add(c);
                }
                break;
            } else if (withinBounds(c)) {
                list.add(c);
            }

        }
        for (Coordinates c :
                moveHelper.getMovesNorthWest(position)) {
            if (withinBounds(c) && piecePositions.get(c).isOccupied()) {
                if (checkFriendly(selectedPiece, c)) {
                    attacks.add(c);
                }
                break;
            } else if (withinBounds(c)) {
                list.add(c);
            }

        }
        for (Coordinates c :
                moveHelper.getMovesSouthEast(position)) {
            if (withinBounds(c) && piecePositions.get(c).isOccupied()) {
                if (checkFriendly(selectedPiece, c)) {
                    attacks.add(c);
                }
                break;
            } else if (withinBounds(c)) {
                list.add(c);
            }

        }
        for (Coordinates c :
                moveHelper.getMovesSouthWest(position)) {
            if (withinBounds(c) && piecePositions.get(c).isOccupied()) {
                if (checkFriendly(selectedPiece, c)) {
                    attacks.add(c);
                }
                break;
            } else if (withinBounds(c)) {
                list.add(c);
            }

        }


        return list;
    }

    private ArrayList<Coordinates> getMovesForBishop(PieceTracker selectedPiece) {
        MoveHelper moveHelper = new MoveHelper();
        ArrayList<Coordinates> list = new ArrayList<>();
        Coordinates position = selectedPiece.getPosition();

        for (Coordinates c :
                moveHelper.getMovesNorthEast(position)) {
            if (withinBounds(c) && piecePositions.get(c).isOccupied()) {
                if (checkFriendly(selectedPiece, c)) {
                    attacks.add(c);
                }
                break;
            } else if (withinBounds(c)) {
                list.add(c);
            }

        }
        for (Coordinates c :
                moveHelper.getMovesNorthWest(position)) {
            if (withinBounds(c) && piecePositions.get(c).isOccupied()) {
                if (checkFriendly(selectedPiece, c)) {
                    attacks.add(c);
                }
                break;
            } else if (withinBounds(c)) {
                list.add(c);
            }

        }
        for (Coordinates c :
                moveHelper.getMovesSouthEast(position)) {
            if (withinBounds(c) && piecePositions.get(c).isOccupied()) {
                if (checkFriendly(selectedPiece, c)) {
                    attacks.add(c);
                }
                break;
            } else if (withinBounds(c)) {
                list.add(c);
            }

        }
        for (Coordinates c :
                moveHelper.getMovesSouthWest(position)) {
            if (withinBounds(c) && piecePositions.get(c).isOccupied()) {
                if (checkFriendly(selectedPiece, c)) {
                    attacks.add(c);
                }
                break;
            } else if (withinBounds(c)) {
                list.add(c);
            }

        }


        return list;
    }

    private ArrayList<Coordinates> getMovesForRook(PieceTracker selectedPiece) {
        MoveHelper moveHelper = new MoveHelper();
        ArrayList<Coordinates> list = new ArrayList<>();
        Coordinates position = selectedPiece.getPosition();

        for (Coordinates c :
                moveHelper.getMovesEast(position)) {
            if (withinBounds(c) && piecePositions.get(c).isOccupied()) {
                if (checkFriendly(selectedPiece, c)) {
                    attacks.add(c);
                }
                break;
            } else if (withinBounds(c)) {
                list.add(c);
            }

        }
        for (Coordinates c :
                moveHelper.getMovesWest(position)) {
            if (withinBounds(c) && piecePositions.get(c).isOccupied()) {
                if (checkFriendly(selectedPiece, c)) {
                    attacks.add(c);
                }
                break;
            } else if (withinBounds(c)) {
                list.add(c);
            }

        }
        for (Coordinates c :
                moveHelper.getMovesSouth(position)) {
            if (withinBounds(c) && piecePositions.get(c).isOccupied()) {
                if (checkFriendly(selectedPiece, c)) {
                    attacks.add(c);
                }
                break;
            } else if (withinBounds(c)) {
                list.add(c);
            }

        }
        for (Coordinates c :
                moveHelper.getMovesNorth(position)) {
            if (withinBounds(c) && piecePositions.get(c).isOccupied()) {
                if (checkFriendly(selectedPiece, c)) {
                    attacks.add(c);
                }
                break;
            } else if (withinBounds(c)) {
                list.add(c);
            }

        }
        return list;
    }

    private boolean checkFriendly(PieceTracker piece, Coordinates coordinates) {
        Log.i("CHECK FRIENDLY", "RESULTS OF INSPECTION ===== " + piece.getColour() + "  =========  " + piecePositions.get(coordinates).getColor());
        return piece.getColour() != piecePositions.get(coordinates).getColor();
    }

    private boolean withinBounds(Coordinates coordinates) {
        return coordinates.getY() > -1 && coordinates.getY() < 8 && coordinates.getX() > -1 && coordinates.getX() < 8;
    }

    private boolean isOccupied(Coordinates coordinates) {
        return piecePositions.get(coordinates).isOccupied();
    }

    private void setUpPieces(int counter, ImageView imageView, Coordinates coordinates, int squareColor) {
        PieceTracker piece = null;

        if (counter == 0 || counter == 7) {
            piece = new PieceTracker(coordinates, imageView, BLACK, ROOK, true, squareColor);
        } else if (counter == 1 || counter == 6) {
            piece = new PieceTracker(coordinates, imageView, BLACK, KNIGHT, true, squareColor);
        } else if (counter == 2 || counter == 5) {
            piece = new PieceTracker(coordinates, imageView, BLACK, BISHOP, true, squareColor);
        } else if (counter == 3) {
            piece = new PieceTracker(coordinates, imageView, BLACK, KING, true, squareColor);
        } else if (counter == 4) {
            piece = new PieceTracker(coordinates, imageView, BLACK, QUEEN, true, squareColor);
        } else if (counter > 7 && counter < 16) {
            piece = new PieceTracker(coordinates, imageView, BLACK, PAWN, true, squareColor);
        } else if (counter > 47 && counter < 56) {
            piece = new PieceTracker(coordinates, imageView, WHITE, PAWN, true, squareColor);
        } else if (counter == 56 || counter == 63) {
            piece = new PieceTracker(coordinates, imageView, WHITE, ROOK, true, squareColor);
        } else if (counter == 57 || counter == 62) {
            piece = new PieceTracker(coordinates, imageView, WHITE, KNIGHT, true, squareColor);
        } else if (counter == 58 || counter == 61) {
            piece = new PieceTracker(coordinates, imageView, WHITE, BISHOP, true, squareColor);
        } else if (counter == 59) {
            piece = new PieceTracker(coordinates, imageView, WHITE, KING, true, squareColor);
        } else if (counter == 60) {
            piece = new PieceTracker(coordinates, imageView, WHITE, QUEEN, true, squareColor);
        }
        if (piece != null) {
            piecePositions.put(coordinates, piece);
            final PieceTracker finalPiece = piece;

            piece.getImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalPiece.getColor() == turn){
                        onPieceSelected(finalPiece);
                    }
                }
            });
        } else
            piecePositions.put(coordinates, new PieceTracker(coordinates, imageView, EMPTY, EMPTY, false, squareColor));


    }

    private void movePiece(int type, Coordinates startingLocation, Coordinates endLocation, boolean isAttack) {
        Log.i("TAG", "MOVING PIECE");
        for (Coordinates coordinates :
                legalMoves) {
            piecePositions.get(coordinates).getImageView().setOnClickListener(null);
        }
        for (final Coordinates coordinates :
                attacks) {
            piecePositions.get(coordinates).getImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (piecePositions.get(coordinates).getColor() == turn){
                        onPieceSelected(piecePositions.get(coordinates));
                    }
                }
            });
        }

        PieceTracker oldPiece = piecePositions.get(startingLocation);
        oldPiece.getImageView().setOnClickListener(null);
        oldPiece.setOccupied(false);
        int image = getPieceImage(oldPiece);
        piecePositions.get(startingLocation).getImageView().setImageResource(0);
        resetSquareImage();

        final PieceTracker newPiece = piecePositions.get(endLocation);


        newPiece.setOccupied(true);
        newPiece.setPosition(endLocation);
        newPiece.setType(oldPiece.getType());
        newPiece.setColour(oldPiece.getColour());
        newPiece.getImageView().setImageResource(image);
        newPiece.setHasMoved(true);

        changeTurn();

    }

    private void changeTurn() {
        if (turn == WHITE) {
            turn = BLACK;
        } else turn = WHITE;
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


    private void resetSquareImage() {
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
