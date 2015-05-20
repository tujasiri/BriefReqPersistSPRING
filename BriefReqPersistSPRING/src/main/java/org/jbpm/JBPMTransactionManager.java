package org.jbpm;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import org.hibernate.HibernateException;
import org.hibernate.transaction.TransactionManagerLookup;

public class JBPMTransactionManager implements TransactionManagerLookup {
public Object getTransactionIdentifier(Transaction transaction) {
  return transaction;
}
public TransactionManager getTransactionManager(Properties properties) throws HibernateException {
  try {
	  System.out.println("Transacting!");
    return (TransactionManager) new InitialContext()
      .lookup("java:jboss/TransactionManager");
  } catch (NamingException e) {
    throw new RuntimeException(e);
  }
}

public TransactionManager getTransactionManager() throws Exception {
	  System.out.println("HERE!");

	return (TransactionManager) new InitialContext().lookup("java:jboss/TransactionManager");
}

protected String getName() {
	  System.out.println("getting transmanager HERE!");

    return "java:jboss/TransactionManager";
}

public String getUserTransactionName() {
	  System.out.println("getting useraction HERE!");

    return "java:comp/UserTransaction";
}
	 
	 
}