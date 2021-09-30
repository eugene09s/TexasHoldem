package com.epam.poker.controller.command;

public class CommandResult {
    private  String page;
    private  boolean isRedirect;
    private boolean typeResponseJson;
    private String jsonResponse;

    private CommandResult(String page, boolean isRedirect) {
        this.page = page;
        this.isRedirect = isRedirect;
    }

    public CommandResult(boolean typeResponseJson) {
        this.typeResponseJson = typeResponseJson;
    }

    public void setJsonResponse(String response) {
        this.jsonResponse = response;
    }

    public String getJsonResponse() {
        return jsonResponse;
    }

    public static CommandResult redirect(String page) {
        return new CommandResult(page, true);
    }

    public static CommandResult forward(String page) {
        return new CommandResult(page, false);
    }

    public String getPage() {
        return page;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public void setTypeResponseJson(boolean typeResponseJson) {
        this.typeResponseJson = typeResponseJson;
    }

    public boolean isTypeResponseJson() {
        return typeResponseJson;
    }
}
