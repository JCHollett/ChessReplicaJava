package ChessGame.Pieces;

import java.util.ArrayList;
import java.util.List;

import ChessGame.Board.BoardLocation;
import ChessGame.Net.Player;
import ChessGame.Types.Node;

public class Utility {
	private static ChessPiece theory;
	
	public static boolean coordTest(BoardLocation l) {
		if (((l.getX() >= 0) && (l.getX() <= 7)) && ((l.getY() >= 0) && (l.getY() <= 7))) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean coordTest(int x, int y) {
		if (((x >= 0) && (x <= 7)) && ((y >= 0) && (y <= 7))) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean fakeMove(BoardLocation defense, BoardLocation offense) {
		King k = defense.getPlayer().getKing();
		Utility.swap(defense, offense);
		k.getBoard().printConsole();
		boolean checkMate = k.isCheckMate(k.getLocation());
		Utility.reverseSwap(defense, offense);
		return checkMate;
	}
	
	public static void getNewPieces(BoardLocation[][] x, Player one, Player two) {
		// x[0][0].setPiece(new Rook(one, x[0][0], true));
		// x[0][1].setPiece(new Knight(one, x[0][1], true));
		// x[0][2].setPiece(new Bishop(one, x[0][2], true));
		King p1 = new King(one, x[0][3], true);
		x[0][3].setPiece(p1);
		one.setKing(p1);
		// x[0][4].setPiece(new Queen(one, x[0][4], true));
		// x[0][5].setPiece(new Bishop(one, x[0][5], true));
		// x[0][6].setPiece(new Knight(one, x[0][6], true));
		// x[0][7].setPiece(new Rook(one, x[0][7], true));
		// x[1][0].setPiece(new Pawn(one, x[1][0], true));
		// x[1][1].setPiece(new Pawn(one, x[1][1], true));
		// x[1][2].setPiece(new Pawn(one, x[1][2], true));
		// x[1][3].setPiece(new Pawn(one, x[1][3], true));
		// x[1][4].setPiece(new Pawn(one, x[1][4], true));
		// x[1][5].setPiece(new Pawn(one, x[1][5], true));
		// x[1][6].setPiece(new Pawn(one, x[1][6], true));
		// x[1][7].setPiece(new Pawn(one, x[1][7], true));
		// x[6][0].setPiece(new Pawn(two, x[6][0], true));
		// x[6][1].setPiece(new Pawn(two, x[6][1], true));
		// x[6][2].setPiece(new Pawn(two, x[6][2], true));
		// x[6][3].setPiece(new Pawn(two, x[6][3], true));
		// x[6][4].setPiece(new Pawn(two, x[6][4], true));
		// x[6][5].setPiece(new Pawn(two, x[6][5], true));
		// x[6][6].setPiece(new Pawn(two, x[6][6], true));
		// x[6][7].setPiece(new Pawn(two, x[6][7], true));
		// x[7][0].setPiece(new Rook(two, x[7][0], true));
		// x[7][1].setPiece(new Knight(two, x[7][1], true));
		// x[7][2].setPiece(new Bishop(two, x[7][2], true));
		x[0][4].setPiece(new Rook(two, x[0][4], true));
		x[2][3].setPiece(new Rook(two, x[2][3], true));
		//		x[0][5].setPiece(new Rook(two, x[0][5], true));
		King p2 = new King(two, x[7][3], true);
		x[7][3].setPiece(p2);
		two.setKing(p2);
		// x[7][4].setPiece(new Queen(two, x[7][4], true));
		// x[7][5].setPiece(new Bishop(two, x[7][5], true));
		// x[7][6].setPiece(new Knight(two, x[7][6], true));
		// x[7][7].setPiece(new Rook(two, x[7][7], true));
	}
	
	public static List<Node<BoardLocation, BoardLocation>> getSpaces(BoardLocation a, BoardLocation b) {
		List<Node<BoardLocation, BoardLocation>> moves = new ArrayList<Node<BoardLocation, BoardLocation>>();
		short deltaY = (short)(b.getY() - a.getY());
		short deltaX = (short)(b.getX() - a.getX());
		short changeX = 0;
		short changeY = 0;
		Node<BoardLocation, BoardLocation> p = null;
		if (((deltaY == 0) && (deltaX != 0)) || ((deltaY != 0) && (deltaX == 0)) || ((deltaX != 0) && (Math.abs(deltaY / deltaX) != 1))) {
			if (deltaY > 0) {
				changeY = -1;
				if (deltaX > 0) {
					changeX = -1;
				} else if (deltaX < 0) {
					changeX = 1;
				}
			} else if (deltaY == 0) {
				if (deltaX > 0) {
					changeX = -1;
				} else if (deltaX < 0) {
					changeX = 1;
				}
			} else {
				changeY = 1;
				if (deltaX > 0) {
					changeX = -1;
				} else if (deltaX < 0) {
					changeX = 1;
				}
			}
		}
		while (!((deltaX == 0) && (deltaY == 0))) {
			p = new Node<BoardLocation, BoardLocation>(a, a.getBoard().getLocation(a.getX() + deltaX, a.getY() + deltaY));
			moves.add(p);
			deltaX += changeX;
			deltaY += changeY;
		}
		return moves;
	}
	
	private static void reverseSwap(BoardLocation Origin, BoardLocation Final) {
		if (theory != null) {
			theory.setOnChessBoard(true);
			Origin.setPiece(Final.getPiece());
			Final.getPiece().setLocation(Origin);
			Final.setPiece(theory);
		} else if ((Final.getPiece() instanceof Pawn) && Pawn.isPassant) {
			Pawn.enpassant.getLocation().setPiece(Pawn.enpassant);
			Pawn.enpassant.setOnChessBoard(true);
			Origin.setPiece(Final.getPiece());
			Final.getPiece().setLocation(Origin);
			Final.setPiece(null);
		} else {
			Origin.setPiece(Final.getPiece());
			Final.getPiece().setLocation(Origin);
			Final.setPiece(null);
		}
		theory = null;
	}
	
	private static void swap(BoardLocation Origin, BoardLocation Final) {
		if (Final.getPiece() != null) {
			theory = Final.getPiece();
			Final.setOnChessBoard(false);
			Final.setPiece(Origin.getPiece());
			Origin.getPiece().setLocation(Final);
			Origin.setPiece(null);
		} else if ((Origin.getPiece() instanceof Pawn) && Pawn.isPassant) {
			Pawn.enpassant.getLocation().setPiece(null);
			Pawn.enpassant.setOnChessBoard(false);
			Final.setPiece(Origin.getPiece());
			Origin.getPiece().setLocation(Final);
			Origin.setPiece(null);
		} else {
			Final.setPiece(Origin.getPiece());
			Origin.getPiece().setLocation(Final);
			Origin.setPiece(null);
		}
	}
}
