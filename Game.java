import java.util.ResourceBundle;
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

    public class GameTexts {
        private static final ResourceBundle texts = ResourceBundle.getBundle("dialog");

        public static String get(String key) {
            return texts.getString(key);
        }
    }

    private void createRooms() {
        schloss = new Room(GameTexts.get("room.schloss.description"));
        taverne = new Room(GameTexts.get("room.taverne.description"));
        kirche = new Room(GameTexts.get("room.kirche.description"));
        friedhof = new Room(GameTexts.get("room.friedhof.description"));
        wald = new Room(GameTexts.get("room.wald.description"));

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
                System.out.println(GameTexts.get("room.direction.unknown"));
                return;
        }

        if (nextRoom == null) {
            System.out.println(GameTexts.get("game.direction.invalid"));
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getDescription());

            if (currentRoom == wald) {
                System.out.println(GameTexts.get("event.wald.gameover"));
                System.exit(0);
            } else if (currentRoom == friedhof && objectTaken == false) {
                System.out.println(GameTexts.get("event.friedhof.fund"));
                System.out.println(GameTexts.get("event.friedhof.frage"));
                System.out.print("> ");
                String antwort = scanner.nextLine().trim().toLowerCase();

                if (antwort.equals("ja")) {
                    System.out.println(GameTexts.get("event.friedhof.mitgenommen"));
                    objectTaken = true;
                } else {
                    System.out.println(GameTexts.get("event.friedhof.gelassen"));
                }
            } else if (currentRoom == kirche && objectTaken == true) {
                System.out.println(GameTexts.get("event.kirche.gewonnen"));
                System.exit(0);
            }

        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        boolean playing = true;

        System.out.println(GameTexts.get("game.welcome"));
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
                System.out.println(GameTexts.get("game.command.help"));
            } else {
                System.out.println(GameTexts.get("game.command.unknown"));
            }
        }

        System.out.println(GameTexts.get("game.command.quit"));
        scanner.close();
    }
}