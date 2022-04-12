--
-- PostgreSQL database dump
--

-- Dumped from database version 13.2 (Debian 13.2-1.pgdg100+1)
-- Dumped by pg_dump version 13.2

-- Started on 2022-04-08 17:15:07

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 16386)
-- Name: data_publish; Type: SCHEMA; Schema: -; Owner: smartcity_data
--

CREATE SCHEMA data_publish;



-- DROP ROLE smartcity_data;

CREATE ROLE smartcity_data WITH 
	SUPERUSER
	NOCREATEDB
	NOCREATEROLE
	NOINHERIT
	LOGIN
	NOREPLICATION
	NOBYPASSRLS
	CONNECTION LIMIT -1;
	
ALTER ROLE smartcity_data WITH PASSWORD 'dpsxndpa';	
	

ALTER SCHEMA data_publish OWNER TO smartcity_data;

--
-- TOC entry 3159 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA data_publish; Type: COMMENT; Schema: -; Owner: smartcity_data
--

COMMENT ON SCHEMA data_publish IS 'data_publish';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 201 (class 1259 OID 16387)
-- Name: dh_adaptor; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_adaptor (
    ad_id bigint NOT NULL,
    ad_status character varying,
    ad_create_user_id character varying,
    ad_transfer_type character varying,
    ad_transfer_description character varying,
    ad_created timestamp without time zone,
    ad_model_id character varying,
    ad_title character varying,
    ad_content character varying,
    ad_modified timestamp without time zone
);


ALTER TABLE data_publish.dh_adaptor OWNER TO postgres;

--
-- TOC entry 3160 (class 0 OID 0)
-- Dependencies: 201
-- Name: TABLE dh_adaptor; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_adaptor IS '어댑터 신청 내역';


--
-- TOC entry 3161 (class 0 OID 0)
-- Dependencies: 201
-- Name: COLUMN dh_adaptor.ad_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_adaptor.ad_id IS '어댑터신청고유번호';


--
-- TOC entry 3162 (class 0 OID 0)
-- Dependencies: 201
-- Name: COLUMN dh_adaptor.ad_status; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_adaptor.ad_status IS '어댑터신청상태';


--
-- TOC entry 3163 (class 0 OID 0)
-- Dependencies: 201
-- Name: COLUMN dh_adaptor.ad_create_user_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_adaptor.ad_create_user_id IS '등록자';


--
-- TOC entry 3164 (class 0 OID 0)
-- Dependencies: 201
-- Name: COLUMN dh_adaptor.ad_transfer_type; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_adaptor.ad_transfer_type IS '전송방식';


--
-- TOC entry 3165 (class 0 OID 0)
-- Dependencies: 201
-- Name: COLUMN dh_adaptor.ad_transfer_description; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_adaptor.ad_transfer_description IS '전송방식설명';


--
-- TOC entry 3166 (class 0 OID 0)
-- Dependencies: 201
-- Name: COLUMN dh_adaptor.ad_created; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_adaptor.ad_created IS '등록일시';


--
-- TOC entry 3167 (class 0 OID 0)
-- Dependencies: 201
-- Name: COLUMN dh_adaptor.ad_model_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_adaptor.ad_model_id IS '데이터모델ID';


--
-- TOC entry 3168 (class 0 OID 0)
-- Dependencies: 201
-- Name: COLUMN dh_adaptor.ad_title; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_adaptor.ad_title IS '어댑터신청제목';


--
-- TOC entry 3169 (class 0 OID 0)
-- Dependencies: 201
-- Name: COLUMN dh_adaptor.ad_content; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_adaptor.ad_content IS '어댑터신청내용';


--
-- TOC entry 3170 (class 0 OID 0)
-- Dependencies: 201
-- Name: COLUMN dh_adaptor.ad_modified; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_adaptor.ad_modified IS '수정일시';


--
-- TOC entry 202 (class 1259 OID 16393)
-- Name: dh_adaptor_field; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_adaptor_field (
    field_id bigint,
    ad_id bigint,
    field_kr_name character varying,
    field_en_name character varying,
    field_data_type character varying,
    field_description character varying,
    field_sequence bigint
);


ALTER TABLE data_publish.dh_adaptor_field OWNER TO postgres;

--
-- TOC entry 3172 (class 0 OID 0)
-- Dependencies: 202
-- Name: TABLE dh_adaptor_field; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_adaptor_field IS '어댑터 신청내역 필드정보';


--
-- TOC entry 3173 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN dh_adaptor_field.field_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_adaptor_field.field_id IS '어댑터 필드 ID';


--
-- TOC entry 3174 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN dh_adaptor_field.ad_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_adaptor_field.ad_id IS '어댑터신청고유번호';


--
-- TOC entry 3175 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN dh_adaptor_field.field_kr_name; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_adaptor_field.field_kr_name IS '필드명(한글)';


--
-- TOC entry 3176 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN dh_adaptor_field.field_en_name; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_adaptor_field.field_en_name IS '필드명(영문)';


--
-- TOC entry 3177 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN dh_adaptor_field.field_data_type; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_adaptor_field.field_data_type IS '필드데이터유형';


--
-- TOC entry 3178 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN dh_adaptor_field.field_description; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_adaptor_field.field_description IS '필드설명';


--
-- TOC entry 3179 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN dh_adaptor_field.field_sequence; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_adaptor_field.field_sequence IS '필드순서';


--
-- TOC entry 203 (class 1259 OID 16399)
-- Name: dh_data_analyst_model_info; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_data_analyst_model_info (
    ar_model_oid bigint NOT NULL,
    ds_model_id character varying(40),
    ami_oid bigint NOT NULL
);


ALTER TABLE data_publish.dh_data_analyst_model_info OWNER TO postgres;

--
-- TOC entry 3181 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN dh_data_analyst_model_info.ar_model_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_data_analyst_model_info.ar_model_oid IS '데이터분석 고유번호';


--
-- TOC entry 3182 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN dh_data_analyst_model_info.ds_model_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_data_analyst_model_info.ds_model_id IS '데이터모델ID';


--
-- TOC entry 3183 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN dh_data_analyst_model_info.ami_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_data_analyst_model_info.ami_oid IS '데이터분석 모델고유번호';


--
-- TOC entry 204 (class 1259 OID 16402)
-- Name: dh_datamodel_analyst_request; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_datamodel_analyst_request (
    ar_model_oid bigint NOT NULL,
    ar_req_status character varying(32),
    ar_user_id character varying(32),
    ar_created timestamp without time zone,
    ar_manager_id character varying(32),
    ar_updated timestamp without time zone,
    ar_reason_apply character varying(500)
);


ALTER TABLE data_publish.dh_datamodel_analyst_request OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 16408)
-- Name: dh_dataset; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_dataset (
    ds_oid character varying(20) NOT NULL,
    ds_title character varying(60) NOT NULL,
    ds_status character varying(32) NOT NULL,
    ds_category_oid bigint NOT NULL,
    ds_desc character varying(4000),
    ds_provider_id character varying(32) NOT NULL,
    ds_create_user_id character varying(32) NOT NULL,
    ds_created timestamp without time zone NOT NULL,
    ds_update_user_id character varying(32),
    ds_updated timestamp without time zone,
    ds_update_cycle character varying(100),
    ds_provider character varying(100),
    ds_provide_system character varying(100),
    ds_offer_type character varying(32),
    ds_keyword character varying(1000),
    ds_license character varying(1000),
    ds_constraints character varying(1000),
    ds_extension character varying(32),
    ds_data_type character varying(32),
    ds_ownership character varying(50),
    ds_hits bigint,
    ds_search_type character varying(32),
    ds_model_type character varying(40),
    ds_model_version character varying(20),
    ds_model_namespace character varying(100)
);


ALTER TABLE data_publish.dh_dataset OWNER TO postgres;

--
-- TOC entry 3186 (class 0 OID 0)
-- Dependencies: 205
-- Name: TABLE dh_dataset; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_dataset IS '데이터셋 기본';


--
-- TOC entry 3187 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_oid IS '데이터셋 고유번호';


--
-- TOC entry 3188 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_title; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_title IS '데이터셋 제목';


--
-- TOC entry 3189 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_status; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_status IS '데이터셋 상태코드';


--
-- TOC entry 3190 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_category_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_category_oid IS '카테고리고유번호';


--
-- TOC entry 3191 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_desc; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_desc IS '데이터셋 설명';


--
-- TOC entry 3192 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_provider_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_provider_id IS '데이터소유자id';


--
-- TOC entry 3193 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_create_user_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_create_user_id IS '등록자id';


--
-- TOC entry 3194 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_created; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_created IS '등록일시';


--
-- TOC entry 3195 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_update_user_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_update_user_id IS '수정자id';


--
-- TOC entry 3196 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_updated; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_updated IS '수정일시';


--
-- TOC entry 3197 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_update_cycle; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_update_cycle IS '갱신주기';


--
-- TOC entry 3198 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_provider; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_provider IS '제공기관';


--
-- TOC entry 3199 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_provide_system; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_provide_system IS '제공시스템';


--
-- TOC entry 3200 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_offer_type; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_offer_type IS '제공유형';


--
-- TOC entry 3201 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_keyword; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_keyword IS '키워드';


