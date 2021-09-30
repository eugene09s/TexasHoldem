package com.epam.poker.game.controller.command.impl;

import com.epam.poker.util.constant.Parameter;
import com.epam.poker.game.controller.command.EventSocket;
import com.epam.poker.game.entity.Gambler;
import com.epam.poker.game.entity.Lobby;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnterToRoomEvent implements EventSocket {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final EnterToRoomEvent instance = new EnterToRoomEvent();
    private static Lobby lobby = Lobby.getInstance();

    private EnterToRoomEvent() {
    }

    public static EnterToRoomEvent getInstance() {
        return instance;
    }

    @Override
    public void execute(String jsonLine, Gambler gambler) {
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readValue(jsonLine, JsonNode.class);
        } catch (JsonMappingException e) {
            LOGGER.error("Parse json: " + e);
        } catch (JsonProcessingException e) {
            LOGGER.error("Json processing json: " + e);
        }
        String titleRoom = jsonNode.get(Parameter.TABLE_ID).asText();
        if (gambler.getTitleRoom() != null) {
            lobby.addGamblerToRoom(titleRoom, gambler);
        }
    }
    //{
    //    "table": {
    //        "id": 2,
    //        "name": "Sample 2-handed Table",
    //        "seatsCount": 2,
    //        "playersSeatedCount": 0,
    //        "bigBlind": 8,
    //        "smallBlind": 4,
    //        "minBuyIn": 160,
    //        "maxBuyIn": 800,
    //        "pot": [
    //            {
    //                "amount": 0,
    //                "contributors": []
    //            }
    //        ],
    //        "biggestBet": 0,
    //        "dealerSeat": null,
    //        "activeSeat": null,
    //        "seats": [],
    //        "phase": null,
    //        "board": [
    //            "",
    //            "",
    //            "",
    //            "",
    //            ""
    //        ],
    //        "log": {
    //            "message": "",
    //            "seat": "",
    //            "action": ""
    //        }
    //    }
    //}
}
