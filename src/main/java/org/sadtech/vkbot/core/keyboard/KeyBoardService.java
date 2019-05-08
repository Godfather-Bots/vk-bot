package org.sadtech.vkbot.core.keyboard;

import java.util.List;

public class KeyBoardService {

    private KeyBoardService() {
        throw new IllegalStateException();
    }

    public static KeyBoard keyBoardYesNo() {
        ButtonKeyBoard yesButton = ButtonKeyBoard.builder().color(ColorButton.POSITIVE).label("Да").payload("{\"button\": \"yes\"}").build();
        ButtonKeyBoard noButton = ButtonKeyBoard.builder().color(ColorButton.NEGATIVE).label("Нет").payload("{\"button\": \"no\"}").build();
        LineKeyBoard lineKeyBoard = LineKeyBoard.builder().setButtonKeyBoard(yesButton).setButtonKeyBoard(noButton).build();
        return KeyBoard.builder().lineKeyBoard(lineKeyBoard).oneTime(true).build();
    }

    public static KeyBoard verticalMenuString(List<String> labelButtons) {
        KeyBoard keyBoard = KeyBoard.builder().oneTime(true).build();
        for (String labelButton : labelButtons) {
            ButtonKeyBoard buttonKeyBoard = ButtonKeyBoard.builder().label(labelButton).type("text").payload("{\"button\": \"" + labelButton + "\"}").build();
            keyBoard.addLine(LineKeyBoard.builder().setButtonKeyBoard(buttonKeyBoard).build());
        }
        return keyBoard;
    }

    public static KeyBoard verticalMenuButton(List<ButtonKeyBoard> buttonKeyBoards) {
        KeyBoard keyBoard = KeyBoard.builder().oneTime(true).build();
        for (ButtonKeyBoard buttonKeyBoard : buttonKeyBoards) {
            keyBoard.addLine(LineKeyBoard.builder().setButtonKeyBoard(buttonKeyBoard).build());
        }
        return keyBoard;
    }
}