--
-- TOC entry 3202 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_license; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_license IS '라이선스';


--
-- TOC entry 3203 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_constraints; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_constraints IS '제약사항';


--
-- TOC entry 3204 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_extension; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_extension IS '확장자';


--
-- TOC entry 3205 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_data_type; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_data_type IS '데이터유형';


--
-- TOC entry 3206 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_ownership; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_ownership IS '소유권';


--
-- TOC entry 3207 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_hits; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_hits IS '조회수';


--
-- TOC entry 3208 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_search_type; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_search_type IS '조회유형';


--
-- TOC entry 3209 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_model_type; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_model_type IS '모델 타입';


--
-- TOC entry 3210 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_model_version; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_model_version IS '모델 버전';


--
-- TOC entry 3211 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN dh_dataset.ds_model_namespace; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset.ds_model_namespace IS '모델 네임스페이스';


--
-- TOC entry 206 (class 1259 OID 16414)
-- Name: dh_dataset_approval_hist; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_dataset_approval_hist (
    apphist_id bigint NOT NULL,
    degree integer DEFAULT 1 NOT NULL,
    ds_status character varying(32) NOT NULL,
    ds_oid character varying(20) NOT NULL,
    assignee_id character varying(32) NOT NULL,
    apphist_created timestamp without time zone
);


ALTER TABLE data_publish.dh_dataset_approval_hist OWNER TO postgres;

--
-- TOC entry 3213 (class 0 OID 0)
-- Dependencies: 206
-- Name: TABLE dh_dataset_approval_hist; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_dataset_approval_hist IS '데이터셋 승인 이력';


--
-- TOC entry 3214 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN dh_dataset_approval_hist.apphist_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_approval_hist.apphist_id IS '승인이력 고유번호';


--
-- TOC entry 3215 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN dh_dataset_approval_hist.degree; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_approval_hist.degree IS '승인 차수';


--
-- TOC entry 3216 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN dh_dataset_approval_hist.ds_status; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_approval_hist.ds_status IS '데이터셋 다음 상태 (승인,거절)';


--
-- TOC entry 3217 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN dh_dataset_approval_hist.ds_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_approval_hist.ds_oid IS '데이터셋 고유번호';


--
-- TOC entry 3218 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN dh_dataset_approval_hist.assignee_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_approval_hist.assignee_id IS '승인처리자ID';


--
-- TOC entry 3219 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN dh_dataset_approval_hist.apphist_created; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_approval_hist.apphist_created IS '등록일시';


--
-- TOC entry 207 (class 1259 OID 16418)
-- Name: dh_dataset_file; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_dataset_file (
    ds_file_oid bigint NOT NULL,
    ds_oid character varying(20) NOT NULL,
    ds_file_flag character varying(32) NOT NULL,
    ds_file_psc_name character varying(128) NOT NULL,
    ds_file_lsc_name character varying(128) NOT NULL,
    ds_file_path character varying(128) NOT NULL,
    ds_file_size bigint NOT NULL
);


ALTER TABLE data_publish.dh_dataset_file OWNER TO postgres;

--
-- TOC entry 3221 (class 0 OID 0)
-- Dependencies: 207
-- Name: TABLE dh_dataset_file; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_dataset_file IS '데이터셋 첨부파일';


--
-- TOC entry 3222 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN dh_dataset_file.ds_file_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_file.ds_file_oid IS '첨부파일 고유번호';


--
-- TOC entry 3223 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN dh_dataset_file.ds_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_file.ds_oid IS '데이터셋 고유번호';


--
-- TOC entry 3224 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN dh_dataset_file.ds_file_flag; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_file.ds_file_flag IS '파일구분코드';


--
-- TOC entry 3225 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN dh_dataset_file.ds_file_psc_name; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_file.ds_file_psc_name IS '물리파일명';


--
-- TOC entry 3226 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN dh_dataset_file.ds_file_lsc_name; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_file.ds_file_lsc_name IS '논리파일명';


--
-- TOC entry 3227 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN dh_dataset_file.ds_file_path; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_file.ds_file_path IS '파일경로';


--
-- TOC entry 3228 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN dh_dataset_file.ds_file_size; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_file.ds_file_size IS '파일크기';


--
-- TOC entry 208 (class 1259 OID 16421)
-- Name: dh_dataset_inquiry; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_dataset_inquiry (
    inq_oid bigint NOT NULL,
    ds_oid character varying(20) NOT NULL,
    inq_question_content character varying(4000),
    inq_answer_content character varying(4000),
    inq_secret boolean,
    inq_status character varying(32),
    inq_create_user_id character varying(32),
    inq_created timestamp without time zone,
    inq_answer_user_id character varying(32),
    inq_answer_date timestamp without time zone
);


ALTER TABLE data_publish.dh_dataset_inquiry OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16427)
-- Name: dh_dataset_instance_info; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_dataset_instance_info (
    ds_oid character varying(20) NOT NULL,
    dii_id bigint NOT NULL,
    instance_id character varying(100) NOT NULL
);


ALTER TABLE data_publish.dh_dataset_instance_info OWNER TO postgres;

--
-- TOC entry 3231 (class 0 OID 0)
-- Dependencies: 209
-- Name: TABLE dh_dataset_instance_info; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_dataset_instance_info IS '데이터셋 인스턴스연결정보';


--
-- TOC entry 3232 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN dh_dataset_instance_info.ds_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_instance_info.ds_oid IS '데이터셋 고유번호';


--
-- TOC entry 3233 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN dh_dataset_instance_info.dii_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_instance_info.dii_id IS '데이터인스턴스ID';


--
-- TOC entry 3234 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN dh_dataset_instance_info.instance_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_instance_info.instance_id IS '데이터인스턴스ID';


--
-- TOC entry 210 (class 1259 OID 16430)
-- Name: dh_dataset_interest; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_dataset_interest (
    ds_oid character varying(20) NOT NULL,
    user_id character varying(32) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    itrt_id bigint NOT NULL
);


ALTER TABLE data_publish.dh_dataset_interest OWNER TO postgres;

--
-- TOC entry 3236 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN dh_dataset_interest.itrt_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_interest.itrt_id IS '관심상품 고유번호';


--
-- TOC entry 211 (class 1259 OID 16433)
-- Name: dh_dataset_interest_log; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_dataset_interest_log (
    itrt_id bigint NOT NULL,
    user_id character varying(32) NOT NULL,
    ds_oid character varying(20) NOT NULL,
    cancel_at timestamp without time zone NOT NULL
);


ALTER TABLE data_publish.dh_dataset_interest_log OWNER TO postgres;

--
-- TOC entry 3238 (class 0 OID 0)
-- Dependencies: 211
-- Name: TABLE dh_dataset_interest_log; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_dataset_interest_log IS '데이터셋 관심상품 해지 현황 내역';


--
-- TOC entry 3239 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN dh_dataset_interest_log.itrt_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_interest_log.itrt_id IS '관심상품 고유번호';


--
-- TOC entry 3240 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN dh_dataset_interest_log.user_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_interest_log.user_id IS '신청자id';


--
-- TOC entry 3241 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN dh_dataset_interest_log.ds_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_interest_log.ds_oid IS '데이터셋 고유번호';


--
-- TOC entry 3242 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN dh_dataset_interest_log.cancel_at; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_interest_log.cancel_at IS '해지일시';


--
-- TOC entry 212 (class 1259 OID 16436)
-- Name: dh_dataset_model; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_dataset_model (
    model_id bigint NOT NULL,
    model_type character varying(40) NOT NULL,
    model_namespace character varying(100) NOT NULL,
    model_version character varying(20) NOT NULL,
    ds_oid character varying(20) NOT NULL
);


ALTER TABLE data_publish.dh_dataset_model OWNER TO postgres;

--
-- TOC entry 3244 (class 0 OID 0)
-- Dependencies: 212
-- Name: TABLE dh_dataset_model; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_dataset_model IS '데이터셋 모델연결정보';


--
-- TOC entry 3245 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN dh_dataset_model.model_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_model.model_id IS '모델ID';


--
-- TOC entry 3246 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN dh_dataset_model.model_type; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_model.model_type IS '모델 타입';


--
-- TOC entry 3247 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN dh_dataset_model.model_namespace; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_model.model_namespace IS '모델 네임스페이스';


--
-- TOC entry 3248 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN dh_dataset_model.model_version; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_model.model_version IS '모델 버전';


--
-- TOC entry 3249 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN dh_dataset_model.ds_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_model.ds_oid IS '데이터셋 고유번호';


--
-- TOC entry 213 (class 1259 OID 16439)
-- Name: dh_dataset_model_ds; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_dataset_model_ds (
    dmd_id bigint NOT NULL,
    ds_origin_id character varying(30) NOT NULL,
    ds_oid character varying(20) NOT NULL
);


ALTER TABLE data_publish.dh_dataset_model_ds OWNER TO postgres;

--
-- TOC entry 3251 (class 0 OID 0)
-- Dependencies: 213
-- Name: TABLE dh_dataset_model_ds; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_dataset_model_ds IS '데이터셋_Origin연결정보';


--
-- TOC entry 3252 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN dh_dataset_model_ds.dmd_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_model_ds.dmd_id IS '연결정보 고유번호';


