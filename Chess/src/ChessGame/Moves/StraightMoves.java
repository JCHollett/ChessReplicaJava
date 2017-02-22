package ChessGame.Moves;

import ChessGame.Board.BoardLocation;

public interface StraightMoves extends MoveValidation {
	public boolean isValidStraightMove(BoardLocation l);
}
