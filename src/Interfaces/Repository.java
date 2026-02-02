package Interfaces;

import java.sql.SQLException;

public interface Repository<T>  {
    void insertUser(T user) throws SQLException;
}
