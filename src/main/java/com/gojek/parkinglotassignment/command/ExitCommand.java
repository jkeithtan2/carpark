package com.gojek.parkinglotassignment.command;

import com.gojek.parkinglotassignment.AppContext;

public class ExitCommand implements Command {
    @Override
    public String execute(AppContext appContext, String[] inputArgs) {
        System.exit(0);
        return null;
    }
}
