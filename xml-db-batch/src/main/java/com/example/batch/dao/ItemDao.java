package com.example.batch.dao;

import com.example.batch.config.DbConfig;
import com.example.batch.model.Item;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

public class ItemDao {

    private static final String INSERT_SQL =
            "INSERT INTO imported_items (id, name, created_date, tags, price) " +
            "VALUES (?, ?, ?, ?, ?)";

    public void insert(Item item) throws SQLException {
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            // id
            ps.setInt(1, item.getId());

            // name
            ps.setString(2, item.getName());

            // created(LocalDate)
            ps.setObject(3, item.getCreated());

            // tags(List<String> → text[])
            List<String> tags = item.getTags();
            if (tags != null && !tags.isEmpty()) {
                Array tagArray = conn.createArrayOf("text", tags.toArray(new String[0]));
                ps.setArray(4, tagArray);
            } else {
                ps.setNull(4, Types.ARRAY);
            }

            // price(BigDecimal)
            BigDecimal price = item.getPrice();
            ps.setBigDecimal(5, price);

            // SQL 実行
            ps.executeUpdate();
        }
    }
}
