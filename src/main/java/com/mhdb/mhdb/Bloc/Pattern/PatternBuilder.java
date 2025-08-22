package com.mhdb.mhdb.Bloc.Pattern;

import java.util.Random;

public class PatternBuilder {

    /**
     * Builds Pattern for MFA.
     * @return
     */
    public static Pattern buildPattern() {
        Shape shape = Shape.values()[new Random().nextInt(Shape.values().length-1)];

        String pattern = switch (shape) {
            case SQUARE -> "#####\n#####\n#####\n#####\n#####";
            case TRIANGLE -> "  #  \n ### \n#####";
            case CIRCLE -> "  ***  \n *   * \n*     *\n *   * \n  ***  ";
            default -> "";
        };

        return new Pattern(shape.name(), pattern);
    }
}
