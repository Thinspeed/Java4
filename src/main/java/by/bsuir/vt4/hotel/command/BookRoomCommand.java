package by.bsuir.vt4.hotel.command;

import by.bsuir.vt4.hotel.entity.Room;
import by.bsuir.vt4.hotel.entity.User;
import by.bsuir.vt4.hotel.exception.ServiceException;
import by.bsuir.vt4.hotel.message.MessageHandler;
import by.bsuir.vt4.hotel.service.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.bsuir.vt4.hotel.constant.PageConstant.USER_MAIN_PAGE;
import static by.bsuir.vt4.hotel.constant.RequestConstant.*;

import java.util.HashMap;
import java.util.Map;

public class BookRoomCommand implements Command {

    private static Logger log = LogManager.getLogger();

    private UserService userService;

    public BookRoomCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult;
        User user = (User) requestContent.getSessionAttribute(USER);
        String login = user.getLogin();
        String arrival = requestContent.getRequestParameter(ARRIVAL)[0];
        String departure = requestContent.getRequestParameter(DEPARTURE)[0];
        Room room = new Room();
        room.setNumber(Integer.parseInt(requestContent.getRequestParameter(ROOM)[0]));
        int guestsNumber = Integer.parseInt(requestContent.getRequestParameter(NUMBER_OF_GUESTS)[0]);
        String guestsNames = requestContent.getRequestParameter(GUESTS_NAMES)[0];

        try {
            userService.addBooking(login, arrival, departure, room, guestsNumber, guestsNames);
        } catch (ServiceException e) {
            log.error("Error while booking room", e);
            return new DefaultCommand().execute(requestContent);
        }
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(SUCCESSFUL_BOOKING, MessageHandler.getMessage("message.successful_book", (String) requestContent.getSessionAttribute(LOCALE)));
        commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, USER_MAIN_PAGE, attributes);
        log.debug("Room was successfully booked");
        return commandResult;
    }
}
