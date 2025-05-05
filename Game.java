import java.util.Scanner;

public class Game {
    private Room currentRoom;
    private Room wald;
    private Room friedhof;
    private Room kirche;
    private Room schloss;
    private Room taverne;
    boolean objectTaken = false;

    public Game() {
        createRooms();
    }

    private void createRooms() {
        schloss = new Room("Du stehst vor dem alten Schloss.");
        taverne = new Room("Du bist in der Taverne. Es riecht nach Bier und Rauch.");
        kirche = new Room("Die Kirche ist kalt und leer.");
        friedhof = new Room("Nebelschwaden ziehen über den Friedhof.");
        wald = new Room("Du stehst am Rand eines dunklen Waldes... Niemand kehrt von hier zurück.");

        // Ausgänge setzen (n, e, s, w)
        schloss.setExits(null, null, taverne, wald);
        taverne.setExits(schloss, null, null, kirche);
        kirche.setExits(wald, taverne, null, friedhof);
        friedhof.setExits(wald, kirche, null, null);
        wald.setExits(null, null, null, null);

        currentRoom = schloss;
    }

    private void goRoom(String direction, Scanner scanner) {
        Room nextRoom = null;

        switch (direction) {
            case "north":
                nextRoom = currentRoom.getNorth();
                break;
            case "east":
                nextRoom = currentRoom.getEast();
                break;
            case "south":
                nextRoom = currentRoom.getSouth();
                break;
            case "west":
                nextRoom = currentRoom.getWest();
                break;
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
            } else if (currentRoom == friedhof && objectTaken == false) {
                System.out.println(
                        "\nDu laeufst um das Friedhof herum. Du spürst die tote Seelen schauen dich zu von einen Gräben");
                System.out.println(
                        "\nUnd doch findest du ein uralter halbstreckender aus einem alten Graben Objekt, verstehst aber nicht was es genau ist. Mitnehmen? (ja/nein)");
                System.out.print("> ");
                String antwort = scanner.nextLine().trim().toLowerCase();

                if (antwort.equals("ja")) {
                    System.out.println("Du nimmst es mit. Was machst du jetzt?");
                    objectTaken = true;
                } else {
                    System.out.println("Du lässt das Objekt liegen. Was ist, wenn es verflucht ist?");
                }
            } else if (currentRoom == kirche && objectTaken == true) {
                System.out.println(
                        "Du bist wieder in der Kirche. Jedoch felt es dir erst jetzt auf, dass in dem weitesten, dünkleren Ecke des Raums steht es ein Opferaltar. \nDu entscheidest, den Objekt von Friedhof dadrauf zu stellen. Und - du siehst den hellen zum Himmel ausstralenden Licht. \nEs ist ein guter Zeichen. \nTO BE CONTINUED");
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

            if (input.equals("quit") || input.equals("exit")) {
                playing = false;
            } else if (input.equals("look")) {
                System.out.println(currentRoom.getDescription());
            } else if (input.startsWith("go ")) {
                String direction = input.substring(3);
                goRoom(direction, scanner);
            } else if (input.equals("help")) {
                System.out.println("Verfügbare Befehle: go [richtung], look, help, quit");
            } else {
                System.out.println("Ich kenne diesen Befehl nicht.");
            }
        }

        System.out.println("Danke fürs Spielen!");
        scanner.close();
    }
}