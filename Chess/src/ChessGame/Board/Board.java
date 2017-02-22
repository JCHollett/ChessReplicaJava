package ChessGame.Board;

import java.util.ArrayList;

import ChessGame.Exceptions.BoardLocationException;
import ChessGame.Net.Color;
import ChessGame.Net.Player;
import ChessGame.Pieces.ChessPiece;
import ChessGame.Pieces.Utility;

public class Board {
	private BoardLocation[][] grid;
	private Player one;
	private Player two;
	
	public Board(Player one, Player two) {
		this.grid = new BoardLocation[8][8];
		for (int y = 0; y < this.grid.length; y++) {
			for (int x = 0; x < this.grid[y].length; x++) {
				try {
					this.grid[y][x] = new BoardLocation(x, y, this);
				} catch (BoardLocationException e) {
					e.printStackTrace();
				}
			}
		}
		this.initializePieces(this.grid, one, two);
		this.one = one.addPieces(this.getChessPieces(one.getColor())).setOpponent(two);
		this.two = two.addPieces(this.getChessPieces(two.getColor())).setOpponent(one);
	}
	
	public ArrayList<ChessPiece> getChessPieces(Color color) {
		ArrayList<ChessPiece> pieces = new ArrayList<ChessPiece>();
		for (BoardLocation[] y : this.grid) {
			for (BoardLocation x : y) {
				if (x.getPiece() != null) {
					if (x.getPiece().getColor() == color) {
						pieces.add(x.getPiece());
					}
				}
			}
		}
		return pieces;
	}
	
	public BoardLocation[][] getGrid() {
		return this.grid;
	}
	
	public BoardLocation getLocation(ChessPiece p) {
		return p.getLocation();
	}
	
	public BoardLocation getLocation(int x, int y) {
		if (Utility.coordTest(x, y)) {
			return this.grid[y][x];
		} else {
			return null;
		}
	}
	
	public ChessPiece getPieceAt(BoardLocation loc) {
		return this.grid[loc.getY()][loc.getX()].getPiece();
	}
	
	public ChessPiece getPieceAt(int x, int y) {
		BoardLocation l = this.getLocation(x, y);
		if (l != null) {
			return this.getPieceAt(l);
		} else {
			return null;
		}
	}
	
	private void initializePieces(BoardLocation[][] g, Player p1, Player p2) {
		Utility.getNewPieces(g, p1, p2);
	}
	
	public Board newBoard() {
		return new Board(this.one, this.two);
	}
	
	public void printConsole() {
		System.out.print("    [x0][x1][x2][x3][x4][x5][x6][x7]");
		for (int y = this.grid.length - 1; y >= 0; y--) {
			System.out.print("\n[y" + (y) + "]");
			for (int x = 0; x < this.grid[y].length; x++) {
				if (this.grid[y][x].getPiece() != null) {
					String c = this.grid[y][x].getPiece().getColor() == Color.BLACK ? "B" : "W";
					System.out.print("[" + c + this.grid[y][x].getPiece().getType() + "]");
				} else {
					System.out.print("[  ]");
				}
			}
			System.out.print("[y" + (y) + "]");
		}
		System.out.println("\n    [x0][x1][x2][x3][x4][x5][x6][x7]\n");
	}
	
	public void setLocation(BoardLocation loc) {
		this.grid[loc.getY()][loc.getX()] = loc;
	}
	
	public void setPieceAt(BoardLocation loc, ChessPiece p) {
		this.grid[loc.getY()][loc.getX()].setPiece(p);
	}
	
	@Override
	public String toString() {
		return this.getClass().getName() + "[grid=" + this.grid + "]";
	}
}
