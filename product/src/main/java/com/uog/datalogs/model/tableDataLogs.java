package com.uog.datalogs.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uog.login.model.LoginUser;
import com.fasterxml.jackson.core.JsonProcessingException;

public class tableDataLogs {
	private static final Logger log = LoggerFactory.getLogger(tableDataLogs.class);

	public static TableDataLog TableSaveDataLog(long tableRecordID, DatabaseTables databaseTableID, long logid,
			String tableData) {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/YYYY HH:mm:ss");
		Date date = new Date();

		TableDataLog tableDataLog = new TableDataLog();
		tableDataLog.setTABLELOG_DATE(dateFormat1.format(date));
		tableDataLog.setTABLERECORD_ID(tableRecordID);
		tableDataLog.setDATABASETABLE_ID(databaseTableID);
		tableDataLog.setTABLE_DATA(tableData);
		tableDataLog.setTABLELOG_BY(logid);
		tableDataLog.setISDELETED("N");

		return tableDataLog;
	}

	public static TableDataLog TableDeleteDataLog(long tableRecordID, DatabaseTables databaseTableID, long logid, String tableData) {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/YYYY HH:mm:ss");
		Date date = new Date();

		TableDataLog tableDataLog = new TableDataLog();
		tableDataLog.setTABLELOG_DATE(dateFormat1.format(date));
		tableDataLog.setTABLERECORD_ID(tableRecordID);
		tableDataLog.setDATABASETABLE_ID(databaseTableID);
		tableDataLog.setTABLE_DATA(tableData);
		tableDataLog.setTABLELOG_BY(logid);
		tableDataLog.setISDELETED("Y");

		return tableDataLog;
	}

	public static APIRequestDataLog errorDataLog(APIRequestDataLog apiRequest, String error, String message)
			throws JsonProcessingException {
		JSONObject obj = new JSONObject();
		obj.put("status", 401);
		obj.put("error", error);
		obj.put("message", message);
		obj.put("path", apiRequest.getREQUEST_PATH());

		apiRequest.setREQUEST_OUTPUT(obj.toString());
		apiRequest.setREQUEST_STATUS("Error");
		log.info("Output: " + apiRequest.getREQUEST_OUTPUT());
		log.info("--------------------------------------------------------");
		return apiRequest;
	}

	public static APIRequestDataLog apiRequestDataLog(String requestType, DatabaseTables databaseTableID,
			LoginUser loginUserID, String path, String input, String workstation) throws JsonProcessingException {
		APIRequestDataLog apiRequest = new APIRequestDataLog();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/YYYY HH:mm:ss");
		Date date = new Date();

		apiRequest.setLOG_DATE(dateFormat1.format(date));
		apiRequest.setDATABASETABLE_ID(databaseTableID);
		apiRequest.setUSER_ID(loginUserID);
		apiRequest.setREQUEST_TYPE(requestType);
		apiRequest.setREQUEST_PATH(path);
		apiRequest.setREQUEST_INPUT(input);
		apiRequest.setLOG_WORKSTATION(workstation);

		return apiRequest;
	}
}
