package com.zlatoust.utils;

import com.zlatoust.models.PersonRole;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnumTypeHandler extends BaseTypeHandler<PersonRole> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, PersonRole parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public PersonRole getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String role = rs.getString(columnName);
        return role != null ? PersonRole.valueOf(role) : null;
    }

    @Override
    public PersonRole getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String role = rs.getString(columnIndex);
        return role != null ? PersonRole.valueOf(role) : null;
    }

    @Override
    public PersonRole getNullableResult(java.sql.CallableStatement cs, int columnIndex) throws SQLException {
        String role = cs.getString(columnIndex);
        return role != null ? PersonRole.valueOf(role) : null;
    }
}
