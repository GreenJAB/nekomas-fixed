package net.greenjab.nekomasfixed.util;

public enum SpriteFacing{
    FRONT, BACK, LEFT, RIGHT;

    public String toString(){
        return switch (this){
            case FRONT -> "front";
            case BACK -> "back";
            case LEFT -> "left";
            case RIGHT -> "right";
        };
    }
}
