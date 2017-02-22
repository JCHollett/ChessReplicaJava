package ChessGame.Moves;

import ChessGame.Board.BoardLocation;

public interface StraightCollision extends StraightMoves {
	public boolean isStraightCollision(BoardLocation l);
}
