package com.group4.repository;

import com.group4.entity.ChatEntity;
import com.group4.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    @Query("SELECT DISTINCT COALESCE(u.userID, c.senderID) as userID, " +
            "COALESCE(u.name, CAST(c.senderID AS string)) as name " +
            "FROM ChatEntity c " +
            "LEFT JOIN UserEntity u ON c.senderID = u.userID " +
            "WHERE c.receiverID = 1")
    List<Map<String, Object>> findDistinctSendersByReceiverId();

}

