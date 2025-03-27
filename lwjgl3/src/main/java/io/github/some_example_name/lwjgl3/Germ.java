package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.awt.Point;
import java.util.*;

public class Germ extends MoveableObjects implements Collidable {
    private Texture germTexture;
    private Random random;
    private int moveCooldown;
    private int lastDirection = -1;
    private Set<Point> visited = new HashSet<>();


    public Germ(Board board, EntityManager entityManager, MovementManager movementManager, CollisionManager collisionManager) {
        super(board, entityManager, 0, 0, movementManager); // temp grid
        Point spawn = getRandomWalkablePosition(board);
        this.setGridX(spawn.x);
        this.setGridY(spawn.y);
        this.updatePixelPosition();
        init();
    }

    private void init() {
        this.entityTags.add("moveable");
        this.random = new Random();
        this.moveCooldown = 30 + random.nextInt(30);
        FileHandle file = Gdx.files.internal("Germs.png");
        this.germTexture = new Texture(file);
    }

    private Point getRandomWalkablePosition(Board board) {
        List<Point> valid = new ArrayList<>();
        char[][] maze = board.getMazeLayoutCopy();

        Point playerPos = new Point(1, 1); // Change if your player spawns elsewhere
        int minDistance = 8; // minimum tiles away from player

        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                if (maze[row][col] == ' ') {
                    Point p = new Point(col, row);
                    if (p.distance(playerPos) >= minDistance) {
                        valid.add(p);
                    }
                }
            }
        }

        Collections.shuffle(valid);
        return valid.isEmpty() ? new Point(maze[0].length - 2, maze.length - 2) : valid.get(0);
    }


    @Override
    public void render(SpriteBatch batch) {
        if (germTexture == null) return;

        float size = board.getTileSize();
        batch.end(); batch.begin(); // Fix render glitch
        batch.draw(germTexture, getX(), getY(), size, size);
        batch.end(); batch.begin();
    }

    public void moveSmartly() {
        if (moveCooldown > 0) {
            moveCooldown--;
            return;
        }

        List<Integer> validDirections = getValidDirections();
        if (validDirections.isEmpty()) {
            moveCooldown = 20;
            return;
        }

        int direction = chooseBestDirection(validDirections);
        moveInDirection(direction);
        lastDirection = direction;
        moveCooldown = 30 + random.nextInt(30);
    }

    private List<Integer> getValidDirections() {
        List<Integer> dirs = new ArrayList<>();
        char[][] maze = board.getMazeLayout();
        int x = getGridX(), y = getGridY();

        if (y > 0 && isWalkable(maze[y - 1][x])) dirs.add(0);
        if (x < maze[0].length - 1 && isWalkable(maze[y][x + 1])) dirs.add(1);
        if (y < maze.length - 1 && isWalkable(maze[y + 1][x])) dirs.add(2);
        if (x > 0 && isWalkable(maze[y][x - 1])) dirs.add(3);

        Collections.shuffle(dirs);
        return dirs;
    }

    private int chooseBestDirection(List<Integer> validDirs) {
        int x = getGridX();
        int y = getGridY();

        for (int dir : validDirs) {
            int nx = x + (dir == 1 ? 1 : dir == 3 ? -1 : 0);
            int ny = y + (dir == 2 ? 1 : dir == 0 ? -1 : 0);
            if (!visited.contains(new Point(nx, ny))) return dir;
        }

        return validDirs.get(random.nextInt(validDirs.size()));
    }

    private void moveInDirection(int dir) {
        int dx = 0, dy = 0;

        switch (dir) {
            case 0: dy = -1; break;
            case 1: dx = 1; break;
            case 2: dy = 1; break;
            case 3: dx = -1; break;
        }

        int nx = getGridX() + dx;
        int ny = getGridY() + dy;
        visited.add(new Point(nx, ny));

        movementManager.applyMovement(this, dx, dy, true);
    }

    private boolean isWalkable(char tile) {
        return tile == ' ' || tile == 'f' || tile == '.' || tile == 'p';
    }

    @Override public boolean isSolid() { return true; }
    @Override public int getGridX() { return super.getGridX(); }
    @Override public int getGridY() { return super.getGridY(); }
    @Override public void setGridX(int gridX) { super.setGridX(gridX); }
    @Override public void setGridY(int gridY) { super.setGridY(gridY); }

    @Override
    public boolean detectCollision(Collidable other) {
        return this.getGridX() == other.getGridX() && this.getGridY() == other.getGridY();
    }

    @Override
    public void dispose() {
        germTexture.dispose();
    }
}
