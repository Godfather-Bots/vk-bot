package org.sadtech.vkbot.core.keyboard;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class KeyBoard {

    private List<LineKeyBoard> lineKeyBoards = new ArrayList<>();
    private boolean oneTime;

    public KeyBoard() {

    }

    public KeyBoard(List<LineKeyBoard> lineKeyBoards, boolean oneTime) {
        this.lineKeyBoards = lineKeyBoards;
        this.oneTime = oneTime;
    }

    public void setOneTime(boolean oneTime) {
        this.oneTime = oneTime;
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

    public void addLine(LineKeyBoard lineKeyBoard) {
        lineKeyBoards.add(lineKeyBoard);
    }

    public static Builder builder() {
        return new KeyBoard().new Builder();
    }

    public class Builder {

        private Builder() {

        }

        public Builder setLineKeyBoard(LineKeyBoard lineKeyBoard) {
            KeyBoard.this.lineKeyBoards.add(lineKeyBoard);
            return this;
        }

        public Builder setOneTime(boolean oneTime) {
            KeyBoard.this.oneTime = oneTime;
            return this;
        }

        public KeyBoard build() {
            return KeyBoard.this;
        }

    }
}
