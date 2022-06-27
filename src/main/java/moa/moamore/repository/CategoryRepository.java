package moa.moamore.repository;


import lombok.RequiredArgsConstructor;
import moa.moamore.domain.Category;
import moa.moamore.domain.Member;
import moa.moamore.domain.Money_type;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final EntityManager em;

    public void save(Category category){ em.persist(category);}

    public List<Category> findByType(Money_type type){
        return em.createQuery("select c from Category c where c.category_type = :type",Category.class)
                .setParameter("type",type).getResultList();
    }

    public List<Category> findByMember(Member member){
        return em.createQuery("select c from Category c where c.member = :member",Category.class)
                .setParameter("member",member).getResultList();
    }

    public List<Category> findByMemberAndType(Money_type type, Member member){
        return em.createQuery("select c from Category c where c.category_type = :type and c.member = :member",Category.class)
                .setParameter("type",type)
                .setParameter("member",member)
                .getResultList();
    }

    public Category findOne(Long id){
        return em.find(Category.class,id);
    }

    public void delete(Category category){
//        em.createQuery("update ")
    }

}
