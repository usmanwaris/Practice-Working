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
import com.uog.product.model.Product;
import com.uog.product.model.Product;
import com.uog.product.repository.ProductRepository;
import com.uog.product.repository.productCategoryRepository;
import com.uog.token.AccessToken;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {
	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductRepository productrepository;
	
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
		log.info("GET: /product");
		String rtn=null, workstation=null;
		
//		List<Product> product = productrepository.findActive();
		List<Product> product = productrepository.findAll();
		
		LoginUser requestUser;
		String user_NAME = AccessToken.getTokenDetail(headToken);
		requestUser = loginuserrepository.getUser(user_NAME);
		
		DatabaseTables databaseTableID = databasetablesrepository.findOne(Product.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/product",
				null, workstation);
		
		rtn = mapper.writeValueAsString(product);

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
		log.info("GET: /product/all");
		String rtn=null, workstation=null;
		
		List<Product> product = productrepository.findAll();
		
		LoginUser requestUser;
		String user_NAME = AccessToken.getTokenDetail(headToken);
		requestUser = loginuserrepository.getUser(user_NAME);
		
		DatabaseTables databaseTableID = databasetablesrepository.findOne(Product.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/product/all",
				null, workstation);
		
		rtn = mapper.writeValueAsString(product);
		

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
		log.info("GET: /product/"+id);
		String rtn=null, workstation=null;
		
		Product product = productrepository.findOne(id);
		
		LoginUser requestUser;
		String user_NAME = AccessToken.getTokenDetail(headToken);
		requestUser = loginuserrepository.getUser(user_NAME);
		
		DatabaseTables databaseTableID = databasetablesrepository.findOne(Product.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/product/" + id,
				null, workstation);
		
		rtn = mapper.writeValueAsString(product);
		

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
		

		log.info("POST: /product");
		log.info("Input: " + data);

		JSONObject jsonObj = new JSONObject(data);
		Product product = new Product();
		String rtn, workstation = null;
		
		LoginUser requestUser;
		String user_NAME = AccessToken.getTokenDetail(headToken);
		requestUser = loginuserrepository.getUser(user_NAME);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Product.getDatabaseTableID());
		
		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, "/product", data,
				workstation);

		if (!jsonObj.has("product_NAME") && jsonObj.isNull("product_NAME")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Product Category", "Product Category Name is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}
		product.setPRODUCT_NAME(jsonObj.getString("product_NAME"));
		
		if (jsonObj.has("product_DESC"))
			product.setPRODUCT_DESC(jsonObj.getString("product_DESC"));

		
		product.setISACTIVE('Y');
		product.setMODIFIED_BY(requestUser.getUSER_ID());
		product.setMODIFIED_WORKSTATION(workstation);
		product.setMODIFIED_WHEN(dateFormat1.format(date));
		product = productrepository.saveAndFlush(product);
		rtn = mapper.writeValueAsString(product);

		tbldatalogrepository
				.saveAndFlush(tableDataLogs.TableSaveDataLog(product.getPRODUCT_ID(), databaseTableID, requestUser.getUSER_ID(), rtn));

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
		

		log.info("PUT: /product/" + id);
		log.info("Input: " + data);

		JSONObject jsonObj = new JSONObject(data);
		Product product = productrepository.findOne(id);
		String rtn, workstation = null;
		
		LoginUser requestUser;
		String user_NAME = AccessToken.getTokenDetail(headToken);
		requestUser = loginuserrepository.getUser(user_NAME);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Product.getDatabaseTableID());
		
		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/product/" + id, data,
				workstation);

		if (jsonObj.has("product_NAME"))
			product.setPRODUCT_NAME(jsonObj.getString("product_NAME"));
		
		if (jsonObj.has("product_DESC"))
			product.setPRODUCT_DESC(jsonObj.getString("product_DESC"));
		
		if (jsonObj.has("isactive"))
			product.setISACTIVE(jsonObj.getString("isactive").charAt(0));
		
		product.setMODIFIED_BY(requestUser.getUSER_ID());
		product.setMODIFIED_WORKSTATION(workstation);
		product.setMODIFIED_WHEN(dateFormat1.format(date));
		product = productrepository.saveAndFlush(product);
		rtn = mapper.writeValueAsString(product);

		tbldatalogrepository
				.saveAndFlush(tableDataLogs.TableSaveDataLog(product.getPRODUCT_ID(), databaseTableID, requestUser.getUSER_ID(), rtn));

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
		
		log.info("DELETE: /product/" + id);

		Product product = productrepository.findOne(id);
		String rtn, workstation = null;
		
		LoginUser requestUser;
		String user_NAME = AccessToken.getTokenDetail(headToken);
		requestUser = loginuserrepository.getUser(user_NAME);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Product.getDatabaseTableID());
		
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("DELETE", databaseTableID, requestUser, "/product/" + id, null,
				workstation);

		productrepository.delete(product);
		rtn = mapper.writeValueAsString(product);

		tbldatalogrepository
				.saveAndFlush(tableDataLogs.TableDeleteDataLog(product.getPRODUCT_ID(), databaseTableID, requestUser.getUSER_ID(), rtn));

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;

	}
}
