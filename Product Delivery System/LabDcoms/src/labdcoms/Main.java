/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labdcoms;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tahmi
 */
public class Main {

    public Main() {
    }
    
    public static void main(String[] args)throws RemoteException{
        Thread myThread = new StartServer();
        myThread.start();
    }
    
    public static class StartServer extends Thread {

        @Override
        public void run() {
            Registry reg = null;
            try {
                reg = LocateRegistry.createRegistry(1044);
            } catch (RemoteException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                reg.rebind("server", new Server());
            } catch (RemoteException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Running...");
        }
        
    }
}
