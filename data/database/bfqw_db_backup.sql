--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2
-- Dumped by pg_dump version 16.2

-- Started on 2024-05-14 01:03:32 +07

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
    name text NOT NULL
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
    name text NOT NULL
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
-- TOC entry 231 (class 1259 OID 16725)
-- Name: audio_feature_extraction_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.audio_feature_extraction_type (
    id integer NOT NULL,
    start_delta integer NOT NULL,
    segment_duration integer NOT NULL
);


ALTER TABLE public.audio_feature_extraction_type OWNER TO postgres;

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
    name text
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
    release_date date NOT NULL
);


ALTER TABLE public.track OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 16730)
-- Name: track_audio_feature; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.track_audio_feature (
    id integer NOT NULL,
    track_id integer NOT NULL,
    audio_feature_extraction_type_id integer NOT NULL
);


ALTER TABLE public.track_audio_feature OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 16745)
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
-- TOC entry 229 (class 1259 OID 16695)
-- Name: user_added_track; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_added_track (
    user_id integer NOT NULL,
    track_id integer NOT NULL,
    track_number_in_user_list integer NOT NULL,
    added_date date NOT NULL
);


ALTER TABLE public.user_added_track OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16660)
-- Name: user_data; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_data (
    id integer NOT NULL,
    username text NOT NULL,
    password text NOT NULL,
    nickname text NOT NULL,
    registration_date date NOT NULL
);


