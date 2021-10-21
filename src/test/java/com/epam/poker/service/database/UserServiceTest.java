package com.epam.poker.service.database;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.User;
import com.epam.poker.model.database.type.UserStatus;
import com.epam.poker.service.database.impl.UserServiceImpl;
import com.epam.poker.util.LineHasher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserServiceTest {
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final long TEST_USER_ID = 9;
    private static final String TEST_USER_LOGIN = "Test_Vadim";
    private static final String TEST_USER_EMAIL = "test.vadim@gmail.com";
    private static final String TEST_USER_PASSWORD = "password12";

    @Test
    public void testCheckUserByLoginAndPassword() {
        LineHasher lineHasher = new LineHasher();
        String hashPass = lineHasher.hashingLine(TEST_USER_PASSWORD);
        boolean actual = false;
        User user = null;
        try {
            user = userService.findUserByLoginAndPassword(TEST_USER_LOGIN, hashPass);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        if (user != null && user.getUserId() == TEST_USER_ID) {
            actual = true;
        }
        assertTrue(actual);
    }

    @Test
    public void testExpectedExceptionFindUserByLogin() {
        Assertions.assertThrows(ServiceException.class, () -> {
            userService.findUserByLogin(String.valueOf(-1));
        });
    }

    @Test
    public void testExpectedExceptionFindUserById() {
        Assertions.assertThrows(ServiceException.class, () -> {
            userService.findUserById(-1);
        });
    }

    @Test
    public void testFindUserByLogin() {
        User user = null;
        boolean actual = false;
        try {
            user = userService.findUserByLogin(TEST_USER_LOGIN);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        if (user != null && user.getUserId() == TEST_USER_ID) {
            actual = true;
        }
        assertTrue(actual);
    }

    @Test
    public void testFindUserByEmail() {
        User user = null;
        boolean actual = false;
        try {
            user = userService.findUserByEmail(TEST_USER_EMAIL);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        if (user != null && user.getUserId() == TEST_USER_ID) {
            actual = true;
        }
        assertTrue(actual);
    }

    @Test
    public void testBlockById() {
        User user = null;
        boolean actual = false;
        try {
            userService.blockById(TEST_USER_ID);
            user = userService.findUserByLogin(TEST_USER_LOGIN);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        if (user != null && user.getUserStatus().equals(UserStatus.BANNED)) {
            actual = true;
        }
        assertTrue(actual);
    }

    @Test
    public void testUpdateUser() {
        User user = null;
        boolean actual = false;
        String testName = "TestName";
        try {
            user = userService.findUserByLogin(TEST_USER_LOGIN);
            user.setFirstName(testName);
            userService.update(user);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        if (user != null && user.getFirstName().equals(testName)) {
            actual = true;
            user.setFirstName("Vadim");
            try {
                userService.update(user);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        assertTrue(actual);
    }

    @Test
    public void testUpdateBalanceByLogin() {
        User user = null;
        boolean actual = false;
        BigDecimal balance = BigDecimal.TEN;
        try {
            user = userService.findUserByLogin(TEST_USER_LOGIN);
            user.setBalance(balance);
            userService.updateBalanceByLogin(TEST_USER_LOGIN, balance);
            user = userService.findUserByLogin(TEST_USER_LOGIN);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        if (user != null && user.getBalance().compareTo(balance) == 0) {
            actual = true;
            user.setBalance(BigDecimal.ZERO);
            try {
                userService.updateBalanceByLogin(TEST_USER_LOGIN, balance);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        assertTrue(actual);
    }

    @Test
    public void testUpdateBalanceById() {
        User user = null;
        boolean actual = false;
        BigDecimal balance = BigDecimal.TEN;
        try {
            user = userService.findUserById(TEST_USER_ID);
            user.setBalance(balance);
            userService.updateBalanceById(TEST_USER_ID, balance);
            user = userService.findUserById(TEST_USER_ID);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        if (user != null && user.getBalance().compareTo(balance) == 0) {
            actual = true;
            user.setBalance(BigDecimal.ZERO);
            try {
                userService.updateBalanceById(TEST_USER_ID, balance);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        assertTrue(actual);
    }

    @Test
    public void testIsUserExistByLoginAndPassword() {
        boolean actual = false;
        LineHasher lineHasher = new LineHasher();
        String hashPass = lineHasher.hashingLine(TEST_USER_PASSWORD);
        try {
            actual = userService.isUserExistByLoginAndPassword(
                            TEST_USER_LOGIN, hashPass);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertTrue(actual);
    }
}
