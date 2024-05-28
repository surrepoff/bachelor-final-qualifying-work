--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2
-- Dumped by pg_dump version 16.2

-- Started on 2024-05-28 23:40:46 +07

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
-- TOC entry 218 (class 1259 OID 16562)
-- Name: album; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.album (
    id integer NOT NULL,
    name text NOT NULL,
    release_date timestamp(6) without time zone NOT NULL,
    image_filename text
);


ALTER TABLE public.album OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16583)
-- Name: album_artist; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.album_artist (
    album_id integer NOT NULL,
    artist_id integer NOT NULL,
    artist_status_id integer NOT NULL
);


ALTER TABLE public.album_artist OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16603)
-- Name: album_track; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.album_track (
    album_id integer NOT NULL,
    track_id integer NOT NULL,
    track_number_in_album integer NOT NULL
);


ALTER TABLE public.album_track OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16569)
-- Name: artist; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.artist (
    id integer NOT NULL,
    name text NOT NULL,
    image_filename text
);


ALTER TABLE public.artist OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16576)
-- Name: artist_status; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.artist_status (
    id integer NOT NULL,
    name text NOT NULL
);


ALTER TABLE public.artist_status OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16618)
-- Name: artist_track; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.artist_track (
    artist_id integer NOT NULL,
    track_id integer NOT NULL,
    artist_status_id integer NOT NULL
);


ALTER TABLE public.artist_track OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16531)
-- Name: genre; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.genre (
    id integer NOT NULL,
    name text NOT NULL
);


ALTER TABLE public.genre OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16538)
-- Name: license; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.license (
    id integer NOT NULL,
    name text NOT NULL
);


ALTER TABLE public.license OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16638)
-- Name: playlist; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.playlist (
    id integer NOT NULL,
    name text,
    creation_date date NOT NULL,
    last_update_date date NOT NULL
);


ALTER TABLE public.playlist OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16645)
-- Name: playlist_track; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.playlist_track (
    playlist_id integer NOT NULL,
    track_id integer NOT NULL,
    track_number_in_playlist integer NOT NULL
);


ALTER TABLE public.playlist_track OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16545)
-- Name: track; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.track (
    id integer NOT NULL,
    primary_genre_id integer NOT NULL,
    license_id integer NOT NULL,
    name text NOT NULL,
    duration_in_seconds integer NOT NULL,
    release_date timestamp(6) without time zone NOT NULL,
    audio_filename text NOT NULL
);


ALTER TABLE public.track OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 16730)
-- Name: track_audio_feature; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.track_audio_feature (
    id integer NOT NULL,
    track_id integer NOT NULL,
    track_audio_feature_extraction_type_id integer NOT NULL
);


ALTER TABLE public.track_audio_feature OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 16725)
-- Name: track_audio_feature_extraction_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.track_audio_feature_extraction_type (
    id integer NOT NULL,
    start_delta integer NOT NULL,
    segment_duration integer NOT NULL
);


ALTER TABLE public.track_audio_feature_extraction_type OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 16745)
-- Name: track_segment_audio_feature; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.track_segment_audio_feature (
    id integer NOT NULL,
    track_audio_feature_id integer NOT NULL,
    segment_number integer NOT NULL,
    data double precision NOT NULL
);


ALTER TABLE public.track_segment_audio_feature OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 17755)
-- Name: user_album; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_album (
    user_id integer NOT NULL,
    album_id integer NOT NULL,
    album_number_in_user_list integer NOT NULL,
    added_date timestamp(6) without time zone NOT NULL
);


ALTER TABLE public.user_album OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 17842)
-- Name: user_album_rating; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_album_rating (
    user_id integer NOT NULL,
    album_id integer NOT NULL,
    user_rating_id integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.user_album_rating OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 17776)
-- Name: user_artist; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_artist (
    user_id integer NOT NULL,
    artist_id integer NOT NULL,
    artist_number_in_user_list integer NOT NULL,
    added_date timestamp(6) without time zone NOT NULL
);


ALTER TABLE public.user_artist OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 17863)
-- Name: user_artist_rating; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_artist_rating (
    user_id integer NOT NULL,
    artist_id integer NOT NULL,
    user_rating_id integer NOT NULL
);


ALTER TABLE public.user_artist_rating OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16660)
-- Name: user_data; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_data (
    id integer NOT NULL,
    username text NOT NULL,
    email text NOT NULL,
    password text NOT NULL,
    nickname text NOT NULL,
    registration_date date NOT NULL,
    last_update_date date NOT NULL
);


