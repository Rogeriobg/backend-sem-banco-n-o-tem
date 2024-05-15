/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastro.model.util;

/**
 *
 * @author rbgor
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceManager {
   
    public static int getValue(String sequenceName) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int nextValue = -1;

        try {
            
            connection = ConectorBD.getConnection();

          
            String sql = "SELECT nextval(?) AS next_value";

            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sequenceName);

            
            resultSet = preparedStatement.executeQuery();

            
            if (resultSet.next()) {
                nextValue = resultSet.getInt("next_value");
            }
        } finally {
          
            ConectorBD.close(resultSet);
            ConectorBD.close(preparedStatement);
            ConectorBD.close(connection);
        }

        return nextValue;
    }
}
