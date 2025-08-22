package com.mhdb.mhdb.Controllers.AuthenticationStorageController;

import com.mhdb.mhdb.Bloc.Exceptions.InvalidPassword;
import com.mhdb.mhdb.Bloc.Exceptions.TokenExpiredException;
import com.mhdb.mhdb.Bloc.Exceptions.UserExistsException;
import com.mhdb.mhdb.Bloc.Exceptions.UserNotFoundException;
import com.mhdb.mhdb.Bloc.PasswordReset.PasswordReset;
import com.mhdb.mhdb.Bloc.PasswordReset.PasswordResetBuilder;
import com.mhdb.mhdb.Bloc.Pattern.Pattern;
import com.mhdb.mhdb.Bloc.Pattern.PatternBuilder;
import com.mhdb.mhdb.Bloc.Utils.IPSimulator;
import com.mhdb.mhdb.Controllers.StorageController;
import com.mhdb.mhdb.Models.Authentication.Authentication;

import java.io.IOException;
import java.util.*;

public class AuthenticationStorageController extends StorageController<Authentication> {

    @Override
    protected Class<Authentication> getClassType() {
        return Authentication.class;
    }

    public AuthenticationStorageController() {
        super();
    }


    /**
     * Authenticates a user based on the provided authentication details.
     *
     * @param authentication The {@code Authentication} object containing username and password.
     * @throws UserNotFoundException If the username does not exist in the system.
     * @throws InvalidPassword       If the password provided does not match the stored password.
     * @throws IOException           If an input or output exception occurs.
     */
    public void authenticate(Authentication authentication) throws UserNotFoundException, InvalidPassword, IOException {
        this.logger.logInfo("Attempting to authenticate user " + authentication.username);
        List<Authentication> data = this.listAll();

        if (data.stream().noneMatch((item) -> item.username.equals(authentication.username))) {
            throw new UserNotFoundException();
        }
        if (data.stream().anyMatch((item) -> item.username.equals(authentication.username) && !item.password.equals(authentication.password))) {
            throw new InvalidPassword();
        }
        if (data.stream().anyMatch((item) -> item.equals(authentication))) {
            Authentication authDat = data.stream().filter((item) -> item.equals(authentication)).findFirst().get();
        }
    }

    /**
     * Creates a new user with the provided authentication details.
     *
     * @param authentication The {@code Authentication} object containing the username and password.
     * @throws IOException         If an input or output exception occurs.
     * @throws UserExistsException If a user with the same username and password already exists.
     */
    public void create(Authentication authentication) throws IOException {
        this.logger.logInfo("Attempting to create user " + authentication.username);
        List<Authentication> data = this.listAll();
        if (data.stream().anyMatch((item) -> item.username == authentication.username && item.password == authentication.password)) {
            this.logger.logError("User " + authentication.username + " already exists");
            throw new UserExistsException();
        }
        this.insert(authentication);
        this.logger.logInfo("User " + authentication.username + " created");
    }

    /**
     * Updates an existing user's authentication details.
     *
     * @param authentication The {@code Authentication} object containing the updated username and password.
     * @throws IOException If an input or output exception occurs during the update process.
     */
    public void update(Authentication authentication) throws IOException {
        this.logger.logInfo("Attempting to update user " + authentication.username);
        List<Authentication> data = this.listAll();
        data.removeIf((item) -> item.username.equals(authentication.username));
        data.add(authentication);
        this.clear();
        this.insert(data.stream().map((item) -> String.join(",", item.index, item.username, item.password)).toList());
        this.logger.logInfo("User " + authentication.username + " created");
    }

    /**
     * Performs Multi-Factor Authentication (MFA) validation.
     * <p>
     * This method prompts the user to identify a shape based on a generated pattern.
     * The user must enter a valid pattern to pass the MFA validation.
     * Logs the validation process and retries until successful.
     */
    public void mfa() {
        this.logger.logInfo("Validating MFA");
        Pattern pattern = PatternBuilder.buildPattern();
        this.logger.logInfo("Validating with Pattern: " + pattern);
        System.out.println("Identify the shape.");
        System.out.println(pattern.pattern);
        while (true) {
            String inputPattern = new Scanner(System.in).nextLine();
            if (pattern.validate(inputPattern)) {
                this.logger.logInfo("MFA Successfully validated");
                this.logger.logInfo(String.format("User successfully logged in from %s", IPSimulator.RandomIPSimulator()));
                break;
            }
            System.out.println("Invalid MFA. Please try again.");
            System.out.println("Identify the above shape.");
        }
    }


    /**
     * Initiates a password reset for the user.
     * <p>
     * The method prompts user to enter the username and answer security questions
     * for validation. If the security validation is successful, the user is allowed
     * to set a new password.
     *
     * @throws TokenExpiredException If the password reset token has expired.
     */
    public void requestResetPassword() {
        try {
            System.out.println("Reset password");
            System.out.println("Enter Username to reset password : ");
            Scanner scanner = new Scanner(System.in);
            String username = scanner.nextLine();
            System.out.println("Please answer security question to reset password.");
            PasswordReset passwordReset = this.validateSecurityQuestions(username);
            if (!passwordReset.isValid()) {
                this.logger.logError("Password reset Token expired");
                throw new TokenExpiredException();
            }
            this.logger.logInfo("Token validated");
            System.out.println("Enter new Password: ");
            String password = scanner.nextLine();
            this.update(new Authentication(username, password));
            scanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Validates security questions for a given username.
     *
     * @param username The username for which security questions are being validated.
     * @return A {@code PasswordReset} object containing the password reset token.
     */
    private PasswordReset validateSecurityQuestions(String username) {
        Map<String, String> map = new HashMap<>() {{
            put("What is your age?", "24");
            put("What is your favorite color?", "Blue");
            put("What is your nickname?", "Pete");
        }};
        Scanner scanner = new Scanner(System.in);
        boolean allTrue = false;
        while (!allTrue) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println(entry.getKey());
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase(entry.getValue())) {
                    allTrue = true;
                } else {
                    System.out.println("Incorrect. Try again.");
                    allTrue = false;
                }
            }
        }
        System.out.println("All answers are correct!");
        return PasswordResetBuilder.generatePasswordReset(username);
    }

}
