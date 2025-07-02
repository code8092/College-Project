package com.rooms.RoomKhojo.Security;

import java.util.Base64;

public class JwtDecoder {
    public static void decodeToken(String token) {
        String[] parts = token.split("\\.");
        String payload = new String(Base64.getDecoder().decode(parts[1]));
        System.out.println("Payload: " + payload);
    }
}
