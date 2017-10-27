/*
 * Copyright 2017-present  Skean Project Contributors.
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

package party.threebody.skean.data.query;

/**
 *
 */
public class PagingInfo {
    private final int offset;
    private final int limit;

    public static PagingInfo NA = PagingInfo.ofOffset(null, null);

    public PagingInfo(int offset, int limit) {
        this.limit = limit;
        this.offset = offset;
    }

    public static PagingInfo ofPageNum(Integer pageNum, Integer pageSize) {
        if (pageSize == null) {
            return new PagingInfo(0, 0);
        }
        if (pageNum == null) {
            return new PagingInfo(0, pageSize);
        }
        return new PagingInfo(pageNum * pageSize - pageSize, pageSize);
    }

    public static PagingInfo ofOffset(Integer offset, Integer limit) {
        if (limit == null) {
            return new PagingInfo(0, 0);
        }
        if (offset == null) {
            return new PagingInfo(0, limit);
        }
        return new PagingInfo(offset, limit);
    }

    public boolean isPagingEnabled() {
        return limit > 0;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }
}
