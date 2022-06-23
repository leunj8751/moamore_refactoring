package moa.moamore.repository;

import lombok.RequiredArgsConstructor;
import moa.moamore.domain.Budget;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class BudgetRepository {

    private final EntityManager em;


    private void save(Budget budget){em.persist(budget);} // budget_category에 같이 들어가야 함



}
