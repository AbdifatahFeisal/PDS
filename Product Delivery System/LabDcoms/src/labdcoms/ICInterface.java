/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labdcoms;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 *
 * @author tahmi
 */
public interface ICInterface extends Remote{
    boolean checkIC(String ic) throws RemoteException, SQLException;
}
