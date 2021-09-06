package com.epam.poker._main;

import com.epam.poker.connection.ConnectionPool;
import com.epam.poker.dao.ProfilePlayerDao;
import com.epam.poker.dao.UserDao;
import com.epam.poker.dao.helper.DaoSaveTransaction;
import com.epam.poker.dao.helper.DaoSaveTransactionFactory;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.logic.service.user.ProfilePlayerService;
import com.epam.poker.logic.service.user.ProfilePlayerServiceImpl;
import com.epam.poker.logic.service.user.UserService;
import com.epam.poker.logic.service.user.UserServiceImpl;
import com.epam.poker.logic.validator.impl.ProfilePlayerValidator;
import com.epam.poker.logic.validator.impl.UserValidator;
import com.epam.poker.model.entity.ProfilePlayer;
import com.epam.poker.model.entity.User;
import com.epam.poker.model.enumeration.UserRole;
import com.epam.poker.model.enumeration.UserStatus;
import com.epam.poker.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class TestDBMain {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String... args) {
        LOGGER.error("Test");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        ProfilePlayer profilePlayer = ProfilePlayer.builder()
//                .setUserId(11)
//                .setBestPrize(BigDecimal.valueOf(199.1))
//                .setAward("TopWinnerUsers")
//                .setPhoto(null)
//                .setAboutYourself("Hello everyone")
//                .setLostMoney(BigDecimal.valueOf(0))
//                .setWinMoney(BigDecimal.valueOf(0))
//                .createRatingPlayer();
        //ConnectionPool connectionPool = new ConnectionPool();
//        DaoSaveTransactionFactory daoSaveTransactionFactory = new DaoSaveTransactionFactory();
//        DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create();
//        UserService userService = new UserServiceImpl(new DaoSaveTransactionFactory());
//        ProfilePlayerService profilePlayerService = new ProfilePlayerServiceImpl(new DaoSaveTransactionFactory(), new ProfilePlayerValidator());
//        long userId = 0;
//        List<User> userList = null;
//        try {
//            System.out.println(profilePlayerService.findAll());
////            profilePlayerService.updateAwardByUserId(5,"TopGamer");
////            profilePlayerService.updateBestPrizeByUserId(5, BigDecimal.valueOf(231));
////            System.out.println(profilePlayerService.findAll());
//            userList = userService.findAll();
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
//        System.out.println(userId);
//        for (User user : userList) {
//            System.out.println(user);
//        }

//        List<User> listUsers = null;
//        try {
//            listUsers = userDao.findAll();
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }
//        System.out.println(listUsers);
//        try {
//            System.out.println("Amount users: " + userDao.findUsersAmount());
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }

//        UserValidator userValidator = new UserValidator();
//        List<String> list = List.of("Eugene", "evgeshasha$+adura@gmail.com", "evgesha.shaASS1d.323.ura@gmai.lsdf.com", "eugene", "eweфы", "raja$test@gmail.com");
//        for (String line : list) {
//            System.out.println(line + ": " + userValidator.isValidEmail(line));
//        }
    }
}