--
-- TOC entry 3253 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN dh_dataset_model_ds.ds_origin_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_model_ds.ds_origin_id IS '데이터셋 Origin ID';


--
-- TOC entry 3254 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN dh_dataset_model_ds.ds_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_model_ds.ds_oid IS '데이터셋 고유번호';


--
-- TOC entry 214 (class 1259 OID 16442)
-- Name: dh_dataset_output_info; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_dataset_output_info (
    ds_oid character varying(20) NOT NULL,
    real_col_name character varying(32) NOT NULL,
    alias_col_name character varying(32),
    output_desc character varying(4000),
    open boolean NOT NULL,
    output_oid bigint NOT NULL
);


ALTER TABLE data_publish.dh_dataset_output_info OWNER TO postgres;

--
-- TOC entry 3256 (class 0 OID 0)
-- Dependencies: 214
-- Name: TABLE dh_dataset_output_info; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_dataset_output_info IS '데이터셋 출력정보 상세';


--
-- TOC entry 3257 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN dh_dataset_output_info.ds_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_output_info.ds_oid IS '데이터셋 고유번호';


--
-- TOC entry 3258 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN dh_dataset_output_info.real_col_name; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_output_info.real_col_name IS '원컬럼명';


--
-- TOC entry 3259 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN dh_dataset_output_info.alias_col_name; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_output_info.alias_col_name IS '별칭컬럼명';


--
-- TOC entry 3260 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN dh_dataset_output_info.output_desc; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_output_info.output_desc IS '설명';


--
-- TOC entry 3261 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN dh_dataset_output_info.open; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_output_info.open IS '공개여부';


--
-- TOC entry 3262 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN dh_dataset_output_info.output_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_output_info.output_oid IS '출력정보 고유번호';


--
-- TOC entry 215 (class 1259 OID 16448)
-- Name: dh_dataset_period_price; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_dataset_period_price (
    ds_period_id bigint NOT NULL,
    ds_price_id bigint NOT NULL,
    period_cd character varying(35) NOT NULL,
    price integer NOT NULL
);


ALTER TABLE data_publish.dh_dataset_period_price OWNER TO postgres;

--
-- TOC entry 3264 (class 0 OID 0)
-- Dependencies: 215
-- Name: TABLE dh_dataset_period_price; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_dataset_period_price IS '데이터셋 가격정책 금액정보';


--
-- TOC entry 3265 (class 0 OID 0)
-- Dependencies: 215
-- Name: COLUMN dh_dataset_period_price.ds_period_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_period_price.ds_period_id IS '가격정책금액 id';


--
-- TOC entry 3266 (class 0 OID 0)
-- Dependencies: 215
-- Name: COLUMN dh_dataset_period_price.ds_price_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_period_price.ds_price_id IS '요금연결정보 id';


--
-- TOC entry 3267 (class 0 OID 0)
-- Dependencies: 215
-- Name: COLUMN dh_dataset_period_price.period_cd; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_period_price.period_cd IS '제공기간 코드';


--
-- TOC entry 3268 (class 0 OID 0)
-- Dependencies: 215
-- Name: COLUMN dh_dataset_period_price.price; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_period_price.price IS '금액';


--
-- TOC entry 216 (class 1259 OID 16451)
-- Name: dh_dataset_price; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_dataset_price (
    ds_price_id bigint NOT NULL,
    title character varying(100) NOT NULL,
    traffic_type character varying(35) NOT NULL,
    daily_limit integer,
    ds_oid character varying(20) NOT NULL
);


ALTER TABLE data_publish.dh_dataset_price OWNER TO postgres;

--
-- TOC entry 3270 (class 0 OID 0)
-- Dependencies: 216
-- Name: TABLE dh_dataset_price; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_dataset_price IS '데이터셋 가격정책 연결정보';


--
-- TOC entry 3271 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN dh_dataset_price.ds_price_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_price.ds_price_id IS '요금연결정보 id';


--
-- TOC entry 3272 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN dh_dataset_price.title; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_price.title IS '제목';


--
-- TOC entry 3273 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN dh_dataset_price.traffic_type; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_price.traffic_type IS '트래픽 유형';


--
-- TOC entry 3274 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN dh_dataset_price.daily_limit; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_price.daily_limit IS '일일제한량';


--
-- TOC entry 3275 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN dh_dataset_price.ds_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_price.ds_oid IS '데이터셋 고유번호';


--
-- TOC entry 217 (class 1259 OID 16454)
-- Name: dh_dataset_satisfaction_rating; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_dataset_satisfaction_rating (
    ds_oid character varying(20) NOT NULL,
    dsr_user_id character varying(32) NOT NULL,
    dsr_score double precision,
    dsr_cre_dt timestamp without time zone,
    dsr_review character varying(2000),
    dsr_oid bigint NOT NULL
);


ALTER TABLE data_publish.dh_dataset_satisfaction_rating OWNER TO postgres;

--
-- TOC entry 3277 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN dh_dataset_satisfaction_rating.ds_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_satisfaction_rating.ds_oid IS '데이터셋 고유번호';


--
-- TOC entry 3278 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN dh_dataset_satisfaction_rating.dsr_user_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_satisfaction_rating.dsr_user_id IS '평가자ID';


--
-- TOC entry 3279 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN dh_dataset_satisfaction_rating.dsr_score; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_satisfaction_rating.dsr_score IS '평가점수';


--
-- TOC entry 3280 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN dh_dataset_satisfaction_rating.dsr_cre_dt; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_satisfaction_rating.dsr_cre_dt IS '등록일시';


--
-- TOC entry 3281 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN dh_dataset_satisfaction_rating.dsr_review; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_satisfaction_rating.dsr_review IS '평가후기';


--
-- TOC entry 3282 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN dh_dataset_satisfaction_rating.dsr_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_satisfaction_rating.dsr_oid IS '만족도평가 고유번호';


--
-- TOC entry 218 (class 1259 OID 16460)
-- Name: dh_dataset_search_inf_tb; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_dataset_search_inf_tb (
    ds_oid character varying(20) NOT NULL,
    si_main_attr character varying(100) NOT NULL,
    si_sub_attr character varying(100),
    si_condition_cd character varying(32),
    si_search_value character varying(100),
    si_oid bigint NOT NULL
);


ALTER TABLE data_publish.dh_dataset_search_inf_tb OWNER TO postgres;

--
-- TOC entry 3284 (class 0 OID 0)
-- Dependencies: 218
-- Name: TABLE dh_dataset_search_inf_tb; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_dataset_search_inf_tb IS '데이터셋 조회조건기본';


--
-- TOC entry 3285 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN dh_dataset_search_inf_tb.ds_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_search_inf_tb.ds_oid IS '데이터셋 고유번호';


--
-- TOC entry 3286 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN dh_dataset_search_inf_tb.si_main_attr; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_search_inf_tb.si_main_attr IS '메인키';


--
-- TOC entry 3287 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN dh_dataset_search_inf_tb.si_sub_attr; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_search_inf_tb.si_sub_attr IS '서브키';


--
-- TOC entry 3288 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN dh_dataset_search_inf_tb.si_condition_cd; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_search_inf_tb.si_condition_cd IS '연산기호';


--
-- TOC entry 3289 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN dh_dataset_search_inf_tb.si_search_value; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_search_inf_tb.si_search_value IS '검색값';


--
-- TOC entry 3290 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN dh_dataset_search_inf_tb.si_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_search_inf_tb.si_oid IS '조회조건 고유번호';


--
-- TOC entry 231 (class 1259 OID 16579)
-- Name: dh_dataset_subscription; Type: TABLE; Schema: data_publish; Owner: smartcity_data
--

CREATE TABLE data_publish.dh_dataset_subscription (
    subscription_id character varying(50) NOT NULL,
    ds_oid character varying(20) NOT NULL
);


ALTER TABLE data_publish.dh_dataset_subscription OWNER TO smartcity_data;

--
-- TOC entry 219 (class 1259 OID 16463)
-- Name: dh_dataset_userequest; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_dataset_userequest (
    ds_oid character varying(20) NOT NULL,
    req_user_id character varying(32) NOT NULL,
    push_uri character varying(100),
    req_created timestamp without time zone NOT NULL,
    req_oid bigint NOT NULL
);


ALTER TABLE data_publish.dh_dataset_userequest OWNER TO postgres;

--
-- TOC entry 3292 (class 0 OID 0)
-- Dependencies: 219
-- Name: TABLE dh_dataset_userequest; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_dataset_userequest IS '데이터셋 이용신청내역';


--
-- TOC entry 3293 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN dh_dataset_userequest.ds_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest.ds_oid IS '데이터셋 고유번호';


--
-- TOC entry 3294 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN dh_dataset_userequest.req_user_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest.req_user_id IS '신청자id';


--
-- TOC entry 3295 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN dh_dataset_userequest.push_uri; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest.push_uri IS 'push uri주소';


--
-- TOC entry 3296 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN dh_dataset_userequest.req_created; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest.req_created IS '등록일시';


--
-- TOC entry 3297 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN dh_dataset_userequest.req_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest.req_oid IS '이용신청 고유번호';


