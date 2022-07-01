package moa.moamore.repository;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import moa.moamore.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import static moa.moamore.domain.QBudget_expense.budget_expense;


@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryRepository {

    //트랜잭션!!

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    @Transactional
    public void save(Category category) {
        em.persist(category);
    }

    public List<Category> findByType(Money_type type) {

        return em.createQuery("select c from Category c where c.category_type = :type", Category.class)
                .setParameter("type", type).getResultList();
    }

    public List<Category> findByMember(Member member) {
        return em.createQuery("select c from Category c where c.member = :member", Category.class)
                .setParameter("member", member).getResultList();

    }

    public List<Category> findByMemberAndType(Money_type type, Member member) {

        return em.createQuery("select c from Category c where c.category_type = :type and c.member = :member", Category.class)
                .setParameter("type", type)
                .setParameter("member", member)
                .getResultList();
    }


    public Category findOne(Long id) {
        return em.find(Category.class, id);
    }

    @Transactional
    public void delete(Category category) {
        em.merge(category);
    }


    public List<Budget_expense> findTopExpense(Budget budget) {

        return queryFactory.select(
                Projections.fields(Budget_expense.class,
                        budget_expense.category,
                        budget_expense.amount.sum().as("sum_amount"))
                ).from(budget_expense)
                .where(budget_expense.budget.eq(budget))
                .groupBy(budget_expense.category)
                .orderBy(budget_expense.amount.sum().desc())
                .fetch();

    }



}
