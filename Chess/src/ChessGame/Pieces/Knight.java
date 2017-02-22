package ChessGame.Pieces;

import ChessGame.Board.BoardLocation;
import ChessGame.Moves.LShapeMoves;
import ChessGame.Net.Player;

public class Knight extends ChessPiece implements LShapeMoves {
	protected Knight(Player p, BoardLocation loc, boolean onBoard) {
		super(p, loc, onBoard);
		this.setType("N");
		this.setMaxLength(5);
		this.setSuit(Suit.KNIGHT);
	}

	@Override
	public boolean isValidLShapeMove(BoardLocation loc) {
		int len = (int)((Math.pow(loc.getX() - this.getX(), 2)) + (Math.pow(loc.getY() - this.getY(), 2)));
		if ((len == this.getMaxMoves()) && (this.getPlayer() != loc.getPlayer()) && Utility.coordTest(loc)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isValidMove(BoardLocation loc) {
		if (this.isOnChessBoard() && this.isValidLShapeMove(loc)
			&& (!this.getPlayer().getKing().isCheckMate() || !this.getPlayer().getKing().isCheckMatePostMove(this, loc))) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean theoryMove(BoardLocation loc) {
		if (this.isOnChessBoard() && this.isValidLShapeMove(loc)) {
			return true;
		} else {
			return false;
		}
	}
}
