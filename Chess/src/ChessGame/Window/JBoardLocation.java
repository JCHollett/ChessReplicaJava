package ChessGame.Window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import ChessGame.Board.BoardLocation;
import ChessGame.Pieces.ChessPiece;

@SuppressWarnings("serial")
public class JBoardLocation extends JPanel {
	private BufferedImage img;
	private BoardLocation location;
	private ChessPiece piece;
	
	public JBoardLocation(BoardLocation loc) {
		this.location = loc;
		this.img = null;
		if ((this.location.getY() % 2) == 0) {
			if ((this.location.getX() % 2) == 1) {
				this.setBackground(new Color(64, 64, 64));
			}
		} else if ((this.location.getX() % 2) == 0) {
			this.setBackground(new Color(64, 64, 64));
		}
		this.setBorder(BorderFactory.createBevelBorder(0, Color.GRAY, Color.YELLOW));
	}
	
	public BufferedImage getImage() {
		return this.img;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if (this.location.hasPiece()) {
			this.updateNode();
			super.paintComponent(g);
			g.drawImage(this.img, (this.getWidth() / 2) - (this.img.getWidth() / 2), (this.getHeight() / 2) - (this.img.getHeight() / 2), null);
		} else {
			super.paintComponent(g);
		}
	}
	
	public void updateNode() {
		try {
			if (this.location.hasPiece() && (this.piece != this.location.getPiece())) {
				this.piece = this.location.getPiece();
				this.img = ImageIO.read(this.getClass().getResource(this.location.getJPiece()));
			}
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}
}
