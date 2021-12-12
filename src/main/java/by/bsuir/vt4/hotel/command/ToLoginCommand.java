package by.bsuir.vt4.hotel.command;

import by.bsuir.vt4.hotel.constant.PageConstant;

public class ToLoginCommand implements Command {
    @Override
    public CommandResult execute(RequestContent requestContent) {
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageConstant.LOGIN_PAGE);
    }
}
