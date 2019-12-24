
insert into owl_user (name, password, email, status) values ("admin","b494cbc4e895f38317b362081133d6e6","admin@admin.com",1);

insert into owl_user_role (user_id, role_id) values (1,1);

insert into owl_role(role) values ("admin");

insert into owl_role_permission (role_id, permission_id) values (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),
(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24);

insert into owl_permission(permission_url)  values ("/permission/create"),("/permission/remove"),
("/permission/removeList"),("/permission/update"),("/permission/details"),("/permission/list")
,("/role/create"),("/role/remove"),("/role/removeList"),("/role/update"),("/role/details"),("/role/list"),
("/role/addPermissionListByRoleId"),("/role/deleteByRoleIDAndPermissionIDList"),("/role/deleteAllPermissionByRoleID"),
 ("/user/create"),("/user/remove"),("/user/removeList"), ("/user/update"),("/user/details"),("/user/list"),
  ("/user/addRoleListByUserID"),("/user/deleteByUserIDAndRoleIDList"),("/user/deleteAllRoleByUserID");

