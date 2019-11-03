package com.uog.product.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uog.datalogs.model.APIRequestDataLog;
import com.uog.datalogs.model.DatabaseTables;
import com.uog.datalogs.model.tableDataLogs;
import com.uog.datalogs.repository.apiRequestDataLogRepository;
import com.uog.datalogs.repository.databaseTablesRepository;
import com.uog.datalogs.repository.tableDataLogRepository;
import com.uog.login.model.LoginUser;
import com.uog.login.repository.loginUserRepository;
import com.uog.product.model.ProductCategory;
import com.uog.product.repository.productCategoryRepository;
import com.uog.token.AccessToken;

@RestController
@CrossOrigin
@RequestMapping("/productcategory")
public class productCategoryController {
	private static final Logger log = LoggerFactory.getLogger(productCategoryController.class);

	@Autowired
	private productCategoryRepository productcategoryrepository;
	

	@Autowired
	private loginUserRepository loginuserrepository;
	
	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;
	
	@Autowired
	private tableDataLogRepository tbldatalogrepository;
	
	@Autowired
	private databaseTablesRepository databasetablesrepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public String get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		log.info("GET: /productcategory");
		String rtn=null, workstation=null;
		
		List<ProductCategory> productcategory = productcategoryrepository.findActive();
		
		LoginUser requestUser;
		String user_NAME = AccessToken.getTokenDetail(headToken);
		requestUser = loginuserrepository.getUser(user_NAME);
		
		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductCategory.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/productcategory",
				null, workstation);
		
		rtn = mapper.writeValueAsString(productcategory);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}
	
	@RequestMapping(value="/all" ,method = RequestMethod.GET)
	public String getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		log.info("GET: /productcategory/all");
		String rtn=null, workstation=null;
		
		List<ProductCategory> productcategory = productcategoryrepository.findAll();
		
		LoginUser requestUser;
		String user_NAME = AccessToken.getTokenDetail(headToken);
		requestUser = loginuserrepository.getUser(user_NAME);
		
		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductCategory.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/productcategory/all",
				null, workstation);
		
		rtn = mapper.writeValueAsString(productcategory);
		

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}
	
	@RequestMapping(value="/{id}" ,method = RequestMethod.GET)
	public String getOne(@PathVariable Long id ,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		log.info("GET: /productcategory/"+id);
		String rtn=null, workstation=null;
		
		ProductCategory productcategory = productcategoryrepository.findOne(id);
		
		LoginUser requestUser;
		String user_NAME = AccessToken.getTokenDetail(headToken);
		requestUser = loginuserrepository.getUser(user_NAME);
		
		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductCategory.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/productcategory/" + id,
				null, workstation);
		
		rtn = mapper.writeValueAsString(productcategory);
		

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}
	@RequestMapping(method = RequestMethod.POST)
	public String insert(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/YYYY HH:mm:ss");
		Date date = new Date();
		ObjectMapper mapper = new ObjectMapper();
		

		log.info("POST: /productcategory");
		log.info("Input: " + data);

		JSONObject jsonObj = new JSONObject(data);
		ProductCategory productcategory = new ProductCategory();
		String rtn, workstation = null;
		
		LoginUser requestUser;
		String user_NAME = AccessToken.getTokenDetail(headToken);
		requestUser = loginuserrepository.getUser(user_NAME);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductCategory.getDatabaseTableID());
		
		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, "/productcategory", data,
				workstation);

		if (!jsonObj.has("productcategory_NAME") && jsonObj.isNull("productcategory_NAME")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Product Category", "Product Category Name is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}
		productcategory.setPRODUCTCATEGORY_NAME(jsonObj.getString("productcategory_NAME"));
		
		if (jsonObj.has("productcategory_DESC"))
			productcategory.setPRODUCTCATEGORY_DESC(jsonObj.getString("productcategory_DESC"));

		
		productcategory.setISACTIVE('Y');
		productcategory.setMODIFIED_BY(requestUser.getUSER_ID());
		productcategory.setMODIFIED_WORKSTATION(workstation);
		productcategory.setMODIFIED_WHEN(dateFormat1.format(date));
		productcategory = productcategoryrepository.saveAndFlush(productcategory);
		rtn = mapper.writeValueAsString(productcategory);

		tbldatalogrepository
				.saveAndFlush(tableDataLogs.TableSaveDataLog(productcategory.getPRODUCTCATEGORY_ID(), databaseTableID, requestUser.getUSER_ID(), rtn));

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;

	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable Long id, @RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/YYYY HH:mm:ss");
		Date date = new Date();
		ObjectMapper mapper = new ObjectMapper();
		

		log.info("PUT: /productcategory/" + id);
		log.info("Input: " + data);

		JSONObject jsonObj = new JSONObject(data);
		ProductCategory productcategory = productcategoryrepository.findOne(id);
		String rtn, workstation = null;
		
		LoginUser requestUser;
		String user_NAME = AccessToken.getTokenDetail(headToken);
		requestUser = loginuserrepository.getUser(user_NAME);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductCategory.getDatabaseTableID());
		
		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/productcategory/" + id, data,
				workstation);

		if (jsonObj.has("productcategory_NAME"))
			productcategory.setPRODUCTCATEGORY_NAME(jsonObj.getString("productcategory_NAME"));
		
		if (jsonObj.has("productcategory_DESC"))
			productcategory.setPRODUCTCATEGORY_DESC(jsonObj.getString("productcategory_DESC"));
		
		if (jsonObj.has("isactive"))
			productcategory.setISACTIVE(jsonObj.getString("isactive").charAt(0));
		
		productcategory.setMODIFIED_BY(requestUser.getUSER_ID());
		productcategory.setMODIFIED_WORKSTATION(workstation);
		productcategory.setMODIFIED_WHEN(dateFormat1.format(date));
		productcategory = productcategoryrepository.saveAndFlush(productcategory);
		rtn = mapper.writeValueAsString(productcategory);

		tbldatalogrepository
				.saveAndFlush(tableDataLogs.TableSaveDataLog(productcategory.getPRODUCTCATEGORY_ID(), databaseTableID, requestUser.getUSER_ID(), rtn));

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;

	}
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		
		log.info("DELETE: /productcategory/" + id);

		ProductCategory productcategory = productcategoryrepository.findOne(id);
		String rtn, workstation = null;
		
		LoginUser requestUser;
		String user_NAME = AccessToken.getTokenDetail(headToken);
		requestUser = loginuserrepository.getUser(user_NAME);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(ProductCategory.getDatabaseTableID());
		
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("DELETE", databaseTableID, requestUser, "/productcategory/" + id, null,
				workstation);

		productcategoryrepository.delete(productcategory);
		rtn = mapper.writeValueAsString(productcategory);

		tbldatalogrepository
				.saveAndFlush(tableDataLogs.TableDeleteDataLog(productcategory.getPRODUCTCATEGORY_ID(), databaseTableID, requestUser.getUSER_ID(), rtn));

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;

	}
}