--
-- TOC entry 220 (class 1259 OID 16466)
-- Name: dh_dataset_userequest_log; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_dataset_userequest_log (
    req_id bigint NOT NULL,
    ds_oid character varying(20) NOT NULL,
    req_user_id character varying(32) NOT NULL,
    req_realtime_tf boolean NOT NULL,
    protocol character varying(32) NOT NULL,
    url character varying(100) NOT NULL,
    traffic_type character varying(35) NOT NULL,
    daily_limit integer,
    expired_at timestamp without time zone,
    price integer NOT NULL,
    pay_status character varying(35) NOT NULL,
    completed_at timestamp without time zone,
    status character varying(32) NOT NULL,
    reason character varying(100),
    update_at timestamp without time zone,
    cancel_at timestamp without time zone NOT NULL
);


ALTER TABLE data_publish.dh_dataset_userequest_log OWNER TO postgres;

--
-- TOC entry 3299 (class 0 OID 0)
-- Dependencies: 220
-- Name: TABLE dh_dataset_userequest_log; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_dataset_userequest_log IS '데이터셋 이용신청 해지 현황 내역';


--
-- TOC entry 3300 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN dh_dataset_userequest_log.req_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_log.req_id IS '이용신청 고유번호';


--
-- TOC entry 3301 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN dh_dataset_userequest_log.ds_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_log.ds_oid IS '데이터셋 고유번호';


--
-- TOC entry 3302 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN dh_dataset_userequest_log.req_user_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_log.req_user_id IS '신청자id';


--
-- TOC entry 3303 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN dh_dataset_userequest_log.req_realtime_tf; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_log.req_realtime_tf IS '실시간 수신여부';


--
-- TOC entry 3304 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN dh_dataset_userequest_log.protocol; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_log.protocol IS '수신 방식(http,mqtt,websocket)';


--
-- TOC entry 3305 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN dh_dataset_userequest_log.url; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_log.url IS '수신 주소';


--
-- TOC entry 3306 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN dh_dataset_userequest_log.traffic_type; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_log.traffic_type IS '트래픽유형';


--
-- TOC entry 3307 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN dh_dataset_userequest_log.daily_limit; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_log.daily_limit IS '일일제한량';


--
-- TOC entry 3308 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN dh_dataset_userequest_log.expired_at; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_log.expired_at IS '제공만료일';


--
-- TOC entry 3309 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN dh_dataset_userequest_log.price; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_log.price IS '결제 가격';


--
-- TOC entry 3310 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN dh_dataset_userequest_log.pay_status; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_log.pay_status IS '결제 진행상태';


--
-- TOC entry 3311 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN dh_dataset_userequest_log.completed_at; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_log.completed_at IS '결제완료일시';


--
-- TOC entry 3312 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN dh_dataset_userequest_log.status; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_log.status IS '환불요청 상태';


--
-- TOC entry 3313 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN dh_dataset_userequest_log.reason; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_log.reason IS '환불요청 사유';


--
-- TOC entry 3314 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN dh_dataset_userequest_log.update_at; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_log.update_at IS '환불 완료일시';


--
-- TOC entry 3315 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN dh_dataset_userequest_log.cancel_at; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_log.cancel_at IS '해지일시';


--
-- TOC entry 221 (class 1259 OID 16469)
-- Name: dh_dataset_userequest_pay; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_dataset_userequest_pay (
    pay_id bigint NOT NULL,
    req_id bigint NOT NULL,
    expired_at timestamp without time zone,
    pay_status character varying(35) NOT NULL,
    completed_at timestamp without time zone,
    ds_price_id bigint NOT NULL,
    ds_period_id bigint NOT NULL
);


ALTER TABLE data_publish.dh_dataset_userequest_pay OWNER TO postgres;

--
-- TOC entry 3317 (class 0 OID 0)
-- Dependencies: 221
-- Name: TABLE dh_dataset_userequest_pay; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_dataset_userequest_pay IS '데이터셋 이용신청 유료결제정보';


--
-- TOC entry 3318 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN dh_dataset_userequest_pay.pay_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_pay.pay_id IS '유료결제정보 id';


--
-- TOC entry 3319 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN dh_dataset_userequest_pay.req_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_pay.req_id IS '이용신청 고유번호';


--
-- TOC entry 3320 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN dh_dataset_userequest_pay.expired_at; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_pay.expired_at IS '제공만료일';


--
-- TOC entry 3321 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN dh_dataset_userequest_pay.pay_status; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_pay.pay_status IS '결제 진행상태';


--
-- TOC entry 3322 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN dh_dataset_userequest_pay.completed_at; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_pay.completed_at IS '결제완료일시';


--
-- TOC entry 3323 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN dh_dataset_userequest_pay.ds_price_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_pay.ds_price_id IS '요금연결정보 ID';


--
-- TOC entry 3324 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN dh_dataset_userequest_pay.ds_period_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_pay.ds_period_id IS '가격정책금액 ID';


--
-- TOC entry 222 (class 1259 OID 16472)
-- Name: dh_dataset_userequest_reception; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_dataset_userequest_reception (
    recv_id bigint NOT NULL,
    req_id bigint NOT NULL,
    protocol character varying(32) NOT NULL,
    url character varying(100) NOT NULL
);


ALTER TABLE data_publish.dh_dataset_userequest_reception OWNER TO postgres;

--
-- TOC entry 3326 (class 0 OID 0)
-- Dependencies: 222
-- Name: TABLE dh_dataset_userequest_reception; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_dataset_userequest_reception IS '데이터셋 이용신청 실시간 수신정보';


--
-- TOC entry 3327 (class 0 OID 0)
-- Dependencies: 222
-- Name: COLUMN dh_dataset_userequest_reception.recv_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_reception.recv_id IS '수신정보 id';


--
-- TOC entry 3328 (class 0 OID 0)
-- Dependencies: 222
-- Name: COLUMN dh_dataset_userequest_reception.req_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_reception.req_id IS '이용신청 고유번호';


--
-- TOC entry 3329 (class 0 OID 0)
-- Dependencies: 222
-- Name: COLUMN dh_dataset_userequest_reception.protocol; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_reception.protocol IS '수신 방식(http,mqtt,websocket)';


--
-- TOC entry 3330 (class 0 OID 0)
-- Dependencies: 222
-- Name: COLUMN dh_dataset_userequest_reception.url; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_reception.url IS '수신 주소';


--
-- TOC entry 223 (class 1259 OID 16475)
-- Name: dh_dataset_userequest_refund; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_dataset_userequest_refund (
    refund_id bigint NOT NULL,
    req_id bigint NOT NULL,
    status character varying(32) NOT NULL,
    refund_degree integer NOT NULL,
    reason character varying(100),
    update_at timestamp without time zone,
    refuse_reason character varying(1000),
    refuse_at timestamp without time zone,
    created_at timestamp without time zone
);


ALTER TABLE data_publish.dh_dataset_userequest_refund OWNER TO postgres;

--
-- TOC entry 3332 (class 0 OID 0)
-- Dependencies: 223
-- Name: TABLE dh_dataset_userequest_refund; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_dataset_userequest_refund IS '데이터셋 이용신청 환불 신청';


--
-- TOC entry 3333 (class 0 OID 0)
-- Dependencies: 223
-- Name: COLUMN dh_dataset_userequest_refund.refund_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_refund.refund_id IS '환불신청id';


--
-- TOC entry 3334 (class 0 OID 0)
-- Dependencies: 223
-- Name: COLUMN dh_dataset_userequest_refund.req_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_refund.req_id IS '이용신청 고유번호';


--
-- TOC entry 3335 (class 0 OID 0)
-- Dependencies: 223
-- Name: COLUMN dh_dataset_userequest_refund.status; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_refund.status IS '환불요청 상태';


--
-- TOC entry 3336 (class 0 OID 0)
-- Dependencies: 223
-- Name: COLUMN dh_dataset_userequest_refund.refund_degree; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_refund.refund_degree IS '차수';


--
-- TOC entry 3337 (class 0 OID 0)
-- Dependencies: 223
-- Name: COLUMN dh_dataset_userequest_refund.reason; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_refund.reason IS '환불요청 사유';


--
-- TOC entry 3338 (class 0 OID 0)
-- Dependencies: 223
-- Name: COLUMN dh_dataset_userequest_refund.update_at; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_refund.update_at IS '환불 완료일시';


--
-- TOC entry 3339 (class 0 OID 0)
-- Dependencies: 223
-- Name: COLUMN dh_dataset_userequest_refund.refuse_reason; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_refund.refuse_reason IS '반려 사유';


--
-- TOC entry 3340 (class 0 OID 0)
-- Dependencies: 223
-- Name: COLUMN dh_dataset_userequest_refund.refuse_at; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_refund.refuse_at IS '반려 일시';


--
-- TOC entry 3341 (class 0 OID 0)
-- Dependencies: 223
-- Name: COLUMN dh_dataset_userequest_refund.created_at; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_dataset_userequest_refund.created_at IS '환불 요청일시';


