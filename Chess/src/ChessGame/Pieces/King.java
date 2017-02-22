package ChessGame.Pieces;

import java.util.ArrayList;
import java.util.List;

import ChessGame.Board.BoardLocation;
import ChessGame.Moves.DiagonalMoves;
import ChessGame.Moves.SpecialMoves;
import ChessGame.Moves.StraightCollision;
import ChessGame.Net.Player;
import ChessGame.Types.Node;

public class King extends ChessPiece implements StraightCollision, DiagonalMoves, SpecialMoves {
	protected ArrayList<BoardLocation> kingMoves = new ArrayList<BoardLocation>();
	private ChessPiece leftSide = null;
	private ChessPiece rightSide = null;
	
	protected King(Player p, BoardLocation loc, boolean onBoard) {
		super(p, loc, onBoard);
		this.setType("K");
		this.setMaxLength(2);
		this.setSuit(Suit.KING);
	}
	
	private ArrayList<BoardLocation> generateKingMoves() {
		if (this.kingMoves.size() > 0) {
			this.kingMoves = new ArrayList<BoardLocation>();
		}
		for (int x = this.getX() - 1; x <= (this.getX() + 1); x++) {
			for (int y = this.getY() - 1; y <= (this.getY() + 1); y++) {
				BoardLocation l = this.getBoard().getLocation(x, y);
				if (l != null) {
					this.kingMoves.add(this.getBoard().getLocation(x, y));
				}
			}
		}
		kingMoves.remove(this.getLocation());
		return this.kingMoves;
	}
	
	public ArrayList<BoardLocation> getKingMoves() {
		this.isCheckMate();
		return this.kingMoves;
	}
	
