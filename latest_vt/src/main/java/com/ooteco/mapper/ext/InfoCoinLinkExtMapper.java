package com.ooteco.mapper.ext;


import com.ooteco.model.InfoCoinLink;

import java.util.List;

public interface InfoCoinLinkExtMapper {

    List<InfoCoinLink> selectByCoinid(int coinid);
}