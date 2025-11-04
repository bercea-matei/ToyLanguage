package toyLanguage.helpers;

import toyLanguage.domain.myExceptions.InvalidInstructionException;
import toyLanguage.domain.prg_state.PrgState;

public class Parser {
    public void parse(String rawInput) throws InvalidInstructionException {
        if (rawInput == null || rawInput.isBlank()) {
            throw new InvalidInstructionException("Input cannot be empty.");
        }
        String[] parts = rawInput.trim().split(" *; *");
        

    }
}
