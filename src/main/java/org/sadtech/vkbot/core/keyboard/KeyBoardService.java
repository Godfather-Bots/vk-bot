package org.sadtech.vkbot.core.keyboard;

import java.util.List;

public class KeyBoardService {

    private KeyBoardService() {
        throw new IllegalStateException();
    }

    public static KeyBoard keyBoardYesNo() {
        ButtonKeyBoard yesButton = ButtonKeyBoard.builder().setColor(ColorButton.POSITIVE).setLabel("Да").setPayload("{\"button\": \"yes\"}").build();
        ButtonKeyBoard noButton = ButtonKeyBoard.builder().setColor(ColorButton.NEGATIVE).setLabel("Нет").setPayload("{\"button\": \"no\"}").build();
        LineKeyBoard lineKeyBoard = LineKeyBoard.builder().setButtonKeyBoard(yesButton).setButtonKeyBoard(noButton).build();
        KeyBoard keyBoard = KeyBoard.builder().setLineKeyBoard(lineKeyBoard).build();
        keyBoard.setOneTime(true);
        return keyBoard;
    }

    public static KeyBoard verticalMenuString(List<String> labelButtons) {
        KeyBoard keyBoard = new KeyBoard();
        for (String labelButton : labelButtons) {
            ButtonKeyBoard buttonKeyBoard = ButtonKeyBoard.builder().setLabel(labelButton).setType("text").setPayload("{\"button\": \"" + labelButton + "\"}").build();
            keyBoard.addLine(LineKeyBoard.builder().setButtonKeyBoard(buttonKeyBoard).build());
        }
        keyBoard.setOneTime(true);
        return keyBoard;
    }

    public static KeyBoard verticalMenuButton(List<ButtonKeyBoard> buttonKeyBoards) {
        KeyBoard keyBoard = new KeyBoard();
        for (ButtonKeyBoard buttonKeyBoard : buttonKeyBoards) {
            keyBoard.addLine(LineKeyBoard.builder().setButtonKeyBoard(buttonKeyBoard).build());
        }
        keyBoard.setOneTime(true);
        return keyBoard;
    }
}
