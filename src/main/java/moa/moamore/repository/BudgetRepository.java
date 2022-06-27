package moa.moamore.repository;

import lombok.RequiredArgsConstructor;
import moa.moamore.domain.*;
import moa.moamore.dto.CategoryDTO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BudgetRepository {

    private final EntityManager em;


    @Transactional
    public void save(Budget budget){ em.persist(budget);} // budget_category에 같이 들어가야 함

    @Transactional
    public void saveBudget_category(List<Budget_category> budget_categoryList) {
        for(Budget_category c: budget_categoryList){
            em.merge(c);
        }
    }

    public Budget findOne(Member member){

        List<Budget> budgetList= em.createQuery("select b from Budget b where b.member = :member order by created_date",Budget.class)
                .setParameter("member", member)
                .setMaxResults(1)
                .getResultList();


        return budgetList.get(0);
    }

    public List<Budget_category> findBudget_category(Member member,Budget budget){
        return em.createQuery("select b from Budget_category b where b.member = :member and b.budget =:budget",Budget_category.class)
                .setParameter("member",member)
                .setParameter("budget",budget)
                .getResultList();
    }



}
