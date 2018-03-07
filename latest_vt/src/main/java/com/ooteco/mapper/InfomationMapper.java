package com.ooteco.mapper;

import com.ooteco.model.FirstHeadNews;
import com.ooteco.model.FirstHeadNewsDetails;
import com.ooteco.model.NiurenNews;
import com.ooteco.model.QuickNews;
import com.ooteco.page.PageBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fangqing on 2/10/18.
 */
@Repository
public interface InfomationMapper {
    public List<QuickNews> getallinfo();

    public List<QuickNews> getInfoBySource(String source);

    public List<QuickNews> getInfoByDate(String date);

    ///////////////
    public List<NiurenNews> getInfoNiuren();

    public List<NiurenNews> getInfoNiurenBySource(String source);

    public List<NiurenNews> getInfoNiurenByDate(String date);

    public List<NiurenNews> getInfNiurenoAll();

    ///////////////////

    public List<FirstHeadNews> getHeadFirstInfoBySource(String source);

    public List<FirstHeadNews> getInfoHeadFirstByDate(String date);

    public List<FirstHeadNews> getHeadFirstInfo();

    public List<FirstHeadNews> getInfoHeadFirstAll();

    public List<FirstHeadNewsDetails> getHeadFirstDetails(String title);
}
