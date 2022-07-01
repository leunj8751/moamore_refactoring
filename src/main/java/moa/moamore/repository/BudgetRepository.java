package moa.moamore.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import moa.moamore.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static moa.moamore.domain.QBudget_expense.budget_expense;
import static moa.moamore.domain.QExcept_budget.except_budget;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BudgetRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Transactional
    public void save(Budget budget) {
        if (budget.getId() != null) {
            em.merge(budget);
        } else {
            em.persist(budget);
        }

    }

    @Transactional
    public void saveBudget_categories(List<Budget_category> budget_categoryList) {
        for (Budget_category c : budget_categoryList) {
            em.merge(c);
        }
    }

    @Transactional
    public void saveBudget_category(Budget_category budget_category) {

        em.merge(budget_category);
    }

    public Budget findOne(Member member) {

        List<Budget> budgetList = em.createQuery("select b from Budget b where b.member = :member order by created_date", Budget.class)
                .setParameter("member", member)
                .setMaxResults(1)
                .getResultList();

        return budgetList.get(0);
    }

    public Budget findOne(Long id) {
        return em.find(Budget.class, id);
    }


    public Budget findOne(LocalDate date) {

        List<Budget> budgetList = em.createQuery("select b from Budget b where b.start_day <= :date and b.end_day >= :date", Budget.class)
                .setParameter("date", date)
                .setParameter("date", date)
                .getResultList();

        if (budgetList.size() > 0) {
            return budgetList.get(0);
        } else {
            return null;
        }
    }


    //member 없어도 되지 않나?
    public List<Budget_category> findBudget_category(Member member, Budget budget) {
        return em.createQuery("select b from Budget_category b where b.member = :member and b.budget =:budget", Budget_category.class)
                .setParameter("member", member)
                .setParameter("budget", budget)
                .getResultList();
    }

    public List<Budget_category> findBudget_category(Budget budget) {
        return em.createQuery("select b from Budget_category b where b.budget =:budget", Budget_category.class)
                .setParameter("budget", budget)
                .getResultList();
    }

    public Budget_category findBudget_category(Member member, Budget budget, Category category) {
        return em.createQuery("select b from Budget_category b where b.member = :member and b.budget =:budget and b.category =:category", Budget_category.class)
                .setParameter("member", member)
                .setParameter("budget", budget)
                .setParameter("category", category)
                .getSingleResult();
    }



    public List<Budget> findBudgetList(Member member) {
        return em.createQuery("select b from Budget b where b.member = :member", Budget.class)
                .setParameter("member", member).getResultList();
    }

    @Transactional
    public void saveBudget_expense(Budget_expense budget_expense) {
        em.persist(budget_expense);
    }

    @Transactional
    public void saveExceptBudget(Except_budget except_budget) {
        em.persist(except_budget);
    }

    public List<Budget_expense> findBudgeTopExpense(Budget budget) {
        return em.createQuery("select b from Budget_expense b where b.budget = :budget order by amount desc", Budget_expense.class).
                setParameter("budget", budget)
                .setMaxResults(3)
                .getResultList();
    }

    public List<Budget_expense> findBudgetExpenseList(LocalDate date,String content) {

        return queryFactory.select(
                        Projections.fields(Budget_expense.class,
                                budget_expense.category,
                                budget_expense.amount,
                                budget_expense.content)
                ).from(budget_expense,except_budget)
                .fetch();

    }

    private BooleanExpression eqContent(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        return budget_expense.content.eq(content);
    }

    private BooleanExpression eqDate(LocalDate date) {
        if (StringUtils.isEmpty(String.valueOf(date))) {
            return null;
        }
        return budget_expense.created_date.eq(LocalDateTime.from(date));
    }

}
