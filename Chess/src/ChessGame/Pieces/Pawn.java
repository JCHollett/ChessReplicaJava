package ChessGame.Pieces;

import ChessGame.Board.BoardLocation;
import ChessGame.Moves.DiagonalMoves;
import ChessGame.Moves.SpecialMoves;
import ChessGame.Moves.StraightCollision;
import ChessGame.Net.Player;

public class Pawn extends ChessPiece implements StraightCollision, DiagonalMoves, SpecialMoves {
	protected static Pawn enpassant;
	protected static boolean isPassant;
	private boolean dir;
	
	protected Pawn(Player p, BoardLocation loc, boolean onBoard) {
		super(p, loc, onBoard);
		this.setType("P");
		this.setMaxLength(2);
		if (this.getY() == 1) {
			this.dir = false;
		} else {
			this.dir = true;
		}
		this.setSuit(Suit.PAWN);
	}
	
	private int getDirection() {
		if (this.dir) {
			return -1;
		} else {
			return 1;
		}
	}
	
	@Override
	public boolean isSpecialMove(BoardLocation loc) {
		int deltaX = loc.getX() - this.getX();
		int deltaY = loc.getY() - this.getY();
		double len = Math.pow(deltaX, 2) + Math.pow(deltaY, 2);
		if ((Pawn.enpassant != null) && (len == 2.0)) {
			if (this.getBoard().getPieceAt(loc.getX(), loc.getY() - this.getDirection()) == Pawn.enpassant) {
				Pawn.isPassant = true;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isStraightCollision(BoardLocation loc) {
		int deltaY = (loc.getY() - this.getY());
		int deltaX = (loc.getX() - this.getX());
		if ((this.getMaxMoves() > 1) && (Math.abs(deltaY) > 1) && (deltaX == 0)) {
			BoardLocation l = this.getBoard().getLocation(this.getX(), this.getY() + (deltaY / 2));
			if (l.getPiece() != null) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isValidDiagonalMove(BoardLocation loc) {
		int deltaX = loc.getX() - this.getX();
		int deltaY = loc.getY() - this.getY();
		double len = Math.pow(deltaX, 2) + Math.pow(deltaY, 2);
		if ((len == 2.0) && (deltaY == this.getDirection()) && (((this.getPlayer() != loc.getPlayer()) && (loc.getPlayer() != null))) && Utility.coordTest(loc)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isValidMove(BoardLocation loc) {
		if ((this.isOnChessBoard() && (this.isValidDiagonalMove(loc) || this.isValidStraightMove(loc) || this.isSpecialMove(loc)) && (!this.getPlayer().getKing()
			.isCheckMate()))
			|| !this.getPlayer().getKing().isCheckMatePostMove(this, loc)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isValidStraightMove(BoardLocation loc) {
		int deltaX = loc.getX() - this.getX();
		int deltaY = (loc.getY() - this.getY());
		if (((deltaX == 0) && ((deltaY * this.getDirection()) <= this.getMaxMoves()) && (Math.abs(deltaY) > 0)) && !(loc.getPlayer() instanceof Player)
			&& Utility.coordTest(loc)) {
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
	public boolean performMove(BoardLocation loc) {
		if (this.getMaxMoves() > 1) {
			boolean b = super.performMove(loc);
			this.setMaxLength(1);
			Pawn.enpassant = b ? this : Pawn.enpassant;
			return b;
		} else {
			boolean b = super.performMove(loc);
			if (isPassant) {
				Pawn.enpassant.getLocation().setPiece(null);
				Pawn.enpassant.setLocation(null);
				Pawn.enpassant.setOnChessBoard(false);
				isPassant = false;
			}
			Pawn.enpassant = b ? null : Pawn.enpassant;
			return b;
		}
	}
	
	@Override
	public boolean theoryMove(BoardLocation loc) {
		if (this.isOnChessBoard() && (this.isValidDiagonalMove(loc) || this.isValidStraightMove(loc) || this.isSpecialMove(loc))) {
			return true;
		} else {
			return false;
		}
	}
}
