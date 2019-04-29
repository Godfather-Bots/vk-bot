package org.sadtech.vkbot.core.keyboard;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

public class LineKeyBoard {

    private List<ButtonKeyBoard> buttonKeyBoards = new ArrayList<>();

    public LineKeyBoard() {

    }

    public LineKeyBoard(List<ButtonKeyBoard> buttonKeyBoards) {
        this.buttonKeyBoards = buttonKeyBoards;
    }

    public JsonArray getLine() {
        JsonArray line = new JsonArray();
        for (ButtonKeyBoard buttonKeyBoard : buttonKeyBoards) {
            line.add(buttonKeyBoard.getButton());
        }
        return line;
    }

    public static Builder builder() {
        return new LineKeyBoard().new Builder();
    }

    public class Builder {

        private Builder() {

        }

        public Builder setButtonKeyBoard(ButtonKeyBoard buttonKeyBoard) {
            LineKeyBoard.this.buttonKeyBoards.add(buttonKeyBoard);
            return this;
        }

        public LineKeyBoard build() {
            return LineKeyBoard.this;
        }
    }
}
