/*
 * Copyright 2017 The Embulk project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.embulk.base.restclient;

import java.util.List;
import org.embulk.base.restclient.record.RecordImporter;
import org.embulk.base.restclient.record.ValueLocator;
import org.embulk.config.ConfigDiff;
import org.embulk.config.TaskReport;
import org.embulk.spi.PageBuilder;
import org.embulk.spi.Schema;

public abstract class DispatchingRestClientInputPluginDelegate<T extends RestClientInputTaskBase>
        implements RestClientInputPluginDelegate<T> {
    public DispatchingRestClientInputPluginDelegate() {
        this.delegateSelected = null;
    }

    @Override  // Overridden from |InputTaskValidatable|
    public final void validateInputTask(final T task) {
        final RestClientInputPluginDelegate<T> delegate = this.cacheDelegate(task);
        delegate.validateInputTask(task);
    }

    @Override  // Overridden from |ServiceResponseMapperBuildable|
    public final ServiceResponseMapper<? extends ValueLocator> buildServiceResponseMapper(final T task) {
        final RestClientInputPluginDelegate<T> delegate = this.cacheDelegate(task);
        return delegate.buildServiceResponseMapper(task);
    }

    @Override  // Overridden from |ConfigDiffBuildable|
    public final ConfigDiff buildConfigDiff(
            final T task, final Schema schema, final int taskCount, final List<TaskReport> taskReports) {
        final RestClientInputPluginDelegate<T> delegate = this.cacheDelegate(task);
        return delegate.buildConfigDiff(task, schema, taskCount, taskReports);
    }

    @Override  // Overridden from |ServiceDataSplitterBuildable|
    public final ServiceDataSplitter<T> buildServiceDataSplitter(final T task) {
        final RestClientInputPluginDelegate<T> delegate = this.cacheDelegate(task);
        return delegate.buildServiceDataSplitter(task);
    }

    @Override  // Overridden from |ServiceDataIngestable|
    public final TaskReport ingestServiceData(
            final T task, final RecordImporter recordImporter, final int taskIndex, final PageBuilder pageBuilder) {
        final RestClientInputPluginDelegate<T> delegate = this.cacheDelegate(task);
        return delegate.ingestServiceData(task, recordImporter, taskIndex, pageBuilder);
    }

    /**
     * Returns an appropriate Delegate instance for the given Task.
     *
     * This method is to be overridden by the plugin Delegate class.
     */
    protected abstract RestClientInputPluginDelegate<T> dispatchPerTask(T task);

    private RestClientInputPluginDelegate<T> cacheDelegate(final T task) {
        if (this.delegateSelected == null) {
            this.delegateSelected = this.dispatchPerTask(task);
        }
        return this.delegateSelected;
    }

    private RestClientInputPluginDelegate<T> delegateSelected;
}
