package com.game;

import java.util.ArrayList;
import java.util.List;

public class MovementManager {
    private List<MoveableObjects> movingEntities = new ArrayList<>();
    private boolean physicsEnabled;

    public void applyMovement(Entity entity) {}
    public void updatePosition(Entity entity) {}
    public void applyPhysics(Entity entity) {}
}
