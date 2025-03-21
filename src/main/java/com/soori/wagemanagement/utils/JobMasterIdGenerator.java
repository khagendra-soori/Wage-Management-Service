package com.soori.wagemanagement.utils;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JobMasterIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        String prefix = "JM00";
        String query = "SELECT COALESCE(MAX(CAST(SUBSTRING(job_master_id FROM 4) AS INTEGER)), 100) FROM job_masters";

        try (Connection connection = session.getJdbcConnectionAccess().obtainConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                int maxId = resultSet.getInt(1); // Get the highest ID number
                int nextId = maxId + 1; // Increment by 1
                return prefix + nextId;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prefix + "101"; // Default starting value if no records exist
    }
}
