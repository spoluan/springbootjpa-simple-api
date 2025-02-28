PGDMP  &                    }            db_interview    17.2 (Debian 17.2-1.pgdg120+1)    17.2 (Debian 17.2-1.pgdg120+1)     /           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            0           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            1           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            2           1262    24576    db_interview    DATABASE     w   CREATE DATABASE db_interview WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';
    DROP DATABASE db_interview;
                     postgres    false            �            1259    24577    app_user    TABLE     �   CREATE TABLE public.app_user (
    id uuid NOT NULL,
    password character varying(255),
    role character varying(255),
    username character varying(255)
);
    DROP TABLE public.app_user;
       public         heap r       postgres    false            �            1259    24584    book    TABLE     �   CREATE TABLE public.book (
    id uuid NOT NULL,
    author character varying(255),
    image character varying(255),
    title character varying(255)
);
    DROP TABLE public.book;
       public         heap r       postgres    false            �            1259    24591 	   inventory    TABLE     �   CREATE TABLE public.inventory (
    id uuid NOT NULL,
    book_id uuid,
    user_id uuid,
    borrow_date timestamp without time zone
);
    DROP TABLE public.inventory;
       public         heap r       postgres    false            *          0    24577    app_user 
   TABLE DATA           @   COPY public.app_user (id, password, role, username) FROM stdin;
    public               postgres    false    217          +          0    24584    book 
   TABLE DATA           8   COPY public.book (id, author, image, title) FROM stdin;
    public               postgres    false    218   �       ,          0    24591 	   inventory 
   TABLE DATA           F   COPY public.inventory (id, book_id, user_id, borrow_date) FROM stdin;
    public               postgres    false    219          �           2606    24583    app_user app_user_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.app_user
    ADD CONSTRAINT app_user_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.app_user DROP CONSTRAINT app_user_pkey;
       public                 postgres    false    217            �           2606    24590    book book_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.book DROP CONSTRAINT book_pkey;
       public                 postgres    false    218            �           2606    24595    inventory borrowed_book_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT borrowed_book_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.inventory DROP CONSTRAINT borrowed_book_pkey;
       public                 postgres    false    219            �           2606    24596    inventory book_id_key    FK CONSTRAINT     s   ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT book_id_key FOREIGN KEY (book_id) REFERENCES public.book(id);
 ?   ALTER TABLE ONLY public.inventory DROP CONSTRAINT book_id_key;
       public               postgres    false    3220    219    218            �           2606    24601    inventory user_id_key    FK CONSTRAINT     w   ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT user_id_key FOREIGN KEY (user_id) REFERENCES public.app_user(id);
 ?   ALTER TABLE ONLY public.inventory DROP CONSTRAINT user_id_key;
       public               postgres    false    217    219    3218            *   q   x�E˫B1 P��K�ݺJ�@���0{t	������:�a�Ȅ�-�xQ�ܬ���dg�2���us�.��J*%��)"P4$dN�jL�r����t<�ҟ��w �X`�      +   s  x����n�0�u�~�z��6�j��J�UHl���i`�9����q�`�n�Hv�>�s�f��3�U,(��43�!�������
��aY��a���<�2�Ղe;��*N���k7vp�L��[Ǚw\z����~�[��ʳ����l���gL�:+�c
T.i� )��!���݂���M"n�`�8������	�/O{�c��0M?C{�����2�2Y�� 
2#U�(�"�R��X�KN�������?����uJ�(�}:����|��1���l *����yu^�W��.ۭ��{Ӹ9�e���[�]�!���xם��Ci�������u��@��\�#�FhFG*Yn}��w`h�ȭT����7X�'�����S�zpL�6�?!7V��Bے���B�����'P�=��������T��ghhN��Eh����f�tF><���R�����h�f�<N�3f���GX���KD�vs~�s����Ӗ�`HC�;e�Ռ�@cw��ϰ+���C�SY��͠���r�{�=kc9���r�7c���SiKs��E���R��В��B�LDi��:�<.���c:ǵ�wG�v�:�*n�[l�?���U���ҌfY      ,   �  x��VK�d7[W�"P���}��`6���?B���}j�t���I����kQ�0Y��hI;���2X��3_��:-���t��[��J{��?o���J����4m^��ԙ��<�}B��7Xh�~��ܾ5�z�	���ܼ�n�K�U4R�bżY���W���,e)S�暴K�)�o���4�	٨MsI'��+�f}�<U�]�Q��C��*}�<�c�O�摱�ͻ'Y=�ש�f���c��m=I�2i�-�sЗ2�/=ζ6mk�@5���NV�kЃ�|�':�A[��C%����vZzy}��]u�M����S+Ǣ��l1��</�1���� ��P�Z~�b��O���琯��Li���J����ZyKl�2��e�M'��?���HD����{�^�����%ᛴAz;��3h{q�M����v��C�Gk9�c�÷�y��	T Cəq�&��2��&�zĔO��cN���0m��])�}]{[�M���_��B�VA�D�����W���nu
C!i�	�3*.���b�-#���(���i{��������pY: ����fP+;Sh�<�k�m�����G��G\)�{��	�B<��K� �i��(1HU#�8^�X�?���&��⚈�&[�W^��ÐW~]ga�^�$���K5޳�0o�ڀ_f"h��i��]��7^5}��8k6C����)�T$���)i�(:�[��������X��~[~��2��N4�,/� ~�_��K���U�:��d|K�B�4�ol��q&��B���K ][��O�;�X'޾ſ\Y����؁s�`����S�������:�����g�����(�&��9.��ԣ�?�|\ǿ�R��-G�7�X��O��c,�6�g�Qy����u,�������S�W���g{�kC)��`/l�����e�:T�� �j����}Ƿ��w�����~���7�(     