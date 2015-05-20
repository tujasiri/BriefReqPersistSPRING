package com.sample;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.drools.persistence.jta.JtaTransactionManager;
import org.jbpm.executor.ExecutorServiceFactory;
import org.jbpm.persistence.JpaProcessPersistenceContextManager;
import org.jbpm.persistence.jta.ContainerManagedTransactionManager;
import org.jbpm.process.audit.AbstractAuditLogger;
import org.jbpm.process.audit.AuditLoggerFactory;
import org.jbpm.process.audit.AuditLoggerFactory.Type;
import org.jbpm.process.audit.JPAWorkingMemoryDbLogger;
import org.jbpm.process.workitem.bpmn2.ServiceTaskHandler;
import org.jbpm.services.task.persistence.JPATaskPersistenceContextManager;
//import org.jbpm.test.JbpmJUnitBaseTestCase;
//import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.persistence.jpa.KieStoreServices;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.EnvironmentName;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.executor.api.ExecutorService;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.persistence.jpa.JPAKnowledgeService;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.kie.internal.utils.KieHelper;

import javax.jms.ConnectionFactory;



import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import bitronix.tm.internal.XAResourceHolderState;
import bitronix.tm.journal.Journal;
import bitronix.tm.resource.jdbc.PoolingDataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;

//import com.sample.BriefVerify.*;

//import org.drools.runtime.*;
/**
 * This is a sample file to test a process.
 */
//public class ProcessTest extends JbpmJUnitBaseTestCase {
public class ProcessTestSpring   {
	
	static EntityManagerFactory emf = null;

	
	public static void main(String[] args) throws Throwable {
		/*

		ProcessTestSpring pt = new ProcessTestSpring();
		
		
		try {
			pt.testProcess();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(String.format("Cause==>%s\n",e.getCause().toString()));
		}
		*/
	}
	/*
	@PersistenceContext
	EntityManager em;
	*/

