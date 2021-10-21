package com.epam.poker.controller.command.util;

import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.dto.StatisticResultGame;
import com.epam.poker.service.database.GameService;
import com.epam.poker.service.database.StatisticOfGameService;
import com.epam.poker.service.database.impl.GameServiceImpl;
import com.epam.poker.service.database.impl.StatisticOfGameServiceImpl;
import com.epam.poker.util.constant.Attribute;
import com.epam.poker.util.constant.Parameter;

import java.util.List;

public class StatisticGameCommandHelper {
    private static final GameService gameService = GameServiceImpl.getInstance();
    private static final StatisticOfGameService statisticOfGameService = StatisticOfGameServiceImpl.getInstance();

    public void processCommandWithPagination(RequestContext requestContext)
            throws InvalidParametersException, ServiceException {
        int page = ParameterTaker.takeNumber(Parameter.PAGE, requestContext);
        int size = ParameterTaker.takeNumber(Parameter.SIZE, requestContext);
        long amount = gameService.findGamesAmount();
        long amountQuery = (page - 1) * size;
        if (amountQuery > amount) {
            throw new InvalidParametersException("Parameter in query invalid");
        }
        if (amount < size) {
            size = (int) amount;
        }
        List<StatisticResultGame> statisticResultGameList = buildStatisticOfGame(page, size);
        requestContext.addAttribute(Attribute.STATISTIC_OF_GAME_LIST, statisticResultGameList);
        requestContext.addAttribute(Attribute.CURRENT_PAGE, page);
        int maxPage = (int) (amount / size);
        if (amount % size != 0) {
            ++maxPage;
        }
        requestContext.addAttribute(Attribute.AMOUNT_OF_PAGE, size);
        requestContext.addAttribute(Attribute.MAX_PAGE, maxPage);
    }

    private List<StatisticResultGame> buildStatisticOfGame(int page, int size) throws InvalidParametersException {
        int offset = (page - 1) * size;
        List<StatisticResultGame> statisticResultGameList;
        try {
            statisticResultGameList = statisticOfGameService.fetchDataByRange(offset, size);
        } catch (ServiceException e) {
            throw new InvalidParametersException("Invalid page or size!");
        }
        return statisticResultGameList;
    }
}
