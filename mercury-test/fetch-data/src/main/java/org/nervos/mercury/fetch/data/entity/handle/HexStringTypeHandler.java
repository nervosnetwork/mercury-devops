package org.nervos.mercury.fetch.data.entity.handle;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.nervos.mercury.fetch.data.entity.type.HexBytes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// TODO: 2021/9/1 移动到 mapper 下
@MappedTypes({HexBytes.class})
public class HexStringTypeHandler extends BaseTypeHandler<HexBytes> {
  @Override
  public void setNonNullParameter(
      PreparedStatement ps, int i, HexBytes parameter, JdbcType jdbcType) throws SQLException {
    ps.setBytes(i, parameter.getHexBytes());
  }

  @Override
  public HexBytes getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return convert(rs.getBytes(columnName));
  }

  @Override
  public HexBytes getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return convert(rs.getBytes(columnIndex));
  }

  @Override
  public HexBytes getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return convert(cs.getBytes(columnIndex));
  }

  private HexBytes convert(byte[] hex) {
    return new HexBytes(hex);
  }
}
