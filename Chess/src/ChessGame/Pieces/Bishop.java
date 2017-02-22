package ChessGame.Pieces;

import ChessGame.Board.BoardLocation;
import ChessGame.Moves.DiagonalCollision;
import ChessGame.Net.Player;

public class Bishop extends ChessPiece implements DiagonalCollision {
	protected Bishop(Player p, BoardLocation l, boolean onBoard) {
		super(p, l, onBoard);
		this.setType("B");
		this.setMaxLength(7);
		this.setSuit(Suit.BISHOP);
	}
	
	@Override
	public boolean isDiagonalCollision(BoardLocation loc) {
		int deltaX = loc.getX() - this.getX();
		int deltaY = loc.getY() - this.getY();
		double slope = deltaX != 0 ? (deltaY / deltaX) : 0;
		if ((deltaX != 0) && (deltaY != 0) && (Math.abs(slope) == 1)) {
			BoardLocation l = this.getBoard().getLocation(this.getX() + deltaX, this.getY() + deltaY);
			while ((deltaX != 0) && (deltaY != 0)) {
				if (deltaX < 0) {
					deltaX++;
				} else {
					deltaX--;
				}
				if (deltaY < 0) {
					deltaY++;
				} else {
					deltaY--;
				}
				l = this.getBoard().getLocation(this.getX() + deltaX, this.getY() + deltaY);
				if ((deltaX != 0) && (deltaY != 0)) {
					if (l.getPiece() != null) {
						return true;
					}
				}
			}
			return false;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isValidDiagonalMove(BoardLocation loc) {
		int deltaX = loc.getX() - this.getX();
		int deltaY = loc.getY() - this.getY();
		double slope = deltaX != 0 ? (deltaY / deltaX) : 0;
		if ((Math.abs(slope) == 1.0) && (this.getPlayer() != loc.getPlayer()) && Utility.coordTest(loc)) {
			if (!this.isDiagonalCollision(loc)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isValidMove(BoardLocation loc) {
		if (this.isOnChessBoard() && this.isValidDiagonalMove(loc)
			&& (!this.getPlayer().getKing().isCheckMate() || !this.getPlayer().getKing().isCheckMatePostMove(this, loc))) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean theoryMove(BoardLocation loc) {
		if (this.isOnChessBoard() && this.isValidDiagonalMove(loc)) {
			return true;
		} else {
			return false;
		}
	}
}
