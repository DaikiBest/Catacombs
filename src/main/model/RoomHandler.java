package model;

import org.json.JSONObject;

import persistence.Writable;

//Represents a room handler which keeps track of the room number.
public class RoomHandler implements Writable {
    
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("roomNumber", roomNumber);
        return json;
    }
}
