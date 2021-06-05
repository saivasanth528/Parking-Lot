package com.vasanth.models;

import com.vasanth.exceptions.InvalidCommandException;
import lombok.Getter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Command {
    private static final String SPACE = " ";
    @Getter
    private String commandName;

    @Getter
    private List<String> params;

    public Command(final String inputLine) {
        /**
            @param inputLine Given Input Command Line
         * command or its given params are not valid, then {@link InvalidCommandException} is thrown.
         *  Converting the array into streams and mapping with String and filtering by length greater than zero finally converting them
         *  to a list
         */
        final List<String> tokenList = Arrays.stream(inputLine.trim().split(SPACE))
                .map(String::trim)
                .filter(token -> token.length() > 0).collect(Collectors.toList());
        if(tokenList.size() == 0) {
            throw new InvalidCommandException();
        }
        this.commandName = tokenList.get(0).toLowerCase();
        tokenList.remove(0);
        params = tokenList;
    }


}
