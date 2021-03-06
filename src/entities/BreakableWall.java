package entities;

import java.awt.image.BufferedImage;
import java.util.Random;

import entities.items.Falle;
import entities.items.Item;
import entities.items.Schuh;
import entities.items.SlowDown;
import game.Game;
import graphics.Sprite;

/**
 * Wall which can be destroyed by Bomb
 */
public class BreakableWall extends Wall {

	protected Item item = null;

	/**
	 * @param x
	 *            Upper Left Corner of the wall
	 * @param y
	 *            Upper Left Corner of the wall
	 */

	public BreakableWall(int x, int y) {
		super(x, y);
		int z = new Random().nextInt(12);
		// int z = (int) (Math.random() * 10);
		if (z < 4) {
			this.images = Sprite.load("w1.png", 100, 100,
					BufferedImage.TYPE_INT_RGB);
		} else if (z < 8) {
			this.images = Sprite.load("w2.png", 100, 100,
					BufferedImage.TYPE_INT_RGB);
		} else {
			this.images = Sprite.load("w3.png", 100, 100,
					BufferedImage.TYPE_INT_RGB);
		}

		if (new Random().nextInt(10) > 8) {
			this.item = new Falle(x, y);
		} else if (new Random().nextInt(10) > 8) {
			this.item = new Schuh(x, y);
		} else if (new Random().nextInt(10) > 8) {
			this.item = new SlowDown(x, y);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see entities.Entity#collide(entities.Entity)
	 */
	@Override
	public void collide(Entity e) {
		if (e instanceof BombAnimation) {
			this.removed = true;
			Game.staticBackground.add(new Background(this.x, this.y));
			((BombAnimation) e).owner.pm.addPoints(200);
			if (this.item != null) {
				((BombAnimation) e).addAfterExplosion(this.item);
			}
		}
	}
}
