package com.springbazaar.repository;

import com.springbazaar.domain.util.LoginAttempts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Date;

@Repository
public class UserRepositoryCustomImpl extends JdbcDaoSupport implements UserRepositoryCustom {

    private static final String SQL_USERS_UPDATE_LOCKED = "UPDATE sb_users SET account_non_locked = ? WHERE username = ?";
    private static final String SQL_USERS_COUNT = "SELECT count(*) FROM sb_users WHERE username = ?";

    private static final String SQL_USER_ATTEMPTS_GET = "SELECT * FROM sb_login_attempts WHERE username = ?";
    private static final String SQL_USER_ATTEMPTS_INSERT = "INSERT INTO sb_login_attempts (username, attempts, last_modified) VALUES(?,?,?)";
    private static final String SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS = "UPDATE sb_login_attempts SET attempts = attempts + 1, last_modified = ? WHERE username = ?";
    private static final String SQL_LOGIN_ATTEMPTS_RESET_ATTEMPTS = "UPDATE sb_login_attempts SET attempts = 0, last_modified = null WHERE username = ?";


    private static final int MAX_ATTEMPTS = 5;


    private final DataSource dataSource;

    @Autowired
    public UserRepositoryCustomImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public void updateFailAttempts(String username) {
        LoginAttempts user = getLoginAttempts(username);
        if (user == null) {
            if (isUserExists(username)) {
                // if no record, insert a new
                getJdbcTemplate().update(SQL_USER_ATTEMPTS_INSERT, username, 1, new Date());
            }
        } else {
            if (isUserExists(username)) {
                // update attempts count, +1
                getJdbcTemplate().update(SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS, new Date(), username);
            }
            if (user.getAttempts() + 1 >= MAX_ATTEMPTS) {
                // locked user
                getJdbcTemplate().update(SQL_USERS_UPDATE_LOCKED, false, username);
                // throw exception
                throw new LockedException("User Account is locked!");
            }
        }
    }

    @Override
    public LoginAttempts getLoginAttempts(String username) {
        try {
            LoginAttempts userAttempts = getJdbcTemplate().queryForObject(SQL_USER_ATTEMPTS_GET,
                    new Object[]{username}, (rs, rowNum) -> {
                        LoginAttempts user = new LoginAttempts();
                        user.setId(rs.getInt("id"));
                        user.setUsername(rs.getString("username"));
                        user.setAttempts(rs.getInt("attempts"));
                        user.setLastModified(rs.getDate("last_modified"));

                        return user;
                    });
            return userAttempts;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void resetFailAttempts(String username) {
        getJdbcTemplate().update(SQL_LOGIN_ATTEMPTS_RESET_ATTEMPTS, username);
    }

    private boolean isUserExists(String username) {
        boolean result = false;

        int count = getJdbcTemplate().queryForObject(SQL_USERS_COUNT, new Object[]{username}, Integer.class);
        if (count > 0) {
            result = true;
        }
        return result;
    }
}
