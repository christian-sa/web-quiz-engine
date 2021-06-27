package com.christian.webquizengine.model.quiz;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;
// RIGHT NOW NOT WORKING, ALWAYS GENERATES 0
/**
 * Custom ID Generator for the database.
 * Behaviour: If ID is already specified before saving to the database, use it and dont override.
 * Otherwise if ID Column is null, generate an ID using Generator_IDENTITY strategy.
 */
public class QuizIdGenerator extends IdentityGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Serializable id = session.getEntityPersister(null, object).getClassMetadata().getIdentifier(object, session);
        return id != null ? id : super.generate(session, object);
    }
}