--
-- TOC entry 224 (class 1259 OID 16481)
-- Name: dh_price_policy; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_price_policy (
    policy_id bigint NOT NULL,
    title character varying(100) NOT NULL,
    use_tf boolean NOT NULL,
    traffic_type character varying(35) NOT NULL,
    daliy_limit integer,
    created_at timestamp without time zone NOT NULL,
    creator_id character varying(35) NOT NULL,
    modified_at timestamp without time zone,
    modifier_id character varying(35)
);


ALTER TABLE data_publish.dh_price_policy OWNER TO postgres;

--
-- TOC entry 3343 (class 0 OID 0)
-- Dependencies: 224
-- Name: TABLE dh_price_policy; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_price_policy IS '가격정책 기본';


--
-- TOC entry 3344 (class 0 OID 0)
-- Dependencies: 224
-- Name: COLUMN dh_price_policy.policy_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_price_policy.policy_id IS '가격정책관리 id';


--
-- TOC entry 3345 (class 0 OID 0)
-- Dependencies: 224
-- Name: COLUMN dh_price_policy.title; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_price_policy.title IS '제목';


--
-- TOC entry 3346 (class 0 OID 0)
-- Dependencies: 224
-- Name: COLUMN dh_price_policy.use_tf; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_price_policy.use_tf IS '사용여부';


--
-- TOC entry 3347 (class 0 OID 0)
-- Dependencies: 224
-- Name: COLUMN dh_price_policy.traffic_type; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_price_policy.traffic_type IS '트래픽 유형';


--
-- TOC entry 3348 (class 0 OID 0)
-- Dependencies: 224
-- Name: COLUMN dh_price_policy.daliy_limit; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_price_policy.daliy_limit IS '일일제한량';


--
-- TOC entry 3349 (class 0 OID 0)
-- Dependencies: 224
-- Name: COLUMN dh_price_policy.created_at; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_price_policy.created_at IS '등록일시';


--
-- TOC entry 3350 (class 0 OID 0)
-- Dependencies: 224
-- Name: COLUMN dh_price_policy.creator_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_price_policy.creator_id IS '등록자id';


--
-- TOC entry 3351 (class 0 OID 0)
-- Dependencies: 224
-- Name: COLUMN dh_price_policy.modified_at; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_price_policy.modified_at IS '수정일시';


--
-- TOC entry 3352 (class 0 OID 0)
-- Dependencies: 224
-- Name: COLUMN dh_price_policy.modifier_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_price_policy.modifier_id IS '수정자id';


--
-- TOC entry 225 (class 1259 OID 16484)
-- Name: dh_price_policy_period; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_price_policy_period (
    period_id bigint NOT NULL,
    policy_id bigint NOT NULL,
    period_cd character varying(35) NOT NULL
);


ALTER TABLE data_publish.dh_price_policy_period OWNER TO postgres;

--
-- TOC entry 3354 (class 0 OID 0)
-- Dependencies: 225
-- Name: TABLE dh_price_policy_period; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_price_policy_period IS '가격정책 제공기간 연결 정보';


--
-- TOC entry 3355 (class 0 OID 0)
-- Dependencies: 225
-- Name: COLUMN dh_price_policy_period.period_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_price_policy_period.period_id IS '제공기간연결정보 id';


--
-- TOC entry 3356 (class 0 OID 0)
-- Dependencies: 225
-- Name: COLUMN dh_price_policy_period.policy_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_price_policy_period.policy_id IS '가격정책관리 id';


--
-- TOC entry 3357 (class 0 OID 0)
-- Dependencies: 225
-- Name: COLUMN dh_price_policy_period.period_cd; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_price_policy_period.period_cd IS '제공기간 코드';


--
-- TOC entry 226 (class 1259 OID 16487)
-- Name: dh_use_history; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.dh_use_history (
    use_id bigint NOT NULL,
    use_user_id character varying(32) NOT NULL,
    protocol character varying(20) NOT NULL,
    transmission_type character varying(20) NOT NULL,
    pay_tf boolean NOT NULL,
    use_time timestamp without time zone NOT NULL,
    ds_oid character varying(20) NOT NULL
);


ALTER TABLE data_publish.dh_use_history OWNER TO postgres;

--
-- TOC entry 3359 (class 0 OID 0)
-- Dependencies: 226
-- Name: TABLE dh_use_history; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.dh_use_history IS '데이터셋 활용내역';


--
-- TOC entry 3360 (class 0 OID 0)
-- Dependencies: 226
-- Name: COLUMN dh_use_history.use_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_use_history.use_id IS '활용ID';


--
-- TOC entry 3361 (class 0 OID 0)
-- Dependencies: 226
-- Name: COLUMN dh_use_history.use_user_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_use_history.use_user_id IS '활용자ID';


--
-- TOC entry 3362 (class 0 OID 0)
-- Dependencies: 226
-- Name: COLUMN dh_use_history.protocol; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_use_history.protocol IS '전송프로토콜';


--
-- TOC entry 3363 (class 0 OID 0)
-- Dependencies: 226
-- Name: COLUMN dh_use_history.transmission_type; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_use_history.transmission_type IS '전송유형';


--
-- TOC entry 3364 (class 0 OID 0)
-- Dependencies: 226
-- Name: COLUMN dh_use_history.pay_tf; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_use_history.pay_tf IS '유료여부';


--
-- TOC entry 3365 (class 0 OID 0)
-- Dependencies: 226
-- Name: COLUMN dh_use_history.use_time; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_use_history.use_time IS '활용일시';


--
-- TOC entry 3366 (class 0 OID 0)
-- Dependencies: 226
-- Name: COLUMN dh_use_history.ds_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.dh_use_history.ds_oid IS '데이터셋 고유번호';


--
-- TOC entry 227 (class 1259 OID 16490)
-- Name: pk_idx; Type: SEQUENCE; Schema: data_publish; Owner: postgres
--

CREATE SEQUENCE data_publish.pk_idx
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE data_publish.pk_idx OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16492)
-- Name: portal_category_tb; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.portal_category_tb (
    category_oid bigint NOT NULL,
    category_nm character varying(60) NOT NULL,
    category_desc character varying(4000) NOT NULL,
    category_cre_dt timestamp without time zone NOT NULL,
    category_cre_usr_id character varying(32) NOT NULL,
    category_img_org_nm character varying(128),
    category_img_save_nm character varying(128),
    category_img_save_path character varying(128),
    category_upt_usr_id character varying(32),
    category_upt_dt timestamp without time zone,
    category_parent_oid bigint DEFAULT '0'::bigint NOT NULL,
    category_img_size bigint,
    category_id character varying(10)
);


ALTER TABLE data_publish.portal_category_tb OWNER TO postgres;

--
-- TOC entry 3368 (class 0 OID 0)
-- Dependencies: 228
-- Name: TABLE portal_category_tb; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.portal_category_tb IS '카테고리 기본';


--
-- TOC entry 3369 (class 0 OID 0)
-- Dependencies: 228
-- Name: COLUMN portal_category_tb.category_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_category_tb.category_oid IS '카테고리 고유번호';


--
-- TOC entry 3370 (class 0 OID 0)
-- Dependencies: 228
-- Name: COLUMN portal_category_tb.category_nm; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_category_tb.category_nm IS '카테고리 이름';


--
-- TOC entry 3371 (class 0 OID 0)
-- Dependencies: 228
-- Name: COLUMN portal_category_tb.category_desc; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_category_tb.category_desc IS '카테고리 설명';


--
-- TOC entry 3372 (class 0 OID 0)
-- Dependencies: 228
-- Name: COLUMN portal_category_tb.category_cre_dt; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_category_tb.category_cre_dt IS '카테고리 등록일';


--
-- TOC entry 3373 (class 0 OID 0)
-- Dependencies: 228
-- Name: COLUMN portal_category_tb.category_cre_usr_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_category_tb.category_cre_usr_id IS '카테고리 등록자';


--
-- TOC entry 3374 (class 0 OID 0)
-- Dependencies: 228
-- Name: COLUMN portal_category_tb.category_img_org_nm; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_category_tb.category_img_org_nm IS '카테고리 이미지 이름';


--
-- TOC entry 3375 (class 0 OID 0)
-- Dependencies: 228
-- Name: COLUMN portal_category_tb.category_img_save_nm; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_category_tb.category_img_save_nm IS '카테고리 이미지 저장이름';


--
-- TOC entry 3376 (class 0 OID 0)
-- Dependencies: 228
-- Name: COLUMN portal_category_tb.category_img_save_path; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_category_tb.category_img_save_path IS '카테고리 이미지 저장경로';


--
-- TOC entry 3377 (class 0 OID 0)
-- Dependencies: 228
-- Name: COLUMN portal_category_tb.category_upt_usr_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_category_tb.category_upt_usr_id IS '카테고리 수정자';


--
-- TOC entry 3378 (class 0 OID 0)
-- Dependencies: 228
-- Name: COLUMN portal_category_tb.category_upt_dt; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_category_tb.category_upt_dt IS '카테고리 수정일';


--
-- TOC entry 3379 (class 0 OID 0)
-- Dependencies: 228
-- Name: COLUMN portal_category_tb.category_parent_oid; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_category_tb.category_parent_oid IS '카테고리 부모';


--
-- TOC entry 3380 (class 0 OID 0)
-- Dependencies: 228
-- Name: COLUMN portal_category_tb.category_img_size; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_category_tb.category_img_size IS '카테고리 이미지 사이즈';


