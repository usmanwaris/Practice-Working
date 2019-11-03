package com.uog.datalogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uog.datalogs.model.APIRequestDataLog;

public interface apiRequestDataLogRepository extends JpaRepository<APIRequestDataLog,Long>{

}
