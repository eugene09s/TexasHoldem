package com.epam.poker.controller.command.impl.user;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.command.util.ParameterTaker;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.ProfilePlayer;
import com.epam.poker.model.database.User;
import com.epam.poker.model.database.type.UserRole;
import com.epam.poker.model.database.type.UserStatus;
import com.epam.poker.service.database.SignUpService;
import com.epam.poker.service.database.impl.SignUpServiceImpl;
import com.epam.poker.util.LineHasher;
import com.epam.poker.util.constant.Attribute;
import com.epam.poker.util.constant.CommandName;
import com.epam.poker.util.constant.PagePath;
import com.epam.poker.util.constant.Parameter;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SignUpCommand implements Command {
    private static final String LOGIN_PAGE_COMMAND = "poker?command=" + CommandName.LOGIN_PAGE;
    private static final BigDecimal INITIAL_BALANCE_USER = new BigDecimal(100);
    private static final String USERNAME_EXIST_KEY = "username.exist";
    private static final String INVALID_DATA_KEY = "invalid.data";
    private static final BigDecimal PRE_MONEY = BigDecimal.valueOf(0);
    private static final String PRE_AWARD = "Your future award";
    private static final String PRE_ABOUT_YOURSELF = "Write about yourself";
    private static final String PRE_PHOTO = "notAva.jpg";
    private SignUpService sigUpService = SignUpServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        String login = ParameterTaker.takeString(Parameter.LOGIN, requestContext);
        String email = ParameterTaker.takeString(Parameter.EMAIL, requestContext);
        boolean isLoginAndEmailExist = false;
        isLoginAndEmailExist = sigUpService.isUserLoginExist(login) || sigUpService.isUserEmailExist(email);
        if (!isLoginAndEmailExist) {
            requestContext.addAttribute(Attribute.SAVED_LOGIN, login);
            requestContext.addAttribute(Attribute.SAVED_EMAIL, email);
            Timestamp nowTime = new Timestamp(System.currentTimeMillis());
            String pass = ParameterTaker.takeString(Parameter.PASSWORD, requestContext);
            LineHasher lineHasher = new LineHasher();
            User user = User.builder()
                    .setLogin(login)
                    .setPassword(lineHasher.hashingLine(pass))
                    .setFirstName(ParameterTaker.takeString(Parameter.FIRST_NAME, requestContext))
                    .setLastName(ParameterTaker.takeString(Parameter.LAST_NAME, requestContext))
                    .setEmail(ParameterTaker.takeString(Parameter.EMAIL, requestContext))
                    .setBalance(INITIAL_BALANCE_USER)
                    .setUserRole(UserRole.USER)
                    .setUserStatus(UserStatus.ACTIVE)
                    .setPhoneNumber(ParameterTaker.takePhoneNumber(Parameter.PHONE_NUMBER, requestContext))
                    .setCreateTime(nowTime)
                    .createUser();
            ProfilePlayer profilePlayer = ProfilePlayer.builder()
                    .setBestPrize(PRE_MONEY)
                    .setAward(PRE_AWARD)
                    .setPhoto(PRE_PHOTO)
                    .setAboutYourself(PRE_ABOUT_YOURSELF)
                    .setLostMoney(PRE_MONEY)
                    .setWinMoney(PRE_MONEY)
                    .createRatingPlayer();
            long idUser = sigUpService.signUp(user, profilePlayer);
            if (idUser != -1) {
                return CommandResult.redirect(LOGIN_PAGE_COMMAND);
            } else {
                requestContext.addAttribute(Attribute.ERROR_MESSAGE, INVALID_DATA_KEY);
            }
        } else {
            requestContext.addAttribute(Attribute.ERROR_MESSAGE, USERNAME_EXIST_KEY);
        }
        return CommandResult.forward(PagePath.SIGN_UP);
    }
}
