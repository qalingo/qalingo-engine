package org.hoteia.qalingo.core.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.domain.Dictionary;
import org.hoteia.qalingo.core.domain.Localization;
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

    // TAG
    
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
    
    // DICTIONARY
    
    public Dictionary getDictionaryById(final Long dictionaryId, Object... params) {
        Criteria criteria = createDefaultCriteria(Dictionary.class);

        criteria.add(Restrictions.eq("id", dictionaryId));

        Dictionary dictionary = (Dictionary) criteria.uniqueResult();
        return dictionary;
    }

    public Dictionary getDictionaryByCodeAndLocalizationId(final String dictionaryCode, final Long localizationId, Object... params) {
        Criteria criteria = createDefaultCriteria(Dictionary.class);

        criteria.add(Restrictions.eq("code", handleCodeValue(dictionaryCode)));

        criteria.add(Restrictions.eq("localization.id", localizationId));

        Dictionary dictionary = (Dictionary) criteria.uniqueResult();
        return dictionary;
    }

    public Long getMaxDictionaryId() {
        Criteria criteria = createDefaultCriteria(Dictionary.class);
        criteria.setProjection(Projections.max("id"));
        Long maxId = (Long) criteria.uniqueResult();
        return (maxId == null) ? new Long(0) : maxId;
    }

    public List<Dictionary> findAllDictionarys(Object... params) {
        Criteria criteria = createDefaultCriteria(Dictionary.class);

        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<Dictionary> dictionarys = criteria.list();
        return dictionarys;
    }

    public Dictionary saveOrUpdateDictionary(final Dictionary dictionary) {
        if (dictionary.getDateCreate() == null) {
            dictionary.setDateCreate(new Date());
        }
        if (StringUtils.isEmpty(dictionary.getCode())) {
            dictionary.setCode(CoreUtil.generateEntityCode());
        }
        dictionary.setDateUpdate(new Date());
        if (dictionary.getId() != null) {
            if (em.contains(dictionary)) {
                em.refresh(dictionary);
            }
            Dictionary mergedDictionary = em.merge(dictionary);
            em.flush();
            return mergedDictionary;
        } else {
            em.persist(dictionary);
            return dictionary;
        }
    }

    public void deleteDictionary(final Dictionary dictionary) {
        em.remove(dictionary);
    }

}