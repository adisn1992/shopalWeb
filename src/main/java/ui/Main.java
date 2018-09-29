package main.java.ui;
import com.mongodb.BasicDBObject;
import main.java.model.Engine;
import main.java.model.Stock;
import org.bson.types.ObjectId;


public class Main {

    public static void main(String[] args) throws Exception {
        Stock stock = new Stock();
        //stock.createOrupdateProduct("5ba68a6df21c55ef12534b8a", 82,10,9);
        //stock.getStock("5ba68a6df21c55ef12534b8a");
        //System.out.print(stock.is_stockId_existInDB("5ba66edef21c55ef12534b88"));

       // System.out.print(stock.getValueOfProduct("5ba68a6df21c55ef12534b8a", 3,"available"));
//5 2



//        String emailAddress;
//        String userInput;
//        Scanner scan = new Scanner(System.in);
//
//
//        System.out.println("Hello friends!");
//        System.out.print("Please choose one of the menu options\n 1. Login In\n 2. Create a new user\n 3. Exit\n");
//        userInput  = scan.nextLine();
//        switch(userInput) {
//            case "1":
//                boolean res = false;
//                do{
//                    System.out.println("Please login");
//                    emailAddress = scan.nextLine();
//                    res = engine.signIn(emailAddress); // update return not to be true
//                    if(!res){
//                        System.out.println("Your email is incorrect");
//                    }
//                }while(!res);
//                break;
//            case "2":
//                System.out.println("Wellcome to our app :)");
//                engine.CreateNewUser();
//                break;
//            case "3":
//                exit(0);
//        }
//
//        System.out.print("Please choose one of the menu options\n 1. Scan Product\n 2. Update Shopping List\n 3. View Detail Account\n 4. Return to the previous page \n");
//
//        userInput  = scan.nextLine();
//        switch(userInput){
//            case "1":
//                System.out.print("Please choose one of the scan options\n 1. Scan With Update\n 2. Continuity Scan\n 3. Return to the previous page \n");
//                userInput  = scan.nextLine();
//                switch(userInput) {
//                    case "1":
//                        engine.scanProductWithUpdate();
//                        engine.updateUi(); //the user clicked on finish
//                        break;
//                    case "2":
//                        engine.continuityScan();
//                        engine.updateUi();//the user clicked on finish
//                        break;
//                    case "3":
//                        break;
//                }
//                break;
//            case "2":
//                engine.increaseAmount();
//                //engine.deleteList();
//                //engine.logOut();
//                break;
//            case "3":
//
//        }


    }
}
