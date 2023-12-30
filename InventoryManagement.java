import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class InventoryItem {
    private String id;
    private String name;
    private double price;
    private String supplier;

    public InventoryItem(String id, String name, double price, String supplier) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.supplier = supplier;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getSupplier() {
        return supplier;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Price: $" + price + ", Supplier: " + supplier;
    }
}

public class InventoryManagement {
    private static Map<String, InventoryItem> inventory = new HashMap<>();
    private static final String EXTERNAL_API_URL = "https://api.example.com/items/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Item");
            System.out.println("2. Edit Item");
            System.out.println("3. Delete Item");
            System.out.println("4. List Items");
            System.out.println("5. Fetch Item Details from API");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addItem(scanner);
                    break;
                case 2:
                    editItem(scanner);
                    break;
                case 3:
                    deleteItem(scanner);
                    break;
                case 4:
                    listItems();
                    break;
                case 5:
                    fetchItemDetails(scanner);
                    break;
                case 6:
                    System.out.println("Exiting program.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addItem(Scanner scanner) {
        System.out.print("Enter item ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter item price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter supplier: ");
        String supplier = scanner.nextLine();

        InventoryItem newItem = new InventoryItem(id, name, price, supplier);
        inventory.put(id, newItem);

        System.out.println("Item added successfully!");
    }

    private static void editItem(Scanner scanner) {
        System.out.print("Enter item ID to edit: ");
        String idToEdit = scanner.nextLine();

        if (inventory.containsKey(idToEdit)) {
            InventoryItem item = inventory.get(idToEdit);

            System.out.print("Enter new item name (current: " + item.getName() + "): ");
            String newName = scanner.nextLine();
            System.out.print("Enter new item price (current: " + item.getPrice() + "): ");
            double newPrice = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character
            System.out.print("Enter new supplier (current: " + item.getSupplier() + "): ");
            String newSupplier = scanner.nextLine();

            item = new InventoryItem(idToEdit, newName, newPrice, newSupplier);
            inventory.put(idToEdit, item);

            System.out.println("Item edited successfully!");
        } else {
            System.out.println("Item with ID " + idToEdit + " not found.");
        }
    }

    private static void deleteItem(Scanner scanner) {
        System.out.print("Enter item ID to delete: ");
        String idToDelete = scanner.nextLine();

        if (inventory.containsKey(idToDelete)) {
            inventory.remove(idToDelete);
            System.out.println("Item deleted successfully!");
        } else {
            System.out.println("Item with ID " + idToDelete + " not found.");
        }
    }

    private static void listItems() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Inventory Items:");
            for (InventoryItem item : inventory.values()) {
                System.out.println(item);
            }
        }
    }

    private static void fetchItemDetails(Scanner scanner) {
        System.out.print("Enter item ID or name to fetch details from API: ");
        String input = scanner.nextLine();

        try {
            URL url = new URL(EXTERNAL_API_URL + input);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                JsonObject jsonResponse = ((Object) JsonParser.parseString(response.toString())).getAsJsonObject();

                // Assuming the API response contains fields like "price" and "supplier"
                double apiPrice = ((Object) jsonResponse.get("price")).getAsDouble();
                String apiSupplier = ((Object) jsonResponse.get("supplier")).toString();

                System.out.println("API Details:");
                System.out.println("Price: $" + apiPrice);
                System.out.println("Supplier: " + apiSupplier);
            } else {
                System.out.println("Failed to fetch details from API. Response Code: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
