package model;

//Represents a room handler which keeps track of the room number.
public class RoomHandler {
    
    int roomNumber;

    // create a room handler with the roomNumber as 0
    public RoomHandler() {
        roomNumber = 0;
    }

    // MODIFIES: this
    // EFFECTS: increase the room number by one
    public void increaseRoomNum() {
        roomNumber++;
    }

    public void setRoomNum(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomNum() {
        return roomNumber;
    }

}
