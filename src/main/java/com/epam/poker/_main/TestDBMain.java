package com.epam.poker._main;

import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.dao.helper.DaoSaveTransaction;
import com.epam.poker.model.entity.ProfilePlayer;
import com.epam.poker.model.entity.User;
import com.epam.poker.model.entity.type.UserRole;
import com.epam.poker.model.entity.type.UserStatus;
import com.epam.poker.model.service.user.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;

public class TestDBMain {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String... args) {
        LOGGER.error("Test");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        ProfilePlayer profilePlayer = ProfilePlayer.builder()
//                .setUserId(11)
//                .setBestPrize(BigDecimal.valueOf(99999))
//                .setAward("TopWinnerUsers")
//                .setPhoto(null)
//                .setAboutYourself("Hello everyone")
//                .setLostMoney(BigDecimal.valueOf(0))
//                .setWinMoney(BigDecimal.valueOf(0))
//                .createRatingPlayer();
        User user = User.builder()
                .setCreateTime(timestamp)
                .setEmail("Test2SSSSSSuea@ah.comehb")
                .setFirstName("Vania")
                .setUserRole(UserRole.USER)
                .setUserStatus(UserStatus.ACTIVE)
                .setPassword("222222222222222")
                .setLogin("Test2zsdfz")
                .setLastName("JJJJJJJJJJJ")
                .setBalance(BigDecimal.valueOf(333333))
                .createUser();
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
        UserService userService = UserServiceImpl.getInstance();
        ProfilePlayerService profilePlayerService = ProfilePlayerServiceImpl.getInstance();
        List<User> userList = null;
        SignUpService signUpService = SignUpServiceImpl.getInstance();
        long id = 0;
        ProfilePlayer profilePlayer = ProfilePlayer.builder()
                .setAward("Hello1111111111")
                .setWinMoney(BigDecimal.valueOf(32))
                .setLostMoney(BigDecimal.valueOf(12))
                .setAboutYourself("dsas")
                .setPhoto("fds")
                .setBestPrize(BigDecimal.valueOf(32))
                .setUserId(21)
                .createRatingPlayer();


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
//        String line = "asbodns.a8i-ad.sskn.jg";
//        System.out.printf(line.substring(line.lastIndexOf(".")));
        String roleLine = "GU3EST";
        String commandName = "localization";
        try {
            System.out.println(UserRole.valueOf(roleLine).isExitCommandName(commandName));
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Permission denied");
        }
        System.out.println(UserRole.USER.toString());
    }
}
