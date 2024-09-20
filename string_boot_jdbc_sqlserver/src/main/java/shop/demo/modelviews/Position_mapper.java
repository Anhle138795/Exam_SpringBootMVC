package shop.demo.modelviews;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import shop.demo.models.Position;
import shop.demo.utils.Views;

public class Position_mapper implements RowMapper<Position> {
	@Override
	public Position mapRow(ResultSet rs, int rowNum) throws SQLException {
		Position item = new Position();
		item.setId(rs.getInt(Views.COL_POSITION_ID));
		item.setTitle(rs.getString(Views.COL_POSITION_TITLE));
		item.setStatus(rs.getInt(Views.COL_POSITION_STATUS));
		return item;
	}
}
