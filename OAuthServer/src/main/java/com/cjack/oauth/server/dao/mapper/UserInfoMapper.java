package com.cjack.oauth.server.dao.mapper;

import com.cjack.oauth.server.dao.domain.UserInfoDto;
import org.springframework.stereotype.Repository;

/**
 * Created by root on 3/30/19.
 */
@Repository
public interface UserInfoMapper {

    public UserInfoDto getUserInfo( Long id);

}
