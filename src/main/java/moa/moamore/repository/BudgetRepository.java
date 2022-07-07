package moa.moamore.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import moa.moamore.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static moa.moamore.domain.QBudget_expense.budget_expense;
import static moa.moamore.domain.QExcept_budget.except_budget;


@Repository
@RequiredArgsConstructor
public class BudgetRepository{

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public void save(Budget budget) {
            em.persist(budget);
    }

    public void saveBudget_categories(List<Budget_category> budget_categoryList) {
        for (Budget_category c : budget_categoryList) {
            em.merge(c);
        }
    }

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
        List<Budget> budgetList =
                em.createQuery("select b from Budget b where b.start_day <= :date and b.end_day >= :date and b.budget_status='ongoing'", Budget.class)
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

    public Budget_category findBudget_category(Budget budget, Category category) {

        List<Budget_category> list = em.createQuery("select b from Budget_category b where b.budget =:budget and b.category =:category", Budget_category.class)
                .setParameter("budget", budget)
                .setParameter("category", category)
                .getResultList();

        if(list.size() >0){
            return list.get(0);
        }

        return null;
    }


    @Transactional
    public void saveBudget_expense(Budget_expense budget_expense) {
        em.persist(budget_expense);
    }

    @Transactional
    public void saveExceptBudget(Except_budget except_budget) {
        em.persist(except_budget);
    }

    public List<Budget_expense> findBudgeTopExpense(Long budgetId) {

        return em.createQuery("select b from Budget_expense b inner join b.budget g on g.id = :budgetId order by amount desc", Budget_expense.class).
                setParameter("budgetId", budgetId)
                .setMaxResults(3)
                .getResultList();
    }

    public List<Budget_expense> findBudgetExpenseList(LocalDate date,String content) {

        List<Budget_expense> list = queryFactory
                .selectFrom(budget_expense)
                .where(budget_expense.expense_date.eq(date),
                        eqContent(content))
                .fetch();


        return list;

    }

    public List<Except_budget> findExceptBudgetList(LocalDate date,String content) {

        List<Except_budget> list = queryFactory
                .selectFrom(except_budget)
                .where(except_budget.expense_date.eq(date),
                        eqContent(content))
                .fetch();


        return list;

    }

    private BooleanExpression eqContent(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        return budget_expense.content.eq(content);
    }


}
