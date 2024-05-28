PGDMP      %                |            bfqw_db    16.2    16.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16398    bfqw_db    DATABASE     i   CREATE DATABASE bfqw_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'C';
    DROP DATABASE bfqw_db;
                postgres    false            �            1259    16562    album    TABLE     �   CREATE TABLE public.album (
    id integer NOT NULL,
    name text NOT NULL,
    release_date date NOT NULL,
    image_filename text
);
    DROP TABLE public.album;
       public         heap    postgres    false            �            1259    16583    album_artist    TABLE     �   CREATE TABLE public.album_artist (
    album_id integer NOT NULL,
    artist_id integer NOT NULL,
    artist_status_id integer NOT NULL
);
     DROP TABLE public.album_artist;
       public         heap    postgres    false            �            1259    16603    album_track    TABLE     �   CREATE TABLE public.album_track (
    album_id integer NOT NULL,
    track_id integer NOT NULL,
    track_number_in_album integer NOT NULL
);
    DROP TABLE public.album_track;
       public         heap    postgres    false            �            1259    16569    artist    TABLE     i   CREATE TABLE public.artist (
    id integer NOT NULL,
    name text NOT NULL,
    image_filename text
);
    DROP TABLE public.artist;
       public         heap    postgres    false            �            1259    16576    artist_status    TABLE     W   CREATE TABLE public.artist_status (
    id integer NOT NULL,
    name text NOT NULL
);
 !   DROP TABLE public.artist_status;
       public         heap    postgres    false            �            1259    16618    artist_track    TABLE     �   CREATE TABLE public.artist_track (
    artist_id integer NOT NULL,
    track_id integer NOT NULL,
    artist_status_id integer NOT NULL
);
     DROP TABLE public.artist_track;
       public         heap    postgres    false            �            1259    16531    genre    TABLE     O   CREATE TABLE public.genre (
    id integer NOT NULL,
    name text NOT NULL
);
    DROP TABLE public.genre;
       public         heap    postgres    false            �            1259    16538    license    TABLE     Q   CREATE TABLE public.license (
    id integer NOT NULL,
    name text NOT NULL
);
    DROP TABLE public.license;
       public         heap    postgres    false            �            1259    16638    playlist    TABLE     I   CREATE TABLE public.playlist (
    id integer NOT NULL,
    name text
);
    DROP TABLE public.playlist;
       public         heap    postgres    false            �            1259    16645    playlist_track    TABLE     �   CREATE TABLE public.playlist_track (
    playlist_id integer NOT NULL,
    track_id integer NOT NULL,
    track_number_in_playlist integer NOT NULL
);
 "   DROP TABLE public.playlist_track;
       public         heap    postgres    false            �            1259    16545    track    TABLE       CREATE TABLE public.track (
    id integer NOT NULL,
    primary_genre_id integer NOT NULL,
    license_id integer NOT NULL,
    name text NOT NULL,
    duration_in_seconds integer NOT NULL,
    release_date date NOT NULL,
    audio_filename text NOT NULL
);
    DROP TABLE public.track;
       public         heap    postgres    false            �            1259    16730    track_audio_feature    TABLE     �   CREATE TABLE public.track_audio_feature (
    id integer NOT NULL,
    track_id integer NOT NULL,
    track_audio_feature_extraction_type_id integer NOT NULL
);
 '   DROP TABLE public.track_audio_feature;
       public         heap    postgres    false            �            1259    16725 #   track_audio_feature_extraction_type    TABLE     �   CREATE TABLE public.track_audio_feature_extraction_type (
    id integer NOT NULL,
    start_delta integer NOT NULL,
    segment_duration integer NOT NULL
);
 7   DROP TABLE public.track_audio_feature_extraction_type;
       public         heap    postgres    false            �            1259    16745    track_segment_audio_feature    TABLE     �   CREATE TABLE public.track_segment_audio_feature (
    id integer NOT NULL,
    track_audio_feature_id integer NOT NULL,
    segment_number integer NOT NULL,
    data double precision NOT NULL
);
 /   DROP TABLE public.track_segment_audio_feature;
       public         heap    postgres    false            �            1259    17755 
   user_album    TABLE     �   CREATE TABLE public.user_album (
    user_id integer NOT NULL,
    album_id integer NOT NULL,
    album_number_in_user_list integer NOT NULL,
    added_date date NOT NULL
);
    DROP TABLE public.user_album;
       public         heap    postgres    false            �            1259    17842    user_album_rating    TABLE     �   CREATE TABLE public.user_album_rating (
    user_id integer NOT NULL,
    album_id integer NOT NULL,
    user_rating_id integer DEFAULT 0 NOT NULL
);
 %   DROP TABLE public.user_album_rating;
       public         heap    postgres    false            �            1259    17776    user_artist    TABLE     �   CREATE TABLE public.user_artist (
    user_id integer NOT NULL,
    artist_id integer NOT NULL,
    artist_number_in_user_list integer NOT NULL,
    added_date date NOT NULL
);
    DROP TABLE public.user_artist;
       public         heap    postgres    false            �            1259    17863    user_artist_rating    TABLE     �   CREATE TABLE public.user_artist_rating (
    user_id integer NOT NULL,
    artist_id integer NOT NULL,
    user_rating_id integer NOT NULL
);
 &   DROP TABLE public.user_artist_rating;
       public         heap    postgres    false            �            1259    16660 	   user_info    TABLE     �   CREATE TABLE public.user_info (
    id integer NOT NULL,
    username text NOT NULL,
    email text NOT NULL,
    password text NOT NULL,
    nickname text NOT NULL,
    registration_date date NOT NULL,
    last_update_date date NOT NULL
);
    DROP TABLE public.user_info;
       public         heap    postgres    false            �            1259    16755 !   user_neural_network_configuration    TABLE     !  CREATE TABLE public.user_neural_network_configuration (
    id integer NOT NULL,
    user_id integer NOT NULL,
    audio_feature_extraction_type_id integer NOT NULL,
    training_date date NOT NULL,
    data double precision NOT NULL,
    track_audio_feature_extraction_type_id integer
);
 5   DROP TABLE public.user_neural_network_configuration;
       public         heap    postgres    false            �            1259    16675    user_playlist    TABLE     �   CREATE TABLE public.user_playlist (
    user_id integer NOT NULL,
    playlist_id integer NOT NULL,
    access_level_id integer NOT NULL,
    playlist_number_in_user_list integer NOT NULL,
    added_date date NOT NULL
);
 !   DROP TABLE public.user_playlist;
       public         heap    postgres    false            �            1259    16668    user_playlist_access_level    TABLE     d   CREATE TABLE public.user_playlist_access_level (
    id integer NOT NULL,
    name text NOT NULL
);
 .   DROP TABLE public.user_playlist_access_level;
       public         heap    postgres    false            �            1259    17883    user_playlist_rating    TABLE     �   CREATE TABLE public.user_playlist_rating (
    user_id integer NOT NULL,
    playlist_id integer NOT NULL,
    user_rating_id integer DEFAULT 0 NOT NULL
);
 (   DROP TABLE public.user_playlist_rating;
       public         heap    postgres    false            �            1259    17726    user_rating    TABLE     U   CREATE TABLE public.user_rating (
    id integer NOT NULL,
    name text NOT NULL
);
    DROP TABLE public.user_rating;
       public         heap    postgres    false            �            1259    17734 
   user_track    TABLE     �   CREATE TABLE public.user_track (
    user_id integer NOT NULL,
    track_id integer NOT NULL,
    track_number_in_user_list integer NOT NULL,
    added_date date NOT NULL
);
    DROP TABLE public.user_track;
       public         heap    postgres    false            �            1259    17797    user_track_history    TABLE     �   CREATE TABLE public.user_track_history (
    user_id integer NOT NULL,
    track_id integer NOT NULL,
    listen_date date NOT NULL
);
 &   DROP TABLE public.user_track_history;
       public         heap    postgres    false            �            1259    17816    user_track_rating    TABLE     �   CREATE TABLE public.user_track_rating (
    user_id integer NOT NULL,
    track_id integer NOT NULL,
    user_rating_id integer DEFAULT 0 NOT NULL
);
 %   DROP TABLE public.user_track_rating;
       public         heap    postgres    false            �          0    16562    album 
   TABLE DATA                 public          postgres    false    218   F�       �          0    16583    album_artist 
   TABLE DATA                 public          postgres    false    221   Ȥ       �          0    16603    album_track 
   TABLE DATA                 public          postgres    false    222   c�       �          0    16569    artist 
   TABLE DATA                 public          postgres    false    219   	�       �          0    16576    artist_status 
   TABLE DATA                 public          postgres    false    220   $�       �          0    16618    artist_track 
   TABLE DATA                 public          postgres    false    223   ��       �          0    16531    genre 
   TABLE DATA                 public          postgres    false    215   @�       �          0    16538    license 
   TABLE DATA                 public          postgres    false    216   �       �          0    16638    playlist 
   TABLE DATA                 public          postgres    false    224   Ω       �          0    16645    playlist_track 
   TABLE DATA                 public          postgres    false    225   �       �          0    16545    track 
   TABLE DATA                 public          postgres    false    217   �       �          0    16730    track_audio_feature 
   TABLE DATA                 public          postgres    false    230   ��       �          0    16725 #   track_audio_feature_extraction_type 
   TABLE DATA                 public          postgres    false    229   Ϋ       �          0    16745    track_segment_audio_feature 
   TABLE DATA                 public          postgres    false    231   M�       �          0    17755 
   user_album 
   TABLE DATA                 public          postgres    false    235   g�       �          0    17842    user_album_rating 
   TABLE DATA                 public          postgres    false    239   ��       �          0    17776    user_artist 
   TABLE DATA                 public          postgres    false    236   ��       �          0    17863    user_artist_rating 
   TABLE DATA                 public          postgres    false    240   ��       �          0    16660 	   user_info 
   TABLE DATA                 public          postgres    false    226   Ϭ       �          0    16755 !   user_neural_network_configuration 
   TABLE DATA                 public          postgres    false    232   �       �          0    16675    user_playlist 
   TABLE DATA                 public          postgres    false    228   �       �          0    16668    user_playlist_access_level 
   TABLE DATA                 public          postgres    false    227   �       �          0    17883    user_playlist_rating 
   TABLE DATA                 public          postgres    false    241   ��       �          0    17726    user_rating 
   TABLE DATA                 public          postgres    false    233   ��       �          0    17734 
   user_track 
   TABLE DATA                 public          postgres    false    234   $�       �          0    17797    user_track_history 
   TABLE DATA                 public          postgres    false    237   >�       �          0    17816    user_track_rating 
   TABLE DATA                 public          postgres    false    238   X�       �           2606    16587    album_artist album_artist_pkey 
   CONSTRAINT     m   ALTER TABLE ONLY public.album_artist
    ADD CONSTRAINT album_artist_pkey PRIMARY KEY (album_id, artist_id);
 H   ALTER TABLE ONLY public.album_artist DROP CONSTRAINT album_artist_pkey;
       public            postgres    false    221    221            �           2606    16568    album album_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.album
    ADD CONSTRAINT album_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.album DROP CONSTRAINT album_pkey;
       public            postgres    false    218            �           2606    16607    album_track album_track_pkey 
   CONSTRAINT     j   ALTER TABLE ONLY public.album_track
    ADD CONSTRAINT album_track_pkey PRIMARY KEY (album_id, track_id);
 F   ALTER TABLE ONLY public.album_track DROP CONSTRAINT album_track_pkey;
       public            postgres    false    222    222            �           2606    16575    artist artist_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.artist
    ADD CONSTRAINT artist_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.artist DROP CONSTRAINT artist_pkey;
       public            postgres    false    219            �           2606    16582     artist_status artist_status_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.artist_status
    ADD CONSTRAINT artist_status_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.artist_status DROP CONSTRAINT artist_status_pkey;
       public            postgres    false    220            �           2606    16622    artist_track artist_track_pkey 
   CONSTRAINT     m   ALTER TABLE ONLY public.artist_track
    ADD CONSTRAINT artist_track_pkey PRIMARY KEY (artist_id, track_id);
 H   ALTER TABLE ONLY public.artist_track DROP CONSTRAINT artist_track_pkey;
       public            postgres    false    223    223            �           2606    16537    genre genre_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.genre
    ADD CONSTRAINT genre_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.genre DROP CONSTRAINT genre_pkey;
       public            postgres    false    215            �           2606    16544    license license_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.license
    ADD CONSTRAINT license_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.license DROP CONSTRAINT license_pkey;
       public            postgres    false    216            �           2606    16644    playlist playlist_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.playlist
    ADD CONSTRAINT playlist_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.playlist DROP CONSTRAINT playlist_pkey;
       public            postgres    false    224            �           2606    16649 "   playlist_track playlist_track_pkey 
   CONSTRAINT     s   ALTER TABLE ONLY public.playlist_track
    ADD CONSTRAINT playlist_track_pkey PRIMARY KEY (playlist_id, track_id);
 L   ALTER TABLE ONLY public.playlist_track DROP CONSTRAINT playlist_track_pkey;
       public            postgres    false    225    225            �           2606    16729 L   track_audio_feature_extraction_type track_audio_feature_extraction_type_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.track_audio_feature_extraction_type
    ADD CONSTRAINT track_audio_feature_extraction_type_pkey PRIMARY KEY (id);
 v   ALTER TABLE ONLY public.track_audio_feature_extraction_type DROP CONSTRAINT track_audio_feature_extraction_type_pkey;
       public            postgres    false    229            �           2606    16734 ,   track_audio_feature track_audio_feature_pkey 
   CONSTRAINT     j   ALTER TABLE ONLY public.track_audio_feature
    ADD CONSTRAINT track_audio_feature_pkey PRIMARY KEY (id);
 V   ALTER TABLE ONLY public.track_audio_feature DROP CONSTRAINT track_audio_feature_pkey;
       public            postgres    false    230            �           2606    16551    track track_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.track
    ADD CONSTRAINT track_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.track DROP CONSTRAINT track_pkey;
       public            postgres    false    217            �           2606    16749 <   track_segment_audio_feature track_segment_audio_feature_pkey 
   CONSTRAINT     z   ALTER TABLE ONLY public.track_segment_audio_feature
    ADD CONSTRAINT track_segment_audio_feature_pkey PRIMARY KEY (id);
 f   ALTER TABLE ONLY public.track_segment_audio_feature DROP CONSTRAINT track_segment_audio_feature_pkey;
       public            postgres    false    231            �           2606    17760    user_album user_album_pkey 
   CONSTRAINT     g   ALTER TABLE ONLY public.user_album
    ADD CONSTRAINT user_album_pkey PRIMARY KEY (user_id, album_id);
 D   ALTER TABLE ONLY public.user_album DROP CONSTRAINT user_album_pkey;
       public            postgres    false    235    235            �           2606    17847 (   user_album_rating user_album_rating_pkey 
   CONSTRAINT     u   ALTER TABLE ONLY public.user_album_rating
    ADD CONSTRAINT user_album_rating_pkey PRIMARY KEY (user_id, album_id);
 R   ALTER TABLE ONLY public.user_album_rating DROP CONSTRAINT user_album_rating_pkey;
       public            postgres    false    239    239            �           2606    17781    user_artist user_artist_pkey 
   CONSTRAINT     j   ALTER TABLE ONLY public.user_artist
    ADD CONSTRAINT user_artist_pkey PRIMARY KEY (user_id, artist_id);
 F   ALTER TABLE ONLY public.user_artist DROP CONSTRAINT user_artist_pkey;
       public            postgres    false    236    236            �           2606    17867 *   user_artist_rating user_artist_rating_pkey 
   CONSTRAINT     x   ALTER TABLE ONLY public.user_artist_rating
    ADD CONSTRAINT user_artist_rating_pkey PRIMARY KEY (user_id, artist_id);
 T   ALTER TABLE ONLY public.user_artist_rating DROP CONSTRAINT user_artist_rating_pkey;
       public            postgres    false    240    240            �           2606    17815    user_info user_info_email_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.user_info
    ADD CONSTRAINT user_info_email_key UNIQUE (email);
 G   ALTER TABLE ONLY public.user_info DROP CONSTRAINT user_info_email_key;
       public            postgres    false    226            �           2606    16666    user_info user_info_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.user_info
    ADD CONSTRAINT user_info_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.user_info DROP CONSTRAINT user_info_pkey;
       public            postgres    false    226            �           2606    17813     user_info user_info_username_key 
   CONSTRAINT     _   ALTER TABLE ONLY public.user_info
    ADD CONSTRAINT user_info_username_key UNIQUE (username);
 J   ALTER TABLE ONLY public.user_info DROP CONSTRAINT user_info_username_key;
       public            postgres    false    226            �           2606    16759 H   user_neural_network_configuration user_neural_network_configuration_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.user_neural_network_configuration
    ADD CONSTRAINT user_neural_network_configuration_pkey PRIMARY KEY (id);
 r   ALTER TABLE ONLY public.user_neural_network_configuration DROP CONSTRAINT user_neural_network_configuration_pkey;
       public            postgres    false    232            �           2606    16674 :   user_playlist_access_level user_playlist_access_level_pkey 
   CONSTRAINT     x   ALTER TABLE ONLY public.user_playlist_access_level
    ADD CONSTRAINT user_playlist_access_level_pkey PRIMARY KEY (id);
 d   ALTER TABLE ONLY public.user_playlist_access_level DROP CONSTRAINT user_playlist_access_level_pkey;
       public            postgres    false    227            �           2606    16679     user_playlist user_playlist_pkey 
   CONSTRAINT     p   ALTER TABLE ONLY public.user_playlist
    ADD CONSTRAINT user_playlist_pkey PRIMARY KEY (user_id, playlist_id);
 J   ALTER TABLE ONLY public.user_playlist DROP CONSTRAINT user_playlist_pkey;
       public            postgres    false    228    228            �           2606    17888 .   user_playlist_rating user_playlist_rating_pkey 
   CONSTRAINT     ~   ALTER TABLE ONLY public.user_playlist_rating
    ADD CONSTRAINT user_playlist_rating_pkey PRIMARY KEY (user_id, playlist_id);
 X   ALTER TABLE ONLY public.user_playlist_rating DROP CONSTRAINT user_playlist_rating_pkey;
       public            postgres    false    241    241            �           2606    17732    user_rating user_rating_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.user_rating
    ADD CONSTRAINT user_rating_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.user_rating DROP CONSTRAINT user_rating_pkey;
       public            postgres    false    233            �           2606    17801 *   user_track_history user_track_history_pkey 
   CONSTRAINT     w   ALTER TABLE ONLY public.user_track_history
    ADD CONSTRAINT user_track_history_pkey PRIMARY KEY (user_id, track_id);
 T   ALTER TABLE ONLY public.user_track_history DROP CONSTRAINT user_track_history_pkey;
       public            postgres    false    237    237            �           2606    17739    user_track user_track_pkey 
   CONSTRAINT     g   ALTER TABLE ONLY public.user_track
    ADD CONSTRAINT user_track_pkey PRIMARY KEY (user_id, track_id);
 D   ALTER TABLE ONLY public.user_track DROP CONSTRAINT user_track_pkey;
       public            postgres    false    234    234            �           2606    17821 (   user_track_rating user_track_rating_pkey 
   CONSTRAINT     u   ALTER TABLE ONLY public.user_track_rating
    ADD CONSTRAINT user_track_rating_pkey PRIMARY KEY (user_id, track_id);
 R   ALTER TABLE ONLY public.user_track_rating DROP CONSTRAINT user_track_rating_pkey;
       public            postgres    false    238    238            �           2606    16588 '   album_artist album_artist_album_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.album_artist
    ADD CONSTRAINT album_artist_album_id_fkey FOREIGN KEY (album_id) REFERENCES public.album(id);
 Q   ALTER TABLE ONLY public.album_artist DROP CONSTRAINT album_artist_album_id_fkey;
       public          postgres    false    3529    221    218            �           2606    16593 (   album_artist album_artist_artist_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.album_artist
    ADD CONSTRAINT album_artist_artist_id_fkey FOREIGN KEY (artist_id) REFERENCES public.artist(id);
 R   ALTER TABLE ONLY public.album_artist DROP CONSTRAINT album_artist_artist_id_fkey;
       public          postgres    false    219    3531    221                        2606    16598 /   album_artist album_artist_artist_status_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.album_artist
    ADD CONSTRAINT album_artist_artist_status_id_fkey FOREIGN KEY (artist_status_id) REFERENCES public.artist_status(id);
 Y   ALTER TABLE ONLY public.album_artist DROP CONSTRAINT album_artist_artist_status_id_fkey;
       public          postgres    false    3533    220    221                       2606    16608 %   album_track album_track_album_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.album_track
    ADD CONSTRAINT album_track_album_id_fkey FOREIGN KEY (album_id) REFERENCES public.album(id);
 O   ALTER TABLE ONLY public.album_track DROP CONSTRAINT album_track_album_id_fkey;
       public          postgres    false    218    222    3529                       2606    16613 %   album_track album_track_track_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.album_track
    ADD CONSTRAINT album_track_track_id_fkey FOREIGN KEY (track_id) REFERENCES public.track(id);
 O   ALTER TABLE ONLY public.album_track DROP CONSTRAINT album_track_track_id_fkey;
       public          postgres    false    3527    217    222                       2606    16623 (   artist_track artist_track_artist_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.artist_track
    ADD CONSTRAINT artist_track_artist_id_fkey FOREIGN KEY (artist_id) REFERENCES public.artist(id);
 R   ALTER TABLE ONLY public.artist_track DROP CONSTRAINT artist_track_artist_id_fkey;
       public          postgres    false    3531    219    223                       2606    16633 /   artist_track artist_track_artist_status_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.artist_track
    ADD CONSTRAINT artist_track_artist_status_id_fkey FOREIGN KEY (artist_status_id) REFERENCES public.artist_status(id);
 Y   ALTER TABLE ONLY public.artist_track DROP CONSTRAINT artist_track_artist_status_id_fkey;
       public          postgres    false    3533    220    223                       2606    16628 '   artist_track artist_track_track_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.artist_track
    ADD CONSTRAINT artist_track_track_id_fkey FOREIGN KEY (track_id) REFERENCES public.track(id);
 Q   ALTER TABLE ONLY public.artist_track DROP CONSTRAINT artist_track_track_id_fkey;
       public          postgres    false    223    3527    217                       2606    16650 .   playlist_track playlist_track_playlist_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.playlist_track
    ADD CONSTRAINT playlist_track_playlist_id_fkey FOREIGN KEY (playlist_id) REFERENCES public.playlist(id) NOT VALID;
 X   ALTER TABLE ONLY public.playlist_track DROP CONSTRAINT playlist_track_playlist_id_fkey;
       public          postgres    false    3541    224    225                       2606    16655 +   playlist_track playlist_track_track_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.playlist_track
    ADD CONSTRAINT playlist_track_track_id_fkey FOREIGN KEY (track_id) REFERENCES public.track(id) NOT VALID;
 U   ALTER TABLE ONLY public.playlist_track DROP CONSTRAINT playlist_track_track_id_fkey;
       public          postgres    false    225    217    3527                       2606    16740 S   track_audio_feature track_audio_feature_track_audio_feature_extraction_type_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.track_audio_feature
    ADD CONSTRAINT track_audio_feature_track_audio_feature_extraction_type_id_fkey FOREIGN KEY (track_audio_feature_extraction_type_id) REFERENCES public.track_audio_feature_extraction_type(id);
 }   ALTER TABLE ONLY public.track_audio_feature DROP CONSTRAINT track_audio_feature_track_audio_feature_extraction_type_id_fkey;
       public          postgres    false    3555    230    229                       2606    16735 5   track_audio_feature track_audio_feature_track_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.track_audio_feature
    ADD CONSTRAINT track_audio_feature_track_id_fkey FOREIGN KEY (track_id) REFERENCES public.track(id);
 _   ALTER TABLE ONLY public.track_audio_feature DROP CONSTRAINT track_audio_feature_track_id_fkey;
       public          postgres    false    3527    217    230            �           2606    16557    track track_license_id_fkey    FK CONSTRAINT        ALTER TABLE ONLY public.track
    ADD CONSTRAINT track_license_id_fkey FOREIGN KEY (license_id) REFERENCES public.license(id);
 E   ALTER TABLE ONLY public.track DROP CONSTRAINT track_license_id_fkey;
       public          postgres    false    3525    217    216            �           2606    16552 !   track track_primary_genre_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.track
    ADD CONSTRAINT track_primary_genre_id_fkey FOREIGN KEY (primary_genre_id) REFERENCES public.genre(id);
 K   ALTER TABLE ONLY public.track DROP CONSTRAINT track_primary_genre_id_fkey;
       public          postgres    false    215    217    3523                       2606    16750 S   track_segment_audio_feature track_segment_audio_feature_track_audio_feature_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.track_segment_audio_feature
    ADD CONSTRAINT track_segment_audio_feature_track_audio_feature_id_fkey FOREIGN KEY (track_audio_feature_id) REFERENCES public.track_audio_feature(id);
 }   ALTER TABLE ONLY public.track_segment_audio_feature DROP CONSTRAINT track_segment_audio_feature_track_audio_feature_id_fkey;
       public          postgres    false    231    230    3557                       2606    17766 #   user_album user_album_album_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_album
    ADD CONSTRAINT user_album_album_id_fkey FOREIGN KEY (album_id) REFERENCES public.album(id);
 M   ALTER TABLE ONLY public.user_album DROP CONSTRAINT user_album_album_id_fkey;
       public          postgres    false    218    235    3529                       2606    17853 1   user_album_rating user_album_rating_album_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_album_rating
    ADD CONSTRAINT user_album_rating_album_id_fkey FOREIGN KEY (album_id) REFERENCES public.album(id);
 [   ALTER TABLE ONLY public.user_album_rating DROP CONSTRAINT user_album_rating_album_id_fkey;
       public          postgres    false    239    218    3529                       2606    17848 0   user_album_rating user_album_rating_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_album_rating
    ADD CONSTRAINT user_album_rating_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_info(id);
 Z   ALTER TABLE ONLY public.user_album_rating DROP CONSTRAINT user_album_rating_user_id_fkey;
       public          postgres    false    226    239    3547                       2606    17858 7   user_album_rating user_album_rating_user_rating_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_album_rating
    ADD CONSTRAINT user_album_rating_user_rating_id_fkey FOREIGN KEY (user_rating_id) REFERENCES public.user_rating(id);
 a   ALTER TABLE ONLY public.user_album_rating DROP CONSTRAINT user_album_rating_user_rating_id_fkey;
       public          postgres    false    3563    239    233                       2606    17761 "   user_album user_album_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_album
    ADD CONSTRAINT user_album_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_info(id);
 L   ALTER TABLE ONLY public.user_album DROP CONSTRAINT user_album_user_id_fkey;
       public          postgres    false    226    3547    235                       2606    17787 &   user_artist user_artist_artist_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_artist
    ADD CONSTRAINT user_artist_artist_id_fkey FOREIGN KEY (artist_id) REFERENCES public.artist(id);
 P   ALTER TABLE ONLY public.user_artist DROP CONSTRAINT user_artist_artist_id_fkey;
       public          postgres    false    236    3531    219                       2606    17873 4   user_artist_rating user_artist_rating_artist_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_artist_rating
    ADD CONSTRAINT user_artist_rating_artist_id_fkey FOREIGN KEY (artist_id) REFERENCES public.artist(id);
 ^   ALTER TABLE ONLY public.user_artist_rating DROP CONSTRAINT user_artist_rating_artist_id_fkey;
       public          postgres    false    240    219    3531                       2606    17868 2   user_artist_rating user_artist_rating_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_artist_rating
    ADD CONSTRAINT user_artist_rating_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_info(id);
 \   ALTER TABLE ONLY public.user_artist_rating DROP CONSTRAINT user_artist_rating_user_id_fkey;
       public          postgres    false    3547    226    240                        2606    17878 9   user_artist_rating user_artist_rating_user_rating_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_artist_rating
    ADD CONSTRAINT user_artist_rating_user_rating_id_fkey FOREIGN KEY (user_rating_id) REFERENCES public.user_rating(id);
 c   ALTER TABLE ONLY public.user_artist_rating DROP CONSTRAINT user_artist_rating_user_rating_id_fkey;
       public          postgres    false    240    233    3563                       2606    17782 $   user_artist user_artist_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_artist
    ADD CONSTRAINT user_artist_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_info(id);
 N   ALTER TABLE ONLY public.user_artist DROP CONSTRAINT user_artist_user_id_fkey;
       public          postgres    false    236    3547    226                       2606    16765 a   user_neural_network_configuration user_neural_network_configura_audio_feature_extraction_typ_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_neural_network_configuration
    ADD CONSTRAINT user_neural_network_configura_audio_feature_extraction_typ_fkey FOREIGN KEY (audio_feature_extraction_type_id) REFERENCES public.track_audio_feature_extraction_type(id);
 �   ALTER TABLE ONLY public.user_neural_network_configuration DROP CONSTRAINT user_neural_network_configura_audio_feature_extraction_typ_fkey;
       public          postgres    false    232    229    3555                       2606    16760 P   user_neural_network_configuration user_neural_network_configuration_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_neural_network_configuration
    ADD CONSTRAINT user_neural_network_configuration_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_info(id);
 z   ALTER TABLE ONLY public.user_neural_network_configuration DROP CONSTRAINT user_neural_network_configuration_user_id_fkey;
       public          postgres    false    232    226    3547                       2606    16690 0   user_playlist user_playlist_access_level_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_playlist
    ADD CONSTRAINT user_playlist_access_level_id_fkey FOREIGN KEY (access_level_id) REFERENCES public.user_playlist_access_level(id);
 Z   ALTER TABLE ONLY public.user_playlist DROP CONSTRAINT user_playlist_access_level_id_fkey;
       public          postgres    false    228    227    3551            	           2606    16685 ,   user_playlist user_playlist_playlist_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_playlist
    ADD CONSTRAINT user_playlist_playlist_id_fkey FOREIGN KEY (playlist_id) REFERENCES public.playlist(id);
 V   ALTER TABLE ONLY public.user_playlist DROP CONSTRAINT user_playlist_playlist_id_fkey;
       public          postgres    false    228    224    3541            !           2606    17894 :   user_playlist_rating user_playlist_rating_playlist_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_playlist_rating
    ADD CONSTRAINT user_playlist_rating_playlist_id_fkey FOREIGN KEY (playlist_id) REFERENCES public.playlist(id);
 d   ALTER TABLE ONLY public.user_playlist_rating DROP CONSTRAINT user_playlist_rating_playlist_id_fkey;
       public          postgres    false    241    3541    224            "           2606    17889 6   user_playlist_rating user_playlist_rating_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_playlist_rating
    ADD CONSTRAINT user_playlist_rating_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_info(id);
 `   ALTER TABLE ONLY public.user_playlist_rating DROP CONSTRAINT user_playlist_rating_user_id_fkey;
       public          postgres    false    241    226    3547            #           2606    17899 =   user_playlist_rating user_playlist_rating_user_rating_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_playlist_rating
    ADD CONSTRAINT user_playlist_rating_user_rating_id_fkey FOREIGN KEY (user_rating_id) REFERENCES public.user_rating(id);
 g   ALTER TABLE ONLY public.user_playlist_rating DROP CONSTRAINT user_playlist_rating_user_rating_id_fkey;
       public          postgres    false    241    3563    233            
           2606    16680 (   user_playlist user_playlist_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_playlist
    ADD CONSTRAINT user_playlist_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_info(id);
 R   ALTER TABLE ONLY public.user_playlist DROP CONSTRAINT user_playlist_user_id_fkey;
       public          postgres    false    3547    228    226                       2606    17807 3   user_track_history user_track_history_track_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_track_history
    ADD CONSTRAINT user_track_history_track_id_fkey FOREIGN KEY (track_id) REFERENCES public.track(id);
 ]   ALTER TABLE ONLY public.user_track_history DROP CONSTRAINT user_track_history_track_id_fkey;
       public          postgres    false    237    217    3527                       2606    17802 2   user_track_history user_track_history_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_track_history
    ADD CONSTRAINT user_track_history_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_info(id);
 \   ALTER TABLE ONLY public.user_track_history DROP CONSTRAINT user_track_history_user_id_fkey;
       public          postgres    false    237    3547    226                       2606    17832 1   user_track_rating user_track_rating_track_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_track_rating
    ADD CONSTRAINT user_track_rating_track_id_fkey FOREIGN KEY (track_id) REFERENCES public.track(id) NOT VALID;
 [   ALTER TABLE ONLY public.user_track_rating DROP CONSTRAINT user_track_rating_track_id_fkey;
       public          postgres    false    217    3527    238                       2606    17827 0   user_track_rating user_track_rating_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_track_rating
    ADD CONSTRAINT user_track_rating_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_info(id) NOT VALID;
 Z   ALTER TABLE ONLY public.user_track_rating DROP CONSTRAINT user_track_rating_user_id_fkey;
       public          postgres    false    3547    226    238                       2606    17837 7   user_track_rating user_track_rating_user_rating_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_track_rating
    ADD CONSTRAINT user_track_rating_user_rating_id_fkey FOREIGN KEY (user_rating_id) REFERENCES public.user_rating(id) NOT VALID;
 a   ALTER TABLE ONLY public.user_track_rating DROP CONSTRAINT user_track_rating_user_rating_id_fkey;
       public          postgres    false    233    238    3563                       2606    17745 #   user_track user_track_track_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_track
    ADD CONSTRAINT user_track_track_id_fkey FOREIGN KEY (track_id) REFERENCES public.track(id);
 M   ALTER TABLE ONLY public.user_track DROP CONSTRAINT user_track_track_id_fkey;
       public          postgres    false    234    3527    217                       2606    17740 "   user_track user_track_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_track
    ADD CONSTRAINT user_track_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_info(id);
 L   ALTER TABLE ONLY public.user_track DROP CONSTRAINT user_track_user_id_fkey;
       public          postgres    false    226    234    3547            �   r  x����n�0E�|��$NB�e(�� Z�z��rl��_����tym����$]��֐��%�˝��]�AS�6(̨�$aN[��U"�#mB��m����}��&k��l�"HK�h#�gWB�b�~�s�u��G���Hj�����sm�S��	*H�UT�]�DI�nƆn���+v��KYԟ.��/BW�����{�l�S�	��j��a�_��|#$��)�=|�lpU�#{�6�Sq�*��~`��S���}k��REx>�"�;k�a�T�~��ڮOK%�aj���^��m�a���}�ʞ{��ܵ��ST?sdm��Ɗw����f�k�C�F��ck;�����Y?���F�Vu�      �   �   x��̽
�0�=Oqc���~���! lu�T�@1�������n��i/�����f;���u7o�G��}����\|r܎��8#�1E~P��rY�$J�"*�&j�!�%Z�#:�'zy �$+�DH(�      �   �   x���v
Q���W((M��L�K�I*͍/)JL�VЀp2St�H���ܤԢ�̼x�M�0G�P�`S 2Դ���:
@dF��Φ�ˍt�Ș6�M���Mt�Ȉ6��c�f.7�Q0�Y�[�( ��mL��Q��� v�1|      �     x����N�0�{�·nR��ا8(�ڗD;��u[�ԙ�T�oO2�' K>������%�9�����,���ΐ60�2-F@���"���g��H2-"�mh�b�}���c��ǛZiG�H�7�#��NJV.a�%��J1+Z�kxnu/��4^"f�{������g^�+�B"�Q���Q���N$��s��֊]����Ҟ�F���E���^앳_ �;��_W^���4�(�j*���^���^��»W0��[���?>~	�"�      �   o   x���v
Q���W((M��L�K,*�,.�/.I,)-V��L�Q�K�M�Ts�	uV�0�QP�M��S��T״��$�C�1��99�I�E�%�y��g4�-([���d �H�      �   �   x��̻
�@��~�b���\��H� 	$�V��,ZHv��.1���S���Owc;L����>�v>��[��~1����'��m��y�WJ���rmGDG"\�����VFN�TFΈLFΉ\F.�BF.�RF��JF��ZFn��'+��6)      �   �   x���K�0��=�������+10��Z�8��BN�p�9��?^TiYC^�Wh�����i��_��p�������p��8#xD�uZ
�����3}������[k���%��׭�e��3�p��Y�m����-��J��\eƥIF�y���      �   �   x���O�@G�~��������L;��Z�q�!�t7V��gBe�x������`��p��%�hT�A�x�gh�����!gb��~Wf��S����$�tM�|�Q����u��jl�f`[`�����7��+�S�aw��Ce��I��,CO{iVMs�4L�����+1�n��M?�����/�W�1E�/��=�J��      �   
   x���          �   
   x���          �   �  x���͊�0�{�bn�i��o��fCkYhҽm�݊�R��n��Ii/}�|0b,~�w�������Η�Q����p�g�'���2N�a��ӻ��"0\��ښ^�~V'k���S��X6H�%�2hۿ�Q�O��e���=�]I N �Hn�Se��W�$�)㔷����9��W��3,����=<Y(���1�E�ܬ��SQ7[���.��h��H�����EB��T����є��g�{��tx�����}�zޤ���	��p ���򔖃��@e�׼��<P�EP�U$���Na�9�����xc��E��UI|���ٌWxrJN1"�Y@��j�@;ћ�������X/����6������>�_!5�g�5�0��88��Ǉt�R�+�x�l��Z��1w      �   
   x���          �   o   x���v
Q���W((M��L�+)JLΎO,M�̏OKM,)-J�O� ��d��ŗT�*hd��(�$�ħ��$9�鹩y@niQ"H��B��O�k������1iZsqq �k%�      �   
   x���          �   
   x���          �   
   x���          �   
   x���          �   
   x���          �   
   x���          �   
   x���          �   
   x���          �   q   x���v
Q���W((M��L�+-N-�/�I���,.�OLNN-.��I-K�Q��L�Q�K�M�Ts�	uV�0�QP�/�K-R״���4C�i��)�E�%��1�h�P!ԅ\\ \2N�      �   
   x���          �   \   x���v
Q���W((M��L�+-N-�/J,��KW��L�Q�K�M�Ts�	uV�0�QP���KU״��$Y�!PwNf6��uA�S2�a&pq ��:,      �   
   x���          �   
   x���          �   
   x���         