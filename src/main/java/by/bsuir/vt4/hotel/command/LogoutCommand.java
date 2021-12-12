package by.bsuir.vt4.hotel.command;

import by.bsuir.vt4.hotel.constant.PageConstant;

public class LogoutCommand implements Command {

    @Override
    public CommandResult execute(RequestContent requestContent) {
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.INDEX_PAGE);
    }
}
