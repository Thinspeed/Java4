package by.bsuir.vt4.hotel.command;

import static by.bsuir.vt4.hotel.constant.RequestConstant.ROOM;

import java.util.HashMap;
import java.util.Map;

import by.bsuir.vt4.hotel.constant.PageConstant;

public class ToBookingRoomCommand implements Command {

    @Override
    public CommandResult execute(RequestContent requestContent) {
        String room = requestContent.getRequestParameter(ROOM)[0];
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(ROOM, room);
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.BOOK_ROOM_PAGE, attributes);
    }
}
