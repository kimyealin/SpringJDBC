package yeling.spring.guestbook.dao;

import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import yeling.spring.guestbook.vo.GuestMessage;

public class JdbcTemplateGuestMessageDao implements GuestMessageDAO {
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplateGuestMessageDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int count() {
		return jdbcTemplate.queryForObject("select count(*) from guestbook", Integer.class);
	}

	@Override
	public int delete(int id) {
		return jdbcTemplate.update("delete from guestbook where message_id = ?", id);
	}
	
	@Override
	public int insert(final GuestMessage message) {
		int insertedCount = jdbcTemplate.update("insert into guestbook(message_id, guest_name, " + "message, registry_date) values(guest_seq.nextval, ?, ?, ?)", 
				message.getGuestName(), message.getMessage(), message.getRegistryDate());
		return insertedCount;
	}
	
	@Override
	public List<GuestMessage> select(int begin, int end) {
		return jdbcTemplate.query("select * from (select rownum rnum, message_id, guest_name,"
				+ "message, registry_date " + "from (select * from guestbook order by message_id desc)) "
				+ "where rnum>=? and rnum<=?", new Object[] {begin, end}, new GuestMessageRowMapper());
	}

	@Override
	public int update(GuestMessage message) {
		return jdbcTemplate.update("update guestbook set message=? where message_id=?", new Object[] {message.getMessage(), message.getId() },
				new int[] {Types.VARCHAR, Types.INTEGER });
	}

}
