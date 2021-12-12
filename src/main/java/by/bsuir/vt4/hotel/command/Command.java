package by.bsuir.vt4.hotel.command;

@FunctionalInterface
public interface Command {
    CommandResult execute(RequestContent requestContent);
}
