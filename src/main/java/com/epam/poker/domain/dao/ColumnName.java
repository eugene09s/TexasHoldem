package com.epam.poker.domain.dao;

public final class ColumnName {
    private ColumnName() {
    }

    //Tables
    public static final String USERS = "users";
    public static final String ROLES = "roles";
    public static final String STATUSES = "statuses";
    public static final String RATING_PLAYERS = "rating_players";

    //Table "users"
    public static final String USER_ID = "user_id";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_EMAIL = "email";
    public static final String USER_BALANCE = "balance";
    public static final String USER_PHOTO = "photo";
    public static final String USER_CREATE_TIME = "create_time";
    public static final String USER_PHONE_NUMBER = "phone_number";
    public static final String USER_ABOUT_YOURSELF = "about_yourself";
    public static final String USER_ROLE_ID = "user_role_id";
    public static final String USER_STATUS_ID = "user_status_id";

    //Table "roles"
    public static final String ROLE_ID = "role_id";
    public static final String ROLE_ROLE = "role";

    //Table "statuses"
    public static final String STATUS_ID = "status_id";
    public static final String STATUS_STATUS = "status";

    //Table "rating_players"
    public static final String RATING_PLAYER_USER_ID = "player_id";
    public static final String RATING_PLAYER_RANKING = "ranking";
    public static final String RATING_PLAYER_BEST_PRIZE = "best_prize";
    public static final String RATING_PLAYER_AWARD = "award";

    //Table "game_players"
    public static final String GAME_PLAYER_ID = "game_player_id";
    public static final String GAME_PLAYER_LAST_ACTION = "last_action";
    public static final String GAME_PLAYER_TWO_CARDS = "two_cards";
    public static final String GAME_PLAYER_COMBINATIONS_CARDS = "combinations_cards";
    public static final String GAME_PLAYER_USER_ID = "game_player_user_id";
    public static final String GAME_PLAYER_GAME_ID = "game_id";

    //Table "games"
    public static final String GAME_ID = "game_id";
    public static final String GAME_TITLE = "title";
    public static final String GAME_DATE = "date";
    public static final String GAME_BANK = "bank";
    public static final String GAME_FIVE_CARDS = "five_cards";

    //Table "game_winners"
    public static final String GAME_WINNER_ID = "game_winner_id";
    public static final String GAME_WINNER_GAME_ID = "game_id";
    public static final String GAME_WINNER_USER_ID = "game_winner_user_id";
}
