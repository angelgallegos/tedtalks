package com.iodigital.tedtalks.repositories.impl;

import com.iodigital.tedtalks.entity.Speaker;
import com.iodigital.tedtalks.entity.Talk;
import com.iodigital.tedtalks.repositories.CustomSpeakerRepository;
import jakarta.persistence.EntityManager;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.JoinType;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public class CustomSpeakerRepositoryImpl implements CustomSpeakerRepository {

    private final EntityManager entityManager;
    private final Session session;

    public CustomSpeakerRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.session = entityManager.unwrap(Session.class);
    }

    /**
     * Applies an order based on an average:
     * likes/views
     *
     * @param pageable Pageable
     * @return Page<Speaker>
     */
    @Override
    public Page<Speaker> listOrderedByAvgRating(Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Speaker> query = cb.createQuery(Speaker.class);
        Root<Speaker> root = query.from(Speaker.class);
        query.groupBy(root.get("id"));

        Join<Speaker, Talk> joinTalk = root.join("talks", JoinType.INNER);
        Expression<Double> likesSum = cb.sum(joinTalk.get("likes"));
        Expression<Double> viewsSum = cb.sum(joinTalk.get("views"));

        query.select(root).orderBy(cb.desc(cb.quot(likesSum, viewsSum)));
        Query<Speaker> cq = session.createQuery(query);
        List<Speaker> results = cq.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Speaker> booksRootCount = countQuery.from(Speaker.class);
        countQuery.select(cb.count(booksRootCount));
        Long count = session.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(results, pageable, count);
    }

    /**
     * Applies an order based on the lower bound of the Wilson score confidence,
     * defined as:
     * (
     *   (positive + 1.9208) / (positive + negative) -
     *   1.96 *
     *   SQRT(
     *     (positive * negative) / (positive + negative) + 0.9604
     *   ) / (positive + negative)
     * ) / (1 + 3.8416 / (positive + negative))
     * since there are no negative values or dislikes we use the number
     * of views minus the likes to calculate
     *
     * @param pageable Pageable
     * @return Page<Speaker>
     */
    @Override
    public Page<Speaker> listOrderedByWilsonRating(Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Speaker> query = cb.createQuery(Speaker.class);
        Root<Speaker> root = query.from(Speaker.class);
        query.groupBy(root.get("id"));

        Join<Speaker, Talk> joinTalk = root.join("talks", JoinType.INNER);
        Expression<Double> likesSum = cb.sum(joinTalk.get("likes"));
        Expression<Double> viewsSum = cb.sum(joinTalk.get("views"));


        query.select(root).orderBy(cb.desc(
            cb.quot(
                cb.diff(
                    cb.quot(
                        cb.sum(new BigDecimal("1.9208"), likesSum), viewsSum
                    ),

                    cb.prod(
                        new BigDecimal("1.96"),
                        cb.quot(
                            cb.sqrt(
                                cb.sum(new BigDecimal("0.9604"), cb.quot(cb.prod(likesSum, cb.diff(viewsSum, likesSum)), viewsSum))
                            ), viewsSum
                        )
                    )
                ),
                cb.sum(new BigDecimal(1), cb.quot(new BigDecimal("3.8416"), viewsSum))
            )
        ));
        Query<Speaker> cq = session.createQuery(query);
        List<Speaker> results = cq.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Speaker> booksRootCount = countQuery.from(Speaker.class);
        countQuery.select(cb.count(booksRootCount));
        Long count = session.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(results, pageable, count);
    }
}
