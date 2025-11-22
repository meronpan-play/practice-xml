package com.example.batch.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConfig {

    private static final Properties props = new Properties();

    static {
        try (InputStream input = DbConfig.class.getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new RuntimeException("application.properties が見つかりません");
            }

            // application.properties を読み込み
            props.load(input);

            // JDBC ドライバのロード（省略可能だが明示的に書く）
            Class.forName("org.postgresql.Driver");

        } catch (IOException e) {
            throw new RuntimeException("application.properties の読み込みに失敗しました", e);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC ドライバが見つかりません", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }
}
