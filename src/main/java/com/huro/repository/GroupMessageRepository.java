package com.huro.repository;

import com.huro.domain.GroupMessage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GroupMessage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupMessageRepository extends JpaRepository<GroupMessage, Long> {

}
