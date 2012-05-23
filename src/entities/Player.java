package entities;

import game.Game;
import game.KeySettings;
import graphics.Image;
import graphics.Sprite;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import level.Box;

public class Player extends Entity {
	private float width;
	private float height;
	private int speed;
	private KeySettings keys;
	private Image[][] facings;
	private int facing = 0;

	public Player(int x, int y) {
		super(x + 1, y + 1);
		this.facings = Sprite.load("bomberman.png", 55, 90);
		this.width = (55 / 2);
		this.height = (90 / 2);
		this.box = new Box(this.x, this.y, (int) this.width, (int) this.height);

		this.speed = 10;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage((this.facings[this.facing][0]).image, this.x, this.y,
				(int) this.width, (int) this.height, null);
	}

	@Override
	public void action(double delta) {
		boolean moved = false;
		if (this.keys.up.down) {
			this.y = this.y - this.speed;
			this.facing = 1;
			moved = true;
		}

		if (this.keys.down.down) {
			this.y = this.y + this.speed;
			this.facing = 0;
			moved = true;
		}

		if (this.keys.left.down) {
			this.x = this.x - this.speed;
			this.facing = 2;
			moved = true;
		}

		if (this.keys.right.down) {
			this.x = this.x + this.speed;
			this.facing = 3;
			moved = true;
		}

		if (moved == false) {
			this.facing = 0;
		}

		this.box.update(this.x, this.y);

		List<Entity> es = Game.getEntities(this.box);
		for (Entity e : es) {
			if (e != this) {
				e.collide(this);
				this.collide(e);
				this.box.update(this.x, this.y);
			}
		}

		if (this.keys.bomb.down) {
			Game.entities.add(new Bomb(Box.fitToBlock(this.x), Box
					.fitToBlock(this.y), this));

		}
	}

	@Override
	public void collide(Entity e) {
		if (e instanceof Wall) {
			ArrayList<Integer> dir = this.box.collideDirection(e.box);
			if (dir.contains(Box.COLLIDE_LEFT)) {
				this.x = this.x + this.speed;
			}

			if (dir.contains(Box.COLLIDE_RIGHT)) {
				this.x = this.x - this.speed;
			}

			if (dir.contains(Box.COLLIDE_DOWN)) {
				this.y = this.y - this.speed;
			}

			if (dir.contains(Box.COLLIDE_UP)) {
				this.y = this.y + this.speed;
			}
		}
		// TODO: Player shouldn't be instantly kicked off the bomb
		if ((e instanceof Bomb) && (((Bomb) e).owner != this)) {
			ArrayList<Integer> dir = this.box.collideDirection(e.box);

			if (dir.contains(Box.COLLIDE_LEFT)) {
				this.x = this.x + this.speed;
			}

			if (dir.contains(Box.COLLIDE_RIGHT)) {
				this.x = this.x - this.speed;
			}

			if (dir.contains(Box.COLLIDE_DOWN)) {
				this.y = this.y - this.speed;
			}

			if (dir.contains(Box.COLLIDE_UP)) {
				this.y = this.y + this.speed;
			}

		}
	}

	public void setKeys(KeySettings keys) {
		this.keys = keys;
	}
}
