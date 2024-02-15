import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EventManagementSystem eventManagementSystem = new EventManagementSystem(100);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nEvent Management System Menu:");
            System.out.println("1. Add Event");
            System.out.println("2. Add Guest to Event");
            System.out.println("3. Add Task to Event");
            System.out.println("4. Print Event Details");
            System.out.println("5. Print Guests for an Event");
            System.out.println("6. Search Guest in an Event");
            System.out.println("7. Print Tasks in the Queue");
            System.out.println("8. Remove Event");
            System.out.println("9. add event dependencies");
            System.out.println("10. print event dependencies");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addEvent(eventManagementSystem, scanner);
                    break;

                case 2:
                    addGuest(eventManagementSystem, scanner);
                    break;

                case 3:
                    addTask(eventManagementSystem, scanner);
                    break;

                case 4:
                    printEventDetails(eventManagementSystem);
                    break;

                case 5:
                    printGuestsForEvent(eventManagementSystem, scanner);
                    break;

                case 6:
                    searchGuestInEvent(eventManagementSystem, scanner);
                    break;

                case 7:
                    printTasksInQueue(eventManagementSystem);
                    break;

                case 8:
                    removeEvent(eventManagementSystem, scanner);
                    break;

                case 9:
                    addEventDependency(eventManagementSystem, scanner);
                    break;

                case 10:
                    printEventDependencies(eventManagementSystem, scanner);
                    break;
                case 11:
                    System.out.println("Exiting Event Management System. Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void addEventDependency(EventManagementSystem eventManagementSystem, Scanner scanner) {
        System.out.println("\nEnter Dependent Event Name:");
        String dependentEvent = scanner.nextLine();

        System.out.println("\nEnter Dependency Event Name:");
        String dependencyEvent = scanner.nextLine();

        eventManagementSystem.addEventDependency(dependentEvent, dependencyEvent);
    }

    private static void printEventDependencies(EventManagementSystem eventManagementSystem, Scanner scanner) {
        System.out.println("\nEnter Event Name to Print Dependencies:");
        String eventName = scanner.nextLine();

        eventManagementSystem.printEventDependencies(eventName);
    }

    private static void searchGuestInEvent(EventManagementSystem eventManagementSystem, Scanner scanner) {
        System.out.println("\nEnter Event Name:");
        String eventName = scanner.nextLine();

        for (EventWithGuestList eventWithGuestList : eventManagementSystem.eventGuestListArray) {
            if (eventWithGuestList != null && eventWithGuestList.event.name.equals(eventName)) {
                System.out.print("Enter Guest Name to search: ");
                String guestName = scanner.nextLine();

                Guest foundGuest = eventWithGuestList.searchGuest(guestName);

                if (foundGuest != null) {
                    System.out.println("Guest found in Event " + eventName + ":");
                    System.out.println("Guest Name: " + foundGuest.name + ", Contact: " + foundGuest.contact);
                } else {
                    System.out.println("Guest not found in Event " + eventName + ".");
                }
                return;
            }
        }
        System.out.println("Event not found or no guests for the event.");
    }

    private static void addEvent(EventManagementSystem eventManagementSystem, Scanner scanner) {
        System.out.println("\nEnter Event Details:");
        System.out.print("Name: ");
        String eventName = scanner.nextLine();
        System.out.print("Date: ");
        String date = scanner.nextLine();
        System.out.print("Time: ");
        String time = scanner.nextLine();
        System.out.print("Venue: ");
        String venue = scanner.nextLine();
        Event event = new Event(eventName, date, time, venue);
        eventManagementSystem.addEvent(event);
        System.out.println("Event added successfully!");
    }

    private static void addGuest(EventManagementSystem eventManagementSystem, Scanner scanner) {
        System.out.println("\nEnter Guest Details:");
        System.out.print("Name: ");
        String guestName = scanner.nextLine();
        System.out.print("Contact: ");
        String contact = scanner.nextLine();
        System.out.println("\nEnter Event Name:");
        String eventToAddGuest = scanner.nextLine();
        Guest guest = new Guest(guestName, contact);
        eventManagementSystem.addGuest(eventToAddGuest, guest);
        System.out.println("Guest added to the event successfully!");
    }

    private static void addTask(EventManagementSystem eventManagementSystem, Scanner scanner) {
        System.out.println("\nEnter Task Details:");
        System.out.print("Enter Event Name for the Task: ");
        String eventName = scanner.nextLine();

        EventWithGuestList eventWithGuestList = eventManagementSystem.findEventByName(eventName);

        if (eventWithGuestList != null) {
            System.out.print("Task: ");
            String task = scanner.nextLine();
            eventManagementSystem.addTask(eventWithGuestList.event, task);
            System.out.println("Task added to the event successfully!");
        } else {
            System.out.println("Event not found.");
        }
    }

    private static void removeEvent(EventManagementSystem eventManagementSystem, Scanner scanner) {
        System.out.println("\nEnter Event Name to remove:");
        String eventNameToRemove = scanner.nextLine();
        eventManagementSystem.removeEvent(eventNameToRemove);
    }

    private static void printEventDetails(EventManagementSystem eventManagementSystem) {
        System.out.println("\nEvent Details:");
        for (Event event : eventManagementSystem.eventHashTable) {
            if (event != null) {
                System.out.println("Event Name: " + event.name + ", Date: " + event.date + ", Venue: " + event.venue);
            }
        }
    }

    private static void printGuestsForEvent(EventManagementSystem eventManagementSystem, Scanner scanner) {
        System.out.println("\nEnter Event Name:");
        String eventName = scanner.nextLine();

        for (EventWithGuestList eventWithGuestList : eventManagementSystem.eventGuestListArray) {
            if (eventWithGuestList != null && eventWithGuestList.event.name.equals(eventName)) {
                System.out.println("\nGuests for Event " + eventName + ":");
                eventWithGuestList.guestBST.inOrderTraversal();
                return;
            }
        }
        System.out.println("Event not found or no guests for the event.");
    }

    private static void printTasksInQueue(EventManagementSystem eventManagementSystem) {
        System.out.println("\nTasks in the Queue:");
        for (int i = eventManagementSystem.tasksQueueFront; i <= eventManagementSystem.tasksQueueRear; i++) {
            System.out.println("Task: " + eventManagementSystem.eventTasksQueue[i]);
        }
    }
}

