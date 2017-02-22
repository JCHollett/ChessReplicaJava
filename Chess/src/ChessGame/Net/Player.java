package ChessGame.Net;

import java.util.ArrayList;
import java.util.List;

import ChessGame.Board.BoardLocation;
import ChessGame.Pieces.ChessPiece;
import ChessGame.Pieces.King;
import ChessGame.Types.Node;

public class Player {
	private int NValid;
	private Color color;
	private King king;
	private List<Node<BoardLocation, BoardLocation>> moves = new ArrayList<Node<BoardLocation, BoardLocation>>();
	private Player opponent;
	private ArrayList<ChessPiece> pieces = new ArrayList<ChessPiece>();
	
	public Player(Color s) {
		this.color = s;
	}
	
	public void addMove(Node<BoardLocation, BoardLocation> n) {
		NValid++;
		this.moves.add(n);
	}
	
	public Player addPiece(ChessPiece p) {
		this.pieces.add(p);
		return this;
	}
	
	public Player addPieces(ArrayList<ChessPiece> arrayList) {
		NValid += arrayList.size();
		this.pieces.addAll(arrayList);
		return this;
	}
	
	public Player clearMoves() {
		this.moves.clear();
		NValid = 0;
		return this;
	}
	
	public Player clearPieces() {
		this.pieces.clear();
		return this;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public King getKing() {
		return this.king;
	}
	
	public List<Node<BoardLocation, BoardLocation>> getMoves() {
		return this.moves;
	}
	
	public Player getOpponent() {
		return this.opponent;
	}
	
	public ArrayList<ChessPiece> getPieces() {
		return this.pieces;
	}
	
	public boolean removePiece(ChessPiece p) {
		return this.pieces.remove(p);
	}
	
	public void setKing(King k) {
		this.king = k;
	}
	
	public boolean hasMoves() {
		return NValid > 0 ? true : false;
	}
	
	public Player setOpponent(Player p) {
		this.opponent = p;
		return this;
	}
	
	@Override
	public String toString() {
		return this.getClass().getName() + "[suit=" + this.getColor() + "]";
	}
}
