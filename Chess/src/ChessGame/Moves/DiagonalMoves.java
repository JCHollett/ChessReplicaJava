package ChessGame.Moves;

import ChessGame.Board.BoardLocation;

public interface DiagonalMoves extends MoveValidation {
	public boolean isValidDiagonalMove(BoardLocation l);
}
