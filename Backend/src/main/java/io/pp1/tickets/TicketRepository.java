package io.pp1.tickets;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	public List<Ticket> findAll();
	
	@Query(value = "SELECT * FROM ticket u WHERE u.ticket_id = ?1", nativeQuery=true)
	List<Ticket> getTicketByID(Integer ticket_id);
	
	@Query(value = "SELECT * FROM ticket u WHERE u.game_date = ?1", nativeQuery=true)
	List<Ticket> getTicketByDate(String game_date);

	@Query(value = "SELECT * FROM ticket u WHERE u.sport = ?1", nativeQuery=true)
	List<Ticket> getTicketBySport(String sport);

	@Query(value = "SELECT * FROM ticket u WHERE u.opponent = ?1", nativeQuery=true)
	List<Ticket> getTicketByOpponent(String opponent);

	@Query(value = "SELECT * FROM ticket u WHERE u.game_location = ?1", nativeQuery=true)
	List<Ticket> getTicketByLocation(String game_location);

//	@Query(value = "DELETE FROM ticket u WHERE  u.ticket_id = ?1", nativeQuery=true)
//	List<Ticket> removeByID(Integer ticket_id);



}
