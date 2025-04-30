import java.util.Scanner;

public class Game {
    private Room currentRoom;
    private Room wald;

    public Game() {
        createRooms();
    }

    private void createRooms() {
        Room schloss = new Room("Du stehst vor dem alten Schloss.");
        Room taverne = new Room("Du bist in der Taverne. Es riecht nach Bier und Rauch.");
        Room kirche = new Room("Die Kirche ist kalt und leer.");
        Room friedhof = new Room("Nebelschwaden ziehen über den Friedhof.");
        wald = new Room("Du stehst am Rand eines dunklen Waldes... Niemand kehrt von hier zurück.");


        // Ausgänge setzen (n, e, s, w)
        schloss.setExits(null, null, taverne, wald);
        taverne.setExits(schloss, null, null, kirche);
        kirche.setExits(wald, taverne, null, friedhof);
        friedhof.setExits(wald, kirche, null, null);
        wald.setExits(null, null, null, null);

        currentRoom = schloss;
    }

    private void goRoom(String direction) {
        Room nextRoom = null;
    
        switch (direction) {
            case "north": nextRoom = currentRoom.getNorth(); break;
            case "east":  nextRoom = currentRoom.getEast();  break;
            case "south": nextRoom = currentRoom.getSouth(); break;
            case "west":  nextRoom = currentRoom.getWest();  break;
            default:
                System.out.println("Unbekannte Richtung.");
                return;
        }
    
        if (nextRoom == null) {
            System.out.println("Dort kannst du nicht hingehen.");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getDescription());

            if (currentRoom == wald) {
                System.out.println("\nEin kalter Schauer läuft dir über den Rücken...");
                System.out.println("Du hast den Wald betreten und wirst nie wieder gesehen.\nGAME OVER");
                System.exit(0);
            }
        }
    }
    

    public void play() {
        Scanner scanner = new Scanner(System.in);
        boolean playing = true;

        System.out.println("Willkommen im Renaissance-Adventure!");
        System.out.println(currentRoom.getDescription());

        while (playing) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();
    
            if (input.equals("quit")) {
                playing = false;
            } else if (input.equals("look")) {
                System.out.println(currentRoom.getDescription());
            } else if (input.startsWith("go ")) {
                String direction = input.substring(3);
                goRoom(direction);
            } else {
                System.out.println("Ich kenne diesen Befehl nicht.");
            }
        }

        System.out.println("Danke fürs Spielen!");
        scanner.close();
    }
}