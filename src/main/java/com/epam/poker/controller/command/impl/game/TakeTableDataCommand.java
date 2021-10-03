package com.epam.poker.controller.command.impl.game;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.command.util.ParameterTaker;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.game.entity.Lobby;
import com.epam.poker.game.entity.Room;
import com.epam.poker.game.entity.Table;
import com.epam.poker.game.logic.ParserDataToJsonService;
import com.epam.poker.util.constant.Attribute;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;

public class TakeTableDataCommand implements Command {
    private static final Lobby lobby = Lobby.getInstance();
    private static final Gson gson = new Gson();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ParserDataToJsonService parserDataToJson = ParserDataToJsonService.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException, DaoException {
        long tableId = ParameterTaker.takeId(requestContext);
        Room room = lobby.findRoom(String.format(Attribute.TABLE_WITH_HYPHEN, tableId));
        String jsonLine = null;
        if (room != null) {
            Table table = room.getTable();
            ObjectNode response = mapper.createObjectNode();
            response.putPOJO(Attribute.TABLE, parserDataToJson.parseDataTableForRoom(table));
            jsonLine = String.valueOf(response);
        } else {
            //todo error if room not found
        }
        CommandResult commandResult = new CommandResult(true);
        commandResult.setJsonResponse(jsonLine);
        return commandResult;
    }
}
