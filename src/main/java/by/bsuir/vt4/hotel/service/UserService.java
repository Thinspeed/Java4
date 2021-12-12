package by.bsuir.vt4.hotel.service;

import by.bsuir.vt4.hotel.entity.Booking;
import by.bsuir.vt4.hotel.entity.Room;
import by.bsuir.vt4.hotel.entity.User;
import by.bsuir.vt4.hotel.exception.RepositoryException;
import by.bsuir.vt4.hotel.exception.ServiceException;
import by.bsuir.vt4.hotel.repository.BookingRepository;
import by.bsuir.vt4.hotel.repository.UserRepository;
import by.bsuir.vt4.hotel.specification.Specification;
import by.bsuir.vt4.hotel.specification.UserLoginSpecification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;

public class UserService {

    private static Logger log = LogManager.getLogger();

    public List<User> login(String login, String password) throws ServiceException {
        List<User> users;

        Specification specification = new UserLoginSpecification(login, password);
        UserRepository repository = new UserRepository();
        try {
            users = repository.query(specification);
        } catch (RepositoryException e) {
            log.error("RepositoryException while login");
            throw new ServiceException(e);
        }
        return users;
    }

    public User register(String login, String password, String email, String firstName,
                         String lastName, String phoneNumber, String country, String birthday, boolean isAdmin)
            throws ServiceException {

        UserRepository repository = new UserRepository();
        User user = new User(login, password, firstName, lastName, email, country, phoneNumber, LocalDate.parse(birthday), isAdmin);
        try {
            repository.add(user);
        } catch (RepositoryException e) {
            log.error("RepositoryException while registering new user");
            throw new ServiceException(e);
        }
        return user;
    }

    public Booking addBooking(String userLogin, String arrival, String departure, Room room, int guestsNumber, String guestName) throws ServiceException {
        BookingRepository repository = new BookingRepository();
        Booking booking = new Booking(userLogin, LocalDate.parse(arrival), LocalDate.parse(departure), room, guestsNumber, guestName);
        try {
            repository.add(booking);
        } catch (RepositoryException e) {
            log.error("RepositoryException while add booking", e);
            throw new ServiceException(e);
        }
        return booking;
    }

    public User updateProfile(String login, String password, String email, String firstName,
                              String lastName, String phoneNumber, String country, String birthday, boolean isAdmin) throws ServiceException {

        UserRepository repository = new UserRepository();
        User user = new User(login, password, firstName, lastName, email, country, phoneNumber, LocalDate.parse(birthday), isAdmin);
        try {
            repository.update(user);
        } catch (RepositoryException e) {
            log.error("RepositoryException while registering new user");
            throw new ServiceException(e);
        }
        return user;

    }
}
