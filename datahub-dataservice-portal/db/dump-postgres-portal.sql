--
-- PostgreSQL database dump
--

-- Dumped from database version 13.2 (Debian 13.2-1.pgdg100+1)
-- Dumped by pg_dump version 13.2

-- Started on 2022-04-08 17:53:05

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
-- TOC entry 4 (class 2615 OID 16387)
-- Name: portal; Type: SCHEMA; Schema: -; Owner: smartcity_portal
--

CREATE SCHEMA portal;

-- DROP ROLE smartcity_portal;

CREATE ROLE smartcity_portal WITH 
	NOSUPERUSER
	NOCREATEDB
	NOCREATEROLE
	NOINHERIT
	LOGIN
	NOREPLICATION
	NOBYPASSRLS
	CONNECTION LIMIT -1;
	
ALTER ROLE smartcity_portal WITH PASSWORD 'dpsxndpa';	


ALTER SCHEMA portal OWNER TO smartcity_portal;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 202 (class 1259 OID 16534)
-- Name: portal_authgroup_tb; Type: TABLE; Schema: portal; Owner: postgres
--

CREATE TABLE portal.portal_authgroup_tb (
    authgroup_oid bigint NOT NULL,
    authgroup_nm character varying(128) NOT NULL,
    authgroup_gb_cd character varying(32) NOT NULL,
    authgroup_use_fl character(1) DEFAULT 'Y'::bpchar NOT NULL,
    authgroup_cre_dt timestamp without time zone,
    authgroup_cre_usr_id character varying(32),
    authgroup_desc character varying(4000),
    authgroup_upt_usr_id character varying(32),
    authgroup_upt_dt timestamp without time zone,
    authgroup_id character varying(6)
);


ALTER TABLE portal.portal_authgroup_tb OWNER TO postgres;

--
-- TOC entry 3145 (class 0 OID 0)
-- Dependencies: 202
-- Name: TABLE portal_authgroup_tb; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON TABLE portal.portal_authgroup_tb IS '권한그룹관리기본';


--
-- TOC entry 3146 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN portal_authgroup_tb.authgroup_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_authgroup_tb.authgroup_oid IS '권한그룹고유번호';


--
-- TOC entry 3147 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN portal_authgroup_tb.authgroup_nm; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_authgroup_tb.authgroup_nm IS '권한그룹명';


--
-- TOC entry 3148 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN portal_authgroup_tb.authgroup_gb_cd; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_authgroup_tb.authgroup_gb_cd IS '권한그룹구분코드';


--
-- TOC entry 3149 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN portal_authgroup_tb.authgroup_use_fl; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_authgroup_tb.authgroup_use_fl IS '권한그룹사용여부';


--
-- TOC entry 3150 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN portal_authgroup_tb.authgroup_cre_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_authgroup_tb.authgroup_cre_dt IS '권한그룹생성일';


--
-- TOC entry 3151 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN portal_authgroup_tb.authgroup_cre_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_authgroup_tb.authgroup_cre_usr_id IS '권한그룹생성자';


--
-- TOC entry 3152 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN portal_authgroup_tb.authgroup_desc; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_authgroup_tb.authgroup_desc IS '권한그룹순서';


--
-- TOC entry 3153 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN portal_authgroup_tb.authgroup_upt_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_authgroup_tb.authgroup_upt_usr_id IS '권한그룹수정자';


--
-- TOC entry 3154 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN portal_authgroup_tb.authgroup_upt_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_authgroup_tb.authgroup_upt_dt IS '권한그룹수정일';


--
-- TOC entry 3155 (class 0 OID 0)
-- Dependencies: 202
-- Name: COLUMN portal_authgroup_tb.authgroup_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_authgroup_tb.authgroup_id IS '권한그룹ID';


--
-- TOC entry 203 (class 1259 OID 16541)
-- Name: portal_authgroupmenumap_tb; Type: TABLE; Schema: portal; Owner: postgres
--

CREATE TABLE portal.portal_authgroupmenumap_tb (
    menu_oid bigint NOT NULL,
    authgroup_oid bigint NOT NULL,
    read_yn character varying(1),
    write_yn character varying(1),
    modified_yn character varying(1),
    delete_yn character varying(1)
);


ALTER TABLE portal.portal_authgroupmenumap_tb OWNER TO postgres;

--
-- TOC entry 3157 (class 0 OID 0)
-- Dependencies: 203
-- Name: TABLE portal_authgroupmenumap_tb; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON TABLE portal.portal_authgroupmenumap_tb IS '권한그룹메뉴매핑';


--
-- TOC entry 3158 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN portal_authgroupmenumap_tb.menu_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_authgroupmenumap_tb.menu_oid IS '메뉴고유번호';


--
-- TOC entry 3159 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN portal_authgroupmenumap_tb.authgroup_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_authgroupmenumap_tb.authgroup_oid IS '권한그룹고유번호';


--
-- TOC entry 3160 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN portal_authgroupmenumap_tb.read_yn; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_authgroupmenumap_tb.read_yn IS '읽기권한';


--
-- TOC entry 3161 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN portal_authgroupmenumap_tb.write_yn; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_authgroupmenumap_tb.write_yn IS '쓰기권한';


--
-- TOC entry 3162 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN portal_authgroupmenumap_tb.modified_yn; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_authgroupmenumap_tb.modified_yn IS '수정권한';


--
-- TOC entry 3163 (class 0 OID 0)
-- Dependencies: 203
-- Name: COLUMN portal_authgroupmenumap_tb.delete_yn; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_authgroupmenumap_tb.delete_yn IS '삭제권한';


--
-- TOC entry 204 (class 1259 OID 16544)
-- Name: portal_authgroupusermap_tb; Type: TABLE; Schema: portal; Owner: postgres
--

CREATE TABLE portal.portal_authgroupusermap_tb (
    authgroup_oid bigint,
    user_id character varying(32)
);


ALTER TABLE portal.portal_authgroupusermap_tb OWNER TO postgres;

--
-- TOC entry 3165 (class 0 OID 0)
-- Dependencies: 204
-- Name: TABLE portal_authgroupusermap_tb; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON TABLE portal.portal_authgroupusermap_tb IS '권한그룹사용자매핑';


--
-- TOC entry 3166 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN portal_authgroupusermap_tb.authgroup_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_authgroupusermap_tb.authgroup_oid IS '권한그룹고유번호';


--
-- TOC entry 3167 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN portal_authgroupusermap_tb.user_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_authgroupusermap_tb.user_id IS '사용자아이디';


--
-- TOC entry 206 (class 1259 OID 16554)
-- Name: portal_code_tb; Type: TABLE; Schema: portal; Owner: postgres
--

