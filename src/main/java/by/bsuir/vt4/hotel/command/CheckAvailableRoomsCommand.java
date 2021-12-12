package by.bsuir.vt4.hotel.command;

import by.bsuir.vt4.hotel.entity.Room;
import by.bsuir.vt4.hotel.exception.ServiceException;
import by.bsuir.vt4.hotel.message.MessageHandler;
import by.bsuir.vt4.hotel.service.CommonService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.bsuir.vt4.hotel.constant.PageConstant.*;
import static by.bsuir.vt4.hotel.constant.RequestConstant.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckAvailableRoomsCommand implements Command {

    private static Logger log = LogManager.getLogger();

    private CommonService commonService;

    public CheckAvailableRoomsCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult;
        String arrivalDate = requestContent.getRequestParameter(ARRIVAL)[0];
        String departureDate = requestContent.getRequestParameter(DEPARTURE)[0];
        try {
            Map<String, Object> requestAttributes = new HashMap<>();
            Map<String, Object> sessionAttributes = new HashMap<>();
            List<Room> roomList = commonService.findAvailableRooms(arrivalDate, departureDate);
            if (roomList.isEmpty()) {
                requestAttributes.put(NO_AVAILABLE_ROOMS, MessageHandler.getMessage("message.no_available_rooms", (String) requestContent.getSessionAttribute(LOCALE)));
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, USER_MAIN_PAGE, requestAttributes);
            } else {
                requestAttributes.put(ROOMS, roomList);
                sessionAttributes.put(ARRIVAL, arrivalDate);
                sessionAttributes.put(DEPARTURE, departureDate);
                commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, AVAILABLE_ROOMS, requestAttributes, sessionAttributes);
            }
            return commandResult;
        } catch (ServiceException e) {
            log.error("Error while receiving rooms", e);
            return new DefaultCommand().execute(requestContent);
        }
    }
}
