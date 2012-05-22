package level;

import entities.Background;
import entities.BreakableWall;
import entities.Finishpoint;
import entities.Player;
import entities.Wall;
import entities.WallWithFinishingPoint;
import game.Game;
import game.KeySettings;
import game.Main;

import java.util.Scanner;

public class Loader {

	/**
	 * Creates a level from a map file Types: 1 = Breakable Wall 2 = Solid Wall
	 * 3 = Spawnpoint 0 = empty Background 5 = Finishpoint
	 * 
	 * @param filename
	 */
	public void loadMap(String filename) {

		int x = 0, type, y = 0;
		int player_count = 0;

		Scanner maps;
		try {

			maps = new Scanner(Main.class.getResourceAsStream("/ressources/maps/" + filename));
			while (maps.hasNextLine()) {
				String text = maps.nextLine();
				for (x = 0; x < text.length(); x++) {
					type = Integer.parseInt("" + text.charAt(x));

					if (type == 1) {
						Game.entities.add(new BreakableWall(x * Game.BLOCK_SIZE, y * Game.BLOCK_SIZE));
					} else if (type == 2) {
						Game.entities.add(new Wall(x * Game.BLOCK_SIZE, y * Game.BLOCK_SIZE));
					} else if (type == 3) {
						Player p = new Player(x * Game.BLOCK_SIZE, y * Game.BLOCK_SIZE);
						KeySettings keys = Game.getKeySettings(player_count);
						p.setKeys(keys);
						player_count++;
						Game.entities.add(p);
						Game.players.add(p);
						Game.staticBackground.add(new Background(x * Game.BLOCK_SIZE, y * Game.BLOCK_SIZE));
					} else if (type == 5) {
						Game.entities.add(new Finishpoint(x * Game.BLOCK_SIZE, y * Game.BLOCK_SIZE));
						Game.staticBackground.add(new Background(x * Game.BLOCK_SIZE, y * Game.BLOCK_SIZE));
					} else if (type == 0) {
						Game.staticBackground.add(new Background(x * Game.BLOCK_SIZE, y * Game.BLOCK_SIZE));
					} else if (type == 6) {
						Game.entities.add(new WallWithFinishingPoint(x * Game.BLOCK_SIZE, y * Game.BLOCK_SIZE));

					}

				}
				y++;
			}
			Game.FIELD_HEIGHT = y;
			Game.FIELD_WIDTH = x;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
