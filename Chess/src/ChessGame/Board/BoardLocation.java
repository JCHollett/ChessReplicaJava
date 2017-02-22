package ChessGame.Board;

import ChessGame.Exceptions.BoardLocationException;
import ChessGame.Net.Color;
import ChessGame.Net.Player;
import ChessGame.Pieces.ChessPiece;
import ChessGame.Pieces.Suit;
import ChessGame.Pieces.Utility;

public class BoardLocation {
	private Board board;
	private ChessPiece piece;
	private int x;
	private int y;
	
	protected BoardLocation(int x, int y, Board b) throws BoardLocationException {
		if (Utility.coordTest(x, y)) {
			this.x = x;
			this.y = y;
		} else {
			throw new BoardLocationException("Invalid BoardLocation(" + this.x + "," + this.y + ")");
		}
		this.board = b;
		this.piece = null;
	}
	
	protected BoardLocation(int x, int y, ChessPiece p, Board b) throws BoardLocationException {
		if (Utility.coordTest(x, y)) {
			this.x = x;
			this.y = y;
		} else {
			throw new BoardLocationException("Invalid BoardLocation(" + this.x + "," + this.y + ")");
		}
		this.board = b;
		this.piece = p;
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public String getJPiece() {
		if (this.getPiece() != null) {
			System.out.println(this.getPiece().getColor() + "-" + this.getPiece().getSuit() + ".png");
			return (this.getPiece().getColor() + "-" + this.getPiece().getSuit() + ".png");
		} else {
			return (Color.NULL + "-" + Suit.NULL + ".png");
		}
	}
	
	public ChessPiece getPiece() {
		return this.piece;
	}
	
	public Player getPlayer() {
		if (this.getPiece() != null) {
			return this.getPiece().getPlayer();
		}
		return null;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public boolean hasPiece() {
		return this.getPiece() != null ? true : false;
	}
	
	public void setOnChessBoard(boolean b) {
		if (this.getPiece() != null) {
			this.getPiece().setOnChessBoard(b);
		}
	}
	
	public void setPiece(ChessPiece p) {
		this.piece = p;
	}
	
	@Override
	public String toString() {
		if (this.getPiece() != null) {
			return new String(this.getClass().getSimpleName() + "[ChessPiece=" + this.getPiece() + ",pointer=l(" + this.getX() + "," + this.getY() + ")->p("
			+ this.getPiece().getX() + "," + this.getPiece().getY() + ")]");
		} else {
			return new String(this.getClass().getSimpleName() + "[ChessPiece=" + this.getPiece() + ",pointer=l(" + this.getX() + "," + this.getY() + ")->p(x,y)]");
		}
	}
}
