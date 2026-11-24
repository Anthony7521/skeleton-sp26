import edu.princeton.cs.algs4.StdRandom;

import java.awt.*;
import java.util.Map;

public class Particle {
    public ParticleFlavor flavor;
    public int lifespan;

    public static final int PLANT_LIFESPAN = 150;
    public static final int FLOWER_LIFESPAN = 75;
    public static final int FIRE_LIFESPAN = 10;
    public static final Map<ParticleFlavor, Integer> LIFESPANS =
            Map.of(ParticleFlavor.FLOWER, FLOWER_LIFESPAN,
                   ParticleFlavor.PLANT, PLANT_LIFESPAN,
                   ParticleFlavor.FIRE, FIRE_LIFESPAN);

    public Particle(ParticleFlavor flavor) {
        this.flavor = flavor;
        if (this.flavor == ParticleFlavor.FLOWER || this.flavor == ParticleFlavor.PLANT || this.flavor == ParticleFlavor.FIRE){
            this.lifespan = LIFESPANS.get(this.flavor);
        }else{
            this.lifespan = -1;
        }
    }

    public Color color() {
        if (flavor == ParticleFlavor.EMPTY) {
            return Color.BLACK;
        }
        if (flavor == ParticleFlavor.SAND) {
            return Color.YELLOW;
        }
        if (flavor == ParticleFlavor.WATER) {
            return Color.BLUE;
        }
        if (flavor == ParticleFlavor.FOUNTAIN) {
            return Color.CYAN;
        }
        if (flavor == ParticleFlavor.PLANT) {
            double ratio = (double) Math.max(0, Math.min(lifespan, PLANT_LIFESPAN)) / PLANT_LIFESPAN;
            int g = 120 + (int) Math.round((255 - 120) * ratio);
            return new Color(0, g, 0);
        }
        if (flavor == ParticleFlavor.FIRE) {
            double ratio = (double) Math.max(0, Math.min(lifespan, FIRE_LIFESPAN)) / FIRE_LIFESPAN;
            int r = (int) Math.round(255 * ratio);
            return new Color(r, 0, 0);
        }
        if (flavor == ParticleFlavor.FLOWER) {
            double ratio = (double) Math.max(0, Math.min(lifespan, FLOWER_LIFESPAN)) / FLOWER_LIFESPAN;
            int r = 120 + (int) Math.round((255 - 120) * ratio);
            int g = 70 + (int) Math.round((141 - 70) * ratio);
            int b = 80 + (int) Math.round((161 - 80) * ratio);
            return new Color(r, g, b);
        }
        else return Color.GRAY;
    }

    public void moveInto(Particle other) {
        other.flavor = this.flavor;
        other.lifespan = this.lifespan;

        this.flavor = ParticleFlavor.EMPTY;
        this.lifespan = -1;
    }

    public void fall(Map<Direction, Particle> neighbors) {
        Particle p = neighbors.get(Direction.DOWN);
        if(p.flavor == ParticleFlavor.EMPTY){
            this.moveInto(p);
        }else{
            return;
        }
    }

    public void flow(Map<Direction, Particle> neighbors) {
        int x = StdRandom.uniformInt(3);
        Particle left = neighbors.get(Direction.LEFT);
        Particle right = neighbors.get(Direction.RIGHT);
        if(x == 0) {
            return;
        }if(x == 1 && left.flavor == ParticleFlavor.EMPTY){
            this.moveInto(left);
        }if(x == 2 && right.flavor == ParticleFlavor.EMPTY){
            this.moveInto((right));
        }
    }

    public void grow(Map<Direction, Particle> neighbors) {
        int i = StdRandom.uniformInt(10);
        Particle up = neighbors.get(Direction.UP);
        Particle left = neighbors.get(Direction.LEFT);
        Particle right = neighbors.get(Direction.RIGHT);
        if(i == 0 && up.flavor == ParticleFlavor.EMPTY){
            up.flavor = this.flavor;
            up.lifespan = LIFESPANS.get(this.flavor);
        }
        if(i == 1 && left.flavor == ParticleFlavor.EMPTY){
            left.flavor = this.flavor;
            left.lifespan = LIFESPANS.get(this.flavor);
        }
        if(i == 2 && right.flavor == ParticleFlavor.EMPTY){
            right.flavor = this.flavor;
            right.lifespan = LIFESPANS.get(this.flavor);
        }
    }

    public void burn(Map<Direction, Particle> neighbors) {
        Particle down = neighbors.get(Direction.DOWN);
        Particle up = neighbors.get(Direction.UP);
        Particle left = neighbors.get(Direction.LEFT);
        Particle right = neighbors.get(Direction.RIGHT);
        if(up.flavor == ParticleFlavor.FLOWER || up.flavor == ParticleFlavor.PLANT){
            int i = StdRandom.uniformInt(10);
            if(i == 0 || i == 1 || i == 2 || i == 3){
                up.flavor = ParticleFlavor.FIRE;
                up.lifespan = FIRE_LIFESPAN;
            }
        }
        if(down.flavor == ParticleFlavor.FLOWER || down.flavor == ParticleFlavor.PLANT){
            int i = StdRandom.uniformInt(10);
            if(i == 0 || i == 1 || i == 2 || i == 3){
                down.flavor = ParticleFlavor.FIRE;
                down.lifespan = FIRE_LIFESPAN;
            }
        }
        if(left.flavor == ParticleFlavor.FLOWER || left.flavor == ParticleFlavor.PLANT){
            int i = StdRandom.uniformInt(10);
            if(i == 0 || i == 1 || i == 2 || i == 3){
                left.flavor = ParticleFlavor.FIRE;
                left.lifespan = FIRE_LIFESPAN;
            }
        }
        if(right.flavor == ParticleFlavor.FLOWER || right.flavor == ParticleFlavor.PLANT){
            int i = StdRandom.uniformInt(10);
            if(i == 0 || i == 1 || i == 2 || i == 3){
                right.flavor = ParticleFlavor.FIRE;
                right.lifespan = FIRE_LIFESPAN;
            }
        }


    }

    public void action(Map<Direction, Particle> neighbors) {
        if (this.flavor == ParticleFlavor.EMPTY) {
            return;
        } if(this.flavor != ParticleFlavor.BARRIER) {
            this.fall(neighbors);
        } if(this.flavor == ParticleFlavor.WATER) {
            this.flow(neighbors);
        } if(this.flavor == ParticleFlavor.FLOWER || this.flavor == ParticleFlavor.PLANT){
            this.grow(neighbors);
        } if(this.flavor == ParticleFlavor.FIRE){
            this.burn(neighbors);
        }
    }

    public void decrementLifespan(){
        if(this.lifespan > 0){
            this.lifespan--;
        }
        if(this.lifespan == 0){
            this.flavor = ParticleFlavor.EMPTY;
            this.lifespan = -1;
        }
    }
}