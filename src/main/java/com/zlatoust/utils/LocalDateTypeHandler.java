package com.zlatoust.utils;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;

public class LocalDateTypeHandler extends BaseTypeHandler<java.time.LocalDate> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, java.time.LocalDate parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString());
    }

    @Override
    public java.time.LocalDate getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String date = rs.getString(columnName);
        return date != null ? java.time.LocalDate.parse(date) : null;
    }

    @Override
    public java.time.LocalDate getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String date = rs.getString(columnIndex);
        return date != null ? java.time.LocalDate.parse(date) : null;
    }

    @Override
    public java.time.LocalDate getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String date = cs.getString(columnIndex);
        return date != null ? java.time.LocalDate.parse(date) : null;
    }
}