package ChessGame.Window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import ChessGame.Board.Board;
import ChessGame.Net.Player;

public class ChessWindow {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ChessWindow window = new ChessWindow();
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/** Launch the application. */
	private Board board;
	public JFrame frame;
	private JBoardLocation loc;
	private JBoardLocation[][] panels = new JBoardLocation[8][8];
	private JPanel piecePanel;
	
	/** Create the application. */
	public ChessWindow() {
		Player one = new Player(ChessGame.Net.Color.BLACK);
		Player two = new Player(ChessGame.Net.Color.WHITE);
		this.board = new Board(one, two);
		this.initialize();
		this.setUpJBoard();
		this.frame.repaint();
		this.frame.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if (ChessWindow.this.piecePanel.getComponentAt(e.getX(), e.getY()) instanceof JBoardLocation) {
					ChessWindow.this.loc = (JBoardLocation)ChessWindow.this.piecePanel.getComponentAt(e.getX(), e.getY());
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if (ChessWindow.this.loc != null) {
					ChessWindow.this.loc = null;
				}
				ChessWindow.this.frame.repaint();
			}
		});
		this.frame.addMouseMotionListener(new MouseMotionListener() {
			private Point p = null;
			
			@Override
			public void mouseDragged(MouseEvent e) {
				Graphics g = ChessWindow.this.frame.getGraphics();
				if (ChessWindow.this.loc != null) {
					if (ChessWindow.this.loc.getImage() != null) {
						if (this.p == null) {
							this.p = e.getPoint();
						}
						if (g == null) {
							g = ChessWindow.this.frame.getGraphics();
						}
						if (ChessWindow.this.loc.getImage() == null) {
							return;
						}
						if ((e.getX() != (int)this.p.getX()) && (e.getY() != (int)this.p.getY())) {
							ChessWindow.this.frame.repaint();
							this.p = e.getPoint();
						}
						g.drawImage(ChessWindow.this.loc.getImage(), (int)this.p.getX() - (ChessWindow.this.loc.getImage().getWidth() / 2), (int)this.p.getY()
							- (ChessWindow.this.loc.getImage().getHeight() / 2), null);
					}
				}
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				Graphics g = ChessWindow.this.frame.getGraphics();
				if (ChessWindow.this.loc != null) {
					if (ChessWindow.this.loc.getImage() != null) {
						if (this.p == null) {
							this.p = e.getPoint();
						}
						if (g == null) {
							g = ChessWindow.this.frame.getGraphics();
						}
						if (ChessWindow.this.loc.getImage() == null) {
							return;
						}
						if ((e.getX() != (int)this.p.getX()) && (e.getY() != (int)this.p.getY())) {
							ChessWindow.this.frame.repaint();
							this.p = e.getPoint();
						}
						g.drawImage(ChessWindow.this.loc.getImage(), (int)this.p.getX() - (ChessWindow.this.loc.getImage().getWidth() / 2), (int)this.p.getY()
							- (ChessWindow.this.loc.getImage().getHeight() / 2), null);
					}
				}
			}
		});
	}
	
	/** Initialize the contents of the frame. */
	private void initialize() {
		this.frame = new JFrame();
		this.frame.setResizable(false);
		this.frame.setSize(new Dimension(750, 750));
		this.frame.setBounds(100, 100, 695, 700);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.getContentPane().setLayout(new BorderLayout(0, 0));
		this.piecePanel = new JPanel();
		this.piecePanel.setBounds(new Rectangle(5, 5, 685, 685));
		this.piecePanel.setBackground(new Color(215, 164, 32));
		this.frame.getContentPane().add(this.piecePanel, BorderLayout.CENTER);
		this.piecePanel.setLayout(new GridLayout(8, 8, 5, 5));
	}
	
	private void setUpJBoard() {
		for (int y = 0; y < this.panels.length; y++) {
			for (int x = 0; x < this.panels[y].length; x++) {
				this.panels[y][x] = new JBoardLocation(this.board.getLocation(x, y));
				this.piecePanel.add(this.panels[y][x]);
			}
		}
	}
}
