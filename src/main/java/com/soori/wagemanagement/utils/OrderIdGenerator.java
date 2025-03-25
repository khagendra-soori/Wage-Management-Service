package com.soori.wagemanagement.utils;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderIdGenerator implements IdentifierGenerator {

    private static final String PREFIX = "ORDER00";
    private static final AtomicInteger FALLBACK_COUNTER = new AtomicInteger(101);

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        // Handle case when session is null (called directly from @PrePersist)
        if (session == null) {
            return generateFallbackId();
        }

        String query = "SELECT COALESCE(MAX(CAST(SUBSTRING(order_id, 8) AS INTEGER)), 100) FROM job_masters";

        try (PreparedStatement preparedStatement = session.createNativeQuery(query).unwrap(PreparedStatement.class);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                int maxId = resultSet.getInt(1); // Get the highest ID number
                int nextId = maxId + 1; // Increment by 1
                return PREFIX + nextId;
            }

        } catch (SQLException e) {
            // Log the error but don't throw - fall back to a default implementation
            System.err.println("Error generating custom Order ID: " + e.getMessage());
            return generateFallbackId();
        }

        return PREFIX + "101"; // Default starting value if no records exist
    }

    private String generateFallbackId() {
        // Simple counter-based ID when session is not available
        return PREFIX + FALLBACK_COUNTER.getAndIncrement();
    }
}

//package com.soori.wagemanagement.utils;
//
//import org.hibernate.engine.spi.SharedSessionContractImplementor;
//import org.hibernate.id.IdentifierGenerator;
//import java.io.Serializable;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class OrderIdGenerator implements IdentifierGenerator {
//
//    private static final String PREFIX = "ORDER00";
//
//    @Override
//    public Serializable generate(SharedSessionContractImplementor session, Object object) {
//        String query = "SELECT COALESCE(MAX(CAST(SUBSTRING(order_id, 8) AS INTEGER)), 100) FROM job_masters";
//
//        try (PreparedStatement preparedStatement = session.createNativeQuery(query).unwrap(PreparedStatement.class);
//             ResultSet resultSet = preparedStatement.executeQuery()) {
//
//            if (resultSet.next()) {
//                int maxId = resultSet.getInt(1); // Get the highest ID number
//                int nextId = maxId + 1; // Increment by 1
//                return PREFIX + nextId;
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error generating custom Order ID", e);
//        }
//
//        return PREFIX + "101"; // Default starting value if no records exist
//    }
//}
