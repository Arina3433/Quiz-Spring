-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: answers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.answers (
    id bigint NOT NULL,
    answer_text character varying(255),
    is_correct boolean,
    question_id bigint
);


ALTER TABLE public.answers OWNER TO postgres;

--
-- Name: answers_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.answers_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.answers_seq OWNER TO postgres;

--
-- Name: questions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.questions (
    id bigint NOT NULL,
    question_categories character varying(255),
    question_text character varying(255),
    CONSTRAINT questions_question_categories_check CHECK (((question_categories)::text = ANY ((ARRAY['MATH'::character varying, 'LITERATURE'::character varying, 'PHYSICS'::character varying, 'CHEMISTRY'::character varying, 'BIOLOGY'::character varying, 'COMMON'::character varying])::text[])))
);


ALTER TABLE public.questions OWNER TO postgres;

--
-- Name: questions_in_quiz; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.questions_in_quiz (
    quiz_id bigint NOT NULL,
    question_id bigint NOT NULL
);


ALTER TABLE public.questions_in_quiz OWNER TO postgres;

--
-- Name: questions_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.questions_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.questions_seq OWNER TO postgres;

--
-- Name: quizzes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.quizzes (
    id bigint NOT NULL,
    quiz_name character varying(255)
);


ALTER TABLE public.quizzes OWNER TO postgres;

--
-- Name: quizzes_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.quizzes_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.quizzes_seq OWNER TO postgres;

--
-- Name: student_statistic; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.student_statistic (
    id bigint NOT NULL,
    date_of_completion_quiz timestamp(6) without time zone,
    quiz_id bigint,
    student_name character varying(255),
    student_result character varying(255)
);


ALTER TABLE public.student_statistic OWNER TO postgres;

--
-- Name: student_statistic_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.student_statistic_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.student_statistic_id_seq OWNER TO postgres;

--
-- Name: student_statistic_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.student_statistic_id_seq OWNED BY public.student_statistic.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    password character varying(255),
    user_roles character varying(255),
    username character varying(255),
    CONSTRAINT users_user_roles_check CHECK (((user_roles)::text = ANY ((ARRAY['STUDENT'::character varying, 'TEACHER'::character varying])::text[])))
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_seq OWNER TO postgres;

--
-- Name: student_statistic id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.student_statistic ALTER COLUMN id SET DEFAULT nextval('public.student_statistic_id_seq'::regclass);


--
-- Data for Name: answers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.answers (id, answer_text, is_correct, question_id) FROM stdin;
17	44	f	42
18	43	t	42
19	how	t	44
20	wow	f	44
\.


--
-- Data for Name: questions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.questions (id, question_categories, question_text) FROM stdin;
44	MATH	C?
42	BIOLOGY	A?
46	COMMON	фигня всякая
47	MATH	математика
48	COMMON	птваота
\.


--
-- Data for Name: questions_in_quiz; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.questions_in_quiz (quiz_id, question_id) FROM stdin;
27	44
27	42
27	46
\.


--
-- Data for Name: quizzes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.quizzes (id, quiz_name) FROM stdin;
27	Math
\.


--
-- Data for Name: student_statistic; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.student_statistic (id, date_of_completion_quiz, quiz_id, student_name, student_result) FROM stdin;
2	2024-04-21 13:44:06.952	22	student	66,67%
3	2024-04-24 13:31:11.378	22	student	33,33%
4	2024-04-25 01:27:41.702	22	student3	33,33%
5	2024-04-25 01:27:50.011	22	student3	66,67%
6	2024-04-25 01:35:13.648	23	student	100%
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, password, user_roles, username) FROM stdin;
4	$2a$10$I0pWMQRCga8zIyFA6G9i9eZP8rbtLTelFmTVoI1705C.PxFvrd.qq	TEACHER	teacher
5	$2a$10$1eo4wkdJfXXeC3khhdPYjObgkTtUzBHPlcuYa5UBxXJVsJtgAA/Te	STUDENT	student
6	$2a$10$PEIOat3CTE7XrYhAWcGT4OXL.RYcNlOFWnkAZzxnjtaIEf0JaB2la	STUDENT	student3
\.


--
-- Name: answers_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.answers_seq', 22, true);


--
-- Name: questions_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.questions_seq', 48, true);


--
-- Name: quizzes_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.quizzes_seq', 27, true);


--
-- Name: student_statistic_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.student_statistic_id_seq', 6, true);


--
-- Name: users_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_seq', 6, true);


--
-- Name: answers answers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.answers
    ADD CONSTRAINT answers_pkey PRIMARY KEY (id);


--
-- Name: questions questions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.questions
    ADD CONSTRAINT questions_pkey PRIMARY KEY (id);


--
-- Name: quizzes quizzes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quizzes
    ADD CONSTRAINT quizzes_pkey PRIMARY KEY (id);


--
-- Name: student_statistic student_statistic_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.student_statistic
    ADD CONSTRAINT student_statistic_pkey PRIMARY KEY (id);


--
-- Name: questions uk_212f1vjg0dqdyamtki6pk48kf; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.questions
    ADD CONSTRAINT uk_212f1vjg0dqdyamtki6pk48kf UNIQUE (question_text);


--
-- Name: quizzes uk_9njf6o3anvtcd89nxpov9yd1j; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quizzes
    ADD CONSTRAINT uk_9njf6o3anvtcd89nxpov9yd1j UNIQUE (quiz_name);


--
-- Name: users uk_r43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: answers fk3erw1a3t0r78st8ty27x6v3g1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.answers
    ADD CONSTRAINT fk3erw1a3t0r78st8ty27x6v3g1 FOREIGN KEY (question_id) REFERENCES public.questions(id);


--
-- Name: questions_in_quiz fk40uant3t2iupib8ffb3y6n1pu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.questions_in_quiz
    ADD CONSTRAINT fk40uant3t2iupib8ffb3y6n1pu FOREIGN KEY (quiz_id) REFERENCES public.quizzes(id);


--
-- Name: questions_in_quiz fkfqnfclh6m2hnxcsw682hrnhjo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.questions_in_quiz
    ADD CONSTRAINT fkfqnfclh6m2hnxcsw682hrnhjo FOREIGN KEY (question_id) REFERENCES public.questions(id);


--
-- PostgreSQL database dump complete
--

