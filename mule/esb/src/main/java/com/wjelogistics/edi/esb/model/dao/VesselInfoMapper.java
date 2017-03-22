package com.wjelogistics.edi.esb.model.dao;

import com.wjelogistics.edi.esb.model.entity.VesselInfo;
import com.wjelogistics.edi.esb.model.entity.VesselInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VesselInfoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table vessel_info
	 * @mbg.generated  Wed Mar 22 11:33:53 CST 2017
	 */
	long countByExample(VesselInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table vessel_info
	 * @mbg.generated  Wed Mar 22 11:33:53 CST 2017
	 */
	int deleteByExample(VesselInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table vessel_info
	 * @mbg.generated  Wed Mar 22 11:33:53 CST 2017
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table vessel_info
	 * @mbg.generated  Wed Mar 22 11:33:53 CST 2017
	 */
	int insert(VesselInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table vessel_info
	 * @mbg.generated  Wed Mar 22 11:33:53 CST 2017
	 */
	int insertSelective(VesselInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table vessel_info
	 * @mbg.generated  Wed Mar 22 11:33:53 CST 2017
	 */
	List<VesselInfo> selectByExample(VesselInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table vessel_info
	 * @mbg.generated  Wed Mar 22 11:33:53 CST 2017
	 */
	VesselInfo selectByPrimaryKey(String id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table vessel_info
	 * @mbg.generated  Wed Mar 22 11:33:53 CST 2017
	 */
	int updateByExampleSelective(@Param("record") VesselInfo record, @Param("example") VesselInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table vessel_info
	 * @mbg.generated  Wed Mar 22 11:33:53 CST 2017
	 */
	int updateByExample(@Param("record") VesselInfo record, @Param("example") VesselInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table vessel_info
	 * @mbg.generated  Wed Mar 22 11:33:53 CST 2017
	 */
	int updateByPrimaryKeySelective(VesselInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table vessel_info
	 * @mbg.generated  Wed Mar 22 11:33:53 CST 2017
	 */
	int updateByPrimaryKey(VesselInfo record);
}