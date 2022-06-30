package moa.moamore.repository;


import lombok.RequiredArgsConstructor;
import moa.moamore.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryRepository {

    //트랜잭션!!

    private final EntityManager em;

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


//    public List<Budget_expense> findTopExpense(Budget budget) {
//
//                 em.createQuery("select sum(b.amount) from Budget_expense b where b.budget = :budget group by b.category order by sum(b.amount) desc", Budget_expense.class)
//                .setParameter("budget", budget)
//                .getResultList();
//
//        return
//    }
}
