package it.pasqualini.planetroid.entity;

import java.util.ArrayList;
import java.util.function.Predicate;

import it.pasqualini.util.Vector2;

public abstract class Entity {
    private final Vector2 position = new Vector2(0d, 0d);

    private int radius = 0;



    public double getX() {
        return position.getX();
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Double getVolume() {
        return 4d * Math.PI * Math.pow(radius, 3) / 3;
    }

    public void setX(double x) {
        position.setX(x);
    }

    public double getY() {
        return position.getY();
    }

    public void setY(double y) {
        position.setY(y);
    }


    public void incrementX(double x) {
        position.setX(position.getX() + x);
    }

    public void incrementY(double y) {
        position.setY(position.getY() + y);
    }


//    public boolean collide(ArrayList<? extends Entity> entities) {
//        for (Entity entity: entities) {
//            if(this.collide(entity)) return true;
//        }
//        return false;
//    }

    public boolean collide(ArrayList<? extends Entity> entities) {
        return entities.stream().anyMatch(new Predicate<Entity>() {
            @Override
            public boolean test(Entity entity) {
                return Entity.this.collide(entity);
            }
        });
    }

    public double distance(Entity entity) {
        return position.distance(entity.position);
    }

    public boolean collide(Entity entity) {
        return distance(entity) < (entity.getRadius() + getRadius());
    }


    public Vector2 getPosition() {
        return position;
    }
}
