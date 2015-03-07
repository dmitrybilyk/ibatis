package com.ibatis.dao.interactions;

import com.ibatis.config.ScorecardSqlMapClientFactory;
import com.ibatis.config.SqlMapVMClientFactory;
import com.ibatis.dao.PersonDao;
import com.ibatis.dao.PersonDaoIbatis;
import com.ibatis.scorecardmodel.bo.interaction.InteractionViewRestriction;
import com.ibatis.search.RequestFailedException;
import com.ibatis.search.SearchBO;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.view.View;
import com.ibatis.view.ViewPersonImpl;
import com.learn.model.BaseBean;
import com.learn.model.Person;

import java.util.List;

/**
 * Created by dmitry on 27.12.14.
 */
public class Run {
    static IbatisInteractionsDao manager = new IbatisInteractionsDao();
    public static void main(String[] args) {
        SqlMapClient sqlMapClient = ScorecardSqlMapClientFactory.getClientInstance();
        Integer answer = null;
        try {
            InteractionViewRestriction interactionViewLimit = new InteractionViewRestriction();
            SearchBO searchBO = new SearchBO();
            searchBO.setLimit(1000);
            interactionViewLimit.setBaseSearch(searchBO);
            answer = manager.getInteractionCount(interactionViewLimit, sqlMapClient);
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
        System.out.println(answer);
    }
}
