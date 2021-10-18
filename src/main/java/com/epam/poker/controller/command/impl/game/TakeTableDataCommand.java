package com.epam.poker.controller.command.impl.game;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.command.util.ParameterTaker;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.game.Lobby;
import com.epam.poker.model.game.Room;
import com.epam.poker.model.game.Table;
import com.epam.poker.service.game.ParserDataToJsonService;
import com.epam.poker.util.constant.Attribute;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TakeTableDataCommand implements Command {
    private static final Lobby lobby = Lobby.getInstance();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ParserDataToJsonService parserDataToJson = ParserDataToJsonService.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
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