ALTER TABLE public.user_data OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 16755)
-- Name: user_neural_network_configuration; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_neural_network_configuration (
    id integer NOT NULL,
    user_id integer NOT NULL,
    audio_feature_extraction_type_id integer NOT NULL,
    training_date date NOT NULL,
    data double precision NOT NULL
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
    added_date date NOT NULL
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
-- TOC entry 230 (class 1259 OID 16710)
-- Name: user_track_rating; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_track_rating (
    user_id integer NOT NULL,
    track_id integer NOT NULL,
    rating integer NOT NULL
);


ALTER TABLE public.user_track_rating OWNER TO postgres;

--
-- TOC entry 3708 (class 0 OID 16562)
-- Dependencies: 218
-- Data for Name: album; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3711 (class 0 OID 16583)
-- Dependencies: 221
-- Data for Name: album_artist; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3712 (class 0 OID 16603)
-- Dependencies: 222
-- Data for Name: album_track; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3709 (class 0 OID 16569)
-- Dependencies: 219
-- Data for Name: artist; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3710 (class 0 OID 16576)
-- Dependencies: 220
-- Data for Name: artist_status; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3713 (class 0 OID 16618)
-- Dependencies: 223
-- Data for Name: artist_track; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3721 (class 0 OID 16725)
-- Dependencies: 231
-- Data for Name: audio_feature_extraction_type; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3705 (class 0 OID 16531)
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
-- TOC entry 3706 (class 0 OID 16538)
-- Dependencies: 216
-- Data for Name: license; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3714 (class 0 OID 16638)
-- Dependencies: 224
-- Data for Name: playlist; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3715 (class 0 OID 16645)
-- Dependencies: 225
-- Data for Name: playlist_track; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3707 (class 0 OID 16545)
-- Dependencies: 217
-- Data for Name: track; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3722 (class 0 OID 16730)
-- Dependencies: 232
-- Data for Name: track_audio_feature; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3723 (class 0 OID 16745)
-- Dependencies: 233
-- Data for Name: track_segment_audio_feature; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3719 (class 0 OID 16695)
-- Dependencies: 229
-- Data for Name: user_added_track; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3716 (class 0 OID 16660)
-- Dependencies: 226
-- Data for Name: user_data; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3724 (class 0 OID 16755)
-- Dependencies: 234
-- Data for Name: user_neural_network_configuration; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3718 (class 0 OID 16675)
-- Dependencies: 228
-- Data for Name: user_playlist; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3717 (class 0 OID 16668)
-- Dependencies: 227
-- Data for Name: user_playlist_access_level; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3720 (class 0 OID 16710)
-- Dependencies: 230
-- Data for Name: user_track_rating; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3511 (class 2606 OID 16587)
-- Name: album_artist album_artist_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.album_artist
    ADD CONSTRAINT album_artist_pkey PRIMARY KEY (album_id, artist_id);


--
-- TOC entry 3505 (class 2606 OID 16568)
-- Name: album album_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.album
    ADD CONSTRAINT album_pkey PRIMARY KEY (id);


--
-- TOC entry 3513 (class 2606 OID 16607)
-- Name: album_track album_track_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.album_track
    ADD CONSTRAINT album_track_pkey PRIMARY KEY (album_id, track_id);


--
-- TOC entry 3507 (class 2606 OID 16575)
-- Name: artist artist_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artist
    ADD CONSTRAINT artist_pkey PRIMARY KEY (id);


--
-- TOC entry 3509 (class 2606 OID 16582)
-- Name: artist_status artist_status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artist_status
    ADD CONSTRAINT artist_status_pkey PRIMARY KEY (id);


--
-- TOC entry 3515 (class 2606 OID 16622)
-- Name: artist_track artist_track_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artist_track
    ADD CONSTRAINT artist_track_pkey PRIMARY KEY (artist_id, track_id);


--
-- TOC entry 3531 (class 2606 OID 16729)
-- Name: audio_feature_extraction_type audio_feature_extraction_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.audio_feature_extraction_type
    ADD CONSTRAINT audio_feature_extraction_type_pkey PRIMARY KEY (id);


--
-- TOC entry 3499 (class 2606 OID 16537)
-- Name: genre genre_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.genre
    ADD CONSTRAINT genre_pkey PRIMARY KEY (id);


--
-- TOC entry 3501 (class 2606 OID 16544)
-- Name: license license_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.license
    ADD CONSTRAINT license_pkey PRIMARY KEY (id);


--
-- TOC entry 3517 (class 2606 OID 16644)
-- Name: playlist playlist_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlist
    ADD CONSTRAINT playlist_pkey PRIMARY KEY (id);


--
-- TOC entry 3519 (class 2606 OID 16649)
-- Name: playlist_track playlist_track_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlist_track
    ADD CONSTRAINT playlist_track_pkey PRIMARY KEY (playlist_id, track_id);


--
-- TOC entry 3533 (class 2606 OID 16734)
-- Name: track_audio_feature track_audio_feature_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track_audio_feature
    ADD CONSTRAINT track_audio_feature_pkey PRIMARY KEY (id);


--
-- TOC entry 3503 (class 2606 OID 16551)
-- Name: track track_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track
    ADD CONSTRAINT track_pkey PRIMARY KEY (id);


--
-- TOC entry 3535 (class 2606 OID 16749)
-- Name: track_segment_audio_feature track_segment_audio_feature_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track_segment_audio_feature
    ADD CONSTRAINT track_segment_audio_feature_pkey PRIMARY KEY (id);


--
-- TOC entry 3527 (class 2606 OID 16699)
-- Name: user_added_track user_added_track_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_added_track
    ADD CONSTRAINT user_added_track_pkey PRIMARY KEY (user_id, track_id);


--
-- TOC entry 3537 (class 2606 OID 16759)
-- Name: user_neural_network_configuration user_neural_network_configuration_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_neural_network_configuration
    ADD CONSTRAINT user_neural_network_configuration_pkey PRIMARY KEY (id);


--
-- TOC entry 3521 (class 2606 OID 16666)
-- Name: user_data user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_data
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- TOC entry 3523 (class 2606 OID 16674)
-- Name: user_playlist_access_level user_playlist_access_level_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_playlist_access_level
    ADD CONSTRAINT user_playlist_access_level_pkey PRIMARY KEY (id);


--
-- TOC entry 3525 (class 2606 OID 16679)
-- Name: user_playlist user_playlist_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_playlist
    ADD CONSTRAINT user_playlist_pkey PRIMARY KEY (user_id, playlist_id);


--
-- TOC entry 3529 (class 2606 OID 16714)
-- Name: user_track_rating user_track_rating_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_track_rating
    ADD CONSTRAINT user_track_rating_pkey PRIMARY KEY (user_id, track_id);


--
-- TOC entry 3540 (class 2606 OID 16588)
-- Name: album_artist album_artist_album_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.album_artist
    ADD CONSTRAINT album_artist_album_id_fkey FOREIGN KEY (album_id) REFERENCES public.album(id);


--
-- TOC entry 3541 (class 2606 OID 16593)
-- Name: album_artist album_artist_artist_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.album_artist
    ADD CONSTRAINT album_artist_artist_id_fkey FOREIGN KEY (artist_id) REFERENCES public.artist(id);


--
-- TOC entry 3542 (class 2606 OID 16598)
-- Name: album_artist album_artist_artist_status_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.album_artist
    ADD CONSTRAINT album_artist_artist_status_id_fkey FOREIGN KEY (artist_status_id) REFERENCES public.artist_status(id);


--
-- TOC entry 3543 (class 2606 OID 16608)
-- Name: album_track album_track_album_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.album_track
    ADD CONSTRAINT album_track_album_id_fkey FOREIGN KEY (album_id) REFERENCES public.album(id);


--
-- TOC entry 3544 (class 2606 OID 16613)
-- Name: album_track album_track_track_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.album_track
    ADD CONSTRAINT album_track_track_id_fkey FOREIGN KEY (track_id) REFERENCES public.track(id);


--
-- TOC entry 3545 (class 2606 OID 16623)
-- Name: artist_track artist_track_artist_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artist_track
    ADD CONSTRAINT artist_track_artist_id_fkey FOREIGN KEY (artist_id) REFERENCES public.artist(id);


--
-- TOC entry 3546 (class 2606 OID 16633)
-- Name: artist_track artist_track_artist_status_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artist_track
    ADD CONSTRAINT artist_track_artist_status_id_fkey FOREIGN KEY (artist_status_id) REFERENCES public.artist_status(id);


--
-- TOC entry 3547 (class 2606 OID 16628)
-- Name: artist_track artist_track_track_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artist_track
    ADD CONSTRAINT artist_track_track_id_fkey FOREIGN KEY (track_id) REFERENCES public.track(id);


--
-- TOC entry 3548 (class 2606 OID 16650)
-- Name: playlist_track playlist_track_playlist_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlist_track
    ADD CONSTRAINT playlist_track_playlist_id_fkey FOREIGN KEY (playlist_id) REFERENCES public.playlist(id) NOT VALID;


--
-- TOC entry 3549 (class 2606 OID 16655)
-- Name: playlist_track playlist_track_track_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlist_track
    ADD CONSTRAINT playlist_track_track_id_fkey FOREIGN KEY (track_id) REFERENCES public.track(id) NOT VALID;


--
-- TOC entry 3557 (class 2606 OID 16740)
-- Name: track_audio_feature track_audio_feature_audio_feature_extraction_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track_audio_feature
    ADD CONSTRAINT track_audio_feature_audio_feature_extraction_type_id_fkey FOREIGN KEY (audio_feature_extraction_type_id) REFERENCES public.audio_feature_extraction_type(id);


--
-- TOC entry 3558 (class 2606 OID 16735)
-- Name: track_audio_feature track_audio_feature_track_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track_audio_feature
    ADD CONSTRAINT track_audio_feature_track_id_fkey FOREIGN KEY (track_id) REFERENCES public.track(id);


--
-- TOC entry 3538 (class 2606 OID 16557)
-- Name: track track_license_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track
    ADD CONSTRAINT track_license_id_fkey FOREIGN KEY (license_id) REFERENCES public.license(id);


--
-- TOC entry 3539 (class 2606 OID 16552)
-- Name: track track_primary_genre_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track
    ADD CONSTRAINT track_primary_genre_id_fkey FOREIGN KEY (primary_genre_id) REFERENCES public.genre(id);


--
-- TOC entry 3559 (class 2606 OID 16750)
-- Name: track_segment_audio_feature track_segment_audio_feature_track_audio_feature_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.track_segment_audio_feature
    ADD CONSTRAINT track_segment_audio_feature_track_audio_feature_id_fkey FOREIGN KEY (track_audio_feature_id) REFERENCES public.track_audio_feature(id);


--
-- TOC entry 3553 (class 2606 OID 16705)
-- Name: user_added_track user_added_track_track_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_added_track
    ADD CONSTRAINT user_added_track_track_id_fkey FOREIGN KEY (track_id) REFERENCES public.track(id);


--
-- TOC entry 3554 (class 2606 OID 16700)
-- Name: user_added_track user_added_track_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_added_track
    ADD CONSTRAINT user_added_track_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_data(id);


--
-- TOC entry 3560 (class 2606 OID 16765)
-- Name: user_neural_network_configuration user_neural_network_configura_audio_feature_extraction_typ_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_neural_network_configuration
    ADD CONSTRAINT user_neural_network_configura_audio_feature_extraction_typ_fkey FOREIGN KEY (audio_feature_extraction_type_id) REFERENCES public.audio_feature_extraction_type(id);


--
-- TOC entry 3561 (class 2606 OID 16760)
-- Name: user_neural_network_configuration user_neural_network_configuration_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_neural_network_configuration
    ADD CONSTRAINT user_neural_network_configuration_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_data(id);


--
-- TOC entry 3550 (class 2606 OID 16690)
-- Name: user_playlist user_playlist_access_level_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_playlist
    ADD CONSTRAINT user_playlist_access_level_id_fkey FOREIGN KEY (access_level_id) REFERENCES public.user_playlist_access_level(id);


--
-- TOC entry 3551 (class 2606 OID 16685)
-- Name: user_playlist user_playlist_playlist_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_playlist
    ADD CONSTRAINT user_playlist_playlist_id_fkey FOREIGN KEY (playlist_id) REFERENCES public.playlist(id);


--
-- TOC entry 3552 (class 2606 OID 16680)
-- Name: user_playlist user_playlist_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_playlist
    ADD CONSTRAINT user_playlist_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_data(id);


--
-- TOC entry 3555 (class 2606 OID 16720)
-- Name: user_track_rating user_track_rating_track_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_track_rating
    ADD CONSTRAINT user_track_rating_track_id_fkey FOREIGN KEY (track_id) REFERENCES public.track(id);


--
-- TOC entry 3556 (class 2606 OID 16715)
-- Name: user_track_rating user_track_rating_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_track_rating
    ADD CONSTRAINT user_track_rating_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_data(id);


-- Completed on 2024-05-14 01:03:32 +07

--
-- PostgreSQL database dump complete
--

