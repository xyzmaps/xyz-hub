/*
 * Copyright (C) 2017-2022 HERE Europe B.V.
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
 * SPDX-License-Identifier: Apache-2.0
 * License-Filename: LICENSE
 */

package com.here.xyz.psql.query;

import com.here.xyz.connectors.ErrorResponseException;
import com.here.xyz.events.Event;
import com.here.xyz.psql.DatabaseHandler;
import com.here.xyz.psql.QueryRunner;
import java.sql.SQLException;

public abstract class XyzEventBasedQueryRunner<E extends Event, R extends Object> extends QueryRunner<E, R> {

  public XyzEventBasedQueryRunner(E input, DatabaseHandler dbHandler)
      throws SQLException, ErrorResponseException {
    super(input, dbHandler);
  }

  protected String getDefaultTable(E event) {
    return dbHandler.getConfig().readTableFromEvent(event);
  }
}
