/*
 * Copyright 2020-2024 Limbo Team (https://github.com/limbo-world).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.limbo.flowjob.worker.starter;

import lombok.Setter;
import lombok.experimental.Delegate;
import org.limbo.flowjob.worker.core.domain.Worker;
import org.limbo.flowjob.worker.starter.processor.WorkerReadyEvent;
import org.limbo.flowjob.worker.starter.properties.WorkerProperties;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

/**
 * @author Brozen
 * @since 2022-09-11
 */
public class SpringDelegatedWorker implements Worker, DisposableBean {

    @Delegate(types = Worker.class)
    private final Worker delegated;

    @Setter(onMethod_ = @Autowired)
    private WorkerProperties properties;


    public SpringDelegatedWorker(Worker delegated) {
        this.delegated = delegated;
    }


    /**
     * Spring 初始化完成后，注册当前 Worker
     */
    @EventListener(WorkerReadyEvent.class)
    public void onApplicationStart(WorkerReadyEvent event) {
        event.getExecutors().forEach(delegated::addExecutor);
        delegated.start(properties.getHeartbeat());
    }


    /**
     * Bean 销毁时，停止 Worker
     */
    @Override
    public void destroy() {
        delegated.stop();
    }

}
