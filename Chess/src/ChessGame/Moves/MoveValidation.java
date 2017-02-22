package ChessGame.Moves;

import ChessGame.Board.BoardLocation;

public interface MoveValidation {
	public boolean isValidMove(BoardLocation l);
	
	public boolean performMove(BoardLocation l);
	
	public boolean theoryMove(BoardLocation l);
}
