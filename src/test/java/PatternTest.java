import com.mhdb.mhdb.Bloc.Loggers.AppLogger;
import com.mhdb.mhdb.Bloc.Pattern.Pattern;
import com.mhdb.mhdb.Bloc.Pattern.Shape;

public class PatternTest {

    private static final AppLogger logger = AppLogger.getAppLogger();

    public static void main(String[] args) {

        testPatternValidation();
        testPatternInValidation();
    }

    private static void testPatternValidation() {
        String patternString = "#####\n#####\n#####\n#####\n#####";
        Pattern pattern = new Pattern(Shape.SQUARE.name(), patternString);
        logger.logInfo("Pattern: " + pattern.validate(Shape.SQUARE.name()) + " Passed");
    }

    private static void testPatternInValidation() {
        String patternString = "#####\n#####\n#####\n#####\n#####";
        Pattern pattern = new Pattern(Shape.SQUARE.name(), patternString);

        logger.logInfo("Failure Pattern: " + pattern.validate(Shape.TRIANGLE.name()) + " Passed");
    }


}
