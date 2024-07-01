--
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
23	2	t	50
24	4	f	50
25	1	f	50
26	30	f	51
27	40	t	51
28	25	f	51
32	16	f	52
33	25	t	52
34	36	f	52
35	Архимед	f	53
36	Роберт Броун	t	53
37	Вольтметр	t	54
38	Амперметр	f	54
\.


--
-- Data for Name: questions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.questions (id, question_categories, question_text) FROM stdin;
50	MATH	Вычислите: 4/2
51	MATH	Вычислите: 50-10
52	MATH	Вычислите: 5*5
53	PHYSICS	Кто первый экспериментально обнаружил движение молекул?
54	PHYSICS	Прибор для измерения напряжения - это?
\.


--
-- Data for Name: questions_in_quiz; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.questions_in_quiz (quiz_id, question_id) FROM stdin;
28	50
28	51
28	52
29	53
29	54
\.


--
-- Data for Name: quizzes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.quizzes (id, quiz_name) FROM stdin;
28	Math
29	Physics
\.


--
-- Data for Name: student_statistic; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.student_statistic (id, date_of_completion_quiz, quiz_id, student_name, student_result) FROM stdin;
7	2024-07-01 13:05:14.206	28	student1	66,67%
8	2024-07-01 13:05:57.725	29	student1	100%
9	2024-07-01 13:06:33.962	29	student2	50%
10	2024-07-01 13:07:15.601	28	student2	66,67%
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, password, user_roles, username) FROM stdin;
7	$2a$10$XpZCEcsPkwcUK5aGL3jgquhPUnkDnJUJBnZU62u6fUraKwJEqXWLC	TEACHER	teacher
8	$2a$10$qCaruqi6MFbkHBtOolaaqu7uKxV3B4UVbk7dPAW44SH8JZtqcfjH2	STUDENT	student1
9	$2a$10$9/YEEcYtcpf2eJ9W6KaFnuaw5ZzcXqV3jSdzDaq3l0H1Qdu8KHAbS	STUDENT	student2
\.


--
-- Name: answers_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.answers_seq', 38, true);


--
-- Name: questions_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.questions_seq', 54, true);


--
-- Name: quizzes_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.quizzes_seq', 29, true);


--
-- Name: student_statistic_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.student_statistic_id_seq', 10, true);


--
-- Name: users_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_seq', 9, true);


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

