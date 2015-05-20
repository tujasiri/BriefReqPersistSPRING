//package defaultPackage;
package com.sample;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.internal.runtime.StatefulKnowledgeSession;

public class MyWorkItemHandler implements WorkItemHandler{
	
StatefulKnowledgeSession ksession;

public MyWorkItemHandler() {
	
}

public MyWorkItemHandler(StatefulKnowledgeSession ksession) { 
	
	this.ksession = ksession;
	
}
	

    public void executeWorkItem(final WorkItem workItem, final WorkItemManager manager) {
    	
   /*` 
    	
    	new Thread(new Runnable(){

			public void run() {
				// TODO Auto-generated method stub
				
	*/
			
    	
    	
        System.out.println("EXECUTING WORK ITEM");
        
        System.out.println(org.jbpm.marshalling.impl.JBPMMessages.WorkItem.getDescriptor());
        System.out.println("manager=="+manager.toString());
        System.out.println("workItem ID=="+workItem.getId());
        System.out.println("workItem name=="+workItem.getName());
        System.out.println("workItem state =="+workItem.getState());
        System.out.println("workItem==>"+workItem.toString());
        

        String service = (String) workItem.getParameter("Interface");
        String operation = (String) workItem.getParameter("Operation");
        String parameterType = (String) workItem.getParameter("ParameterType");
        Object parameter = workItem.getParameter("Parameter");
        
		//System.out.println("operation==>"+operation);
        
        try {
            Class<?> c = Class.forName(service);
            Object instance = c.newInstance();
            Class<?>[] classes = null;
            Object[] params = null;
            if (parameterType != null) {
                classes = new Class<?>[] {
                    Class.forName(parameterType)
                };
                params = new Object[] {
                    parameter
                };
            }
            Method method = c.getMethod(operation, classes);
            Object result = method.invoke(instance, params);
            
        
        	 //System.out.println("RESULT==>"+(int)result);
        	 System.out.println("RESULT==>"+result);
         
         if ((int)result == 99){
        	 System.out.println("dying...result==>"+(int)result);
        	 abortWorkItem (workItem, manager);
       
         }
         else{
            manager.completeWorkItem(workItem.getId(), null);
         }
        
        	 
            
            Map<String, Object> results = new HashMap<String, Object>();
            results.put("Result", result);
            
            //manager.completeWorkItem(workItem.getId(), results);
            
        } catch (ClassNotFoundException cnfe) {

	} catch (InstantiationException ie) {
			// TODO Auto-generated catch block
			ie.printStackTrace();
		} catch (IllegalAccessException iae) {
			// TODO Auto-generated catch block
			iae.printStackTrace();
		} catch (NoSuchMethodException nsme) {
			// TODO Auto-generated catch block
			nsme.printStackTrace();
		} catch (SecurityException se) {
			// TODO Auto-generated catch block
			se.printStackTrace();
		} catch (IllegalArgumentException iarge) {
			// TODO Auto-generated catch block
			iarge.printStackTrace();
		} catch (InvocationTargetException ite) {
			// TODO Auto-generated catch block
			ite.printStackTrace();
		}
        
        /*
        BriefVerify bv = new BriefVerify();
        
        try {
			bv.sayHello();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
        
        try {
			//System.out.println("Hell00000 there...before work item...");
        	
        	System.out.println("operation==>"+operation);
        	System.out.println("service==>"+service);
        	
        	/*
			System.out.println(String.format("IN TRY/CATCH, completing work item name==>%s\n and having id ==>%d\n and processInstanceId ==>%d\n",workItem.getName(),workItem.getId(), workItem.getProcessInstanceId() ));
			manager.completeWorkItem(workItem.getId(), null);
			*/
			//manager.completeWorkItem(workItem.getId(), null);
			
			
			//System.out.println("Hell0 there...after work item...");
			
			//abortWorkItem(workItem,manager);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
		}
        
        /*
		}//end run
    	}).start(); //end Thread Implementation
    	*/
    }//end execute

     public void abortWorkItem (WorkItem workItem, WorkItemManager manager) {
        // Nothing
    	 System.out.println("Hell0 there...Aborting...");
    	 //manager.abortWorkItem(org.jbpm.marshalling.impl.JBPMMessages.WorkItem.getDefaultInstance().getId());
    	 
    	 
		 //manager.completeWorkItem(workItem.getId(), null);
		 //System.out.println(String.format("IN ABORT completing work item name==>%s and having id ==>%d",workItem.getName(),workItem.getId() ));
		 
		
    	 
    	 //Thread currentThread = Thread.currentThread();
    	 
    	 System.out.println("Interrupting THREAD...");
    	 
    	 
    	 
    	 //currentThread.interrupt();
    	 
		System.out.println(String.format("PROCESSINSTANCE ID==> %d",ksession.getId()));
    	 
    	 ksession.abortProcessInstance(workItem.getProcessInstanceId());
    	 
		System.out.println(String.format("ABORTED\n"));
    	 ksession.halt();
    	 
		System.out.println(String.format("HALTED\n"));
    	 
    	 manager.abortWorkItem(workItem.getId());
    	 
    	 try {
			ksession.wait(60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(String.format("ABORTING ==> %s",e.getMessage().toString()));
		}
    	 
    	 //System.exit(1000);;
    	 
    }
}
