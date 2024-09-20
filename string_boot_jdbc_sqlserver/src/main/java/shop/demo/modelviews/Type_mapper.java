package shop.demo.modelviews;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import shop.demo.models.Type;
import shop.demo.utils.Views;

public class Type_mapper implements RowMapper<Type> {
	@Override
	public Type mapRow(ResultSet rs, int rowNum) throws SQLException {
		Type item = new Type();
		item.setId(rs.getInt(Views.COL_TYPE_ID));
		item.setTitle(rs.getString(Views.COL_TYPE_TITLE));
		return item;
	}
}