/**
 *    Copyright (C) 2010-2017 the original author or authors.
 *                  2017 iObserve Project (https://www.iobserve-devops.net)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.jpetstore.domain;

import java.io.Serializable;

/**
 * The Class Sequence.
 *
 * @author Eduardo Macarron
 */
public class Sequence implements Serializable {

  private static final long serialVersionUID = 8278780133180137281L;

  private String name;
  private int nextId;

  /**
   * Default constructor.
   */
  public Sequence() {
  }

  /**
   * Initialize a sequence with name and nextId.
   *
   * @param name
   *            sequence name
   * @param nextId
   *            next id in the sequence
   */
  public Sequence(final String name, final int nextId) {
    this.name = name;
    this.nextId = nextId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public int getNextId() {
    return this.nextId;
  }

  public void setNextId(final int nextId) {
    this.nextId = nextId;
  }

}
