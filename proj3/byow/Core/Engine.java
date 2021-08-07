package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
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
        ArrayList<Room> rooms = new ArrayList<>();
        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        int mainRoomX = WIDTH / 2;
        int mainRoomY = HEIGHT / 2;
        Room mainRoom = new Room(tiles, mainRoomX, mainRoomY, 4);
        tiles[mainRoomX+1][mainRoomY+1]=Tileset.AVATAR;
        int roomCount = RANDOM.nextInt(30);
        while (roomCount < 25) {
            roomCount = RANDOM.nextInt(30);
        }
        System.out.println("roomCount" + roomCount);
        for (int i = 0; i < roomCount; i++) {
            int roomSize = RANDOM.nextInt(7);
            if (roomSize < 3) {
                roomSize = RANDOM.nextInt(7);
            }
            System.out.println("roomSize" + roomSize);
            int a = RANDOM.nextInt(WIDTH);
            int b = RANDOM.nextInt(HEIGHT);
            System.out.println(a + "," + b);
            if (isInScope(a, b)) {
                Room temp = new Room(tiles, a, b, roomSize);
                if (roomSize != 0) {
                    addRoad(tiles, temp, mainRoom);
                    rooms.add(temp);
                }
            }
        }
        addWalls(tiles);
        mainRoom.changeElement(tiles, Tileset.FLOWER);
        addGrass(tiles);
        ter.renderFrame(tiles);
        return tiles;
    }

    public void addWalls(TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (tiles[x][y] == Tileset.SAND || tiles[x][y] == Tileset.FLOOR) {
                    addWall(tiles, x, y + 1);
                    addWall(tiles, x, y - 1);
                    addWall(tiles, x - 1, y);
                    addWall(tiles, x + 1, y);
                    addWall(tiles, x - 1, y - 1);
                    addWall(tiles, x - 1, y + 1);
                    addWall(tiles, x + 1, y - 1);
                    addWall(tiles, x + 1, y + 1);
                }
                if (tiles[x][y] != Tileset.NOTHING) {
                    if (x == 0 || x == WIDTH - 1 || y == 0 || y == HEIGHT - 1) {
                        tiles[x][y] = Tileset.WALL;
                    }
                }
            }
        }
    }

    public void addWall(TETile[][] tiles, int x, int y) {
        if (x >= WIDTH || y >= HEIGHT || x < 0 || y < 0) {
            return;
        }
        if (tiles[x][y] == Tileset.NOTHING) {
            tiles[x][y] = Tileset.WALL;
        }
    }

    public void addGrass(TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (tiles[x][y] == Tileset.NOTHING) {
                    tiles[x][y] = Tileset.GRASS;
                }
            }
        }
    }

    public void addRoad(TETile[][] tiles, Room a, Room main) {
        if (Math.min(a.x, main.x) == a.x) {
            if (Math.min(a.y, main.y) == a.y) {
                for (int x = a.x + a.size; x < main.x + RANDOM.nextInt(2); x += 1) {
                    if (isInScope(x, a.y) && tiles[x][a.y] != Tileset.SAND) {
                        tiles[x][a.y] = Tileset.SAND;
                    }
                }
                for (int y = a.y; y < main.y; y += 1) {
                    if (isInScope(main.x, y) && tiles[main.x][y] != Tileset.SAND) {
                        tiles[main.x][y] = Tileset.SAND;
                    }
                }
            } else {
                for (int x = a.x + a.size; x < main.x; x += 1) {
                    if (isInScope(x, a.y) && tiles[x][a.y] != Tileset.SAND) {
                        tiles[x][a.y] = Tileset.SAND;
                    }
                }
                for (int y = a.y; y > main.y; y -= 1) {
                    if (isInScope(main.x, y) && tiles[main.x][y] != Tileset.SAND) {
                        tiles[main.x][y] = Tileset.SAND;
                    }
                }
            }
        } else {
            if (Math.min(a.y, main.y) == a.y) {
                for (int x = main.x + main.size; x < a.x; x += 1) {
                    if (isInScope(x, main.y) && tiles[x][main.y] != Tileset.SAND) {
                        tiles[x][main.y] = Tileset.SAND;
                    }
                }
                for (int y = main.y; y > a.y + a.size - 1; y -= 1) {
                    if (isInScope(a.x, y) && tiles[a.x][y] != Tileset.SAND) {
                        tiles[a.x][y] = Tileset.SAND;
                    }
                }
            } else {
                for (int x = main.x + main.size; x < a.x; x += 1) {
                    if (isInScope(x, main.y) && tiles[x][main.y] != Tileset.SAND) {
                        tiles[x][main.y] = Tileset.SAND;
                    }
                }
                for (int y = main.y; y < a.y; y += 1) {
                    if (isInScope(a.x, y) && tiles[a.x][y] != Tileset.SAND) {
                        tiles[a.x][y] = Tileset.SAND;
                    }
                }
            }
        }
    }

    public boolean isInScope(int x, int y) {
        if (x < WIDTH && y < HEIGHT && y > 0 && y > 0) {
            return true;
        }
        return false;
    }

    private class Room {
        private int size;
        private int x;
        private int y;

        Room(TETile[][] tiles, int a, int b, int c) {
            x = a;
            y = b;
            size = c;
            for (int i = 0; i < size; i += 1) {
                for (int j = 0; j < size; j += 1) {
                    if (a + i < WIDTH && b + j < HEIGHT && b + j > 0 && a + i > 0) {
                        tiles[i + a][j + b] = Tileset.FLOOR;
                    }
                }
            }
        }

        public void changeElement(TETile[][] tiles, TETile a) {
            for (int i = 0; i < size; i += 1) {
                for (int j = 0; j < size; j += 1) {
                    if (x + i < WIDTH && y + j < HEIGHT && y + j > 0 && x + i > 0) {
                        tiles[x + i][j + y] = a;
                    }
                }
            }
        }
    }


}

