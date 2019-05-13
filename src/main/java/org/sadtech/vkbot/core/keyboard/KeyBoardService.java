package org.sadtech.vkbot.core.keyboard;

import com.vk.api.sdk.objects.messages.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class KeyBoardService {

    private KeyBoardService() {
        throw new IllegalStateException();
    }

    public static Keyboard keyBoardYesNo() {
        KeyboardButton yesButton = new KeyboardButton();
        KeyboardButtonAction buttonActionYes = new KeyboardButtonAction();
        buttonActionYes.setLabel("Да");
        buttonActionYes.setType(KeyboardButtonActionType.TEXT);
        buttonActionYes.setPayload("{\"button\": \"yes\"}");
        yesButton.setAction(buttonActionYes);
        yesButton.setColor(KeyboardButtonColor.POSITIVE);

        KeyboardButton noButton = new KeyboardButton();
        KeyboardButtonAction buttonActionNo = new KeyboardButtonAction();
        buttonActionNo.setLabel("Да");
        buttonActionNo.setType(KeyboardButtonActionType.TEXT);
        buttonActionNo.setPayload("{\"button\": \"no\"}");
        noButton.setAction(buttonActionNo);
        noButton.setColor(KeyboardButtonColor.NEGATIVE);

        List<KeyboardButton> line1 = new ArrayList<>();
        line1.add(yesButton);
        line1.add(noButton);

        Keyboard keyboard = new Keyboard();
        keyboard.setButtons(Collections.singletonList(line1));
        keyboard.setOneTime(true);
        return keyboard;
    }

    public static Keyboard verticalKeyboard(List<String> labelButtons) {
        Keyboard keyBoard = new Keyboard();
        keyBoard.setOneTime(true);
        List<KeyboardButton> menu = new ArrayList<>();
        for (String labelButton : labelButtons) {
            KeyboardButton button = new KeyboardButton();
            button.setColor(KeyboardButtonColor.DEFAULT);

            KeyboardButtonAction buttonAction = new KeyboardButtonAction();
            buttonAction.setPayload("{\"button\": \"" + labelButton + "\"}");
            buttonAction.setType(KeyboardButtonActionType.TEXT);
            buttonAction.setLabel(labelButton);

            button.setAction(buttonAction);
            menu.add(button);
        }
        keyBoard.setButtons(Collections.singletonList(menu));
        return keyBoard;
    }
}
