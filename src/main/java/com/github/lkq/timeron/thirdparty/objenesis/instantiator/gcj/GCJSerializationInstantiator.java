/**
 * Copyright 2006-2016 the original author or authors.
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
package com.github.lkq.timeron.thirdparty.objenesis.instantiator.gcj;

import com.github.lkq.timeron.thirdparty.objenesis.ObjenesisException;
import com.github.lkq.timeron.thirdparty.objenesis.instantiator.ObjectInstantiator;
import com.github.lkq.timeron.thirdparty.objenesis.instantiator.SerializationInstantiatorHelper;

/**
 * Instantiates a class by making a call to internal GCJ private methods. It is only supposed to
 * work on GCJ JVMs. This instantiator will create classes in a way compatible with serialization,
 * calling the first non-serializable superclass' no-arg constructor.
 * 
 * @author Leonardo Mesquita
 * @see ObjectInstantiator
 */
public class GCJSerializationInstantiator<T> extends GCJInstantiatorBase<T> {
   private Class<? super T> superType;

   public GCJSerializationInstantiator(Class<T> type) {
      super(type);
      this.superType = SerializationInstantiatorHelper.getNonSerializableSuperClass(type);
   }

   @Override
   public T newInstance() {
      try {
         return type.cast(newObjectMethod.invoke(dummyStream, type, superType));
      }
      catch(Exception e) {
         throw new ObjenesisException(e);
      }
   }

}
