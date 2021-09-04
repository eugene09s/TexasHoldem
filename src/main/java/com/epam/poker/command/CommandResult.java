package com.epam.poker.command;

import java.util.HashMap;

public class CommandResult {
    private final String page;
    private final boolean isRedirect;
    private HashMap<String, String> responseAjax;

    public CommandResult(String page, boolean isRedirect) {
        this.page = page;
        this.isRedirect = isRedirect;
        responseAjax = new HashMap<>();
    }

    public void addResponseAjax(HashMap<String, String> values) {
        responseAjax = values;
    }

    public boolean isEmptyResponseAjax() {
        return responseAjax.isEmpty();
    }

    public HashMap<String, String> getResponseAjax() {
        return responseAjax;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommandResult that = (CommandResult) o;

        if (isRedirect != that.isRedirect) return false;
        return page != null ? page.equals(that.page) : that.page == null;
    }

    @Override
    public int hashCode() {
        int result = page != null ? page.hashCode() : 0;
        result = 31 * result + (isRedirect ? 1 : 0);
        return result;
    }
}