class EventManagementSystem {
    Event[] eventsArray;
    EventWithGuestList[] eventGuestListArray;
    String[] eventTasksQueue;
    int tasksQueueFront, tasksQueueRear;
    Event[] eventHashTable;
    String[] eventNames;
    String[][] eventDependencies;


    public EventManagementSystem(int maxSize) {
        eventsArray = new Event[maxSize];
        eventGuestListArray = new EventWithGuestList[maxSize];
        eventTasksQueue = new String[maxSize];
        tasksQueueFront = tasksQueueRear = -1;
        eventHashTable = new Event[maxSize];
        eventNames = new String[maxSize];
        eventDependencies = new String[maxSize][maxSize];
    }


    public void addEventDependency(String dependentEvent, String dependencyEvent) {
        int dependentIndex = findEventIndexByName(dependentEvent);
        int dependencyIndex = findEventIndexByName(dependencyEvent);

        if (dependentIndex != -1 && dependencyIndex != -1) {
            eventDependencies[dependentIndex][dependencyIndex] = "1";
            System.out.println("Dependency added: " + dependentEvent + " depends on " + dependencyEvent);
        } else {
            System.out.println("One or both events not found.");
        }
    }

    public void printEventDependencies(String eventName) {
        int eventIndex = findEventIndexByName(eventName);

        if (eventIndex != -1) {
            System.out.println("Dependencies for Event " + eventName + ":");
            for (int i = 0; i < eventDependencies[eventIndex].length; i++) {
                if ("1".equals(eventDependencies[eventIndex][i])) {
                    System.out.println(eventNames[i]);
                }
            }
        } else {
            System.out.println("Event not found.");
        }
    }

    private int findEventIndexByName(String eventName) {
        for (int i = 0; i < eventNames.length; i++) {
            if (eventNames[i] != null && eventNames[i].equalsIgnoreCase(eventName)) {
                return i;
            }
        }
        return -1;
    }

    public void addEvent(Event event) {
        for (int i = 0; i < eventsArray.length; i++) {
            if (eventsArray[i] == null) {
                eventsArray[i] = event;
                eventHashTable[i] = event;
                eventGuestListArray[i] = new EventWithGuestList(event);
                eventNames[i] = event.name;
                return;
            }
        }
    }

