package com.b07.store;

import java.sql.SQLException;
import java.util.List;

import com.b07.exceptions.*;

public interface SalesLog {

    public List<Sale> getSalesLog() throws SQLException, SaleAlreadyExistsException;

    public void setSalesLog(List<Sale> salesLogToSet);

    public void addSales(int saleId) throws SaleAlreadyExistsException, ConnectionCorruptionException, SQLException;

    public void addSales(List<Integer> salesToAdd) throws ConnectionCorruptionException, SQLException;

    public void deleteSale(int saleId) throws SQLException, SaleDoesnotExistException, ConnectionCorruptionException;

}
