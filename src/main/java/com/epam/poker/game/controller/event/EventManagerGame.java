package com.epam.poker.game.controller.event;

import com.epam.poker.game.controller.event.impl.*;

public enum EventManagerGame {
    ENTER_TO_ROOM(EnterToRoomEvent.getInstance(), "enterRoom"),
    REGISTER_TO_LOBBY(RegisterToLobbyEvent.getInstance(), "register"),
    LEAVE_FROM_ROOM(LeaveFromRoomEvent.getInstance(), "leaveRoom"),
    SEAT_ON_THE_TABLE(SitOnTheTableEvent.getInstance(),"sitOnTheTable"),
    CHAT_SEND_MESSAGE(ChatSendMessageEvent.getInstance(), "sendMessage"),
    POST_BLIND(PostBlindPartOfGameEvent.getInstance(), "postBlind"),
    SIT_IN(SitInEvent.getInstance(), "sitIn"),
    CALL(CallEvent.getInstance(), "call"),
    CHECK(CheckEvent.getInstance(), "check"),
    FOLD(FoldPartOfGameEvent.getInstance(), "fold"),
    RAISE(RaisePartOfGameEvent.getInstance(), "raise"),
    LEAVE_TABLE(LeaveTableEvent.getInstance(), "leaveTable"),
    BET(BetEvent.getInstance(), "bet");

    private final EventSocket event;
    private final String eventName;

    EventManagerGame(EventSocket event, String eventName) {
        this.event = event;
        this.eventName = eventName;
    }

    public EventSocket getEvent() {
        return event;
    }

    public String getEventName() {
        return eventName;
    }

    static EventSocket of(String name) {
        for (EventManagerGame action : EventManagerGame.values()) {
            if (action.getEventName().equalsIgnoreCase(name)) {
                return action.event;
            }
        }
        return null;
    }
}
