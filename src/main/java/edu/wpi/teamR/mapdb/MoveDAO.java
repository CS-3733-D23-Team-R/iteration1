package edu.wpi.teamR.mapdb;

import edu.wpi.teamR.Configuration;
import oracle.ucp.proxy.annotation.Pre;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;

public class MoveDAO {
    private Connection aConnection;
    MoveDAO(Connection connection) throws SQLException, ClassNotFoundException {
        aConnection = connection;
    }
    ArrayList<Move> getMoves() throws SQLException {
        ArrayList<Move> temp = new ArrayList<Move>();
        Statement statement = aConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM "+Configuration.getMoveSchemaNameTableName()+";");
        while(resultSet.next()){
            java.sql.Date aDate = resultSet.getDate("date");
            String aLongName = resultSet.getString("longname");
            int aNodeID = resultSet.getInt("nodeID");
            Move aMove = new Move(aNodeID, aLongName, aDate);
            temp.add(aMove);
        }
        return temp;
    }
    ArrayList<Move> getMovesByNodeID(int nodeID) throws SQLException {
        ArrayList<Move> temp = new ArrayList<Move>();
        Statement statement = aConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM "+Configuration.getMoveSchemaNameTableName()+" WHERE nodeID = "+nodeID+";");
        while(resultSet.next()){
            java.sql.Date aDate = resultSet.getDate("date");
            String aLongName = resultSet.getString("longname");
            int aNodeID = resultSet.getInt("nodeID");
            Move aMove = new Move(aNodeID, aLongName, aDate);
            temp.add(aMove);
        }
        return temp;
    }
    Move getLatestMoveByNodeID(int nodeID) throws SQLException {
        Statement statement = aConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM "+Configuration.getMoveSchemaNameTableName()+" WHERE date=(select max(date) FROM "+Configuration.getMoveSchemaNameTableName()+" WHERE nodeID = "+nodeID+" AND date<now()) AND nodeID = "+nodeID+";");
        resultSet.next();
        Move temp = new Move(resultSet.getInt("nodeid"), resultSet.getString("longname"), resultSet.getDate("date"));
        return temp;
    }
    Move addMove(int nodeID, String longName, java.sql.Date moveDate) throws SQLException {
        Statement statement = aConnection.createStatement();
        statement.executeUpdate("INSERT INTO "+Configuration.getMoveSchemaNameTableName()+"(nodeID, longName, date) VALUES("+nodeID+", '"+longName+"', '"+moveDate.toString()+"');");
        return new Move(nodeID, longName, moveDate);
    }
    void deleteMovesByNode(int nodeID) throws SQLException {
        Statement statement = aConnection.createStatement();
        statement.executeUpdate("DELETE FROM "+Configuration.getMoveSchemaNameTableName()+" WHERE nodeid = "+nodeID+";");
    }
    void deleteMovesByLongName(String longName) throws SQLException {
        Statement statement = aConnection.createStatement();
        statement.executeUpdate("DELETE FROM "+Configuration.getMoveSchemaNameTableName()+" WHERE longname = '"+longName+"';");
    }
    void deleteMove(int nodeID, String longName, java.sql.Date moveDate) throws SQLException {
        Statement statement = aConnection.createStatement();
        statement.executeUpdate("DELETE FROM "+Configuration.getMoveSchemaNameTableName()+" WHERE longname = '"+longName+"' and nodeID = "+nodeID+" and date = '"+moveDate.toString()+"';");
    }

    void deleteAllMoves() throws SQLException {
        PreparedStatement preparedStatement = aConnection.prepareStatement("DELETE FROM "+Configuration.getMoveSchemaNameTableName()+";");
        preparedStatement.executeUpdate();
    }
}
