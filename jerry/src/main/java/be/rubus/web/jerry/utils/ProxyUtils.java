/*
 * Copyright 2014-2016 Rudy De Busscher
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
 *
 */
package be.rubus.web.jerry.utils;


/**
 */
public final class ProxyUtils {

    private ProxyUtils() {
    }

    public static Class getUnproxiedClass(Class currentClass) {
        if (isProxiedClass(currentClass)) {
            return currentClass.getSuperclass();
        }
        return currentClass;
    }

    public static String getClassName(Class currentClass) {
        if (isProxiedClass(currentClass)) {
            return currentClass.getSuperclass().getName();
        }
        return currentClass.getName();
    }

    public static boolean isProxiedClass(Class currentClass) {
        return currentClass.getName().contains("$$EnhancerByCGLIB$$") ||
                currentClass.getName().contains("$$FastClassByCGLIB$$") ||
                currentClass.getName().contains("_$$_javassist") ||
                currentClass.getName().contains("$Proxy$_$$_Weld");
    }

}
