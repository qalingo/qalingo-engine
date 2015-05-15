package org.hoteia.qalingo.core.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.domain.Tag;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.common.FetchPlanGraphCommon;
import org.hoteia.qalingo.core.util.CoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("referentialDataDao")
public class ReferentialDataDao extends AbstractGenericDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Tag getTagById(final Long tagId, Object... params) {
        Criteria criteria = createDefaultCriteria(Tag.class);

        FetchPlan fetchPlan = handleSpecificTagFetchMode(criteria, params);

        criteria.add(Restrictions.eq("id", tagId));

        Tag tag = (Tag) criteria.uniqueResult();
        if (tag != null) {
            tag.setFetchPlan(fetchPlan);
        }
        return tag;
    }

    public Tag getTagByCode(final String tagCode, Object... params) {
        Criteria criteria = createDefaultCriteria(Tag.class);

        FetchPlan fetchPlan = handleSpecificTagFetchMode(criteria, params);

        criteria.add(Restrictions.eq("code", handleCodeValue(tagCode)));

        Tag tag = (Tag) criteria.uniqueResult();
        if (tag != null) {
            tag.setFetchPlan(fetchPlan);
        }
        return tag;
    }

    public Long getMaxTagId() {
        Criteria criteria = createDefaultCriteria(Tag.class);
        criteria.setProjection(Projections.max("id"));
        Long maxId = (Long) criteria.uniqueResult();
        return (maxId == null) ? new Long(0) : maxId;
    }

    public List<Tag> findAllTags(Object... params) {
        Criteria criteria = createDefaultCriteria(Tag.class);

        handleSpecificTagFetchMode(criteria, params);

        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<Tag> tags = criteria.list();
        return tags;
    }

    public Tag saveOrUpdateTag(final Tag tag) {
        if (tag.getDateCreate() == null) {
            tag.setDateCreate(new Date());
        }
        if (StringUtils.isEmpty(tag.getCode())) {
            tag.setCode(CoreUtil.generateEntityCode());
        }
        tag.setDateUpdate(new Date());
        if (tag.getId() != null) {
            if (em.contains(tag)) {
                em.refresh(tag);
            }
            Tag mergedTag = em.merge(tag);
            em.flush();
            return mergedTag;
        } else {
            em.persist(tag);
            return tag;
        }
    }

    public void deleteTag(final Tag tag) {
        em.remove(tag);
    }

    protected FetchPlan handleSpecificTagFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCommon.defaultTagFetchPlan());
        }
    }

}