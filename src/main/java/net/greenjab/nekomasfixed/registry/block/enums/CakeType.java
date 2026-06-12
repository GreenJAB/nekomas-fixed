package net.greenjab.nekomasfixed.registry.block.enums;

public enum CakeType {
    SWEETBERRY_CAKE("sweetberry_cake"),
    COOKIE_CAKE("cookie_cake"),
    CHOCOLATE_CAKE("chocolate_cake"),
    PAN_CAKE("pan_cake"), // plz let it be pan_cake for the sake of following the same naming scheme
    BEETROOT_CAKE("beetroot_cake"),
    GLOWBERRY_CAKE("glowberry_cake")
    ;

    public String id;
    CakeType(String id){
        this.id = id;
    }

    @Override
    public String toString(){
        return this.id;
    }

}
