/*
 *   Copyright 2016 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.powermock.api.mockito.internal.mockcreation;

import org.mockito.internal.configuration.GlobalConfiguration;
import org.powermock.api.support.ClassLoaderUtil;
import org.powermock.reflect.Whitebox;

class MockitoStateCleaner {
    
    void clearMockProgress() {
// FIXME?: ThreadSafeMockingProgress has ThreadLocal variable with initial value in mockito2
//        If you clear it MockingProgress for all following tests will be null
//       clearThreadLocalIn(ThreadSafeMockingProgress.class);
    }

    void clearConfiguration() {
        clearThreadLocalIn(GlobalConfiguration.class);
    }

    private void clearThreadLocalIn(Class<?> cls) {
        Whitebox.getInternalState(cls, ThreadLocal.class).set(null);
        final Class<?> clazz;
        if(ClassLoaderUtil.hasClass(cls, ClassLoader.getSystemClassLoader())) {
            clazz = ClassLoaderUtil.loadClass(cls, ClassLoader.getSystemClassLoader());
        } else {
            clazz = ClassLoaderUtil.loadClass(cls, cls.getClassLoader());
        }
        Whitebox.getInternalState(clazz, ThreadLocal.class).set(null);
    }
    
    
}
