package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;

public class View extends JComponent{
		private Game game;

		public View(Game game) {
			this.game = game;
		}

		@Override
		public void paintComponent(Graphics g2) {
			synchronized (Game.class) {
				Graphics2D g = (Graphics2D) g2;
				RenderingHints rendererTextAntialiasing = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				RenderingHints rendererAntialiasing = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g.setRenderingHints(rendererTextAntialiasing);
				g.setRenderingHints(rendererAntialiasing);

				// paint the background
				Color BG_COLOR_A = new Color(0, 6, 30);
				Color BG_COLOR_B = new Color(14, 0, 28);
				GradientPaint gp = new GradientPaint(0, (int) (0.6 * getHeight()), BG_COLOR_A, getWidth(), (int) (0.3 * getHeight()), BG_COLOR_B, true);
				g.setPaint(gp);

				//Translates by the camera position to give illusion of scrolling camera.
				g.translate(-game.camera.position.x, -game.camera.position.y);
				g.fillRect(0, 0, (int) game.currentZone.size.x, (int) game.currentZone.size.y);

				for (Particle particle : game.particles) {
					particle.draw(g);
				}

				for (LootCrate lootCrate : game.lootCrates) {
					lootCrate.draw(g);
				}

				for (Asteroid asteroid : game.asteroids) {
					asteroid.draw(g);
				}

				for (Ship player : game.players) {
					player.draw(g);
				}

				for (Bullet bullet : game.bullets) {
					bullet.draw(g);
				}

				g.translate(game.camera.position.x, game.camera.position.y);

				game.gui.draw(g);

			}
		}

		@Override
		public Dimension getPreferredSize() {
			return Constants.FRAME_SIZE;
		}
}
