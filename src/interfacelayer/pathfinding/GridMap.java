package interfacelayer.pathfinding;

import java.util.ArrayList;
import java.util.Map;

public enum  GridMap
{
    INSTANCE;

    private Map<Node, Node> map;
    private ArrayList<Node> route;

    private boolean hasRoute = false;

    public void recieveMap      (Map<Node,Node> map)
    {
        this.map = map;
    }

    public void recieveRoute    (ArrayList<Node> route)
    {
        this.route = route;
    }
}