CREATE TABLE portal.portal_code_tb (
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


ALTER TABLE portal.portal_code_tb OWNER TO postgres;

--
-- TOC entry 3183 (class 0 OID 0)
-- Dependencies: 206
-- Name: TABLE portal_code_tb; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON TABLE portal.portal_code_tb IS '코드 기본';


--
-- TOC entry 3184 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN portal_code_tb.code_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_code_tb.code_id IS '코드 아이디';


--
-- TOC entry 3185 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN portal_code_tb.code_name; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_code_tb.code_name IS '코드명';


--
-- TOC entry 3186 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN portal_code_tb.code_use_fl; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_code_tb.code_use_fl IS '코드 사용여부';


--
-- TOC entry 3187 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN portal_code_tb.code_cre_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_code_tb.code_cre_dt IS '코드 생성일';


--
-- TOC entry 3188 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN portal_code_tb.code_cre_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_code_tb.code_cre_usr_id IS '코드 생성자';


--
-- TOC entry 3189 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN portal_code_tb.code_sort; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_code_tb.code_sort IS '코드 순서';


--
-- TOC entry 3190 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN portal_code_tb.code_desc; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_code_tb.code_desc IS '코드 설명';


--
-- TOC entry 3191 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN portal_code_tb.code_upt_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_code_tb.code_upt_usr_id IS '코드 수정자';


--
-- TOC entry 3192 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN portal_code_tb.code_upt_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_code_tb.code_upt_dt IS '코드 수정일';


--
-- TOC entry 3193 (class 0 OID 0)
-- Dependencies: 206
-- Name: COLUMN portal_code_tb.codegroup_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_code_tb.codegroup_id IS '코드그룹 아이디';


--
-- TOC entry 207 (class 1259 OID 16560)
-- Name: portal_codegroup_tb; Type: TABLE; Schema: portal; Owner: postgres
--

CREATE TABLE portal.portal_codegroup_tb (
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


ALTER TABLE portal.portal_codegroup_tb OWNER TO postgres;

--
-- TOC entry 3195 (class 0 OID 0)
-- Dependencies: 207
-- Name: TABLE portal_codegroup_tb; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON TABLE portal.portal_codegroup_tb IS '코드그룹 기본';


--
-- TOC entry 3196 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN portal_codegroup_tb.codegroup_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_codegroup_tb.codegroup_id IS '코드그룹 아이디';


--
-- TOC entry 3197 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN portal_codegroup_tb.codegroup_name; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_codegroup_tb.codegroup_name IS '코드그룹명';


--
-- TOC entry 3198 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN portal_codegroup_tb.codegroup_use_fl; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_codegroup_tb.codegroup_use_fl IS '코드그룹 사용여부';


--
-- TOC entry 3199 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN portal_codegroup_tb.codegroup_cre_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_codegroup_tb.codegroup_cre_dt IS '코드그룹 생성일';


--
-- TOC entry 3200 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN portal_codegroup_tb.codegroup_cre_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_codegroup_tb.codegroup_cre_usr_id IS '코드그룹 생성자';


--
-- TOC entry 3201 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN portal_codegroup_tb.codegroup_col; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_codegroup_tb.codegroup_col IS '코드그룹 순서';


--
-- TOC entry 3202 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN portal_codegroup_tb.codegroup_desc; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_codegroup_tb.codegroup_desc IS '코드그룹 설명';


--
-- TOC entry 3203 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN portal_codegroup_tb.codegroup_upt_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_codegroup_tb.codegroup_upt_usr_id IS '코드그룹 수정자';


--
-- TOC entry 3204 (class 0 OID 0)
-- Dependencies: 207
-- Name: COLUMN portal_codegroup_tb.codegroup_upt_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_codegroup_tb.codegroup_upt_dt IS '코드그룹 수정일';


--
-- TOC entry 208 (class 1259 OID 16566)
-- Name: portal_datacomplain_tb; Type: TABLE; Schema: portal; Owner: postgres
--

CREATE TABLE portal.portal_datacomplain_tb (
    datacomplain_oid bigint NOT NULL,
    datacomplain_cnt bigint DEFAULT 0,
    datacomplain_title character varying(128),
    datacomplain_gb_cd character varying(128),
    datacomplain_cre_dt timestamp without time zone,
    datacomplain_stat_cd character varying(32),
    datacomplain_content character varying(4000),
    datacomplain_answer_dt timestamp without time zone,
    datacomplain_cre_usr_id character varying(32),
    datacomplain_answer_usr_id character varying(32),
    datacomplain_id character varying(6),
    datacomplain_answer character varying(4000)
);


ALTER TABLE portal.portal_datacomplain_tb OWNER TO postgres;

--
-- TOC entry 3206 (class 0 OID 0)
-- Dependencies: 208
-- Name: TABLE portal_datacomplain_tb; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON TABLE portal.portal_datacomplain_tb IS '데이터민원관리 기본';


--
-- TOC entry 3207 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN portal_datacomplain_tb.datacomplain_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_datacomplain_tb.datacomplain_oid IS '데이터민원 고유번호';


--
-- TOC entry 3208 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN portal_datacomplain_tb.datacomplain_cnt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_datacomplain_tb.datacomplain_cnt IS '데이터민원 조회수';


--
-- TOC entry 3209 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN portal_datacomplain_tb.datacomplain_title; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_datacomplain_tb.datacomplain_title IS '데이터민원 제목';


--
-- TOC entry 3210 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN portal_datacomplain_tb.datacomplain_gb_cd; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_datacomplain_tb.datacomplain_gb_cd IS '데이터민원 구분코드';


--
-- TOC entry 3211 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN portal_datacomplain_tb.datacomplain_cre_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_datacomplain_tb.datacomplain_cre_dt IS '데이터민원 생성일';


--
-- TOC entry 3212 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN portal_datacomplain_tb.datacomplain_stat_cd; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_datacomplain_tb.datacomplain_stat_cd IS '데이터민원 상태코드';


--
-- TOC entry 3213 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN portal_datacomplain_tb.datacomplain_content; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_datacomplain_tb.datacomplain_content IS '데이터민원 내용';


--
-- TOC entry 3214 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN portal_datacomplain_tb.datacomplain_answer_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_datacomplain_tb.datacomplain_answer_dt IS '데이터민원 답변일';


--
-- TOC entry 3215 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN portal_datacomplain_tb.datacomplain_cre_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_datacomplain_tb.datacomplain_cre_usr_id IS '데이터민원 생성자';


--
-- TOC entry 3216 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN portal_datacomplain_tb.datacomplain_answer_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_datacomplain_tb.datacomplain_answer_usr_id IS '데이터민원 답변자';


--
-- TOC entry 3217 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN portal_datacomplain_tb.datacomplain_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_datacomplain_tb.datacomplain_id IS '데이터민원 ID';


--
-- TOC entry 3218 (class 0 OID 0)
-- Dependencies: 208
-- Name: COLUMN portal_datacomplain_tb.datacomplain_answer; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_datacomplain_tb.datacomplain_answer IS '데이터민원 답변';


--
-- TOC entry 209 (class 1259 OID 16573)
-- Name: portal_datacomplainfile_tb; Type: TABLE; Schema: portal; Owner: postgres
--

CREATE TABLE portal.portal_datacomplainfile_tb (
    dcp_file_oid bigint DEFAULT nextval('portal.portal_datacomplainfile_tb'::regclass) NOT NULL,
    datacomplain_oid bigint NOT NULL,
    dcp_file_org_nm character varying(128),
    dcp_file_saved_nm character varying(128),
    dcp_file_save_path character varying(128),
    dcp_file_size_byte bigint,
    dcp_file_id character varying(6),
    dcp_file_type_answer character varying(6)
);


ALTER TABLE portal.portal_datacomplainfile_tb OWNER TO postgres;

--
-- TOC entry 3220 (class 0 OID 0)
-- Dependencies: 209
-- Name: TABLE portal_datacomplainfile_tb; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON TABLE portal.portal_datacomplainfile_tb IS '데이터민원 파일 기본';


--
-- TOC entry 3221 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN portal_datacomplainfile_tb.dcp_file_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_datacomplainfile_tb.dcp_file_oid IS '데이터민원 파일 고유번호';


--
-- TOC entry 3222 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN portal_datacomplainfile_tb.datacomplain_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_datacomplainfile_tb.datacomplain_oid IS '데이터민원 고유번호';


--
-- TOC entry 3223 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN portal_datacomplainfile_tb.dcp_file_org_nm; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_datacomplainfile_tb.dcp_file_org_nm IS '데이터민원 원본파일명';


--
-- TOC entry 3224 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN portal_datacomplainfile_tb.dcp_file_saved_nm; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_datacomplainfile_tb.dcp_file_saved_nm IS '데이터민원 저장된파일명';


--
-- TOC entry 3225 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN portal_datacomplainfile_tb.dcp_file_save_path; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_datacomplainfile_tb.dcp_file_save_path IS '데이터민원 파일저장경로';


--
-- TOC entry 3226 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN portal_datacomplainfile_tb.dcp_file_size_byte; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_datacomplainfile_tb.dcp_file_size_byte IS '데이터민원 파일사이즈';


--
-- TOC entry 3227 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN portal_datacomplainfile_tb.dcp_file_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_datacomplainfile_tb.dcp_file_id IS '데이터민원 파일ID';


--
-- TOC entry 3228 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN portal_datacomplainfile_tb.dcp_file_type_answer; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_datacomplainfile_tb.dcp_file_type_answer IS '데이터민원 파일유형';


--
-- TOC entry 210 (class 1259 OID 16577)
-- Name: portal_ext_features; Type: TABLE; Schema: portal; Owner: postgres
--

CREATE TABLE portal.portal_ext_features (
    enabled_tf boolean NOT NULL,
    feature_cd character varying(35) NOT NULL
);


ALTER TABLE portal.portal_ext_features OWNER TO postgres;

--
-- TOC entry 3230 (class 0 OID 0)
-- Dependencies: 210
-- Name: TABLE portal_ext_features; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON TABLE portal.portal_ext_features IS '확장기능관리';


--
-- TOC entry 3231 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN portal_ext_features.enabled_tf; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_ext_features.enabled_tf IS '활성화여부';


--
-- TOC entry 3232 (class 0 OID 0)
-- Dependencies: 210
-- Name: COLUMN portal_ext_features.feature_cd; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_ext_features.feature_cd IS '확장기능코드';


--
-- TOC entry 211 (class 1259 OID 16580)
-- Name: portal_faq_tb; Type: TABLE; Schema: portal; Owner: postgres
--

CREATE TABLE portal.portal_faq_tb (
    faq_oid bigint NOT NULL,
    faq_cnt bigint,
    faq_title character varying(255),
    faq_cre_dt timestamp without time zone,
    faq_upt_dt timestamp without time zone,
    faq_answer character varying(4000),
    faq_question character varying(4000),
    faq_cre_usr_id character varying(32),
    faq_upt_usr_id character varying(32),
    faq_id character varying(6)
);


ALTER TABLE portal.portal_faq_tb OWNER TO postgres;

--
-- TOC entry 3234 (class 0 OID 0)
-- Dependencies: 211
-- Name: TABLE portal_faq_tb; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON TABLE portal.portal_faq_tb IS 'FAQ 관리 기본';


--
-- TOC entry 3235 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN portal_faq_tb.faq_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_faq_tb.faq_oid IS 'FAQ 고유번호';


--
-- TOC entry 3236 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN portal_faq_tb.faq_cnt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_faq_tb.faq_cnt IS 'FAQ 조회수';


--
-- TOC entry 3237 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN portal_faq_tb.faq_title; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_faq_tb.faq_title IS 'FAQ 제목';


--
-- TOC entry 3238 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN portal_faq_tb.faq_cre_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_faq_tb.faq_cre_dt IS 'FAQ 생성일';


--
-- TOC entry 3239 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN portal_faq_tb.faq_upt_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_faq_tb.faq_upt_dt IS 'FAQ 수정일';


--
-- TOC entry 3240 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN portal_faq_tb.faq_answer; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_faq_tb.faq_answer IS 'FAQ 답변';


--
-- TOC entry 3241 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN portal_faq_tb.faq_question; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_faq_tb.faq_question IS 'FAQ 질문';


--
-- TOC entry 3242 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN portal_faq_tb.faq_cre_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_faq_tb.faq_cre_usr_id IS 'FAQ 생성자';


--
-- TOC entry 3243 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN portal_faq_tb.faq_upt_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_faq_tb.faq_upt_usr_id IS 'FAQ 수정자';


--
-- TOC entry 3244 (class 0 OID 0)
-- Dependencies: 211
-- Name: COLUMN portal_faq_tb.faq_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_faq_tb.faq_id IS 'FAQ ID';


--
-- TOC entry 212 (class 1259 OID 16586)
-- Name: portal_faqfile_tb; Type: TABLE; Schema: portal; Owner: postgres
--

CREATE TABLE portal.portal_faqfile_tb (
    faqfile_oid bigint NOT NULL,
    faqfile_org_nm character varying(128),
    faqfile_saved_nm character varying(128),
    faqfile_save_path character varying(128),
    faqfile_size_byte bigint,
    faq_oid bigint NOT NULL,
    faqfile_id character varying(6)
);


ALTER TABLE portal.portal_faqfile_tb OWNER TO postgres;

--
-- TOC entry 3246 (class 0 OID 0)
-- Dependencies: 212
-- Name: TABLE portal_faqfile_tb; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON TABLE portal.portal_faqfile_tb IS 'FAQ 파일 기본';


--
-- TOC entry 3247 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN portal_faqfile_tb.faqfile_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_faqfile_tb.faqfile_oid IS 'FAQ 파일 고유번호';


--
-- TOC entry 3248 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN portal_faqfile_tb.faqfile_org_nm; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_faqfile_tb.faqfile_org_nm IS 'FAQ 원본파일명';


--
-- TOC entry 3249 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN portal_faqfile_tb.faqfile_saved_nm; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_faqfile_tb.faqfile_saved_nm IS 'FAQ 저장된파일명';


--
-- TOC entry 3250 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN portal_faqfile_tb.faqfile_save_path; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_faqfile_tb.faqfile_save_path IS 'FAQ 파일저장경로';


--
-- TOC entry 3251 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN portal_faqfile_tb.faqfile_size_byte; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_faqfile_tb.faqfile_size_byte IS 'FAQ 파일사이즈';


--
-- TOC entry 3252 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN portal_faqfile_tb.faq_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_faqfile_tb.faq_oid IS 'FAQ 고유번호';


--
-- TOC entry 3253 (class 0 OID 0)
-- Dependencies: 212
-- Name: COLUMN portal_faqfile_tb.faqfile_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_faqfile_tb.faqfile_id IS 'FAQ 파일ID';


--
-- TOC entry 213 (class 1259 OID 16589)
-- Name: portal_menu_tb; Type: TABLE; Schema: portal; Owner: postgres
--

CREATE TABLE portal.portal_menu_tb (
    menu_oid bigint NOT NULL,
    menu_id character varying(32) NOT NULL,
    menu_nm character varying(60) NOT NULL,
    menu_url character varying(128) NOT NULL,
    menu_cre_dt timestamp without time zone NOT NULL,
    menu_cre_usr_id character varying(32) NOT NULL,
    menu_upt_dt timestamp without time zone,
    menu_ord integer,
    menu_parent_id bigint,
    menu_gb_cd character varying(12),
    menu_param character varying(255),
    menu_use_fl character varying(11),
    menu_upt_usr_id character varying(32),
    program_grp_oid bigint
);


ALTER TABLE portal.portal_menu_tb OWNER TO postgres;

--
-- TOC entry 3255 (class 0 OID 0)
-- Dependencies: 213
-- Name: TABLE portal_menu_tb; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON TABLE portal.portal_menu_tb IS '메뉴 관리 기본';


--
-- TOC entry 3256 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN portal_menu_tb.menu_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_menu_tb.menu_oid IS '메뉴고유번호';


--
-- TOC entry 3257 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN portal_menu_tb.menu_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_menu_tb.menu_id IS '메뉴ID';


--
-- TOC entry 3258 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN portal_menu_tb.menu_nm; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_menu_tb.menu_nm IS '메뉴명';


--
-- TOC entry 3259 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN portal_menu_tb.menu_url; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_menu_tb.menu_url IS '메뉴URL';


--
-- TOC entry 3260 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN portal_menu_tb.menu_cre_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_menu_tb.menu_cre_dt IS '메뉴 생성일';


--
-- TOC entry 3261 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN portal_menu_tb.menu_cre_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_menu_tb.menu_cre_usr_id IS '메뉴 생성자';


--
-- TOC entry 3262 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN portal_menu_tb.menu_upt_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_menu_tb.menu_upt_dt IS '메뉴 수정일';


--
-- TOC entry 3263 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN portal_menu_tb.menu_ord; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_menu_tb.menu_ord IS '메뉴순서';


--
-- TOC entry 3264 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN portal_menu_tb.menu_parent_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_menu_tb.menu_parent_id IS '메뉴 상위고유번호';


--
-- TOC entry 3265 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN portal_menu_tb.menu_gb_cd; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_menu_tb.menu_gb_cd IS '메뉴 구분코드';


--
-- TOC entry 3266 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN portal_menu_tb.menu_param; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_menu_tb.menu_param IS '메뉴 PARAM';


--
-- TOC entry 3267 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN portal_menu_tb.menu_use_fl; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_menu_tb.menu_use_fl IS '메뉴 사용여부';


--
-- TOC entry 3268 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN portal_menu_tb.menu_upt_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_menu_tb.menu_upt_usr_id IS '메뉴 수정자';


--
-- TOC entry 3269 (class 0 OID 0)
-- Dependencies: 213
-- Name: COLUMN portal_menu_tb.program_grp_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_menu_tb.program_grp_oid IS '프로그램그룹 고유번호';


--
-- TOC entry 214 (class 1259 OID 16595)
-- Name: portal_notice_tb; Type: TABLE; Schema: portal; Owner: postgres
--

CREATE TABLE portal.portal_notice_tb (
    notice_oid bigint NOT NULL,
    notice_title character varying(255) NOT NULL,
    notice_content character varying(4000),
    notice_cnt bigint DEFAULT 0 NOT NULL,
    notice_first_fl character(1) DEFAULT 'N'::bpchar NOT NULL,
    notice_admin_fl character(1),
    notice_main_pop_fl character(1) DEFAULT 'N'::character varying NOT NULL,
    notice_main_start_dt timestamp without time zone,
    notice_main_end_dt timestamp without time zone,
    notice_main_pop_width bigint,
    notice_main_pop_height bigint,
    notice_main_pop_not_open_fl character(1),
    notice_cre_usr_id character varying(32),
    notice_cre_dt timestamp without time zone,
    notice_upt_usr_id character varying(32),
    notice_upt_dt timestamp without time zone,
    notice_id character varying(10) NOT NULL
);


ALTER TABLE portal.portal_notice_tb OWNER TO postgres;

--
-- TOC entry 3271 (class 0 OID 0)
-- Dependencies: 214
-- Name: TABLE portal_notice_tb; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON TABLE portal.portal_notice_tb IS '공지사항 관리 기본';


--
-- TOC entry 3272 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN portal_notice_tb.notice_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_notice_tb.notice_oid IS '공지사항 고유번호';


--
-- TOC entry 3273 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN portal_notice_tb.notice_title; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_notice_tb.notice_title IS '공지사항 제목';


--
-- TOC entry 3274 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN portal_notice_tb.notice_content; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_notice_tb.notice_content IS '공지사항 내용';


--
-- TOC entry 3275 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN portal_notice_tb.notice_cnt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_notice_tb.notice_cnt IS '공지사항 조회수';


--
-- TOC entry 3276 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN portal_notice_tb.notice_first_fl; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_notice_tb.notice_first_fl IS '공지사항 우선공지여부';


--
-- TOC entry 3277 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN portal_notice_tb.notice_admin_fl; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_notice_tb.notice_admin_fl IS '공지사항 관리자공지여부';


--
-- TOC entry 3278 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN portal_notice_tb.notice_main_pop_fl; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_notice_tb.notice_main_pop_fl IS '공지사항 메인팝업여부';


--
-- TOC entry 3279 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN portal_notice_tb.notice_main_start_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_notice_tb.notice_main_start_dt IS '공지사항 시작일';


--
-- TOC entry 3280 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN portal_notice_tb.notice_main_end_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_notice_tb.notice_main_end_dt IS '공지사항 종료일';


--
-- TOC entry 3281 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN portal_notice_tb.notice_main_pop_width; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_notice_tb.notice_main_pop_width IS '공지사항 팝업 너비';


--
-- TOC entry 3282 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN portal_notice_tb.notice_main_pop_height; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_notice_tb.notice_main_pop_height IS '공지사항 팝업 높이';


--
-- TOC entry 3283 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN portal_notice_tb.notice_main_pop_not_open_fl; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_notice_tb.notice_main_pop_not_open_fl IS '공지사항 금일미공지여부';


--
-- TOC entry 3284 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN portal_notice_tb.notice_cre_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_notice_tb.notice_cre_usr_id IS '공지사항 생성자';


--
-- TOC entry 3285 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN portal_notice_tb.notice_cre_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_notice_tb.notice_cre_dt IS '공지사항 생성일';


--
-- TOC entry 3286 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN portal_notice_tb.notice_upt_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_notice_tb.notice_upt_usr_id IS '공지사항 수정자';


--
-- TOC entry 3287 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN portal_notice_tb.notice_upt_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_notice_tb.notice_upt_dt IS '공지사항 수정일';


--
-- TOC entry 3288 (class 0 OID 0)
-- Dependencies: 214
-- Name: COLUMN portal_notice_tb.notice_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_notice_tb.notice_id IS '공지사항 ID';


--
-- TOC entry 215 (class 1259 OID 16604)
-- Name: portal_noticefile_tb; Type: TABLE; Schema: portal; Owner: postgres
--

CREATE TABLE portal.portal_noticefile_tb (
    noticefile_oid bigint NOT NULL,
    noticefile_org_nm character varying(160) NOT NULL,
    noticefile_saved_nm character varying(160) NOT NULL,
    noticefile_save_path character varying(160) NOT NULL,
    noticefile_size_byte bigint NOT NULL,
    notice_oid bigint NOT NULL
);


ALTER TABLE portal.portal_noticefile_tb OWNER TO postgres;

--
-- TOC entry 3290 (class 0 OID 0)
-- Dependencies: 215
-- Name: TABLE portal_noticefile_tb; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON TABLE portal.portal_noticefile_tb IS '공지사항 파일 기본';


--
-- TOC entry 3291 (class 0 OID 0)
-- Dependencies: 215
-- Name: COLUMN portal_noticefile_tb.noticefile_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_noticefile_tb.noticefile_oid IS '공지사항 파일고유번호';


--
-- TOC entry 3292 (class 0 OID 0)
-- Dependencies: 215
-- Name: COLUMN portal_noticefile_tb.noticefile_org_nm; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_noticefile_tb.noticefile_org_nm IS '공지사항 원본파일명';


--
-- TOC entry 3293 (class 0 OID 0)
-- Dependencies: 215
-- Name: COLUMN portal_noticefile_tb.noticefile_saved_nm; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_noticefile_tb.noticefile_saved_nm IS '공지사항 저장된파일명';


--
-- TOC entry 3294 (class 0 OID 0)
-- Dependencies: 215
-- Name: COLUMN portal_noticefile_tb.noticefile_save_path; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_noticefile_tb.noticefile_save_path IS '공지사항 파일저장경로';


--
-- TOC entry 3295 (class 0 OID 0)
-- Dependencies: 215
-- Name: COLUMN portal_noticefile_tb.noticefile_size_byte; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_noticefile_tb.noticefile_size_byte IS '공지사항 파일사이즈';


--
-- TOC entry 3296 (class 0 OID 0)
-- Dependencies: 215
-- Name: COLUMN portal_noticefile_tb.notice_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_noticefile_tb.notice_oid IS '공지사항 고유번호';


--
-- TOC entry 216 (class 1259 OID 16607)
-- Name: portal_program_tb; Type: TABLE; Schema: portal; Owner: postgres
--

CREATE TABLE portal.portal_program_tb (
    program_oid bigint NOT NULL,
    program_url character varying(128) NOT NULL,
    program_nm character varying(255) NOT NULL,
    program_desc character varying(4000) NOT NULL,
    program_cre_dt timestamp without time zone NOT NULL,
    program_upt_dt timestamp without time zone,
    program_cre_usr_id character varying(32) NOT NULL,
    program_upt_usr_id character varying(32),
    programgroup_oid bigint NOT NULL,
    program_division character varying(32),
    program_use_fl character(1) NOT NULL,
    program_use_token_yn character(1) NOT NULL
);


ALTER TABLE portal.portal_program_tb OWNER TO postgres;

--
-- TOC entry 3298 (class 0 OID 0)
-- Dependencies: 216
-- Name: TABLE portal_program_tb; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON TABLE portal.portal_program_tb IS '프로그램 관리 기본';


--
-- TOC entry 3299 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN portal_program_tb.program_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_program_tb.program_oid IS '프로그램 고유번호';


--
-- TOC entry 3300 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN portal_program_tb.program_url; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_program_tb.program_url IS '프로그램 URL';


--
-- TOC entry 3301 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN portal_program_tb.program_nm; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_program_tb.program_nm IS '프로그램명';


--
-- TOC entry 3302 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN portal_program_tb.program_desc; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_program_tb.program_desc IS '프로그램 설명';


--
-- TOC entry 3303 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN portal_program_tb.program_cre_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_program_tb.program_cre_dt IS '프로그램 생성일';


--
-- TOC entry 3304 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN portal_program_tb.program_upt_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_program_tb.program_upt_dt IS '프로그램 수정일';


--
-- TOC entry 3305 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN portal_program_tb.program_cre_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_program_tb.program_cre_usr_id IS '프로그램 생성자';


--
-- TOC entry 3306 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN portal_program_tb.program_upt_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_program_tb.program_upt_usr_id IS '프로그램 수정자';


--
-- TOC entry 3307 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN portal_program_tb.programgroup_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_program_tb.programgroup_oid IS '프로그램그룹 고유번호';


--
-- TOC entry 3308 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN portal_program_tb.program_division; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_program_tb.program_division IS '프로그램 구분코드';


--
-- TOC entry 3309 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN portal_program_tb.program_use_fl; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_program_tb.program_use_fl IS '프로그램 사용여부';


--
-- TOC entry 3310 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN portal_program_tb.program_use_token_yn; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_program_tb.program_use_token_yn IS '프로그램 인가여부';


--
-- TOC entry 217 (class 1259 OID 16613)
-- Name: portal_programgroup_tb; Type: TABLE; Schema: portal; Owner: postgres
--

CREATE TABLE portal.portal_programgroup_tb (
    programgroup_oid bigint NOT NULL,
    programgroup_nm character varying(128) NOT NULL,
    programgroup_cre_dt timestamp without time zone NOT NULL,
    programgroup_cre_usr_id character varying(32) NOT NULL,
    programgroup_desc character varying(4000),
    programgroup_upt_dt timestamp without time zone,
    programgroup_upt_usr_id character varying(32),
    programgroup_id character varying(10) NOT NULL,
    programgroup_use_fl character(1) DEFAULT 'N'::bpchar NOT NULL
);


ALTER TABLE portal.portal_programgroup_tb OWNER TO postgres;

--
-- TOC entry 3312 (class 0 OID 0)
-- Dependencies: 217
-- Name: TABLE portal_programgroup_tb; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON TABLE portal.portal_programgroup_tb IS '프로그램그룹 관리 기본';


--
-- TOC entry 3313 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN portal_programgroup_tb.programgroup_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_programgroup_tb.programgroup_oid IS '프로그램그룹 고유번호';


--
-- TOC entry 3314 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN portal_programgroup_tb.programgroup_nm; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_programgroup_tb.programgroup_nm IS '프로그램그룹명';


--
-- TOC entry 3315 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN portal_programgroup_tb.programgroup_cre_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_programgroup_tb.programgroup_cre_dt IS '프로그램그룹 생성일';


--
-- TOC entry 3316 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN portal_programgroup_tb.programgroup_cre_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_programgroup_tb.programgroup_cre_usr_id IS '프로그램그룹 생성자';


--
-- TOC entry 3317 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN portal_programgroup_tb.programgroup_desc; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_programgroup_tb.programgroup_desc IS '프로그램그룹 설명';


--
-- TOC entry 3318 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN portal_programgroup_tb.programgroup_upt_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_programgroup_tb.programgroup_upt_dt IS '프로그램그룹 수정일';


--
-- TOC entry 3319 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN portal_programgroup_tb.programgroup_upt_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_programgroup_tb.programgroup_upt_usr_id IS '프로그램그룹 수정자';


--
-- TOC entry 3320 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN portal_programgroup_tb.programgroup_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_programgroup_tb.programgroup_id IS '프로그램그룹 ID';


--
-- TOC entry 3321 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN portal_programgroup_tb.programgroup_use_fl; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_programgroup_tb.programgroup_use_fl IS '프로그램그룹 사용여부';


--
-- TOC entry 218 (class 1259 OID 16620)
-- Name: portal_qna_tb; Type: TABLE; Schema: portal; Owner: postgres
--

CREATE TABLE portal.portal_qna_tb (
    qna_oid bigint NOT NULL,
    qna_title character varying(255),
    qna_question character varying(4000),
    qna_answer character varying(4000),
    qna_status character varying(60) NOT NULL,
    qna_question_type_cd character varying(255),
    qna_cnt bigint DEFAULT 0 NOT NULL,
    qna_question_usr_id character varying(32),
    qna_question_dt timestamp without time zone,
    qna_answer_usr_id character varying(32),
    qna_answer_dt timestamp without time zone,
    qna_id character varying(10) NOT NULL
);


ALTER TABLE portal.portal_qna_tb OWNER TO postgres;

--
-- TOC entry 3323 (class 0 OID 0)
-- Dependencies: 218
-- Name: TABLE portal_qna_tb; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON TABLE portal.portal_qna_tb IS 'QNA 관리 기본';


--
-- TOC entry 3324 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN portal_qna_tb.qna_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_qna_tb.qna_oid IS 'QNA 고유번호';


--
-- TOC entry 3325 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN portal_qna_tb.qna_title; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_qna_tb.qna_title IS 'QNA 제목';


--
-- TOC entry 3326 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN portal_qna_tb.qna_question; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_qna_tb.qna_question IS 'QNA 질문';


--
-- TOC entry 3327 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN portal_qna_tb.qna_answer; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_qna_tb.qna_answer IS 'QNA 답변';


--
-- TOC entry 3328 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN portal_qna_tb.qna_status; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_qna_tb.qna_status IS 'QNA 상태코드';


--
-- TOC entry 3329 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN portal_qna_tb.qna_question_type_cd; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_qna_tb.qna_question_type_cd IS 'QNA 질문 유형';


--
-- TOC entry 3330 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN portal_qna_tb.qna_cnt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_qna_tb.qna_cnt IS 'QNA 조회수';


--
-- TOC entry 3331 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN portal_qna_tb.qna_question_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_qna_tb.qna_question_usr_id IS 'QNA 질문자';


--
-- TOC entry 3332 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN portal_qna_tb.qna_question_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_qna_tb.qna_question_dt IS 'QNA 질문일';


--
-- TOC entry 3333 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN portal_qna_tb.qna_answer_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_qna_tb.qna_answer_usr_id IS 'QNA 답변자';


--
-- TOC entry 3334 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN portal_qna_tb.qna_answer_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_qna_tb.qna_answer_dt IS 'QNA 답변일';


--
-- TOC entry 3335 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN portal_qna_tb.qna_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_qna_tb.qna_id IS 'QNA ID';


--
-- TOC entry 219 (class 1259 OID 16627)
-- Name: portal_qnafile_tb; Type: TABLE; Schema: portal; Owner: postgres
--

CREATE TABLE portal.portal_qnafile_tb (
    qnafile_oid bigint NOT NULL,
    qnafile_org_nm character varying(128) NOT NULL,
    qnafile_save_nm character varying(128) NOT NULL,
    qnafile_save_path character varying(128) NOT NULL,
    qnafile_size bigint NOT NULL,
    qna_oid bigint NOT NULL
);


ALTER TABLE portal.portal_qnafile_tb OWNER TO postgres;

--
-- TOC entry 3337 (class 0 OID 0)
-- Dependencies: 219
-- Name: TABLE portal_qnafile_tb; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON TABLE portal.portal_qnafile_tb IS 'QNA 파일 기본';


--
-- TOC entry 3338 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN portal_qnafile_tb.qnafile_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_qnafile_tb.qnafile_oid IS 'QNA 파일 고유번호';


--
-- TOC entry 3339 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN portal_qnafile_tb.qnafile_org_nm; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_qnafile_tb.qnafile_org_nm IS 'QNA 원본파일명';


--
-- TOC entry 3340 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN portal_qnafile_tb.qnafile_save_nm; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_qnafile_tb.qnafile_save_nm IS 'QNA 저장된파일명';


--
-- TOC entry 3341 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN portal_qnafile_tb.qnafile_save_path; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_qnafile_tb.qnafile_save_path IS 'QNA 파일저장경로';


--
-- TOC entry 3342 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN portal_qnafile_tb.qnafile_size; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_qnafile_tb.qnafile_size IS 'QNA 파일사이즈';


--
-- TOC entry 3343 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN portal_qnafile_tb.qna_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_qnafile_tb.qna_oid IS 'QNA 고유번호';


--
-- TOC entry 220 (class 1259 OID 16630)
-- Name: portal_review_datasetmap_tb; Type: TABLE; Schema: portal; Owner: postgres
--

CREATE TABLE portal.portal_review_datasetmap_tb (
    ds_oid character varying(20) NOT NULL,
    review_oid bigint NOT NULL
);


ALTER TABLE portal.portal_review_datasetmap_tb OWNER TO postgres;

--
-- TOC entry 3345 (class 0 OID 0)
-- Dependencies: 220
-- Name: TABLE portal_review_datasetmap_tb; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON TABLE portal.portal_review_datasetmap_tb IS '활용사례 데이터셋연결정보';


--
-- TOC entry 3346 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN portal_review_datasetmap_tb.ds_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_review_datasetmap_tb.ds_oid IS '데이터셋 고유번호';


--
-- TOC entry 3347 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN portal_review_datasetmap_tb.review_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_review_datasetmap_tb.review_oid IS '활용사례 고유번호';


--
-- TOC entry 221 (class 1259 OID 16633)
-- Name: portal_review_tb; Type: TABLE; Schema: portal; Owner: postgres
--

CREATE TABLE portal.portal_review_tb (
    review_oid bigint NOT NULL,
    review_title character varying(255),
    review_desc character varying(4000),
    review_purpose character varying(1000),
    review_developer_id character varying(20),
    review_open_dt timestamp without time zone,
    review_dev_type character varying(50),
    review_cre_dt timestamp without time zone,
    review_developer_type character varying(50),
    review_developer_loc character varying(50),
    review_src character varying(100),
    review_cre_usr_id character varying(32),
    review_upt_usr_id character varying(32),
    review_upt_dt timestamp without time zone
);


ALTER TABLE portal.portal_review_tb OWNER TO postgres;

--
-- TOC entry 3349 (class 0 OID 0)
-- Dependencies: 221
-- Name: TABLE portal_review_tb; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON TABLE portal.portal_review_tb IS '활용사례 기본';


--
-- TOC entry 3350 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN portal_review_tb.review_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_review_tb.review_oid IS '활용후기고유번호';


--
-- TOC entry 3351 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN portal_review_tb.review_title; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_review_tb.review_title IS '활용후기제목';


--
-- TOC entry 3352 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN portal_review_tb.review_desc; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_review_tb.review_desc IS '활용후기설명';


--
-- TOC entry 3353 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN portal_review_tb.review_purpose; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_review_tb.review_purpose IS '활용후기활용목적';


--
-- TOC entry 3354 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN portal_review_tb.review_developer_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_review_tb.review_developer_id IS '활용후기개발자';


--
-- TOC entry 3355 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN portal_review_tb.review_open_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_review_tb.review_open_dt IS '활용후기서비스개시일';


--
-- TOC entry 3356 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN portal_review_tb.review_dev_type; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_review_tb.review_dev_type IS '활용후기개발유형';


--
-- TOC entry 3357 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN portal_review_tb.review_cre_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_review_tb.review_cre_dt IS '활용후기등록일';


--
-- TOC entry 3358 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN portal_review_tb.review_developer_type; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_review_tb.review_developer_type IS '활용후기개발자유형';


--
-- TOC entry 3359 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN portal_review_tb.review_developer_loc; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_review_tb.review_developer_loc IS '활용후기개발자소재지';


--
-- TOC entry 3360 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN portal_review_tb.review_src; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_review_tb.review_src IS '서비스 홈페이지 주소';


--
-- TOC entry 3361 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN portal_review_tb.review_cre_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_review_tb.review_cre_usr_id IS '등록자ID';


--
-- TOC entry 3362 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN portal_review_tb.review_upt_usr_id; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_review_tb.review_upt_usr_id IS '수정자ID';


--
-- TOC entry 3363 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN portal_review_tb.review_upt_dt; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_review_tb.review_upt_dt IS '수정일시';


--
-- TOC entry 222 (class 1259 OID 16639)
-- Name: portal_reviewfile_tb; Type: TABLE; Schema: portal; Owner: postgres
--

CREATE TABLE portal.portal_reviewfile_tb (
    reviewfile_oid bigint NOT NULL,
    reviewfile_flag character varying(32),
    reviewfile_psc_name character varying(128),
    reviewfile_lsc_name character varying(128),
    reviewfile_file_path character varying(128),
    reviewfile_file_size bigint,
    review_oid bigint
);


ALTER TABLE portal.portal_reviewfile_tb OWNER TO postgres;

--
-- TOC entry 3365 (class 0 OID 0)
-- Dependencies: 222
-- Name: TABLE portal_reviewfile_tb; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON TABLE portal.portal_reviewfile_tb IS '활용사례 파일 기본';


--
-- TOC entry 3366 (class 0 OID 0)
-- Dependencies: 222
-- Name: COLUMN portal_reviewfile_tb.reviewfile_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_reviewfile_tb.reviewfile_oid IS '활용사례 파일고유번호';


--
-- TOC entry 3367 (class 0 OID 0)
-- Dependencies: 222
-- Name: COLUMN portal_reviewfile_tb.reviewfile_flag; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_reviewfile_tb.reviewfile_flag IS '활용사례 파일구분';


--
-- TOC entry 3368 (class 0 OID 0)
-- Dependencies: 222
-- Name: COLUMN portal_reviewfile_tb.reviewfile_psc_name; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_reviewfile_tb.reviewfile_psc_name IS '활용사례 물리파일명';


--
-- TOC entry 3369 (class 0 OID 0)
-- Dependencies: 222
-- Name: COLUMN portal_reviewfile_tb.reviewfile_lsc_name; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_reviewfile_tb.reviewfile_lsc_name IS '활용사례 논리파일명';


--
-- TOC entry 3370 (class 0 OID 0)
-- Dependencies: 222
-- Name: COLUMN portal_reviewfile_tb.reviewfile_file_path; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_reviewfile_tb.reviewfile_file_path IS '활용사례 파일경로';


--
-- TOC entry 3371 (class 0 OID 0)
-- Dependencies: 222
-- Name: COLUMN portal_reviewfile_tb.reviewfile_file_size; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_reviewfile_tb.reviewfile_file_size IS '활용사례 파일사이즈';


--
-- TOC entry 3372 (class 0 OID 0)
-- Dependencies: 222
-- Name: COLUMN portal_reviewfile_tb.review_oid; Type: COMMENT; Schema: portal; Owner: postgres
--

COMMENT ON COLUMN portal.portal_reviewfile_tb.review_oid IS '활용사례 고유번호';


--
-- TOC entry 223 (class 1259 OID 16642)
-- Name: seq_portal_authgroup_tb; Type: SEQUENCE; Schema: portal; Owner: postgres
--

CREATE SEQUENCE portal.seq_portal_authgroup_tb
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE portal.seq_portal_authgroup_tb OWNER TO postgres;

--
-- TOC entry 3374 (class 0 OID 0)
-- Dependencies: 223
-- Name: seq_portal_authgroup_tb; Type: SEQUENCE OWNED BY; Schema: portal; Owner: postgres
--

ALTER SEQUENCE portal.seq_portal_authgroup_tb OWNED BY portal.portal_authgroup_tb.authgroup_id;


--
-- TOC entry 224 (class 1259 OID 16644)
-- Name: seq_portal_codegroup_tb; Type: SEQUENCE; Schema: portal; Owner: postgres
--

CREATE SEQUENCE portal.seq_portal_codegroup_tb
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE portal.seq_portal_codegroup_tb OWNER TO postgres;

--
-- TOC entry 3375 (class 0 OID 0)
-- Dependencies: 224
-- Name: seq_portal_codegroup_tb; Type: SEQUENCE OWNED BY; Schema: portal; Owner: postgres
--

ALTER SEQUENCE portal.seq_portal_codegroup_tb OWNED BY portal.portal_codegroup_tb.codegroup_id;


--
-- TOC entry 225 (class 1259 OID 16646)
-- Name: seq_portal_datacomplain_tb; Type: SEQUENCE; Schema: portal; Owner: postgres
--

CREATE SEQUENCE portal.seq_portal_datacomplain_tb
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE portal.seq_portal_datacomplain_tb OWNER TO postgres;

--
-- TOC entry 3376 (class 0 OID 0)
-- Dependencies: 225
-- Name: seq_portal_datacomplain_tb; Type: SEQUENCE OWNED BY; Schema: portal; Owner: postgres
--

ALTER SEQUENCE portal.seq_portal_datacomplain_tb OWNED BY portal.portal_datacomplain_tb.datacomplain_id;


--
-- TOC entry 226 (class 1259 OID 16648)
-- Name: seq_portal_datacomplainfile_tb; Type: SEQUENCE; Schema: portal; Owner: postgres
--

CREATE SEQUENCE portal.seq_portal_datacomplainfile_tb
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE portal.seq_portal_datacomplainfile_tb OWNER TO postgres;

--
-- TOC entry 3377 (class 0 OID 0)
-- Dependencies: 226
-- Name: seq_portal_datacomplainfile_tb; Type: SEQUENCE OWNED BY; Schema: portal; Owner: postgres
--

ALTER SEQUENCE portal.seq_portal_datacomplainfile_tb OWNED BY portal.portal_datacomplainfile_tb.dcp_file_id;


--
-- TOC entry 227 (class 1259 OID 16650)
-- Name: seq_portal_faq_tb; Type: SEQUENCE; Schema: portal; Owner: postgres
--

CREATE SEQUENCE portal.seq_portal_faq_tb
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE portal.seq_portal_faq_tb OWNER TO postgres;

--
-- TOC entry 3378 (class 0 OID 0)
-- Dependencies: 227
-- Name: seq_portal_faq_tb; Type: SEQUENCE OWNED BY; Schema: portal; Owner: postgres
--

ALTER SEQUENCE portal.seq_portal_faq_tb OWNED BY portal.portal_faq_tb.faq_id;


--
-- TOC entry 228 (class 1259 OID 16652)
-- Name: seq_portal_faqfile_tb; Type: SEQUENCE; Schema: portal; Owner: postgres
--

CREATE SEQUENCE portal.seq_portal_faqfile_tb
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE portal.seq_portal_faqfile_tb OWNER TO postgres;

--
-- TOC entry 3379 (class 0 OID 0)
-- Dependencies: 228
-- Name: seq_portal_faqfile_tb; Type: SEQUENCE OWNED BY; Schema: portal; Owner: postgres
--

ALTER SEQUENCE portal.seq_portal_faqfile_tb OWNED BY portal.portal_faqfile_tb.faqfile_id;


--
-- TOC entry 229 (class 1259 OID 16654)
-- Name: seq_portal_menu_tb; Type: SEQUENCE; Schema: portal; Owner: postgres
--

CREATE SEQUENCE portal.seq_portal_menu_tb
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE portal.seq_portal_menu_tb OWNER TO postgres;

--
-- TOC entry 3380 (class 0 OID 0)
-- Dependencies: 229
-- Name: seq_portal_menu_tb; Type: SEQUENCE OWNED BY; Schema: portal; Owner: postgres
--

ALTER SEQUENCE portal.seq_portal_menu_tb OWNED BY portal.portal_menu_tb.menu_oid;


--
-- TOC entry 230 (class 1259 OID 16656)
-- Name: seq_portal_notice_tb; Type: SEQUENCE; Schema: portal; Owner: postgres
--

CREATE SEQUENCE portal.seq_portal_notice_tb
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE portal.seq_portal_notice_tb OWNER TO postgres;

--
-- TOC entry 3381 (class 0 OID 0)
-- Dependencies: 230
-- Name: seq_portal_notice_tb; Type: SEQUENCE OWNED BY; Schema: portal; Owner: postgres
--

ALTER SEQUENCE portal.seq_portal_notice_tb OWNED BY portal.portal_notice_tb.notice_oid;


--
-- TOC entry 231 (class 1259 OID 16658)
-- Name: seq_portal_program_tb; Type: SEQUENCE; Schema: portal; Owner: postgres
--

CREATE SEQUENCE portal.seq_portal_program_tb
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE portal.seq_portal_program_tb OWNER TO postgres;

--
-- TOC entry 3382 (class 0 OID 0)
-- Dependencies: 231
-- Name: seq_portal_program_tb; Type: SEQUENCE OWNED BY; Schema: portal; Owner: postgres
--

ALTER SEQUENCE portal.seq_portal_program_tb OWNED BY portal.portal_program_tb.program_oid;


--
-- TOC entry 232 (class 1259 OID 16660)
-- Name: seq_portal_programgroup_tb; Type: SEQUENCE; Schema: portal; Owner: postgres
--

CREATE SEQUENCE portal.seq_portal_programgroup_tb
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE portal.seq_portal_programgroup_tb OWNER TO postgres;

--
-- TOC entry 3383 (class 0 OID 0)
-- Dependencies: 232
-- Name: seq_portal_programgroup_tb; Type: SEQUENCE OWNED BY; Schema: portal; Owner: postgres
--

ALTER SEQUENCE portal.seq_portal_programgroup_tb OWNED BY portal.portal_programgroup_tb.programgroup_oid;


--
-- TOC entry 233 (class 1259 OID 16662)
-- Name: seq_portal_qna_tb; Type: SEQUENCE; Schema: portal; Owner: postgres
--

CREATE SEQUENCE portal.seq_portal_qna_tb
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE portal.seq_portal_qna_tb OWNER TO postgres;

--
-- TOC entry 3384 (class 0 OID 0)
-- Dependencies: 233
-- Name: seq_portal_qna_tb; Type: SEQUENCE OWNED BY; Schema: portal; Owner: postgres
--

ALTER SEQUENCE portal.seq_portal_qna_tb OWNED BY portal.portal_qna_tb.qna_oid;


--
-- TOC entry 234 (class 1259 OID 16664)
-- Name: seq_portal_qnafile_tb; Type: SEQUENCE; Schema: portal; Owner: postgres
--

CREATE SEQUENCE portal.seq_portal_qnafile_tb
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE portal.seq_portal_qnafile_tb OWNER TO postgres;

--
-- TOC entry 3385 (class 0 OID 0)
-- Dependencies: 234
-- Name: seq_portal_qnafile_tb; Type: SEQUENCE OWNED BY; Schema: portal; Owner: postgres
--

ALTER SEQUENCE portal.seq_portal_qnafile_tb OWNED BY portal.portal_qnafile_tb.qnafile_oid;


--
-- TOC entry 2921 (class 2604 OID 16666)
-- Name: portal_authgroup_tb authgroup_id; Type: DEFAULT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_authgroup_tb ALTER COLUMN authgroup_id SET DEFAULT nextval('portal.seq_portal_authgroup_tb'::regclass);


--
-- TOC entry 2923 (class 2604 OID 16667)
-- Name: portal_codegroup_tb codegroup_id; Type: DEFAULT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_codegroup_tb ALTER COLUMN codegroup_id SET DEFAULT nextval('portal.seq_portal_codegroup_tb'::regclass);


--
-- TOC entry 2925 (class 2604 OID 16668)
-- Name: portal_datacomplain_tb datacomplain_id; Type: DEFAULT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_datacomplain_tb ALTER COLUMN datacomplain_id SET DEFAULT nextval('portal.seq_portal_datacomplain_tb'::regclass);


--
-- TOC entry 2927 (class 2604 OID 16669)
-- Name: portal_datacomplainfile_tb dcp_file_id; Type: DEFAULT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_datacomplainfile_tb ALTER COLUMN dcp_file_id SET DEFAULT nextval('portal.seq_portal_datacomplainfile_tb'::regclass);


--
-- TOC entry 2928 (class 2604 OID 16670)
-- Name: portal_faq_tb faq_id; Type: DEFAULT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_faq_tb ALTER COLUMN faq_id SET DEFAULT nextval('portal.seq_portal_faq_tb'::regclass);


--
-- TOC entry 2929 (class 2604 OID 16671)
-- Name: portal_faqfile_tb faqfile_id; Type: DEFAULT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_faqfile_tb ALTER COLUMN faqfile_id SET DEFAULT nextval('portal.seq_portal_faqfile_tb'::regclass);


--
-- TOC entry 2933 (class 2604 OID 16672)
-- Name: portal_notice_tb notice_id; Type: DEFAULT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_notice_tb ALTER COLUMN notice_id SET DEFAULT nextval('portal.seq_portal_notice_tb'::regclass);


--
-- TOC entry 2935 (class 2604 OID 16673)
-- Name: portal_programgroup_tb programgroup_id; Type: DEFAULT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_programgroup_tb ALTER COLUMN programgroup_id SET DEFAULT nextval('portal.seq_portal_programgroup_tb'::regclass);


--
-- TOC entry 2937 (class 2604 OID 16674)
-- Name: portal_qna_tb qna_id; Type: DEFAULT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_qna_tb ALTER COLUMN qna_id SET DEFAULT nextval('portal.seq_portal_qna_tb'::regclass);


--
-- TOC entry 2938 (class 2604 OID 16675)
-- Name: portal_qnafile_tb qnafile_oid; Type: DEFAULT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_qnafile_tb ALTER COLUMN qnafile_oid SET DEFAULT nextval('portal.seq_portal_qnafile_tb'::regclass);


--
-- TOC entry 3107 (class 0 OID 16534)
-- Dependencies: 202
-- Data for Name: portal_authgroup_tb; Type: TABLE DATA; Schema: portal; Owner: postgres
--

INSERT INTO portal.portal_authgroup_tb VALUES (1565657071450001, 'ADMIN 관리자 권한', 'adminPortal', 'Y', '2019-08-13 09:44:33', 'cityhub08', 'ADMIN 관리자 권한', 'cityhub08', '2020-07-28 17:05:34.944531', '9');


--
-- TOC entry 3108 (class 0 OID 16541)
-- Dependencies: 203
-- Data for Name: portal_authgroupmenumap_tb; Type: TABLE DATA; Schema: portal; Owner: postgres
--

INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640004, 1573013323538001, 'Y', 'Y', 'Y', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640006, 1573013323538001, 'Y', 'Y', 'Y', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640008, 1573013323538001, 'Y', 'Y', 'Y', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640010, 1573013323538001, 'Y', 'Y', 'Y', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640011, 1573013323538001, 'Y', 'Y', 'Y', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640012, 1573013323538001, 'Y', 'Y', 'Y', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640013, 1573013323538001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640014, 1573013323538001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640015, 1573013323538001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1570669105404001, 1573013323538001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1570409767702001, 1573013323538001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1570669537147001, 1573013323538001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640004, 1573088059263001, 'Y', 'Y', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640006, 1573088059263001, 'Y', 'Y', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640008, 1573088059263001, 'Y', 'Y', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640010, 1573088059263001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640011, 1573088059263001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640012, 1573088059263001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640013, 1573088059263001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640004, 1573014674038001, 'Y', 'Y', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640006, 1573014674038001, 'Y', 'Y', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640008, 1573014674038001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640010, 1573014674038001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640011, 1573014674038001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640012, 1573014674038001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640013, 1573014674038001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640014, 1573014674038001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640015, 1573014674038001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1570669105404001, 1573014674038001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1570409767702001, 1573014674038001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1570669537147001, 1573014674038001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640014, 1573088059263001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640015, 1573088059263001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1570669105404001, 1573088059263001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1570409767702001, 1573088059263001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1570669537147001, 1573088059263001, 'N', 'N', 'N', 'N');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640004, 1565657071450001, 'Y', 'Y', 'Y', 'Y');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640006, 1565657071450001, 'Y', 'Y', 'Y', 'Y');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640008, 1565657071450001, 'Y', 'Y', 'Y', 'Y');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1570669105404001, 1565657071450001, 'Y', 'Y', 'Y', 'Y');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640010, 1565657071450001, 'Y', 'Y', 'Y', 'Y');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1574315118648001, 1565657071450001, 'Y', 'Y', 'Y', 'Y');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640011, 1565657071450001, 'Y', 'Y', 'Y', 'Y');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640012, 1565657071450001, 'Y', 'Y', 'Y', 'Y');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640013, 1565657071450001, 'Y', 'Y', 'Y', 'Y');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640014, 1565657071450001, 'Y', 'Y', 'Y', 'Y');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1565247635640015, 1565657071450001, 'Y', 'Y', 'Y', 'Y');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1603964564494001, 1565657071450001, 'Y', 'Y', 'Y', 'Y');
INSERT INTO portal.portal_authgroupmenumap_tb VALUES (1603964605853001, 1565657071450001, 'Y', 'Y', 'Y', 'Y');


