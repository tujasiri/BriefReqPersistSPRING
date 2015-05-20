/*
 * Copyright 2013 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sample;

import java.util.Collection;

import org.drools.core.command.impl.GenericCommand;
import org.drools.core.command.impl.KnowledgeCommandContext;
import org.jbpm.process.core.context.exception.ExceptionScope;
import org.jbpm.process.instance.context.exception.ExceptionScopeInstance;
import org.jbpm.workflow.instance.NodeInstanceContainer;
import org.jbpm.workflow.instance.WorkflowProcessInstance;
import org.jbpm.workflow.instance.node.WorkItemNodeInstance;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.NodeInstance;
import org.kie.api.runtime.process.WorkItem;
import org.kie.internal.command.Context;
import org.kie.internal.executor.api.CommandCallback;
import org.kie.internal.executor.api.CommandContext;
import org.kie.internal.executor.api.ExecutionResults;
import org.kie.internal.runtime.manager.RuntimeManagerRegistry;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dedicated callback for <code>AsyncWorkItemHandler</code> that is responsible for:
 * <ul>
 *  <li>completing work item in case of successful execution</li>
 *  <li>attempting to handle exception (by utilizing ExceptionScope mechanism) in case of unsuccessful execution</li>
 * </ul>
 */
public class AsyncWorkItemHandlerCmdCallback implements CommandCallback {
    
    private static final Logger logger = LoggerFactory.getLogger(AsyncWorkItemHandlerCmdCallback.class);

    @Override
    public void onCommandDone(CommandContext ctx, ExecutionResults results) {
        WorkItem workItem = (WorkItem) ctx.getData("workItem");
        logger.debug("About to complete work item {}", workItem);
        
        // find the right runtime to do the complete
        RuntimeManager manager = getRuntimeManager(ctx);
        RuntimeEngine engine = manager.getRuntimeEngine(ProcessInstanceIdContext.get((Long) ctx.getData("processInstanceId")));
        try {
            engine.getKieSession().getWorkItemManager().completeWorkItem(workItem.getId(), results.getData());
        } finally {
            manager.disposeRuntimeEngine(engine);
        }
    }

    @Override
    public void onCommandError(CommandContext ctx, final Throwable exception) {
        
        final Long processInstanceId = (Long) ctx.getData("processInstanceId");
        final WorkItem workItem = (WorkItem) ctx.getData("workItem");
        
        
        // find the right runtime to do the complete
        RuntimeManager manager = getRuntimeManager(ctx);
        RuntimeEngine engine = manager.getRuntimeEngine(ProcessInstanceIdContext.get(processInstanceId));
        try {
            
            engine.getKieSession().execute(new GenericCommand<Void>() {

                private static final long serialVersionUID = 1L;

                @Override
                public Void execute(Context context) {
                    KieSession ksession = ((KnowledgeCommandContext) context).getKieSession();
                    WorkflowProcessInstance processInstance = (WorkflowProcessInstance) ksession.getProcessInstance(processInstanceId);        
                    NodeInstance nodeInstance = getNodeInstance(workItem, processInstance);
                    
                    String exceptionName = exception.getClass().getName();
                    ExceptionScopeInstance exceptionScopeInstance = (ExceptionScopeInstance)
                        ((org.jbpm.workflow.instance.NodeInstance)nodeInstance).resolveContextInstance(ExceptionScope.EXCEPTION_SCOPE, exceptionName);
                    if (exceptionScopeInstance != null) {
                        
                        exceptionScopeInstance.handleException(exceptionName, exception);
                    }
                    return null;
                }
            });
            
        } catch(Exception e) {
          logger.error("Error when handling callback from executor", e);  
        } finally {
            manager.disposeRuntimeEngine(engine);
        }
    }
    
    protected RuntimeManager getRuntimeManager(CommandContext ctx) {
        String deploymentId = (String) ctx.getData("deploymentId");
        RuntimeManager runtimeManager = RuntimeManagerRegistry.get().getManager(deploymentId);
        
        if (runtimeManager == null) {
            throw new IllegalStateException("There is no runtime manager for deployment " + deploymentId);
        }
        
        return runtimeManager;
    }
    
    protected NodeInstance getNodeInstance(WorkItem workItem, WorkflowProcessInstance processInstance) {
        Collection<NodeInstance> nodeInstances = processInstance.getNodeInstances();
        
        return getNodeInstance(workItem, nodeInstances);
    }
    
    protected NodeInstance getNodeInstance(WorkItem workItem, Collection<NodeInstance> nodeInstances) {
    	for (NodeInstance nodeInstance : nodeInstances) {
            if (nodeInstance instanceof WorkItemNodeInstance) {
                if (((WorkItemNodeInstance) nodeInstance).getWorkItemId() == workItem.getId()) {
                    return nodeInstance;
                }
            } else if (nodeInstance instanceof NodeInstanceContainer) {
            	NodeInstance found = getNodeInstance(workItem, ((NodeInstanceContainer) nodeInstance).getNodeInstances());
            	if (found != null) {
            		return found;
            	}
            }
        }
    	
    	return null;
    }
    
}