--
-- TOC entry 3381 (class 0 OID 0)
-- Dependencies: 228
-- Name: COLUMN portal_category_tb.category_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_category_tb.category_id IS '카테고리 아이디';


--
-- TOC entry 229 (class 1259 OID 16499)
-- Name: portal_code_tb; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.portal_code_tb (
    code_id character varying(60) NOT NULL,
    code_name character varying(60) NOT NULL,
    code_use_fl character(1) NOT NULL,
    code_cre_dt timestamp without time zone NOT NULL,
    code_cre_usr_id character varying(32) NOT NULL,
    code_sort integer,
    code_desc character varying(4000),
    code_upt_usr_id character varying(32),
    code_upt_dt timestamp without time zone,
    codegroup_id character varying(128) NOT NULL
);


ALTER TABLE data_publish.portal_code_tb OWNER TO postgres;

--
-- TOC entry 3383 (class 0 OID 0)
-- Dependencies: 229
-- Name: TABLE portal_code_tb; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.portal_code_tb IS '코드 기본';


--
-- TOC entry 3384 (class 0 OID 0)
-- Dependencies: 229
-- Name: COLUMN portal_code_tb.code_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_code_tb.code_id IS '코드 아이디';


--
-- TOC entry 3385 (class 0 OID 0)
-- Dependencies: 229
-- Name: COLUMN portal_code_tb.code_name; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_code_tb.code_name IS '코드명';


--
-- TOC entry 3386 (class 0 OID 0)
-- Dependencies: 229
-- Name: COLUMN portal_code_tb.code_use_fl; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_code_tb.code_use_fl IS '코드 사용여부';


--
-- TOC entry 3387 (class 0 OID 0)
-- Dependencies: 229
-- Name: COLUMN portal_code_tb.code_cre_dt; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_code_tb.code_cre_dt IS '코드 생성일';


--
-- TOC entry 3388 (class 0 OID 0)
-- Dependencies: 229
-- Name: COLUMN portal_code_tb.code_cre_usr_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_code_tb.code_cre_usr_id IS '코드 생성자';


--
-- TOC entry 3389 (class 0 OID 0)
-- Dependencies: 229
-- Name: COLUMN portal_code_tb.code_sort; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_code_tb.code_sort IS '코드 순서';


--
-- TOC entry 3390 (class 0 OID 0)
-- Dependencies: 229
-- Name: COLUMN portal_code_tb.code_desc; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_code_tb.code_desc IS '코드 설명';


--
-- TOC entry 3391 (class 0 OID 0)
-- Dependencies: 229
-- Name: COLUMN portal_code_tb.code_upt_usr_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_code_tb.code_upt_usr_id IS '코드 수정자';


--
-- TOC entry 3392 (class 0 OID 0)
-- Dependencies: 229
-- Name: COLUMN portal_code_tb.code_upt_dt; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_code_tb.code_upt_dt IS '코드 수정일';


--
-- TOC entry 3393 (class 0 OID 0)
-- Dependencies: 229
-- Name: COLUMN portal_code_tb.codegroup_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_code_tb.codegroup_id IS '코드그룹 아이디';


--
-- TOC entry 230 (class 1259 OID 16505)
-- Name: portal_codegroup_tb; Type: TABLE; Schema: data_publish; Owner: postgres
--

CREATE TABLE data_publish.portal_codegroup_tb (
    codegroup_id character varying(128) NOT NULL,
    codegroup_name character varying(60) NOT NULL,
    codegroup_use_fl character(1) NOT NULL,
    codegroup_cre_dt timestamp without time zone NOT NULL,
    codegroup_cre_usr_id character varying(32) NOT NULL,
    codegroup_col integer,
    codegroup_desc character varying(4000),
    codegroup_upt_usr_id character varying(32),
    codegroup_upt_dt timestamp without time zone
);


ALTER TABLE data_publish.portal_codegroup_tb OWNER TO postgres;

--
-- TOC entry 3395 (class 0 OID 0)
-- Dependencies: 230
-- Name: TABLE portal_codegroup_tb; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON TABLE data_publish.portal_codegroup_tb IS '코드그룹 기본';


--
-- TOC entry 3396 (class 0 OID 0)
-- Dependencies: 230
-- Name: COLUMN portal_codegroup_tb.codegroup_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_codegroup_tb.codegroup_id IS '코드그룹 아이디';


--
-- TOC entry 3397 (class 0 OID 0)
-- Dependencies: 230
-- Name: COLUMN portal_codegroup_tb.codegroup_name; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_codegroup_tb.codegroup_name IS '코드그룹명';


--
-- TOC entry 3398 (class 0 OID 0)
-- Dependencies: 230
-- Name: COLUMN portal_codegroup_tb.codegroup_use_fl; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_codegroup_tb.codegroup_use_fl IS '코드그룹 사용여부';


--
-- TOC entry 3399 (class 0 OID 0)
-- Dependencies: 230
-- Name: COLUMN portal_codegroup_tb.codegroup_cre_dt; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_codegroup_tb.codegroup_cre_dt IS '코드그룹 생성일';


--
-- TOC entry 3400 (class 0 OID 0)
-- Dependencies: 230
-- Name: COLUMN portal_codegroup_tb.codegroup_cre_usr_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_codegroup_tb.codegroup_cre_usr_id IS '코드그룹 생성자';


--
-- TOC entry 3401 (class 0 OID 0)
-- Dependencies: 230
-- Name: COLUMN portal_codegroup_tb.codegroup_col; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_codegroup_tb.codegroup_col IS '코드그룹 순서';


--
-- TOC entry 3402 (class 0 OID 0)
-- Dependencies: 230
-- Name: COLUMN portal_codegroup_tb.codegroup_desc; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_codegroup_tb.codegroup_desc IS '코드그룹 설명';


--
-- TOC entry 3403 (class 0 OID 0)
-- Dependencies: 230
-- Name: COLUMN portal_codegroup_tb.codegroup_upt_usr_id; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_codegroup_tb.codegroup_upt_usr_id IS '코드그룹 수정자';


--
-- TOC entry 3404 (class 0 OID 0)
-- Dependencies: 230
-- Name: COLUMN portal_codegroup_tb.codegroup_upt_dt; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON COLUMN data_publish.portal_codegroup_tb.codegroup_upt_dt IS '코드그룹 수정일';



--
-- TOC entry 3406 (class 0 OID 0)
-- Dependencies: 227
-- Name: pk_idx; Type: SEQUENCE SET; Schema: data_publish; Owner: postgres
--

SELECT pg_catalog.setval('data_publish.pk_idx', 1100000000000622, true);


--
-- TOC entry 2936 (class 2606 OID 16514)
-- Name: dh_data_analyst_model_info dh_data_analyst_model_info_pk; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_data_analyst_model_info
    ADD CONSTRAINT dh_data_analyst_model_info_pk PRIMARY KEY (ami_oid);


--
-- TOC entry 2938 (class 2606 OID 16516)
-- Name: dh_datamodel_analyst_request dh_datamodel_analyst_request_pkey; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_datamodel_analyst_request
    ADD CONSTRAINT dh_datamodel_analyst_request_pkey PRIMARY KEY (ar_model_oid);


--
-- TOC entry 2942 (class 2606 OID 16518)
-- Name: dh_dataset_approval_hist dh_dataset_approval_hist_pk; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_dataset_approval_hist
    ADD CONSTRAINT dh_dataset_approval_hist_pk PRIMARY KEY (apphist_id);


--
-- TOC entry 2946 (class 2606 OID 16520)
-- Name: dh_dataset_inquiry dh_dataset_inquiry_pkey; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_dataset_inquiry
    ADD CONSTRAINT dh_dataset_inquiry_pkey PRIMARY KEY (inq_oid);


--
-- TOC entry 2948 (class 2606 OID 16522)
-- Name: dh_dataset_instance_info dh_dataset_instance_info_pk; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_dataset_instance_info
    ADD CONSTRAINT dh_dataset_instance_info_pk PRIMARY KEY (dii_id);


--
-- TOC entry 2950 (class 2606 OID 16524)
-- Name: dh_dataset_interest dh_dataset_interest_pk; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_dataset_interest
    ADD CONSTRAINT dh_dataset_interest_pk PRIMARY KEY (itrt_id);


--
-- TOC entry 2956 (class 2606 OID 16526)
-- Name: dh_dataset_model_ds dh_dataset_model_ds_pk; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_dataset_model_ds
    ADD CONSTRAINT dh_dataset_model_ds_pk PRIMARY KEY (dmd_id);


--
-- TOC entry 2954 (class 2606 OID 16528)
-- Name: dh_dataset_model dh_dataset_model_pk; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_dataset_model
    ADD CONSTRAINT dh_dataset_model_pk PRIMARY KEY (model_id);


--
-- TOC entry 2958 (class 2606 OID 16530)
-- Name: dh_dataset_output_info dh_dataset_output_info_pk; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_dataset_output_info
    ADD CONSTRAINT dh_dataset_output_info_pk PRIMARY KEY (output_oid);


