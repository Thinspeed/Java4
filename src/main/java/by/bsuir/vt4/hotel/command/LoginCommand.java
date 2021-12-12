package by.bsuir.vt4.hotel.command;

import by.bsuir.vt4.hotel.entity.User;
import by.bsuir.vt4.hotel.exception.ServiceException;
import by.bsuir.vt4.hotel.message.MessageHandler;
import by.bsuir.vt4.hotel.service.UserService;
import by.bsuir.vt4.hotel.validator.LoginValidator;
import by.bsuir.vt4.hotel.validator.PasswordValidator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.bsuir.vt4.hotel.constant.PageConstant.*;
import static by.bsuir.vt4.hotel.constant.RequestConstant.*;

import java.util.HashMap;
import java.util.Map;

public class LoginCommand implements Command {

    private static Logger log = LogManager.getLogger();

    private UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        User user;
        CommandResult commandResult;
        String login = requestContent.getRequestParameter(LOGIN)[0];
        String password = requestContent.getRequestParameter(PASSWORD)[0];
        Map<String, Object> requestAttributes = new HashMap<>();
        Map<String, Object> users = new HashMap<>();
        LoginValidator loginValidator = new LoginValidator();
        PasswordValidator passwordValidator = new PasswordValidator();
        if (loginValidator.isValidated(login) && passwordValidator.isValidated(password)) {
            try {
                if (userService.login(login, password).isEmpty()) {
                    users.put(ERROR_LOGIN_PASS, MessageHandler.getMessage("warning.login_password", (String) requestContent.getSessionAttribute(LOCALE)));
                    commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, LOGIN_PAGE, requestAttributes, users);
                    log.debug("Login error: can't find user");
                } else {
                    user = userService.login(login, password).get(0);
                    if (!user.isAdmin()) {
                        users.put(USER, user);
                        commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, USER_MAIN_PAGE, requestAttributes, users);
                        log.debug(user + " logged in as user");
                    } else {
                        user = userService.login(login, password).get(0);
                        users.put(USER, user);
                        commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, ADMIN_MAIN_PAGE, requestAttributes, users);
                        log.debug(user + " logged in as admin");
                    }
                }
            } catch (ServiceException e) {
                log.debug("Unable to log in", e);
                return new DefaultCommand().execute(requestContent);
            }
        } else {
            commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, LOGIN_PAGE);
        }
        return commandResult;
    }
}