--
-- TOC entry 3109 (class 0 OID 16544)
-- Dependencies: 204
-- Data for Name: portal_authgroupusermap_tb; Type: TABLE DATA; Schema: portal; Owner: postgres
--

INSERT INTO portal.portal_authgroupusermap_tb VALUES (1565657071450001, 'cityhub08');


--
-- TOC entry 3111 (class 0 OID 16554)
-- Dependencies: 206
-- Data for Name: portal_code_tb; Type: TABLE DATA; Schema: portal; Owner: postgres
--

INSERT INTO portal.portal_code_tb VALUES ('image', '대표이미지', 'Y', '2019-09-26 15:35:38', 'cityhub08', 10, '대표이미지', 'cityhub08', '2020-07-01 16:58:58', 'CG_00018');
INSERT INTO portal.portal_code_tb VALUES ('file', '일반파일', 'Y', '2019-09-26 15:35:51', 'cityhub08', 20, '일반파일', 'cityhub08', '2020-07-01 16:58:58', 'CG_00018');
INSERT INTO portal.portal_code_tb VALUES ('ccBy', 'CC BY', 'Y', '2020-07-15 14:02:00', 'cityhub08', 10, 'CC BY', 'cityhub08', '2020-07-15 14:02:00', 'CG_00032');
INSERT INTO portal.portal_code_tb VALUES ('ccBySa', 'CC BY-SA', 'Y', '2020-07-15 14:02:00', 'cityhub08', 20, 'CC BY-SA', 'cityhub08', '2020-07-15 14:02:00', 'CG_00032');
INSERT INTO portal.portal_code_tb VALUES ('ccByNd', 'CC BY-ND', 'Y', '2020-07-15 14:02:00', 'cityhub08', 30, 'CC BY-ND', 'cityhub08', '2020-07-15 14:02:00', 'CG_00032');
INSERT INTO portal.portal_code_tb VALUES ('ccByNc', 'CC BY-NC', 'Y', '2020-07-15 14:02:00', 'cityhub08', 40, 'CC BY-NC', 'cityhub08', '2020-07-15 14:02:00', 'CG_00032');
INSERT INTO portal.portal_code_tb VALUES ('ccByNcSa', 'CC BY-NC-SA', 'Y', '2020-07-15 14:02:00', 'cityhub08', 50, 'CC BY-NC-SA', 'cityhub08', '2020-07-15 14:02:00', 'CG_00032');
INSERT INTO portal.portal_code_tb VALUES ('ccByNcNd', 'CC BY-NC-ND', 'Y', '2020-07-15 14:02:00', 'cityhub08', 60, 'CC BY-NC-ND', 'cityhub08', '2020-07-15 14:02:00', 'CG_00032');
INSERT INTO portal.portal_code_tb VALUES ('7', '7일', 'Y', '2020-11-09 10:16:41.793757', 'cityhub08', 20, '7일', NULL, NULL, 'CG_00037');
INSERT INTO portal.portal_code_tb VALUES ('mashupData', '파생데이터', 'Y', '2019-10-15 16:30:21.990427', 'cityhub08', 20, '파생데이터', 'cityhub08', '2020-07-30 17:30:09.770052', 'CG_00021');
INSERT INTO portal.portal_code_tb VALUES ('empty', '없음', 'Y', '2020-10-27 19:27:24.396668', 'cityhub08', 10, '없음', NULL, NULL, 'CG_00033');
INSERT INTO portal.portal_code_tb VALUES ('pay_complete', '결제 완료', 'Y', '2020-11-09 10:13:49.415236', 'cityhub08', 20, '결제 완료', NULL, NULL, 'CG_00035');
INSERT INTO portal.portal_code_tb VALUES ('pay_error', '결제 오류', 'Y', '2020-11-09 10:14:02.678261', 'cityhub08', 30, '결제 오류', NULL, NULL, 'CG_00035');
INSERT INTO portal.portal_code_tb VALUES ('pay_cancel', '결제 취소', 'Y', '2020-11-09 10:14:18.444027', 'cityhub08', 40, '결제 취소', NULL, NULL, 'CG_00035');
INSERT INTO portal.portal_code_tb VALUES ('pay_request', '환불 요청', 'Y', '2020-11-09 10:15:08.312181', 'cityhub08', 10, '환불 요청', NULL, NULL, 'CG_00036');
INSERT INTO portal.portal_code_tb VALUES ('30', '30일', 'Y', '2020-11-09 10:16:53.959417', 'cityhub08', 30, '30일', NULL, NULL, 'CG_00037');
INSERT INTO portal.portal_code_tb VALUES ('90', '90일', 'Y', '2020-11-09 10:17:11.014835', 'cityhub08', 40, '90일', NULL, NULL, 'CG_00037');
INSERT INTO portal.portal_code_tb VALUES ('datasetPayments', '데이터셋 유료결제 기능', 'Y', '2020-11-09 10:46:28.980359', 'cityhub08', 10, '데이터셋 유료결제 기능', NULL, NULL, 'CG_00038');
INSERT INTO portal.portal_code_tb VALUES ('limit', '제한', 'Y', '2020-11-09 10:11:54.419317', 'cityhub08', 10, '제한', NULL, NULL, 'CG_00034');
INSERT INTO portal.portal_code_tb VALUES ('releaseReject', '출시요청반려', 'Y', '2019-09-26 15:29:47', 'cityhub08', 60, '출시요청반려', 'cityhub08', '2020-07-01 16:58:58', 'CG_00017');
INSERT INTO portal.portal_code_tb VALUES ('ondemand', 'polling', 'Y', '2020-11-26 14:15:36.92941', 'cityhub08', 10, 'polling', NULL, NULL, 'CG_00039');
INSERT INTO portal.portal_code_tb VALUES ('write', '쓰기', 'Y', '2019-07-29 16:34:14', 'cityhub08', 10, '등록 페이지', 'cityhub08', '2020-07-01 16:58:58', 'CG_00016');
INSERT INTO portal.portal_code_tb VALUES ('read', '읽기', 'Y', '2019-07-29 16:34:32', 'cityhub08', 20, '목록 페이지', 'cityhub08', '2020-07-01 16:58:58', 'CG_00016');
INSERT INTO portal.portal_code_tb VALUES ('dispose', '폐기', 'N', '2019-09-26 15:31:14', 'cityhub08', 110, '폐기', 'cityhub08', '2020-07-01 16:58:58', 'CG_00017');
INSERT INTO portal.portal_code_tb VALUES ('http', 'HTTP', 'Y', '2020-11-16 14:58:40.626869', 'cityhub08', 10, 'HTTP', NULL, NULL, 'CG_00024');
INSERT INTO portal.portal_code_tb VALUES ('mqtt', 'MQTT', 'Y', '2020-11-16 14:58:58.958517', 'cityhub08', 20, 'MQTT', NULL, NULL, 'CG_00024');
INSERT INTO portal.portal_code_tb VALUES ('update', '수정', 'Y', '2019-07-29 16:34:43', 'cityhub08', 30, '수정 페이지', 'cityhub08', '2020-07-01 16:58:58', 'CG_00016');
INSERT INTO portal.portal_code_tb VALUES ('delete', '삭제', 'Y', '2019-07-29 16:35:00', 'cityhub08', 40, '삭제', 'cityhub08', '2020-07-01 16:58:58', 'CG_00016');
INSERT INTO portal.portal_code_tb VALUES ('paidRequest', '유료화 요청', 'Y', '2019-09-26 15:30:30', 'cityhub08', 80, '유료화 요청', 'cityhub08', '2020-07-01 16:58:58', 'CG_00017');
INSERT INTO portal.portal_code_tb VALUES ('paidMode', '유료운영', 'Y', '2019-09-26 15:30:59', 'cityhub08', 100, '유료 운영', 'cityhub08', '2020-07-01 16:58:58', 'CG_00017');
INSERT INTO portal.portal_code_tb VALUES ('releaseRequest', '출시요청', 'Y', '2019-09-26 15:29:18', 'cityhub08', 50, '출시요청', 'cityhub08', '2020-07-01 16:58:58', 'CG_00017');
INSERT INTO portal.portal_code_tb VALUES ('registration', '등록', 'Y', '2019-09-26 15:28:46', 'cityhub08', 40, '개발운영', 'cityhub08', '2020-07-01 16:58:58', 'CG_00017');
INSERT INTO portal.portal_code_tb VALUES ('prodMode', '무료운영', 'Y', '2019-09-26 15:30:01', 'cityhub08', 70, '무료운영', 'cityhub08', '2020-07-01 16:58:58', 'CG_00017');
INSERT INTO portal.portal_code_tb VALUES ('paidReject', '유료화 요청 반려', 'Y', '2019-09-26 15:30:46', 'cityhub08', 90, '유료화 요청 반려', 'cityhub08', '2020-07-01 16:58:58', 'CG_00017');
INSERT INTO portal.portal_code_tb VALUES ('apprComplete', '승인완료', 'Y', '2019-09-26 15:36:46', 'cityhub08', 20, '승인완료', 'cityhub08', '2020-07-01 16:58:58', 'CG_00019');
INSERT INTO portal.portal_code_tb VALUES ('apprWait', '승인대기', 'Y', '2019-09-26 15:36:35', 'cityhub08', 10, '승인대기', 'cityhub08', '2020-07-01 16:58:58', 'CG_00019');
INSERT INTO portal.portal_code_tb VALUES ('file', '파일', 'Y', '2019-10-15 16:29:15.181652', 'cityhub08', 10, '파일', 'cityhub08', '2020-07-01 16:58:58', 'CG_00020');
INSERT INTO portal.portal_code_tb VALUES ('api', 'Open API', 'Y', '2019-10-15 16:29:32.197229', 'cityhub08', 20, 'Open API', 'cityhub08', '2020-07-01 16:58:58', 'CG_00020');
INSERT INTO portal.portal_code_tb VALUES ('rawData', '원천데이터', 'Y', '2019-10-15 16:30:09.824581', 'cityhub08', 10, '원천데이터', 'cityhub08', '2020-07-01 16:58:58', 'CG_00021');
INSERT INTO portal.portal_code_tb VALUES ('application/xml', 'XML', 'Y', '2019-10-15 16:31:02.339155', 'cityhub08', 10, 'XML', 'cityhub08', '2020-07-01 16:58:58', 'CG_00022');
INSERT INTO portal.portal_code_tb VALUES ('text/csv', 'CSV', 'Y', '2019-10-15 16:31:20.476113', 'cityhub08', 20, 'CSV', 'cityhub08', '2020-07-01 16:58:58', 'CG_00022');
INSERT INTO portal.portal_code_tb VALUES ('application/json', 'JSON', 'Y', '2019-10-15 16:31:33.923599', 'cityhub08', 30, 'JSON', 'cityhub08', '2020-07-01 16:58:58', 'CG_00022');
INSERT INTO portal.portal_code_tb VALUES ('lastData', 'lastdata', 'Y', '2019-10-29 15:47:38.765368', 'cityhub08', 10, 'lastdata', 'cityhub08', '2020-07-01 16:58:58', 'CG_00025');
INSERT INTO portal.portal_code_tb VALUES ('historyData', 'historydata', 'Y', '2019-10-29 15:47:55.98718', 'cityhub08', 20, 'historydata', 'cityhub08', '2020-07-01 16:58:58', 'CG_00025');
INSERT INTO portal.portal_code_tb VALUES ('adminPortal', '관리자포탈', 'Y', '2019-07-16 14:21:27', 'cityhub08', 10, '', 'cityhub08', '2020-07-01 16:58:58', 'CG_00001');
INSERT INTO portal.portal_code_tb VALUES ('answerWait', '답변대기', 'Y', '2019-07-16 14:27:30', 'cityhub08', 10, '', 'cityhub08', '2020-07-01 16:58:58', 'CG_00002');
INSERT INTO portal.portal_code_tb VALUES ('inquiry_dataset', '데이터명세서문의', 'Y', '2019-07-16 14:26:05', 'cityhub08', 30, '', 'cityhub08', '2020-07-01 16:58:58', 'CG_00003');
INSERT INTO portal.portal_code_tb VALUES ('inquiry_login', '로그인문의', 'Y', '2019-07-16 14:25:16', 'cityhub08', 20, '', 'cityhub08', '2020-07-01 16:58:58', 'CG_00003');
INSERT INTO portal.portal_code_tb VALUES ('inquiry_etc', '기타', 'Y', '2019-07-16 14:26:50', 'cityhub08', 0, '', 'cityhub08', '2020-07-01 16:58:58', 'CG_00003');
INSERT INTO portal.portal_code_tb VALUES ('inquiry_', '장애문의', 'Y', '2019-07-16 14:26:23', 'cityhub08', 40, '', 'cityhub08', '2020-07-01 16:58:58', 'CG_00003');
INSERT INTO portal.portal_code_tb VALUES ('inquiry_system', '시스템문의', 'Y', '2019-07-16 14:25:39', 'cityhub08', 10, '', 'cityhub08', '2020-07-01 16:58:58', 'CG_00003');
INSERT INTO portal.portal_code_tb VALUES ('daily', '일별', 'Y', '2019-07-16 14:30:30', 'cityhub08', 10, '', 'cityhub08', '2020-07-01 16:58:58', 'CG_00005');
INSERT INTO portal.portal_code_tb VALUES ('monthly', '월별', 'Y', '2019-07-16 14:30:49', 'cityhub08', 20, '', 'cityhub08', '2020-07-01 16:58:58', 'CG_00005');
INSERT INTO portal.portal_code_tb VALUES ('push', 'Push', 'Y', '2019-07-16 15:52:19', 'cityhub08', 10, '', 'cityhub08', '2020-07-01 16:58:58', 'CG_00010');
INSERT INTO portal.portal_code_tb VALUES ('polling', 'Polling', 'Y', '2019-07-16 15:52:46', 'cityhub08', 20, '', 'cityhub08', '2020-07-01 16:58:58', 'CG_00010');
INSERT INTO portal.portal_code_tb VALUES ('normalPortal', '사용자포탈', 'Y', '2019-07-16 14:21:46', 'cityhub08', 20, '', 'cityhub08', '2020-07-01 16:58:58', 'CG_00001');
INSERT INTO portal.portal_code_tb VALUES ('notPatternOp', '!~=', 'Y', '2019-11-22 15:33:34.326441', 'cityhub08', 80, '포함하지않음', 'cityhub08', '2020-07-03 17:02:30.944784', 'CG_00030');
INSERT INTO portal.portal_code_tb VALUES ('equal', '==', 'Y', '2019-11-22 15:30:22.622638', 'cityhub08', 10, '같음', 'cityhub08', '2020-07-06 14:39:03.584644', 'CG_00030');
INSERT INTO portal.portal_code_tb VALUES ('keyValuesHistory', 'keyValuesHistory', 'Y', '2019-10-29 15:49:45.519367', 'cityhub08', 30, 'keyValuesHistory', 'cityhub08', '2020-07-01 16:58:58', 'CG_00026');
INSERT INTO portal.portal_code_tb VALUES ('before', 'before', 'Y', '2019-10-29 15:50:39.210742', 'cityhub08', 10, 'before', 'cityhub08', '2020-07-01 16:58:58', 'CG_00027');
INSERT INTO portal.portal_code_tb VALUES ('AD_SR_CD_001', '신청', 'Y', '2019-12-10 17:51:07.071139', 'cityhub08', 10, '신청 상태', 'cityhub08', '2020-07-30 17:29:22.843497', 'CG_00031');
INSERT INTO portal.portal_code_tb VALUES ('createdAt', 'createdAt', 'N', '2019-10-29 15:51:42.627829', 'cityhub08', 10, 'createdAt', 'cityhub08', '2020-07-30 18:02:31.107241', 'CG_00028');
INSERT INTO portal.portal_code_tb VALUES ('modifiedAt', 'modifiedAt', 'Y', '2019-10-29 15:51:59.752017', 'cityhub08', 20, 'modifiedAt', 'cityhub08', '2020-07-30 18:02:38.911726', 'CG_00028');
INSERT INTO portal.portal_code_tb VALUES ('after', 'after', 'Y', '2019-10-29 15:50:50.377702', 'cityhub08', 20, 'after', 'cityhub08', '2020-07-01 16:58:58', 'CG_00027');
INSERT INTO portal.portal_code_tb VALUES ('instance', '인스턴스형', 'Y', '2020-10-27 19:28:02.997258', 'cityhub08', 20, '인스턴스형', NULL, NULL, 'CG_00033');
INSERT INTO portal.portal_code_tb VALUES ('between', 'between', 'Y', '2019-10-29 15:51:03.552066', 'cityhub08', 30, 'between', 'cityhub08', '2020-07-01 16:58:58', 'CG_00027');
INSERT INTO portal.portal_code_tb VALUES ('filter', '필터형', 'Y', '2020-10-27 19:28:21.459346', 'cityhub08', 30, '필터형', NULL, NULL, 'CG_00033');
INSERT INTO portal.portal_code_tb VALUES ('unlimit', '무제한', 'Y', '2020-11-09 10:12:10.610621', 'cityhub08', 20, '무제한', NULL, NULL, 'CG_00034');
INSERT INTO portal.portal_code_tb VALUES ('pay_request', '결제 요청', 'Y', '2020-11-09 10:13:38.320679', 'cityhub08', 10, '결제 요청', NULL, NULL, 'CG_00035');
INSERT INTO portal.portal_code_tb VALUES ('pay_complete', '환불 완료', 'Y', '2020-11-09 10:15:32.372484', 'cityhub08', 20, '환불 완료', NULL, NULL, 'CG_00036');
INSERT INTO portal.portal_code_tb VALUES ('pay_error', '환불 반려', 'Y', '2020-11-09 10:15:45.737653', 'cityhub08', 30, '환불 반려', NULL, NULL, 'CG_00036');
INSERT INTO portal.portal_code_tb VALUES ('1', '1일', 'Y', '2020-11-09 10:16:32.930485', 'cityhub08', 10, '1일', NULL, NULL, 'CG_00037');
INSERT INTO portal.portal_code_tb VALUES ('365', '365일', 'Y', '2020-11-09 10:17:23.835903', 'cityhub08', 50, '365일', NULL, NULL, 'CG_00037');
INSERT INTO portal.portal_code_tb VALUES ('testfeature', '테스트 확장기능', 'Y', '2020-11-09 14:05:11.621262', 'cityhub08', 20, 'ㅁㅁㅁ', NULL, NULL, 'CG_00038');
INSERT INTO portal.portal_code_tb VALUES ('websocket', 'WEBSOCKET', 'Y', '2020-11-16 14:59:12.946439', 'cityhub08', 30, 'WEBSOCKET', NULL, NULL, 'CG_00024');
INSERT INTO portal.portal_code_tb VALUES ('realtime', 'push', 'Y', '2020-11-26 14:15:52.569464', 'cityhub08', 20, 'push', NULL, NULL, 'CG_00039');
INSERT INTO portal.portal_code_tb VALUES ('answerComplete', '답변완료', 'Y', '2019-07-16 14:27:45', 'cityhub08', 20, '', 'cityhub08', '2020-07-01 16:58:58', 'CG_00002');
INSERT INTO portal.portal_code_tb VALUES ('normalValues', 'normalValues', 'Y', '2019-10-29 15:48:58.611115', 'cityhub08', 10, 'normalValues', 'cityhub08', '2020-07-01 16:58:58', 'CG_00026');
INSERT INTO portal.portal_code_tb VALUES ('unequal', '!=', 'Y', '2019-11-22 15:30:44.369299', 'cityhub08', 20, '다름', 'cityhub08', '2020-07-01 16:58:58', 'CG_00030');
INSERT INTO portal.portal_code_tb VALUES ('greater', '>', 'Y', '2019-11-22 15:31:10.824686', 'cityhub08', 30, '크다', 'cityhub08', '2020-07-01 16:58:58', 'CG_00030');
INSERT INTO portal.portal_code_tb VALUES ('greaterEq', '>=', 'Y', '2019-11-22 15:31:47.471784', 'cityhub08', 40, '크거나 같다', 'cityhub08', '2020-07-01 16:58:58', 'CG_00030');
INSERT INTO portal.portal_code_tb VALUES ('less', '<', 'Y', '2019-11-22 15:32:05.817436', 'cityhub08', 50, '작다', 'cityhub08', '2020-07-01 16:58:58', 'CG_00030');
INSERT INTO portal.portal_code_tb VALUES ('lessEq', '<=', 'Y', '2019-11-22 15:32:20.09847', 'cityhub08', 60, '작거나 같다', 'cityhub08', '2020-07-01 16:58:58', 'CG_00030');
INSERT INTO portal.portal_code_tb VALUES ('patternOp', '~=', 'Y', '2019-11-22 15:33:16.798702', 'cityhub08', 70, '포함', 'cityhub08', '2020-07-01 16:58:58', 'CG_00030');
INSERT INTO portal.portal_code_tb VALUES ('AD_SR_CD_003', '종료', 'Y', '2019-12-10 17:51:41.816207', 'cityhub08', 30, '종료 상태', 'cityhub08', '2020-07-01 16:58:58', 'CG_00031');
INSERT INTO portal.portal_code_tb VALUES ('AD_SR_CD_002', '처리 중', 'Y', '2019-12-10 17:51:25.01098', 'cityhub08', 20, '처리중 상태', 'cityhub08', '2020-07-01 16:58:58', 'CG_00031');
INSERT INTO portal.portal_code_tb VALUES ('AD_SR_CD_004', '삭제요청', 'Y', '2019-12-10 17:52:09.285617', 'cityhub08', 40, '삭제요청 상태', 'cityhub08', '2020-07-01 16:58:58', 'CG_00031');
INSERT INTO portal.portal_code_tb VALUES ('AD_SR_CD_005', '폐기', 'Y', '2019-12-10 17:52:23.546163', 'cityhub08', 50, '폐기 상태', 'cityhub08', '2020-07-01 16:58:58', 'CG_00031');


