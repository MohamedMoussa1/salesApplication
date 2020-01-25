package com.b07.store;

import java.util.*;
import java.util.List;
import java.sql.SQLException;

import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.store.Sale;
import com.b07.exceptions.*;

public class SalesLogImpl implements SalesLog {

    private static List<Sale> salesLog;
    private static int salesCounter;

    private static boolean salesLogContains(int saleId) {
        Sale sale;
        for (int i = 0; i < salesLog.size(); i++) {
            sale = salesLog.get(i);
            if (saleId == sale.getId()) {
                return true;
            }
        }

        return false;

    }

    /**
     * Returns the salesLog which is a list<Sale>
     * 
     * @return salesLog
     */
    public List<Sale> getSalesLog() {
        return salesLog;
    }

    /**
     * Sets the salesLog with the salesLogToSet
     * 
     * @param salesLogToSet
     */
    public void setSalesLog(List<Sale> salesLogToSet) {
        salesLog = salesLogToSet;
    }

    /**
     * Add sale with given saleId to the salesLog
     * 
     * @param saleId
     * @throws SQLException
     * @throws ConnectionCorruptionException
     * @throws SaleAlreadyExists
     */
    public void addSales(int saleId) throws SQLException, SaleAlreadyExistsException, ConnectionCorruptionException {
        Sale newSale;
        if (!salesLogContains(saleId)) {

            newSale = DatabaseSelectHelper.getSaleById(saleId);

            salesLog.add(newSale);
            salesCounter++;
        } else {

            throw new SaleAlreadyExistsException();

        }
    }

    public void addSales(List<Integer> salesToAdd) throws ConnectionCorruptionException, SQLException {

        for (int i = 0; i < salesToAdd.size(); i++) {
            if (!salesLogContains(salesToAdd.get(i))) {
                salesLog.add(DatabaseSelectHelper.getSaleById(salesToAdd.get(i)));
                salesCounter++;
            }

        }
    }

    /**
     * Deletes the sale with given saleId from the salesLog by
     * 
     * @param saleId
     * @throws SQLException
     * @throws ConnectionCorruptionException
     */

    public void deleteSales(int saleId) throws SQLException, SaleDoesnotExistException, ConnectionCorruptionException {
        if (salesLogContains(saleId)) {
            Sale saleToRemove = DatabaseSelectHelper.getSaleById(saleId);
            salesLog.remove(saleToRemove);
            salesCounter--;
        } else {

            throw new SaleDoesnotExistException();
        }

    }

}