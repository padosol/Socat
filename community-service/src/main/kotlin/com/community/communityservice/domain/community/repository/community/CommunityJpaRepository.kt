package com.community.communityservice.domain.community.repository.community

import com.community.communityservice.domain.community.entity.CommunityEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CommunityJpaRepository: JpaRepository<CommunityEntity, String> {

}