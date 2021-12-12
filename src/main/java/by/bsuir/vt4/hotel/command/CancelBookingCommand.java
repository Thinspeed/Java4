package by.bsuir.vt4.hotel.command;

import by.bsuir.vt4.hotel.entity.Booking;
import by.bsuir.vt4.hotel.exception.ServiceException;
import by.bsuir.vt4.hotel.message.MessageHandler;
import by.bsuir.vt4.hotel.service.CommonService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.bsuir.vt4.hotel.constant.PageConstant.BOOKINGS_PAGE;
import static by.bsuir.vt4.hotel.constant.PageConstant.USER_MAIN_PAGE;
import static by.bsuir.vt4.hotel.constant.RequestConstant.*;

import java.util.HashMap;
import java.util.Map;

public class CancelBookingCommand implements Command {

    private static Logger log = LogManager.getLogger();

    private CommonService commonService;

    public CancelBookingCommand(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        CommandResult commandResult;
        int bookingId = Integer.parseInt(requestContent.getRequestParameter(BOOKING_ID)[0]);
        System.out.println(bookingId);
        try {
            Booking booking = commonService.cancelBooking(bookingId);
        } catch (ServiceException e) {
            log.error("Error while canceling booking", e);
            return new DefaultCommand().execute(requestContent);
        }

        Map<String, Object> attributes = new HashMap<>();
        attributes.put(SUCCESSFUL_BOOKING, MessageHandler.getMessage("message.successful_canceling", (String) requestContent.getSessionAttribute(LOCALE)));
        commandResult = new CommandResult(CommandResult.ResponseType.FORWARD, BOOKINGS_PAGE, attributes);
        log.debug("Booking was successfully canceled");
        return commandResult;
    }
}
