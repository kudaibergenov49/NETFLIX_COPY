insert into dbtest.users(created,
                         email,
                         first_name,
                         last_name,
                         password,
                         status,
                         updated,
                         username)
values ('2020-04-05 18:54:06',
        'admin@mail.ru',
        'admin_fn',
        'admin_ln',
        '$2y$04$eFyZ4L63ZIo0QA361xkHX.yc9HiWZAmOdj0U45UsuirhVh8W/STci',
        'ACTIVE',
        '2020-04-05 18:54:06',
        'admin');

insert into dbtest.users(created,
                         email,
                         first_name,
                         last_name,
                         password,
                         status,
                         updated,
                         username)
values ('2020-04-05 18:54:06',
        'user@mail.ru',
        'user_fn',
        'user_ln',
        '$2y$04$R.bx1DzzWLdWd0SHlPrdR.Dvse3oYE08uc.VjSH9l/LQeoqYzmFxO',
        'ACTIVE',
        '2020-04-05 18:54:06',
        'user');

commit;