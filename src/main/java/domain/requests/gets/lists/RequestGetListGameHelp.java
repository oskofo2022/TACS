package domain.requests.gets.lists;

import java.util.ArrayList;

public record RequestGetListGameHelp(String goodLetters, String badLetters, ArrayList<RequestGetListGreenLetter> greenLetters) { }
