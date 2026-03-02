// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

suite("test_to_timestamp_tz") {
    sql "set time_zone = '+08:00';"

    qt_basic """
        select
            to_timestamp_tz('2024-01-15 12:30:45 +03:00', 'YYYY-MM-DD HH24:MI:SS TZH:TZM'),
            to_timestamp_tz('2024-01-15 12:30:45 -05:30', 'YYYY-MM-DD HH24:MI:SS TZH:TZM');
    """

    qt_fraction """
        select to_timestamp_tz('2024-01-15 12:30:45.123456 +03:00', 'YYYY-MM-DD HH24:MI:SS.FF6 TZH:TZM');
    """

    qt_invalid_returns_null """
        select to_timestamp_tz('2024-01-15 12:30:45 +25:00', 'YYYY-MM-DD HH24:MI:SS TZH:TZM') is null;
    """

    sql "set time_zone = default;"
}
