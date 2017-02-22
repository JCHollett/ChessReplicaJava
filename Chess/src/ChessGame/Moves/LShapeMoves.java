package ChessGame.Moves;

import ChessGame.Board.BoardLocation;

public interface LShapeMoves extends MoveValidation {
	public boolean isValidLShapeMove(BoardLocation l);
}
