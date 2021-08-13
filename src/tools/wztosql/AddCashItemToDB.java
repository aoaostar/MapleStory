package tools.wztosql;

import com.mysql.jdbc.PreparedStatement;
import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class AddCashItemToDB
{
    public static void addItem(final int id, final int Count, final int Price, final int SN, final int Expire, final int Gender, final int OnSale) throws Exception {
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = (PreparedStatement)conn.prepareStatement("INSERT INTO `cashshop_items` VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, id);
            ps.setInt(2, Count);
            ps.setInt(3, Price);
            ps.setInt(4, SN);
            ps.setInt(5, Expire);
            ps.setInt(6, Gender);
            ps.setInt(7, OnSale);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
