package interfacelayer.gui;

import java.util.ArrayList;

public class Route {
    private final String name;
    private ArrayList<String> commands;

    public Route(ArrayList<String> commands, String name) {
        this.commands = commands;
        this.name = name;
    }

    public ArrayList<String> getCommands() {
        return commands;
    }

    public void setCommands(ArrayList<String> commands) {
        this.commands = commands;
    }

    public String toString() {
        return "> " + this.name;
    }
}
