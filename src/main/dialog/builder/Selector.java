package main.dialog.builder;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;

import board.Board;
import graphics.sprites.Sprite;
import tiles.Tile;
import tiles.TileCode;

public class Selector extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static int block = TileCode.REGULAR_TOP;

	private JDialog jd;

	public Selector() {
		initComponents();

		jd = new JDialog();
		jd.add(this);
		jd.pack();
		jd.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		jd.setAlwaysOnTop(true);
		jd.setVisible(true);
	}

	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		jPanel1 = new Panel();
		jPanel1.setSize(new Dimension(100, 100));

		jLabel1.setText("Select a tile:");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 133, Short.MAX_VALUE));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 121, Short.MAX_VALUE));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel1)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jPanel1.createImg(100, 100);
	}// </editor-fold>        

	// Variables declaration - do not modify                     
	private javax.swing.JLabel jLabel1;
	private Panel jPanel1;
	// End of variables declaration    

	public void close() {
		jd.dispose();
	}

	class Panel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		protected int[] pixels;
		private BufferedImage img;
		private int width, height;
		private final int SCALE = 2;

		private List<Tiles> tiles = new ArrayList<Tiles>();

		private boolean imgCreated = false;

		public void createImg(int width, int height) {
			this.width = width;
			this.height = height;

			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

			addTiles();
			imgCreated = true;

			setPreferredSize(new Dimension(width * SCALE, height * SCALE));

			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					for (int i = 0; i < tiles.size(); i++) {
						tiles.get(i).isSelected(false);
						if (e.getX() >= tiles.get(i).getPositionX() * SCALE && e.getX() <= (tiles.get(i).getPositionX() + tiles.get(i).getWidth()) * SCALE && e.getY() >= tiles.get(i).getPositionY() * SCALE && e.getY() <= (tiles.get(i).getPositionY() + tiles.get(i).getHeight()) * SCALE) {
							tiles.get(i).isSelected(true);
						}
					}
				}
			});
		}

		private void addTiles() {
			for (int y = 0; y < height / Board.tileSize; y++) {
				for (int x = 0; x < width / Board.tileSize; x++) {
					if (x + y * (width / Board.tileSize) >= Tile.listOfSprites.length) return;

					tiles.add(new Tiles(x, y, Tile.listOfSprites[x + y * (width / Board.tileSize)]));
				}
			}
		}

		@Override
		public void paintComponent(Graphics g) {
			for (int i = 0; i < tiles.size(); i++)
				tiles.get(i).render(this);

			if (imgCreated) g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
			repaint();
		}
	}

	class Tiles {

		private int xOff, yOff;
		private boolean isSelected;
		private Sprite sprite;

		public Tiles(int x, int y, final Sprite sprite) {
			this.xOff = x * Board.tileSize;
			this.yOff = y * Board.tileSize;
			this.sprite = sprite;
		}

		protected void isSelected(boolean isSelected) {
			this.isSelected = isSelected;

			if (isSelected) {
				System.out.println((xOff / Board.tileSize) + ((yOff ) / Board.tileSize) * 6 + 1);
				Selector.block = (xOff / Board.tileSize) + ((yOff ) / Board.tileSize) * 6 + 1;
			}
		}

		protected void render(Panel p) {
			for (int y = 0; y < sprite.getHeight(); y++) {
				for (int x = 0; x < sprite.getWidth(); x++) {
					p.pixels[(x + xOff) + (y + yOff) * p.width] = sprite.getSprite()[x + y * Board.tileSize];

					if ((y == 0 || y == sprite.getHeight() - 1 || x == 0 || x == sprite.getWidth() - 1) && isSelected) p.pixels[(x + xOff) + (y + yOff) * p.width] = 0x0;
				}
			}
		}

		public int getPositionY() {
			return yOff;
		}

		public int getPositionX() {
			return xOff;
		}

		public int getWidth() {
			return sprite.getWidth();
		}

		public int getHeight() {
			return sprite.getHeight();
		}
	}
}