    public void removeEvent(String eventName) {
        for (int i = 0; i < eventsArray.length; i++) {
            if (eventsArray[i] != null && eventsArray[i].name.equals(eventName)) {
                removeTasksForEvent(eventName);

                removeGuestsForEvent(eventName);

                eventsArray[i] = null;
                eventGuestListArray[i] = null;
                eventHashTable[i] = null;
                eventNames[i] = null;
                updateEventDependencies(eventName);

                System.out.println("Event removed successfully!");
                return;
            }
        }
        System.out.println("Event not found.");
    }
    private void updateEventDependencies(String removedEventName) {
        int removedIndex = findEventIndexByName(removedEventName);

        if (removedIndex != -1) {
            for (int i = 0; i < eventDependencies.length; i++) {
                eventDependencies[i][removedIndex] = null; // Remove dependencies on the removed event
                eventDependencies[removedIndex][i] = null; // Remove dependencies from the removed event
            }
        } else {
            System.out.println("Error: Event index not found.");
        }
    }


    private void removeTasksForEvent(String eventName) {
        for (int i = tasksQueueFront; i <= tasksQueueRear; i++) {
            if (eventTasksQueue[i] != null && eventTasksQueue[i].startsWith(eventName)) {
                eventTasksQueue[i] = null;
            }
        }
        compactTasksQueue();
    }

    private void removeGuestsForEvent(String eventName) {
        for (EventWithGuestList eventWithGuestList : eventGuestListArray) {
            if (eventWithGuestList != null && eventWithGuestList.event.name.equals(eventName)) {
                eventWithGuestList.guestBST.clear();
            }
        }
    }

    private void compactTasksQueue() {
        int newIndex = 0;

        for (int i = tasksQueueFront; i <= tasksQueueRear; i++) {
            if (eventTasksQueue[i] != null) {
                eventTasksQueue[newIndex++] = eventTasksQueue[i];
            }
        }

        tasksQueueFront = 0;
        tasksQueueRear = newIndex - 1;
    }

    public EventWithGuestList findEventByName(String eventName) {
        for (EventWithGuestList eventWithGuestList : eventGuestListArray) {
            if (eventWithGuestList != null && eventWithGuestList.event.name.equalsIgnoreCase(eventName)) {
                return eventWithGuestList;
            }
        }
        return null;
    }

    public void addGuest(String eventName, Guest guest) {
        for (EventWithGuestList eventWithGuestList : eventGuestListArray) {
            if (eventWithGuestList != null && eventWithGuestList.event.name.equals(eventName)) {
                eventWithGuestList.addGuest(guest);
                return;
            }
        }
    }

    public void addTask(Event event, String task) {
        if (tasksQueueFront == -1) {
            tasksQueueFront = 0;
        }
        tasksQueueRear++;
        eventTasksQueue[tasksQueueRear] = task;
    }
}

class EventWithGuestList {
    Event event;
    GuestBST guestBST;

    public EventWithGuestList(Event event) {
        this.event = event;
        this.guestBST = new GuestBST();
    }

    public void addGuest(Guest guest) {
        guestBST.insert(guest);
    }

    public Guest searchGuest(String guestName) {
        return guestBST.search(guestName);
    }
}

class Guest {
    String name;
    String contact;

    public Guest(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }
}

class GuestBST {
    static class TreeNode {
        Guest guest;
        TreeNode left, right;

        public TreeNode(Guest guest) {
            this.guest = guest;
            this.left = this.right = null;
        }
    }

    private TreeNode root;

    public GuestBST() {
        this.root = null;
    }

    public void insert(Guest guest) {
        root = insertRecursive(root, guest);
    }

    private TreeNode insertRecursive(TreeNode root, Guest guest) {
        if (root == null) {
            root = new TreeNode(guest);
            return root;
        }

        if (guest.name.compareTo(root.guest.name) < 0) {
            root.left = insertRecursive(root.left, guest);
        } else if (guest.name.compareTo(root.guest.name) > 0) {
            root.right = insertRecursive(root.right, guest);
        }

        return root;
    }

    public Guest search(String guestName) {
        return searchRecursive(root, guestName);
    }

    private Guest searchRecursive(TreeNode root, String guestName) {
        if (root == null || root.guest.name.equals(guestName)) {
            return (root != null) ? root.guest : null;
        }

        if (guestName.compareTo(root.guest.name) < 0) {
            return searchRecursive(root.left, guestName);
        } else {
            return searchRecursive(root.right, guestName);
        }
    }

    public void inOrderTraversal() {
        inOrderRecursive(root);
    }

    private void inOrderRecursive(TreeNode root) {
        if (root != null) {
            inOrderRecursive(root.left);
            System.out.println("Guest Name: " + root.guest.name + ", Contact: " + root.guest.contact);
            inOrderRecursive(root.right);
        }
    }

    public void clear() {
        root = null;
    }
}

class Event {
    String name;
    String date;
    String time;
    String venue;

    public Event(String name, String date, String time, String venue) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.venue = venue;
    }
}
