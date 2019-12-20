package interfacelayer.gui;

import java.util.ArrayList;
import java.util.Arrays;

public class Route {

    private String name;
    private ArrayList<String> commands;

    public Route(String name) {
        this.commands = new ArrayList<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getCommands() {
        return commands;
    }

    public void setCommands(ArrayList<String> commands) {
        this.commands = new ArrayList<>(commands);
    }

    public boolean isValid() {
        return !this.commands.isEmpty();
    }

    public String toString() {
        StringBuilder res = new StringBuilder("> " + this.name + "[");
        for (int i = 0; i < this.commands.size(); i++) {
            res.append(this.commands.get(i));
            if (i != this.commands.size() - 1) {
                res.append(" ");
            }
        }
        res.append("]");
        return res.toString();
    }
}
