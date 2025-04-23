package org.frgm.util;

public class Colores {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String ORANGE = "\u001b[31;1m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void main(String[] args) {
        System.out.println(RED+"HELLO");
        System.out.println(GREEN+"HELLO");
        System.out.println(YELLOW+"HELLO");
        System.out.println(BLUE+"HELLO");
        System.out.println(PURPLE+"HELLO");
        System.out.println(CYAN+"HELLO");
        System.out.println(WHITE+"HELLO");

        System.out.println(ANSI_WHITE_BACKGROUND + "HELLO");

        int x=9, y=99;
                   //true:false
        int mayor=(x>y)?x:y;
        System.out.println(mayor);
    }
}
