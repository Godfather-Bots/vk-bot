package org.sadtech.vk.bot.sdk.service.convert;

import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.objects.messages.KeyboardButton;
import com.vk.api.sdk.objects.messages.KeyboardButtonAction;
import com.vk.api.sdk.objects.messages.KeyboardButtonActionType;
import com.vk.api.sdk.objects.messages.KeyboardButtonColor;
import org.sadtech.social.core.domain.keyboard.ButtonColor;
import org.sadtech.social.core.domain.keyboard.KeyBoard;
import org.sadtech.social.core.domain.keyboard.KeyBoardButton;
import org.sadtech.social.core.domain.keyboard.KeyBoardLine;
import org.sadtech.social.core.domain.keyboard.button.KeyBoardButtonAccount;
import org.sadtech.social.core.domain.keyboard.button.KeyBoardButtonText;
import org.sadtech.vk.bot.sdk.config.VkConnect;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyBoardConvert implements Converter<KeyBoard, Keyboard> {

    private final GroupActor groupActor;
    private final ServiceActor serviceActor;

    public KeyBoardConvert(VkConnect vkConnect) {
        this.groupActor = vkConnect.getGroupActor();
        this.serviceActor = vkConnect.getServiceActor();
    }

    @Override
    public Keyboard convert(KeyBoard keyboard) {
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

    private KeyboardButton convertButton(KeyBoardButton button) {
        KeyboardButton buttonVk = new KeyboardButton();

        switch (button.getType()) {
            case TEXT:
                KeyBoardButtonText buttonText = (KeyBoardButtonText) button;
                buttonVk.setColor(convertColor(buttonText.getColor()));
                break;
        }

        KeyboardButtonAction buttonActionVk = createActionButton(button);
        buttonVk.setAction(buttonActionVk);
        return buttonVk;
    }


    private KeyboardButtonAction createActionButton(KeyBoardButton button) {
        KeyboardButtonAction keyboardButtonAction = new KeyboardButtonAction();
        switch (button.getType()) {
            case TEXT:
                KeyBoardButtonText buttonText = (KeyBoardButtonText) button;
                keyboardButtonAction.setType(KeyboardButtonActionType.TEXT);
                keyboardButtonAction.setLabel(buttonText.getLabel());
                break;
            case ACCOUNT:
                KeyBoardButtonAccount buttonAccount = (KeyBoardButtonAccount) button;
                keyboardButtonAction.setType(KeyboardButtonActionType.VKPAY);
                keyboardButtonAction.setHash(createHash(buttonAccount));
                break;
        }
        return keyboardButtonAction;
    }

    private String createHash(KeyBoardButtonAccount button) {
        StringBuilder stringBuilder = new StringBuilder();
        if (button.getAmount() != null) {
            stringBuilder
                    .append("action=pay-to-group&amount=")
                    .append(button.getAmount()).append("&group_id=")
                    .append(groupActor.getGroupId());
        } else {
            stringBuilder
                    .append("action=transfer-to-group")
                    .append(button.getAmount()).append("&group_id=")
                    .append(groupActor.getGroupId());
        }
        if (button.getAccountId() != null) {
            stringBuilder.append("&description=").append(button.getAccountId());
        }
        return stringBuilder.append("&aid=").append(serviceActor.getId()).toString();
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