--
-- TOC entry 2964 (class 2606 OID 16532)
-- Name: dh_dataset_satisfaction_rating dh_dataset_satisfaction_rating_pk; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_dataset_satisfaction_rating
    ADD CONSTRAINT dh_dataset_satisfaction_rating_pk PRIMARY KEY (dsr_oid);


--
-- TOC entry 2966 (class 2606 OID 16534)
-- Name: dh_dataset_search_inf_tb dh_dataset_search_inf_tb_pk; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_dataset_search_inf_tb
    ADD CONSTRAINT dh_dataset_search_inf_tb_pk PRIMARY KEY (si_oid);


--
-- TOC entry 2990 (class 2606 OID 16583)
-- Name: dh_dataset_subscription dh_dataset_subscription_pk; Type: CONSTRAINT; Schema: data_publish; Owner: smartcity_data
--

ALTER TABLE ONLY data_publish.dh_dataset_subscription
    ADD CONSTRAINT dh_dataset_subscription_pk PRIMARY KEY (subscription_id);


--
-- TOC entry 2968 (class 2606 OID 16536)
-- Name: dh_dataset_userequest dh_dataset_userequest_pk; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_dataset_userequest
    ADD CONSTRAINT dh_dataset_userequest_pk PRIMARY KEY (req_oid);


--
-- TOC entry 2934 (class 2606 OID 16538)
-- Name: dh_adaptor pk_dh_adaptor; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_adaptor
    ADD CONSTRAINT pk_dh_adaptor PRIMARY KEY (ad_id);


--
-- TOC entry 2944 (class 2606 OID 16540)
-- Name: dh_dataset_file pk_dh_dataset_file; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_dataset_file
    ADD CONSTRAINT pk_dh_dataset_file PRIMARY KEY (ds_file_oid, ds_oid);


--
-- TOC entry 2952 (class 2606 OID 16542)
-- Name: dh_dataset_interest_log pk_dh_dataset_interest_log; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_dataset_interest_log
    ADD CONSTRAINT pk_dh_dataset_interest_log PRIMARY KEY (itrt_id);


--
-- TOC entry 2960 (class 2606 OID 16544)
-- Name: dh_dataset_period_price pk_dh_dataset_period_price; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_dataset_period_price
    ADD CONSTRAINT pk_dh_dataset_period_price PRIMARY KEY (ds_period_id);


--
-- TOC entry 2962 (class 2606 OID 16546)
-- Name: dh_dataset_price pk_dh_dataset_price; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_dataset_price
    ADD CONSTRAINT pk_dh_dataset_price PRIMARY KEY (ds_price_id);


--
-- TOC entry 2940 (class 2606 OID 16548)
-- Name: dh_dataset pk_dh_dataset_prod; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_dataset
    ADD CONSTRAINT pk_dh_dataset_prod PRIMARY KEY (ds_oid);


--
-- TOC entry 2970 (class 2606 OID 16550)
-- Name: dh_dataset_userequest_log pk_dh_dataset_userequest_log; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_dataset_userequest_log
    ADD CONSTRAINT pk_dh_dataset_userequest_log PRIMARY KEY (req_id);


--
-- TOC entry 2972 (class 2606 OID 16552)
-- Name: dh_dataset_userequest_pay pk_dh_dataset_userequest_pay; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_dataset_userequest_pay
    ADD CONSTRAINT pk_dh_dataset_userequest_pay PRIMARY KEY (pay_id);


--
-- TOC entry 2974 (class 2606 OID 16554)
-- Name: dh_dataset_userequest_reception pk_dh_dataset_userequest_reception; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_dataset_userequest_reception
    ADD CONSTRAINT pk_dh_dataset_userequest_reception PRIMARY KEY (recv_id);


--
-- TOC entry 2976 (class 2606 OID 16556)
-- Name: dh_dataset_userequest_refund pk_dh_dataset_userequest_refund; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_dataset_userequest_refund
    ADD CONSTRAINT pk_dh_dataset_userequest_refund PRIMARY KEY (refund_id);


--
-- TOC entry 2978 (class 2606 OID 16558)
-- Name: dh_price_policy pk_dh_price_policy; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_price_policy
    ADD CONSTRAINT pk_dh_price_policy PRIMARY KEY (policy_id);


--
-- TOC entry 2980 (class 2606 OID 16560)
-- Name: dh_price_policy_period pk_dh_price_policy_period; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_price_policy_period
    ADD CONSTRAINT pk_dh_price_policy_period PRIMARY KEY (period_id);


--
-- TOC entry 2982 (class 2606 OID 16562)
-- Name: dh_use_history pk_dh_use_history; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_use_history
    ADD CONSTRAINT pk_dh_use_history PRIMARY KEY (use_id);


--
-- TOC entry 2986 (class 2606 OID 16564)
-- Name: portal_code_tb pk_portal_code_tb; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.portal_code_tb
    ADD CONSTRAINT pk_portal_code_tb PRIMARY KEY (code_id, codegroup_id);


--
-- TOC entry 2988 (class 2606 OID 16566)
-- Name: portal_codegroup_tb pk_portal_codegroup_tb; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.portal_codegroup_tb
    ADD CONSTRAINT pk_portal_codegroup_tb PRIMARY KEY (codegroup_id);


--
-- TOC entry 2984 (class 2606 OID 16568)
-- Name: portal_category_tb portal_category_tb_pkey; Type: CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.portal_category_tb
    ADD CONSTRAINT portal_category_tb_pkey PRIMARY KEY (category_oid);


--
-- TOC entry 3407 (class 0 OID 0)
-- Dependencies: 2952
-- Name: INDEX pk_dh_dataset_interest_log; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON INDEX data_publish.pk_dh_dataset_interest_log IS '데이터셋 관심상품 해지 현황 내역 기본키';


--
-- TOC entry 3408 (class 0 OID 0)
-- Dependencies: 2960
-- Name: INDEX pk_dh_dataset_period_price; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON INDEX data_publish.pk_dh_dataset_period_price IS '데이터셋 가격정책 금액정보 기본키';


--
-- TOC entry 3409 (class 0 OID 0)
-- Dependencies: 2962
-- Name: INDEX pk_dh_dataset_price; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON INDEX data_publish.pk_dh_dataset_price IS '데이터셋 가격정책 연결정보 기본키';


--
-- TOC entry 3410 (class 0 OID 0)
-- Dependencies: 2940
-- Name: INDEX pk_dh_dataset_prod; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON INDEX data_publish.pk_dh_dataset_prod IS '데이터셋 기본(출시) 기본키';


--
-- TOC entry 3411 (class 0 OID 0)
-- Dependencies: 2970
-- Name: INDEX pk_dh_dataset_userequest_log; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON INDEX data_publish.pk_dh_dataset_userequest_log IS '데이터셋 이용신청 해지 현황 내역 기본키';


--
-- TOC entry 3412 (class 0 OID 0)
-- Dependencies: 2972
-- Name: INDEX pk_dh_dataset_userequest_pay; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON INDEX data_publish.pk_dh_dataset_userequest_pay IS '데이터셋 이용신청 유료결제정보 기본키';


--
-- TOC entry 3413 (class 0 OID 0)
-- Dependencies: 2974
-- Name: INDEX pk_dh_dataset_userequest_reception; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON INDEX data_publish.pk_dh_dataset_userequest_reception IS '데이터셋 이용신청 실시간 수신정보 기본키';


--
-- TOC entry 3414 (class 0 OID 0)
-- Dependencies: 2976
-- Name: INDEX pk_dh_dataset_userequest_refund; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON INDEX data_publish.pk_dh_dataset_userequest_refund IS '데이터셋 이용신청 환불 신청 기본키';


--
-- TOC entry 3415 (class 0 OID 0)
-- Dependencies: 2978
-- Name: INDEX pk_dh_price_policy; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON INDEX data_publish.pk_dh_price_policy IS '가격정책 기본 기본키';


--
-- TOC entry 3416 (class 0 OID 0)
-- Dependencies: 2980
-- Name: INDEX pk_dh_price_policy_period; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON INDEX data_publish.pk_dh_price_policy_period IS '가격정책 제공기간 연결 정보 기본키';


--
-- TOC entry 3417 (class 0 OID 0)
-- Dependencies: 2982
-- Name: INDEX pk_dh_use_history; Type: COMMENT; Schema: data_publish; Owner: postgres
--

COMMENT ON INDEX data_publish.pk_dh_use_history IS '데이터셋 활용내역 기본키';




