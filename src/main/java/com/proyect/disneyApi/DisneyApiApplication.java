package com.proyect.disneyApi;

import com.proyect.disneyApi.model.*;
import com.proyect.disneyApi.model.Character;
import com.proyect.disneyApi.service.CharacterService;
import com.proyect.disneyApi.service.GenderService;
import com.proyect.disneyApi.service.MovieService;
import com.proyect.disneyApi.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.URL;
import java.time.LocalDate;

@SpringBootApplication
public class DisneyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisneyApiApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public CommandLineRunner initData(CharacterService characterService, GenderService genderService,
									  MovieService movieService, UserService userService){
		return (args) ->{

			//Roles
			Role user = new Role(1L, "USER");
			Role admin = new Role(2L, "ADMIN");
			userService.saveRole(user);
			userService.saveRole(admin);

			//Users
			User user1 = new User(1L, "PABLO", "PEREZ","gabriel@jedify.io", "pablo1234", null);
			User user2 = new User(2L, "GABRIEL", "TORRELABA", "gabriel.torrealba33@gmail.com", "gabriel1234", null);
			userService.saveUser(user1);
			userService.saveUser(user2);

			userService.addRoleToUser("gabriel@jedify.io", "USER");
			userService.addRoleToUser("gabriel.torrealba33@gmail.com", "ADMIN");


			//Genders
			Gender gender1 = new Gender(1L,"ACCION/AVENTURA",null);
			Gender gender2 = new Gender(2L,"COMEDIA",null);
			Gender gender3 = new Gender(3L,"FANTASIA",null);
			Gender gender4 = new Gender(4L,"FAMILIAR",null);
			Gender gender5 = new Gender(5L,"MUSICAL",null);
			genderService.saveGender(gender1);
			genderService.saveGender(gender2);
			genderService.saveGender(gender3);
			genderService.saveGender(gender4);
			genderService.saveGender(gender5);

			//Characters
			Character character1 = new Character(1L, new URL("https://static.wikia.nocookie.net/disney/images/b/ba/A.R.F.png"), "A.R.F.", 2, 10F,"A.R.F. is a supporting character in the Disney Junior animated series Puppy Dog Pals. He is a robot dog invented by Bob to spend time with Bingo and Rolly while he is away at work and to help clean their messes.", null);
			Character character2 = new Character(2L, new URL("https://static.wikia.nocookie.net/disney/images/9/95/Abigail_Markham.jpeg"), "ABIGAIL MARKHAM", 5, 15f, "Abby arrives in the jungle along with her father and workers as he intends to build a new life in Africa. When she falls ill with the same plague that threatens his men, he asks for Tarzan's help to cure her.",null);
			Character character3 = new Character(3L, new URL("https://static.wikia.nocookie.net/disney/images/0/05/Fox-disneyscreencaps_com-901.jpg"), "ABIGAIL THE COW", 30, 50F,"In the movie, Widow Tweed is milking Abigail when Tod comes in. He starts playing with Abigail's tail. Abigail starts to moo in annoyance, and Widow Tweed tells Tod to stop pestering Abigail.",null);
			Character character4 = new Character(4L, new URL("https://static.wikia.nocookie.net/disney/images/b/ba/Abis_Mal%27s_Thugs.jpg"), "ABIS MAL´S THUGS", 60, 55F, "Despite his obvious stupidity and incompetence as an antagonist, Abis Mal has his own gang of various sword-wielding thieves who do his bidding.", null);
			Character character5 = new Character(5L, new URL("https://static.wikia.nocookie.net/disney/images/3/3f/Profile_-_Abu.png"),"ABU", 7, 8F, "He is a mischievous petty thief who has the honor of being Aladdin 's best friend , as well as the Genie and Jasmine . Although he is a monkey and does not speak, he chatters in a way that is understood.", null );
			Character character6 = new Character(6L, new URL("https://static.wikia.nocookie.net/disney/images/d/d3/Vlcsnap-2015-05-06-23h04m15s601.png"), "ACHILLES", 30, 75F,"Achilles is an an old Greek hero whom almost everyone except for Hercules has forgotten. He was once trained by Phil, but he failed due to his heel.",null);
			Character character7 = new Character(7L, new URL("https://static.wikia.nocookie.net/disney/images/b/b9/Profile_-_Boun.jpeg"), "BOUN", 10, 15F,"Boun is a supporting character in the 2021 Disney animated film Raya and the Last Dragon . He is a street businessman and owner of a boat restaurant, the Shrimporium.", null);
			Character character8 = new Character(8L, new URL("https://static.wikia.nocookie.net/disney/images/0/04/Fantasia-disneyscreencaps.com-8985.jpg"),"CENTAURUS", 35, 85F, "The Centaurs first appeared during the \"Pastoral Symphony\" in Fantasia. They were handsome horse-like men of different colors who were in love with the female horse-like centaurettes.", null);
			Character character9 = new Character(9L, new URL("https://static.wikia.nocookie.net/disney/images/b/bc/Jabberwock.jpg"),"JABBERWOCKY", 100, 250F,"This monster is the one who gives power to the Red Queen , since it makes the inhabitants of the Underworld fear her for him. According to the Oracle he must be defeated by Alicia.", null);
			Character character10 = new Character(10L, new URL("https://static.wikia.nocookie.net/disney/images/9/99/The_matchmaker_Mulan_.jpg"), "THE MATCHMAKER", 55, 65F, "The Matchmaker is responsible for arranging marriages and evaluating potential brides and grooms. Thus, she holds a great deal of influence.", null );
			characterService.saveCharacter(character1);
			characterService.saveCharacter(character2);
			characterService.saveCharacter(character3);
			characterService.saveCharacter(character4);
			characterService.saveCharacter(character5);
			characterService.saveCharacter(character6);
			characterService.saveCharacter(character7);
			characterService.saveCharacter(character8);
			characterService.saveCharacter(character9);
			characterService.saveCharacter(character10);

			//Movies
			Movie movie1 = new Movie(1L,new URL("https://static.wikia.nocookie.net/disney/images/8/89/Puppy_Dog_Pals.png/revision/latest/scale-to-width-down/1000?cb=20170323153225"),"PUPPY DOG PALS", LocalDate.parse("2017-04-14"), 7.1F, gender4, null);
			Movie movie2 = new Movie(2L,new URL("https://static.wikia.nocookie.net/disney/images/2/2f/LegendOfTarzanTitle.jpg/revision/latest/scale-to-width-down/640?cb=20130915041202"),"THE LEGEND OF TARZAN", LocalDate.parse("2001-06-25"), 6.8F, gender1,null );
			Movie movie3 = new Movie(3L,new URL("https://static.wikia.nocookie.net/disney/images/9/9b/The_Fox_and_the_Hound_poster.jpg/revision/latest/scale-to-width-down/1000?cb=20200615134150"), "THE FOX AND THE HOUND", LocalDate.parse("1981-07-10"), 7.3F, gender4, null);
			Movie movie4 = new Movie(4L,new URL("https://static.wikia.nocookie.net/disney/images/3/33/Aladdin_TV_series_title.gif/revision/latest?cb=20110825230840"), "ALADDIN (TV SERIE)", LocalDate.parse("1994-08-15"), 7.3F, gender2, null);
			Movie movie5 = new Movie(5L,new URL("https://static.wikia.nocookie.net/disney/images/d/de/Aladdin_ver2_xlg.jpg/revision/latest?cb=20160923054445&path-prefix=es"), "ALADDIN", LocalDate.parse("1992-11-25"), 8.1F, gender2, null);
			Movie movie6 = new Movie(6L,new URL("https://static.wikia.nocookie.net/disney/images/7/76/Hercules.png/revision/latest?cb=20151108163136"), "HERCULES", LocalDate.parse("1997-02-12"), 7.3F, gender5, null);
			Movie movie7 = new Movie(7L,new URL("https://static.wikia.nocookie.net/disney/images/b/b5/Raya_and_the_Last_Dragon_Poster.jpeg/revision/latest?cb=20210205183350&path-prefix=es"), "RAYA AND THE LAST DRAGON", LocalDate.parse("2021-03-05"), 7.3F, gender1, null);
			Movie movie8 = new Movie(8L,new URL("https://static.wikia.nocookie.net/disney/images/1/12/Fantasia-poster-1940.jpg/revision/latest?cb=20110715005424"), "FANTASIA", LocalDate.parse("1940-10-13"), 7.7F, gender3, null);
			Movie movie9 = new Movie(9L,new URL("https://static.wikia.nocookie.net/disney/images/c/c9/Aliceposter.jpg/revision/latest/scale-to-width-down/1000?cb=20160617163012"), "ALICE IN WONDERLAND", LocalDate.parse("1951-05-21"), 7.4F, gender5, null);
			Movie movie10 = new Movie(10L,new URL("https://static.wikia.nocookie.net/disney/images/a/a5/Mulan.JPG/revision/latest?cb=20140316163838"), "MULAN", LocalDate.parse("1998-06-09"),7.6F, gender1, null);
			movieService.saveMovie(movie1);
			movieService.saveMovie(movie2);
			movieService.saveMovie(movie3);
			movieService.saveMovie(movie4);
			movieService.saveMovie(movie5);
			movieService.saveMovie(movie6);
			movieService.saveMovie(movie7);
			movieService.saveMovie(movie8);
			movieService.saveMovie(movie9);
			movieService.saveMovie(movie10);

			characterService.addCharacter("A.R.F.", "PUPPY DOG PALS");
			characterService.addCharacter("ABIGAIL MARKHAM", "THE LEGEND OF TARZAN");
			characterService.addCharacter("ABIGAIL THE COW", "THE FOX AND THE HOUND");
			characterService.addCharacter("ABIS MAL´S THUGS", "ALADDIN (TV SERIE)");
			characterService.addCharacter("ABU", "ALADDIN");
			characterService.addCharacter("ACHILLES", "HERCULES");
			characterService.addCharacter("BOUN", "RAYA AND THE LAST DRAGON");
			characterService.addCharacter("CENTAURUS", "FANTASIA");
			characterService.addCharacter("JABBERWOCKY", "ALICE IN WONDERLAND");
			characterService.addCharacter("THE MATCHMAKER", "MULAN");

			movieService.addMovie("A.R.F.", "PUPPY DOG PALS");
			movieService.addMovie("ABIGAIL MARKHAM", "THE LEGEND OF TARZAN");
			movieService.addMovie("ABIGAIL THE COW", "THE FOX AND THE HOUND");
			movieService.addMovie("ABIS MAL´S THUGS", "ALADDIN (TV SERIE)");
			movieService.addMovie("ABU", "ALADDIN");
			movieService.addMovie("ACHILLES", "HERCULES");
			movieService.addMovie("BOUN", "RAYA AND THE LAST DRAGON");
			movieService.addMovie("CENTAURUS", "FANTASIA");
			movieService.addMovie("JABBERWOCKY", "ALICE IN WONDERLAND");
			movieService.addMovie("THE MATCHMAKER", "MULAN");


		};
	}

}
