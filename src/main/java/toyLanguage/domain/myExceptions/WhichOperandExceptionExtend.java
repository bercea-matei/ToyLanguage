package toyLanguage.domain.myExceptions;

public class WhichOperandExceptionExtend extends ToyLanguageExceptions {
    public WhichOperandExceptionExtend(int oprnd_num, Exception excp) {
        super(createMessage(oprnd_num, excp));
    }

    private static String createMessage(int oprnd_num, Exception excp) {
        String baseMessage = " operand had an exception: " + excp.getMessage();
        
        switch (oprnd_num) {
            case 1:
                return "The 1st" + baseMessage;
            case 2:
                return "The 2nd" + baseMessage;
            case 3:
                return "The 3rd" + baseMessage;
            default:
                return "The " + oprnd_num + "th" + baseMessage;
        }
    }
}
