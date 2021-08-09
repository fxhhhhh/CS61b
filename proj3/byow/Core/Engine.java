package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    public HashMap<String, String> existWorld = new HashMap<>();



    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        String usrInput = new String();
        String seed = "121313";
        char key;
        normalStartGame(WIDTH, HEIGHT, Long.parseLong(seed));
        int count = 0;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                count += 1;
                key = StdDraw.nextKeyTyped();
                System.out.println(key);
                String input;
                if (key == 'n') {
                    input = "n" + seed + "s";
                    interactWithInputString(input);
                }
                if (key == 'l') {
                    input = "l";
                    interactWithInputString(input);
                }
                if (key == 'q') {
                    System.exit(0);
                }
                if (key == 'c') {
                    if (count % 2 == 0) {
                        normalStartGame(WIDTH, HEIGHT, Long.parseLong(seed));
                    } else {
                        chineseStartGame(WIDTH, HEIGHT, Long.parseLong(seed));
                    }
                }
            }
        }
    }

    public void normalStartGame(int width, int height, long seed) {
        StdDraw.setCanvasSize(16 * width, 16 * height);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH / 2, HEIGHT * 6 / 8, "Brogue");
        Font fontMiddle = new Font("Monaco", Font.PLAIN, 20);
        StdDraw.setFont(fontMiddle);
        StdDraw.text(WIDTH / 2, HEIGHT * 8 / 16, "NEW WORLD(N)");
        StdDraw.text(WIDTH / 2, HEIGHT * 7 / 16, "LOAD WORLD(L)");
        StdDraw.text(WIDTH / 2, HEIGHT * 6 / 16, "CHANGE LANGUAGE(C)");
        StdDraw.text(WIDTH / 2, HEIGHT * 5 / 16, "QUIT(Q)");
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT + 2);
        Font fontSmall = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(fontSmall);
        StdDraw.textLeft(WIDTH - 10, HEIGHT + 1, "@Nintendo");
        StdDraw.show();
    }

    public void chineseStartGame(int width, int height, long seed) {
        StdDraw.setCanvasSize(16 * width, 16 * height);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH / 2, HEIGHT * 6 / 8, "Brogue");
        Font fontMiddle = new Font("Monaco", Font.PLAIN, 20);
        StdDraw.setFont(fontMiddle);
        StdDraw.text(WIDTH / 2, HEIGHT * 8 / 16, "新建游戏(N)");
        StdDraw.text(WIDTH / 2, HEIGHT * 7 / 16, "下载存档(L)");
        StdDraw.text(WIDTH / 2, HEIGHT * 6 / 16, "改变语言(C)");
        StdDraw.text(WIDTH / 2, HEIGHT * 5 / 16, "退出(Q)");
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT + 2);
        Font fontSmall = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(fontSmall);
        StdDraw.textLeft(WIDTH - 10, HEIGHT + 1, "@Nintendo");
        StdDraw.show();
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     *
     * <p>
     * <p>
     * <p>
     *
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     *
     *
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * <p>
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
        int stop = 0;
        boolean flagNew = false;
        TETile[][] finalWorldFrame = null;
        if (input.charAt(0) == 'n') {
            flagNew = true;
        }
        if (flagNew) {
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == 's') {
                    stop = i;
                    break;
                }
            }
            seed = input.substring(1, stop);
            String movement = input.substring(stop + 1, input.length());
            finalWorldFrame = createWorld(seed, movement);
            return finalWorldFrame;
        } else {
            Charset cs = Charset.forName("US-ASCII");
            BufferedReader r = null;
            try {
                String movement = input.substring(1, input.length());
                r = Files.newBufferedReader(Paths.get("saving.txt"), cs);
                String addedMove;
                seed = r.readLine();
                addedMove = r.readLine();
                finalWorldFrame = createWorld(seed, addedMove + movement);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return finalWorldFrame;
        }
    }


    public TETile[][] createWorld(String seed, String movement) {

        // set up main room, 4x4
        int mainRoomX = WIDTH / 2;
        int mainRoomY = HEIGHT / 2;

        ter.initialize(WIDTH, HEIGHT + 2);

//        ter.renderFrame(tiles);
        while (true) {
            boolean moveFlag = false;
            TETile[][] tiles = generateRoom(seed, movement, mainRoomX, mainRoomY);
            double mouseX = StdDraw.mouseX();
            double mouseY = StdDraw.mouseY();
            ter.renderFrame(tiles);
            createWindows(tiles);
            while (true) {
                if (StdDraw.hasNextKeyTyped()) {
                    Character key = StdDraw.nextKeyTyped();
                    movement += key;
                    moveFlag = true;
                    break;
                }
                if (StdDraw.mouseX() != mouseX || StdDraw.mouseY() != mouseY) {
                    break;
                }
            }
//            StdDraw.clear(Color.BLACK);
//            while (!moveFlag || StdDraw.mouseX() == mouseX && StdDraw.mouseY() == mouseY) {
//                StdDraw.pause(10);
//                System.out.println("still in the loop ");
//            }


        }
    }

    private TETile[][] generateRoom(String seed, String movement, int mainRoomX, int mainRoomY) {
        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        RANDOM = new Random(seed.hashCode());
        ArrayList<Room> rooms = new ArrayList();
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        Room mainRoom = new Room(tiles, mainRoomX, mainRoomY, 4);


        // always have 25-30 rooms
        int roomCount = RandomUtils.uniform(RANDOM, 25, 30);

        for (int i = 0; i < roomCount; i++) {
            // size is always 3-7
            int roomSize = RandomUtils.uniform(RANDOM, 3, 7);


            int a = RandomUtils.uniform(RANDOM, 1, WIDTH);
            int b = RandomUtils.uniform(RANDOM, 1, HEIGHT);


            Room temp = new Room(tiles, a, b, roomSize);
            if (roomSize != 0) { // why roomsize could be zero?
                addRoad(tiles, temp, mainRoom);
                rooms.add(temp);
            }
        }
        addWalls(tiles);
//            mainRoom.changeElement(tiles, Tileset.FLOWER);

        tiles[mainRoomX][mainRoomY] = Tileset.AVATAR;
        move(tiles, movement, seed);


        addGrass(tiles);
        return tiles;
    }

    public void move(TETile[][] tiles, String movement, String seed) {
        boolean existQFlag = false;
        String temp = "";
        for (int i = 0; i < movement.length(); i++) {
            System.out.println(movement);
            if (movement.charAt(i) != 'q' && movement.charAt(i) != 'Q') {
                temp += movement.charAt(i);
                walk(tiles, movement.charAt(i));
            } else {
                System.out.println(movement.charAt(i));
                System.out.println(movement.charAt(i - 1));
                if (movement.charAt(i - 1) == ':') {
                    existQFlag = true;
                    break;
                }
            }
        }

        if (existQFlag) {
            File file = new File("saving.txt");
            try {
                BufferedWriter output = new BufferedWriter(new FileWriter(file));
                output.write(seed + "\n");
                output.write(temp);
                output.close();
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void walk(TETile[][] tiles, Character a) {
        int peopleX = 0;
        int peopleY = 0;
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (tiles[x][y] == Tileset.AVATAR) {
                    peopleX = x;
                    peopleY = y;
                    break;
                }
            }
        }

        if (a == 'd' || a == 'D') {
            tiles[peopleX][peopleY] = Tileset.FLOWER;
            peopleX += 1;
            System.out.println(123);
            if (isInScope(peopleX, peopleY) && tiles[peopleX][peopleY] != Tileset.WALL) {
                System.out.println(123);
                tiles[peopleX][peopleY] = Tileset.AVATAR;
            } else {
                peopleX -= 1;
                tiles[peopleX][peopleY] = Tileset.AVATAR;
            }
        }
        if (a == 'w' || a == 'W') {
            tiles[peopleX][peopleY] = Tileset.FLOWER;
            peopleY += 1;

            if (isInScope(peopleX, peopleY) && tiles[peopleX][peopleY] != Tileset.WALL) {
                tiles[peopleX][peopleY] = Tileset.AVATAR;
            } else {
                peopleY -= 1;
                tiles[peopleX][peopleY] = Tileset.AVATAR;
            }
        }
        if (a == 's' || a == 'S') {
            tiles[peopleX][peopleY] = Tileset.FLOWER;
            peopleY -= 1;
            if (isInScope(peopleX, peopleY) && tiles[peopleX][peopleY] != Tileset.WALL) {
                tiles[peopleX][peopleY] = Tileset.AVATAR;
            } else {
                peopleY += 1;
                tiles[peopleX][peopleY] = Tileset.AVATAR;
            }
        }
        if (a == 'a' || a == 'A') {
            tiles[peopleX][peopleY] = Tileset.FLOWER;
            peopleX -= 1;
            if (isInScope(peopleX, peopleY) && tiles[peopleX][peopleY] != Tileset.WALL) {
                tiles[peopleX][peopleY] = Tileset.AVATAR;
            } else {
                peopleX += 1;
                tiles[peopleX][peopleY] = Tileset.AVATAR;
            }
        }
        if (a == 'b' || a == 'B') {
            interactWithKeyboard();
        }
    }

    private void createWindows(TETile[][] tiles) {
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT + 2);
        Font fontSmall = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(fontSmall);
        StdDraw.line(0, HEIGHT, WIDTH, HEIGHT);
        StdDraw.textLeft(0, HEIGHT + 1, "X:" + StdDraw.mouseX());
        StdDraw.textLeft(10, HEIGHT + 1, "Y:" + StdDraw.mouseY());
        StdDraw.textLeft(20, HEIGHT + 1, "This is :" + getObject(tiles, (int) StdDraw.mouseX(), (int) StdDraw.mouseY()));
        StdDraw.textLeft(WIDTH - 10, HEIGHT + 1, "@Nintendo");

        StdDraw.show();
    }


    public String getObject(TETile[][] tiles, int x, int y) {
        if (x >= WIDTH || y >= HEIGHT || x < 0 || y < 0) {
            return "something weird";
        } else {
            TETile tileObject = tiles[x][y];
            if (Tileset.FLOWER.equals(tileObject)) {
                return "flower";
            } else if (Tileset.WALL.equals(tileObject)) {
                return "wall";
            } else if (Tileset.SAND.equals(tileObject)) {
                return "sand";
            } else if (Tileset.GRASS.equals(tileObject)) {
                return "grass";
            } else if (Tileset.FLOOR.equals(tileObject)) {
                return "floor";
            }
            return "something weird";
        }
    }


    // add walls around rooms
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
                    if (isInScope(x, a.y) && tiles[x][a.y] != Tileset.FLOOR) {
                        tiles[x][a.y] = Tileset.SAND;
                    }
                }
                for (int y = a.y; y < main.y; y += 1) {
                    if (isInScope(main.x, y) && tiles[main.x][y] != Tileset.FLOOR) {
                        tiles[main.x][y] = Tileset.SAND;
                    }
                }
            } else {
                for (int x = a.x + a.size; x < main.x; x += 1) {
                    if (isInScope(x, a.y) && tiles[x][a.y] != Tileset.FLOOR) {
                        tiles[x][a.y] = Tileset.SAND;
                    }
                }
                for (int y = a.y; y > main.y; y -= 1) {
                    if (isInScope(main.x, y) && tiles[main.x][y] != Tileset.FLOOR) {
                        tiles[main.x][y] = Tileset.SAND;
                    }
                }
            }
        } else {
            if (Math.min(a.y, main.y) == a.y) {
                for (int x = main.x + main.size; x < a.x; x += 1) {
                    if (isInScope(x, main.y) && tiles[x][main.y] != Tileset.FLOOR) {
                        tiles[x][main.y] = Tileset.SAND;
                    }
                }
                for (int y = main.y; y > a.y + a.size - 1; y -= 1) {
                    if (isInScope(a.x, y) && tiles[a.x][y] != Tileset.FLOOR) {
                        tiles[a.x][y] = Tileset.SAND;
                    }
                }
            } else {
                for (int x = main.x + main.size; x < a.x; x += 1) {
                    if (isInScope(x, main.y) && tiles[x][main.y] != Tileset.FLOOR) {
                        tiles[x][main.y] = Tileset.SAND;
                    }
                }
                for (int y = main.y; y < a.y; y += 1) {
                    if (isInScope(a.x, y) && tiles[a.x][y] != Tileset.FLOOR) {
                        tiles[a.x][y] = Tileset.SAND;
                    }
                }
            }
        }
    }

    public boolean isInScope(int x, int y) {
        if (x < WIDTH && y < HEIGHT && x > 0 && y > 0) {
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
                    if (isInScope(a + i, b + j)) {
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
