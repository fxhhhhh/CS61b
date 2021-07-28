package PlusWorld;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.Random;
import java.util.Timer;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final Random RANDOM = new Random(100);


    /**
     * Fills the given 2D array of tiles with RANDOM tiles.
     *
     * @param TETile
     */
    public static void fillWithPlus(TETile[][] TETile, int size) {
        int height = TETile[0].length;
        int width = TETile.length;
        int a =RANDOM.nextInt(WIDTH-3*size);
        System.out.println(a);
        int b =RANDOM.nextInt(HEIGHT-3*size);
        System.out.println(b);
        for (int i = 0; i < 3 * size; i++) {
            for (int j = size; j < 2 * size; j++) {
                if(a+i>WIDTH){
                    break;
                }
                TETile[a + i][b+j] = Tileset.FLOWER;
            }
        }
        for (int i = size; i < 2 * size; i++) {
            for (int j = 0; j < 3 * size; j++) {
                TETile[a + i][b+j] = Tileset.FLOWER;
            }
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        fillWithPlus(tiles, 5);
        fillWithPlus(tiles, 4);
        fillWithPlus(tiles, 3);
        fillWithPlus(tiles, 2);

        ter.renderFrame(tiles);
    }


}