ALTER TABLE public.user_data OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 16755)
-- Name: user_neural_network_configuration; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_neural_network_configuration (
    id integer NOT NULL,
    user_id integer NOT NULL,
    audio_feature_extraction_type_id integer NOT NULL,
    training_date timestamp(6) without time zone NOT NULL,
    data double precision NOT NULL,
    track_audio_feature_extraction_type_id integer
);


ALTER TABLE public.user_neural_network_configuration OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16675)
-- Name: user_playlist; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_playlist (
    user_id integer NOT NULL,
    playlist_id integer NOT NULL,
    access_level_id integer NOT NULL,
    playlist_number_in_user_list integer NOT NULL,
    added_date timestamp(6) without time zone NOT NULL
);


ALTER TABLE public.user_playlist OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 16668)
-- Name: user_playlist_access_level; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_playlist_access_level (
    id integer NOT NULL,
    name text NOT NULL
);


ALTER TABLE public.user_playlist_access_level OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 17883)
-- Name: user_playlist_rating; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_playlist_rating (
    user_id integer NOT NULL,
    playlist_id integer NOT NULL,
    user_rating_id integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.user_playlist_rating OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 17726)
-- Name: user_rating; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_rating (
    id integer NOT NULL,
    name text NOT NULL
);


ALTER TABLE public.user_rating OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 17734)
-- Name: user_track; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_track (
    user_id integer NOT NULL,
    track_id integer NOT NULL,
    track_number_in_user_list integer NOT NULL,
    added_date timestamp(6) without time zone NOT NULL
);


ALTER TABLE public.user_track OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 17797)
-- Name: user_track_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_track_history (
    user_id integer NOT NULL,
    track_id integer NOT NULL,
    listen_date timestamp(6) without time zone NOT NULL
);


