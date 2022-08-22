/*
 * Copyright 2022 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package software.amazon.smithy.rulesengine.reterminus.fn.partition;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import java.util.Iterator;
import java.util.ServiceLoader;
import org.junit.jupiter.api.Test;
import software.amazon.smithy.rulesengine.reterminus.lang.fn.partition.PartitionDataProvider;

public class ServiceLoaderTest {
    @Test
    public void load() {
        ServiceLoader<PartitionDataProvider> dataProviderLoader = ServiceLoader.load(PartitionDataProvider.class);
        Iterator<PartitionDataProvider> iter = dataProviderLoader.iterator();
        assertThat(iter.next().loadPartitions(), not(equalTo(null)));
    }
}