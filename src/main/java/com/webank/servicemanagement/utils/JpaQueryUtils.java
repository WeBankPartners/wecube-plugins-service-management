package com.webank.servicemanagement.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import org.hibernate.proxy.HibernateProxy;

import com.google.common.base.Strings;
import com.webank.servicemanagement.dto.Filter;
import com.webank.servicemanagement.dto.FilterOperator;
import com.webank.servicemanagement.dto.Pageable;
import com.webank.servicemanagement.dto.Sorting;

public class JpaQueryUtils {

	public static void applyPaging(boolean isPaging, Pageable pageable, TypedQuery typedQuery) {
		if (isPaging && pageable != null) {
			int startIndex = pageable.getStartIndex();
			int pageSize = pageable.getPageSize();
			typedQuery.setFirstResult(startIndex);
			typedQuery.setMaxResults(pageSize);
		}
	}

	public static void applyFilter(CriteriaBuilder cb, CriteriaQuery query, List<Filter> filters,
			Map<String, Expression> selectionMap, Map<String, Class<?>> fieldTypeMap, List<Predicate> predicates,
			Predicate accessControlPredicate) throws Exception {
		if (predicates == null) {
			predicates = new LinkedList<>();
		}
		for (Filter filter : filters) {
			Expression filterExpr = validateFilterName(selectionMap, filter);
			switch (FilterOperator.fromCode(filter.getOperator())) {
			case IN:
				processInOperator(cb, predicates, filter, filterExpr);
				break;
			case CONTAINS:
				processContainsOperator(cb, predicates, filter, filterExpr);
				break;
			case EQUAL:
				processEqualsOperator(cb, predicates, filter, filterExpr);
				break;
			case GREATER:
				processGreaterOperator(cb, predicates, filter, filterExpr);
				break;
			case LESS:
				processLessOperator(cb, predicates, filter, filterExpr);
				break;
			case NOT_EQUAL:
				processNotEqualsOperator(cb, predicates, filter, filterExpr);
				break;
			case NOT_NULL:
				predicates.add(cb.isNotNull(filterExpr));
				break;
			case NULL:
				predicates.add(cb.isNull(filterExpr));
				break;
			default:
				throw new Exception(String.format("Filter operator [%s] is unsupportted.", filter.getOperator()));
			}
		}

		Predicate mergedFilterPredicate;
		if (predicates.size() > 0) {

			mergedFilterPredicate = cb.and(predicates.toArray(new Predicate[0]));

		} else {
			mergedFilterPredicate = cb.conjunction();
		}

		if (accessControlPredicate != null) {
			query.where(cb.and(mergedFilterPredicate, accessControlPredicate));
		} else {
			query.where(mergedFilterPredicate);
		}
	}
	
    public static void applySorting(Sorting sorting, CriteriaBuilder cb, CriteriaQuery query, Map<String, Expression> selectionMap) {
        if (sorting != null && sorting.getField() != null && selectionMap.get(sorting.getField()) == null) {
            throw new IllegalArgumentException(String.format("Sorting field name [%s] is invalid.", sorting.getField()));
        }

        if (sorting != null && (!Strings.isNullOrEmpty(sorting.getField()))) {
            boolean asc = sorting.getAsc();
            String sortField = sorting.getField();
            if (asc) {
                query.orderBy(cb.asc(selectionMap.get(sortField)));
            } else {
                query.orderBy(cb.desc(selectionMap.get(sortField)));
            }
        }
    }

	public static void processNotEqualsOperator(CriteriaBuilder cb, List<Predicate> predicates, Filter filter,
			Expression filterExpr) throws Exception {

		Object value = filter.getValue();
		if (value instanceof String) {
			if (filterExpr.getJavaType().equals(Timestamp.class)) {
				java.util.Date date = DateUtils.convertToTimestamp((String) value);
				predicates.add(cb.notEqual(filterExpr, new Timestamp(date.getTime())));
			} else {
				predicates.add(cb.notEqual(cb.upper(filterExpr), (String) filter.getValue().toString().toUpperCase()));
			}
		} else {
			predicates.add(cb.notEqual(filterExpr, value));
		}
	}

