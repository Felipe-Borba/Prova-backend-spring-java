package com.borba.backendprovasenior.item;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemCustomRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Item> find(String descricao, Item.Tipo tipo, Boolean active) {

        String query = "select P from Item as P ";
        String condition = "where";

        if (descricao != null) {
            query += condition + " P.descricao = :descricao";
            condition = " and ";
        }

        if (tipo != null) {
            query += condition + " P.tipo = :tipo";
            condition = " and ";
        }

        if (active != null) {
            query += condition + " P.active = :active";
        }

        var q = em.createQuery(query, Item.class);

        if (descricao != null) {
            q.setParameter("descricao", descricao);
        }

        if (tipo != null) {
            q.setParameter("tipo", tipo);

        }

        if (active != null) {
            q.setParameter("active", active);
        }

        return q.getResultList();
    }
}
