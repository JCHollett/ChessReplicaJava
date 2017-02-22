package ChessGame.Moves;

import ChessGame.Board.BoardLocation;

public interface SpecialMoves extends MoveValidation {
	public boolean isSpecialMove(BoardLocation l);
}
