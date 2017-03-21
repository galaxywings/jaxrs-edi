package com.wjelogistics.edi.esb.model.entity;

import java.util.ArrayList;
import java.util.List;

public class VesselInfoExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vessel_info
     *
     * @mbg.generated Tue Mar 21 14:01:10 CST 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vessel_info
     *
     * @mbg.generated Tue Mar 21 14:01:10 CST 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vessel_info
     *
     * @mbg.generated Tue Mar 21 14:01:10 CST 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vessel_info
     *
     * @mbg.generated Tue Mar 21 14:01:10 CST 2017
     */
    public VesselInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vessel_info
     *
     * @mbg.generated Tue Mar 21 14:01:10 CST 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vessel_info
     *
     * @mbg.generated Tue Mar 21 14:01:10 CST 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vessel_info
     *
     * @mbg.generated Tue Mar 21 14:01:10 CST 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vessel_info
     *
     * @mbg.generated Tue Mar 21 14:01:10 CST 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vessel_info
     *
     * @mbg.generated Tue Mar 21 14:01:10 CST 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vessel_info
     *
     * @mbg.generated Tue Mar 21 14:01:10 CST 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vessel_info
     *
     * @mbg.generated Tue Mar 21 14:01:10 CST 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vessel_info
     *
     * @mbg.generated Tue Mar 21 14:01:10 CST 2017
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vessel_info
     *
     * @mbg.generated Tue Mar 21 14:01:10 CST 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vessel_info
     *
     * @mbg.generated Tue Mar 21 14:01:10 CST 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vessel_info
     *
     * @mbg.generated Tue Mar 21 14:01:10 CST 2017
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUncodeIsNull() {
            addCriterion("uncode is null");
            return (Criteria) this;
        }

        public Criteria andUncodeIsNotNull() {
            addCriterion("uncode is not null");
            return (Criteria) this;
        }

        public Criteria andUncodeEqualTo(String value) {
            addCriterion("uncode =", value, "uncode");
            return (Criteria) this;
        }

        public Criteria andUncodeNotEqualTo(String value) {
            addCriterion("uncode <>", value, "uncode");
            return (Criteria) this;
        }

        public Criteria andUncodeGreaterThan(String value) {
            addCriterion("uncode >", value, "uncode");
            return (Criteria) this;
        }

        public Criteria andUncodeGreaterThanOrEqualTo(String value) {
            addCriterion("uncode >=", value, "uncode");
            return (Criteria) this;
        }

        public Criteria andUncodeLessThan(String value) {
            addCriterion("uncode <", value, "uncode");
            return (Criteria) this;
        }

        public Criteria andUncodeLessThanOrEqualTo(String value) {
            addCriterion("uncode <=", value, "uncode");
            return (Criteria) this;
        }

        public Criteria andUncodeLike(String value) {
            addCriterion("uncode like", value, "uncode");
            return (Criteria) this;
        }

        public Criteria andUncodeNotLike(String value) {
            addCriterion("uncode not like", value, "uncode");
            return (Criteria) this;
        }

        public Criteria andUncodeIn(List<String> values) {
            addCriterion("uncode in", values, "uncode");
            return (Criteria) this;
        }

        public Criteria andUncodeNotIn(List<String> values) {
            addCriterion("uncode not in", values, "uncode");
            return (Criteria) this;
        }

        public Criteria andUncodeBetween(String value1, String value2) {
            addCriterion("uncode between", value1, value2, "uncode");
            return (Criteria) this;
        }

        public Criteria andUncodeNotBetween(String value1, String value2) {
            addCriterion("uncode not between", value1, value2, "uncode");
            return (Criteria) this;
        }

        public Criteria andEngNameIsNull() {
            addCriterion("eng_name is null");
            return (Criteria) this;
        }

        public Criteria andEngNameIsNotNull() {
            addCriterion("eng_name is not null");
            return (Criteria) this;
        }

        public Criteria andEngNameEqualTo(String value) {
            addCriterion("eng_name =", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameNotEqualTo(String value) {
            addCriterion("eng_name <>", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameGreaterThan(String value) {
            addCriterion("eng_name >", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameGreaterThanOrEqualTo(String value) {
            addCriterion("eng_name >=", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameLessThan(String value) {
            addCriterion("eng_name <", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameLessThanOrEqualTo(String value) {
            addCriterion("eng_name <=", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameLike(String value) {
            addCriterion("eng_name like", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameNotLike(String value) {
            addCriterion("eng_name not like", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameIn(List<String> values) {
            addCriterion("eng_name in", values, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameNotIn(List<String> values) {
            addCriterion("eng_name not in", values, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameBetween(String value1, String value2) {
            addCriterion("eng_name between", value1, value2, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameNotBetween(String value1, String value2) {
            addCriterion("eng_name not between", value1, value2, "engName");
            return (Criteria) this;
        }

        public Criteria andDirectionIsNull() {
            addCriterion("direction is null");
            return (Criteria) this;
        }

        public Criteria andDirectionIsNotNull() {
            addCriterion("direction is not null");
            return (Criteria) this;
        }

        public Criteria andDirectionEqualTo(String value) {
            addCriterion("direction =", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionNotEqualTo(String value) {
            addCriterion("direction <>", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionGreaterThan(String value) {
            addCriterion("direction >", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionGreaterThanOrEqualTo(String value) {
            addCriterion("direction >=", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionLessThan(String value) {
            addCriterion("direction <", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionLessThanOrEqualTo(String value) {
            addCriterion("direction <=", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionLike(String value) {
            addCriterion("direction like", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionNotLike(String value) {
            addCriterion("direction not like", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionIn(List<String> values) {
            addCriterion("direction in", values, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionNotIn(List<String> values) {
            addCriterion("direction not in", values, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionBetween(String value1, String value2) {
            addCriterion("direction between", value1, value2, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionNotBetween(String value1, String value2) {
            addCriterion("direction not between", value1, value2, "direction");
            return (Criteria) this;
        }

        public Criteria andTerminalIsNull() {
            addCriterion("terminal is null");
            return (Criteria) this;
        }

        public Criteria andTerminalIsNotNull() {
            addCriterion("terminal is not null");
            return (Criteria) this;
        }

        public Criteria andTerminalEqualTo(String value) {
            addCriterion("terminal =", value, "terminal");
            return (Criteria) this;
        }

        public Criteria andTerminalNotEqualTo(String value) {
            addCriterion("terminal <>", value, "terminal");
            return (Criteria) this;
        }

        public Criteria andTerminalGreaterThan(String value) {
            addCriterion("terminal >", value, "terminal");
            return (Criteria) this;
        }

        public Criteria andTerminalGreaterThanOrEqualTo(String value) {
            addCriterion("terminal >=", value, "terminal");
            return (Criteria) this;
        }

        public Criteria andTerminalLessThan(String value) {
            addCriterion("terminal <", value, "terminal");
            return (Criteria) this;
        }

        public Criteria andTerminalLessThanOrEqualTo(String value) {
            addCriterion("terminal <=", value, "terminal");
            return (Criteria) this;
        }

        public Criteria andTerminalLike(String value) {
            addCriterion("terminal like", value, "terminal");
            return (Criteria) this;
        }

        public Criteria andTerminalNotLike(String value) {
            addCriterion("terminal not like", value, "terminal");
            return (Criteria) this;
        }

        public Criteria andTerminalIn(List<String> values) {
            addCriterion("terminal in", values, "terminal");
            return (Criteria) this;
        }

        public Criteria andTerminalNotIn(List<String> values) {
            addCriterion("terminal not in", values, "terminal");
            return (Criteria) this;
        }

        public Criteria andTerminalBetween(String value1, String value2) {
            addCriterion("terminal between", value1, value2, "terminal");
            return (Criteria) this;
        }

        public Criteria andTerminalNotBetween(String value1, String value2) {
            addCriterion("terminal not between", value1, value2, "terminal");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeIsNull() {
            addCriterion("arrival_time is null");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeIsNotNull() {
            addCriterion("arrival_time is not null");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeEqualTo(String value) {
            addCriterion("arrival_time =", value, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeNotEqualTo(String value) {
            addCriterion("arrival_time <>", value, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeGreaterThan(String value) {
            addCriterion("arrival_time >", value, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeGreaterThanOrEqualTo(String value) {
            addCriterion("arrival_time >=", value, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeLessThan(String value) {
            addCriterion("arrival_time <", value, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeLessThanOrEqualTo(String value) {
            addCriterion("arrival_time <=", value, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeLike(String value) {
            addCriterion("arrival_time like", value, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeNotLike(String value) {
            addCriterion("arrival_time not like", value, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeIn(List<String> values) {
            addCriterion("arrival_time in", values, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeNotIn(List<String> values) {
            addCriterion("arrival_time not in", values, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeBetween(String value1, String value2) {
            addCriterion("arrival_time between", value1, value2, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeNotBetween(String value1, String value2) {
            addCriterion("arrival_time not between", value1, value2, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andVoyageIsNull() {
            addCriterion("voyage is null");
            return (Criteria) this;
        }

        public Criteria andVoyageIsNotNull() {
            addCriterion("voyage is not null");
            return (Criteria) this;
        }

        public Criteria andVoyageEqualTo(String value) {
            addCriterion("voyage =", value, "voyage");
            return (Criteria) this;
        }

        public Criteria andVoyageNotEqualTo(String value) {
            addCriterion("voyage <>", value, "voyage");
            return (Criteria) this;
        }

        public Criteria andVoyageGreaterThan(String value) {
            addCriterion("voyage >", value, "voyage");
            return (Criteria) this;
        }

        public Criteria andVoyageGreaterThanOrEqualTo(String value) {
            addCriterion("voyage >=", value, "voyage");
            return (Criteria) this;
        }

        public Criteria andVoyageLessThan(String value) {
            addCriterion("voyage <", value, "voyage");
            return (Criteria) this;
        }

        public Criteria andVoyageLessThanOrEqualTo(String value) {
            addCriterion("voyage <=", value, "voyage");
            return (Criteria) this;
        }

        public Criteria andVoyageLike(String value) {
            addCriterion("voyage like", value, "voyage");
            return (Criteria) this;
        }

        public Criteria andVoyageNotLike(String value) {
            addCriterion("voyage not like", value, "voyage");
            return (Criteria) this;
        }

        public Criteria andVoyageIn(List<String> values) {
            addCriterion("voyage in", values, "voyage");
            return (Criteria) this;
        }

        public Criteria andVoyageNotIn(List<String> values) {
            addCriterion("voyage not in", values, "voyage");
            return (Criteria) this;
        }

        public Criteria andVoyageBetween(String value1, String value2) {
            addCriterion("voyage between", value1, value2, "voyage");
            return (Criteria) this;
        }

        public Criteria andVoyageNotBetween(String value1, String value2) {
            addCriterion("voyage not between", value1, value2, "voyage");
            return (Criteria) this;
        }

        public Criteria andSailingTimeIsNull() {
            addCriterion("sailing_time is null");
            return (Criteria) this;
        }

        public Criteria andSailingTimeIsNotNull() {
            addCriterion("sailing_time is not null");
            return (Criteria) this;
        }

        public Criteria andSailingTimeEqualTo(String value) {
            addCriterion("sailing_time =", value, "sailingTime");
            return (Criteria) this;
        }

        public Criteria andSailingTimeNotEqualTo(String value) {
            addCriterion("sailing_time <>", value, "sailingTime");
            return (Criteria) this;
        }

        public Criteria andSailingTimeGreaterThan(String value) {
            addCriterion("sailing_time >", value, "sailingTime");
            return (Criteria) this;
        }

        public Criteria andSailingTimeGreaterThanOrEqualTo(String value) {
            addCriterion("sailing_time >=", value, "sailingTime");
            return (Criteria) this;
        }

        public Criteria andSailingTimeLessThan(String value) {
            addCriterion("sailing_time <", value, "sailingTime");
            return (Criteria) this;
        }

        public Criteria andSailingTimeLessThanOrEqualTo(String value) {
            addCriterion("sailing_time <=", value, "sailingTime");
            return (Criteria) this;
        }

        public Criteria andSailingTimeLike(String value) {
            addCriterion("sailing_time like", value, "sailingTime");
            return (Criteria) this;
        }

        public Criteria andSailingTimeNotLike(String value) {
            addCriterion("sailing_time not like", value, "sailingTime");
            return (Criteria) this;
        }

        public Criteria andSailingTimeIn(List<String> values) {
            addCriterion("sailing_time in", values, "sailingTime");
            return (Criteria) this;
        }

        public Criteria andSailingTimeNotIn(List<String> values) {
            addCriterion("sailing_time not in", values, "sailingTime");
            return (Criteria) this;
        }

        public Criteria andSailingTimeBetween(String value1, String value2) {
            addCriterion("sailing_time between", value1, value2, "sailingTime");
            return (Criteria) this;
        }

        public Criteria andSailingTimeNotBetween(String value1, String value2) {
            addCriterion("sailing_time not between", value1, value2, "sailingTime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vessel_info
     *
     * @mbg.generated do_not_delete_during_merge Tue Mar 21 14:01:10 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table vessel_info
     *
     * @mbg.generated Tue Mar 21 14:01:10 CST 2017
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}