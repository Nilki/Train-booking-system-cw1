import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main extends Application {
    public static final int SEATING_CAPACITY = 42;


    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage priStage) throws Exception {
        //use hashmap integer value to seats string to customer
        HashMap<Integer, String> ColomboToBadulla = new HashMap<>();
        HashMap<Integer, String> BadullaToColombo = new HashMap<>();

            // select menu
        Loop:
        while (true) {
            System.out.println("Denuwara manike train A/C compartment");
            System.out.println(" ");
            System.out.println("Press a Key.....");
            System.out.println("\tA : Add a Customer ");
            System.out.println("\tV : View Seats ");
            System.out.println("\tE : Show Empty Seats");
            System.out.println("\tD : Delete Customer from seat ");
            System.out.println("\tF : Find the seat of customer ");
            System.out.println("\tS : Save");
            System.out.println("\tL : Load");
            System.out.println("\tO : View seats Ordered alphabetically by name");
            System.out.println("\tQ : Quit");
            System.out.println(" ");
            //user input
            Scanner input = new Scanner(System.in);
            System.out.print("Enter your choice here :  ");
            String answerKey = input.nextLine();

            switch (answerKey) {
                case "A":
                case "a":
                    addCustomer(ColomboToBadulla, BadullaToColombo);//call method
                    break;
                case "V":
                case "v":
                    viewSeats(ColomboToBadulla, BadullaToColombo);//call method
                    break;
                case "E":
                case "e":
                    emptySeats(ColomboToBadulla, BadullaToColombo);//call method
                    break;
                case "D":
                case "d":
                    deleteSeat(ColomboToBadulla, BadullaToColombo);//call method
                    break;
                case "F":
                case "f":
                    findSeats(ColomboToBadulla, BadullaToColombo);//call method
                    break;
                case "S":
                case "s":
                    saveData(ColomboToBadulla, BadullaToColombo);//call method
                    break;
                case "L":
                case "l":
                    loadData(ColomboToBadulla, BadullaToColombo);//call method
                    break;
                case "O":
                case "o":
                    viewByOrder(ColomboToBadulla, BadullaToColombo);//call method
                    break;
                case "Q":
                case "q":
                    System.out.println("Quit the menu");//exit the menu
                    break Loop;
                default:
                    System.out.println("Sorry.Invalid Input."); //if user input incorrect char this will display
            }
        }
    }

    public HashMap<Integer, String> addCustomer(HashMap<Integer, String> colomboToBadulla, HashMap<Integer, String> badullaToColombo) {

        HashMap<Integer, String> hashMap = null;
        //ask route
        System.out.println("Which route you want to choose : ");
        Scanner scanner = new Scanner(System.in);
        System.out.println("    A : Colombo to Badulla");
        System.out.println("    B : Badulla to Colombo");
        System.out.print("Enter option A or B : ");
        String option = scanner.next();
        String name = null;
        if(option.equals("A") || option.equals("a")){
            System.out.println("Trip to Colombo to Badulla");
            hashMap = colomboToBadulla; //input data colomboToBadulla hashmap
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter your first name :");
            String firstName = sc.nextLine();
            System.out.print("Enter your last name :");
            String lastName = sc.nextLine();
            name = (firstName + " " + lastName);
        }else if (option.equals("B") || option.equals("b")){
            System.out.println("Trip to Badulla to Colombo");
            hashMap = badullaToColombo;
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter your first name :");
            String firstName = sc.nextLine();
            System.out.print("Enter your last name :");
            String lastName = sc.nextLine();
            name = (firstName + " " + lastName);
        }
        else {
            System.out.print("Wrong Input \n\n");
        }
           //GUI add customer
        if (name != null) {
            ToggleButton[] btn = new ToggleButton[SEATING_CAPACITY];
            Button book;
            TextField customer;

            Stage primaryStage = new Stage();
            primaryStage.setTitle("Book a Seat");
            FlowPane layout = new FlowPane();
            layout.setPadding(new Insets(5));
            layout.setHgap(10);
            layout.setVgap(10);
            layout.setStyle("-fx-background-color: black;");

            Label cust_lbl = new Label();
            cust_lbl.setId("cust_lbl");
            cust_lbl.setText("         -  Denuwara Manike  -");
            cust_lbl.setFont(Font.font("Cambria",FontWeight.BOLD, 28));
            cust_lbl.setTextFill(Color.web("white"));
            layout.getChildren().add(cust_lbl);

            for (int i = 0; i < SEATING_CAPACITY; i++) { //diplay 42 seats
                btn[i] = new ToggleButton("Seat " + (i + 1));
                btn[i].setPrefHeight(30);
                btn[i].setPrefWidth(120);
                btn[i].setFont(Font.font("Cambria", 18));
                layout.getChildren().add(btn[i]);
                btn[i].setId(Integer.toString(i));
                //btn[i].setOnAction(this);

                if (hashMap.containsKey(i)) {
                    btn[i].setDisable(true);
                    btn[i].setStyle("-fx-background-color: #630909; -fx-text-fill: #faf5f5;"); //Booked seats
                } else {
                    btn[i].setStyle("-fx-background-color: #d4d4d4; -fx-text-fill: black;");// empty seats
                }
            }
                // bottom book button
            book = new Button("Book");
            book.setPrefWidth(120);
            book.setPrefHeight(35);
            book.setFont(Font.font("Cambria", 18));
            book.setStyle("-fx-background-color: #729651; -fx-text-fill: white;");
            layout.getChildren().add(book);

            Scene scene = new Scene(layout, 390, 710);
            primaryStage.setScene(scene);

            HashMap<Integer, String> finalHashMap = hashMap;
            String finalName = name;
            book.setOnAction(event -> {
                System.out.println("------------------------");
                System.out.println("Your booking is success");
                System.out.println("------------------------");
                for (int i = 0; i < SEATING_CAPACITY; i++) {
                    if (event.getSource() == book) {
                        // if book is success,final name and seat number will display the console
                        if (btn[i].isSelected()) {
                            finalHashMap.put(i, finalName);
                            System.out.println("Name : " + finalName);
                            int seat = i+1;
                            System.out.println("Seat Number : " + seat);
                            System.out.println("");

                        }

                    }
                }
                primaryStage.close();
            });

            primaryStage.showAndWait();

            return hashMap;
        }
        return hashMap;
    }

    public static void viewSeats(HashMap<Integer, String> colomboToBadulla, HashMap<Integer, String> badullaToColombo) {
        HashMap<Integer, String> hashMap = null;
        System.out.println("Which route you want to choose : ");
        Scanner scanner = new Scanner(System.in);
        System.out.println("    A : Colombo to Badulla");
        System.out.println("    B : Badulla to Colombo");
        System.out.print("Enter option A or B : ");
        String option = scanner.next();

        if(option.equals("A") || option.equals("a")){
            System.out.println("Trip to Colombo to Badulla");
            hashMap = colomboToBadulla;
        }else if (option.equals("B") || option.equals("b")){
            System.out.println("Trip to Badulla to Colombo");
            hashMap = badullaToColombo;
        }
        else {
            System.out.print("Wrong Input");
        }

        Stage primaryStage = new Stage();
        primaryStage.setTitle("View Seats");
        FlowPane layout = new FlowPane();
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(4));
        layout.setStyle("-fx-background-color: black;");

        Button[] button = new Button[SEATING_CAPACITY];

        Label cust_lbl = new Label();
        Label cust_lbl_1 = new Label();
        cust_lbl.setId("cust_lbl");
        cust_lbl.setText("        -  Denuwara Manike  -");
        cust_lbl.setFont(Font.font("Cambria",FontWeight.BOLD, 28));
        cust_lbl.setTextFill(Color.web("white"));
        layout.getChildren().add(cust_lbl);

        for (int i = 0; i < SEATING_CAPACITY; i++) {
            button[i] = new Button("Seat " + (i + 1));
            button[i].setPrefHeight(30);
            button[i].setPrefWidth(120);
            button[i].setFont(Font.font("Cambria", 18));
            layout.getChildren().add(button[i]);
            if (hashMap.containsKey(i)) {
                button[i].setStyle("-fx-background-color: #630909; -fx-text-fill: #faf5f5;");
            } else {
                button[i].setStyle("-fx-background-color: #729651; -fx-text-fill: white;");
            }
        }

        cust_lbl_1.setId("cust_lbl");
        cust_lbl_1.setText(" #Red color seats are booked !");
        cust_lbl_1.setFont(Font.font("Cambria", FontWeight.BOLD, 18));
        cust_lbl_1.setTextFill(Color.web("white"));
        layout.getChildren().add(cust_lbl_1);

        Scene scene = new Scene(layout, 389, 705);
        primaryStage.setScene(scene);

        primaryStage.showAndWait();
    }

    public void emptySeats(HashMap<Integer, String> colomboToBadulla, HashMap<Integer, String> badullaToColombo) {

        HashMap<Integer, String> hashMap = null;
        System.out.println("Which route you want to choose : ");
        Scanner scanner = new Scanner(System.in);
        System.out.println("    A : Colombo to Badulla");
        System.out.println("    B : Badulla to Colombo");
        System.out.print("Enter option A or B : ");
        String option = scanner.next();

        if(option.equals("A") || option.equals("a")){
            System.out.println("Trip to Colombo to Badulla");
            hashMap = colomboToBadulla;
        }else if (option.equals("B") || option.equals("b")){
            System.out.println("Trip to Badulla to Colombo");
            hashMap = badullaToColombo;
        }
        else {
            System.out.print("Wrong Input");
        }

        Button btn2[] = new Button[SEATING_CAPACITY];

        Stage primaryStage = new Stage();
        primaryStage.setTitle("View Available Seats");
        FlowPane layout = new FlowPane();
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setStyle("-fx-background-color: black;");
        layout.setPadding(new Insets(5));

        Label cust_lbl = new Label();
        Label cust_lbl_1 = new Label();
        cust_lbl.setId("cust_lbl");
        cust_lbl.setText("        - Denuwara Manike -");
        cust_lbl.setFont(Font.font("Cambria",FontWeight.BOLD, 28));
        cust_lbl.setTextFill(Color.web("white"));
        layout.getChildren().add(cust_lbl);


        for (int i = 0; i < SEATING_CAPACITY; i++) {
            if (!hashMap.containsKey(i)) {
                btn2[i] = new Button("Seat " + (i + 1));
                btn2[i].setStyle("-fx-background-color: #729651; -fx-text-fill: white;");
                btn2[i].setPrefHeight(35);
                btn2[i].setPrefWidth(120);
                btn2[i].setFont(Font.font("Cambria", 18));
                layout.getChildren().add(btn2[i]);
            }
        }

        Scene scene = new Scene(layout, 390, 690);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }

    public HashMap<Integer, String> deleteSeat(HashMap<Integer, String> colomboToBadulla, HashMap<Integer, String> badullaToColombo) {

        HashMap<Integer, String> hashMap = null;
        System.out.println("Which route you want to choose : ");
        Scanner scanner = new Scanner(System.in);
        System.out.println("    A : Colombo to Badulla");
        System.out.println("    B : Badulla to Colombo");
        System.out.print("Enter option A or B : ");
        String option = scanner.next();

        if (option.equals("A") || option.equals("a")) {
            System.out.println("Trip to Colombo to Badulla");
            hashMap = colomboToBadulla;

        } else if (option.equals("B") || option.equals("b")) {
            System.out.println("Trip to Badulla to Colombo");
            hashMap = badullaToColombo;
        } else {
            System.out.print("Wrong Input");
        }

        Scanner input = new Scanner(System.in);
        System.out.print("Enter your first name :");
        String firstName = input.nextLine();
        System.out.print("Enter your last name :");
        String lastName = input.nextLine();
        String Name = (firstName + " " + lastName);

        if (hashMap.containsValue(Name)) {
            System.out.println("These seats are booked by " + Name); // find seat number equals name
            for (int i = 0; i < SEATING_CAPACITY; i++) {
                if (!hashMap.containsKey(i))
                    continue;
                String temp = hashMap.get(i);
                if (temp.equals(Name)) {
                    System.out.println("\tSeat Number " + (i + 1)); // display seat numbers to own
                }
            }
            Scanner input_1 = new Scanner(System.in);
            System.out.print("Enter the Seat Number to delete : ");
            int seatNumber = input.nextInt() - 1; //remove seats
            System.out.println("Seat Number " + (seatNumber + 1) + " which belongs to " + hashMap.remove(seatNumber) + " is removed.");


        } else {
            System.out.println("--------------------------------------------------");
            System.out.println(" !!! There is no any seat booked by this customer.");
            System.out.println("--------------------------------------------------");
        }

        return hashMap;
    }

    public void findSeats(HashMap<Integer, String> colomboToBadulla, HashMap<Integer, String> badullaToColombo) {
        HashMap<Integer, String> hashMap = null;
        System.out.println("Which route you want to choose : ");
        Scanner scanner = new Scanner(System.in);
        System.out.println("    A : Colombo to Badulla");
        System.out.println("    B : Badulla to Colombo");
        System.out.print("Enter option A or B : ");
        String option = scanner.next();

        if(option.equals("A") || option.equals("a")){
            System.out.println("Trip to Colombo to Badulla");
            hashMap = colomboToBadulla;
        }else if (option.equals("B") || option.equals("b")){
            System.out.println("Trip to Badulla to Colombo");
            hashMap = badullaToColombo;
        }
        else {
            System.out.print("Wrong Input");
        }
        //ask name
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your first name :");
        String firstName = input.nextLine();
        System.out.print("Enter your last name :");
        String lastName = input.nextLine();
        String customerName = (firstName + " " + lastName);

        if (hashMap.containsValue(customerName)) {
            System.out.println("These seats are booked by " + customerName);
            for (int i = 0; i < SEATING_CAPACITY; i++) {
                if (!hashMap.containsKey(i))
                    continue;
                String temp = hashMap.get(i);
                if (temp.equals(customerName)) {
                    System.out.println("\tSeat Number " + (i + 1)); //display seats customer booked
                }
            }
        } else {  // input incorrect name this will run
            System.out.println("---------------------------------------------------");
            System.out.println(" !!! There is no any seat booked by this customer.");
            System.out.println("---------------------------------------------------");
        }
    }

    public void saveData(HashMap<Integer, String> colomboToBadulla, HashMap<Integer, String> badullaToColombo) {
        try {
            FileWriter myWriter = new FileWriter("CustomerDataCtoB.txt"); // passenger data write text files
            FileWriter myWriter2 = new FileWriter("CustomerDataBtoC.txt");

            StringBuilder dataToSave = new StringBuilder();
            StringBuilder dataToSave2 = new StringBuilder();
            for (int i = 0; i < SEATING_CAPACITY; i++) {
                dataToSave.append(colomboToBadulla.get(i) + "\n"); //update value of the inputs
                dataToSave2.append(badullaToColombo.get(i) + "\n");
            }

            myWriter.write(String.valueOf(dataToSave)); //writes inputs
            myWriter2.write(String.valueOf(dataToSave2));
            myWriter.close();
            myWriter2.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void loadData(HashMap<Integer, String> colomboToBadulla, HashMap<Integer, String> badullaToColombo) {

        System.out.println("Customer name\t\t\t\t\t Seat no");
        try {
            File myObj = new File("CustomerDataCtoB.txt"); //text file path
            File myObj2 = new File("CustomerDataBtoC.txt");

            Scanner myReader = new Scanner(myObj);
            Scanner myReader2 = new Scanner(myObj2);
            for (int i = 0; i < SEATING_CAPACITY; i++) {
                // read customers name
                String customerName = myReader.nextLine();
                String customerName2 = myReader2.nextLine();

                int seat = i + 1;

                if (!customerName.equals("null")) {
                    System.out.println("Colombo To Badulla : " + customerName + "\t\t\t" + seat);
                    colomboToBadulla.put(i, customerName);
                }

                if (!customerName2.equals("null")) {
                    System.out.println("Badulla To Colombo : " + customerName2 + "\t\t\t" + seat);
                    badullaToColombo.put(i, customerName2);
                }
            }
            myReader.close();
            myReader2.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();

        }

    }

    public void viewByOrder(HashMap<Integer, String> colomboToBadulla, HashMap<Integer, String> badullaToColombo) {

        HashMap<Integer, String> hashMap = null;
        System.out.println("Which route you want to choose : ");
        Scanner scanner = new Scanner(System.in);
        System.out.println("    A : Colombo to Badulla");
        System.out.println("    B : Badulla to Colombo");
        System.out.print("Enter option A or B : ");
        String option = scanner.next();

        if(option.equals("A") || option.equals("a")){
            System.out.println("Trip to Colombo to Badulla");
            hashMap = colomboToBadulla;
        }else if (option.equals("B") || option.equals("b")){
            System.out.println("Trip to Badulla to Colombo");
            hashMap = badullaToColombo;
        }
        else {
            System.out.print("Wrong Input");
        }

        ArrayList<String> newArraylist = new ArrayList<>();
        newArraylist.addAll(hashMap.values()); // add all values to array list
        ArrayList<Integer> newArraylist1 = new ArrayList<>();
        newArraylist1.addAll(hashMap.keySet());// add all key set to array list

        //bubble sort implementation
        for (int j = 0; j < newArraylist.size(); j++) {

            for (int i = j; i < newArraylist.size(); i++) {
                //swap if j is greater than i
                if ((newArraylist.get(i)).compareToIgnoreCase(newArraylist.get(j)) < 0) {
                    String temp = newArraylist.get(j);
                    newArraylist.set(j, newArraylist.get(i));
                    newArraylist.set(i, temp);
                    Integer test = newArraylist1.get(j);
                    newArraylist1.set(j, newArraylist1.get(i));
                    newArraylist1.set(i, test);
                }
            }
            int seat = newArraylist1.get(j) + 1;
            System.out.println(newArraylist.get(j)+ "  "+seat);
        }
        System.out.println("Sorted the booking by customer name");
    }


}








