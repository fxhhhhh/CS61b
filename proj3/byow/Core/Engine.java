package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashMap;
import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static int SEED;
    public static Random RANDOM = new Random(SEED);
    public HashMap<String, TETile[][]> existWorld = new HashMap<>();

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        String seed = null;
        seed = input.substring(1, input.length() - 1);
        TETile[][] finalWorldFrame = null;
        if (existWorld.containsKey(seed)) {
            return existWorld.get(seed);
        } else {
            finalWorldFrame = createWorld(seed);
            existWorld.put(seed, finalWorldFrame);
            return finalWorldFrame;
        }
    }

    public TETile[][] createWorld(String seed) {
        RANDOM = new Random(seed.hashCode());
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        int c = 0;
        int d = 0;
        int times = 0;
        while (times < 5) {
            times = RANDOM.nextInt(7);
        }
        for (int k = 2; k < 3 * times; k++) {
            int i = k % 2;
            int a = RANDOM.nextInt(WIDTH - 3 * i);
            int b = RANDOM.nextInt(HEIGHT - 3 * i);
            if (i < 3) {
                fillWithSquare(tiles, RANDOM.nextInt(3), a, b);
                if (c != 0 && d != 0) {
                    for (int j = 0; j < Math.abs(a - c) + i; j++) {
                        tiles[Math.min(a, c) + j][Math.min(b, d)] = Tileset.SAND;
                    }
                    for (int j = 0; j < Math.abs(b - d) + i - 1; j++) {
                        tiles[Math.min(a, c) + Math.abs(a - c) + i - 1][Math.min(b, d) + j] = Tileset.SAND;
                        tiles[Math.min(a, c)][Math.min(b, d) + j] = Tileset.SAND;
                    }
                }
                c = a;
                d = b;
            } else {
                fillWithPlus(tiles, i, a, b);
                if (c != 0 && d != 0) {
                    a = a + i;
                    for (int j = 0; j < Math.abs(a - c) + i; j++) {
                        tiles[Math.min(a, c) + j][Math.min(b, d)] = Tileset.SAND;
                    }
                    for (int j = 0; j < Math.abs(b - d) + i - 1; j++) {
                        tiles[Math.min(a, c) + Math.abs(a - c) + i - 1][Math.min(b, d) + j] = Tileset.SAND;
                        tiles[Math.min(a, c)][Math.min(b, d) + j] = Tileset.SAND;
                    }
                    c = a;
                    d = b;
                } else {
                    c = a + i;
                    d = b;
                }
            }
        }
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (tiles[x][y] == Tileset.SAND) {
                    addWall(tiles, x, y + 1);
                    addWall(tiles, x, y - 1);
                    addWall(tiles, x - 1, y);
                    addWall(tiles, x + 1, y);
                    addWall(tiles, x - 1, y - 1);
                    addWall(tiles, x - 1, y + 1);
                    addWall(tiles, x + 1, y - 1);
                    addWall(tiles, x + 1, y + 1);
                    deleteSAND(tiles, x, y);
                }

            }
        }
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (tiles[x][y] != Tileset.NOTHING) {
                    if (x == 0 || x == WIDTH - 1 || y == 0 || y == HEIGHT - 1) {
                        tiles[x][y] = Tileset.WALL;
                    }
                }
            }
        }


        ter.renderFrame(tiles);
        return tiles;
    }

    public void addWall(TETile[][] tiles, int x, int y) {
        if (x >= WIDTH || y >= HEIGHT || x < 0 || y < 0) {
            return;
        }
        if (tiles[x][y] == Tileset.NOTHING) {
            tiles[x][y] = Tileset.WALL;
        }
    }

    public boolean isWall(TETile[][] tiles, int x, int y) {
        if (x >= WIDTH || y >= HEIGHT || x < 0 || y < 0) {
            return false;
        }
        if (tiles[x][y] == Tileset.WALL) {
            return true;
        }
        return false;
    }

    public void deleteSAND(TETile[][] tiles, int x, int y) {
        if (isWall(tiles, x, y - 1) && isWall(tiles, x, y + 1) && isWall(tiles, x - 1, y) && isWall(tiles, x + 1, y)) {
            tiles[x][y] = Tileset.NOTHING;
        }
    }

    public void deleteWall(TETile[][] tiles, int x, int y) {
        if (isWall(tiles, x, y - 1) && isWall(tiles, x, y + 1) && isWall(tiles, x - 1, y) && isWall(tiles, x + 1, y)) {
            tiles[x][y] = Tileset.NOTHING;
        }
    }

    /**
     * Fills the given 2D array of tiles with RANDOM tiles.
     *
     * @param TETile
     */
    public static void fillWithPlus(TETile[][] TETile, int size, int a, int b) {
        for (int i = 0; i < 3 * size ; i++) {
            for (int j = size ; j < 2 * size ; j++) {
                if (a + i > WIDTH || b + j > HEIGHT || b + j < 0 || a + i < 0 || TETile[a + i][b + j] == Tileset.SAND) {
                    continue;
                } else {
//                    if (j == size - 1 || j == 2 * size || i == -1 || i == 3 * size) {
//                        TETile[a + i][b + j] = Tileset.WALL;
//                    } else {
                        TETile[a + i][b + j] = Tileset.SAND;
//                    }
                }
            }
        }

        for (int i = size; i < 2 * size; i++) {
            for (int j = 0; j < 3 * size; j++) {
                if (a + i > WIDTH || b + j > HEIGHT || b + j < 0 || a + i < 0 || TETile[a + i][b + j] == Tileset.SAND) {
                    continue;
                } else {
                    TETile[a + i][b + j] = Tileset.SAND;
                }
            }
        }
//        for (int j = -1; j < size; j++) {
//            if (b + j > HEIGHT || b + j < 0 || TETile[a + size - 1][b + j] == Tileset.SAND || TETile[a + 2 * size][b + j] == Tileset.SAND) {
//                continue;
//            } else {
//                TETile[a + size - 1][b + j] = Tileset.WALL;
//                TETile[a + 2 * size][b + j] = Tileset.WALL;
//            }
//        }
//        for (int j = 2 * size; j < 3 * size + 1; j++) {
//            if (TETile[size - 1][b + j] == Tileset.SAND || TETile[a + 2 * size][b + j] == Tileset.SAND) {
//                continue;
//            } else {
//                TETile[a + size - 1][b + j] = Tileset.WALL;
//                TETile[a + 2 * size][b + j] = Tileset.WALL;
//            }
//        }
//        for (int i = size; i < 2 * size; i++) {
//            if (TETile[a + i][b - 1] == Tileset.SAND || TETile[a + i][b + 3 * size] == Tileset.SAND) {
//                continue;
//            } else {
//                TETile[a + i][b - 1] = Tileset.WALL;
//                TETile[a + i][b + 3 * size] = Tileset.WALL;
//            }
//        }


    }

    public static void fillWithSquare(TETile[][] TETile, int size, int a, int b) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i + a >= WIDTH || j + b >= HEIGHT || i + a < 0 || j + b < 0) {
                    continue;
                } else {
                    TETile[a + i][b + j] = Tileset.SAND;
                }
            }
        }
    }
}

