package gui;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * route class that stores the instructions to be done by the BoeBot
 */
public class Route {

    private String name;
    private ArrayList<String> commands;
    // 1 rechts, 2 links, 0 rechtdoor

    /**
     * make a new route
     *
     * @param name the name to give the route
     */
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

    public int[] routeToNumbers() {
        int[] res = new int[this.commands.size()];
        if (this.isValid()) {
            for (int i = 0; i < this.commands.size(); i++) {
                String cur = this.commands.get(i);
                switch (cur) {
                    case "Forward":
                        res[i] = 0;
                        break;
                    case "Left":
                        res[i] = 2;
                        break;
                    case "Right":
                        res[i] = 1;
                        break;
                }
            }
        }
        return res;
    }

    /**
     * checks if the route is valid
     *
     * @return <code>true</code> if this route's commands are not empty, <code>false</code> otherwise
     */
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