INSERT INTO data_publish.portal_category_tb VALUES (1572334474264001, '안전', '안전 관련 데이터 제공', '2019-10-29 16:34:34', 'cityhub08', 'img_safety.jpg', '33ad53a1-804f-445a-9228-8afd2b6f7d72', '/opt/smartcity', 'cityhub08', '2019-10-30 17:32:24', 0, 96935);
INSERT INTO data_publish.portal_category_tb VALUES (1572334484475001, '환경', '환경 관련 데이터 제공', '2019-10-29 16:34:44', 'cityhub08', 'img_environment.jpg', '5420ea08-5a27-4adb-8b2c-53e0cc89f70d', '/opt/smartcity', 'cityhub08', '2019-10-30 17:32:30', 0, 109187);
INSERT INTO data_publish.portal_category_tb VALUES (1572334504297001, '생활/복지', '생활/복지 관련 데이터 제공', '2019-10-29 16:35:04', 'cityhub08', 'img_life.jpg', '85c99df8-a8b0-4c32-9d29-f2f1ec44c0e3', '/opt/smartcity', 'cityhub08', '2019-10-30 17:32:43', 0, 111406);
INSERT INTO data_publish.portal_category_tb VALUES (1572334513294001, '문화', '문화 관련 데이터 제공', '2019-10-29 16:35:13', 'cityhub08', 'img_culture.jpg', 'db9a6f86-3c87-4991-ac94-751ed619ccfc', '/opt/smartcity', 'cityhub08', '2019-10-30 17:32:50', 0, 113450);
INSERT INTO data_publish.portal_category_tb VALUES (1572334494101001, '에너지', '에너지 관련 데이터 제공', '2019-10-29 16:34:54', 'cityhub08', 'img_energy.jpg', 'c0674d9b-6046-4cc7-825f-713d3d4001f7', '/opt/smartcity', 'cityhub08', '2019-11-26 17:07:22.620664', 0, 134187);
INSERT INTO data_publish.portal_category_tb VALUES (1572334465528001, '교통', '교통 관련 데이터 제공', '2019-10-29 16:34:25', 'cityhub08', 'img_traffic.jpg', 'eca0fc35-0d6c-44ff-b3b3-554dacf03eb9', '/opt/smartcity', 'cityhub08', '2020-02-24 17:23:41.61941', 0, 129212);
INSERT INTO data_publish.portal_category_tb VALUES (1572334455994001, '행정', '행정 관련 데이터 제공', '2019-10-29 16:34:15', 'cityhub08', NULL, NULL, NULL, 'cityhub08', '2020-07-30 14:03:36.441542', 0, 0);


--
-- TOC entry 2991 (class 2606 OID 16569)
-- Name: dh_data_analyst_model_info dh_data_analyst_model_info_fk; Type: FK CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_data_analyst_model_info
    ADD CONSTRAINT dh_data_analyst_model_info_fk FOREIGN KEY (ar_model_oid) REFERENCES data_publish.dh_datamodel_analyst_request(ar_model_oid) ON DELETE CASCADE;


--
-- TOC entry 2992 (class 2606 OID 16574)
-- Name: dh_dataset_approval_hist dh_dataset_approval_hist_fk; Type: FK CONSTRAINT; Schema: data_publish; Owner: postgres
--

ALTER TABLE ONLY data_publish.dh_dataset_approval_hist
    ADD CONSTRAINT dh_dataset_approval_hist_fk FOREIGN KEY (ds_oid) REFERENCES data_publish.dh_dataset(ds_oid) ON DELETE CASCADE;


--
-- TOC entry 3171 (class 0 OID 0)
-- Dependencies: 201
-- Name: TABLE dh_adaptor; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_adaptor TO smartcity_data;


--
-- TOC entry 3180 (class 0 OID 0)
-- Dependencies: 202
-- Name: TABLE dh_adaptor_field; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_adaptor_field TO smartcity_data;


--
-- TOC entry 3184 (class 0 OID 0)
-- Dependencies: 203
-- Name: TABLE dh_data_analyst_model_info; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_data_analyst_model_info TO smartcity_data;


--
-- TOC entry 3185 (class 0 OID 0)
-- Dependencies: 204
-- Name: TABLE dh_datamodel_analyst_request; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_datamodel_analyst_request TO smartcity_data;


--
-- TOC entry 3212 (class 0 OID 0)
-- Dependencies: 205
-- Name: TABLE dh_dataset; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_dataset TO smartcity_data;


--
-- TOC entry 3220 (class 0 OID 0)
-- Dependencies: 206
-- Name: TABLE dh_dataset_approval_hist; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_dataset_approval_hist TO smartcity_data;


--
-- TOC entry 3229 (class 0 OID 0)
-- Dependencies: 207
-- Name: TABLE dh_dataset_file; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_dataset_file TO smartcity_data;


--
-- TOC entry 3230 (class 0 OID 0)
-- Dependencies: 208
-- Name: TABLE dh_dataset_inquiry; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_dataset_inquiry TO smartcity_data;


--
-- TOC entry 3235 (class 0 OID 0)
-- Dependencies: 209
-- Name: TABLE dh_dataset_instance_info; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_dataset_instance_info TO smartcity_data;


--
-- TOC entry 3237 (class 0 OID 0)
-- Dependencies: 210
-- Name: TABLE dh_dataset_interest; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_dataset_interest TO smartcity_data;


--
-- TOC entry 3243 (class 0 OID 0)
-- Dependencies: 211
-- Name: TABLE dh_dataset_interest_log; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_dataset_interest_log TO smartcity_data;


--
-- TOC entry 3250 (class 0 OID 0)
-- Dependencies: 212
-- Name: TABLE dh_dataset_model; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_dataset_model TO smartcity_data;


--
-- TOC entry 3255 (class 0 OID 0)
-- Dependencies: 213
-- Name: TABLE dh_dataset_model_ds; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_dataset_model_ds TO smartcity_data;


--
-- TOC entry 3263 (class 0 OID 0)
-- Dependencies: 214
-- Name: TABLE dh_dataset_output_info; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_dataset_output_info TO smartcity_data;


--
-- TOC entry 3269 (class 0 OID 0)
-- Dependencies: 215
-- Name: TABLE dh_dataset_period_price; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_dataset_period_price TO smartcity_data;


--
-- TOC entry 3276 (class 0 OID 0)
-- Dependencies: 216
-- Name: TABLE dh_dataset_price; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_dataset_price TO smartcity_data;


--
-- TOC entry 3283 (class 0 OID 0)
-- Dependencies: 217
-- Name: TABLE dh_dataset_satisfaction_rating; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_dataset_satisfaction_rating TO smartcity_data;


--
-- TOC entry 3291 (class 0 OID 0)
-- Dependencies: 218
-- Name: TABLE dh_dataset_search_inf_tb; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_dataset_search_inf_tb TO smartcity_data;


--
-- TOC entry 3298 (class 0 OID 0)
-- Dependencies: 219
-- Name: TABLE dh_dataset_userequest; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_dataset_userequest TO smartcity_data;


--
-- TOC entry 3316 (class 0 OID 0)
-- Dependencies: 220
-- Name: TABLE dh_dataset_userequest_log; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_dataset_userequest_log TO smartcity_data;


--
-- TOC entry 3325 (class 0 OID 0)
-- Dependencies: 221
-- Name: TABLE dh_dataset_userequest_pay; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_dataset_userequest_pay TO smartcity_data;


--
-- TOC entry 3331 (class 0 OID 0)
-- Dependencies: 222
-- Name: TABLE dh_dataset_userequest_reception; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_dataset_userequest_reception TO smartcity_data;


--
-- TOC entry 3342 (class 0 OID 0)
-- Dependencies: 223
-- Name: TABLE dh_dataset_userequest_refund; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_dataset_userequest_refund TO smartcity_data;


--
-- TOC entry 3353 (class 0 OID 0)
-- Dependencies: 224
-- Name: TABLE dh_price_policy; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_price_policy TO smartcity_data;


--
-- TOC entry 3358 (class 0 OID 0)
-- Dependencies: 225
-- Name: TABLE dh_price_policy_period; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_price_policy_period TO smartcity_data;


--
-- TOC entry 3367 (class 0 OID 0)
-- Dependencies: 226
-- Name: TABLE dh_use_history; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.dh_use_history TO smartcity_data;


--
-- TOC entry 3382 (class 0 OID 0)
-- Dependencies: 228
-- Name: TABLE portal_category_tb; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.portal_category_tb TO smartcity_data;


--
-- TOC entry 3394 (class 0 OID 0)
-- Dependencies: 229
-- Name: TABLE portal_code_tb; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.portal_code_tb TO smartcity_data;


--
-- TOC entry 3405 (class 0 OID 0)
-- Dependencies: 230
-- Name: TABLE portal_codegroup_tb; Type: ACL; Schema: data_publish; Owner: postgres
--

GRANT ALL ON TABLE data_publish.portal_codegroup_tb TO smartcity_data;


-- Completed on 2022-04-08 17:15:11

--
-- PostgreSQL database dump complete
--




-- Permissions

GRANT CREATE, USAGE ON SCHEMA data_publish TO smartcity_data;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_adaptor TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_adaptor_field TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_data_analyst_model_info TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_datamodel_analyst_request TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_dataset TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_dataset_approval_hist TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_dataset_file TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_dataset_inquiry TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_dataset_instance_info TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_dataset_interest TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_dataset_interest_log TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_dataset_model TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_dataset_model_ds TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_dataset_output_info TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_dataset_period_price TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_dataset_price TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_dataset_satisfaction_rating TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_dataset_search_inf_tb TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_dataset_subscription TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_dataset_userequest TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_dataset_userequest_log TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_dataset_userequest_pay TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_dataset_userequest_reception TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_dataset_userequest_refund TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_price_policy TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_price_policy_period TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.dh_use_history TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.portal_category_tb TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.portal_code_tb TO smartcity_data WITH GRANT OPTION;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE data_publish.portal_codegroup_tb TO smartcity_data WITH GRANT OPTION;
