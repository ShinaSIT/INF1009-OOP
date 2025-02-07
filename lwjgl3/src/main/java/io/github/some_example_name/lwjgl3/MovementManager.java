package io.github.some_example_name.lwjgl3;

import java.util.ArrayList;
import java.util.List;

public class MovementManager {
    private List<MoveableObjects> movingEntities = new ArrayList<>();
    private boolean physicsEnabled;

    public void applyMovement(Entity entity) {}
    public void updatePosition(Entity entity) {}
    public void applyPhysics(Entity entity) {}
}