ALTER TABLE public.user_track_history OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 17816)
-- Name: user_track_rating; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_track_rating (
    user_id integer NOT NULL,
    track_id integer NOT NULL,
    user_rating_id integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.user_track_rating OWNER TO postgres;

--
-- TOC entry 3766 (class 0 OID 16562)
-- Dependencies: 218
-- Data for Name: album; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.album (id, name, release_date, image_filename) VALUES (0, 'Brave Nu World', '2009-01-13 00:00:00', '0.jpg');
INSERT INTO public.album (id, name, release_date, image_filename) VALUES (5, 'Calling For You/Can I Live/This Is What You Are', '2017-01-19 00:00:00', 'default.jpg');
INSERT INTO public.album (id, name, release_date, image_filename) VALUES (1, 'Giovanni Croce''s Cantato Domino', '2009-04-23 00:00:00', 'default.jpg');
INSERT INTO public.album (id, name, release_date, image_filename) VALUES (2, 'Wild Heart', '2015-02-05 00:00:00', '2.jpg');
INSERT INTO public.album (id, name, release_date, image_filename) VALUES (3, 'Nightwavs', '2014-12-01 00:00:00', '3.jpg');
INSERT INTO public.album (id, name, release_date, image_filename) VALUES (4, 'It Never Happened', '2015-11-27 00:00:00', '4.jpg');
INSERT INTO public.album (id, name, release_date, image_filename) VALUES (6, 'The Only Dream', '2007-01-01 00:00:00', '6.jpg');
INSERT INTO public.album (id, name, release_date, image_filename) VALUES (7, 'Coordinates', '2010-02-23 00:00:00', '7.jpg');
INSERT INTO public.album (id, name, release_date, image_filename) VALUES (8, 'Dead And/Or Famous', '2008-11-26 00:00:00', '8.jpg');
INSERT INTO public.album (id, name, release_date, image_filename) VALUES (9, 'Aware', '2021-01-22 00:00:00', '9.jpg');


--
-- TOC entry 3769 (class 0 OID 16583)
-- Dependencies: 221
-- Data for Name: album_artist; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.album_artist (album_id, artist_id, artist_status_id) VALUES (5, 5, 0);
INSERT INTO public.album_artist (album_id, artist_id, artist_status_id) VALUES (0, 0, 0);
INSERT INTO public.album_artist (album_id, artist_id, artist_status_id) VALUES (1, 1, 0);
INSERT INTO public.album_artist (album_id, artist_id, artist_status_id) VALUES (2, 2, 0);
INSERT INTO public.album_artist (album_id, artist_id, artist_status_id) VALUES (3, 3, 0);
INSERT INTO public.album_artist (album_id, artist_id, artist_status_id) VALUES (4, 4, 0);
INSERT INTO public.album_artist (album_id, artist_id, artist_status_id) VALUES (6, 6, 0);
INSERT INTO public.album_artist (album_id, artist_id, artist_status_id) VALUES (7, 7, 0);
INSERT INTO public.album_artist (album_id, artist_id, artist_status_id) VALUES (8, 8, 0);
INSERT INTO public.album_artist (album_id, artist_id, artist_status_id) VALUES (9, 9, 0);


--
-- TOC entry 3770 (class 0 OID 16603)
-- Dependencies: 222
-- Data for Name: album_track; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.album_track (album_id, track_id, track_number_in_album) VALUES (5, 5, 1);
INSERT INTO public.album_track (album_id, track_id, track_number_in_album) VALUES (0, 0, 6);
INSERT INTO public.album_track (album_id, track_id, track_number_in_album) VALUES (1, 1, 1);
INSERT INTO public.album_track (album_id, track_id, track_number_in_album) VALUES (2, 2, 3);
INSERT INTO public.album_track (album_id, track_id, track_number_in_album) VALUES (3, 3, 1);
INSERT INTO public.album_track (album_id, track_id, track_number_in_album) VALUES (4, 4, 2);
INSERT INTO public.album_track (album_id, track_id, track_number_in_album) VALUES (6, 6, 1);
INSERT INTO public.album_track (album_id, track_id, track_number_in_album) VALUES (7, 7, 3);
INSERT INTO public.album_track (album_id, track_id, track_number_in_album) VALUES (8, 8, 10);
INSERT INTO public.album_track (album_id, track_id, track_number_in_album) VALUES (9, 9, 1);


--
-- TOC entry 3767 (class 0 OID 16569)
-- Dependencies: 219
-- Data for Name: artist; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.artist (id, name, image_filename) VALUES (5, 'SK', '5.jpg');
INSERT INTO public.artist (id, name, image_filename) VALUES (0, 'Little Howlin'' Wolf', '0.jpg');
INSERT INTO public.artist (id, name, image_filename) VALUES (1, 'Anonymous Choir', 'default.jpg');
INSERT INTO public.artist (id, name, image_filename) VALUES (2, 'Bryan Mathys', '2.jpg');
INSERT INTO public.artist (id, name, image_filename) VALUES (3, 'The Easton Ellises', 'default.jpg');
INSERT INTO public.artist (id, name, image_filename) VALUES (4, 'Albin Andersson', '4.jpg');
INSERT INTO public.artist (id, name, image_filename) VALUES (6, 'Michael Winkle', '6.jpg');
INSERT INTO public.artist (id, name, image_filename) VALUES (7, 'Monk Turner', '7.jpg');
INSERT INTO public.artist (id, name, image_filename) VALUES (8, 'Mors Ontologica', '8.jpg');
INSERT INTO public.artist (id, name, image_filename) VALUES (9, 'Double-F the King', '9.jpg');


--
-- TOC entry 3768 (class 0 OID 16576)
-- Dependencies: 220
-- Data for Name: artist_status; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.artist_status (id, name) VALUES (0, 'Main artist');
INSERT INTO public.artist_status (id, name) VALUES (1, 'Collaborating artist');
INSERT INTO public.artist_status (id, name) VALUES (2, 'Featured artist');


--
-- TOC entry 3771 (class 0 OID 16618)
-- Dependencies: 223
-- Data for Name: artist_track; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.artist_track (artist_id, track_id, artist_status_id) VALUES (5, 5, 0);
INSERT INTO public.artist_track (artist_id, track_id, artist_status_id) VALUES (0, 0, 0);
INSERT INTO public.artist_track (artist_id, track_id, artist_status_id) VALUES (1, 1, 0);
INSERT INTO public.artist_track (artist_id, track_id, artist_status_id) VALUES (2, 2, 0);
INSERT INTO public.artist_track (artist_id, track_id, artist_status_id) VALUES (3, 3, 0);
INSERT INTO public.artist_track (artist_id, track_id, artist_status_id) VALUES (4, 4, 0);
INSERT INTO public.artist_track (artist_id, track_id, artist_status_id) VALUES (6, 6, 0);
INSERT INTO public.artist_track (artist_id, track_id, artist_status_id) VALUES (7, 7, 0);
INSERT INTO public.artist_track (artist_id, track_id, artist_status_id) VALUES (8, 8, 0);
INSERT INTO public.artist_track (artist_id, track_id, artist_status_id) VALUES (9, 9, 0);


--
-- TOC entry 3763 (class 0 OID 16531)
-- Dependencies: 215
-- Data for Name: genre; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.genre (id, name) VALUES (0, 'Blues');
INSERT INTO public.genre (id, name) VALUES (1, 'Classical');
INSERT INTO public.genre (id, name) VALUES (2, 'Country');
INSERT INTO public.genre (id, name) VALUES (3, 'Electronic');
INSERT INTO public.genre (id, name) VALUES (4, 'Folk');
INSERT INTO public.genre (id, name) VALUES (5, 'Hip-Hop');
INSERT INTO public.genre (id, name) VALUES (6, 'Jazz');
INSERT INTO public.genre (id, name) VALUES (7, 'Pop');
INSERT INTO public.genre (id, name) VALUES (8, 'Rock');
INSERT INTO public.genre (id, name) VALUES (9, 'Soul-RnB');


--
-- TOC entry 3764 (class 0 OID 16538)
-- Dependencies: 216
-- Data for Name: license; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.license (id, name) VALUES (0, 'CC0: Public Domain Dedication / No Rights Reserved');
INSERT INTO public.license (id, name) VALUES (2, 'CC BY-SA: Attribution-ShareAlike');
INSERT INTO public.license (id, name) VALUES (5, 'CC BY-NC-SA: Attribution-NonCommercial-ShareAlike');
INSERT INTO public.license (id, name) VALUES (6, 'CC BY-NC-ND: Attribution-NonCommercial-NoDerivatives');
INSERT INTO public.license (id, name) VALUES (1, 'CC BY: Attribution');
INSERT INTO public.license (id, name) VALUES (3, 'CC BY-ND: Attribution-NoDerivatives');
INSERT INTO public.license (id, name) VALUES (4, 'CC BY-NC: Attribution-NonCommercial');


--
-- TOC entry 3772 (class 0 OID 16638)
-- Dependencies: 224
-- Data for Name: playlist; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3773 (class 0 OID 16645)
-- Dependencies: 225
-- Data for Name: playlist_track; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3765 (class 0 OID 16545)
-- Dependencies: 217
-- Data for Name: track; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.track (id, primary_genre_id, license_id, name, duration_in_seconds, release_date, audio_filename) VALUES (5, 5, 1, 'Can I live', 136, '2017-01-19 00:00:00', '5.mp3');
INSERT INTO public.track (id, primary_genre_id, license_id, name, duration_in_seconds, release_date, audio_filename) VALUES (0, 0, 5, 'Last Double Eagle', 296, '2008-11-26 00:00:00', '0.mp3');
INSERT INTO public.track (id, primary_genre_id, license_id, name, duration_in_seconds, release_date, audio_filename) VALUES (1, 1, 0, 'Cantate Domino', 134, '2009-04-23 00:00:00', '1.mp3');
INSERT INTO public.track (id, primary_genre_id, license_id, name, duration_in_seconds, release_date, audio_filename) VALUES (2, 2, 1, 'It''s Not Hard to Get Lost', 238, '2015-02-05 00:00:00', '2.mp3');
INSERT INTO public.track (id, primary_genre_id, license_id, name, duration_in_seconds, release_date, audio_filename) VALUES (3, 3, 5, 'Stay', 204, '2014-12-01 00:00:00', '3.mp3');
INSERT INTO public.track (id, primary_genre_id, license_id, name, duration_in_seconds, release_date, audio_filename) VALUES (4, 4, 5, 'Connections', 154, '2016-01-16 00:00:00', '4.mp3');
INSERT INTO public.track (id, primary_genre_id, license_id, name, duration_in_seconds, release_date, audio_filename) VALUES (6, 6, 5, 'The Only Dream', 232, '2010-01-03 00:00:00', '6.mp3');
INSERT INTO public.track (id, primary_genre_id, license_id, name, duration_in_seconds, release_date, audio_filename) VALUES (7, 7, 4, 'The Club', 218, '2010-02-23 00:00:00', '7.mp3');
INSERT INTO public.track (id, primary_genre_id, license_id, name, duration_in_seconds, release_date, audio_filename) VALUES (8, 8, 5, 'Life In A Box', 171, '2008-11-26 00:00:00', '8.mp3');
INSERT INTO public.track (id, primary_genre_id, license_id, name, duration_in_seconds, release_date, audio_filename) VALUES (9, 9, 2, 'Awware', 175, '2022-05-02 00:00:00', '9.mp3');


--
-- TOC entry 3778 (class 0 OID 16730)
-- Dependencies: 230
-- Data for Name: track_audio_feature; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3777 (class 0 OID 16725)
-- Dependencies: 229
-- Data for Name: track_audio_feature_extraction_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.track_audio_feature_extraction_type (id, start_delta, segment_duration) VALUES (0, 3, 3);


--
-- TOC entry 3779 (class 0 OID 16745)
-- Dependencies: 231
-- Data for Name: track_segment_audio_feature; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3783 (class 0 OID 17755)
-- Dependencies: 235
-- Data for Name: user_album; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3787 (class 0 OID 17842)
-- Dependencies: 239
-- Data for Name: user_album_rating; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3784 (class 0 OID 17776)
-- Dependencies: 236
-- Data for Name: user_artist; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3788 (class 0 OID 17863)
-- Dependencies: 240
-- Data for Name: user_artist_rating; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3774 (class 0 OID 16660)
-- Dependencies: 226
-- Data for Name: user_data; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3780 (class 0 OID 16755)
-- Dependencies: 232
-- Data for Name: user_neural_network_configuration; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3776 (class 0 OID 16675)
-- Dependencies: 228
-- Data for Name: user_playlist; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3775 (class 0 OID 16668)
-- Dependencies: 227
-- Data for Name: user_playlist_access_level; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.user_playlist_access_level (id, name) VALUES (0, 'Owner');
INSERT INTO public.user_playlist_access_level (id, name) VALUES (1, 'Moderator');
INSERT INTO public.user_playlist_access_level (id, name) VALUES (2, 'Listner');


--
-- TOC entry 3789 (class 0 OID 17883)
-- Dependencies: 241
-- Data for Name: user_playlist_rating; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3781 (class 0 OID 17726)
-- Dependencies: 233
-- Data for Name: user_rating; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.user_rating (id, name) VALUES (0, 'none');
INSERT INTO public.user_rating (id, name) VALUES (1, 'like');
INSERT INTO public.user_rating (id, name) VALUES (-1, 'dislike');


--
-- TOC entry 3782 (class 0 OID 17734)
-- Dependencies: 234
-- Data for Name: user_track; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3785 (class 0 OID 17797)
-- Dependencies: 237
-- Data for Name: user_track_history; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3786 (class 0 OID 17816)
-- Dependencies: 238
-- Data for Name: user_track_rating; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3535 (class 2606 OID 16587)
-- Name: album_artist album_artist_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.album_artist
    ADD CONSTRAINT album_artist_pkey PRIMARY KEY (album_id, artist_id);


--
-- TOC entry 3529 (class 2606 OID 16568)
-- Name: album album_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.album
    ADD CONSTRAINT album_pkey PRIMARY KEY (id);


--
-- TOC entry 3537 (class 2606 OID 16607)
-- Name: album_track album_track_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.album_track
    ADD CONSTRAINT album_track_pkey PRIMARY KEY (album_id, track_id);


--
-- TOC entry 3531 (class 2606 OID 16575)
-- Name: artist artist_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artist
    ADD CONSTRAINT artist_pkey PRIMARY KEY (id);


--
-- TOC entry 3533 (class 2606 OID 16582)
-- Name: artist_status artist_status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artist_status
    ADD CONSTRAINT artist_status_pkey PRIMARY KEY (id);


--
-- TOC entry 3539 (class 2606 OID 16622)
-- Name: artist_track artist_track_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artist_track
    ADD CONSTRAINT artist_track_pkey PRIMARY KEY (artist_id, track_id);


--
-- TOC entry 3523 (class 2606 OID 16537)
-- Name: genre genre_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.genre
    ADD CONSTRAINT genre_pkey PRIMARY KEY (id);


--
-- TOC entry 3525 (class 2606 OID 16544)
-- Name: license license_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.license
    ADD CONSTRAINT license_pkey PRIMARY KEY (id);


--
-- TOC entry 3541 (class 2606 OID 16644)
-- Name: playlist playlist_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlist
    ADD CONSTRAINT playlist_pkey PRIMARY KEY (id);


--
-- TOC entry 3543 (class 2606 OID 16649)
-- Name: playlist_track playlist_track_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlist_track
    ADD CONSTRAINT playlist_track_pkey PRIMARY KEY (playlist_id, track_id);


--
-- TOC entry 3555 (class 2606 OID 16729)
-- Name: track_audio_feature_extraction_type track_audio_feature_extraction_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track_audio_feature_extraction_type
    ADD CONSTRAINT track_audio_feature_extraction_type_pkey PRIMARY KEY (id);


--
-- TOC entry 3557 (class 2606 OID 16734)
-- Name: track_audio_feature track_audio_feature_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track_audio_feature
    ADD CONSTRAINT track_audio_feature_pkey PRIMARY KEY (id);


--
-- TOC entry 3527 (class 2606 OID 16551)
-- Name: track track_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track
    ADD CONSTRAINT track_pkey PRIMARY KEY (id);


--
-- TOC entry 3559 (class 2606 OID 16749)
-- Name: track_segment_audio_feature track_segment_audio_feature_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track_segment_audio_feature
    ADD CONSTRAINT track_segment_audio_feature_pkey PRIMARY KEY (id);


--
-- TOC entry 3567 (class 2606 OID 17760)
-- Name: user_album user_album_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_album
    ADD CONSTRAINT user_album_pkey PRIMARY KEY (user_id, album_id);


--
-- TOC entry 3575 (class 2606 OID 17847)
-- Name: user_album_rating user_album_rating_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_album_rating
    ADD CONSTRAINT user_album_rating_pkey PRIMARY KEY (user_id, album_id);


--
-- TOC entry 3569 (class 2606 OID 17781)
-- Name: user_artist user_artist_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_artist
    ADD CONSTRAINT user_artist_pkey PRIMARY KEY (user_id, artist_id);


--
-- TOC entry 3577 (class 2606 OID 17867)
-- Name: user_artist_rating user_artist_rating_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_artist_rating
    ADD CONSTRAINT user_artist_rating_pkey PRIMARY KEY (user_id, artist_id);


--
-- TOC entry 3545 (class 2606 OID 17815)
-- Name: user_data user_data_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_data
    ADD CONSTRAINT user_data_email_key UNIQUE (email);


--
-- TOC entry 3547 (class 2606 OID 16666)
-- Name: user_data user_data_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_data
    ADD CONSTRAINT user_data_pkey PRIMARY KEY (id);


--
-- TOC entry 3549 (class 2606 OID 17813)
-- Name: user_data user_data_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_data
    ADD CONSTRAINT user_data_username_key UNIQUE (username);


--
-- TOC entry 3561 (class 2606 OID 16759)
-- Name: user_neural_network_configuration user_neural_network_configuration_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_neural_network_configuration
    ADD CONSTRAINT user_neural_network_configuration_pkey PRIMARY KEY (id);


--
-- TOC entry 3551 (class 2606 OID 16674)
-- Name: user_playlist_access_level user_playlist_access_level_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_playlist_access_level
    ADD CONSTRAINT user_playlist_access_level_pkey PRIMARY KEY (id);


--
-- TOC entry 3553 (class 2606 OID 16679)
-- Name: user_playlist user_playlist_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_playlist
    ADD CONSTRAINT user_playlist_pkey PRIMARY KEY (user_id, playlist_id);


--
-- TOC entry 3579 (class 2606 OID 17888)
-- Name: user_playlist_rating user_playlist_rating_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_playlist_rating
    ADD CONSTRAINT user_playlist_rating_pkey PRIMARY KEY (user_id, playlist_id);


--
-- TOC entry 3563 (class 2606 OID 17732)
-- Name: user_rating user_rating_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_rating
    ADD CONSTRAINT user_rating_pkey PRIMARY KEY (id);


--
-- TOC entry 3571 (class 2606 OID 17801)
-- Name: user_track_history user_track_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_track_history
    ADD CONSTRAINT user_track_history_pkey PRIMARY KEY (user_id, track_id);


--
-- TOC entry 3565 (class 2606 OID 17739)
-- Name: user_track user_track_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_track
    ADD CONSTRAINT user_track_pkey PRIMARY KEY (user_id, track_id);


--
-- TOC entry 3573 (class 2606 OID 17821)
-- Name: user_track_rating user_track_rating_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_track_rating
    ADD CONSTRAINT user_track_rating_pkey PRIMARY KEY (user_id, track_id);


--
-- TOC entry 3582 (class 2606 OID 16588)
-- Name: album_artist album_artist_album_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.album_artist
    ADD CONSTRAINT album_artist_album_id_fkey FOREIGN KEY (album_id) REFERENCES public.album(id);


--
-- TOC entry 3583 (class 2606 OID 16593)
-- Name: album_artist album_artist_artist_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.album_artist
    ADD CONSTRAINT album_artist_artist_id_fkey FOREIGN KEY (artist_id) REFERENCES public.artist(id);


--
-- TOC entry 3584 (class 2606 OID 16598)
-- Name: album_artist album_artist_artist_status_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.album_artist
    ADD CONSTRAINT album_artist_artist_status_id_fkey FOREIGN KEY (artist_status_id) REFERENCES public.artist_status(id);


--
-- TOC entry 3585 (class 2606 OID 16608)
-- Name: album_track album_track_album_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.album_track
    ADD CONSTRAINT album_track_album_id_fkey FOREIGN KEY (album_id) REFERENCES public.album(id);


--
-- TOC entry 3586 (class 2606 OID 16613)
-- Name: album_track album_track_track_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.album_track
    ADD CONSTRAINT album_track_track_id_fkey FOREIGN KEY (track_id) REFERENCES public.track(id);


--
-- TOC entry 3587 (class 2606 OID 16623)
-- Name: artist_track artist_track_artist_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artist_track
    ADD CONSTRAINT artist_track_artist_id_fkey FOREIGN KEY (artist_id) REFERENCES public.artist(id);


--
-- TOC entry 3588 (class 2606 OID 16633)
-- Name: artist_track artist_track_artist_status_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artist_track
    ADD CONSTRAINT artist_track_artist_status_id_fkey FOREIGN KEY (artist_status_id) REFERENCES public.artist_status(id);


--
-- TOC entry 3589 (class 2606 OID 16628)
-- Name: artist_track artist_track_track_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artist_track
    ADD CONSTRAINT artist_track_track_id_fkey FOREIGN KEY (track_id) REFERENCES public.track(id);


--
-- TOC entry 3590 (class 2606 OID 16650)
-- Name: playlist_track playlist_track_playlist_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlist_track
    ADD CONSTRAINT playlist_track_playlist_id_fkey FOREIGN KEY (playlist_id) REFERENCES public.playlist(id) NOT VALID;


--
-- TOC entry 3591 (class 2606 OID 16655)
-- Name: playlist_track playlist_track_track_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlist_track
    ADD CONSTRAINT playlist_track_track_id_fkey FOREIGN KEY (track_id) REFERENCES public.track(id) NOT VALID;


--
-- TOC entry 3595 (class 2606 OID 16740)
-- Name: track_audio_feature track_audio_feature_track_audio_feature_extraction_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track_audio_feature
    ADD CONSTRAINT track_audio_feature_track_audio_feature_extraction_type_id_fkey FOREIGN KEY (track_audio_feature_extraction_type_id) REFERENCES public.track_audio_feature_extraction_type(id);


--
-- TOC entry 3596 (class 2606 OID 16735)
-- Name: track_audio_feature track_audio_feature_track_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track_audio_feature
    ADD CONSTRAINT track_audio_feature_track_id_fkey FOREIGN KEY (track_id) REFERENCES public.track(id);


--
-- TOC entry 3580 (class 2606 OID 16557)
-- Name: track track_license_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track
    ADD CONSTRAINT track_license_id_fkey FOREIGN KEY (license_id) REFERENCES public.license(id);


--
-- TOC entry 3581 (class 2606 OID 16552)
-- Name: track track_primary_genre_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track
    ADD CONSTRAINT track_primary_genre_id_fkey FOREIGN KEY (primary_genre_id) REFERENCES public.genre(id);


--
-- TOC entry 3597 (class 2606 OID 16750)
-- Name: track_segment_audio_feature track_segment_audio_feature_track_audio_feature_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track_segment_audio_feature
    ADD CONSTRAINT track_segment_audio_feature_track_audio_feature_id_fkey FOREIGN KEY (track_audio_feature_id) REFERENCES public.track_audio_feature(id);


--
-- TOC entry 3602 (class 2606 OID 17766)
-- Name: user_album user_album_album_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_album
    ADD CONSTRAINT user_album_album_id_fkey FOREIGN KEY (album_id) REFERENCES public.album(id);


--
-- TOC entry 3611 (class 2606 OID 17853)
-- Name: user_album_rating user_album_rating_album_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_album_rating
    ADD CONSTRAINT user_album_rating_album_id_fkey FOREIGN KEY (album_id) REFERENCES public.album(id);


--
-- TOC entry 3612 (class 2606 OID 17848)
-- Name: user_album_rating user_album_rating_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_album_rating
    ADD CONSTRAINT user_album_rating_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_data(id);


--
-- TOC entry 3613 (class 2606 OID 17858)
-- Name: user_album_rating user_album_rating_user_rating_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_album_rating
    ADD CONSTRAINT user_album_rating_user_rating_id_fkey FOREIGN KEY (user_rating_id) REFERENCES public.user_rating(id);


--
-- TOC entry 3603 (class 2606 OID 17761)
-- Name: user_album user_album_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_album
    ADD CONSTRAINT user_album_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_data(id);


--
-- TOC entry 3604 (class 2606 OID 17787)
-- Name: user_artist user_artist_artist_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_artist
    ADD CONSTRAINT user_artist_artist_id_fkey FOREIGN KEY (artist_id) REFERENCES public.artist(id);


--
-- TOC entry 3614 (class 2606 OID 17873)
-- Name: user_artist_rating user_artist_rating_artist_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_artist_rating
    ADD CONSTRAINT user_artist_rating_artist_id_fkey FOREIGN KEY (artist_id) REFERENCES public.artist(id);


--
-- TOC entry 3615 (class 2606 OID 17868)
-- Name: user_artist_rating user_artist_rating_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_artist_rating
    ADD CONSTRAINT user_artist_rating_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_data(id);


--
-- TOC entry 3616 (class 2606 OID 17878)
-- Name: user_artist_rating user_artist_rating_user_rating_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_artist_rating
    ADD CONSTRAINT user_artist_rating_user_rating_id_fkey FOREIGN KEY (user_rating_id) REFERENCES public.user_rating(id);


--
-- TOC entry 3605 (class 2606 OID 17782)
-- Name: user_artist user_artist_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_artist
    ADD CONSTRAINT user_artist_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_data(id);


--
-- TOC entry 3598 (class 2606 OID 16765)
-- Name: user_neural_network_configuration user_neural_network_configura_audio_feature_extraction_typ_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_neural_network_configuration
    ADD CONSTRAINT user_neural_network_configura_audio_feature_extraction_typ_fkey FOREIGN KEY (audio_feature_extraction_type_id) REFERENCES public.track_audio_feature_extraction_type(id);


--
-- TOC entry 3599 (class 2606 OID 16760)
-- Name: user_neural_network_configuration user_neural_network_configuration_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_neural_network_configuration
    ADD CONSTRAINT user_neural_network_configuration_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_data(id);


--
-- TOC entry 3592 (class 2606 OID 16690)
-- Name: user_playlist user_playlist_access_level_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_playlist
    ADD CONSTRAINT user_playlist_access_level_id_fkey FOREIGN KEY (access_level_id) REFERENCES public.user_playlist_access_level(id);


--
-- TOC entry 3593 (class 2606 OID 16685)
-- Name: user_playlist user_playlist_playlist_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_playlist
    ADD CONSTRAINT user_playlist_playlist_id_fkey FOREIGN KEY (playlist_id) REFERENCES public.playlist(id);


--
-- TOC entry 3617 (class 2606 OID 17894)
-- Name: user_playlist_rating user_playlist_rating_playlist_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_playlist_rating
    ADD CONSTRAINT user_playlist_rating_playlist_id_fkey FOREIGN KEY (playlist_id) REFERENCES public.playlist(id);


--
-- TOC entry 3618 (class 2606 OID 17889)
-- Name: user_playlist_rating user_playlist_rating_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_playlist_rating
    ADD CONSTRAINT user_playlist_rating_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_data(id);


--
-- TOC entry 3619 (class 2606 OID 17899)
-- Name: user_playlist_rating user_playlist_rating_user_rating_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_playlist_rating
    ADD CONSTRAINT user_playlist_rating_user_rating_id_fkey FOREIGN KEY (user_rating_id) REFERENCES public.user_rating(id);


--
-- TOC entry 3594 (class 2606 OID 16680)
-- Name: user_playlist user_playlist_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_playlist
    ADD CONSTRAINT user_playlist_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_data(id);


--
-- TOC entry 3606 (class 2606 OID 17807)
-- Name: user_track_history user_track_history_track_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_track_history
    ADD CONSTRAINT user_track_history_track_id_fkey FOREIGN KEY (track_id) REFERENCES public.track(id);


--
-- TOC entry 3607 (class 2606 OID 17802)
-- Name: user_track_history user_track_history_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_track_history
    ADD CONSTRAINT user_track_history_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_data(id);


--
-- TOC entry 3608 (class 2606 OID 17832)
-- Name: user_track_rating user_track_rating_track_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_track_rating
    ADD CONSTRAINT user_track_rating_track_id_fkey FOREIGN KEY (track_id) REFERENCES public.track(id) NOT VALID;


--
-- TOC entry 3609 (class 2606 OID 17827)
-- Name: user_track_rating user_track_rating_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_track_rating
    ADD CONSTRAINT user_track_rating_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_data(id) NOT VALID;


--
-- TOC entry 3610 (class 2606 OID 17837)
-- Name: user_track_rating user_track_rating_user_rating_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_track_rating
    ADD CONSTRAINT user_track_rating_user_rating_id_fkey FOREIGN KEY (user_rating_id) REFERENCES public.user_rating(id) NOT VALID;


--
-- TOC entry 3600 (class 2606 OID 17745)
-- Name: user_track user_track_track_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_track
    ADD CONSTRAINT user_track_track_id_fkey FOREIGN KEY (track_id) REFERENCES public.track(id);


--
-- TOC entry 3601 (class 2606 OID 17740)
-- Name: user_track user_track_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_track
    ADD CONSTRAINT user_track_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_data(id);


-- Completed on 2024-05-28 23:40:46 +07

--
-- PostgreSQL database dump complete
--

