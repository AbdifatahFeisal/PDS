/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labdcoms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tahmi
 */
public class Client {
    static String[][] cartProducts;
    static int cartItems = 0;
    static String[] userData = new String[4];
    public static void main(String[] args)throws MalformedURLException, NotBoundException, RemoteException, SQLException, InterruptedException {
        MainInterface();
    }
    
    public static void MainInterface() throws NotBoundException, MalformedURLException, RemoteException, SQLException, InterruptedException {
        int choice = 0;
        boolean complete = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to CFK.\nPlease choose an option below: \n1. Sign in\n2. Sign up");
        while(!complete) {
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    complete = true;
                    SignIn();
                    break;
                case 2:
                    complete = true;
                    SignUp();
                    break;
                default:
                    System.out.println("You chose a wrong number.\nPlease choose an option below: \n1. Sign in\n2. Sign up");
            }
        }
    }
    
    // For user sign up
    public static void SignUp() throws MalformedURLException, NotBoundException, RemoteException, SQLException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        String username = "", password = "", firstName = "", lastName = "", ic = "";
        boolean usernameExists = false, icExists = false, dataStored = false;
        
        System.out.print("Sign Up Page\nFirst Name: ");
        firstName = sc.nextLine();
        System.out.print("Last Name: ");
        lastName = sc.nextLine();
        System.out.print("IC/Passport ID: ");
        ic = sc.nextLine();
        ICInterface checkIC = (ICInterface)Naming.lookup("rmi://localhost:1044/server");
        icExists = checkIC.checkIC(ic);
        if (icExists) {
            while (icExists) {
                ICSwitchCase();
                System.out.print("IC/Passport ID: ");
                ic = sc.nextLine();
                icExists = checkIC.checkIC(ic);
            }
        }
        System.out.print("Username: ");
        username = sc.nextLine();
        SignUpInterface checkU = (SignUpInterface)Naming.lookup("rmi://localhost:1044/server");
        usernameExists = checkU.checkUsername(username);
        if (usernameExists) {
            while (usernameExists) {
                System.out.print("Entered username already exists. Please choose a new username.\nUsername: ");
                username = sc.nextLine();
                usernameExists = checkU.checkUsername(username);
            }
        }
        System.out.print("Password: ");
        password = sc.nextLine();
        StoreDataInterface storeData = (StoreDataInterface)Naming.lookup("rmi://localhost:1044/server");
        dataStored = storeData.storeData(username, password, firstName, lastName, ic);
        if (dataStored) {
            System.out.println("Account has been created sucessfully! Redirecting to the login page...");
            Thread.sleep(3000);
            SignIn();
        }
    }
   
    // For user sign in 
    public static void SignIn() throws NotBoundException, RemoteException, MalformedURLException, SQLException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        String username = "", password = "";
        boolean loggedIn = false;
        while(!loggedIn) {
            System.out.print("Login Page\nUsername: ");
            username = sc.nextLine();
            System.out.print("Password: ");
            password = sc.nextLine();
            LoginInterface object = (LoginInterface)Naming.lookup("rmi://localhost:1044/server");
            userData = object.authenticate(username, password);
                if(userData == null) {
                    System.out.println("Wrong username or password. Please try again.");
                    System.out.flush();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else {
                    loggedIn = true;
                    System.out.println("Logged in successfully...");
                }
        }
        int j = 0;
        cartProducts = new String[20][5];
        DeserializeData(userData);
        for(int i=0; i <= cartProducts.length - 1; i++) {
            if(cartProducts[i][0] != null){
                j++;
            }
        }
        cartItems = j;
        Dashboard(userData);
    }
    
    // CFK Dashboard Page
    public static void Dashboard(String[] customerData) throws NotBoundException, RemoteException, MalformedURLException, SQLException, InterruptedException {
        System.out.println("\nCFK Group Malaysia - All in one e-commerce\nWelcome," + customerData[1]);
        int choice = 0;
        boolean complete = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("\n1.Order Items\n2.Checkout\n3.Logout\n4.Exit");
        while(!complete) {
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    complete = true;
                    OrderItems(customerData);
                    break;
                case 2:
                    Checkout(customerData);
                case 3:
                    Logout();
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Wrong number entered.\nPlease choose an option:\n1.Order Items\n2.Exit");
            }
        }
    }
    
    public static void OrderItems(String[] customerData) throws NotBoundException, NotBoundException, MalformedURLException, RemoteException, SQLException, InterruptedException {
        String[][] products = null;
        boolean finishShopping = false;
        Scanner sc = new Scanner(System.in);
        int quantity = 0, total = 0, storeProdID = 0, choice = 0;
                
        ViewProductsInterface getProducts = (ViewProductsInterface)Naming.lookup("rmi://localhost:1044/server");
        products = getProducts.getProducts();
        System.out.println("\t\tPRODUCTS CATALOUGE\t\t");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("ID\tName\t\t\tPrice\t\tRating\t");
        System.out.println("-------------------------------------------------------------------");
        for(int i=0; i<= products.length-1; i++) {
            System.out.println(products[i][0] + "\t" + products[i][1] + "\t\t" + products[i][2] + " RM\t\t" + products[i][3] + "\t");
            System.out.println("-------------------------------------------------------------------");
        }
        
        while(!finishShopping && cartItems != 19) {
            Scanner sc2 = new Scanner(System.in);
            String prodID = null;
            System.out.print("\nAdd a product to your cart.\nProduct ID: ");
            prodID = sc.next();
            boolean idFound = false;
            for(int i=0; i<= products.length-1; i++) {
                if(prodID.equals(products[i][0])) {
                    boolean productExists = false;
                    System.out.print("\nQuantity of " + products[i][1] + ": ");
                    quantity = sc.nextInt();
                    total = quantity * Integer.parseInt(products[i][2]);

                    for(int j=0; j<= cartProducts.length-1; j++) {
                        if(prodID.equals(cartProducts[j][0])) {
                            quantity = quantity + Integer.parseInt(cartProducts[j][3]);
                            total = total + Integer.parseInt(cartProducts[j][4]);
                            cartProducts[j][3] = Integer.toString(quantity); // Product Quantity
                            cartProducts[j][4] = Integer.toString(total); // Product Total         
                            productExists = true;
                        }
                        if(productExists) {
                            break;
                        }
                    }
                    if(!productExists) {
                        cartProducts[cartItems][0] = products[i][0]; // Product ID
                        cartProducts[cartItems][1] = products[i][1]; // Product Name
                        cartProducts[cartItems][2] = products[i][2]; // Product Price
                        cartProducts[cartItems][3] = Integer.toString(quantity); // Product Quantity
                        cartProducts[cartItems][4] = Integer.toString(total); // Product Total                    
                    }
                    idFound = true;
                }
                if(idFound) {
                    storeProdID = i;
                    break;
                }
            }
            if(idFound) {
                System.out.println("Product: " + products[storeProdID][1] + " successfully added to the cart.\n1.Add another product to the cart\n2.Exit");
                cartItems++;
            }
            else {
                System.out.println("Product ID does not exist.\n1.Add another product to the cart\n2.Exit");
            }
            boolean complete = false;
            while(!complete) {
                choice = sc.nextInt();
                switch(choice) {
                    case 1:
                        complete = true;
                        break;
                    case 2:
                        complete = true;
                        if(cartProducts != null) {
                            SerializeData(customerData);
                        }
                        Dashboard(customerData);
                        break;
                    default:
                    System.out.println("Wrong number entered.\nPlease choose an option:\n1.Add another product to the cart\n2.Exit");
                }            
            }
        }
    }
    
    public static void Checkout(String[] customerData) throws NotBoundException, RemoteException, MalformedURLException, SQLException, NumberFormatException, InterruptedException {
        int total = 0;
        Scanner sc = new Scanner(System.in);
        int choice;
        boolean complete = false;
        System.out.println("\t\t\t\t\tCART\t\t");
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("ID\tName\t\t\tPrice\t\tQuantity\tTotal Price");
        System.out.println("---------------------------------------------------------------------------------------");
        for(int i=0; i <= cartProducts.length -1; i++) {
            if(cartProducts[i][0] != null){
                System.out.println(cartProducts[i][0] + "\t" + cartProducts[i][1] + "\t\t" + cartProducts[i][2] + " RM\t\t" + cartProducts[i][3] + "\t\t" + cartProducts[i][4] + " RM\t");
                System.out.println("---------------------------------------------------------------------------------------");
                total = total + Integer.parseInt(cartProducts[i][4]);
            }
        }
        System.out.println("\t\t\t\t\t\t\t\tGrand Total: " + total + " RM \n1.Checkout\n2.Go back");
        while(!complete) {
            choice = sc.nextInt();
            switch(choice) {
                case 1: 
                    complete = true;
                    if(cartItems != 0) {
                        ConfirmCheckout(customerData);
                    }
                    else {
                        System.out.println("You have no items in the cart. Please order an item and then checkout.");
                        Thread.sleep(2000);
                        Dashboard(customerData);
                    }
                    break;
                case 2:
                    complete = true;
                    Dashboard(customerData);
                    break;
                default:
                System.out.println("Wrong number entered.\nPlease choose an option:\n1.Checkout\n2.Go back");
            }              
        }
    }
    
    
    // Switch case when IC is same
    public static void ICSwitchCase () throws NotBoundException, RemoteException, MalformedURLException, SQLException, InterruptedException{
        int choice = 0;
        boolean complete = false;
        Scanner sc = new Scanner(System.in);
        System.out.print("Entered IC/Passport ID is already registered to an account. Please choose an option:\n1. Login\n2. Enter a new IC\n ");
        while(!complete) {
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    complete = true;
                    SignIn();
                    break;
                case 2:
                    complete = true;
                    break;
                default:
                    System.out.println("Wrong number entered.\nPlease choose an option:\n1. Login\n2. Enter a new IC\n ");
            }
        }
    }
    
    public static void SerializeData(String[] customerData) {
        try {
            //Create stream and write the array
            FileOutputStream fout = new FileOutputStream(customerData[0] + "-cart-products.ser");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(cartProducts);
            out.flush();
            out.close();
        }
        catch(Exception e){
            System.out.println(e);;
        }
    }
    
    public static void DeserializeData(String[] customerData) {
        //Check if the file exists
        File tmpDir = new File(customerData[0] + "-cart-products.ser");
        boolean exists = tmpDir.exists();
        if(exists){
            try {
                //Create stream and write the array
                    FileInputStream fin = new FileInputStream(customerData[0] + "-cart-products.ser");
                    ObjectInputStream in = new ObjectInputStream(fin);
                    cartProducts = (String[][]) in.readObject();
                    in.close();
                    fin.close();
            }
            catch(Exception e){
                System.out.println(e);;
            }
        }
    }
    
    public static void ConfirmCheckout(String[] customerData) throws InterruptedException, NotBoundException, RemoteException, MalformedURLException, SQLException {
        cartProducts = new String[20][5];
        cartItems = 0;
        //Delete the stored file
        File myObj = new File(customerData[0] + "-cart-products.ser"); 
        if (!myObj.delete()) { 
          System.out.println("Failed to delete the file.");
        } 
        System.out.println("Successfully bought the items!");
        Thread.sleep(2000);
        Dashboard(customerData);
    }
    
    public static void Logout() throws InterruptedException, NotBoundException, MalformedURLException, RemoteException, SQLException {
        System.out.println("Logging out...");
        Thread.sleep(2000);
        MainInterface();
    }
}