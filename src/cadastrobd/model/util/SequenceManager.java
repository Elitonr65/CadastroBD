package cadastrobd.model.util;

import java.sql.*;

public class SequenceManager {
    public static int getValue(String sequenceName) {
        int value = -1;
        String sql = "SELECT NEXT VALUE FOR " + sequenceName;

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmt = ConectorBD.getPrepared(conn, sql);
             ResultSet rs = ConectorBD.getSelect(stmt)) {

            if (rs.next()) {
                value = rs.getInt(1);
            }
        } catch (SQLException e) {
        }

        return value;
    }
}
