public class Room {
    private String description;
    private Room north, east, south, west;

    public Room(String description) {
        this.description = description;
    }

    public void setExits(Room n, Room e, Room s, Room w) {
        this.north = n;
        this.east = e;
        this.south = s;
        this.west = w;
    }

    public String getDescription() {
        return description;
    }

    public Room getNorth() {
        return north;
    }
    
    public Room getEast() {
        return east;
    }
    
    public Room getSouth() {
        return south;
    }
    
    public Room getWest() {
        return west;
    }
}
