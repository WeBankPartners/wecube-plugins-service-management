package com.webank.servicemanagement.dto;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

public class QueryRequest {
    protected boolean paging = false;
    protected Pageable pageable = new Pageable();

    private Sorting sorting = new Sorting();
    protected List<Filter> filters = new LinkedList<>();

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public Sorting getSorting() {
        return sorting;
    }

    public void setSorting(Sorting sorting) {
        this.sorting = sorting;
    }

    public boolean isPaging() {
        return paging;
    }

    public void setPaging(boolean paging) {
        this.paging = paging;
    }

    public QueryRequest addEqualsFilter(String name, Object value) {
        filters.add(new Filter(name, "eq", value));
        return this;
    }

    public QueryRequest addGreaterThanFilter(String name, Object value) {
        filters.add(new Filter(name, "gt", value));
        return this;
    }

    public QueryRequest addLessThanFilter(String name, Object value) {
        filters.add(new Filter(name, "lt", value));
        return this;
    }

    public QueryRequest addEqualsFilters(Map<String, Object> filterObject) {
        filterObject.entrySet().forEach(entry -> filters.add(new Filter(entry.getKey(), "eq", entry.getValue())));
        return this;
    }

    public QueryRequest addInFilters(Map<String, Object> filterObject) {
        filterObject.entrySet().forEach(entry -> filters.add(new Filter(entry.getKey(), "in", entry.getValue())));
        return this;
    }

    public QueryRequest addNotEqualsFilter(String name, Object value) {
        filters.add(new Filter(name, "ne", value));
        return this;
    }

    public QueryRequest addInFilter(String name, List<?> values) {
        filters.add(new Filter(name, "in", values));
        return this;
    }

    public QueryRequest addContainsFilter(String name, String value) {
        filters.add(new Filter(name, "contains", value));
        return this;
    }

    public QueryRequest addNotNullFilter(String name) {
        filters.add(new Filter(name, "notNull", null));
        return this;
    }

    public QueryRequest addNullFilter(String name) {
        filters.add(new Filter(name, "null", null));
        return this;
    }

    public static QueryRequest defaultQueryObject() {
        return new QueryRequest();
    }

    public static QueryRequest defaultQueryObject(String name, Object value) {
        return defaultQueryObject().addEqualsFilter(name, value);
    }

    public static QueryRequest buildQueryRequest(String filter, String sorting, String select) throws Exception {
        QueryRequest queryRequest = new QueryRequest();

        if (filter != null && !filter.isEmpty()) {
            String[] filterStrings = StringUtils.split(filter, ",");
            if (filterStrings.length != 2) {
                throw new Exception("Invalid query parameter [filter], please double check");
            }
            if ("id".equals(filterStrings[0])) {
                String id = filterStrings[1];
                queryRequest.addEqualsFilter(filterStrings[0], id);
            } else {
                queryRequest.addEqualsFilter(filterStrings[0], filterStrings[1]);
            }
        }

        // TODO - implement 'sorting' and 'select' function

        return queryRequest;
    }
}
