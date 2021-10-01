package com.epam.poker._main;

import com.epam.poker.game.entity.Deck;
import com.epam.poker.game.entity.Room;
import com.epam.poker.game.entity.Table;
import com.epam.poker.util.constant.Attribute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

public class TestDBMain {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper();
//    private static Lobby lobby = Lobby.getInstance();
    private static final Gson gson = new Gson();
    private static final Map<String, Table> tableMap = Collections.synchronizedMap(new HashMap<>());

    public static void main(String... args) {
        LOGGER.error("Test");
        ObjectNode jsonNodes = mapper.createObjectNode();
        jsonNodes.put("name", "Eugene");
        jsonNodes.put("age", "19");
        jsonNodes.put("money", new BigDecimal(199.99));


        ObjectNode dataNode = mapper.createObjectNode();
        dataNode.put("cars", "ADADSA(#@");
        dataNode.put("id", 3223);
        dataNode.put("FirsStep", "Go to School");
        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.add(dataNode);

        jsonNodes.putPOJO("data", arrayNode);
//        System.out.println(jsonNodes.get("data"));
//        System.out.println(jsonNodes.get("data").asText());
//        System.out.println(jsonNodes.get("data").textValue());
        String json = "{\"event\":\"enterRoom\",\"data\":\"2\"}";
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        JsonNode jsonNode1 = mapper.createObjectNode();
        System.out.println(jsonNode1.findValue("event"));
        System.out.println(jsonNode.findValue("fsdf"));
        System.out.println(jsonNode.get("event").asText());
        System.out.println(jsonNode);

//        System.out.println(jsonNodes.get("id").toString());
//        System.out.println(ConfigReaderJwt.getAccessTokenLifeTime());
//        System.out.println(ConfigReaderJwt.getSecretKey());
//        System.out.println(LocalTime.now());
//        List<Table> tables = new ArrayList<>();
//        tables.add(new Table(8, 2, "2-handed Table", 0, 2, 4));
//        tables.add(new Table(8, 2, "2-handed Table", 0, 2, 4));
//        tables.add(new Table(8, 2, "2-handed Table", 0, 2, 4));
//        System.out.println(gson.toJson(tables));
//        ObjectNode response = mapper.createObjectNode();
//        response.put(Attribute.CHECK_USERNAME_EXIST, String.valueOf(true));
//        System.out.println(gson.toJson(response));
//        try {
//            System.out.println(mapper.writeValueAsString(response));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }
    }
}