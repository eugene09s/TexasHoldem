package com.epam.poker.controller.command.util;

import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.ProfilePlayer;
import com.epam.poker.model.database.User;
import com.epam.poker.model.dto.UserDto;
import com.epam.poker.service.database.ProfilePlayerService;
import com.epam.poker.service.database.UserService;
import com.epam.poker.service.database.impl.ProfilePlayerServiceImpl;
import com.epam.poker.service.database.impl.UserServiceImpl;
import com.epam.poker.util.constant.Attribute;
import com.epam.poker.util.constant.Parameter;

import java.util.ArrayList;
import java.util.List;

public class UserDtoCommandHelper {
    private static UserService userService = UserServiceImpl.getInstance();
    private static ProfilePlayerService profilePlayerService = ProfilePlayerServiceImpl.getInstance();

    public void processCommandWithPagination(RequestContext requestContext)
            throws InvalidParametersException, ServiceException {
        int page = ParameterTaker.takeNumber(Parameter.PAGE, requestContext);
        int size = ParameterTaker.takeNumber(Parameter.SIZE, requestContext);
        long amount = userService.findUsersAmount();
        long amountQuery = (page - 1) * size;
        if (amountQuery > amount) {
            throw new InvalidParametersException("Parameter in query invalid");
        }
        if (amount < size) {
            size = (int) amount;
        }
        List<UserDto> userDtoList = buildUserDtoList(page, size);
        requestContext.addAttribute(Attribute.USER_DTO_LIST, userDtoList);
        requestContext.addAttribute(Attribute.CURRENT_PAGE, page);
        int maxPage = (int) (amount / size);
        if (amount % size != 0) {
            ++maxPage;
        }
        requestContext.addAttribute(Attribute.AMOUNT_OF_PAGE, size);
        requestContext.addAttribute(Attribute.MAX_PAGE, maxPage);
    }

    private List<UserDto> buildUserDtoList(int page, int size) throws InvalidParametersException {
        int offset = (page - 1) * size;
        List<User> userList;
        List<ProfilePlayer> profilePlayerList;
        try {
            userList = userService.findUsersRange(offset, size);
            profilePlayerList = profilePlayerService.findProfilePlayerOfRange(offset, size);
        } catch (ServiceException e) {
            throw new InvalidParametersException("Invalid page or size!");
        }
        List<UserDto> userDtoList = new ArrayList<>();
        for (int i = 0; i < userList.size(); ++i) {
            UserDto userDto = UserDto.builder()
                    .setFirstName(userList.get(i).getFirstName())
                    .setLastName(userList.get(i).getLastName())
                    .setId(userList.get(i).getUserId())
                    .setCreateTime(userList.get(i).getCreateTime())
                    .setUserRole(userList.get(i).getUserRole())
                    .setUserStatus(userList.get(i).getUserStatus())
                    .setPhoto(profilePlayerList.get(i).getPhoto())
                    .setBalance(userList.get(i).getBalance())
                    .setLogin(userList.get(i).getLogin())
                    .createUserDto();
            userDtoList.add(userDto);
        }
        return userDtoList;
    }
}
