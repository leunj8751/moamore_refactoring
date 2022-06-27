package moa.moamore.repository;

import lombok.RequiredArgsConstructor;
import moa.moamore.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {


    private final EntityManager em;

    public Member findOne(String id){
        System.out.println("iiiiid :"+id);
        return em.find(Member.class,id);
    }

    public void save(Member member){
        em.persist(member);
    }


    public List<Member> findByName(String id) {
        return em.createQuery("select m from Member m where m.id = :id",Member.class)
                .setParameter("id",id)
                .getResultList();
    }
}
