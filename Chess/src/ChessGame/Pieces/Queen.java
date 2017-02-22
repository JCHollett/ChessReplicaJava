package ChessGame.Pieces;

import ChessGame.Board.BoardLocation;
import ChessGame.Moves.DiagonalCollision;
import ChessGame.Moves.StraightCollision;
import ChessGame.Net.Player;

public class Queen extends ChessPiece implements StraightCollision, DiagonalCollision {
	protected Queen(Player p, BoardLocation l, boolean onBoard) {
		super(p, l, onBoard);
		this.setType("Q");
		this.setMaxLength(7);
		this.setSuit(Suit.QUEEN);
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
	public boolean isStraightCollision(BoardLocation loc) {
		int deltaY = (loc.getY() - this.getY());
		int deltaX = (loc.getX() - this.getX());
		if ((deltaX == 0) && (deltaY != 0)) {
			BoardLocation l = this.getBoard().getLocation(this.getX(), this.getY() + deltaY);
			while (deltaY != 0) {
				if (deltaY < 0) {
					deltaY++;
					l = this.getBoard().getLocation(this.getX(), this.getY() + deltaY);
					if ((l.getPiece() != null) && (deltaY != 0)) {
						return true;
					}
				} else {
					deltaY--;
					l = this.getBoard().getLocation(this.getX(), this.getY() + deltaY);
					if ((l.getPiece() != null) && (deltaY != 0)) {
						return true;
					}
				}
			}
			return false;
		} else if ((deltaX != 0) && (deltaY == 0)) {
			BoardLocation l = this.getBoard().getLocation(this.getX() + deltaX, this.getY());
			while (deltaX != 0) {
				if (deltaX < 0) {
					deltaX++;
					l = this.getBoard().getLocation(this.getX() + deltaX, this.getY());
					if ((l.getPiece() != null) && (deltaX != 0)) {
						return true;
					}
				} else {
					deltaX--;
					l = this.getBoard().getLocation(this.getX() + deltaX, this.getY());
					if ((l.getPiece() != null) && (deltaX != 0)) {
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
		if (((Math.abs(slope) == 1.0) & (this.getPlayer() != loc.getPlayer())) && Utility.coordTest(loc)) {
			if (!this.isDiagonalCollision(loc)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isValidMove(BoardLocation loc) {
		if (this.isOnChessBoard() && (this.isValidStraightMove(loc) || this.isValidDiagonalMove(loc))
			&& (!this.getPlayer().getKing().isCheckMate() || !this.getPlayer().getKing().isCheckMatePostMove(this, loc))) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isValidStraightMove(BoardLocation loc) {
		int deltaX = loc.getX() - this.getX();
		int deltaY = loc.getY() - this.getY();
		if ((((deltaX == 0) && (Math.abs(deltaY) > 0)) || ((deltaY == 0) && (Math.abs(deltaX) > 0))) && (this.getPlayer() != loc.getPlayer()) && Utility.coordTest(loc)) {
			if (!this.isStraightCollision(loc)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public boolean theoryMove(BoardLocation loc) {
		if (this.isOnChessBoard() && (this.isValidStraightMove(loc) || this.isValidDiagonalMove(loc))) {
			return true;
		} else {
			return false;
		}
	}
}
