package com.epam.poker.controller.event;

import com.epam.poker.controller.event.impl.*;
import com.epam.poker.util.constant.EventName;

public enum EventManagerGame {
    ENTER_TO_ROOM(EnterToRoomEvent.getInstance(), EventName.ENTER_ROOM),
    REGISTER_TO_LOBBY(RegisterToLobbyEvent.getInstance(), EventName.REGISTER),
    LEAVE_FROM_ROOM(LeaveFromRoomEvent.getInstance(), EventName.LEAVE_ROOM),
    SEAT_ON_THE_TABLE(SitOnTheTableEvent.getInstance(),EventName.SIT_ON_THE_TABLE),
    CHAT_SEND_MESSAGE(ChatSendMessageEvent.getInstance(), EventName.SEND_MESSAGE),
    POST_BLIND(PostBlindPartOfGameEvent.getInstance(), EventName.POST_BLIND),
    SIT_IN(SitInEvent.getInstance(), EventName.SIT_IN),
    CALL(CallEvent.getInstance(), EventName.CALL),
    CHECK(CheckEvent.getInstance(), EventName.CHECK),
    FOLD(FoldPartOfGameEvent.getInstance(), EventName.FOLD),
    RAISE(RaiseEvent.getInstance(), EventName.RAISE),
    LEAVE_TABLE(LeaveTableEvent.getInstance(), EventName.LEAVE_TABLE),
    BET(BetEvent.getInstance(), EventName.BET);

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
