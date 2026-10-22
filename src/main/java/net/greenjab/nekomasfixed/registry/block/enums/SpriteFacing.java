package net.greenjab.nekomasfixed.registry.block.enums;

public enum SpriteFacing {
        BACK(0), LEFT(1), RIGHT(2), FRONT(3);
        public int ordinal;
        SpriteFacing(int ord) { this.ordinal = ord; }

    public String toString(){
        return switch (this){
            case FRONT -> "front";
            case BACK -> "back";
            case LEFT -> "left";
            case RIGHT -> "right";
        };
    }
    }