	public void testProcess() throws Exception  {
			Map transMap = null;
			Iterator it;
		
		// create the entity manager factory and register it in the environment
		
		System.out.println("Started it...");
		
		//jtm.get
		//EntityManagerFactory emf = null;
		UserTransaction ut = null;
		
		if(TransactionManagerServices.isTransactionManagerRunning())
			System.out.println("Transaction Manager is running!");
		else
			System.out.println("Transaction Manager is NOT running!");
		
		//TransactionManagerServices.
		
		/*
	
		i= transMap.keySet().iterator();
			
			while(it.hasNext()){
				String pairKey = it.next().toString();
				String pairVal = transMap.get(pairKey).toString();
				System.out.println(String.format("Transaction Map key==>%s, value==>%s\n",pairKey,pairVal));
			}
		*/
		
		//org.springframework.orm.jpa.JpaTransactionManager tm = new org.springframework.orm.jpa.JpaTransactionManager();
		//javax.transaction.TransactionManager   tm = null;
	/*
		BitronixTransactionManager txm = TransactionManagerServices.getTransactionManager();
		txm.shutdown();
		
		if(txm != null){
			System.out.println("Shutting town previous transactions...");
			transMap = txm.getInFlightTransactions();
			
			it = transMap.keySet().iterator();
			
			while(it.hasNext()){
				String pairKey = it.next().toString();
				String pairVal = transMap.get(pairKey).toString();
				System.out.println(String.format("Transaction Map key==>%s, value==>%s\n",pairKey,pairVal));
			}
			
			txm.shutdown();
		}
		
      Journal tmJournal = TransactionManagerServices.getJournal();
      
      tmJournal.close();
      */
		/*

		PoolingDataSource ds = new PoolingDataSource();
		
		ds.setUniqueName("java:jboss/datasources/jbpmDS");
		ds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
		
		//ds.setClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
		//ds.setClassName("org.h2.jdbcx.JdbcDataSource");
		ds.setMinPoolSize(0);

		ds.setMaxPoolSize(10);

		ds.setAllowLocalTransactions(true);

		//ds.getDriverProperties().put("user", "jbpm6");
		ds.getDriverProperties().put("user", "jbpm6");

		//ds.getDriverProperties().put("password", "jbpm6");
		ds.getDriverProperties().put("password", "jbpm6");

		ds.getDriverProperties().put("url", "jdbc:mysql://localhost:3306/jbpm6");

		ds.getDriverProperties().put("driverClassName", "com.mysql.jdbc.Driver");
		//ds.getDriverProperties().put("driverClassName", "org.h2.Driver");
		
		ds.setTestQuery("select 1;");

			try {
				ds.init();
			} catch (Exception e) {
			System.out.println(String.format("datasource init failed ==>%s",e.getMessage().toString()));
				// TODO: handle exception
			}
		*/
			
			InitialContext initialContext = new InitialContext(); 
			
		
		///tm = TransactionManagerServices.getTransactionManager();
		//tm.begin();
		

		Environment env = KnowledgeBaseFactory.newEnvironment();
		
		ut = (UserTransaction) new InitialContext().lookup( "java:comp/UserTransaction" );
		
		//tm = context.getBean();
		//tm.begin();
		
		//emf = javax.persistence.Persistence.createEntityManagerFactory( "org.persistence.unit" );

		//emf = Persistence.createEntityManagerFactory( "org.persistence.unit.short" );
		//ApplicationContext context = new ClassPathXmlApplicationContext("file:src/main/resources/applicationContextBPMN.xml");
		ApplicationContext context = new ClassPathXmlApplicationContext("/WEB-INF/config/applicationContextBPMN.xml");
		
		emf = (EntityManagerFactory)context.getBean("jbpmEMF");
		
		
		//*****AsyncWorkItemHandler Entity
		//ExecutorService executorService = ExecutorServiceFactory.newExecutorService(ProcessTestSpring.emf);
		ExecutorService executorService = ExecutorServiceFactory.newExecutorService(emf);
		
		//org.springframework.transaction.jta.JtaTransactionManager  jtm = (org.springframework.transaction.jta.JtaTransactionManager)context.getBean("jbpmTxManager");
		
		org.springframework.orm.jpa.JpaTransactionManager  jtm = (org.springframework.orm.jpa.JpaTransactionManager)context.getBean("jbpmTxManager");
		
		//emf = jtm.getEntityManagerFactory();
		//ut = jtm.getUserTransaction();
		//jtm.getTransactionManager();
		//Then obtain an EntityManager from the EntityManagerFactory instance:
		
		//em = emf.createEntityManager();
	

		
		
		//jtm.setDataSource(ds);
		
		
		/*
		jtm.setUserTransaction(ut);
		jtm.setTransactionManagerName("java:comp/TransactionManager");
		*/
		
		try {
			ut.begin();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(String.format("start transaction failed ==>%s",e.getMessage().toString()));
		}
		
		
	/*	
		try {
			em.joinTransaction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(String.format("EXCEPTION ==>%s\n",e.getMessage().toString()));
			
		}
	*/
		/*
	    if(!jtm.isValidateExistingTransaction())
	    	System.out.println("is NOT Valid Transaction...");
	    else
	    	System.out.println("is Valid Transaction...");
	    */
		
		//emf = (EntityManagerFactory)context.getBean("jbpmEMF");
		//tm = (org.springframework.orm.jpa.JpaTransactionManager) context.getBean("jbpmTxManager");
		
	    //System.out.println(String.format("Transaction Manager data==>%s\n", jtm.toString()));
	    //System.out.println(String.format("Transaction Manager data==>%s\n", tm.toString()));
	    System.out.println(String.format("JNDI User Transaction Name==>%s\n", TransactionManagerServices.getConfiguration().getJndiUserTransactionName()));
	    System.out.println(String.format("Resource Config File==>%s",TransactionManagerServices.getConfiguration().getResourceConfigurationFilename()));
	    //System.out.println(String.format("EM ==> %s\n\nEMF Config ==>  %s\n",em.toString(), emf.getProperties().toString()));
	    System.out.println(String.format("UT Status==>  %s\n",ut.getStatus())); 
	    
	    //System.out.println(String.format("jtm info ==> %s\n", jtm.toString(),jtm.getJndiEnvironment().toString()));
		
		env.set( EnvironmentName.ENTITY_MANAGER_FACTORY, emf );
		env.set( EnvironmentName.TRANSACTION_MANAGER, jtm);
		//env.set( EnvironmentName.TRANSACTION_MANAGER, tm);
		
		

		//ApplicationContext context = new ClassPathXmlApplicationContext("file:src/main/resources/applicationContext.xml");
		//ut = (UserTransaction) new InitialContext().lookup( "java:jboss/UserTransaction" );
		//ut.begin();
		//tm.begin();
		
		
		KieHelper kieHelper = new KieHelper();
		
	  	KieBase kbase = kieHelper.addResource(ResourceFactory
					.newClassPathResource("briefverify_mod.bpmn2"))
					.build();

	  	
/*
	  	KieBase kbase = kieHelper.addResource(ResourceFactory
					.newClassPathResource("test_signal.bpmn2"))
					.build();

*/ 
		 
		// create a new knowledge session that uses JPA to store the runtime state
	    System.out.println(String.format("UT Status b4 kession==>  %s\n",ut.getStatus())); 
	  	
		StatefulKnowledgeSession ksession = JPAKnowledgeService.newStatefulKnowledgeSession( kbase, null, env );
		
		
		AbstractAuditLogger auditLogger = AuditLoggerFactory.newJPAInstance();
		
		JPAWorkingMemoryDbLogger JPADbLogger = new JPAWorkingMemoryDbLogger(ksession);
		
	
		//AbstractAuditLogger auditLogger = AuditLoggerFactory.newJPAInstance(emf);

		
		ksession.addEventListener(auditLogger);

		
		KieRuntimeLogger logger = KieServices.Factory.get().getLoggers() 
			.newThreadedFileLogger(ksession, "src/main/resources/mylogfile", 1000);
		

			//TestWorkItemHandler testHandler = getTestWorkItemHandler();
		
		try{
			
			ksession.getWorkItemManager().registerWorkItemHandler("Service Task",  new MyWorkItemHandler(ksession));
			
			//ksession.getWorkItemManager().registerWorkItemHandler("Service Task",  new MyWorkItemHandler());
			
			//ksession.getWorkItemManager().registerWorkItemHandler("Service Task",  new AsyncWorkItemHandler(executorService));
			//executorService.init();
			//ksession.getWorkItemManager().registerWorkItemHandler("Hello",  new MyWorkItemHandler());

  			//ksession.getWorkItemManager().registerWorkItemHandler("Hello", testHandler);
		}
		catch (Exception ex){
				System.err.println("WorkitemHandler==>"+ex.toString());
		}
		System.out.println("before process instance start");
		
		
		System.out.println("Session ID ==>"+ksession.getId());
		

		//ut.begin();

		
		
		ProcessInstance processInstance = ksession.startProcess("ServiceProcess");
		
		
		/*
		WorkItem wi = testHandler.getWorkItem();

		System.out.println("workitem info.name==>"+wi.getName());
		System.out.println("workitem info.id==>"+wi.getId());
		System.out.println("workitem info.state==>"+wi.getState());
		 */
		//System.out.println("Process state=="+processInstance.getState());
		//ProcessInstance processInstance = ksession.startProcess("ServiceProcess");
		
		System.out.println("process instance started");

		ksession.dispose();
		//logger.close();
		//ut.commit();
		
		
		
		
		System.out.println("reached the end...");
	}



}
