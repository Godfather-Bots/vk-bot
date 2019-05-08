package org.sadtech.vkbot.core.keyboard;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class KeyBoard {

    private List<LineKeyBoard> lineKeyBoards;
    private boolean oneTime;

    private KeyBoard() {
        lineKeyBoards = new ArrayList<>();
        throw new IllegalStateException();
    }

    public JsonObject getKeyboard() {
        JsonObject keyboard = new JsonObject();
        keyboard.addProperty("one_time", oneTime);

        JsonArray menuLine = new JsonArray();
        for (LineKeyBoard lineKeyboard : lineKeyBoards) {
            menuLine.add(lineKeyboard.getLine());
        }

        keyboard.add("buttons", menuLine);
        return keyboard;
    }

    public static Builder builder() {
        return new KeyBoard().new Builder();
    }

    public void addLine(LineKeyBoard lineKeyBoard) {
        lineKeyBoards.add(lineKeyBoard);
    }

    public class Builder {

        private Builder() {

        }

        public Builder lineKeyBoard(LineKeyBoard lineKeyBoard) {
            KeyBoard.this.lineKeyBoards.add(lineKeyBoard);
            return this;
        }

        public Builder oneTime(boolean oneTime) {
            KeyBoard.this.oneTime = oneTime;
            return this;
        }

        public KeyBoard build() {
            return KeyBoard.this;
        }

    }
}
