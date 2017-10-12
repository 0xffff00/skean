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
