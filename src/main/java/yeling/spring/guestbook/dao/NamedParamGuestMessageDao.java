package yeling.spring.guestbook.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import yeling.spring.guestbook.vo.GuestMessage;

public class NamedParamGuestMessageDao implements GuestMessageDAO {
	private NamedParameterJdbcTemplate template;
	private SimpleJdbcInsert insertMessage;
	
	public NamedParamGuestMessageDao(SimpleJdbcInsert insertMessage) {
		this.insertMessage = insertMessage;
		insertMessage.withTableName("guestbook");
		insertMessage.usingColumns("message_id", "guest_name", "message", "registry_date");
	}
	
	public int nextval() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return template.queryForObject("select guest_seq.nextval from dual", paramMap, Integer.class);
	}
	
	public void setTemplate(NamedParameterJdbcTemplate template) {
		this.template = template;
	}
	
	@Override
	public int count() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return template.queryForObject("select count(*) from guestbook", paramMap, Integer.class);
	}

	@Override
	public int delete(int id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return template.update("delete from guestbook where message_id = :id", paramMap);
	}
	
	@Override
	public int insert(final GuestMessage message) {
//		BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(message);
//		int insertedCount = template.update("insert into guestbook(message_id, guest_name, message, registry_date)" 
//					+ "values(guest_seq.nextval, :guestName, :message, :registryDate)", paramSource);
//		if(insertedCount > 0) {
//			int id = template.queryForObject("select count(*) from guestbook", Collections.<String, Object>emptyMap(),
//					Integer.class);
//			message.setId(id);
//		}
//		return insertedCount;
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		message.setId(nextval());
		paramSource.addValue("MESSAGE_ID", message.getId());
		paramSource.addValue("GUEST_NAME", message.getGuestName());
		paramSource.addValue("MESSAGE", message.getMessage());
		paramSource.addValue("REGISTRY_DATE", message.getRegistryDate());
		return insertMessage.execute(paramSource);
	}
	
	@Override
	public List<GuestMessage> select(int begin, int end) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startRowNum", begin);
		paramMap.put("count", end - begin + 1);
		return template.query("select * from (select rownum rnum, message_id, guest_name,"
				+ "message, registry_date from (select * from guestbook order by message_id desc)) "
				+ "where rnum>=:startRowNum and rnum<=:count", paramMap, new RowMapper<GuestMessage>() {
					@Override
					public GuestMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
						GuestMessage message = new GuestMessage();
						message.setId(rs.getInt("MESSAGE_ID"));
						message.setGuestName(rs.getString("GUEST_NAME"));
						message.setMessage(rs.getString("MESSAGE"));
						message.setRegistryDate(rs.getDate("REGISTRY_DATE"));
						return message;
					}
				});
	}

	@Override
	public int update(GuestMessage message) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("message", message.getMessage());
		paramSource.addValue("id", message.getId(), Types.INTEGER);
		return template.update("update guestbook set message=:message where message_id=:id", paramSource); 

	}
}