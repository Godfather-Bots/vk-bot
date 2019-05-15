package org.sadtech.vkbot.core.convert;

import com.vk.api.sdk.objects.messages.*;
import org.sadtech.bot.core.domain.keyboard.ButtonColor;
import org.sadtech.bot.core.domain.keyboard.KeyBoard;
import org.sadtech.bot.core.domain.keyboard.KeyBoardButton;
import org.sadtech.bot.core.domain.keyboard.KeyBoardLine;

import java.util.ArrayList;
import java.util.List;

public final class KeyBoardConvert {

    private KeyBoardConvert() {

    }

    public static Keyboard convertKeyboard(KeyBoard keyboard) {
        Keyboard keyboardVk = new Keyboard();
        keyboardVk.setOneTime(keyboard.isOneTime());

        List<List<KeyboardButton>> listButton = new ArrayList<>();
        for (KeyBoardLine keyBoardLine : keyboard.getKeyBoardLines()) {
            List<KeyboardButton> buttonList = new ArrayList<>();
            for (KeyBoardButton button : keyBoardLine.getKeyBoardButtons()) {
                buttonList.add(convertButton(button));
            }
            listButton.add(buttonList);
        }
        keyboardVk.setButtons(listButton);

        return keyboardVk;
    }

    private static KeyboardButton convertButton(KeyBoardButton button) {
        KeyboardButton buttonVk = new KeyboardButton();
        buttonVk.setColor(convertColor(button.getColor()));

        KeyboardButtonAction buttonActionVk = new KeyboardButtonAction();
        buttonActionVk.setLabel(button.getLabel());
        buttonActionVk.setType(KeyboardButtonActionType.TEXT);
        buttonActionVk.setPayload(button.getPayload());

        buttonVk.setAction(buttonActionVk);
        return buttonVk;
    }


    private static KeyboardButtonColor convertColor(ButtonColor color) {
        KeyboardButtonColor buttonColorVk;
        switch (color) {
            case DEFAULT:
                buttonColorVk = KeyboardButtonColor.DEFAULT;
                break;
            case PRIMARY:
                buttonColorVk = KeyboardButtonColor.PRIMARY;
                break;
            case NEGATIVE:
                buttonColorVk = KeyboardButtonColor.NEGATIVE;
                break;
            case POSITIVE:
                buttonColorVk = KeyboardButtonColor.POSITIVE;
                break;
            default:
                buttonColorVk = KeyboardButtonColor.DEFAULT;
        }
        return buttonColorVk;
    }

}
