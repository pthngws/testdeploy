package com.group4.repository;

import com.group4.entity.ChatEntity;
import com.group4.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    @Query("SELECT DISTINCT u.userID, COALESCE(u.name, CAST(u.userID AS string)) " +
            "FROM ChatEntity c " +
            "JOIN UserEntity u ON c.senderID = u.userID " +
            "WHERE c.receiverID = 1")
    List<Object[]> findDistinctSendersByReceiverId();

    @Query("SELECT DISTINCT c.senderID " +
            "FROM ChatEntity c " +
            "WHERE c.receiverID = 1 " +
            "AND NOT EXISTS (SELECT u FROM UserEntity u WHERE u.userID = c.senderID)")
    List<Long> findDistinctGuestsByReceiverId();
}

