/*******************************************************************************
 * BSD 3-Clause License
 * 
 * Copyright (c) 2021, N2M
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package kr.co.n2m.smartcity.datapublish.common.config;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
    protected Logger errorLogger = LoggerFactory.getLogger("error");


	@Bean(name = "threadPoolTaskReceiver")
	public Executor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(10);
		taskExecutor.setMaxPoolSize(50);
		taskExecutor.setQueueCapacity(100);
		taskExecutor.setThreadNamePrefix("DATAPUBLISH--RECEIVER-JOB-");
		taskExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				try {
					executor.getQueue().put(r);
				} catch (InterruptedException e) {
					throw new RejectedExecutionException("[TaskReceiver] There was an unexpected InterruptedException whilst waiting to add a Runnable in the executor's blocking queue", e);
				}
			}
		});
		taskExecutor.initialize();
		
		return new HandlingExecutor(taskExecutor);
	}
	
	@Bean(name = "threadPoolTaskSender")
	public Executor threadPoolTaskSender() {
		
		logger.debug("AvailableProcessors => " + String.valueOf(Runtime.getRuntime().availableProcessors()));
		
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//		taskExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
		taskExecutor.setCorePoolSize(12);
		taskExecutor.setMaxPoolSize(50);
		taskExecutor.setQueueCapacity(50);
		taskExecutor.setThreadNamePrefix("DATAPUBLISH-SENDER-JOB-");
		taskExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				try {
					executor.getQueue().put(r);
				} catch (InterruptedException e) {
					throw new RejectedExecutionException("[TaskSender] There was an unexpected InterruptedException whilst waiting to add a Runnable in the executor's blocking queue", e);
				}
			}
		});
		taskExecutor.initialize();
		
		return new HandlingExecutor(taskExecutor);
	}
	
	public class HandlingExecutor implements AsyncTaskExecutor {
		
		private AsyncTaskExecutor executor;
		
		public HandlingExecutor(AsyncTaskExecutor executor) {
			this.executor = executor;
		}

		@Override
		public void execute(Runnable task) {
			executor.execute(task);
			
		}

		@Override
		public void execute(Runnable task, long startTimeout) {
			executor.execute(createWrappedRunnable(task), startTimeout);
			
		}

		@Override
		public Future<?> submit(Runnable task) {
			return executor.submit(createWrappedRunnable(task));
		}

		@Override
		public <T> Future<T> submit(Callable<T> task) {
			return executor.submit(createCallable(task));
		}
		
		private <T> Callable<T> createCallable(final Callable<T> task) {
            return new Callable<T>() {
                @Override
                public T call() throws Exception {
                    try {
                        return task.call();
                    } catch (Exception ex) {
                        handle(ex);
                        throw ex;
                    }
                }
            };
        }
 
        private Runnable createWrappedRunnable(final Runnable task) {
            return new Runnable() {
                @Override
                public void run() {
                    try {
                        task.run();
                    } catch (Exception ex) {
                        handle(ex);
                    }
                }
            };
        }
 
        private void handle(Exception ex) {
            errorLogger.info("Failed to execute task. : {}", ex.getMessage());
            errorLogger.error("Failed to execute task. ",ex);
        }

	}
}
