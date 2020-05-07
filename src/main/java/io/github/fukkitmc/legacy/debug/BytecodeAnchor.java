package io.github.fukkitmc.legacy.debug;

public class BytecodeAnchor {

    private static int iterator = 0;

    public static void next(){
        System.out.println("AT BYTECODE ANCHOR " + iterator);
        iterator++;
    }

}
