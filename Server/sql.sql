drop database IPS;

create database IPS;

create table booth(
	booth_id int(11) auto_increment primary key,
	information text,
	booth_id_model int(11) unique
);

create table booth_info(
	info_id int(11) auto_increment primary key,
	booth_id int(11),
	title varchar(50),
	subtitle text,
	description text,
	FOREIGN KEY (booth_id) REFERENCES booth(booth_id)
);

INSERT INTO `booth` (`information`, `booth_id_model`) VALUES ('Test booth 1', '1'), ('Test booth 2', '2') , ('Test booth 3', '3');

INSERT INTO `booth_info` ( `booth_id`, `title`, `subtitle`, `description`) VALUES ('1', 'testInfo1', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis, mauris nec congue auctor, odio tortor lobortis lectus, vel iaculis metus tortor nec ante. Vestibulum ultricies venenatis turpis, et bibendum nibh laoreet id. Etiam quis convallis sem. Maecenas accumsan eu felis id ornare. Vivamus dui arcu, aliquet maximus lacus nec, tempus aliquet velit. Curabitur nec hendrerit sem. Phasellus eget enim non leo sollicitudin luctus sed vel orci. Donec viverra mauris justo, sit amet elementum arcu accumsan tincidunt.', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis, mauris nec congue auctor, odio tortor lobortis lectus, vel iaculis metus tortor nec ante. Vestibulum ultricies venenatis turpis, et bibendum nibh laoreet id. Etiam quis convallis sem. Maecenas accumsan eu felis id ornare. Vivamus dui arcu, aliquet maximus lacus nec, tempus aliquet velit. Curabitur nec hendrerit sem. Phasellus eget enim non leo sollicitudin luctus sed vel orci. Donec viverra mauris justo, sit amet elementum arcu accumsan tincidunt.');


INSERT INTO `booth_info` ( `booth_id`, `title`, `subtitle`, `description`) VALUES ('1', 'testInfo1a', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis, mauris nec congue auctor, odio tortor lobortis lectus, vel iaculis metus tortor nec ante. Vestibulum ultricies venenatis turpis, et bibendum nibh laoreet id. Etiam quis convallis sem. Maecenas accumsan eu felis id ornare. Vivamus dui arcu, aliquet maximus lacus nec, tempus aliquet velit. Curabitur nec hendrerit sem. Phasellus eget enim non leo sollicitudin luctus sed vel orci. Donec viverra mauris justo, sit amet elementum arcu accumsan tincidunt.', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis, mauris nec congue auctor, odio tortor lobortis lectus, vel iaculis metus tortor nec ante. Vestibulum ultricies venenatis turpis, et bibendum nibh laoreet id. Etiam quis convallis sem. Maecenas accumsan eu felis id ornare. Vivamus dui arcu, aliquet maximus lacus nec, tempus aliquet velit. Curabitur nec hendrerit sem. Phasellus eget enim non leo sollicitudin luctus sed vel orci. Donec viverra mauris justo, sit amet elementum arcu accumsan tincidunt.');



INSERT INTO `booth_info` ( `booth_id`, `title`, `subtitle`, `description`) VALUES ('2', 'testInfo2', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis, mauris nec congue auctor, odio tortor lobortis lectus, vel iaculis metus tortor nec ante. Vestibulum ultricies venenatis turpis, et bibendum nibh laoreet id. Etiam quis convallis sem. Maecenas accumsan eu felis id ornare. Vivamus dui arcu, aliquet maximus lacus nec, tempus aliquet velit. Curabitur nec hendrerit sem. Phasellus eget enim non leo sollicitudin luctus sed vel orci. Donec viverra mauris justo, sit amet elementum arcu accumsan tincidunt.', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis, mauris nec congue auctor, odio tortor lobortis lectus, vel iaculis metus tortor nec ante. Vestibulum ultricies venenatis turpis, et bibendum nibh laoreet id. Etiam quis convallis sem. Maecenas accumsan eu felis id ornare. Vivamus dui arcu, aliquet maximus lacus nec, tempus aliquet velit. Curabitur nec hendrerit sem. Phasellus eget enim non leo sollicitudin luctus sed vel orci. Donec viverra mauris justo, sit amet elementum arcu accumsan tincidunt.');





INSERT INTO `booth_info` ( `booth_id`, `title`, `subtitle`, `description`) VALUES ('3', 'testInfo3', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis, mauris nec congue auctor, odio tortor lobortis lectus, vel iaculis metus tortor nec ante. Vestibulum ultricies venenatis turpis, et bibendum nibh laoreet id. Etiam quis convallis sem. Maecenas accumsan eu felis id ornare. Vivamus dui arcu, aliquet maximus lacus nec, tempus aliquet velit. Curabitur nec hendrerit sem. Phasellus eget enim non leo sollicitudin luctus sed vel orci. Donec viverra mauris justo, sit amet elementum arcu accumsan tincidunt.', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mollis, mauris nec congue auctor, odio tortor lobortis lectus, vel iaculis metus tortor nec ante. Vestibulum ultricies venenatis turpis, et bibendum nibh laoreet id. Etiam quis convallis sem. Maecenas accumsan eu felis id ornare. Vivamus dui arcu, aliquet maximus lacus nec, tempus aliquet velit. Curabitur nec hendrerit sem. Phasellus eget enim non leo sollicitudin luctus sed vel orci. Donec viverra mauris justo, sit amet elementum arcu accumsan tincidunt.');


