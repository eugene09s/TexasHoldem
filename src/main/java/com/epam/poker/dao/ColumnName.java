package com.epam.poker.dao;

public final class ColumnName {
    private ColumnName() {
    }

    //Tables
    public static final String USERS = "users";
    public static final String PROFILE_PLAYERS = "profile_players";
    public static final String GAME_PlAYERS = "game_players";
    public static final String GAMES = "games";
    public static final String GAME_WINNERS = "game_winners";

    //Table "users"
    public static final String USER_ID = "user_id";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_EMAIL = "email";
    public static final String USER_BALANCE = "balance";
    public static final String USER_ROLE = "role";
    public static final String USER_STATUS = "status";
    public static final String USER_PHONE_NUMBER = "phone_number";
    public static final String USER_CREATE_TIME = "create_time";

    //Table "profile_players"
    public static final String PROFILE_PLAYER_USER_ID = "player_id";
    public static final String PROFILE_PLAYER_BEST_PRIZE = "best_prize";
    public static final String PROFILE_PLAYER_AWARD = "award";
    public static final String PROFILE_PLAYER_PHOTO = "photo";
    public static final String PROFILE_PLAYER_ABOUT_YOURSELF = "about_yourself";
    public static final String PROFILE_PLAYER_LOST_MONEY = "lost_money";
    public static final String PROFILE_PLAYER_WIN_MONEY = "win_money";

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
