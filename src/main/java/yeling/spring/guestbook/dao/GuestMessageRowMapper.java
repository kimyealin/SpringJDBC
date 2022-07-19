package yeling.spring.guestbook.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import yeling.spring.guestbook.vo.GuestMessage;

public class GuestMessageRowMapper implements RowMapper<GuestMessage> {
	
	@Override
	public GuestMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
		GuestMessage message = new GuestMessage();
		message.setId(rs.getInt("MESSAGE_ID"));
		message.setGuestName(rs.getString("GUEST_NAME"));
		message.setMessage(rs.getString("MESSAGE"));
		message.setRegistryDate(rs.getDate("REGISTRY_DATE"));
		return message;
	}
}
