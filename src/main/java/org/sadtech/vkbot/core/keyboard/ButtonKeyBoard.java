package org.sadtech.vkbot.core.keyboard;

import com.google.gson.JsonObject;

public class ButtonKeyBoard {

    private String type;
    private String payload;
    private String label;
    private ColorButton color;

    private ButtonKeyBoard() {
        throw new IllegalStateException();
    }

    public JsonObject getButton() {
        JsonObject newButton = new JsonObject();
        newButton.addProperty("color", color.toString().toLowerCase());
        newButton.add("action", generateAction());
        return newButton;
    }

    private JsonObject generateAction() {
        JsonObject action = new JsonObject();
        action.addProperty("type", type);
        action.addProperty("payload", payload);
        action.addProperty("label", label);
        return action;
    }

    public static Builder builder() {
        return new ButtonKeyBoard().new Builder();
    }

    public class Builder {

        private Builder() {

        }

        public Builder color(ColorButton color) {
            ButtonKeyBoard.this.color = color;
            return this;
        }

        public Builder label(String label) {
            ButtonKeyBoard.this.label = label;
            return this;
        }

        public Builder payload(String payload) {
            ButtonKeyBoard.this.payload = payload;
            return this;
        }

        public Builder type(String type) {
            ButtonKeyBoard.this.type = type;
            return this;
        }

        public ButtonKeyBoard build() {
            return ButtonKeyBoard.this;
        }

    }

}