	@SuppressWarnings("unchecked")
	public boolean isCheckMate() {
		this.getPlayer().clearMoves();
		ArrayList<BoardLocation> kingMoves = this.generateKingMoves();
		for (BoardLocation l : (ArrayList<BoardLocation>)kingMoves.clone()) {
			if (this.isCheckMatePostMove(this, l)) {
				kingMoves.remove(l);
			} else {
				Node<BoardLocation, BoardLocation> loc = new Node<BoardLocation, BoardLocation>(this.getLocation(), l);
				if (!this.getPlayer().getMoves().contains(loc)) {
					this.getPlayer().addMove(loc);
				}
			}
		}
		List<Node<BoardLocation, BoardLocation>> attacking = new ArrayList<Node<BoardLocation, BoardLocation>>();
		for (ChessPiece p : this.getPlayer().getOpponent().getPieces()) {
			if (p.theoryMove(this.getLocation())) {
				for (Node<BoardLocation, BoardLocation> l : Utility.getSpaces(p.getLocation(), this.getLocation())) {
					if (!attacking.contains(l)) {
						attacking.add(l);
					}
				}
			}
		}
		for (Node<BoardLocation, BoardLocation> l : attacking) {
			for (ChessPiece p : this.getPlayer().getPieces()) {
				if (p.isValidMove(l.getKey()) && !this.isCheckMatePostMove(p, l.getKey())) {
					Node<BoardLocation, BoardLocation> loc = new Node<BoardLocation, BoardLocation>(p.getLocation(), l.getKey());
					if (!this.getPlayer().getMoves().contains(loc)) {
						this.getPlayer().addMove(loc);
					}
				}
			}
		}
		if (kingMoves.size() > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	protected boolean isCheckMate(BoardLocation l) {
		for (ChessPiece c : this.getPlayer().getOpponent().getPieces()) {
			if (c.theoryMove(l)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isCheckMatePostMove(ChessPiece p, BoardLocation loc) {
		if (loc.getPiece() != null) {
			boolean theory = p.theoryMove(loc);
			if (theory) {
				return Utility.fakeMove(p.getLocation(), loc);
			}
		} else {
			boolean theory = p.theoryMove(loc);
			if (theory) {
				return Utility.fakeMove(p.getLocation(), loc);
			}
		}
		return false;
	}
	
	@Override
	public boolean isSpecialMove(BoardLocation loc) {
		int deltaX = loc.getX() - this.getX();
		int deltaY = loc.getY() - this.getY();
		if ((deltaY == 0) && (Math.abs(deltaX) == 2) && (this.getMaxMoves() > 1)) {
			this.rightSide = this.getBoard().getPieceAt(7, this.getY());
			this.leftSide = this.getBoard().getPieceAt(0, this.getY());
			if ((deltaX > 0) && (this.rightSide instanceof Rook) && (this.rightSide.getPlayer() == this.getPlayer())) {
				if (!this.isStraightCollision(this.rightSide.getLocation())) {
					if (((Rook)this.rightSide).castling) {
						this.leftSide = null;
						return true;
					} else {
						return false;
					}
				}
			}
			if ((deltaX < 0) && (this.leftSide instanceof Rook) && (this.leftSide.getPlayer() == this.getPlayer())) {
				if (!this.isStraightCollision(this.leftSide.getLocation())) {
					if (((Rook)this.leftSide).castling) {
						this.rightSide = null;
						return true;
					} else {
						return false;
					}
				}
			}
		} else {
			return false;
		}
		return false;
	}
	
	@Override
	public boolean isStraightCollision(BoardLocation loc) {
		int deltaY = (loc.getY() - this.getY());
		int deltaX = (loc.getX() - this.getX());
		if ((deltaX == 0) && (deltaY != 0)) {
			while (deltaY != 0) {
				if (deltaY < 0) {
					deltaY++;
					if ((this.getBoard().getLocation(this.getX(), this.getY() + deltaY).getPiece() != null) && (deltaY != 0)) {
						return true;
					}
				} else {
					deltaY--;
					if ((this.getBoard().getLocation(this.getX(), this.getY() + deltaY).getPiece() != null) && (deltaY != 0)) {
						return true;
					}
				}
			}
			return false;
		} else if ((deltaX != 0) && (deltaY == 0)) {
			while (deltaX != 0) {
				if (deltaX < 0) {
					deltaX++;
					if ((this.getBoard().getLocation(this.getX() + deltaX, this.getY()).getPiece() != null) && (deltaX != 0)) {
						return true;
					}
				} else {
					deltaX--;
					if ((this.getBoard().getLocation(this.getX() + deltaX, this.getY()).getPiece() != null) && (deltaX != 0)) {
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
		double len = Math.pow(deltaX, 2) + Math.pow(deltaY, 2);
		if ((Math.abs(slope) == 1) && (len == 2) && (this.getPlayer() != loc.getPlayer()) && Utility.coordTest(loc)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isValidMove(BoardLocation loc) {
		if (this.isOnChessBoard() && (this.isValidDiagonalMove(loc) || this.isValidStraightMove(loc) || this.isSpecialMove(loc))
		&& (!this.getPlayer().getKing().isCheckMatePostMove(this, loc))) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isValidStraightMove(BoardLocation loc) {
		int deltaX = loc.getX() - this.getX();
		int deltaY = loc.getY() - this.getY();
		if ((((deltaX == 0) && (Math.abs(deltaY) == 1)) || ((deltaY == 0) && (Math.abs(deltaX) == 1))) && (this.getPlayer() != loc.getPlayer()) && Utility.coordTest(loc)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean performMove(BoardLocation loc) {
		if (this.getMaxMoves() > 1) {
			boolean b = super.performMove(loc);
			this.setMaxLength(1);
			if (b && (this.leftSide != null)) {
				this.leftSide.getLocation().setPiece(null);
				this.leftSide.setLocation(this.getBoard().getLocation(this.getX() + 1, this.getY()));
				this.leftSide.getBoard().getLocation(this.leftSide).setPiece(this.leftSide);
				((Rook)this.leftSide).castling = false;
				this.leftSide = null;
			}
			if (b && (this.rightSide != null)) {
				this.rightSide.getLocation().setPiece(null);
				this.rightSide.setLocation(this.getBoard().getLocation(this.getX() - 1, this.getY()));
				this.rightSide.getBoard().getLocation(this.rightSide).setPiece(this.rightSide);
				((Rook)this.rightSide).castling = false;
				this.rightSide = null;
			}
			return b;
		} else {
			return super.performMove(loc);
		}
	}
	
	@Override
	public boolean theoryMove(BoardLocation loc) {
		if (this.isOnChessBoard() && !this.isSpecialMove(loc) && (this.isValidDiagonalMove(loc) || this.isValidStraightMove(loc))) {
			return true;
		} else {
			return false;
		}
	}
}