	public static void processLessOperator(CriteriaBuilder cb, List<Predicate> predicates, Filter filter,
			Expression filterExpr) throws Exception {
		Object value = filter.getValue();
		if (value instanceof Date) {
			Timestamp timestamp = new Timestamp(((Date) value).getTime());
			predicates.add(cb.lessThan(filterExpr, timestamp));
		} else if (value instanceof String) {
			if (filterExpr.getJavaType().equals(Timestamp.class)) {
				java.util.Date date = DateUtils.convertToTimestamp(String.valueOf(value));
				predicates.add(cb.lessThan(filterExpr, new Timestamp(date.getTime())));
			} else {
				predicates.add(cb.lessThan(filterExpr, (String) value));
			}
		} else if (value instanceof Number) {
			if (value instanceof Integer)
				predicates.add(cb.lessThan(filterExpr, (Integer) value));
			if (value instanceof Long)
				predicates.add(cb.lessThan(filterExpr, (Long) value));
			if (value instanceof Float)
				predicates.add(cb.lessThan(filterExpr, (Float) value));
			if (value instanceof Double)
				predicates.add(cb.lessThan(filterExpr, (Double) value));
		}
	}

	public static void processGreaterOperator(CriteriaBuilder cb, List<Predicate> predicates, Filter filter,
			Expression filterExpr) throws Exception {
		Object value = filter.getValue();
		if (value instanceof Date) {
			Timestamp timestamp = new Timestamp(((Date) value).getTime());
			predicates.add(cb.greaterThan(filterExpr, timestamp));
		} else if (value instanceof String) {
			if (filterExpr.getJavaType().equals(Timestamp.class)) {
				java.util.Date date = DateUtils.convertToTimestamp((String) value);
				predicates.add(cb.greaterThan(filterExpr, new Timestamp(date.getTime())));
			} else {
				predicates.add(cb.greaterThan(filterExpr, (String) value));
			}
		} else if (value instanceof Number) {
			if (value instanceof Integer)
				predicates.add(cb.greaterThan(filterExpr, (Integer) value));
			if (value instanceof Long)
				predicates.add(cb.greaterThan(filterExpr, (Long) value));
			if (value instanceof Float)
				predicates.add(cb.greaterThan(filterExpr, (Float) value));
			if (value instanceof Double)
				predicates.add(cb.greaterThan(filterExpr, (Double) value));
		}
	}

	public static void processEqualsOperator(CriteriaBuilder cb, List<Predicate> predicates, Filter filter,
			Expression filterExpr) throws Exception {
		Object value = filter.getValue();
		Class expectedType = filterExpr.getJavaType();

		if (value instanceof String && expectedType.equals(Timestamp.class)) {// datetime
			java.util.Date date = DateUtils.convertToTimestamp((String) value);
			predicates.add(cb.equal(filterExpr, new Timestamp(date.getTime())));
		} else if (String.class.equals(expectedType)) {// string
			predicates.add(cb.equal(cb.upper(filterExpr), (String) filter.getValue().toString().toUpperCase()));
		} else {
			predicates.add(cb.equal(filterExpr, ClassUtils.toObject(expectedType, value)));
		}

	}

	public static void processContainsOperator(CriteriaBuilder cb, List<Predicate> predicates, Filter filter,
			Expression filterExpr) throws Exception {
		if (!(filter.getValue() instanceof String)) {
			throw new Exception("Filter value should be string for 'contains' operator.");
		}
		String filterVal = (String) filter.getValue();
		predicates.add(cb.like(cb.upper(filterExpr), "%" + filterVal.toUpperCase() + "%"));
	}

	public static void processInOperator(CriteriaBuilder cb, List<Predicate> predicates, Filter filter,
			Expression filterExpr) throws Exception {
		Class<?> expectedType = filterExpr.getJavaType();
		if (!(filter.getValue() instanceof List)) {
			throw new Exception("Filter value should be list for 'in' operator.");
		}
		List<?> values = (List<?>) filter.getValue();

		if (expectedType == null) {
			if (filter.getValue() instanceof List) {
				expectedType = ((List) filter.getValue()).get(0).getClass();
			} else {
				expectedType = filter.getValue().getClass();
			}
		}

		CriteriaBuilder.In<Object> in = null;
		if (expectedType.equals(String.class)) {
			in = cb.in(cb.upper(filterExpr));
		} else {
			in = cb.in(filterExpr);
		}

		for (Object val : values) {
			if (String.class.equals(expectedType)) {
				in.value(String.valueOf(val).toUpperCase());
			} else if (val.getClass().equals(expectedType)) {
				in.value(val);
			} else {
				in.value(ClassUtils.toObject(expectedType, String.valueOf(val)));
			}
		}
		predicates.add(in);
	}

	private static Expression validateFilterName(Map<String, Expression> selectionMap, Filter filter) throws Exception {
		Expression filterExpr = selectionMap.get(filter.getName());
		if (filterExpr == null) {
			throw new Exception(String.format("Given filter name [%s] is not existed.", filter.getName()));
		}
		return filterExpr;
	}

	public static <T> T findEager(EntityManager em, Class<T> type, Object id) {
		T entity = em.find(type, id);
		if (entity instanceof HibernateProxy) {
			entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
		}
		return entity;
	}

}
