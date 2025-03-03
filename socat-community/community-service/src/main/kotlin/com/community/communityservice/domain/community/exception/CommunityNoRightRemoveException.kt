package com.community.communityservice.domain.community.exception

import com.community.communityservice.global.exception.CustomException
import com.community.communityservice.global.exception.CustomExceptionCode

class CommunityNoRightRemoveException(
        override val customExceptionCode: CustomExceptionCode
) : CustomException(customExceptionCode){
}