--
-- TOC entry 3112 (class 0 OID 16560)
-- Dependencies: 207
-- Data for Name: portal_codegroup_tb; Type: TABLE DATA; Schema: portal; Owner: postgres
--

INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00002', '질의응답진행구분', 'Y', '2019-07-16 14:22:55', 'test', 20, '질문 및 답변에 대한 진행 상태 표기를 위한 코드						
', 'test', '2019-07-17 15:10:50');
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00005', '기간별 구분 코드', 'Y', '2019-07-16 14:30:00', 'test', 50, '특정 검색 및 통계를 위한 일별/월별 구분코드						', 'test', '2019-07-17 15:14:31');
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00003', 'Q&A 질문유형', 'Y', '2019-07-16 14:25:02', 'test', 30, '질문 유형을 구분하기 위한 코드', 'test', '2019-07-17 15:15:05');
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00017', '데이터셋 상태 코드', 'Y', '2019-09-26 15:26:34', 'test', 170, '데이터셋 상태 코드', 'test', '2019-09-26 15:26:48');
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00025', '데이터셋 샘플 데이터유형', 'Y', '2019-10-29 15:46:52.532508', 'cityhub08', 250, '데이터셋 샘플 조회 시 전송할 데이터유형 구분', NULL, NULL);
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00026', '데이터셋 샘플 옵션', 'Y', '2019-10-29 15:48:28.556705', 'cityhub08', 260, '데이터셋 샘플 조회 시 전송할 옵션 구분', NULL, NULL);
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00021', '데이터 유형 코드', 'Y', '2019-10-15 16:29:53.491833', 'test', 210, '데이터셋의 데이터 유형', NULL, NULL);
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00022', '확장자 코드', 'Y', '2019-10-15 16:30:43.484934', 'test', 220, '데이터셋의 파일유형에 따른 확장자', NULL, NULL);
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00027', '데이터셋 샘플 날짜검색조건(이전, 이후, 중간)', 'Y', '2019-10-29 15:50:21.844241', 'cityhub08', 270, '데이터셋 샘플 조회 시 전송할 날짜검색조건(이전, 이후, 중간)', NULL, NULL);
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00018', '데이터셋 첨부파일 구분 코드', 'Y', '2019-09-26 15:35:22', 'test', 180, '데이터셋 첨부파일 구분 코드', NULL, NULL);
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00019', '분석가요청 상태코드', 'Y', '2019-09-26 15:36:17', 'test', 190, '분석가요청 상태코드', NULL, NULL);
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00020', '제공유형 코드', 'Y', '2019-10-14 06:07:56', 'test', 200, '데이터셋의 제공유형', 'test', '2019-10-15 16:28:28.643099');
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00016', '프로그램 접근권한 코드', 'Y', '2019-07-29 10:37:28', 'test', 160, '프로그램 접근권한 코드', 'test', '2019-08-20 16:38:04');
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00029', '데이터셋 문의 상태 코드', 'Y', '2019-11-19 13:03:59.033955', 'cityhub08', 290, '데이터셋 문의 상태 코드', NULL, NULL);
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00032', '데이터셋 라이센스 구분', 'Y', '2020-07-15 13:57:49.227023', 'cityhub08', 320, '데이터셋 라이센스 구분 (https://creativecommons.org/licenses/)', 'cityhub08', '2020-07-28 17:02:16.93373');
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00001', '포탈 구분', 'Y', '2019-07-16 14:20:52', 'test', 10, '사용자,관리자 포탈 구분을 위한 코드.', 'cityhub08', '2020-10-07 17:45:41.26504');
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00033', '데이터셋 제공유형', 'Y', '2020-10-27 19:26:28.937106', 'cityhub08', 330, '데이터셋 제공유형', NULL, NULL);
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00028', '데이터셋 샘플 날짜검색조건(생성일, 수정일)', 'Y', '2019-10-29 15:51:25.106261', 'cityhub08', 280, '데이터셋 샘플 조회 시 전송할 날짜검색조건(생성일, 수정일)dd', 'cityhub08', '2020-02-17 14:12:44.931978');
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00034', '데이터 실시간 수신 트래픽 유형', 'Y', '2020-11-09 10:08:59.171111', 'cityhub08', 340, '데이터 실시간 수신 트래픽 유형', NULL, NULL);
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00010', '데이터 구독형태 구분', 'Y', '2019-07-16 15:51:47', 'test', 100, '데이터 Push 및 Polling  구분을 위한 코드', 'test', '2019-07-17 15:14:59');
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00035', '결제 진행 상태', 'Y', '2020-11-09 10:12:36.981355', 'cityhub08', 350, '유료 결제 진행 상태를 표시		
', NULL, NULL);
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00030', '데이터셋 조건 구분 코드', 'Y', '2019-11-22 15:15:24.210264', 'cityhub08', 300, '데이터셋 조건 구분 코드', 'cityhub08', '2020-07-06 14:39:27.948845');
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00036', '환불 진행 상태', 'Y', '2020-11-09 10:14:42.770731', 'cityhub08', 360, '유료 결제 진행 상태를 표시		
', NULL, NULL);
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00031', '데이터셋 어댑터 신청 상태 코드', 'Y', '2019-12-10 17:50:22.872526', 'cityhub08', 310, '데이터셋 어댑터 신청 상태 코드', 'cityhub08', '2020-07-09 17:40:36.340305');
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00037', '가격정책 제공기간', 'Y', '2020-11-09 10:16:14.874913', 'cityhub08', 370, '유료결제의 제공기간에 대한 코드		', NULL, NULL);
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00038', '확장 기능 코드', 'Y', '2020-11-09 10:17:49.328493', 'cityhub08', 380, '확장 기능을 표시 한다.		', NULL, NULL);
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00024', '전송프로토콜 구분코드', 'Y', '2020-11-16 14:58:18.767615', 'cityhub08', 240, '데이터셋 이용신청 시 전송 할 프로토콜 구분', NULL, NULL);
INSERT INTO portal.portal_codegroup_tb VALUES ('CG_00039', '활용내역 전송 타입', 'Y', '2020-11-26 14:15:19.179903', 'cityhub08', 390, 'on-demand 또는 realtime 형태의 전송 타입의 코드		', NULL, NULL);


--
-- TOC entry 3115 (class 0 OID 16577)
-- Dependencies: 210
-- Data for Name: portal_ext_features; Type: TABLE DATA; Schema: portal; Owner: postgres
--

INSERT INTO portal.portal_ext_features VALUES (true, 'datasetPayments');
INSERT INTO portal.portal_ext_features VALUES (true, 'testfeature');


--
-- TOC entry 3118 (class 0 OID 16589)
-- Dependencies: 213
-- Data for Name: portal_menu_tb; Type: TABLE DATA; Schema: portal; Owner: postgres
--

INSERT INTO portal.portal_menu_tb VALUES (1574315118648001, 'MNU056', '데이터셋관리', '/dataset/pageList.do', '2019-11-21 14:45:19.118685', 'cityhub08', '2019-12-06 15:17:18.632082', 107, 1565247635640007, 'adminPortal', '', 'Y', 'cityhub08', 1570501508372001);
INSERT INTO portal.portal_menu_tb VALUES (1570669537147001, 'MNU034', '분석가요청', '/analy/pageList.do', '2019-10-10 10:05:39', 'test', '2020-04-24 14:07:24.638618', 109, 1570669105404001, 'adminPortal', '', 'Y', 'cityhub08', 1570409551486001);
INSERT INTO portal.portal_menu_tb VALUES (1570669105404001, 'MNU033', '승인관리', '', '2019-10-10 09:58:27', 'test', '2020-04-24 14:08:11.984877', 106, 1565247635640007, 'adminPortal', '', 'Y', 'cityhub08', 1570409551486001);
INSERT INTO portal.portal_menu_tb VALUES (1565247635640006, 'MNU001', '권한관리', '/authgroup/pageList.do', '2019-07-16 20:42:14', 'test', '2020-07-28 12:01:26.525294', 102, 1565247635640007, 'adminPortal', '', 'Y', 'cityhub08', 1565324241947001);
INSERT INTO portal.portal_menu_tb VALUES (1565247635640015, 'MNU011', '데이터민원관리', '/datacomplaint/pageList.do', '2019-07-17 13:21:29', 'test', '2020-04-29 15:54:07.131761', 111, 1565247635640007, 'adminPortal', '', 'Y', 'cityhub08', 1565324690868001);
INSERT INTO portal.portal_menu_tb VALUES (1565247635640004, 'MNU003', '메뉴관리', '/menu/pageList.do', '2019-07-16 20:42:14.03015', 'test', '2020-10-27 18:29:09.709566', 101, 1565247635640007, 'adminPortal', '', 'Y', 'cityhub08', 1565324241932001);
INSERT INTO portal.portal_menu_tb VALUES (1606460334292001, 'MNU079', '카테고리별 데이터셋 현황', '/statistics/pageDatasetStatisticsChart.do', '2020-11-27 15:58:53.719922', 'cityhub08', '2020-12-04 10:46:18.934173', 20, 1570499023547001, 'normalPortal', '', 'Y', 'cityhub08', 1570501508372001);
INSERT INTO portal.portal_menu_tb VALUES (1603944184164001, 'MNU075', '인센티브 토큰 발급 내역', '/mypage/token/log/pageList.do', '2020-10-29 13:03:02.874526', 'cityhub08', '2020-11-04 10:10:44.159695', 5, 1571017264379001, 'normalPortal', '', 'Y', 'cityhub08', 1603944205862001);
INSERT INTO portal.portal_menu_tb VALUES (1570409767702001, 'MNU014', '데이터셋 요청', '/request/pageList.do', '2019-10-07 09:56:08', 'test', '2020-11-11 11:30:51.192585', 114, 1570669105404001, 'adminPortal', '', 'Y', 'cityhub08', 1570409551486001);
INSERT INTO portal.portal_menu_tb VALUES (1565247635640011, 'MNU007', 'Q&A 관리', '/qna/pageList.do', '2019-07-17 13:20:18', 'test', '2020-04-10 10:54:02.720178', 107, 1565247635640007, 'adminPortal', '', 'Y', 'cityhub08', 1565324688055001);
INSERT INTO portal.portal_menu_tb VALUES (1607326595305001, 'MNU081', '데이터 환불 내역', '/mypage/refundUser/pageList.do', '2020-12-07 16:36:34.994028', 'cityhub08', '2020-12-09 18:14:24.852891', 6, 1571017264379001, 'normalPortal', '', 'Y', 'cityhub08', 1570501508372001);
INSERT INTO portal.portal_menu_tb VALUES (1607326700666001, 'MNU082', '데이터 환불 내역', '/mypage/refundProvider/pageList.do', '2020-12-07 16:38:20.353305', 'cityhub08', '2020-12-09 18:14:33.943941', 4, 1571017516443001, 'normalPortal', '', 'Y', 'cityhub08', 1570501508372001);
INSERT INTO portal.portal_menu_tb VALUES (1565247635640007, 'MNU002', '관리자메뉴', 'menu', '2019-07-16 20:42:14.026692', 'test', '2019-07-29 09:45:50', 1, 0, 'adminPortal', NULL, 'Y', 'admin', 0);
INSERT INTO portal.portal_menu_tb VALUES (1565247635640008, 'MNU004', '코드관리', '/codegroup/pageList.do', '2019-07-16 20:54:31.222172', 'test', '2020-04-24 15:18:05.33394', 103, 1565247635640007, 'adminPortal', '', 'Y', 'cityhub08', 1565324685939001);
INSERT INTO portal.portal_menu_tb VALUES (1565247635640010, 'MNU006', '공지사항 관리', '/notice/pageList.do', '2019-07-17 13:20:04', 'test', '2019-08-07 11:19:07', 106, 1565247635640007, 'adminPortal', '', 'Y', 'admin', 1565324687355001);
INSERT INTO portal.portal_menu_tb VALUES (1565247635640014, 'MNU010', '프로그램관리', '/programgroup/pageList.do', '2019-07-17 13:21:11', 'test', '2019-08-07 11:19:35', 110, 1565247635640007, 'adminPortal', '', 'Y', 'admin', 1565324690168001);
INSERT INTO portal.portal_menu_tb VALUES (1570499125091001, 'MNU032', '활용후기', '/review/pageList.do', '2019-10-08 10:45:27', 'test', '2019-10-25 10:15:47.715046', 0, 1570499106621001, 'normalPortal', '', 'Y', 'cityhub08', 1571966107022001);
INSERT INTO portal.portal_menu_tb VALUES (1565247635640013, 'MNU009', '카테고리관리', '/category/pageList.do', '2019-07-17 13:20:45', 'test', '2019-10-21 09:32:03.140436', 109, 1565247635640007, 'adminPortal', '', 'Y', 'cityhub08', 1565324689467001);
INSERT INTO portal.portal_menu_tb VALUES (1565247635640012, 'MNU008', 'FAQ 관리', '/faq/pageList.do', '2019-07-17 13:20:27', 'test', '2020-04-10 10:54:11.406831', 108, 1565247635640007, 'adminPortal', '', 'Y', 'cityhub08', 1565324688755001);
INSERT INTO portal.portal_menu_tb VALUES (1570498694125001, 'MNU016', '소개', '', '2019-10-08 10:38:16', 'test', '2019-11-06 11:14:28.321966', 210, 1565247635640016, 'normalPortal', '', 'Y', 'cityhub08', 1571899642620001);
INSERT INTO portal.portal_menu_tb VALUES (1570498810503001, 'MNU019', '고객 서비스', '', '2019-10-08 10:40:13', 'test', '2019-11-06 11:17:36.649177', 220, 1565247635640016, 'normalPortal', '', 'Y', 'cityhub08', 1573006633612001);
INSERT INTO portal.portal_menu_tb VALUES (1571017264379001, 'MNU037', '데이터활용', '', '2019-10-14 10:41:07', 'test', '2019-10-30 14:42:48.904677', 2, 1571014871452001, 'normalPortal', '', 'Y', 'cityhub08', 1571014820930001);
INSERT INTO portal.portal_menu_tb VALUES (1570498954185001, 'MNU024', '데이터', '', '2019-10-08 10:42:36', 'test', '2019-11-06 11:15:50.456048', 230, 1565247635640016, 'normalPortal', '', 'Y', 'cityhub08', 1570501508372001);
INSERT INTO portal.portal_menu_tb VALUES (1570499106621001, 'MNU031', '활용사례', '', '2019-10-08 10:45:09', 'test', '2019-11-06 11:16:27.55148', 250, 1565247635640016, 'normalPortal', '', 'Y', 'cityhub08', 1571966107022001);
INSERT INTO portal.portal_menu_tb VALUES (1575420199573001, 'MNU057', '어댑터 신청 내역', '/mypage/adaptor/pageList.do', '2019-12-04 09:43:18.314194', 'cityhub08', NULL, 4, 1571017516443001, 'normalPortal', '', 'Y', NULL, 1571014820930001);
INSERT INTO portal.portal_menu_tb VALUES (1570498746707001, 'MNU017', '데이터 허브 소개', '/intro/datahub/pageList.do', '2019-10-08 10:39:09', 'test', '2019-10-24 16:12:43.487534', 0, 1570498694125001, 'normalPortal', '', 'Y', 'cityhub08', 1571899642620001);
INSERT INTO portal.portal_menu_tb VALUES (1570498772251001, 'MNU018', '제공 서비스 소개', '/intro/provsvc/pageList.do', '2019-10-08 10:39:34', 'test', '2019-10-24 15:53:59.206256', 0, 1570498694125001, 'normalPortal', '', 'Y', 'cityhub08', 1571899642620001);
INSERT INTO portal.portal_menu_tb VALUES (1571017391161001, 'MNU039', '데이터 이용내역', '/mypage/usage/pageList.do', '2019-10-14 10:43:14', 'test', NULL, 1, 1571017264379001, 'normalPortal', '', 'Y', NULL, 1571014820930001);
INSERT INTO portal.portal_menu_tb VALUES (1571017439966001, 'MNU040', '데이터 분석가요청', '/mypage/analy/pageList.do', '2019-10-14 10:44:03', 'test', NULL, 2, 1571017264379001, 'normalPortal', '', 'Y', NULL, 1571014820930001);
INSERT INTO portal.portal_menu_tb VALUES (1570498981861001, 'MNU025', '데이터셋', '/dataset/pageList.do', '2019-10-08 10:43:04', 'test', '2019-10-08 11:27:35', 0, 1570498954185001, 'normalPortal', '', 'Y', 'cityhub08', 1570501508372001);
INSERT INTO portal.portal_menu_tb VALUES (1571017753996001, 'MNU044', '데이터 사용자현황', '/mypage/userstats/pageList.do', '2019-10-14 10:49:17', 'test', NULL, 1, 1571017516443001, 'normalPortal', '', 'Y', NULL, 1571014820930001);
INSERT INTO portal.portal_menu_tb VALUES (1571017322907001, 'MNU038', '관심데이터', '/mypage/wishlist/pageList.do', '2019-10-14 10:42:06', 'test', '2019-10-14 10:42:37', 0, 1571017264379001, 'normalPortal', '', 'Y', 'cityhub08', 1571014820930001);
INSERT INTO portal.portal_menu_tb VALUES (1571017791098001, 'MNU045', '대시보드', '/mypage/dashboard/pageList.do', '2019-10-14 10:49:54', 'test', NULL, 2, 1571017516443001, 'normalPortal', '', 'Y', NULL, 1571014820930001);
INSERT INTO portal.portal_menu_tb VALUES (1571017848356001, 'MNU046', '데이터 판매내역', '/mypage/saleshist/pageList.do', '2019-10-14 10:50:51', 'test', NULL, 3, 1571017264379001, 'normalPortal', '', 'Y', NULL, 1571014820930001);
INSERT INTO portal.portal_menu_tb VALUES (1571017607863001, 'MNU042', '문의사항', '/mypage/dataset/inquiry/pageList.do', '2019-10-14 10:46:51', 'test', '2019-10-14 10:51:34', 4, 1571014871452001, 'normalPortal', '', 'Y', 'cityhub08', 1571014820930001);
INSERT INTO portal.portal_menu_tb VALUES (1571014871452001, 'MNU035', '마이페이지', '', '2019-10-14 10:01:14', 'test', '2019-10-14 10:24:15', 0, 1565247635640016, 'normalPortal', '', 'Y', 'cityhub08', 1571014820930001);
INSERT INTO portal.portal_menu_tb VALUES (1571017716564001, 'MNU043', '데이터 관리', '/mypage/dataset/pageList.do', '2019-10-14 10:48:39', 'test', NULL, 0, 1571017516443001, 'normalPortal', '', 'Y', NULL, 1571014820930001);
INSERT INTO portal.portal_menu_tb VALUES (1571014963245001, 'MNU036', '개인정보수정', '/mypage/usermod/pageInfo.do', '2019-10-14 10:02:46', 'test', '2019-10-16 15:04:05.599232', 1, 1571014871452001, 'normalPortal', '', 'N', 'cityhub08', 1571014820930001);
INSERT INTO portal.portal_menu_tb VALUES (1565247635640016, 'MNU013', '사용자메뉴', 'menu', '2019-07-30 14:47:15.115225', 'test', '2019-07-30 14:47:15.115225', 2, 0, 'normalPortal', NULL, 'Y', 'admin', 0);
INSERT INTO portal.portal_menu_tb VALUES (1571017516443001, 'MNU041', '데이터제공', '', '2019-10-14 10:45:19', 'test', '2019-10-30 14:43:11.377477', 3, 1571014871452001, 'normalPortal', '', 'Y', 'cityhub08', 1571014820930001);
INSERT INTO portal.portal_menu_tb VALUES (1570499023547001, 'MNU026', '통계', '', '2019-10-08 10:43:46', 'test', '2019-11-12 16:04:14.293293', 240, 1565247635640016, 'normalPortal', '', 'Y', 'cityhub08', 1571965218616001);
INSERT INTO portal.portal_menu_tb VALUES (1570498913514001, 'MNU022', '신고하기', '/cusvc/datacomplaint/pageList.do', '2019-10-08 10:41:56', 'test', '2020-08-04 14:11:56.970368', 0, 1570498810503001, 'normalPortal', '', 'Y', 'cityhub08', 1573006633612001);
INSERT INTO portal.portal_menu_tb VALUES (1570498933917001, 'MNU023', '공지사항', '/cusvc/notice/pageList.do', '2019-10-08 10:42:16', 'test', '2020-08-04 14:12:26.665275', 0, 1570498810503001, 'normalPortal', '', 'Y', 'cityhub08', 1573006633612001);
INSERT INTO portal.portal_menu_tb VALUES (1570498887719001, 'MNU021', '묻고답하기', '/cusvc/qna/pageList.do', '2019-10-08 10:41:30', 'test', '2020-08-04 14:12:47.52727', 0, 1570498810503001, 'normalPortal', '', 'Y', 'cityhub08', 1573006633612001);
INSERT INTO portal.portal_menu_tb VALUES (1570498864201001, 'MNU020', 'FAQ', '/cusvc/faq/pageList.do', '2019-10-08 10:41:06', 'test', '2020-08-04 14:13:00.672198', 0, 1570498810503001, 'normalPortal', '', 'Y', 'cityhub08', 1573006633612001);
INSERT INTO portal.portal_menu_tb VALUES (1607044929121001, 'MNU080', '지도 기반 데이터셋 데이터 현황', '/statistics/pageDatasetStatisticsMap.do', '2020-12-04 10:22:07.840553', 'cityhub08', '2020-12-04 10:46:29.157684', 30, 1570499023547001, 'normalPortal', '', 'Y', 'cityhub08', 1570501508372001);
INSERT INTO portal.portal_menu_tb VALUES (1574302073806001, 'MNU054', '데이터제공현황', '/analy/stat/datastat/pageList.do', '2019-11-21 11:07:54.185185', 'cityhub08', '2020-12-04 17:38:47.090125', 10, 1570499023547001, 'normalPortal', '', 'N', 'cityhub08', 1573005999696001);
INSERT INTO portal.portal_menu_tb VALUES (1603964564494001, 'MNU076', '확장기능관리', '/features/pageDetail.do', '2020-10-29 18:42:44.418997', 'cityhub08', '2020-11-09 09:54:41.750272', 997, 1565247635640007, 'adminPortal', '', 'Y', 'cityhub08', 1604280927875001);
INSERT INTO portal.portal_menu_tb VALUES (1603964605853001, 'MNU077', '가격정책관리', '/pricePolicies/pageList.do', '2020-10-29 18:43:25.776755', 'cityhub08', '2020-11-09 18:12:38.102587', 998, 1565247635640007, 'adminPortal', '', 'Y', 'cityhub08', 1604909927898001);
INSERT INTO portal.portal_menu_tb VALUES (1570499125091002, 'MNU047', '분석', 'http://1.1.1.1:12020', '2019-10-06 11:10:30', 'cityhub08', '2022-04-08 16:27:35.467325', 260, 1565247635640016, 'normalPortal', NULL, 'N', 'cityhub08', 1573005999696001);



--
-- TOC entry 3121 (class 0 OID 16607)
-- Dependencies: 216
-- Data for Name: portal_program_tb; Type: TABLE DATA; Schema: portal; Owner: postgres
--

INSERT INTO portal.portal_program_tb VALUES (1565326452129001, '/faq/get.do', '상세 데이터 호출', 'sample desc', '2019-08-02 10:53:41', '2020-02-26 10:07:17.09759', 'test', 'test', 1565324688755001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452111001, '/notice/pageEdit.do', '등록 이동', 'sample desc', '2019-08-02 10:53:34', '2019-08-02 15:46:58', 'test', 'test', 1565324687355001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452159001, '/datacomplaint/pageDetail.do', '상세 이동', 'sample desc', '2019-08-02 10:53:55', '2020-02-26 10:01:53.179629', 'test', 'test', 1565324690868001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452125001, '/faq/getList.do', '목록 데이터 호출', 'sample desc', '2019-08-02 10:53:39', '2020-02-26 10:07:11.637847', 'test', 'test', 1565324688755001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452058001, '/codegroup/pageModify.do', '수정 이동', 'sample desc', '2019-08-02 10:53:24', NULL, 'test', NULL, 1565324685939001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326451992001, '/menu/create.do', '데이터 등록', 'sample desc', '2019-08-02 10:40:35', '2020-02-26 16:14:20.144649', 'test', 'test', 1565324241932001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452131001, '/faq/pageModify.do', '수정 이동', 'sample desc', '2019-08-02 10:53:42', '2020-07-16 17:01:17.367642', 'test', 'test', 1565324688755001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452112001, '/notice/create.do', '데이터 등록', 'sample desc', '2019-08-02 10:53:35', '2019-08-02 15:47:30', 'test', 'test', 1565324687355001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452138001, '/category/create.do', '데이터 등록', 'sample desc', '2019-08-02 10:53:17', NULL, 'test', NULL, 1565324689467001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565683592675001, '123', '123', '123', '2019-08-13 17:06:33', NULL, 'test', NULL, 1565324691569001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452052001, '/codegroup/pageEdit.do', '등록 이동', 'sample desc', '2019-08-02 10:53:22', NULL, 'test', NULL, 1565324685939001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452055001, '/codegroup/create.do', '데이터 등록', 'sample desc', '2019-08-02 10:53:23', NULL, 'test', NULL, 1565324685939001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452079001, '/user/create.do', '데이터 등록', 'sample desc', '2019-08-02 10:53:26', NULL, 'test', NULL, 1565324686654001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452126001, '/faq/pageEdit.do', '등록 이동', 'sample desc', '2019-08-02 10:53:13', NULL, 'test', NULL, 1565324688755001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565742227117001, '/codegroup/code/check.do', '코드ID 중복체크', '코드ID 중복체크', '2019-08-14 09:23:50', NULL, 'test', NULL, 1565324685939001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571014853065001, '/mypage/usermod/pageInfo.do', '비밀번호 재입력화면 이동', '비밀번호 재입력화면 이동', '2019-10-14 10:00:56', NULL, 'test', NULL, 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571018931527001, '/mypage/userstats/getList.do', '데이터 사용자현황 데이터 조회', '데이터 사용자현황 데이터 조회', '2019-10-14 11:08:54', NULL, 'test', NULL, 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571018946925001, '/mypage/dashboard/pageList.do', '대시보드 화면 이동', '대시보드 화면 이동', '2019-10-14 11:09:10', NULL, 'test', NULL, 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571018972837001, '/mypage/dashboard/getList.do', '대시보드 데이터 조회', '대시보드 데이터 조회', '2019-10-14 11:09:36', NULL, 'test', NULL, 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571018995985001, '/mypage/saleshist/pageList.do', '데이터 판매내역 화면이동', '데이터 판매내역 화면이동', '2019-10-14 11:09:59', NULL, 'test', NULL, 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571019012809001, '/mypage/saleshist/getList.do', '데이터 판매내역 데이터 조회', '데이터 판매내역 데이터 조회', '2019-10-14 11:10:16', NULL, 'test', NULL, 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571019029448001, '/mypage/dataset/inquiry/pageList.do', '문의사항 목록화면 이동', '문의사항 목록화면 이동', '2019-10-14 11:10:32', NULL, 'test', NULL, 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452047001, '/codegroup/pageDetail.do', '상세 이동', 'sample desc', '2019-08-02 10:53:21', NULL, 'test', NULL, 1565324685939001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452085001, '/user/pop/getList.do', '목록 데이터 호출', 'sample desc', '2019-08-02 10:53:29', NULL, 'test', NULL, 1565324686654001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452062001, '/codegroup/code/pageDetail.do', '팝업 이동', 'sample desc', '2019-08-02 10:53:05', '2019-08-13 15:29:30', 'test', 'test', 1565324685939001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452101001, '/company/pageDetail.do', '상세 화면 이동', 'sample desc', '2019-08-02 10:54:08', '2019-08-13 16:57:21', 'test', 'test', 1565324691569001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452106001, '/notice/pageList.do', '목록 이동', 'sample desc', '2019-08-02 10:53:30', '2019-08-02 15:44:26', 'test', 'test', 1565324687355001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452045001, '/codegroup/get.do', '상세 데이터 호출', 'sample desc', '2019-08-02 10:53:20', NULL, 'test', NULL, 1565324685939001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452069001, '/user/pageList.do', '목록 이동', 'sample desc', '2019-08-02 10:53:25', NULL, 'test', NULL, 1565324686654001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452107001, '/notice/getList.do', '목록 데이터 호출', 'sample desc', '2019-08-02 10:53:31', '2019-08-02 15:44:36', 'test', 'test', 1565324687355001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452118001, '/qna/pageList.do', 'Q&A 목록 이동', 'sample desc', '2019-08-02 10:53:36', '2019-08-02 16:18:47', 'test', 'test', 1565324688055001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452120001, '/qna/pageDetail.do', 'Q&A 상세 이동', 'sample desc', '2019-08-02 10:53:37', '2019-08-02 16:18:40', 'test', 'test', 1565324688055001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452144001, '/programgroup/pageList.do', '프로그램 그룹 목록 이동', 'sample desc', '2019-08-02 10:53:49', '2019-08-05 16:19:18', 'test', 'test', 1565324690168001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452136001, '/category/getList.do', '목록 데이터 호출', 'sample desc', '2019-08-02 10:53:43', NULL, 'test', NULL, 1565324689467001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452139001, '/category/pageDetail.do', '상세 이동', 'sample desc', '2019-08-02 10:53:44', NULL, 'test', NULL, 1565324689467001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452140001, '/category/get.do', '상세 데이터 호출', 'sample desc', '2019-08-02 10:53:45', NULL, 'test', NULL, 1565324689467001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452150001, '/programgroup/pageDetail.do', '프로그램 그룹 상세 이동', 'sample desc', '2019-08-02 10:53:51', '2019-08-05 16:19:53', 'test', 'test', 1565324690168001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452160001, '/datacomplaint/get.do', '상세 데이터 호출', 'sample desc', '2019-08-02 10:53:56', '2020-04-29 15:43:04.37905', 'test', 'test', 1565324690868001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452141001, '/category/pageModify.do', '수정 이동', 'sample desc', '2019-08-02 10:53:46', NULL, 'test', NULL, 1565324689467001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452081001, '/user/modify.do', '데이터 수정', 'sample desc', '2019-08-02 10:53:27', NULL, 'test', NULL, 1565324686654001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452142001, '/category/modify.do', '데이터 수정', 'sample desc', '2019-08-02 10:53:47', NULL, 'test', NULL, 1565324689467001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452083001, '/user/remove.do', '데이터 삭제', 'sample desc', '2019-08-02 10:53:28', NULL, 'test', NULL, 1565324686654001, 'delete', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452116001, '/notice/remove.do', '데이터 삭제', 'sample desc', '2019-08-02 10:53:11', '2019-08-02 15:47:44', 'test', 'test', 1565324687355001, 'delete', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452143001, '/category/remove.do', '데이터 삭제', 'sample desc', '2019-08-02 10:53:48', NULL, 'test', NULL, 1565324689467001, 'delete', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452099001, '/company/remove.do', '데이터 삭제', 'sample desc', '2019-08-02 10:54:06', NULL, 'test', NULL, 1565324691569001, 'delete', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452064001, '/codegroup/code/create.do', '데이터 등록', 'sample desc', '2019-08-02 10:53:06', NULL, 'test', NULL, 1565324685939001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452024001, '/menu/pageList.do', '목록 화면 이동', 'sample desc', '2019-08-02 11:28:10', '2020-04-29 13:23:48.554212', 'test', 'test', 1565324241932001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452093001, '/company/pageEdit.do', '등록 이동', 'sample desc', '2019-08-02 10:54:02', NULL, 'test', NULL, 1565324691569001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452168001, '/programgroup/program/get.do', '프로그램 상세 데이터 호출', '프로그램 데이터 호출', '2019-08-02 16:03:24', '2019-08-05 16:22:27', 'test', 'test', 1565324690168001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452094001, '/company/create.do', '데이터 등록', 'sample desc', '2019-08-02 10:54:03', NULL, 'test', NULL, 1565324691569001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452176001, '/qna/create.do', 'Q&A 데이터 등록', 'Q&A 데이터 등록', '2019-08-02 16:19:28', '2020-02-28 20:51:09.685354', 'test', 'test', 1565324688055001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452012001, '/authgroup/create.do', '데이터 등록', 'sample desc', '2019-08-02 10:51:03', NULL, 'test', NULL, 1565324241947001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452171001, '/programgroup/program/create.do', '프로그램 데이터 등록', '프로그램 데이터 등록', '2019-08-02 16:04:06', '2019-08-05 16:22:48', 'test', 'test', 1565324690168001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452011001, '/authgroup/pageEdit.do', '등록 화면 이동', 'sample desc', '2019-08-02 10:50:46', NULL, 'test', NULL, 1565324241947001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452165001, '/programgroup/create.do', '프로그램 그룹 데이터 등록', '프로그램 그룹 데이터 등록', '2019-08-02 16:01:05', '2019-08-05 16:21:20', 'test', 'test', 1565324690168001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452092001, '/company/getList.do', '목록 데이터 호출', 'sample desc', '2019-08-02 10:53:59', NULL, 'test', NULL, 1565324691569001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452100001, '/company/get.do', '상세 데이터 호출', 'sample desc', '2019-08-02 10:54:07', NULL, 'test', NULL, 1565324691569001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452105001, '/company/pop/getList.do', '팝업 목록 데이터 호출', 'sample desc', '2019-08-02 10:54:09', NULL, 'test', NULL, 1565324691569001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571016464349001, '/mypage/usermod/getInfo.do', '비밀번호 데이터 확인', '비밀번호 데이터 확인', '2019-10-14 10:27:47', NULL, 'test', NULL, 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571016497416001, '/mypage/wishlist/getList.do', '관심데이터목록 조회', '관심데이터목록 조회', '2019-10-14 10:28:20', NULL, 'test', NULL, 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571016509918001, '/mypage/usage/pageList.do', '데이터 이용내역 화면이동', '데이터 이용내역 화면이동', '2019-10-14 10:28:33', NULL, 'test', NULL, 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452026001, '/menu/getList.do', '목록 데이터 호출', 'sample desc', '2019-08-02 11:28:41', '2019-08-02 12:38:07', 'test', 'test', 1565324241932001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452031001, '/menu/get.do', '상세 데이터 호출', 'sample desc', '2019-08-02 12:39:04', NULL, 'test', NULL, 1565324241932001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1570409669652001, '/request/getList.do', '데이터셋 요청 목록 조회', '데이터셋 요청 목록 조회', '2019-10-07 09:54:30', '2020-11-11 10:53:42.075708', 'test', 'test', 1570409551486001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452188001, '/menu/pageDetail.do', '상세 화면 이동', '상세 화면 이동', '2019-08-05 15:12:03', NULL, 'test', NULL, 1565324241932001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571016658358001, '/mypage/analy/getList.do', '데이터 분석가요청 목록', '데이터 분석가요청 목록', '2019-10-14 10:31:01', NULL, 'test', NULL, 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571016858514001, '/mypage/analy/pageDetail.do', '데이터 분석가요청 상세 화면 이동', '데이터 분석가요청 상세 화면 이동', '2019-10-14 10:34:21', NULL, 'test', NULL, 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452199001, '/user/check.do', '아이디 중복체크', '아이디 중복체크', '2019-08-07 13:29:24', NULL, 'test', NULL, 1565324686654001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571016876018001, '/mypage/analy/get.do', '데이터 분석가요청 상세 조회', '데이터 분석가요청 상세 조회', '2019-10-14 10:34:39', NULL, 'test', NULL, 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452184001, '/qna/updateCnt.do', 'Q&A 조회수 증가', 'Q&A 조회수 증가', '2019-08-02 16:24:23', '2019-08-05 10:48:14', 'test', 'test', 1565324688055001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452177001, '/qna/getList.do', 'Q&A 목록 데이터 호출', 'Q&A 목록 데이터 호출', '2019-08-02 16:21:06', NULL, 'test', NULL, 1565324688055001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452178001, '/qna/get.do', 'Q&A 상세 데이터 호출', 'Q&A 상세 데이터 호출', '2019-08-02 16:21:35', '2020-07-28 11:03:13.120675', 'test', 'test', 1565324688055001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452164001, '/programgroup/getList.do', '프로그램 그룹 목록 데이터 호출', '프로그램 목록 데이터 호출', '2019-08-02 16:00:28', '2019-08-05 16:20:49', 'test', 'test', 1565324690168001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571019041660001, '/mypage/dataset/inquiry/getList.do', '문의사항 데이터 목록 조회', '문의사항 데이터 목록 조회', '2019-10-14 11:10:44', NULL, 'test', NULL, 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452088001, '/company/pageList.do', '목록 이동', 'sample desc', '2019-08-02 10:53:58', NULL, 'test', NULL, 1565324691569001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452007001, '/authgroup/pageList.do', '목록 화면 이동', 'sample desc', '2019-08-02 10:50:06', NULL, 'test', NULL, 1565324241947001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452009001, '/authgroup/getList.do', '목록 데이터 호출', 'sample desc', '2019-08-02 10:50:25', NULL, 'test', NULL, 1565324241947001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452175001, '/qna/pageModify.do', 'Q&A 수정 이동', 'Q&A 수정 이동', '2019-08-02 16:18:30', '2020-02-28 20:51:41.182525', 'test', 'test', 1565324688055001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452170001, '/programgroup/program/getList.do', '프로그램 목록 데이터 호출', '프로그램 목록 데이터 호출', '2019-08-02 16:03:43', '2019-08-05 16:22:37', 'test', 'test', 1565324690168001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326451998001, '/menu/pop/pageList.do', '메뉴 선택 팝업', 'sample desc', '2019-08-02 10:43:34', '2019-08-02 12:38:40', 'test', 'test', 1565324241932001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452166001, '/programgroup/modify.do', '프로그램 그룹 데이터 수정', '프로그램 그룹 데이터 수정', '2019-08-02 16:01:26', '2019-08-05 16:21:32', 'test', 'test', 1565324690168001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1570409646100001, '/request/pageList.do', '데이터셋 요청 목록 페이지 이동', '데이터셋 요청 목록 페이지 이동', '2019-10-07 09:54:06', '2020-11-11 10:53:24.483819', 'test', 'test', 1570409551486001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452097001, '/company/modify.do', '데이터 수정', 'sample desc', '2019-08-02 10:54:05', NULL, 'test', NULL, 1565324691569001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452096001, '/company/pageModify.do', '수정 이동', 'sample desc', '2019-08-02 10:54:04', NULL, 'test', NULL, 1565324691569001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452180001, '/qna/modify.do', 'Q&A 데이터 수정', 'Q&A 데이터 수정', '2019-08-02 16:21:55', '2020-07-28 13:32:06.106028', 'test', 'test', 1565324688055001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452059001, '/codegroup/modify.do', '데이터 수정', 'sample desc', '2019-08-02 10:53:03', NULL, 'test', NULL, 1565324685939001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326451995001, '/menu/modify.do', '데이터 수정', 'sample desc', '2019-08-02 10:41:36', NULL, 'test', NULL, 1565324241932001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326451994001, '/menu/pageModify.do', '수정 화면 이동', 'sample desc', '2019-08-02 10:41:16', NULL, 'test', NULL, 1565324241932001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452167001, '/programgroup/remove.do', '프로그램 그룹 데이터 삭제', '프로그램 그룹 데이터 삭제', '2019-08-02 16:01:47', '2019-08-05 16:21:44', 'test', 'test', 1565324690168001, 'delete', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452133001, '/faq/remove.do', '데이터 삭제', 're', '2019-08-02 10:53:15', '2019-08-12 17:16:53', 'test', 'test', 1565324688755001, 'delete', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326451997001, '/menu/remove.do', '데이터 삭제', '/menu/remove.do', '2019-08-02 10:42:03', '2019-08-06 16:41:28', 'test', 'test', 1565324241932001, 'delete', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452061001, '/codegroup/remove.do', '데이터 삭제', 'sample desc', '2019-08-02 10:53:04', NULL, 'test', NULL, 1565324685939001, 'delete', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452084001, '/user/pop/pageList.do', '팝업 이동', 'sample desc', '2019-08-02 10:53:10', NULL, 'test', NULL, 1565324686654001, NULL, 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452103001, '/company/pop/pageList.do', '팝업 이동', 'dsf', '2019-08-02 10:53:18', '2019-08-13 15:43:43', 'test', 'test', 1565324691569001, NULL, 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452185001, '/faq/updateCnt.do', 'FAQ 조회수 증가', 'FAQ 조회수 증가', '2019-08-02 20:13:32', '2020-02-26 10:07:19.668082', 'test', 'test', 1565324688755001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452182001, '/qna/reply.do', 'Q&A 답변 데이터 등록', 'Q&A 답변 데이터 등록', '2019-08-02 16:23:10', '2020-07-07 11:25:45.945567', 'test', 'test', 1565324688055001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452157001, '/datacomplaint/getList.do', '목록 데이터 호출', 'sample desc', '2019-08-02 10:53:54', '2020-04-29 11:47:18.053833', 'test', 'test', 1565324690868001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452124001, '/faq/pageList.do', '목록 이동', 'ㄴㅇ ㅇㅇ', '2019-08-02 10:53:38', '2020-02-26 10:07:07.60356', 'test', 'test', 1565324688755001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452172001, '/programgroup/program/modify.do', '프로그램 데이터 수정', '프로그램 데이터 수정', '2019-08-02 16:04:25', '2019-08-05 16:22:58', 'test', 'test', 1565324690168001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452115001, '/notice/modify.do', '데이터 수정', 'ㅅㅅㅅㅈㅈㅈ ㅅㅈ ㅅㅈ ㅅㅅㅅ ㅇㅇㅇ ㅇㅇ ㄹㄹ ㅇㅇ', '2019-08-02 10:54:17', '2019-08-12 17:49:32', 'test', 'test', 1565324687355001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452127001, '/faq/create.do', '데이터 등록', 'sdf df df df df df df re re re re re', '2019-08-02 10:53:16', '2019-08-12 17:17:16', 'test', 'test', 1565324688755001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326451980001, '/menu/pageEdit.do', '등록 화면 이동', 'sample desc', '2019-08-02 10:40:05', '2020-02-26 16:14:25.621077', 'test', 'test', 1565324241932001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452122001, '/qna/pageEdit.do', 'Q&A 등록 이동', '등록', '2019-08-02 10:53:12', '2019-08-02 16:18:44', 'test', 'test', 1565324688055001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452137001, '/category/pageEdit.do', '등록 이동', 'sample desc', '2019-08-02 10:54:18', '2019-08-13 13:43:57', 'test', 'test', 1565324689467001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452197001, '/category/getCategoryList.do', '카테고리 팝업', '카테고리 팝업', '2019-08-06 18:21:16', NULL, 'test', NULL, 1565324689467001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571016755075001, '/mypage/analy/create.do', '데이터 분석가요청 등록', '데이터 분석가요청 등록', '2019-10-14 10:32:38', NULL, 'test', NULL, 1571014820930001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452078001, '/user/pageEdit.do', '등록 이동', 'sample desc', '2019-08-02 10:53:09', NULL, 'test', NULL, 1565324686654001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452195001, '/datacomplaint/downloadFile.do', '파일 다운로드', '파일 다운로드', '2019-08-05 17:41:35', '2020-07-28 17:04:03.407753', 'test', 'test', 1565324690868001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1570669477508001, '/analy/pageList.do', '분석가요청 목록 이동', 'sample desc', '2019-10-10 10:04:40', NULL, 'test', NULL, 1570409551486001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1570669504829001, '/analy/getList.do', '분석가요청 목록 데이터 호출', 'sample desc', '2019-10-10 10:05:07', NULL, 'test', NULL, 1570409551486001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452109001, '/notice/pageDetail.do', '상세 이동', 'df', '2019-08-02 10:54:10', '2019-08-12 17:05:48', 'test', 'test', 1565324687355001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452043001, '/codegroup/getList.do', '목록 데이터 호출', 'sample desc', '2019-08-02 10:53:19', NULL, 'test', NULL, 1565324685939001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565679817046001, '/allCodeGroup', '코드그룹전체조회', '코드그룹전체조회', '2019-08-13 16:03:38', '2019-08-14 09:23:04', 'test', 'test', 1565324685939001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571018896424001, '/mypage/dataset/pageList.do', '데이터 명세서 목록 화면 이동', '데이터 명세서 목록 화면 이동', '2019-10-14 11:08:19', NULL, 'test', NULL, 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571018916279001, '/mypage/userstats/pageList.do', '데이터 사용자현황 화면이동', '데이터 사용자현황 화면이동', '2019-10-14 11:08:39', NULL, 'test', NULL, 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452134001, '/category/pageList.do', '목록 이동', 'sample desc', '2019-08-02 10:54:19', '2019-08-13 13:44:09', 'test', 'test', 1565324689467001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452072001, '/user/getList.do', '목록 데이터 호출', 'sample desc', '2019-08-02 10:54:13', NULL, 'test', NULL, 1565324686654001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452075001, '/user/get.do', '상세 데이터 호출', 'sample desc', '2019-08-02 10:54:14', NULL, 'test', NULL, 1565324686654001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452076001, '/user/pageDetail.do', '상세 이동', 'sample desc', '2019-08-02 10:54:15', NULL, 'test', NULL, 1565324686654001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452032001, '/authgroup/get.do', '상세 데이터 호출', 'sample desc', '2019-08-02 12:39:57', NULL, 'test', NULL, 1565324241947001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452034001, '/authgroup/get.do', '상세 데이터 호출', 'sample desc', '2019-08-02 12:40:31', NULL, 'test', NULL, 1565324241947001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452110001, '/notice/get.do', '상세 데이터 호출', 'ㅇㅇ', '2019-08-02 10:53:32', '2019-08-12 17:50:46', 'test', 'test', 1565324687355001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565742204911001, '/codegroup/check.do', '코드그룹ID 중복체크', '코드그룹ID 중복체크', '2019-08-14 09:23:28', NULL, 'test', NULL, 1565324685939001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571016483445001, '/mypage/wishlist/pageList.do', '관심데이터목록 화면이동', '관심데이터목록 화면이동', '2019-10-14 10:28:06', NULL, 'test', NULL, 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452162001, '/programgroup/get.do', '프로그램 그룹 상세 데이터 호출', '프로그램 그룹 상세 데이터 호출', '2019-08-02 16:00:01', '2019-08-05 16:20:35', 'test', 'test', 1565324690168001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452014001, '/authgroup/pageDetail.do', '상세 화면 이동', 'sample desc', '2019-08-02 10:51:44', '2019-08-02 11:29:25', 'test', 'test', 1565324241947001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452187001, '/datacomplaint/updateCnt.do', '조회수 증가', 'sample desc', '2019-08-05 13:36:04', '2020-02-26 10:02:03.890115', 'test', 'test', 1565324690868001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1570697416220001, '/release/modify.do', '출시요청 승인/반려', '출시요청 승인/반려', '2019-10-10 17:50:16', NULL, 'test', NULL, 1570409551486001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452048001, '/codegroup/code/getList.do', '코드 목록 데이터 호출', 'sample desc', '2019-08-02 10:54:12', NULL, 'test', NULL, 1565324685939001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452080001, '/user/pageModify.do', '수정 이동', 'sample desc', '2019-08-02 10:54:16', NULL, 'test', NULL, 1565324686654001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452019001, '/authgroup/pageModify.do', '수정 화면 이동', 'sample desc', '2019-08-02 10:53:33', '2019-08-02 10:53:58', 'test', 'test', 1565324241947001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452015001, '/authgroup/user/modify.do', '사용자 데이터 수정', 'sample desc', '2019-08-02 10:52:44', NULL, 'test', NULL, 1565324241947001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452017001, '/authgroup/menu/modify.do', '사용자 메뉴 수정', 'sample desc', '2019-08-02 10:53:02', NULL, 'test', NULL, 1565324241947001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452020001, '/authgroup/modify.do', '데이터 수정', 'sample desc', '2019-08-02 10:53:49', NULL, 'test', NULL, 1565324241947001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571103723800001, '/analy/modify.do', '분석가요청 승인', '분석가요청 승인', '2019-10-15 10:42:03.820829', NULL, 'test', NULL, 1570409551486001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452065001, '/codegroup/code/modify.do', '데이터 수정', 'sample desc', '2019-08-02 10:53:07', NULL, 'test', NULL, 1565324685939001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452132001, '/faq/modify.do', '데이터 수정', ' df df df', '2019-08-02 10:53:14', '2020-07-16 17:01:09.364262', 'test', 'test', 1565324688755001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452114001, '/notice/pageModify.do', '수정 이동', 'ㅇㅇ', '2019-08-02 10:53:33', '2019-08-12 17:50:52', 'test', 'test', 1565324687355001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452067001, '/codegroup/code/remove.do', '데이터 삭제', 'sample desc', '2019-08-02 10:53:08', NULL, 'test', NULL, 1565324685939001, 'delete', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452174001, '/programgroup/program/remove.do', '프로그램 데이터 삭제', '프로그램 데이터 삭제', '2019-08-02 16:04:51', '2019-08-05 16:23:10', 'test', 'test', 1565324690168001, 'delete', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452022001, '/authgroup/remove.do', '데이터 삭제', 'sample desc', '2019-08-02 10:54:13', NULL, 'test', NULL, 1565324241947001, 'delete', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452196001, '/datacomplaint/remove.do', '데이터 민원 삭제', '/datacomplaint/remove.do', '2019-08-06 16:43:55', '2020-02-26 10:02:09.034534', 'test', 'test', 1565324690868001, 'delete', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574649549957001, '/analy/get.do', '분석가요청 상세 조회', '분석가요청 상세 조회', '2019-11-25 11:39:09.871043', NULL, 'cityhub08', NULL, 1570409551486001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574833293822001, '/dataset/use/create.do', '데이터셋 이용신청 등록', '데이터셋 이용신청 등록', '2019-11-27 14:41:33.69021', NULL, 'cityhub08', NULL, 1570501508372001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1575336334691001, '/dataset/pageModify.do', '데이터셋 수정화면 이동', '데이터셋 수정화면 이동', '2019-12-03 10:25:34.606204', NULL, 'cityhub08', NULL, 1570501508372001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1575420242472001, '/mypage/adaptor/pageList.do', '어댑터 신청 내역 조회', '어댑터 신청 내역 조회', '2019-12-04 09:44:01.200967', NULL, 'cityhub08', NULL, 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574900889025001, '/dataset/qna/modify.do', '데이터셋 문의하기 수정', '데이터셋 문의하기 수정', '2019-11-28 09:28:10.875629', NULL, 'djko', NULL, 1570501508372001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571983697932001, '/sesarch/getList.do', '통합검색 데이터 조회', '통합검색 데이터 조회', '2019-10-25 15:08:18.589403', '2020-02-26 09:30:36.638333', 'cityhub08', 'cityhub08', 1571983646537001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571016639927001, '/mypage/analy/pageList.do', '데이터 분석가요청 목록 화면 이동', '데이터 분석가요청 목록 화면 이동', '2019-10-14 10:30:43', '2020-02-26 09:32:05.050797', 'test', 'test', 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452036001, '/codegroup/pageList.do', '목록 이동', 'sample desc', '2019-08-02 10:54:11', '2020-02-26 09:33:15.262135', 'test', 'test', 1565324685939001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1582890040972001, 'test2222', 'test2222', 'test2222', '2020-02-28 20:40:40.972802', NULL, 'cityhub08', NULL, 0, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574922154396001, '/dataset/create.do', '데이터셋 데이터 등록', '데이터셋 데이터 등록
', '2019-11-28 15:22:34.286263', NULL, 'djko', NULL, 1570501508372001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574900535437001, '/dataset/qna/create.do', '데이터셋 문의하기 등록', '데이터셋 문의하기 등록', '2019-11-28 09:22:17.293317', '2019-11-28 09:28:16.26324', 'cityhub08', 'cityhub08', 1570501508372001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452146001, '/programgroup/pageEdit.do', '프로그램 그룹 등록 이동', 'sample desc', '2019-08-02 10:53:50', '2019-08-05 16:19:38', 'test', 'test', 1565324690168001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574833101647001, '/dataset/wishlist/create.do', '데이터셋 관심상품 등록', '데이터셋 관심상품 등록', '2019-11-27 14:38:21.516181', NULL, 'cityhub08', NULL, 1570501508372001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574928615990001, '/dataset/satisfaction/create.do', '만족도 평가 등록', '만족도 평가 등록', '2019-11-28 17:10:15.779717', NULL, 'djko', NULL, 1570501508372001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574923198731001, '/dataset/use/create.do', '데이터셋 이용신청 등록', '데이터셋 이용신청 등록', '2019-11-28 15:39:58.606741', NULL, 'djko', NULL, 1570501508372001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571016526098001, '/mypage/usage/getList.do', '데이터 이용내역 조회', '데이터 이용내역 조회', '2019-10-14 10:28:49', NULL, 'test', NULL, 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1589188384979001, '/user/getList.do', '사용자목록조회', '사용자목록조회', '2020-05-11 18:13:06.643091', NULL, 'cityhub08', NULL, 1565324241947001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571016735939001, '/mypage/analy/pageEdit.do', '데이터 분석가요청 등록 화면 이동', '데이터 분석가요청 등록 화면 이동', '2019-10-14 10:32:19', NULL, 'test', NULL, 1571014820930001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571901255057001, '/intro/datahub/pageList.do', '데이터 허브 소개', '데이터 허브 소개', '2019-10-24 16:14:17.623387', NULL, 'cityhub08', NULL, 1571899642620001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571901284056001, '/intro/provsvc/pageList.do', '제공 서비스 소개', '제공 서비스 소개', '2019-10-24 16:14:46.62183', NULL, 'cityhub08', NULL, 1571899642620001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1573013993794001, '/authgroup/menu/getList.do', '읽기', '읽기', '2019-11-06 13:19:54.18139', NULL, 'cityhub08', NULL, 1565324241947001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574649516394001, '/analy/pageDetail.do', '분석가요청 상세 이동', '분석가요청 상세 화면이동', '2019-11-25 11:38:36.310808', '2019-11-25 11:38:46.721286', 'cityhub08', 'cityhub08', 1570409551486001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574828440666001, '/dataset/pageList.do', '데이터셋 목록화면 이동', '데이터셋 목록화면 이동', '2019-11-27 13:20:40.620307', NULL, 'cityhub08', NULL, 1570501508372001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574828458589001, '/dataset/getList.do', '데이터셋 목록 조회', '데이터셋 목록 조회', '2019-11-27 13:20:58.543392', NULL, 'cityhub08', NULL, 1570501508372001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574835051375001, '/dataset/pageDetail.do', '데이터셋 상세화면 이동', '데이터셋 상세화면 이동', '2019-11-27 15:10:51.220059', NULL, 'cityhub08', NULL, 1570501508372001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574835089388001, '/dataset/get.do', '데이터셋 상세 조회', '데이터셋 상세 조회', '2019-11-27 15:11:29.232865', NULL, 'cityhub08', NULL, 1570501508372001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574902839744001, '/dataset/pageEdit.do', '데이터셋 등록화면 이동', '데이터셋 등록화면 이동', '2019-11-28 10:00:41.556295', NULL, 'cityhub08', NULL, 1570501508372001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574915012061001, '/dataset/getCategoryList.do', '카테고리 목록 조회', '카테고리 목록 조회', '2019-11-28 13:23:34.24419', NULL, 'cityhub08', NULL, 1570501508372001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574917673776001, '/dataset/model/getList.do', '데이터 모델 리스트 조회', '데이터 모델 리스트 조회', '2019-11-28 14:07:55.937218', NULL, 'djko', NULL, 1570501508372001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574917745038001, '/dataset/instance/getList.do', '데이터 인스턴스 조회', '데이터 인스턴스 조회', '2019-11-28 14:09:07.198407', NULL, 'djko', NULL, 1570501508372001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574929968133001, '/dataset/sample/get.do', '데이터 샘플 조회', '데이터 샘플 조회', '2019-11-28 17:32:47.89634', NULL, 'cityhub08', NULL, 1570501508372001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1575009000045001, 'test', 'test', 'testdfdddd', '2019-11-29 15:29:59.714104', '2019-11-29 15:30:08.971616', 'cityhub08', 'cityhub08', 1565324241932001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1582676833815001, '/search/pageList.do', '통합검색 페이지 이동', '통합검색 페이지 이동', '2020-02-26 09:27:14.598888', NULL, 'cityhub08', NULL, 1571983646537001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452155001, '/datacomplaint/pageList.do', '목록 이동', 'sample desc', '2019-08-02 10:53:53', '2020-04-29 14:02:32.262483', 'test', 'test', 1565324690868001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452128001, '/faq/pageDetail.do', '상세 이동', 'ㄴㅇㅊㄹ', '2019-08-02 10:53:40', '2020-02-26 10:07:14.290599', 'test', 'test', 1565324688755001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452151001, '/programgroup/pageModify.do', '프로그램 그룹 수정 이동', 'sample desc', '2019-08-02 10:53:52', '2019-08-05 16:20:23', 'test', 'test', 1565324690168001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1571372085470001, '/notice/updateCnt.do', '조회수 증가', '조회수 증가', '2019-10-18 13:14:47.468235', NULL, 'cityhub08', NULL, 1565324687355001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1575336636305001, '/dataset/modify.do', '데이터셋 데이터 수정', '데이터셋 데이터 수정', '2019-12-03 10:30:36.213405', NULL, 'cityhub08', NULL, 1570501508372001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574833264316001, '/dataset/use/remove.do', '데이터셋 이용신청 해제', '데이터셋 이용신청 해제', '2019-11-27 14:41:04.184416', NULL, 'cityhub08', NULL, 1570501508372001, 'delete', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452181001, '/qna/remove.do', 'Q&A 데이터 삭제', 'Q&A 데이터 삭제', '2019-08-02 16:22:17', '2019-12-05 17:26:28.986114', 'test', 'test', 1565324688055001, 'delete', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574923228218001, '/dataset/use/remove.do', '데이터셋 이용신청 해제', '데이터셋 이용신청 해제', '2019-11-28 15:40:28.096656', NULL, 'djko', NULL, 1570501508372001, 'delete', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1574833053301001, '/dataset/wishlist/remove.do', '데이터셋 관심상품 해제', '데이터셋 관심상품 해제', '2019-11-27 14:37:33.170732', NULL, 'cityhub08', NULL, 1570501508372001, 'delete', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1573014099853001, '/authgroup/user/getList.do', '사용자 목록', '상세 데이터 읽기 - 사용자 목록', '2019-11-06 13:21:40.239183', NULL, 'cityhub08', NULL, 1565324241947001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1565326452161001, '/datacomplaint/part/modify.do', '답변 등록', 'sample desc', '2019-08-02 10:53:57', '2020-07-16 19:43:02.979163', 'test', 'test', 1565324690868001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1594974564202001, '/codegroup/code/get.do', '코드 상세', '코드 상세', '2020-07-17 17:29:24.566634', NULL, 'cityhub08', NULL, 1565324685939001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1595225640102001, '/downloadExcelFile.do', '데이터셋 엑셀 다운로드', '데이터셋 엑셀 다운로드', '2020-07-20 15:14:00.365585', NULL, 'cityhub08', NULL, 1570501508372001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1595901248476001, '/qna/downloadFile.do', 'Q&A 파일 다운로드', 'Q&A 파일 다운로드', '2020-07-28 10:54:07.124797', NULL, 'cityhub08', NULL, 1565324688055001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1595901353707001, '/faq/downloadFile.do', 'FAQ 파일 다운로드', 'FAQ 파일 다운로드', '2020-07-28 10:55:52.353353', NULL, 'cityhub08', NULL, 1565324688755001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1595901425654001, '/notice/downloadFile.do', '공지사항 파일 다운로드', '공지사항 파일 다운로드', '2020-07-28 10:57:04.298196', NULL, 'cityhub08', NULL, 1565324687355001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1595910415900001, '/category/downloadFile.do', '카테고리 이미지 다운로드', '카테고리 이미지 다운로드', '2020-07-28 13:26:55.814811', NULL, 'cityhub08', NULL, 1565324689467001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1603944252801001, '/mypage/token/transfer.do', '블록체인 인센티브 토큰 전송', '블록체인 인센티브 토큰 전송', '2020-10-29 13:04:11.505965', '2020-10-29 13:05:05.183626', 'cityhub08', 'cityhub08', 1603944205862001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1603944292626001, '/mypage/token/log/getList.do', '블록체인 인센티브 토큰 발급 내역 조회', '블록체인 인센티브 토큰 발급 내역 조회', '2020-10-29 13:04:51.330048', '2020-10-29 13:09:56.694364', 'cityhub08', 'cityhub08', 1603944205862001, 'write', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1603946675662001, '/mypage/token/log/pageList.do', '인센티브 토큰 발급 내역 페이지 이동', '인센티브 토큰 발급 내역 페이지 이동', '2020-10-29 13:44:34.328527', NULL, 'cityhub08', NULL, 1603944205862001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1604034877819001, '/dataset/user/getList.do', '데이터셋 제공자 목록 조회', '데이터셋 제공자 목록 조회', '2020-10-30 14:14:38.024243', NULL, 'cityhub08', NULL, 1570501508372001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1604036972693001, '/dataset/datasetorigin/getList.do', '데이터셋 오리진 목록 조회', '데이터셋 오리진 목록 조회', '2020-10-30 14:49:32.864482', NULL, 'cityhub08', NULL, 1570501508372001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1604280967421001, '/features/pageDetail.do', '확장기능관리 페이지 이동', '확장기능관리 페이지 이동', '2020-11-02 10:36:09.25591', '2020-11-09 09:55:39.332109', 'cityhub08', 'cityhub08', 1604280927875001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1604888266420001, '/features/modify.do', '확장기능관리 등록/수정', '확장기능관리 등록/수정', '2020-11-09 11:17:46.380359', NULL, 'cityhub08', NULL, 1604280927875001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1604912418480001, '/pricePolicies/pageList.do', '가격정책관리 목록 페이지 이동', '가격정책관리 목록 페이지 이동', '2020-11-09 18:00:18.072284', NULL, 'cityhub08', NULL, 1604909927898001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1604912441579001, '/pricePolicies/pageDetail.do', '가격정책관리 상세 페이지 이동', '가격정책관리 상세 페이지 이동', '2020-11-09 18:00:41.168942', NULL, 'cityhub08', NULL, 1604909927898001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1604912467115001, '/pricePolicies/pageRegist.do', '가격정책관리 등록 페이지 이동', '가격정책관리 등록 페이지 이동', '2020-11-09 18:01:06.703554', NULL, 'cityhub08', NULL, 1604909927898001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1604912496100001, '/pricePolicies/pageModify.do', '가격정책관리 수정 페이지 이동', '가격정책관리 수정 페이지 이동', '2020-11-09 18:01:35.688845', NULL, 'cityhub08', NULL, 1604909927898001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1604912518157001, '/pricePolicies/getList.do', '가격정책관리 목록 조회', '가격정책관리 목록 조회', '2020-11-09 18:01:57.745683', NULL, 'cityhub08', NULL, 1604909927898001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1604912547028001, '/pricePolicies/get.do', '가격정책관리 상세 조회', '가격정책관리 상세 조회', '2020-11-09 18:02:26.616235', NULL, 'cityhub08', NULL, 1604909927898001, 'read', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1604912594168001, '/pricePolicies/modify.do', '가격정책관리 수정', '가격정책관리 수정', '2020-11-09 18:03:13.757965', NULL, 'cityhub08', NULL, 1604909927898001, 'update', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1604912615901001, '/pricePolicies/remove.do', '가격정책관리 삭제', '가격정책관리 삭제', '2020-11-09 18:03:35.489607', NULL, 'cityhub08', NULL, 1604909927898001, 'delete', 'Y', 'Y');
INSERT INTO portal.portal_program_tb VALUES (1604912567139001, '/pricePolicies/create.do', '가격정책관리 등록', '가격정책관리 등록', '2020-11-09 18:02:46.726701', '2020-11-10 11:28:46.785029', 'cityhub08', 'cityhub08', 1604909927898001, 'write', 'Y', 'Y');


--
-- TOC entry 3122 (class 0 OID 16613)
-- Dependencies: 217
-- Data for Name: portal_programgroup_tb; Type: TABLE DATA; Schema: portal; Owner: postgres
--

INSERT INTO portal.portal_programgroup_tb VALUES (1571899642620001, '소개', '2019-10-24 15:47:25.212509', 'cityhub08', '소개', NULL, NULL, '28', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1571965218616001, '분석데이터', '2019-10-25 10:00:19.459841', 'cityhub08', '분석데이터
통계
- 맞춤형
- 예측
- 통계', NULL, NULL, '29', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1571966107022001, '활용후기', '2019-10-25 10:15:07.851016', 'cityhub08', '활용후기', NULL, NULL, '30', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1571983646537001, '검색', '2019-10-25 15:07:27.198853', 'cityhub08', '통합검색', NULL, NULL, '31', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1573005999696001, '분석', '2019-11-06 11:06:41.165643', 'cityhub08', '분석', NULL, NULL, '33', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1573006633612001, '고객서비스', '2019-11-06 11:17:15.048531', 'cityhub08', '고객서비스', NULL, NULL, '34', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1571014820930001, '마이페이지', '2019-10-14 10:00:24', 'cityhub08', '마이페이지', NULL, NULL, '27', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1565324685939001, '코드관리', '2019-08-02 10:35:01', 'cityhub08', '코드관리', '2020-04-24 15:17:13.828623', 'cityhub08', '5', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1565324691569001, '업체관리', '2019-08-02 10:37:01', 'cityhub08', '업체관리', '2019-08-13 13:25:26', 'admin', '13', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1565324690868001, '데이터민원관리', '2019-08-02 10:36:51', 'cityhub08', '데이터민원관리', '2020-04-29 15:53:47.781603', 'cityhub08', '12', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1603944205862001, '블록체인 인센티브 토큰', '2020-10-29 13:03:24.572483', 'cityhub08', '블록체인 인센티브 토큰', NULL, NULL, '38', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1565324690168001, '프로그램관리', '2019-08-02 10:36:30', 'cityhub08', '프로그램관리', NULL, NULL, '11', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1604280927875001, '확장기능관리', '2020-11-02 10:35:29.710637', 'cityhub08', '확장기능관리', '2020-11-09 09:55:06.644103', 'cityhub08', '39', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1570501508372001, '데이터셋관리', '2019-10-08 11:25:08', 'cityhub08', '데이터셋관리', '2019-10-08 11:26:23', 'cityhub08', '26', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1570409551486001, '승인관리', '2019-10-07 09:52:32', 'cityhub08', '승인관리', '2019-10-10 10:02:42', 'cityhub08', '25', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1565324689467001, '카테고리관리', '2019-08-02 10:36:20', 'cityhub08', '카테고리관리', NULL, NULL, '10', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1565324688755001, 'FAQ관리', '2019-08-02 10:36:10', 'cityhub08', 'FAQ관리', NULL, NULL, '9', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1565324688055001, 'Q&A관리', '2019-08-02 10:36:01', 'cityhub08', 'Q&A관리', '2019-08-13 13:22:53', 'admin', '8', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1565324687355001, '공지사항관리', '2019-08-02 10:35:26', 'cityhub08', '공지사항관리', '2019-08-13 13:28:25', 'admin', '7', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1565324686654001, '사용자관리', '2019-08-02 10:35:09', 'cityhub08', '사용자관리', NULL, NULL, '6', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1604909927898001, '가격정책관리', '2020-11-09 17:18:47.537686', 'cityhub08', '가격정책관리', NULL, NULL, '40', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1565324241947001, '권한관리', '2019-08-02 10:34:52', 'cityhub08', '권한관리', NULL, NULL, '4', 'Y');
INSERT INTO portal.portal_programgroup_tb VALUES (1565324241932001, '메뉴관리', '2019-08-02 10:34:44', 'cityhub08', '메뉴관리', '2020-04-24 14:50:00.717385', 'cityhub08', '3', 'Y');


--
-- TOC entry 3386 (class 0 OID 0)
-- Dependencies: 223
-- Name: seq_portal_authgroup_tb; Type: SEQUENCE SET; Schema: portal; Owner: postgres
--

SELECT pg_catalog.setval('portal.seq_portal_authgroup_tb', 29, true);


--
-- TOC entry 3388 (class 0 OID 0)
-- Dependencies: 224
-- Name: seq_portal_codegroup_tb; Type: SEQUENCE SET; Schema: portal; Owner: postgres
--

SELECT pg_catalog.setval('portal.seq_portal_codegroup_tb', 8, true);


--
-- TOC entry 3389 (class 0 OID 0)
-- Dependencies: 225
-- Name: seq_portal_datacomplain_tb; Type: SEQUENCE SET; Schema: portal; Owner: postgres
--

SELECT pg_catalog.setval('portal.seq_portal_datacomplain_tb', 169, true);


--
-- TOC entry 3390 (class 0 OID 0)
-- Dependencies: 226
-- Name: seq_portal_datacomplainfile_tb; Type: SEQUENCE SET; Schema: portal; Owner: postgres
--

SELECT pg_catalog.setval('portal.seq_portal_datacomplainfile_tb', 73, true);


--
-- TOC entry 3391 (class 0 OID 0)
-- Dependencies: 227
-- Name: seq_portal_faq_tb; Type: SEQUENCE SET; Schema: portal; Owner: postgres
--

SELECT pg_catalog.setval('portal.seq_portal_faq_tb', 165, true);


--
-- TOC entry 3392 (class 0 OID 0)
-- Dependencies: 228
-- Name: seq_portal_faqfile_tb; Type: SEQUENCE SET; Schema: portal; Owner: postgres
--

SELECT pg_catalog.setval('portal.seq_portal_faqfile_tb', 37, true);


--
-- TOC entry 3393 (class 0 OID 0)
-- Dependencies: 229
-- Name: seq_portal_menu_tb; Type: SEQUENCE SET; Schema: portal; Owner: postgres
--

SELECT pg_catalog.setval('portal.seq_portal_menu_tb', 82, true);


--
-- TOC entry 3394 (class 0 OID 0)
-- Dependencies: 230
-- Name: seq_portal_notice_tb; Type: SEQUENCE SET; Schema: portal; Owner: postgres
--

SELECT pg_catalog.setval('portal.seq_portal_notice_tb', 69, true);


--
-- TOC entry 3395 (class 0 OID 0)
-- Dependencies: 231
-- Name: seq_portal_program_tb; Type: SEQUENCE SET; Schema: portal; Owner: postgres
--

SELECT pg_catalog.setval('portal.seq_portal_program_tb', 288, true);


--
-- TOC entry 3396 (class 0 OID 0)
-- Dependencies: 232
-- Name: seq_portal_programgroup_tb; Type: SEQUENCE SET; Schema: portal; Owner: postgres
--

SELECT pg_catalog.setval('portal.seq_portal_programgroup_tb', 40, true);


--
-- TOC entry 3397 (class 0 OID 0)
-- Dependencies: 233
-- Name: seq_portal_qna_tb; Type: SEQUENCE SET; Schema: portal; Owner: postgres
--

SELECT pg_catalog.setval('portal.seq_portal_qna_tb', 71, true);


--
-- TOC entry 3398 (class 0 OID 0)
-- Dependencies: 234
-- Name: seq_portal_qnafile_tb; Type: SEQUENCE SET; Schema: portal; Owner: postgres
--

SELECT pg_catalog.setval('portal.seq_portal_qnafile_tb', 60, true);


--
-- TOC entry 2940 (class 2606 OID 16679)
-- Name: portal_authgroup_tb pk_portal_authgroup_tb; Type: CONSTRAINT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_authgroup_tb
    ADD CONSTRAINT pk_portal_authgroup_tb PRIMARY KEY (authgroup_oid);


--
-- TOC entry 2946 (class 2606 OID 16681)
-- Name: portal_codegroup_tb pk_portal_codegroup_tb; Type: CONSTRAINT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_codegroup_tb
    ADD CONSTRAINT pk_portal_codegroup_tb PRIMARY KEY (codegroup_id);


--
-- TOC entry 2948 (class 2606 OID 16683)
-- Name: portal_datacomplain_tb pk_portal_datacomplain_tb; Type: CONSTRAINT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_datacomplain_tb
    ADD CONSTRAINT pk_portal_datacomplain_tb PRIMARY KEY (datacomplain_oid);


--
-- TOC entry 2950 (class 2606 OID 16685)
-- Name: portal_datacomplainfile_tb pk_portal_datacomplainfile_tb; Type: CONSTRAINT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_datacomplainfile_tb
    ADD CONSTRAINT pk_portal_datacomplainfile_tb PRIMARY KEY (dcp_file_oid);


--
-- TOC entry 2952 (class 2606 OID 16687)
-- Name: portal_faq_tb pk_portal_faq_tb; Type: CONSTRAINT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_faq_tb
    ADD CONSTRAINT pk_portal_faq_tb PRIMARY KEY (faq_oid);


--
-- TOC entry 2954 (class 2606 OID 16689)
-- Name: portal_faqfile_tb pk_portal_faqfile_tb; Type: CONSTRAINT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_faqfile_tb
    ADD CONSTRAINT pk_portal_faqfile_tb PRIMARY KEY (faqfile_oid);


--
-- TOC entry 2956 (class 2606 OID 16691)
-- Name: portal_menu_tb pk_portal_menu_tb; Type: CONSTRAINT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_menu_tb
    ADD CONSTRAINT pk_portal_menu_tb PRIMARY KEY (menu_oid);


--
-- TOC entry 2958 (class 2606 OID 16693)
-- Name: portal_notice_tb pk_portal_notice_tb; Type: CONSTRAINT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_notice_tb
    ADD CONSTRAINT pk_portal_notice_tb PRIMARY KEY (notice_oid);


--
-- TOC entry 2960 (class 2606 OID 16695)
-- Name: portal_noticefile_tb pk_portal_noticefile_tb; Type: CONSTRAINT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_noticefile_tb
    ADD CONSTRAINT pk_portal_noticefile_tb PRIMARY KEY (noticefile_oid);


--
-- TOC entry 2962 (class 2606 OID 16697)
-- Name: portal_program_tb pk_portal_program_tb; Type: CONSTRAINT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_program_tb
    ADD CONSTRAINT pk_portal_program_tb PRIMARY KEY (program_oid);


--
-- TOC entry 2964 (class 2606 OID 16699)
-- Name: portal_programgroup_tb pk_portal_programgroup_tb; Type: CONSTRAINT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_programgroup_tb
    ADD CONSTRAINT pk_portal_programgroup_tb PRIMARY KEY (programgroup_oid);


--
-- TOC entry 2966 (class 2606 OID 16701)
-- Name: portal_qna_tb pk_portal_qna_tb; Type: CONSTRAINT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_qna_tb
    ADD CONSTRAINT pk_portal_qna_tb PRIMARY KEY (qna_oid);


--
-- TOC entry 2968 (class 2606 OID 16703)
-- Name: portal_qnafile_tb pk_portal_qnafile_tb; Type: CONSTRAINT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_qnafile_tb
    ADD CONSTRAINT pk_portal_qnafile_tb PRIMARY KEY (qnafile_oid);


--
-- TOC entry 2974 (class 2606 OID 16705)
-- Name: portal_reviewfile_tb pk_portal_reviewfile_tb; Type: CONSTRAINT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_reviewfile_tb
    ADD CONSTRAINT pk_portal_reviewfile_tb PRIMARY KEY (reviewfile_oid);



--
-- TOC entry 2944 (class 2606 OID 16709)
-- Name: portal_code_tb portal_code_tb_pk; Type: CONSTRAINT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_code_tb
    ADD CONSTRAINT portal_code_tb_pk PRIMARY KEY (code_id, codegroup_id);


--
-- TOC entry 2970 (class 2606 OID 16711)
-- Name: portal_review_datasetmap_tb portal_review_datasetmap_tb_pk; Type: CONSTRAINT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_review_datasetmap_tb
    ADD CONSTRAINT portal_review_datasetmap_tb_pk PRIMARY KEY (ds_oid, review_oid);


--
-- TOC entry 2972 (class 2606 OID 16713)
-- Name: portal_review_tb portal_review_tb_pk; Type: CONSTRAINT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_review_tb
    ADD CONSTRAINT portal_review_tb_pk PRIMARY KEY (review_oid);


--
-- TOC entry 2975 (class 2606 OID 16714)
-- Name: portal_review_datasetmap_tb portal_review_datasetmap_tb_fk; Type: FK CONSTRAINT; Schema: portal; Owner: postgres
--

ALTER TABLE ONLY portal.portal_review_datasetmap_tb
    ADD CONSTRAINT portal_review_datasetmap_tb_fk FOREIGN KEY (review_oid) REFERENCES portal.portal_review_tb(review_oid);


--
-- TOC entry 3156 (class 0 OID 0)
-- Dependencies: 202
-- Name: TABLE portal_authgroup_tb; Type: ACL; Schema: portal; Owner: postgres
--

GRANT ALL ON TABLE portal.portal_authgroup_tb TO smartcity_portal;


--
-- TOC entry 3164 (class 0 OID 0)
-- Dependencies: 203
-- Name: TABLE portal_authgroupmenumap_tb; Type: ACL; Schema: portal; Owner: postgres
--

GRANT ALL ON TABLE portal.portal_authgroupmenumap_tb TO smartcity_portal;


--
-- TOC entry 3168 (class 0 OID 0)
-- Dependencies: 204
-- Name: TABLE portal_authgroupusermap_tb; Type: ACL; Schema: portal; Owner: postgres
--

GRANT ALL ON TABLE portal.portal_authgroupusermap_tb TO smartcity_portal;


--
-- TOC entry 3194 (class 0 OID 0)
-- Dependencies: 206
-- Name: TABLE portal_code_tb; Type: ACL; Schema: portal; Owner: postgres
--

GRANT ALL ON TABLE portal.portal_code_tb TO smartcity_portal;


--
-- TOC entry 3205 (class 0 OID 0)
-- Dependencies: 207
-- Name: TABLE portal_codegroup_tb; Type: ACL; Schema: portal; Owner: postgres
--

GRANT ALL ON TABLE portal.portal_codegroup_tb TO smartcity_portal;


--
-- TOC entry 3219 (class 0 OID 0)
-- Dependencies: 208
-- Name: TABLE portal_datacomplain_tb; Type: ACL; Schema: portal; Owner: postgres
--

GRANT ALL ON TABLE portal.portal_datacomplain_tb TO smartcity_portal;


--
-- TOC entry 3229 (class 0 OID 0)
-- Dependencies: 209
-- Name: TABLE portal_datacomplainfile_tb; Type: ACL; Schema: portal; Owner: postgres
--

GRANT ALL ON TABLE portal.portal_datacomplainfile_tb TO smartcity_portal;


--
-- TOC entry 3233 (class 0 OID 0)
-- Dependencies: 210
-- Name: TABLE portal_ext_features; Type: ACL; Schema: portal; Owner: postgres
--

GRANT ALL ON TABLE portal.portal_ext_features TO smartcity_portal;


--
-- TOC entry 3245 (class 0 OID 0)
-- Dependencies: 211
-- Name: TABLE portal_faq_tb; Type: ACL; Schema: portal; Owner: postgres
--

GRANT ALL ON TABLE portal.portal_faq_tb TO smartcity_portal;


--
-- TOC entry 3254 (class 0 OID 0)
-- Dependencies: 212
-- Name: TABLE portal_faqfile_tb; Type: ACL; Schema: portal; Owner: postgres
--

GRANT ALL ON TABLE portal.portal_faqfile_tb TO smartcity_portal;


--
-- TOC entry 3270 (class 0 OID 0)
-- Dependencies: 213
-- Name: TABLE portal_menu_tb; Type: ACL; Schema: portal; Owner: postgres
--

GRANT ALL ON TABLE portal.portal_menu_tb TO smartcity_portal;


--
-- TOC entry 3289 (class 0 OID 0)
-- Dependencies: 214
-- Name: TABLE portal_notice_tb; Type: ACL; Schema: portal; Owner: postgres
--

GRANT ALL ON TABLE portal.portal_notice_tb TO smartcity_portal;


--
-- TOC entry 3297 (class 0 OID 0)
-- Dependencies: 215
-- Name: TABLE portal_noticefile_tb; Type: ACL; Schema: portal; Owner: postgres
--

GRANT ALL ON TABLE portal.portal_noticefile_tb TO smartcity_portal;


--
-- TOC entry 3311 (class 0 OID 0)
-- Dependencies: 216
-- Name: TABLE portal_program_tb; Type: ACL; Schema: portal; Owner: postgres
--

GRANT ALL ON TABLE portal.portal_program_tb TO smartcity_portal;


--
-- TOC entry 3322 (class 0 OID 0)
-- Dependencies: 217
-- Name: TABLE portal_programgroup_tb; Type: ACL; Schema: portal; Owner: postgres
--

GRANT ALL ON TABLE portal.portal_programgroup_tb TO smartcity_portal;


--
-- TOC entry 3336 (class 0 OID 0)
-- Dependencies: 218
-- Name: TABLE portal_qna_tb; Type: ACL; Schema: portal; Owner: postgres
--

GRANT ALL ON TABLE portal.portal_qna_tb TO smartcity_portal;


--
-- TOC entry 3344 (class 0 OID 0)
-- Dependencies: 219
-- Name: TABLE portal_qnafile_tb; Type: ACL; Schema: portal; Owner: postgres
--

GRANT ALL ON TABLE portal.portal_qnafile_tb TO smartcity_portal;


--
-- TOC entry 3348 (class 0 OID 0)
-- Dependencies: 220
-- Name: TABLE portal_review_datasetmap_tb; Type: ACL; Schema: portal; Owner: postgres
--

GRANT ALL ON TABLE portal.portal_review_datasetmap_tb TO smartcity_portal;


--
-- TOC entry 3364 (class 0 OID 0)
-- Dependencies: 221
-- Name: TABLE portal_review_tb; Type: ACL; Schema: portal; Owner: postgres
--

GRANT ALL ON TABLE portal.portal_review_tb TO smartcity_portal;


--
-- TOC entry 3373 (class 0 OID 0)
-- Dependencies: 222
-- Name: TABLE portal_reviewfile_tb; Type: ACL; Schema: portal; Owner: postgres
--

GRANT ALL ON TABLE portal.portal_reviewfile_tb TO smartcity_portal;


-- Completed on 2022-04-08 17:53:09

--
-- PostgreSQL database dump complete
--




-- Permissions

GRANT CREATE, USAGE ON SCHEMA portal TO smartcity_portal;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE portal.portal_authgroup_tb TO smartcity_portal;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE portal.portal_authgroupmenumap_tb TO smartcity_portal;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE portal.portal_authgroupusermap_tb TO smartcity_portal;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE portal.portal_code_tb TO smartcity_portal;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE portal.portal_codegroup_tb TO smartcity_portal;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE portal.portal_datacomplain_tb TO smartcity_portal;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE portal.portal_datacomplainfile_tb TO smartcity_portal;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE portal.portal_ext_features TO smartcity_portal;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE portal.portal_faq_tb TO smartcity_portal;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE portal.portal_faqfile_tb TO smartcity_portal;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE portal.portal_menu_tb TO smartcity_portal;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE portal.portal_notice_tb TO smartcity_portal;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE portal.portal_noticefile_tb TO smartcity_portal;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE portal.portal_program_tb TO smartcity_portal;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE portal.portal_programgroup_tb TO smartcity_portal;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE portal.portal_qna_tb TO smartcity_portal;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE portal.portal_qnafile_tb TO smartcity_portal;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE portal.portal_review_datasetmap_tb TO smartcity_portal;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE portal.portal_review_tb TO smartcity_portal;
GRANT REFERENCES, SELECT, INSERT, TRIGGER, UPDATE, DELETE, TRUNCATE ON TABLE portal.portal_reviewfile_tb TO smartcity_portal;
