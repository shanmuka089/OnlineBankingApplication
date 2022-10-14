package com.abc.bankingApp.properties;

import java.io.Serializable;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class TransactionIdGenerator implements IdentifierGenerator{
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {				
		Random random=new Random();
		Long transactionId=1000000000+random.nextLong(999999999);
		return transactionId;
	}

}
