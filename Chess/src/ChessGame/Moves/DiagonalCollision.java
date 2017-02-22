package ChessGame.Moves;

import ChessGame.Board.BoardLocation;

public interface DiagonalCollision extends DiagonalMoves {
	public boolean isDiagonalCollision(BoardLocation l);
}
