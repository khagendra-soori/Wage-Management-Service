package com.soori.wagemanagement.utils;


import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JobMasterIdGenerator implements IdentifierGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        String prefix = "JM00";
        JdbcConnectionAccess jdbcConnectionAccess = session.getJdbcConnectionAccess();
        String query = "select count(job_master_id) as Id from JobMaster";
        try (Connection connection = jdbcConnectionAccess.obtainConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){
            if(resultSet.next()){
                int id = resultSet.getInt(1)+101;
                String generatedId = prefix + id;
                return generatedId;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
