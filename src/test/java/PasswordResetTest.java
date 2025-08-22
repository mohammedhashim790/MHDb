import com.mhdb.mhdb.Bloc.Loggers.AppLogger;
import com.mhdb.mhdb.Bloc.PasswordReset.PasswordReset;
import com.mhdb.mhdb.Bloc.PasswordReset.PasswordResetBuilder;

public class PasswordResetTest {

    private static final AppLogger logger = AppLogger.getAppLogger();

    public static void main(String[] args) {
        testPasswordResetValidity();
        testTokenGeneration();
    }

    private static void testPasswordResetValidity() {
        PasswordReset reset = new PasswordReset("testUser", "sampleToken");
        logger.logInfo("Is token valid? " + reset.isValid());
        logger.logInfo(reset.isValid() ? "testPasswordResetValidity PASSED" : "testPasswordResetValidity FAILED");
    }

    private static void testTokenGeneration() {
        String token = PasswordResetBuilder.generateToken();
        logger.logInfo("Generated Token: " + token);
        logger.logInfo("Token Length: " + token.length());
        logger.logInfo(token.length() > 0 ? "testTokenGeneration PASSED" : "testTokenGeneration FAILED");
    }

}
