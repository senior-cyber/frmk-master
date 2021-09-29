package com.senior.cyber.frmk.common.wicket;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.IRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class ConnectionRequestCycle implements IRequestCycleListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionRequestCycle.class);

    @Override
    public IRequestHandler onException(RequestCycle cycle, Exception ex) {
        LOGGER.info("issue {}", ex.getMessage());
        DataSource dataSource = getDataSource();
        try {
            Connection connection = dataSource.getConnection();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    LOGGER.info("issue {}", e.getMessage());
                }
            }
        } catch (SQLException e) {
            LOGGER.info("issue {}", e.getMessage());
            throw new WicketRuntimeException(e);
        }
        return null;
    }

    protected abstract DataSource getDataSource();

}
