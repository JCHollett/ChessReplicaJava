package ChessGame.Pieces;

import ChessGame.Board.Board;
import ChessGame.Board.BoardLocation;
import ChessGame.Moves.MoveValidation;
import ChessGame.Net.Color;
import ChessGame.Net.Player;

public class ChessPiece implements MoveValidation {
	private Color color;
	private boolean isInPlay;
	private BoardLocation location;
	private int maxMoveLength;
	private Player player;
	private Suit suit;
	private String type;
	
	public ChessPiece(BoardLocation loc, Board b) {
		this.isInPlay = false;
		this.location = loc;
		this.player = null;
		this.type = " ";
		this.maxMoveLength = -1;
		this.suit = Suit.NULL;
		this.color = Color.NULL;
	}
	
	protected ChessPiece(Player p, BoardLocation loc, boolean onBoard) {
		this.player = p;
		this.location = loc;
		this.isInPlay = onBoard;
		this.type = " ";
		this.maxMoveLength = -1;
		this.suit = Suit.NULL;
		this.color = p.getColor();
	}
	
	protected Board getBoard() {
		return this.getLocation().getBoard();
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public BoardLocation getLocation() {
		return this.location;
	}
	
	protected int getMaxMoves() {
		return this.maxMoveLength;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Suit getSuit() {
		return this.suit;
	}
	
	public String getType() {
		return this.type;
	}
	
	public int getX() {
		return this.getLocation().getX();
	}
	
	public int getY() {
		return this.getLocation().getY();
	}
	
	public boolean isOnChessBoard() {
		return this.isInPlay;
	}
	
	@Override
	public boolean isValidMove(BoardLocation loc) {
		return false;
	}
	
	@Override
	public boolean performMove(BoardLocation loc) {
		if (!(this instanceof Pawn)) {
			Pawn.enpassant = null;
		}
		if (this.isValidMove(loc)) {
			loc.setOnChessBoard(false);
			loc.setPiece(this);
			this.getLocation().setPiece(null);
			this.setLocation(loc);
			System.out.println(this.getClass());
			return true;
		} else {
			System.out.println("Fail");
			return false;
		}
	}
	
	public boolean performMove(int x, int y) {
		return this.performMove(this.getBoard().getLocation(x, y));
	}
	
	public void setLocation(BoardLocation loc) {
		this.location = loc;
	}
	
	protected void setMaxLength(int x) {
		this.maxMoveLength = x;
	}
	
	public void setOnChessBoard(boolean state) {
		this.isInPlay = state;
	}
	
	protected void setSuit(Suit s) {
		this.suit = s;
	}
	
	protected void setType(String x) {
		this.type = x;
	}
	
	@Override
	public boolean theoryMove(BoardLocation loc) {
		return false;
	}
	
	@Override
	public String toString() {
		return new String(this.getClass().getSimpleName() + "[pointer=p(" + this.getX() + "," + this.getY() + ")->l(" + this.getLocation().getX() + ","
		+ this.getLocation().getY() + "),isInPlay=" + this.isOnChessBoard() + "]");
	}
}
